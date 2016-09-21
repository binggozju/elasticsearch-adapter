package org.binggo.esadapter.elasticsearch;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.core.env.Environment;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.DefaultHttpParams;

import org.binggo.esadapter.common.UtilFacade;
import org.binggo.esadapter.common.OpType;

public abstract class AbstractSearcher implements Searcher {
	
	private static final int SEARCH_TIMEOUT_DEFAULT = 10;
	
	private String esClusterUrl; 
	
	private ThreadPoolTaskExecutor taskExecutor;
	private HttpClient httpClient;
	
	public AbstractSearcher(Environment env, UtilFacade utilFacade, String url) {
		taskExecutor = utilFacade.getThreadPool();
		httpClient = utilFacade.getHttpClient();
		
		esClusterUrl = env.getProperty("es.cluster.url") + url;
	}
	
	@Override
	public List<Document> search(Dsl dsl, OpType opType) {
		Callable<List<Document>> task = new SearchTask(dsl, opType);
		Future<List<Document>> future = taskExecutor.submit(task);
		
		try {
			List<Document> ret = future.get(SEARCH_TIMEOUT_DEFAULT, TimeUnit.SECONDS);
			return ret;
		} catch (ExecutionException ex) {
			return null;
		} catch (InterruptedException ex) {
			return null;
		} catch (TimeoutException ex) {
			return null;
		}
	}
	
	// process the http response from elasticsearch
	protected abstract List<Document> postProcess(String httpResponse, OpType opType);
	
	
	private class SearchTask implements Callable<List<Document>> {
		private Dsl dsl;
		private OpType opType;
		
		public SearchTask(Dsl dsl, OpType opType) {
			this.dsl = dsl;
			this.opType = opType;
		}

		@Override
		public List<Document> call() throws Exception {
			String dslSentence = dsl.getDslSentence();
			
			InputStream stream;
			try {
				stream = new ByteArrayInputStream(dslSentence.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
			RequestEntity entity = new InputStreamRequestEntity(stream);
			
			PostMethod post = new PostMethod(esClusterUrl);
			post.setRequestEntity(entity);
			DefaultHttpParams.getDefaultParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);
			
			String response = "";
			try {
		    	httpClient.executeMethod(post);	
		    	response = new String(post.getResponseBodyAsString().getBytes("UTF-8"));
		    } catch (IOException ex) {
				// handle exception
		    } finally {
		    	post.releaseConnection();
		    }
			
			return postProcess(response, opType);
		}
		
	}

}

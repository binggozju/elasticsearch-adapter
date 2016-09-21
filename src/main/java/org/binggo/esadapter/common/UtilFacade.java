package org.binggo.esadapter.common;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import com.google.gson.JsonParser;

@Component
public class UtilFacade {
	
	@Autowired
	private ThreadPoolUtil threadPoolUtil;
	
	private HttpClient httpClient;
	
	private JsonParser jsonParser;
	
	public UtilFacade() {
		httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
		jsonParser = new JsonParser();
	}
	
	// get the singleton instance of the ThreadPoolTaskExecutor
	public ThreadPoolTaskExecutor getThreadPool() {
		return threadPoolUtil.getThreadPool();
	}
	
	// get the singleton instance of the HttpClient
	public HttpClient getHttpClient() {
		return httpClient;
	}

	// get the singleton instance of the JsonParser
	public JsonParser getJsonParser() {
		return jsonParser;
	}
	
}

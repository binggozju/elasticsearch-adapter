package org.binggo.esadapter.elasticsearch;

import java.util.List;

import org.binggo.esadapter.common.OpType;

public interface Searcher {
	
	/**
	 * 
	 * @param dsl the DSL sentence
	 * @param opTpye indicate the type of the search operation
	 * @return the list of document from ElasticSearch
	 */
	List<Document> search(Dsl dsl, OpType opType);

}

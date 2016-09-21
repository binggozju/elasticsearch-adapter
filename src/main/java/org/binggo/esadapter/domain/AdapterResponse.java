package org.binggo.esadapter.domain;

import java.util.List;

import org.binggo.esadapter.elasticsearch.Document;

public class AdapterResponse {
	
	private int errcode;
	private List<Document> result;
	
	public AdapterResponse(int errcode, List<Document> result) {
		this.errcode = errcode;
		this.result = result;
	}
	
	@Override
	public String toString() {
		if (result == null) {
			return String.format("{\"errcode\": %d, \"result\": []}", errcode);
		}
		
		StringBuilder docList = new StringBuilder();
		for (Document doc : result) {
			docList.append(doc.toString() + ",");
		}
		
		if (docList.length() > 0) {
			docList.deleteCharAt(docList.length() - 1);
		}
		return String.format("{\"errcode\": %d, \"result\": [%s]}", errcode, docList.toString());
	}
}

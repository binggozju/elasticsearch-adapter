package org.binggo.esadapter.common;

public enum OpType {
	
	// /weishop/shipment/*
	SHIPMENT_INFO (0, "/weishop/shipment/shipmentinfo"),
	SHIPMENT_GROUP_STAT (1, "/weishop/shipment/groupstat"),
	
	// other
	UNKNOWN_TYPE (999, "/unknown");
	
	private final int opId;
	private final String opName;
	
	OpType(int opId, String opName) {
		this.opId = opId;
		this.opName = opName;
	}

	public int getOpId() {
		return opId;
	}

	public String getOpName() {
		return opName;
	}
	
}

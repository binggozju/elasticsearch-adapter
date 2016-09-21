package org.binggo.esadapter.domain.shipment;

import org.binggo.esadapter.elasticsearch.Document;
import org.binggo.esadapter.elasticsearch.Dsl;

public class ShipmentGroupStat implements Document {
	
	private long shippingId;
	private long productId;
	private long count;
	
	public ShipmentGroupStat(long shippingId, long productId, long count) {
		this.shippingId = shippingId;
		this.productId = productId;
		this.count = count;
	}
	
	/**
	 * get the shipment group statistics group by shipping_id and product_id
	 */
	public static class GroupStatDslArgument implements Dsl {
		private long facilityId;
		private String status;
		private long orderStatus;
		
		private static String dslTemplate = "{" +
			"\"query\": {" +
			    "\"filtered\": {" +
			        "\"query\": { \"match_all\": {} }," +
			        "\"filter\": {" +
			            "\"and\": [" +
			                "{ \"term\": {\"facility_id\": %d} }," +
			                "{ \"term\": {\"status\": \"%s\"} }," +
			                "{ \"term\": {\"order_status\": %d} }" +
			            "] } } }," +
			"\"aggregations\": {" +
			    "\"groupby_shipping_id\": {" +
			        "\"terms\": {\"field\": \"shipping_id\"}," +
			        "\"aggregations\": {" +
			            "\"groupby_product_id\": {" +
			                "\"terms\": {\"field\": \"product_id\"}" +
			            "} } } }," +
			"\"from\": 0," +                                                                                            
			"\"size\": 0" +
			"}";

		public long getFacilityId() {
			return facilityId;
		}
		public void setFacilityId(long facilityId) {
			this.facilityId = facilityId;
		}

		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		
		public long getOrderStatus() {
			return orderStatus;
		}
		public void setOrderStatus(long orderStatus) {
			this.orderStatus = orderStatus;
		}
		
		@Override
		public String toString() {
			return String.format("GroupStatDslArgument[facilityId=%d, status=%s, orderStatus=%d]", facilityId, status, orderStatus);
		}
		
		@Override
		public String getDslSentence() {
			return String.format(dslTemplate, facilityId, status, orderStatus);
		}
	}
	
	public long getShippingId() {
		return shippingId;
	}
	public void setShippingId(long shippingId) {
		this.shippingId = shippingId;
	}
	
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	
	@Override
	public String toString() {
		return String.format("{ \"shippingId\": %d, \"productId\": %d, \"count\": %d }", shippingId, productId, count);
	}

}

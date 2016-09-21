package org.binggo.esadapter.domain.shipment;

import org.binggo.esadapter.elasticsearch.Document;
import org.binggo.esadapter.elasticsearch.Dsl;

public final class ShipmentInfo implements Document {
	
	private long shipmentId;
	private String status;
	private long shippingId;
	private long facilityId;
	private long productId;
	private long provinceId;
	private long cityId;
	private long districtId;
	private long orderStatus;
	private String createdTime;
	private String confirmTime;
	
	public static class ShipmentInfoDslArgument implements Dsl {
		private long facilityId;
		private long shippingId;
		private long productId;
		private String status;
		private long orderStatus;
		
		// for paging
		private int pageFrom;
		private int pageTo;
		
		private static String dslTemplate = "{" + 
			"\"query\": {" +
			"\"filtered\": {" +
			"\"query\": {" +
				"\"match_all\": {}" +
			"}," +
			"\"filter\": {" + 
				"\"bool\": {" +
					"\"must\": [" + 
						"{ \"term\": {\"facility_id\": %d} }," + 
						"{ \"term\": {\"shipping_id\": %d} }," +
						"{ \"term\": {\"product_id\": %d}  }," +
						"{ \"term\": {\"status\": %s}      }," +
						"{ \"term\": {\"order_status\": %d}}" +
					"]" +
				"}}}}," +
			"\"_source\": { \"exclude\": [] }," +
			"\"from\": %d," +
			"\"size\": %d}";
		
		public long getFacilityId() {
			return facilityId;
		}
		public void setFacilityId(long facilityId) {
			this.facilityId = facilityId;
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
		
		public int getPageFrom() {
			return pageFrom;
		}
		public void setPageFrom(int pageFrom) {
			this.pageFrom = pageFrom;
		}
		
		public int getPageTo() {
			return pageTo;
		}
		public void setPageTo(int pageTo) {
			this.pageTo = pageTo;
		}
		
		@Override
		public String toString() {
			return String.format("ShipmentInfoDslArgument[facilityId=%d, shippingId=%d, productId=%d]", facilityId, shippingId, productId);
		}
		
		@Override
		public String getDslSentence() {
			return String.format(dslTemplate, facilityId, shippingId, productId, status, orderStatus, pageFrom, pageTo);
		}
	}


	public long getShipmentId() {
		return shipmentId;
	}
	public void setShipmentId(long shipmentId) {
		this.shipmentId = shipmentId;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public long getShippingId() {
		return shippingId;
	}
	public void setShippingId(long shippingId) {
		this.shippingId = shippingId;
	}
	
	public long getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(long facilityId) {
		this.facilityId = facilityId;
	}
	
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	public long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}
	
	public long getCityId() {
		return cityId;
	}
	public void setCityId(long cityId) {
		this.cityId = cityId;
	}
	
	public long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(long districtId) {
		this.districtId = districtId;
	}
	
	public long getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(long orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	
	public String getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(String confirmTime) {
		this.confirmTime = confirmTime;
	}
	
	@Override
	public String toString() {
		return String.format("{ \"shipmentId\": %d, \"status\": \"%s\", \"shippingId\": %d, \"facilityId\": %d, "
				+ "\"productId\": %d, \"provinceId\": %d, \"cityId\": %d, \"districtId\": %d, \"orderStatus\": %d, "
				+ "\"createdTime\": \"%s\", \"confirmTime\": \"%s\"}", shipmentId, status, shippingId, facilityId, 
				productId, provinceId, cityId, districtId, orderStatus, createdTime, confirmTime);
	}

}

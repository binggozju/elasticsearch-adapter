
package org.binggo.esadapter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.binggo.esadapter.elasticsearch.AbstractSearcher;
import org.binggo.esadapter.elasticsearch.Document;
import org.binggo.esadapter.common.UtilFacade;
import org.binggo.esadapter.common.OpType;
import org.binggo.esadapter.domain.shipment.*;

@Service
public class ShipmentService extends AbstractSearcher {
	
	private static final Logger logger = LoggerFactory.getLogger(ShipmentService.class);
	
	private static final String url = "/weishop/shipment/_search";
	
	private JsonParser jsonParser;
	
	@Autowired
	public ShipmentService(Environment env, UtilFacade utilFacade) {
		super(env, utilFacade, url);
		
		jsonParser = utilFacade.getJsonParser();
	}

	@Override
	protected List<Document> postProcess(String httpResponse, OpType opType) {
		logger.debug("response from elasticsearch: " + httpResponse);
		if (httpResponse == "") {
			logger.error("fail to get response from elasticsearch");
			return null;
		}

		try {	
			if (opType == OpType.SHIPMENT_INFO) {
				return processShipmentInfo(httpResponse);
			} else if (opType == OpType.SHIPMENT_GROUP_STAT) {
				return processGroupStat(httpResponse);
			} else {
				logger.warn(String.format("ShipmentService can't process OpType: %s", opType.toString()));
			}
			
		} catch (JsonSyntaxException ex) {
			logger.error("fail to parse the response from elasticsearch");
		}

		return null;
	}

	// process the response of shipmentinfo operation
	private List<Document> processShipmentInfo(String httpResponse) throws JsonSyntaxException {
		List<Document> result = new ArrayList<Document>();

		JsonObject jsonObj = jsonParser.parse(httpResponse).getAsJsonObject();
		if (jsonObj.has("error")) {
			logger.warn("the parameters from client are invalid, whose type may be wrong");
			return null;
		}
		
		JsonArray jsonDocArray = jsonObj.get("hits").getAsJsonObject().get("hits").getAsJsonArray();
		logger.debug(String.format("the number of documents in the response from elasticsearch: %d", jsonDocArray.size()));
		
		for (JsonElement jsonDoc: jsonDocArray) {
			JsonObject jsonSource = jsonDoc.getAsJsonObject().get("_source").getAsJsonObject();
			ShipmentInfo shipment = new ShipmentInfo();
			
			shipment.setShipmentId(jsonSource.get("shipment_id").getAsLong());
			
			if (jsonSource.get("status").isJsonNull()) {
				shipment.setStatus("");
			} else {
				shipment.setStatus(jsonSource.get("status").getAsString());
			}
			
			shipment.setShippingId(jsonSource.get("shipping_id").getAsLong());
			
			if (jsonSource.get("facility_id").isJsonNull()) {
				shipment.setFacilityId(-1);
			} else {
				shipment.setFacilityId(jsonSource.get("facility_id").getAsLong());
			}
			
			if (jsonSource.get("product_id").isJsonNull()) {
				shipment.setProductId(-1);
			} else {
				shipment.setProductId(jsonSource.get("product_id").getAsLong());
			}

			shipment.setProvinceId(jsonSource.get("province_id").getAsLong());
			shipment.setCityId(jsonSource.get("city_id").getAsLong());
			shipment.setDistrictId(jsonSource.get("district_id").getAsLong());
			shipment.setOrderStatus(jsonSource.get("order_status").getAsLong());
			
			if (jsonSource.get("created_time").isJsonNull()) {
				shipment.setCreatedTime("");
			} else {
				shipment.setCreatedTime(jsonSource.get("created_time").getAsString());
			}
			
			if (jsonSource.get("confirm_time").isJsonNull()) {
				shipment.setConfirmTime("");
			} else {
				shipment.setConfirmTime(jsonSource.get("confirm_time").getAsString());
			}
			
			result.add(shipment);
		}
			
		return result;
	}
	
	// process the response of groupstat operation
	private List<Document> processGroupStat(String httpResponse) throws JsonSyntaxException {
		List<Document> result = new ArrayList<Document>();
		
		JsonObject jsonObj = jsonParser.parse(httpResponse).getAsJsonObject();
		if (jsonObj.has("error")) {
			logger.warn("the parameters from client are invalid, whose type may be wrong");
			return null;
		}
		
		JsonArray jsonShippingIdBucket = jsonObj.get("aggregations").getAsJsonObject().
				get("groupby_shipping_id").getAsJsonObject().get("buckets").getAsJsonArray();
		
		for (JsonElement jsonShippingId: jsonShippingIdBucket) {
			long shippingId = jsonShippingId.getAsJsonObject().get("key").getAsLong();
			
			JsonArray jsonProductIdBucket = jsonShippingId.getAsJsonObject().get("groupby_product_id").
					getAsJsonObject().get("buckets").getAsJsonArray();
			
			for (JsonElement jsonProductId: jsonProductIdBucket) {
				long productId = jsonProductId.getAsJsonObject().get("key").getAsLong();
				long count = jsonProductId.getAsJsonObject().get("doc_count").getAsLong();
				
				ShipmentGroupStat shipmentGroupStat = new ShipmentGroupStat(shippingId, productId, count);
				result.add(shipmentGroupStat);
			}
		}
		
		return result;
	}
}

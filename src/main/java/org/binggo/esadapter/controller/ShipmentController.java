package org.binggo.esadapter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;

import org.binggo.esadapter.common.OpType;
import org.binggo.esadapter.domain.AdapterResponse;
import org.binggo.esadapter.domain.shipment.ShipmentInfo;
import org.binggo.esadapter.domain.shipment.ShipmentGroupStat;
import org.binggo.esadapter.elasticsearch.Document;
import org.binggo.esadapter.service.ShipmentService;

@RestController
@RequestMapping("/weishop/shipment")
public class ShipmentController {
	
	private static final Logger logger = LoggerFactory.getLogger(ShipmentController.class);
	
	private ShipmentService shipmentService;
	
	@Autowired
	public void setShipmentService(ShipmentService shipmentService) {
		this.shipmentService = shipmentService;
		
	}
	
	@RequestMapping(value="/shipmentinfo", method=RequestMethod.POST, consumes={"application/json"})
	public String getShipmentInfo(@RequestBody ShipmentInfo.ShipmentInfoDslArgument dslArg) throws Exception {
		logger.info("receive a request to /weishop/shipment/shipmentinfo: " + dslArg.toString());
		
		List<Document> result = shipmentService.search(dslArg, OpType.SHIPMENT_INFO);
		
		if (result == null) {
			logger.error("ShipmentController has not get the search result of operation " + OpType.SHIPMENT_INFO.toString());
			return new AdapterResponse(1, result).toString();
		}
		
		return new AdapterResponse(0, result).toString();
	}
	
	@RequestMapping(value="/groupstat", method=RequestMethod.POST, consumes={"application/json"})
	public String getGroupStat(@RequestBody ShipmentGroupStat.GroupStatDslArgument dslArg) throws Exception {
		logger.info("receive a request to /weishop/shipment/groupstat: " + dslArg.toString());
		
		List<Document> result = shipmentService.search(dslArg, OpType.SHIPMENT_GROUP_STAT);
		
		if (result == null) {
			logger.error("ShipmentController has not get the search result of operation " + OpType.SHIPMENT_GROUP_STAT.toString());
			return new AdapterResponse(1, result).toString();
		}
		
		return new AdapterResponse(0, result).toString();
	}

}

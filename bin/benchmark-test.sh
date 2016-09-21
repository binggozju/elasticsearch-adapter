#!/bin/bash
# -------------------------------------------------------------
# Description: 
# -------------------------------------------------------------

COUNT=2

HOST=http://localhost:8012

function send_search_request() {
	curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{\"facilityId\": 46, \"shippingId\": 4, \"productId\": 3, \"status\": "1", \"orderStatus\": 2}" $HOST/weishop/shipment 2> /dev/null
}

for((i=1; i<= $COUNT; i++)); do
	echo "$i"
	send_search_request
	echo
done

echo "complete the benchmark test."


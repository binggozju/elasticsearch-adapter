#!/bin/bash

HOST=http://localhost:8012

shipment_getinfo_request=$(cat <<EOF
{
    "facilityId": 46,
    "shippingId": 4,
    "productId": 3,
    "status": "1",
    "orderStatus": 2,

    "pageFrom": 0,
    "pageTo": 10
}
EOF
)
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "$shipment_getinfo_request" $HOST/weishop/shipment/shipmentinfo 2> /dev/null
#!/bin/bash
# --------------------------------------------------------------
# Description: 
# --------------------------------------------------------------

if [ $# -ne 1 ]; then
	echo "Usage: $0 local|pre-release|production"
	exit 1
fi

ENV=$1
case $ENV in
	local|pre-release|production)
	;;

	*)
	echo "Error: unvalid parameter"
	echo "Usage: $0 local|pre-release|production"
	exit 1
	;;
esac

# kill the old esadapter process
ps -ef | grep esadapter.properties | grep -v 'grep' | awk '{print $2}' | xargs -I {} kill -9 {}
wait

DIR=$(dirname `readlink -m $0`)
cd $DIR/..; pwd

# configuration of JVM
#JAVA_OPTS="-Xms512m -Xmx1g"
JAVA_OPTS=
echo "JAVA_OPTS: ${JAVA_OPTS}"

# configuration of esadapter
ESADAPTER_OPTS="--spring.config.location=config/esadapter.properties,config/${ENV}.properties"
echo "ESADAPTER_OPTS: ${ESADAPTER_OPTS}"

if [ $ENV = local ]; then
	echo "in the local environment"
	exec java $JAVA_OPTS -jar target/elasticsearch-adapter.jar $ESADAPTER_OPTS
else
	echo "in the pre-release|production environment" 
	exec java $JAVA_OPTS -jar libs/elasticsearch-adapter.jar $ESADAPTER_OPTS
fi

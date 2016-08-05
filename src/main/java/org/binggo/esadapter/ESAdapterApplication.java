package org.binggo.esadapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ESAdapterApplication 
{
	private static final Logger logger = LoggerFactory.getLogger(ESAdapterApplication.class);
	
    public static void main( String[] args )
    {
		SpringApplication app = new SpringApplication(ESAdapterApplication.class);
		app.run(args);
		
		logger.info("start msgsender service successfully.");
    }
}
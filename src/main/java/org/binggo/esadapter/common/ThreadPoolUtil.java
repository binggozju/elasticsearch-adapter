package org.binggo.esadapter.common;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.core.env.Environment;

@Component
final class ThreadPoolUtil {
	
	private static final int CORE_POOL_SIZE_DEFAULT = 2;
	private static final int MAX_POOL_SIZE_DEFAULT = 5;
	private static final int KEEPALIVE_SECONDS_DEFAULT = 180;
	private static final int QUEUE_CAPACITY_DEFAULT = 30;
	
	private ThreadPoolTaskExecutor executor;
	
	@Autowired
	public ThreadPoolUtil(Environment env) {
		executor = new ThreadPoolTaskExecutor();
		
		Integer corePoolSize = env.getProperty("searcher.threadpool.core-pool-size", Integer.class);
		if (corePoolSize != null) {
			executor.setCorePoolSize(corePoolSize);
		} else {
			executor.setCorePoolSize(CORE_POOL_SIZE_DEFAULT);
		}
		
		Integer maxPoolSize = env.getProperty("searcher.threadpool.max-pool-size", Integer.class);
		if (maxPoolSize != null) {
			executor.setMaxPoolSize(maxPoolSize);
		} else {
			executor.setMaxPoolSize(MAX_POOL_SIZE_DEFAULT);
		}
		
		Integer keepAliveSeconds = env.getProperty("searcher.threadpool.keepalive-seconds", Integer.class);
		if (keepAliveSeconds != null) {
			executor.setKeepAliveSeconds(keepAliveSeconds);
		} else {
			executor.setKeepAliveSeconds(KEEPALIVE_SECONDS_DEFAULT);
		}
		
		Integer queueCapacity = env.getProperty("searcher.threadpool.queue-capacity", Integer.class);
		if (queueCapacity != null) {
			executor.setQueueCapacity(queueCapacity);
		} else {
			executor.setQueueCapacity(QUEUE_CAPACITY_DEFAULT);
		}
		
		executor.initialize();
	}
	
	public ThreadPoolTaskExecutor getThreadPool() {
		return executor;
	}
	
}

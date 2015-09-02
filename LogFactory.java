package org.koushik.javabrains.messenger.resources;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

public class LogFactory implements Runnable{
	private static final Logger logger = LogManager.getLogger(LogFactory.class);

	public static Multimap<String, String> map = LinkedListMultimap.create();

	public static synchronized void writeLog(String key) {
		Collection<String> logKey = map.get(key);
		// Iterator<String> it=logKey.iterator();

		ThreadContext.put("ROUTINGKEY", "special");
		for (String msg : logKey) {
			logger.info(msg);
		}
		ThreadContext.clearAll();
		map.removeAll(key);
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(map.size()>0){
			Collection<String> keys=map.keySet();
			for(String key:keys){
				writeLog(key);
			}
		}
		
	}



}

/*
 * Copyright (C) 2022  即时通讯网(52im.net) & Jack Jiang.
 * The MobileIMSDK v6.1 Project. 
 * All rights reserved.
 * 
 * > Github地址：https://github.com/JackJiang2011/MobileIMSDK
 * > 文档地址：  http://www.52im.net/forum-89-1.html
 * > 技术社区：  http://www.52im.net/
 * > 技术交流群：320837163 (http://www.52im.net/topic-qqgroup.html)
 * > 作者公众号：“【即时通讯技术圈】”，欢迎关注！
 * > 联系作者：  http://www.52im.net/thread-2792-1-1.html
 *  
 * "即时通讯网(52im.net) - 即时通讯开发者社区!" 推荐开源工程。
 * 
 * QoS4ReciveDaemonRoot.java at 2022-7-12 16:35:57, code by Jack Jiang.
 */
package net.x52im.mobileimsdk.server.qos;

import net.x52im.mobileimsdk.server.protocol.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class QoS4ReciveDaemonRoot
{
	private static Logger logger = LoggerFactory.getLogger(QoS4ReciveDaemonRoot.class);  

	private boolean DEBUG = false;
	private int CHECH_INTERVAL = 5 * 60 * 1000; // 5分钟
	private int MESSAGES_VALID_TIME = 10 * 60 * 1000; // 10分钟
	private ConcurrentMap<String, Long> recievedMessages = new ConcurrentHashMap<String, Long>();
	private Timer timer = null;
	private Runnable runnable = null;
	private boolean _excuting = false;
	private String debugTag = "";
	
	public QoS4ReciveDaemonRoot(int CHECH_INTERVAL, int MESSAGES_VALID_TIME
			, boolean DEBUG, String debugTag)
	{
		if(CHECH_INTERVAL > 0)
			this.CHECH_INTERVAL = CHECH_INTERVAL;
		if(MESSAGES_VALID_TIME > 0)
			this.MESSAGES_VALID_TIME = MESSAGES_VALID_TIME;
		this.DEBUG = DEBUG;
		this.debugTag = debugTag;
	}
	
	private void doTaskOnece()
	{
		if(!_excuting)
		{
			_excuting = true;
			
			if(DEBUG)
				logger.debug("【IMCORE"+this.debugTag+"】【QoS接收方】+++++ START 暂存处理线程正在运行中，当前长度"+recievedMessages.size()+".");
			
			//** 遍历HashMap方法二（在大数据量情况下，方法二的性能要5倍优于方法一）
			Iterator<Entry<String, Long>> entryIt = recievedMessages.entrySet().iterator();  
		    while(entryIt.hasNext())
		    {  
		        Entry<String, Long> entry = entryIt.next();  
		        String key = entry.getKey();  
		        long value = entry.getValue();
		        
		        long delta = System.currentTimeMillis() - value;
				if(delta >= MESSAGES_VALID_TIME)
				{
					if(DEBUG)
						logger.debug("【IMCORE"+this.debugTag+"】【QoS接收方】指纹为"+key+"的包已生存"+delta
							+"ms(最大允许"+MESSAGES_VALID_TIME+"ms), 马上将删除之.");
					recievedMessages.remove(key);
				}
		    }  
		}

		if(DEBUG)
			logger.debug("【IMCORE"+this.debugTag+"】【QoS接收方】+++++ END 暂存处理线程正在运行中，当前长度"+recievedMessages.size()+".");
	
		//
		_excuting = false;
	}
	
	public void startup()
	{
		stop();
		if(recievedMessages != null && recievedMessages.size() > 0)
		{
			for(String key : recievedMessages.keySet())
			{
				putImpl(key);
			}
		}
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() 
		{
			@Override
			public void run()
			{
				doTaskOnece();
			}
		}
		, CHECH_INTERVAL
		, CHECH_INTERVAL);
	}
	
	public void stop()
	{
		if(timer != null)
		{
			try{
				timer.cancel();
			}
			finally{
				timer = null;
			}
		}
	}
	
	public boolean isRunning()
	{
		return timer != null;
	}
	
	public void addRecieved(Protocol p)
	{
		if(p != null && p.isQoS())
			addRecieved(p.getFp());
	}
	public void addRecieved(String fingerPrintOfProtocal)
	{
		if(fingerPrintOfProtocal == null)
		{
			logger.debug("【IMCORE"+this.debugTag+"】无效的 fingerPrintOfProtocal==null!");
			return;
		}
		
		if(recievedMessages.containsKey(fingerPrintOfProtocal))
			logger.debug("【IMCORE"+this.debugTag+"】【QoS接收方】指纹为"+fingerPrintOfProtocal
					+"的消息已经存在于接收列表中，该消息重复了（原理可能是对方因未收到应答包而错误重传导致），更新收到时间戳哦.");
		
		putImpl(fingerPrintOfProtocal);
	}
	
	private void putImpl(String fingerPrintOfProtocal)
	{
		if(fingerPrintOfProtocal != null)
			recievedMessages.put(fingerPrintOfProtocal, System.currentTimeMillis());
	}
	
	public boolean hasRecieved(String fingerPrintOfProtocal)
	{
		return recievedMessages.containsKey(fingerPrintOfProtocal);
	}

	public int size()
	{
		return recievedMessages.size();
	}
	
	public QoS4ReciveDaemonRoot setDebugable(boolean debugable)
	{
		this.DEBUG = debugable;
		return this;
	}
	
	public boolean isDebugable()
	{
		return this.DEBUG;
	}
}

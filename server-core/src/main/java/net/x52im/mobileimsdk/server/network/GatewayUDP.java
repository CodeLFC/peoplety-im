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
 * GatewayUDP.java at 2022-7-12 16:35:57, code by Jack Jiang.
 */
package net.x52im.mobileimsdk.server.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.timeout.ReadTimeoutHandler;
import net.x52im.mobileimsdk.server.ServerCoreHandler;
import net.x52im.mobileimsdk.server.network.udp.MBUDPClientInboundHandler;
import net.x52im.mobileimsdk.server.network.udp.MBUDPServerChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatewayUDP extends Gateway
{
	private static Logger logger = LoggerFactory.getLogger(GatewayUDP.class); 
	
    public static int PORT = 7901;
    public static int SESION_RECYCLER_EXPIRE = 10;

    protected final EventLoopGroup __bossGroup4Netty = new NioEventLoopGroup();
 	protected final EventLoopGroup __workerGroup4Netty = new DefaultEventLoopGroup();
 	protected Channel __serverChannel4Netty = null;
 	protected ServerBootstrap bootstrap = null;

	@Override
 	public void init(ServerCoreHandler serverCoreHandler)
    {
    	bootstrap = new ServerBootstrap()
    		.group(__bossGroup4Netty, __workerGroup4Netty)
    		.channel(MBUDPServerChannel.class)
    		.childHandler(initChildChannelHandler(serverCoreHandler));
    }
    
 	@Override
    public void bind() throws Exception
    {
		ChannelFuture cf = bootstrap.bind("0.0.0.0", PORT).syncUninterruptibly();
		if (cf.isSuccess()) {
        	logger.info("[IMCORE-udp] 基于MobileIMSDK的UDP服务绑定端口"+PORT+"成功 √");
        }
        else{
        	logger.info("[IMCORE-udp] 基于MobileIMSDK的UDP服务绑定端口"+PORT+"失败 ×");
        }
		__serverChannel4Netty = cf.channel();
		__serverChannel4Netty.closeFuture().addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				__bossGroup4Netty.shutdownGracefully();
				__workerGroup4Netty.shutdownGracefully();
			}
		});
		
		logger.info("[IMCORE-udp] .... continue ...");
		logger.info("[IMCORE-udp] 基于MobileIMSDK的UDP服务正在端口" + PORT+"上监听中...");
    }
	
	@Override
	public void shutdown()
	{
    	if (__serverChannel4Netty != null) 
    		__serverChannel4Netty.close();
	}
	
	protected ChannelHandler initChildChannelHandler(final ServerCoreHandler serverCoreHandler)
	{
		return new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel channel) throws Exception {
				channel.pipeline()
					.addLast(new ReadTimeoutHandler(SESION_RECYCLER_EXPIRE))
					.addLast(new MBUDPClientInboundHandler(serverCoreHandler));
			}
		};
	}
}

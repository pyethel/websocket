package com.lehteypzzz.demo.Netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

/**
 * 程序的入口，负责启动应用
 *
 */
@Component
public class NettyStarter {
	private static class SingletionNettyStarter{
		static  final NettyStarter instance = new NettyStarter();
	}
	public static NettyStarter getInstance(){
		return SingletionNettyStarter.instance;
	}

	private ServerBootstrap server;

	public NettyStarter(){
		EventLoopGroup mainGroup = new NioEventLoopGroup();
		EventLoopGroup subGroup = new NioEventLoopGroup();
		// 启动类
		server = new ServerBootstrap();
		//	设置主从线程组
		server.group(mainGroup, subGroup)
				// 设置nio的双向通道
				.channel(NioServerSocketChannel.class)
				// 子处理器
				.childHandler(new WebSocketChannelHandler());
	}
	public void start() {
		// 绑定端口
		ChannelFuture future = server.bind(8889);
		System.err.println("netty websocket 启动完毕...");
	}

}

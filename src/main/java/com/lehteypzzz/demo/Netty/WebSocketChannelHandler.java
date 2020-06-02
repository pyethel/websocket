package com.lehteypzzz.demo.Netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * channel初始化器
 * 初始化连接时候的各个组件
 */
public class WebSocketChannelHandler extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel e){
//		服务端，对请求解码
		e.pipeline().addLast("http-codec", new HttpServerCodec());
//		聚合器，把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse
		e.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
//		块写入处理器
		e.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
//		WebSocket协议
		e.pipeline().addLast("websocket", new WebSocketServerProtocolHandler("/ws"));
//		自定义服务端处理器
		e.pipeline().addLast("handler", new WebSocketHandler());
	}
}

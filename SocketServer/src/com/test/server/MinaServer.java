package com.test.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaServer {

	public static void main(String[] args) {
		try {
		//定义接受socket对象的 接收器
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		//定义消息处理handler
		acceptor.setHandler(new MinaHander());
		//指定解码器
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory()));
		//发送心跳包
		acceptor.getSessionConfig().setIdleTime(IdleStatus.READER_IDLE, 5);
//		acceptor.getSessionConfig().setReaderIdleTime(30);
		acceptor.bind(new InetSocketAddress(9999));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

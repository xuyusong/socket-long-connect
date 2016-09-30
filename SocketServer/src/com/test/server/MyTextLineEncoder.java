package com.test.server;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class MyTextLineEncoder implements ProtocolEncoder {

	@Override
	public void dispose(IoSession arg0) throws Exception {

	}

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out)
			throws Exception {
		String s =null;
		if (message instanceof String) {
			s = (String) message;
		}
		if (s != null) {
			CharsetEncoder charsetEndoer = (CharsetEncoder)session.getAttribute("encoder");
			if (charsetEndoer == null) {
				charsetEndoer = Charset.defaultCharset().newEncoder();
				session.setAttribute("encoder", charsetEndoer);
			}
			IoBuffer ioBuffer = IoBuffer.allocate(s.length());
			ioBuffer.setAutoExpand(true);
			ioBuffer.putString(s, charsetEndoer);
			ioBuffer.flip();
			out.write(ioBuffer);
		}
	}

}

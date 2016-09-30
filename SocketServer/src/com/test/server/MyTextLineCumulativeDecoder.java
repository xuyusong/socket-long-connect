package com.test.server;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class MyTextLineCumulativeDecoder extends CumulativeProtocolDecoder {

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		int startPosition = in.position();
		while (in.hasRemaining()) {
			byte b = in.get();
			if (b == '\n') {
				int currentPositoin = in.position();
				int limit = in.limit();
				in.position(startPosition);
				in.limit(currentPositoin);
				IoBuffer buf = in.slice();
				byte [] dest = new byte[buf.limit()];
				buf.get(dest);
				String str = new String(dest);
				out.write(str);
				in.position(currentPositoin);
				in.limit(limit);
				return true;
			}
		}
		in.position(startPosition);
		return false;
	}

}

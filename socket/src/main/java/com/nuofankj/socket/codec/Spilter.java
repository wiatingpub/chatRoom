package com.nuofankj.socket.codec;

import com.nuofankj.socket.constant.SocketConstant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author xifanxiaxue
 * @date 5/18/19
 * @desc
 */
public class Spilter extends LengthFieldBasedFrameDecoder {
    private static final int LENGTH_FIELD_OFFSET = 7;
    private static final int LENGTH_FIELD_LENGTH = 4;

    public Spilter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {

        // 接受到的数据包和我们定义的类型不匹配，则不做任何处理
        // 此处in.readerIndex()作用是取出ByteBuff的read指针，in.getInt()作用是取出一个int的数
        try {

            if (in.getInt(in.readerIndex()) != SocketConstant.MAGIC_NUMBER) {
                ctx.channel().close();
                return null;
            }

            return super.decode(ctx, in);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}

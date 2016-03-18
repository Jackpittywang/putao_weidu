package com.putao.wd.redpoint;

import com.putao.wd.GlobalApplication;
import com.sunnybear.library.util.Logger;

import org.msgpack.MessagePack;
import org.msgpack.packer.Packer;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;


/**
 * Created by zhanghao on 2016/3/18.
 */
public class NettyClientBootstrap {
    private static final String PT_MT_HOST = GlobalApplication.isDebug ? "122.226.100.152" : "10.1.11.31";
    private static final int PT_MT_PORT = GlobalApplication.isDebug ? 8040 : 8083;
    private int port;
    private String host;
    private SocketChannel socketChanne;
    private static final EventExecutorGroup group = new DefaultEventExecutorGroup(20);

    public NettyClientBootstrap() throws InterruptedException {
        this.host = PT_MT_HOST;
        this.port = PT_MT_PORT;
        start();
    }

    private void start() throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.group(eventLoopGroup);
        bootstrap.remoteAddress(host, port);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new IdleStateHandler(20, 10, 0));
                socketChannel.pipeline().addLast(new ObjectEncoder());
                socketChannel.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                socketChannel.pipeline().addLast(new NettyClientHandler());
            }
        });
        ChannelFuture future = bootstrap.connect(host, port).sync();
        if (future.isSuccess()) {
            socketChanne = (SocketChannel) future.channel();
            System.out.println("connect server  成功---------");
            MessagePack msgPack = new MessagePack();
            byte[] outbytes = null;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Packer packer = msgPack.createPacker(out);
            Map postData = new HashMap();
            postData.put("SId", "1");
            try {
//                packer.write(postData);
                packer.write("struct  499478a81030bb177e578f86410cda8641a22799 {ubyte 1;}");
                outbytes = out.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            socketChanne.writeAndFlush(outbytes);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String msg = null;
                try {
                    msg = console.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (null != msg)
                    Logger.d("-------------+++++++++++", msg);
                if (msg == null) {
                    break;
                } else if ("bye".equals(msg.toLowerCase())) {
                    break;
                } else if ("ping".equals(msg.toLowerCase())) {
                } else {
                    AskMsg askMsg = new AskMsg();
                    AskParams askParams = new AskParams();
                    askParams.setAuth("authToken");
                    askParams.setContent(msg);
                    askMsg.setParams(askParams);
                    socketChanne.writeAndFlush(askMsg);
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {

        NettyClientBootstrap bootstrap = new NettyClientBootstrap();
        MessagePack msgPack = new MessagePack();
        byte[] outbytes = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Packer packer = msgPack.createPacker(out);
        Map postData = new HashMap();
        postData.put("SId", "1");
        try {
            packer.write(postData);
            outbytes = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Constants.setClientId("002");

       /* LoginMsg loginMsg = new LoginMsg();
        loginMsg.setPassword("yao");
        loginMsg.setUserName("robin");*/
        bootstrap.socketChanne.writeAndFlush(outbytes);
//        while (true){
//            TimeUnit.SECONDS.sleep(3);
//            AskMsg askMsg=new AskMsg();
//            AskParams askParams=new AskParams();
//            askParams.setAuth("authToken");
//            askMsg.setParams(askParams);
//            bootstrap.socketChannel.writeAndFlush(askMsg);
//        }

        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String msg = console.readLine();
            Logger.d("-------------+++++++++++", msg);
            if (msg == null) {
                break;
            } else if ("bye".equals(msg.toLowerCase())) {
                break;
            } else if ("ping".equals(msg.toLowerCase())) {
            } else {
                AskMsg askMsg = new AskMsg();
                AskParams askParams = new AskParams();
                askParams.setAuth("authToken");
                askParams.setContent(msg);
                askMsg.setParams(askParams);
                bootstrap.socketChanne.writeAndFlush(askMsg);
            }
        }
    }
}

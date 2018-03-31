package com.jy.im.server.tcp.javanio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer  implements Runnable{

    public static void main(String[] args) {
        int port = 8080;
        //MultiplexerTimeServer的多路复用类，它是个一个独立的线程，
        //负责轮询多路复用器Selector，可以处理多个客户端的并发接入。
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread (timeServer, "NIO-MultiplexerTimeServer-001").start();
        new Thread (new MultiplexerTimeServer(8081), "NIO-MultiplexerTimeServer-001").start();
        new Thread (new MultiplexerTimeServer(8082), "NIO-MultiplexerTimeServer-001").start();
    }

    private Selector selector;

    private ServerSocketChannel serverChannel;

    private volatile boolean stop;

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                //在线程的run方法的while循环体中循环遍历selector，它的休眠时间为1s，
                //无论是否有读写等事件发生，selector每隔1s都被唤醒一次，selector也提供了一个无参的select方法。
                //当有处于就绪状态的Channel时，selector将返回就绪状态的Channel的SelectionKey集合，
                //通过对就绪状态的Channel集合进行迭代，可以进行网络的异步读写操作。
                selector.select(1000);
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator it = selectedKeys.iterator();
                while (it.hasNext()) {
                    SelectionKey key = (SelectionKey) it.next();
                    it.remove();
                    try {
                        handleInput(key);//这里可以用线程池启线程去单独处理客户端的请求业务
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null)
                                key.channel().close();
                        }
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        // 多路复用器关闭后，所有注册在上面的Channel和Pipe等资源都会被自动去注册并关闭，所以不需要重复释放资源
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {

        if (key.isValid()) {
            //根据SelectionKey的操作位进行判断即可获知网络事件的类型，
            if (key.isAcceptable()) {
                //通过ServerSocketChannel的accept接收客户端的连接请求并创建SocketChannel实例，
                //完成上述操作后，相当于完成了TCP的三次握手，TCP物理链路正式建立。
                //注意，我们需要将新创建的SocketChannel设置为异步非阻塞，同时也可以对其TCP参数进行设置，
                //例如TCP接收和发送缓冲区的大小等，作为入门的例子，没有进行额外的参数设置。
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                // Add the new connection to the selector
                sc.register(selector, SelectionKey.OP_READ);
            }
            if (key.isReadable()) {
                //首先创建一个ByteBuffer，由于我们事先无法得知客户端发送的码流大小，
                //作为例程，我们开辟一个1M的缓冲区。然后调用SocketChannel的read方法读取请求码流。
                //注意，由于我们已经将SocketChannel设置为异步非阻塞模式，因此它的read是非阻塞的。
                //使用返回值进行判断，看读取到的字节数
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                //返回值有以下三种可能的结果
                //返回值大于0：读到了字节，对字节进行编解码；
                //返回值等于0：没有读取到字节，属于正常场景，忽略；
                //返回值为-1：链路已经关闭，需要关闭SocketChannel，释放资源。
                if (readBytes > 0) {
                    //当读取到码流以后，我们进行解码，首先对readBuffer进行flip操作，
                    //它的作用是将缓冲区当前的limit设置为position，position设置为0，用于后续对缓冲区的读取操作。
                    //然后根据缓冲区可读的字节个数创建字节数组，
                    //调用ByteBuffer的get操作将缓冲区可读的字节数组复制到新创建的字节数组中，
                    //最后调用字符串的构造函数创建请求消息体并打印。
                    //如果请求指令是"QUERY TIME ORDER"则把服务器的当前时间编码后返回给客户端
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("The time server receive order : "
                        + body);
                    String currentTime = "QUERY TIME ORDER\r\n"
                        .equalsIgnoreCase(body) ? new java.util.Date(
                        System.currentTimeMillis()).toString()
                        : "BAD ORDER";
                    //异步发送应答消息给客户端
                    doWrite(sc, currentTime);
                } else if (readBytes < 0) {
                    // 对端链路关闭
                    key.cancel();
                    sc.close();
                } else {
                    // 读到0字节，忽略
                }

            }
        }
    }

    private void doWrite(SocketChannel channel, String response)
        throws IOException {
        //首先将字符串编码成字节数组，根据字节数组的容量创建ByteBuffer，
        //调用ByteBuffer的put操作将字节数组复制到缓冲区中，然后对缓冲区进行flip操作，
        //最后调用SocketChannel的write方法将缓冲区中的字节数组发送出去。
        //需要指出的是，由于SocketChannel是异步非阻塞的，它并不保证一次能够把需要发送的字节数组发送完，
        //此时会出现“写半包”问题，我们需要注册写操作，不断轮询Selector将没有发送完的ByteBuffer发送完毕，
        //可以通过ByteBuffer的hasRemain()方法判断消息是否发送完成。
        //此处仅仅是个简单的入门级例程，没有演示如何处理“写半包”场景。
        if (response != null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer);
        }
    }

    //在构造方法中进行资源初始化，创建多路复用器Selector、ServerSocketChannel，对Channel和TCP参数进行配置。
    //例如，将ServerSocketChannel设置为异步非阻塞模式，它的backlog设置为1024。
    //系统资源初始化成功后，将ServerSocket Channel注册到Selector，监听SelectionKey.OP_ACCEPT操作位；如果资源初始化失败（例如端口被占用），则退出。
    private MultiplexerTimeServer(int port) {
        try {
            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(port), 1024);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The time server is start in port : " + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

//    步骤一：打开ServerSocketChannel，用于监听客户端的连接，它是所有客户端连接的父管道，代码示例如下。
//    ServerSocketChannel acceptorSvr = ServerSocketChannel.open();
//    步骤二：绑定监听端口，设置连接为非阻塞模式，示例代码如下。
//    acceptorSvr.socket().bind(new InetSocketAddress(InetAddress.getByName(“IP”), port));
//    acceptorSvr.configureBlocking(false);
//    步骤三：创建Reactor线程，创建多路复用器并启动线程，代码如下。
//    Selector selector = Selector.open();
//    new Thread(new ReactorTask()).start();
//    步骤四：将ServerSocketChannel注册到Reactor线程的多路复用器Selector上，监听ACCEPT事件，代码如下。
//    SelectionKey key = acceptorSvr.register( selector, SelectionKey.OP_ACCEPT, ioHandler);
//    步骤五：多路复用器在线程run方法的无限循环体内轮询准备就绪的Key，代码如下。
//    int num = selector.select();
//    Set selectedKeys = selector.selectedKeys();
//    Iterator it = selectedKeys.iterator();
//    while (it.hasNext()) {
//        SelectionKey key = (SelectionKey)it.next();
//        ... deal with I/O event ...
//    }
//    步骤六：多路复用器监听到有新的客户端接入，处理新的接入请求，完成TCP三次握手，建立物理链路，代码示例如下。
//    SocketChannel channel = svrChannel.accept();
//    步骤七：设置客户端链路为非阻塞模式，示例代码如下。
//        channel.configureBlocking(false);
//    channel.socket().setReuseAddress(true);
//    ......
//    步骤八：将新接入的客户端连接注册到Reactor线程的多路复用器上，监听读操作，用来读取客户端发送的网络消息，代码如下。
//    SelectionKey key = socketChannel.register( selector, SelectionKey.OP_READ, ioHandler);
//    步骤九：异步读取客户端请求消息到缓冲区，示例代码如下。
//    int readNumber = channel.read(receivedBuffer);
//    步骤十：对ByteBuffer进行编解码，如果有半包消息指针reset，继续读取后续的报文，将解码成功的消息封装成Task，投递到业务线程池中，进行业务逻辑编排。
//    Object message = null;
//    while(buffer.hasRemain()) {
//    　　byteBuffer.mark();
//    　　Object message = decode(byteBuffer);
//    　　if (message == null) {
//    　　　　byteBuffer.reset();
//    　　　　break;
//    　　}
//    　　messageList.add(message );
//    }
//    if (!byteBuffer.hasRemain()) {
//        byteBuffer.clear();
//    } else {
//        byteBuffer.compact();
//    }
//    if (messageList != null & !messageList.isEmpty()) {
//    　　for(Object messageE : messageList) {
//            handlerTask(messageE);
//        }
//    }
//    步骤十一：将POJO对象encode成ByteBuffer，调用SocketChannel的异步write接口，将消息异步发送给客户端，示例代码如下。
//    socketChannel.write(buffer);
//    注意：如果发送区TCP缓冲区满，会导致写半包，此时，需要注册监听写操作位，循环写，直到整包消息写入TCP缓冲区。
}

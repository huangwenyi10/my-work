## BIO,NIO,AIO深入学习 ——阿毅

#### **前言**



java的核心库java.io提供了全面的IO接口。包括：文件读写、标准设备输出等。Java中IO是以流为基础进行输入输出的，所有数据被串行化写入输出流，或者从输入流读入。

java.nio（java non-blocking IO），nio 是non-blocking的简称，是jdk1.4 及以上版本里提供的新api（New IO） ，为所有的原始类型（boolean类型除外）提供[缓存](http://baike.baidu.com/item/%E7%BC%93%E5%AD%98)支持。Sun 官方标榜的特性如下： 为所有的原始类型提供(Buffer)[缓存](http://baike.baidu.com/item/%E7%BC%93%E5%AD%98)支持。[字符集](http://baike.baidu.com/item/%E5%AD%97%E7%AC%A6%E9%9B%86)编码解码解决方案。 Channel ：一个新的原始I/O 抽象。 支持锁和[内存映射文件](http://baike.baidu.com/item/%E5%86%85%E5%AD%98%E6%98%A0%E5%B0%84%E6%96%87%E4%BB%B6)的文件访问接口。 提供多路(non-bloking) 非阻塞式的高伸缩性网络I/O 。



###### **Typora软件介绍**

强大的Markdown编辑器。



###### **课程介绍**

​    《Java之IO , BIO , NIO , AIO 知多少？》主要是针对有一定基础的Java学员。本课程主要围绕着IO，BIO，NIO，AIO等基础知识展开的，同时会讲解很多具体代码实例。在本课程中，学员可以快速掌握这些基础的概念和自己动手写出性能高的IO流代码。以及把这些知识运用到自己真实的项目中去。



###### 课程目标

​    本课程一方面可以让学员掌握IO流的高级基础知识，另一方面可以把这些知识运用到自己的真实项目中，提高IO流操作的效率和性能。在最短时间内帮助学员掌握知识。



###### **课程计划**

​    每周都会陆续更新



###### **课程目录**

- 1.课程介绍
- 2.IO基础知识回顾
- 3.BIO编程
- 4.伪异步I/O编程
- 5.NIO 编程简单介绍
- 6.通道 Channel
- 7.缓冲区 Buffer
- 8.选择器 Selector
- 9.分散（Scatter）/聚集（Gather）
- 10.其他通道
- 11.管道（Pipe）
- 12.AIO编程




###### IO，NIO，JDK介绍**



查看JDK



###### **结束语**

我的邮箱：huangwenyi10@163.com



#### **IO基础知识回顾**



###### IO流类图结构



![QQ截图20170628170248](C:\Users\Ay\Desktop\QQ截图20170628170248.png)



###### IO流简单例子



实例一：		

    FileInputStream fis=null;
    FileOutputStream fos=null;
    try {
        fis = new FileInputStream(new File("D:\\a.txt"));
        fos = new FileOutputStream(new File("D:\\y.txt"));
        int ch;
        while((ch=fis.read()) != -1){
            System.out.println((char)ch);
            fos.write(ch);
        }
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }finally {
        if(null != fos){
            fos.close();
        }
        if(null != fis){
            fis.close();
        }
    }



![微信截图_20170629173148](C:\Users\Ay\Desktop\微信截图_20170629173148.png)

实例二：字节流转换成字符流

```
public static void main(String[] args) throws Exception{
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\a.txt")));
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\y.txt")));
            String s;
            StringBuilder sb = new StringBuilder();
            while((s=br.readLine())!=null){
                System.out.println(s);
                bw.write(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != bw){
                bw.close();
            }
            if(null != br){
                br.close();
            }
        }
    }
```



实例三：用转换流从控制台上读入数据

```
public static void main(String[] args) throws Exception{
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            String s=br.readLine();
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != br){
                br.close();
            }
        }
    }
```







#### BIO编程

##### **传统BIO通信模型图**

​    传统的同步阻塞模型开发中，ServerSocket负责绑定IP地址，启动监听端口；Socket负责发起连接操作。连接成功后，双方通过输入和输出流进行**同步阻塞式**通信。 服务端提供IP和监听端口，客户端通过连接操作想服务端监听的地址发起连接请求，通过三次握手连接，如果连接成功建立，双方就可以通过套接字进行通信。

​    简单的描述一下BIO的服务端通信模型：采用BIO通信模型的服务端，通常由一个独立的Acceptor线程负责监听客户端的连接，它接收到客户端连接请求之后为每个客户端创建一个新的线程进行链路处理没处理完成后，通过输出流返回应答给客户端，线程销毁。即典型的一请求一应答通宵模型。

​    传统BIO通信模型图：

​    ![01](http://blog.anxpp.com/usr/uploads/2016/05/549520916.png)

​    该模型最大的问题就是缺乏弹性伸缩能力，当客户端并发访问量增加后，**服务端的线程个数和客户端并发访问数呈1:1的正比关系**，[Java](http://lib.csdn.net/base/java)中的线程也是比较宝贵的系统资源，线程数量快速膨胀后，系统的性能将急剧下降，随着访问量的继续增大，系统最终就**死-掉-了**。



##### **传统BIO编程实例**

传统的同步阻塞模型开发中，ServerSocket负责绑定IP地址，启动监听端口；Socket负责发起连接操作。连接成功后，双方通过输入和输出流进行同步阻塞式通信。

```
package com.evada.de;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * 描述：传统BIO编程实例
 * @author Ay
 * @date 2017/6/27
 */
public final class AyTest extends BaseTest {

    public static void main(String[] args) throws InterruptedException {
        //启动线程，运行服务器
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerBetter.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //避免客户端先于服务器启动前执行代码
        Thread.sleep(100);

        //启动线程，运行客户端
        char operators[] = {'+', '-', '*', '/'};
        Random random = new Random(System.currentTimeMillis());
        new Thread(new Runnable() {
            @SuppressWarnings("static-access")
            @Override
            public void run() {
                while (true) {
                    //随机产生算术表达式
                    String expression = random.nextInt(10) + "" + operators[random.nextInt(4)] + (random.nextInt(10) + 1);
                    Client.send(expression);
                    try {
                        Thread.currentThread().sleep(random.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}

class ServerBetter{

    //默认的端口号
    private static int DEFAULT_PORT = 12345;
    //单例的ServerSocket
    private static ServerSocket server;

    //根据传入参数设置监听端口，如果没有参数调用以下方法并使用默认值
    public static void start() throws IOException {
        //使用默认值端口
        start(DEFAULT_PORT);
    }
    //这个方法不会被大量并发访问，不太需要考虑效率，直接进行方法同步就行了
    public synchronized static void start(int port) throws IOException{
        if(server != null) return;
        try{
            //通过构造函数创建ServerSocket，如果端口合法且空闲，服务端就监听成功
            server = new ServerSocket(port);
            System.out.println("服务器已启动，端口号：" + port);
            //通过无线循环监听客户端连接，如果没有客户端接入，将阻塞在accept操作上。
            while(true){
                Socket socket = server.accept();
                //当有新的客户端接入时，会执行下面的代码
                //然后创建一个新的线程处理这条Socket链路
                new Thread(new ServerHandler(socket)).start();
            }
        }finally{
            //一些必要的清理工作
            if(server != null){
                System.out.println("服务器已关闭。");
                server.close();
                server = null;
            }
        }
    }

}


class ServerHandler implements Runnable{
    private Socket socket;
    public ServerHandler(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
            String expression;
            String result;
            while(true){
                //通过BufferedReader读取一行
                //如果已经读到输入流尾部，返回null,退出循环
                //如果得到非空值，就尝试计算结果并返回
                if((expression = in.readLine())==null) break;
                System.out.println("服务器收到消息：" + expression);
                try{
                    result = "123";//Calculator.cal(expression).toString();
                }catch(Exception e){
                    result = "计算错误：" + e.getMessage();
                }
                out.println(result);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            //一些必要的清理工作
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
            if(out != null){
                out.close();
                out = null;
            }
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }
}

class Client {
    //默认的端口号
    private static int DEFAULT_SERVER_PORT = 12345;
    //默认服务器Ip
    private static String DEFAULT_SERVER_IP = "127.0.0.1";

    public static void send(String expression){
        send(DEFAULT_SERVER_PORT,expression);
    }
    public static void send(int port,String expression){
        System.out.println("算术表达式为：" + expression);
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try{
            socket = new Socket(DEFAULT_SERVER_IP,port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
            out.println(expression);
            System.out.println("___结果为：" + in.readLine());
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            //一下必要的清理工作
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
            if(out != null){
                out.close();
                out = null;
            }
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }
}
```





#### **伪异步I/O编程**

我们可以使用线程池来管理这些线程（需要了解更多请参考前面提供的文章），实现1个或多个线程处理N个客户端的模型（**但是底层还是使用的同步阻塞I/O**），通常被称为“伪异步I/O模型“



##### 伪异步I/O编程模型图

![66666](C:\Users\Ay\Desktop\66666.png)



  测试运行结果是一样的。

​    我们知道，如果使用CachedThreadPool线程池（不限制线程数量，如果不清楚请参考文首提供的文章），其实除了能自动帮我们管理线程（复用），看起来也就像是1:1的客户端：线程数模型，而使用**FixedThreadPool**我们就有效的控制了线程的最大数量，保证了系统有限的资源的控制，实现了N:M的伪异步I/O模型。

​    但是，正因为限制了线程数量，如果发生大量并发请求，超过最大数量的线程就只能等待，直到线程池中的有空闲的线程可以被复用。而对Socket的输入流就行读取时，会一直阻塞，直到发生：

- ​    有数据可读
- ​    可用数据以及读取完毕
- ​    发生空指针或I/O异常

​    所以在读取数据较慢时（比如数据量大、网络传输慢等），大量并发的情况下，其他接入的消息，只能一直等待，这就是最大的弊端。

​    而后面即将介绍的NIO，就能解决这个难题。



##### **伪异步IO编程代码**

```
package com.anxpp.io.calculator.bio;  
import java.io.IOException;  
import java.net.ServerSocket;  
import java.net.Socket;  
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
/** 
 * BIO服务端源码__伪异步I/O 
 * @author yangtao__anxpp.com 
 * @version 1.0 
 */  
public final class ServerBetter {  
    //默认的端口号  
    private static int DEFAULT_PORT = 12345;  
    //单例的ServerSocket  
    private static ServerSocket server;  
    //线程池 懒汉式的单例  
    private static ExecutorService executorService = Executors.newFixedThreadPool(60);  
    //根据传入参数设置监听端口，如果没有参数调用以下方法并使用默认值  
    public static void start() throws IOException{  
        //使用默认值  
        start(DEFAULT_PORT);  
    }  
    //这个方法不会被大量并发访问，不太需要考虑效率，直接进行方法同步就行了  
    public synchronized static void start(int port) throws IOException{  
        if(server != null) return;  
        try{  
            //通过构造函数创建ServerSocket  
            //如果端口合法且空闲，服务端就监听成功  
            server = new ServerSocket(port);  
            System.out.println("服务器已启动，端口号：" + port);  
            //通过无线循环监听客户端连接  
            //如果没有客户端接入，将阻塞在accept操作上。  
            while(true){  
                Socket socket = server.accept();  
                //当有新的客户端接入时，会执行下面的代码  
                //然后创建一个新的线程处理这条Socket链路  
                executorService.execute(new ServerHandler(socket));  
            }  
        }finally{  
            //一些必要的清理工作  
            if(server != null){  
                System.out.println("服务器已关闭。");  
                server.close();  
                server = null;  
            }  
        }  
    }  
}  
```



- 1,同步和异步是针对应用程序和内核的交互而言的。 
- 2,阻塞和非阻塞是针对于进程在访问数据的时候，根据IO操作的就绪状态来采取的不同方式，说白了是一种读取或者写入操作函数的实现方式，阻塞方式下读取或者写入函数将一直等待，而非阻塞方式下，读取或者写入函数会立即返回一个状态值。  

由上描述基本可以总结一句简短的话，**同步和异步是目的，阻塞和非阻塞是实现方式**。



**同步阻塞：** 
在此种方式下，用户进程在发起一个IO操作以后，必须等待IO操作的完成，只有当真正完成了IO操作以后，用户进程才能运行。JAVA传统的IO模型属于此种方式。 

**同步非阻塞：** 
在此种方式下，用户进程发起一个IO操作以后边可返回做其它事情，但是用户进程需要时不时的询问IO操作是否就绪，这就要求用户进程不停的去询问，从而引入不必要的CPU资源浪费。其中目前JAVA的NIO就属于同步非阻塞IO。 
**异步：** 
此种方式下是指应用发起一个IO操作以后，不等待内核IO操作的完成，等内核完成IO操作以后会通知应用程序。



如果你想吃一份宫保鸡丁盖饭： 

同步阻塞：你到饭馆点餐，然后在那等着，还要一边喊：好了没啊！ 

同步非阻塞：在饭馆点完餐，就去遛狗了。不过溜一会儿，就回饭馆喊一声：好了没啊！ 

异步阻塞：遛狗的时候，接到饭馆电话，说饭做好了，让您亲自去拿。 

异步非阻塞：饭馆打电话说，我们知道您的位置，一会给你送过来，安心遛狗就可以了。





#### NIO 编程



##### **简介**

Java NIO(New IO)是一个可以替代标准Java IO API（从Java 1.4开始)，Java NIO提供了与标准IO不同的IO工作方式。

Java NIO 由以下几个核心部分组成： 

- Channels
- Buffers
- Selectors



虽然Java NIO 中除此之外还有很多类和组件，但Channel，Buffer 和 Selector 构成了核心的API。其它组件，如Pipe和FileLock，只不过是与三个核心组件共同使用的工具类。因此，我将集中精力在这三个组件上。其它组件会在单独的章节中讲到。 



注意（每个线程的处理流程大概都是读取数据、解码、计算处理、编码、发送响应）



##### **非常形象的实例**



小量的线程如何同时为大量连接服务呢，答案就是就绪选择。这就好比到餐厅吃饭，每来一桌客人，都有一个服务员专门为你服务，从你到餐厅到结帐走人，这样方式的好处是服务质量好，一对一的服务，VIP啊，可是缺点也很明显，成本高，如果餐厅生意好，同时来100桌客人，就需要100个服务员，那老板发工资的时候得心痛死了，这就是传统的一个连接一个线程的方式。

 

老板是什么人啊，精着呢。这老板就得捉摸怎么能用10个服务员同时为100桌客人服务呢，老板就发现，服务员在为客人服务的过程中并不是一直都忙着，客人点完菜，上完菜，吃着的这段时间，服务员就闲下来了，可是这个服务员还是被这桌客人占用着，不能为别的客人服务，用华为领导的话说，就是工作不饱满。那怎么把这段闲着的时间利用起来呢。这餐厅老板就想了一个办法，让一个服务员（前台）专门负责收集客人的需求，登记下来，比如有客人进来了、客人点菜了，客人要结帐了，都先记录下来按顺序排好。每个服务员到这里领一个需求，比如点菜，就拿着菜单帮客人点菜去了。点好菜以后，服务员马上回来，领取下一个需求，继续为别人客人服务去了。这种方式服务质量就不如一对一的服务了，当客人数据很多的时候可能需要等待。但好处也很明显，由于在客人正吃饭着的时候服务员不用闲着了，服务员这个时间内可以为其他客人服务了，原来10个服务员最多同时为10桌客人服务，现在可能为50桌，10客人服务了。

 

这种服务方式跟传统的区别有两个：

1、增加了一个角色，要有一个专门负责收集客人需求的人。NIO里对应的就是Selector。

2、由阻塞服务方式改为非阻塞服务了，客人吃着的时候服务员不用一直侯在客人旁边了。传统的IO操作，比如read()，当没有数据可读的时候，线程一直阻塞被占用，直到数据到来。NIO中没有数据可读时，read()会立即返回0，线程不会阻塞。

 

NIO中，客户端创建一个连接后，先要将连接注册到Selector，相当于客人进入餐厅后，告诉前台你要用餐，前台会告诉你你的桌号是几号，然后你就可能到那张桌子坐下了，SelectionKey就是桌号。当某一桌需要服务时，前台就记录哪一桌需要什么服务，比如1号桌要点菜，2号桌要结帐，服务员从前台取一条记录，根据记录提供服务，完了再来取下一条。这样服务的时间就被最有效的利用起来了。



##### **工作原理**



![nio原理](C:\Users\Ay\Desktop\nio原理.png)





##### Java NIO和IO的主要区别

| IO              | NIO             |
| --------------- | --------------- |
| Stream oriented | Buffer oriented |
| Blocking IO     | Non blocking IO |
|                 | Selectors       |

###### **面向流与面向缓冲**



Java NIO和IO之间第一个最大的区别是，IO是面向流的，NIO是面向缓冲区的

Java IO面向流意味着每次从流中读一个或多个字节，直至读取所有字节，它们没有被缓存在任何地方。此外，它**不能前后移动流中的数据**。如果需要前后移动从流中读取的数据，需要先将它缓存到一个缓冲区。 Java NIO的缓冲导向方法略有不同。数据读取到一个它稍后处理的缓冲区，需要时可在缓冲区中前后移动。这就增加了处理过程中的**灵活性**。但是，还需要检查是否该缓冲区中包含所有您需要处理的数据。而且，需确保当更多的数据读入缓冲区时，不要覆盖缓冲区里尚未处理的数据。 



###### **阻塞与非阻塞IO**



Java IO的各种流是阻塞的。这意味着，当一个线程调用read() 或 write()时，该线程被阻塞，直到有一些数据被读取，或数据完全写入。该线程在此期间不能再干任何事情了。 Java NIO的非阻塞模式，使一个线程从某通道发送请求读取数据，但是它仅能得到目前可用的数据，如果目前没有数据可用时，就什么都不会获取。而不是保持线程阻塞，所以直至数据变的可以读取之前，该线程可以继续做其他的事情。 非阻塞写也是如此。一个线程请求写入一些数据到某通道，但不需要等待它完全写入，这个线程同时可以去做别的事情。 线程通常将非阻塞IO的空闲时间用于在其它通道上执行IO操作，所以一个单独的线程现在可以管理多个输入和输出通道（channel）。 



##### **NIO和IO如何影响应用程序的设计**

无论您选择IO或NIO工具箱，可能会影响您应用程序设计的以下几个方面： 

- 对NIO或IO类的API调用。
- 数据处理。
- 用来处理数据的线程数。





##### 通道 Channel

###### **简介**

Channel 是对数据的源头和数据目标点流经途径的抽象，在这个意义上和 InputStream 和 OutputStream 类似。Channel可以译为“通道、管 道”，而传输中的数据仿佛就像是在其中流淌的水。前面也提到了Buffer，Buffer和Channel相互配合使用，才是Java的NIO。



###### Java NIO的通道与流区别

- 既可以从通道中读取数据，又可以写数据到通道。但流的读写通常是单向的。

- 通道可以异步地读写。

- 通道中的数据总是要先读到一个Buffer，或者总是要从一个Buffer中写入。

  ​

我们对数据的读取和写入要通过Channel，它就像水管一样，是一个通道。通道不同于流的地方就是通道是双向的，可以用于读、写和同时读写操作。 数据可以从Channel读到Buffer中，也可以从Buffer 写到Channel中。



![![img](file:///C:/Users/Ay/Desktop/QQ%E6%88%AA%E5%9B%BE20170627173835.png?lastModify=1498614388)Channel和Buffer](C:\Users\Ay\Desktop\QQ截图20170627173835.png)

注意：通道必须结合Buffer使用，不能直接向通道中读/写数据



###### **Channel主要分类**



广义上来说通道可以被分为两类：File I/O和Stream I/O，也就是文件通道和套接字通道。如果分的更细致一点则是：

- FileChannel 从文件读写数据
- SocketChannel 通过TCP读写网络数据
- ServerSocketChannel 可以监听新进来的TCP连接，并对每个链接创建对应的SocketChannel
- DatagramChannel 通过UDP读写网络中的数据
- Pipe



###### **Channel的实现** 

这些是Java NIO中最重要的通道的实现： 

- FileChannel：从文件中读写数据。
- DatagramChannel：能通过UDP读写网络中的数据。
- SocketChannel：能通过TCP读写网络中的数据。
- ServerSocketChannel：可以监听新进来的TCP连接，像Web服务器那样。对每一个新进来的连接都会创建一个SocketChannel。




###### 打开FileChannel

在使用FileChannel之前，必须先打开它。但是，我们无法直接打开一个FileChannel，需要通过使用一个InputStream、OutputStream或RandomAccessFile来获取一个FileChannel实例。下面是通过RandomAccessFile打开FileChannel的示例：

```
RandomAccessFile aFile = new RandomAccessFile("d://nio-data.txt", "rw");
FileChannel inChannel = aFile.getChannel();
```

###### 从FileChannel读取数据

调用多个read()方法之一从FileChannel中读取数据。如：

```
ByteBuffer buf = ByteBuffer.allocate(48);
int bytesRead = inChannel.read(buf);
```

首先，分配一个Buffer。从FileChannel中读取的数据将被读到Buffer中。

然后，调用FileChannel.read()方法。该方法将数据从FileChannel读取到Buffer中。read()方法返回的int值表示了有多少字节被读到了Buffer中。如果返回-1，表示到了文件末尾。

###### 向FileChannel写数据

使用FileChannel.write()方法向FileChannel写数据，该方法的参数是一个Buffer。如：

```
String newData = "New String to write to file..." + System.currentTimeMillis();
ByteBuffer buf = ByteBuffer.allocate(48);
buf.clear();
buf.put(newData.getBytes());
buf.flip();
while(buf.hasRemaining()) {
	channel.write(buf);
}
```

注意FileChannel.write()是在while循环中调用的。因为无法保证write()方法一次能向FileChannel写入多少字节，因此需要重复调用write()方法，直到Buffer中已经没有尚未写入通道的字节。

###### 关闭FileChannel

用完FileChannel后必须将其关闭。如：

```
channel.close();
```

###### FileChannel的position方法

有时可能需要在FileChannel的某个特定位置进行数据的读/写操作。可以通过调用position()方法获取FileChannel的当前位置。

也可以通过调用position(long pos)方法设置FileChannel的当前位置。

这里有两个例子:

```
long pos = channel.position();
channel.position(pos +123);
```

如果将位置设置在文件结束符之后，然后试图从文件通道中读取数据，读方法将返回-1 —— 文件结束标志。

如果将位置设置在文件结束符之后，然后向通道中写数据，文件将撑大到当前位置并写入数据。这可能导致“文件空洞”，磁盘上物理文件中写入的数据间有空隙。

###### FileChannel的size方法

FileChannel实例的size()方法将返回该实例所关联文件的大小。如:

```
long fileSize = channel.size();
```

###### FileChannel的truncate方法

可以使用FileChannel.truncate()方法截取一个文件。截取文件时，文件将中指定长度后面的部分将被删除。如：

```
channel.truncate(1024);
```

这个例子截取文件的前1024个字节。

###### FileChannel的force方法

FileChannel.force()方法将通道里尚未写入磁盘的数据强制写到磁盘上。出于性能方面的考虑，操作系统会将数据缓存在内存中，所以无法保证写入到FileChannel里的数据一定会即时写到磁盘上。要保证这一点，需要调用force()方法。

force()方法有一个boolean类型的参数，指明是否同时将文件元数据（权限信息等）写到磁盘上。

下面的例子同时将文件数据和元数据强制写到磁盘上：

```
channel.force(true);
```



**transferFrom()**

FileChannel无法设置为非阻塞模式，它总是运行在阻塞模式下。



FileChannel的transferFrom()方法可以将数据从源通道传输到FileChannel中（译者注：这个方法在JDK文档中的解释为将字节从给定的可读取字节通道传输到此通道的文件中）。下面是一个简单的例子： 

```
//在使用FileChannel之前，必须先打开它。但是，我们无法直接打开一个FileChannel，
//需要通过使用一个InputStream、OutputStream或RandomAccessFile来获取一个FileChannel实例。
RandomAccessFile fromFile = new RandomAccessFile("fromFile.txt", "rw");  
FileChannel      fromChannel = fromFile.getChannel();  
RandomAccessFile toFile = new RandomAccessFile("toFile.txt", "rw");  
FileChannel      toChannel = toFile.getChannel();   
long position = 0;  
long count = fromChannel.size();  
toChannel.transferFrom(position, count, fromChannel);  
```

transferFrom 方法的输入参数 position 表示从 position 处开始向目标文件写入数据，count 表示最多传输的字节数。如果源通道的剩余空间小于 count 个字节，则所传输的字节数要小于请求的字节数。 

此外要注意，在 SoketChannel 的实现中，SocketChannel 只会传输此刻准备好的数据（可能不足count字节）。因此，SocketChannel 可能不会将请求的所有数据(count个字节)全部传输到 FileChannel 中。 



**transferTo()**

transferTo()方法将数据从FileChannel传输到其他的channel中。下面是一个简单的例子： 

```
RandomAccessFile fromFile = new RandomAccessFile("fromFile.txt", "rw");  
FileChannel      fromChannel = fromFile.getChannel();    
RandomAccessFile toFile = new RandomAccessFile("toFile.txt", "rw");  
FileChannel      toChannel = toFile.getChannel();  
long position = 0;  
long count = fromChannel.size();  
fromChannel.transferTo(position, count, toChannel);  
```

是不是发现这个例子和前面那个例子特别相似？除了调用方法的FileChannel对象不一样外，其他的都一样。 

上面所说的关于SocketChannel的问题在transferTo()方法中同样存在。SocketChannel会一直传输数据直到目标buffer被填满。 



###### **Channel简单实例**

下面是Channel的一个简单的实例：

```
程序清单 1-1
RandomAccessFile aFile = new RandomAccessFile("d:\\ay.txt", "rw");
FileChannel fileChannel = aFile.getChannel();
//分配缓存区大小
ByteBuffer buf = ByteBuffer.allocate(48);
int bytesRead = fileChannel.read(buf);
while (bytesRead != -1) {
    System.out.println("Read " + bytesRead);
    //buf.flip()的调用，首先读取数据到Buffer，然后反转Buffer,接着再从Buffer中读取数据（注：flip：空翻，反转）
    buf.flip();
    //判断是否有剩余（注：Remaining：剩余的）
    while(buf.hasRemaining()){
    	System.out.print((char) buf.get());
    }
    buf.clear();
    bytesRead = fileChannel.read(buf);
}
aFile.close();


```



##### 缓冲区 Buffer



缓冲区本质上是一块可以写入数据，然后可以从中读取数据的内存。这块内存被包装成NIO Buffer对象，并提供了一组方法，用来方便的访问该块内存。 



###### **Buffer的基本用法** 



使用Buffer读写数据一般遵循以下四个步骤： 

- 写入数据到Buffer
- 调用flip()方法
- 从Buffer中读取数据
- 调用clear()方法或者compact()方法



当向buffer写入数据时，buffer会记录下写了多少数据。一旦要读取数据，需要通过 flip() 方法将 Buffer 从**写模式切换到读模式**。在读模式下，可以读取之前写入到buffer的所有数据。 

一旦读完了所有的数据，就需要清空缓冲区，让它可以再次被写入。有两种方式能清空缓冲区：调用 **clear() 或 compact() 方法**。clear() 方法会清空整个缓冲区。compact() **方法只会清除已经读过的数据**。任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面。 



```
程序清单 1-1
RandomAccessFile aFile = new RandomAccessFile("d:\\ay.txt", "rw");
FileChannel fileChannel = aFile.getChannel();
//分配缓存区大小
ByteBuffer buf = ByteBuffer.allocate(48);
int bytesRead = fileChannel.read(buf);
while (bytesRead != -1) {
    System.out.println("Read " + bytesRead);
    //buf.flip()的调用，首先读取数据到Buffer，然后反转Buffer,接着再从Buffer中读取数据（注：flip：空翻，反转）
    buf.flip();
    //判断是否有剩余（注：Remaining：剩余的）
    while(buf.hasRemaining()){
    	System.out.print((char) buf.get());
    }
    buf.clear();
    bytesRead = fileChannel.read(buf);
}
aFile.close();
```



###### **Buffer的三个属性** 



为了理解Buffer的工作原理，需要熟悉它的三个属性： 

- **capacity**：作为一个内存块，Buffer 有一个固定的大小值，也叫 “capacity”. 你只能往里写 capacity 个 byte、long，char 等类型。一旦 Buffer 满了，需要将其清空（通过读数据或者清除数据）才能继续写数据往里写数据。 
- **position**：当你写数据到Buffer中时，position表示当前的位置。初始的position值为0.当一个byte、long等数据写到Buffer后， position会向前移动到下一个可插入数据的 Buffer 单元。position 最大可为 capacity – 1。 当读取数据时，也是从某个特定位置读。当将 Buffer 从写模式切换到读模式，position会被重置为 0。当从Buffer的 position 处读取数据时，position 向前移动到下一个可读的位置。 
- **limit**：在写模式下，Buffer的limit表示你最多能往 Buffer 里写多少数据。 **写模式下**，limit 等于 Buffer 的 capacity 。 当切换Buffer到读模式时， limit 表示你**最多**能读到多少数据。因此，当切换Buffer到读模式时，limit 会被设置成写模式下的 position 值。换句话说，你能读到之前写入的所有数据（limit被设置成已写数据的数量，这个值在写模式下就是 position ）。 



![QQ截图20170628100159](C:\Users\Ay\Desktop\QQ截图20170628100159.png)





###### **Buffer的类型** 

Java NIO 有以下Buffer类型： 

- ByteBuffer
- MappedByteBuffer
- CharBuffer
- DoubleBuffer
- FloatBuffer
- IntBuffer
- LongBuffer
- ShortBuffer




###### **Buffer的分配** 



要想获得一个Buffer对象首先要进行分配。 每一个Buffer类都有一个allocate方法。下面是一个分配48字节capacity的ByteBuffer的例子。 

```
ByteBuffer buf = ByteBuffer.allocate(48);  
```

这是分配一个可存储1024个字符的CharBuffer： 

```
CharBuffer buf = CharBuffer.allocate(1024);  
```



###### **Buffer写数据** 



写数据到Buffer有两种方式： 

- 从Channel写到Buffer。
- 通过Buffer的put()方法写到Buffer里。



从Channel写到Buffer，例如：

```
int bytesRead = inChannel.read(buf); //read into buffer
```

通过put方法写Buffer的例子： 

```
buf.put(127);  
```

put方法有很多版本，允许你以不同的方式把数据写入到Buffer中。例如， 写到一个指定的位置，或者把一个字节数组写入到Buffer。 更多Buffer实现的细节参考JavaDoc。 



##### **flip()方法**

flip方法将Buffer从写模式切换到读模式。调用flip()方法会将position设回0，并将limit设置成之前position的值。 

换句话说，position现在用于标记读的位置，limit表示之前写进了多少个byte、char等 —— 现在能读取多少个byte、char等。 



###### **Buffer中读取数据** 

从Buffer中读取数据有两种方式： 

- 从Buffer读取数据到Channel。
- 使用get()方法从Buffer中读取数据。

从Buffer读取数据到Channel的例子： 

```
//read from buffer into channel.  
int bytesWritten = inChannel.write(buf);  
```

使用get()方法从Buffer中读取数据的例子 ：

```
byte aByte = buf.get();  
```

get方法有很多版本，允许你以不同的方式从Buffer中读取数据。例如，从指定position读取，或者从Buffer中读取数据到字节数组。



**rewind()方法** 

Buffer.rewind()将 position 设回0，所以你可以重读Buffer中的所有数据。limit 保持不变，仍然表示能从Buffer中读取多少个元素（byte、char等）



**clear()与compact()方法**

 

一旦读完Buffer中的数据，需要让Buffer准备好再次被写入。可以通过clear()或compact()方法来完成。 

如果调用的是 clear() 方法，position将被设回 0，limit被设置成 capacity 的值。换句话说，Buffer 被清空了。

如果Buffer中有一些未读的数据，调用clear()方法，数据将“被遗忘”，意味着不再有任何标记会告诉你哪些数据被读过，哪些还没有。 

如果Buffer中仍有未读的数据，且后续还需要这些数据，但是此时想要先先写些数据，那么使用compact()方法。

compact()方法将所有未读的数据拷贝到Buffer起始处。然后将position设到最后一个未读元素正后面。limit 属性依然像 clear() 方法一样，设置成 capacity。现在Buffer准备好写数据了，但是不会覆盖未读的数据。 



**mark()与reset()方法**

通过调用Buffer.mark()方法，可以标记Buffer中的一个特定position。之后可以通过调用Buffer.reset()方法恢复到这个position。例如： 



```
buffer.mark();  
//set position back to mark.  
buffer.reset();  
equals()与compareTo()方法
```

可以使用equals()和compareTo()方法两个Buffer。 

**equals()**

当满足下列条件时，表示两个Buffer相等： 

- 有相同的类型（byte、char、int等）。
- Buffer中剩余的 byte、char 等的个数相等。
- Buffer中所有剩余的byte、char等都相同。

如你所见，equals只是比较Buffer的一部分，不是每一个在它里面的元素都比较。实际上，它只比较Buffer中的剩余元素。 



**compareTo()方法**

compareTo()方法比较两个Buffer的剩余元素(byte、char等)， 如果满足下列条件，则认为一个Buffer “小于” 另一个Buffer： 

- 第一个不相等的元素小于另一个Buffer中对应的元素。
- 所有元素都相等，但第一个Buffer比另一个先耗尽(第一个Buffer的元素个数比另一个少)。







##### 选择器（ Selector）

###### 简单介绍

Java NIO引入了选择器的概念，选择器用于监听多个通道的事件（比如：连接打开，数据到达）。Selector提供选择**已经就绪的任务的能力**：Selector会不断轮询注册在其上的Channel，如果某个Channel上面发生读或者写事件，这个Channel就处于就绪状态，会被Selector轮询出来，然后通过**SelectionKey**可以获取就绪Channel的集合，进行后续的I/O操作。

一个Selector可以同时轮询多个Channel，因为JDK使用了epoll()代替传统的select实现，所以没有最大连接句柄1024/2048的限制。所以，只需要一个线程负责Selector的轮询，就可以接入成千上万的客户端。





![QQ截图20170627174529](C:\Users\Ay\Desktop\QQ截图20170627174529.png)

要使用Selector，得向 Selector 注册 Channel ，然后调用它的 select() 方法。这个方法会一直阻塞到某个注册的通道有事件就绪。一旦这个方法返回，线程就可以处理这些事件，事件的例子比如新连接进来，数据接收等。 



###### **Selector的创建**



通过调用Selector.open()方法创建一个Selector，如下： 

```
Selector selector = Selector.open();  
```

###### **Selector注册通道** 

为了将 Channel 和 Selector 配合使用，必须将 channel 注册到 selector 上。通过 SelectableChannel.register() 方法来实现，如下： 

```
channel.configureBlocking(false);  
SelectionKey key = channel.register(selector,  Selectionkey.OP_READ);  
```

与 Selector 一起使用时，Channel 必须处于非阻塞模式下。这意味着不能将 FileChannel 与 Selector 一起使用，因为 FileChannel 不能切换到非阻塞模式。而套接字通道都可以。 

注意register()方法的第二个参数。这是一个“interest集合”，意思是在通过Selector监听Channel时对什么事件感兴趣。可以监听四种不同类型的事件： 

- Connect
- Accept
- Read
- Write

通道触发了一个事件意思是该事件已经就绪。所以，某个channel成功连接到另一个服务器称为“连接就绪”。一个 server socket channel 准备好接收新进入的连接称为“接收就绪”。一个有数据可读的通道可以说是“读就绪”。等待写数据的通道可以说是“写就绪”。 



这四种事件用 SelectionKey 的四个常量来表示： 

- SelectionKey.OP_CONNECT
- SelectionKey.OP_ACCEPT
- SelectionKey.OP_READ
- SelectionKey.OP_WRITE




如果你对不止一种事件感兴趣，那么可以用 “ 位 或 ” 操作符将常量连接起来，如下： 

```
int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;  
```




###### **SelectionKey** 



在上一小节中，当向Selector注册Channel时，register() 方法会返回一个SelectionKey对象。这个对象包含了一些你感兴趣的属性： 

- interest集合
- ready集合
- Channel
- Selector
- 附加的对象（可选）




下面我会描述这些属性。

**interest集合**

就像向Selector注册通道一节中所描述的，interest集合是你所选择的感兴趣的事件集合。可以通过 SelectionKey 读写 interest 集合，像这样：



```
int interestSet = selectionKey.interestOps();
boolean isInterestedInAccept  = (interestSet & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT；
boolean isInterestedInConnect = interestSet & SelectionKey.OP_CONNECT;
boolean isInterestedInRead    = interestSet & SelectionKey.OP_READ;
boolean isInterestedInWrite   = interestSet & SelectionKey.OP_WRITE;
```


可以看到，用“位与”操作interest 集合和给定的 SelectionKey 常量，**可以确定某个确定的事件是否在 interest 集合中**。

**ready集合**

ready 集合是通道已经准备就绪的操作的集合。在一次选择(Selection)之后，你会首先访问这个readySet。Selection将在下一小节进行解释。可以这样访问ready集合：

```
int readySet = selectionKey.readyOps();
```

可以用像检测 interest 集合那样的方法，来检测channel中什么事件或操作已经就绪。但是，也可以使用以下四个方法，它们都会返回一个布尔类型：

```
selectionKey.isAcceptable();
selectionKey.isConnectable();
selectionKey.isReadable();
selectionKey.isWritable();
```

**Channel + Selector**

从SelectionKey访问Channel和Selector很简单。如下：

```
Channel  channel  = selectionKey.channel();
Selector selector = selectionKey.selector();
```


**附加的对象**

可以将一个对象或者更多信息附着到SelectionKey上，这样就能方便的识别某个给定的通道。例如，可以附加 与通道一起使用的Buffer，或是包含聚集数据的某个对象。使用方法如下：



```
selectionKey.attach(theObject);
Object attachedObj = selectionKey.attachment();
```

还可以在用register()方法向Selector注册Channel的时候附加对象。如：

```
SelectionKey key = channel.register(selector, SelectionKey.OP_READ, theObject);
```

**通过Selector选择通道**

一旦向Selector注册了一或多个通道，就可以调用几个重载的select()方法。这些方法返回你所感兴趣的事件（如连接、接受、读或写）已经准备就绪的那些通道。换句话说，如果你对“读就绪”的通道感兴趣，select()方法会返回读事件已经就绪的那些通道。

下面是select()方法：

- int select()
- int select(long timeout)
- int selectNow()



select()阻塞到至少有一个通道在你注册的事件上就绪了。

select(long timeout) 和 select() 一样，除了最长会阻塞 timeout 毫秒(参数)。

selectNow() 不会阻塞，不管什么通道就绪都立刻返回（译者注：此方法执行非阻塞的选择操作。如果自从前一次选择操作后，没有通道变成可选择的，则此方法直接返回零。）。

select()方法返回的int值表示有多少通道已经就绪。亦即，自上次调用select()方法后有多少通道变成就绪状态。如果调用select()方法，因为有一个通道变成就绪状态，返回了1，若再次调用select()方法，如果另一个通道就绪了，它会再次返回1。如果对第一个就绪的channel没有做任何操作，现在就有两个就绪的通道，但在每次select()方法调用之间，只有一个通道就绪了。



**selectedKeys()**

一旦调用了select()方法，并且返回值表明有一个或更多个通道就绪了，然后可以通过调用selector的selectedKeys()方法，访问“已选择键集（selected key set）”中的就绪通道。如下所示：

```
Set selectedKeys = selector.selectedKeys();
```

当向 Selector 注册 Channel 时，Channel.register() 方法会返回一个 SelectionKey 对象。这个对象代表了注册到该Selector的通道。可以通过SelectionKey的selectedKeySet()方法访问这些对象。

可以遍历这个已选择的键集合来访问就绪的通道。如下：

```
Set selectedKeys = selector.selectedKeys();
Iterator keyIterator = selectedKeys.iterator();
while(keyIterator.hasNext()) {
    SelectionKey key = keyIterator.next();
    if(key.isAcceptable()) {
        // a connection was accepted by a ServerSocketChannel.
    } else if (key.isConnectable()) {
        // a connection was established with a remote server.
    } else if (key.isReadable()) {
        // a channel is ready for reading
    } else if (key.isWritable()) {
        // a channel is ready for writing
    }
    keyIterator.remove();
}
```


这个循环遍历已选择键集中的每个键，并检测各个键所对应的通道的就绪事件。

注意每次迭代末尾的 keyIterator.remove() 调用。Selector不会自己从已选择键集中移除 SelectionKey 实例。必须在处理完通道时自己移除。下次该通道变成就绪时，Selector会再次将其放入已选择键集中。

SelectionKey.channel() 方法返回的通道需要转型成你要处理的类型，如 ServerSocketChannel 或 SocketChannel 等。

**wakeUp()**

某个线程调用select()方法后阻塞了，即使没有通道已经就绪，也有办法让其从select()方法返回。只要让其它线程在第一个线程调用select()方法的那个对象上调用Selector.wakeup()方法即可。阻塞在select()方法上的线程会立马返回。

如果有其它线程调用了wakeup()方法，但当前没有线程阻塞在select()方法上，下个调用select()方法的线程会立即“醒来（wake up）”。

**close()**

用完 Selector 后调用其 close() 方法会关闭该 Selector，且使注册到该Selector上的所有SelectionKey实例无效。通道本身并不会关闭。

完整的示例

这里有一个完整的示例，打开一个Selector，注册一个通道注册到这个Selector上(通道的初始化过程略去),然后持续监控这个Selector的四种事件（接受，连接，读，写）是否就绪。

    Selector selector = Selector.open();
    channel.configureBlocking(false);
    SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
    while(true) {
      int readyChannels = selector.select();
      if(readyChannels == 0) continue;
      Set selectedKeys = selector.selectedKeys();
      Iterator keyIterator = selectedKeys.iterator();
      while(keyIterator.hasNext()) {
      SelectionKey key = keyIterator.next();
      if(key.isAcceptable()) {
        // a connection was accepted by a ServerSocketChannel.
      } else if (key.isConnectable()) {
        // a connection was established with a remote server.
      } else if (key.isReadable()) {
        // a channel is ready for reading
      } else if (key.isWritable()) {
        // a channel is ready for writing
      }
        keyIterator.remove();
      }
    }


##### 分散（Scatter）/聚集（Gather）

###### **分散概念**

分散（scatter）：从Channel中读取是指在读操作时将读取的数据写入多个buffer中。因此，Channel将从Channel中读取的数据“分散（scatter）”到多个Buffer中。 

![QQ截图20170628113309](C:\Users\Ay\Desktop\QQ截图20170628113309.png)

```
程序清单 1-1
ByteBuffer header = ByteBuffer.allocate(128);  
ByteBuffer body   = ByteBuffer.allocate(1024);  
ByteBuffer[] bufferArray = { header, body };  
channel.read(bufferArray);  
```

注意buffer首先被插入到数组，然后再将数组作为 channel.read() 的输入参数。read() 方法按照 buffer 在数组中的顺序将从 channel 中读取的数据写入到buffer，当一个 buffer 被写满后，channel 紧接着向另一个 buffer 中写。 

Scattering Reads在移动下一个buffer前，必须填满当前的buffer，这也意味着它不适用于动态消息(译者注：消息大小不固定)。换句话说，如果存在消息头和消息体，消息头必须完成填充（例如 128byte），Scattering Reads才能正常工作。 



###### **聚集概念**



![无标题888888](C:\Users\Ay\Desktop\无标题888888.png)聚集（gather）写入Channel是指在写操作时将多个buffer的数据写入同一个Channel，因此，Channel 将多个Buffer中的数据“聚集（gather）”后发送到Channel。 



```
示例1-1
ByteBuffer header = ByteBuffer.allocate(128);  
ByteBuffer body   = ByteBuffer.allocate(1024);  
//write data into buffers  
ByteBuffer[] bufferArray = { header, body };  
channel.write(bufferArray);  
```



buffer的一个数组被传递给了 write() 方法，这个方法写他们在数组中遇到的接下来的 buffer 的内容。只是这些数据在 buffer 的 position 和 limit 直接被写。因此，如果一个buffer有一个128字节的容量，但是只包含了58个字节，只有58个字节可以从 buffer 中写到 channel 。因此，一个聚集写操作通过动态可变大小的消息部分会工作的很好，跟分散读取正好相反。



###### **分散/聚集的应用**

scatter / gather经常用于需要将传输的数据分开处理的场合。例如，您可能在编写一个使用消息对象的网络应用程序，每一个消息被划分为固定长度的头部和固定长度的正文。您可以创建一个刚好可以容纳头部的缓冲区和另一个刚好可以容纳正文的缓冲区。当您将它们放入一个数组中并使用分散读取来向它们读入消息时，头部和正文将整齐地划分到这两个缓冲区中。

我们从缓冲区所得到的方便性对于缓冲区数组同样有效。因为每一个缓冲区都跟踪自己还可以接受多少数据，所以分散读取会自动找到有空间接受数据的第一个缓冲区。在这个缓冲区填满后，它就会移动到下一个缓冲区。



###### **简单小例子**

```
RandomAccessFile raf1=new RandomAccessFile("d:\\ay.txt", "rw");
//获取通道
FileChannel channel1 = raf1.getChannel();
//设置缓冲区
ByteBuffer buf1=ByteBuffer.allocate(50);
ByteBuffer buf2=ByteBuffer.allocate(1024);
//分散读取的时候缓存区应该是有序的，所以把几个缓冲区加入数组中
ByteBuffer[] bufs={buf1,buf2};
//通道进行传输
channel1.read(bufs);
//查看缓冲区中的内容
for (int i = 0; i < bufs.length; i++) {
   //切换为读模式
   bufs[i].flip();
}
System.out.println(new String(bufs[0].array(),0,bufs[0].limit()));
System.out.println();
System.out.println(new String(bufs[1].array(),0,bufs[1].limit()));
//聚集写入
RandomAccessFile  raf2=new RandomAccessFile("d:\\al.txt", "rw");
FileChannel channel2 = raf2.getChannel();
//只能通过通道来进行写入
channel2.write(bufs);
```



##### **其他通道**

​		    

###### **文件通道**

略

###### Socket 通道



Java NIO中的 SocketChannel 是一个连接到 TCP 网络套接字的通道。可以通过以下2种方式创建 SocketChannel：

- 打开一个SocketChannel并连接到互联网上的某台服务器。
- 一个新连接到达 ServerSocketChannel 时，会创建一个 SocketChannel。



**打开 SocketChannel**



下面是SocketChannel的打开方式：

```
SocketChannel socketChannel = SocketChannel.open();
socketChannel.connect(new InetSocketAddress("http://jenkov.com",80));
```



**从 SocketChannel 读取数据**

要从SocketChannel中读取数据，调用一个read()的方法之一。以下是例子：

```
ByteBuffer buf = ByteBuffer.allocate(48);
int bytesRead = socketChannel.read(buf);
```

首先，分配一个Buffer。从SocketChannel读取到的数据将会放到这个Buffer中。

然后，调用SocketChannel.read()。该方法将数据从SocketChannel 读到Buffer中。read()方法返回的int值表示读了多少字节进Buffer里。如果返回的是-1，表示已经读到了流的末尾（连接关闭了）。



**写入 SocketChannel**



写数据到SocketChannel用的是SocketChannel.write()方法，该方法以一个Buffer作为参数。示例如下：



```
String newData = "New String to write to file..." + System.currentTimeMillis();
ByteBuffer buf = ByteBuffer.allocate(48);
buf.clear();
buf.put(newData.getBytes());
buf.flip();
while(buf.hasRemaining()) {
    channel.write(buf);
}
```

注意SocketChannel.write()方法的调用是在一个while循环中的。Write()方法无法保证能写多少字节到SocketChannel。所以，我们重复调用write()直到Buffer没有要写的字节为止。





**非阻塞模式**



可以设置 SocketChannel 为非阻塞模式（non-blocking mode）.设置之后，就可以在异步模式下调用connect(), read() 和write()了。



**connect()**



如果SocketChannel在非阻塞模式下，此时调用connect()，该方法可能在连接建立之前就返回了。为了确定连接是否建立，可以调用finishConnect()的方法。像这样：



```
socketChannel.configureBlocking(false);
socketChannel.connect(new InetSocketAddress("http://jenkov.com", 80));
while(! socketChannel.finishConnect() ){
    //wait, or do something else...
}
```



**write()**

非阻塞模式下，write()方法在尚未写出任何内容时可能就返回了。所以需要在循环中调用write()。前面已经有例子了，这里就不赘述了。



**read()**

非阻塞模式下,read()方法在尚未读取到任何数据时可能就返回了。所以需要关注它的int返回值，它会告诉你读取了多少字节。



**不错的小例子**



一下是来自网络的一个小例子，个人觉得很不错，就贴到这里。



```
class NioClient {
    //管道管理器
    private Selector selector;

    public NioClient init(String serverIp, int port) throws IOException{
        //获取socket通道
        SocketChannel channel = SocketChannel.open();

        channel.configureBlocking(false);
        //获得通道管理器
        selector=Selector.open();

        //客户端连接服务器，需要调用channel.finishConnect();才能实际完成连接。
        channel.connect(new InetSocketAddress(serverIp, port));
        //为该通道注册SelectionKey.OP_CONNECT事件
        channel.register(selector, SelectionKey.OP_CONNECT);
        return this;
    }

    public void listen() throws IOException{
        System.out.println("客户端启动");
        //轮询访问selector
        while(true){
            //选择注册过的io操作的事件(第一次为SelectionKey.OP_CONNECT)
            selector.select();
            Iterator<SelectionKey> ite = selector.selectedKeys().iterator();
            while(ite.hasNext()){
                SelectionKey key = ite.next();
                //删除已选的key，防止重复处理
                ite.remove();
                if(key.isConnectable()){
                    SocketChannel channel=(SocketChannel)key.channel();

                    //如果正在连接，则完成连接
                    if(channel.isConnectionPending()){
                        channel.finishConnect();
                    }

                    channel.configureBlocking(false);
                    //向服务器发送消息
                    channel.write(ByteBuffer.wrap(new String("send message to server.").getBytes()));

                    //连接成功后，注册接收服务器消息的事件
                    channel.register(selector, SelectionKey.OP_READ);
                    System.out.println("客户端连接成功");
                }else if(key.isReadable()){ //有可读数据事件。
                    SocketChannel channel = (SocketChannel)key.channel();

                    ByteBuffer buffer = ByteBuffer.allocate(10);
                    channel.read(buffer);
                    byte[] data = buffer.array();
                    String message = new String(data);

                    System.out.println("recevie message from server:, size:" + buffer.position() + " msg: " + message);
//                    ByteBuffer outbuffer = ByteBuffer.wrap(("client.".concat(msg)).getBytes());
//                    channel.write(outbuffer);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new NioClient().init("127.0.0.1", 9981).listen();
    }
}
```



```
class NioServer {
    //通道管理器
    private Selector selector;

    //获取一个ServerSocket通道，并初始化通道
    public NioServer init(int port) throws IOException{
        //获取一个ServerSocket通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.socket().bind(new InetSocketAddress(port));
        //获取通道管理器
        selector=Selector.open();
        //将通道管理器与通道绑定，并为该通道注册SelectionKey.OP_ACCEPT事件，
        //只有当该事件到达时，Selector.select()会返回，否则一直阻塞。
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        return this;
    }

    public void listen() throws IOException{
        System.out.println("服务器端启动成功");

        //使用轮询访问selector
        while(true){
            //当有注册的事件到达时，方法返回，否则阻塞。
            selector.select();

            //获取selector中的迭代器，选中项为注册的事件
            Iterator<SelectionKey> ite=selector.selectedKeys().iterator();

            while(ite.hasNext()){
                SelectionKey key = ite.next();
                //删除已选key，防止重复处理
                ite.remove();
                //客户端请求连接事件
                if(key.isAcceptable()){
                    ServerSocketChannel server = (ServerSocketChannel)key.channel();
                    //获得客户端连接通道
                    SocketChannel channel = server.accept();
                    channel.configureBlocking(false);
                    //向客户端发消息
                    channel.write(ByteBuffer.wrap(new String("send message to client").getBytes()));
                    //在与客户端连接成功后，为客户端通道注册SelectionKey.OP_READ事件。
                    channel.register(selector, SelectionKey.OP_READ);

                    System.out.println("客户端请求连接事件");
                }else if(key.isReadable()){//有可读数据事件
                    //获取客户端传输数据可读取消息通道。
                    SocketChannel channel = (SocketChannel)key.channel();
                    //创建读取数据缓冲器
                    ByteBuffer buffer = ByteBuffer.allocate(10);
                    int read = channel.read(buffer);
                    byte[] data = buffer.array();
                    String message = new String(data);

                    System.out.println("receive message from client, size:" + buffer.position() + " msg: " + message);
//                    ByteBuffer outbuffer = ByteBuffer.wrap(("server.".concat(msg)).getBytes());
//                    channel.write(outbuffer);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new NioServer().init(9981).listen();
    }
}
```





###### Datagram 通道

Java NIO中的DatagramChannel是一个能收发UDP包的通道。因为UDP是无连接的网络协议，所以不能像其它通道那样读取和写入。它发送和接收的是数据包。



Datagram 通道就作为大家自学的内容。



##### 管道（Pipe）

Java NIO 管道是2个线程之间的单向数据连接。Pipe有一个source通道和一个sink通道。数据会被写到sink通道，从source通道读取。 



###### 

![QQ截图20170628152554](C:\Users\Ay\Desktop\QQ截图20170628152554.png)



###### **创建管道** 



通过Pipe.open()方法打开管道。例如： 

```
Pipe pipe = Pipe.open(); 
```



###### **向管**道写数据

要向管道写数据，需要访问sink通道。像这样： 

```
Pipe.SinkChannel sinkChannel = pipe.sink(); 
```

通过调用SinkChannel的write()方法，将数据写入SinkChannel,像这样： 



```
String newData = "New String to write to file..." + System.currentTimeMillis();  
ByteBuffer buf = ByteBuffer.allocate(48);  
buf.clear();  
buf.put(newData.getBytes());  
buf.flip();  
while(buf.hasRemaining()) {  
   <b>sinkChannel.write(buf);</b>  
} 
```



###### **从管道读取数据** 

从读取管道的数据，需要访问source通道，像这样： 

```
Pipe.SourceChannel sourceChannel = pipe.source(); 
```

调用source通道的read()方法来读取数据，像这样： 

```
ByteBuffer buf = ByteBuffer.allocate(48);  
int bytesRead = inChannel.read(buf);  
```

read()方法返回的int值会告诉我们多少字节被读进了缓冲区。 

###### 简单完整实例



```
//获取管道
Pipe pipe = Pipe.open();
//获取Sink 管道
Pipe.SinkChannel sinkChannel = pipe.sink();
//需要写入数据
String newData = "New String to write to file..." + System.currentTimeMillis();
//新建缓存区
ByteBuffer buf = ByteBuffer.allocate(48);
buf.clear();
//缓存区存放数据
buf.put(newData.getBytes());
buf.flip();
while(buf.hasRemaining()) {
    sinkChannel.write(buf);
}
//获取Source 管道
Pipe.SourceChannel sourceChannel = pipe.source();
ByteBuffer buf2 = ByteBuffer.allocate(48);
int bytesRead = sourceChannel.read(buf2);
while (bytesRead != -1) {
    System.out.println("Read " + bytesRead);
    //buf.flip()的调用，首先读取数据到Buffer，然后反转Buffer,接着再从Buffer中读取数据（注：flip：空翻，反转）
    buf.flip();
    //判断是否有剩余（注：Remaining：剩余的）
    while(buf.hasRemaining()){
        System.out.print((char) buf.get());
    }
    buf.clear();
    bytesRead = sourceChannel.read(buf);
}
sourceChannel.close();
sinkChannel.close();
```



#### AIO编程



###### **AIO的特点**

- 读完了再通知我

- 不会加快IO，只是在读完后进行通知

- 使用回调函数，进行业务处理

  ​

AIO的相关代码：

```
//AsynchronousServerSocketChannel类
server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(PORT));
```



使用server上的accept方法



```
public abstract <A> void accept(A attachment,CompletionHandler<AsynchronousSocketChannel,? super A> handler);
```

CompletionHandler为回调接口，当有客户端accept之后，就做handler中的事情。



###### NIO与AIO区别

- NIO是同步非阻塞的，AIO是异步非阻塞的
- 由于NIO的读写过程依然在应用线程里完成，所以对于那些读写过程时间长的，NIO就不太适合。而AIO的读写过程完成后才被通知，所以AIO能够胜任那些重量级，读写过程长的任务。





**结束语**







#### **参考文章**

【1】[http://blog.csdn.net/anxpp/article/details/51512200](http://blog.csdn.net/anxpp/article/details/51512200)

【2】http://www.iteye.com/magazines/132-Java-NIO

【3】http://www.jb51.net/article/92448.htm

【4】http://www.cnblogs.com/good-temper/p/5003892.html

【5】http://www.cnblogs.com/fanzhidongyzby/p/4098546.htm

【6】https://www.ibm.com/developerworks/cn/java/l-niosvr/

【7】Netty权威指南

【8】http://ifeve.com/selectors/

【9】http://ifeve.com/socket-channel/
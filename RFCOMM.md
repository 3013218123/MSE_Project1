#网址http://blog.sina.com.cn/s/blog_4c02ba150101r81r.html

RFCOMM是一个简单的协议，其中针对9针RS-232串口仿真附加了部分条款.可支持在两个蓝牙设备之间同时保持高达60路的通信连接.RFCOMM的目的是针对如何在两个不同设备上的应用之间保证一条完整的通信路径。

image

1.RFCOMM服务概述

RFCOMM仿真RS232串口，该仿真过程包括非数据通路状态的传输。RFCOMM不限制人工速率或步长，如果通信链路两端的设备都是负责将数据转发到其他通信介质的第二类设备，或在两端RFCOMM设备接口上进行数据传输，实际数据吞吐一般将反映波特率的设置.RFCOMM支持两个设备之间的多串口仿真，也支持多个设备多串口的仿真.

1.1 RS232控制信令

RFCOMM提供针对9针RS-232接口的仿真.

image

1.2 空MODEM仿真

RFCOMM基于TS 07.10，当设备准备传输非数据通路的状态信息时，TS07.10不区分DTE和DCE设备，而是通过RS232控制信号来表示DTE/DCE各自的信号.如下表反映其映射关系.

当两同类设备互联时，TS07.10传输RS232控制信号的方式就会创建空MODEM，

image

1.3 多串口仿真

1.两设备间的多串口仿真

两个采用RFCOMM进行通信的BT设备可以同时打开多个串口,最多60个，但是一个设备打开端口数根据实际实现而不同.

image

一个数据链接标识(DLCI)唯一标识对客户和服务器之间的持续连接.DLCI长度为6bit,可用值区间为2~61.TS07.10中，DLCI 0 为控制信道，DLCI 1根据服务器信道概念不能使用，DLCI 62-63保留使用，DLCI在两个设备间的RFCOMM会话中保持一致.

在一次RFCOMM会话中，客户和服务器应用可以分布在通信的两端，每一段的客户都可以独立发起建立通信连接。因此RFCOMM服务器信道的概念将DLCI值域空间在两个正在进行通信的设备间进行划分。

2.多仿真串口和多BT设备.

如果BT设备支持多串口仿真，通信连接两端允许使用不同BT设备,那么RFCOMM实体必须能够运行多个TS07.10多路复用器会话,每一多路复用器都是用其L2CAP信道ID(CID)。RFCOMM可以选择支持TS07.10多路复用器的多个会话.

2. 服务接口描述

RFCOMM目的在于定义一个能够利用仿真串口的协议。

image

image

3.采用TS07.10子集和TS07.10的修正后的RFCOMM

3.1 RFCOMM的帧结构

RFCOMM不使用TS07.10基本帧格式中的开始和结束标志，而仅适用包含在L2CAP层和RFCOMM层间交换标志的那些域。

image

1.地址字段(Address Field)

地址字段占一个字节，它包括DLCI（Data Link connection identifier,数据链路链接标识），C/R位和地址字段扩展位(EA),其中在DLCI（第三位到第八位）中D位表示方向.

image

对于一次RFCOMM会话，发起方设备方向位(Direction bit)设为D=1（相反则D=0），当在已有的RFCOMM会话上建立一条新的数据链接时，方向位用于服务器端通道相关，以确定其DLCI，从而建立到特定应用的连接.连接建立后，DLCI就在两端间的两个方向上传输数据分组.DLCI值域实际上分为两部分，非发起方设备上的应用使用DLCI偶数号访问，发起方设备上的应用则使用DLCI奇数号，对于一个支持多路同步RFCOMM会话的设备，方向位不一定在所有会话中都一致.

一个在已有会话上建立新的DLC的RFCOMM实体，将其他设备使用的服务器通道和该会话方向位的求反值组合为DLCI。

DLCI不仅是标志着个人用户信息流，而且标志着连接着TE和MS之间的链路。DLC是动态分配的.

image

DLC0用于控制信道.

C/R(Command/Response)位表示的是此帧是一个命令还是一个响应.和HDLC规则一样，命令帧包含数据链路连接的地址是目的地的地址，而应答帧包含的是应答方的地址,对于给定的DLCI,命令和应答的地址字段的DLCI值是一样的，就是C/R位不同.

image

2.控制字段(Control Field)

image

image

image

image

4. 流控制

RFCOMM协议提供两类流控制协议:

<1>RFCOMM协议定义了能对两RFCOMM实体之间全部数据流操作的流控制指令，对所有的DLCI都起作用.

<2>调制解调器状态指令实质就是可操作单个DLCI的流控制机制.

image

端口仿真实体将系统指定通信端口映射与RFCOMM服务,端口代理实体将数据从RFCOMM转发至连接DCE设备的外部RS232接口,RS232接口的通信参数根据接受的RPN指令进行设置.
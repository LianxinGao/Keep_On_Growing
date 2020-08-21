### Background
1. 文件系统：位于硬盘上，负责存储和管理文件，并提供文件的增查删改等操作。
2. 分布式文件系统：一个大文件会被切分成块，分别存储到几台机器上，然后提供给client一个统一的操作接口，就像在操作本地文件系统一样。
- - -
### 知识点
1. 元数据（metadata）：描述数据的数据，这里指描述文件路径、文件被分为几块，每一块在哪些DataNode上。
2. fsImage：之前某一时刻整个NameNode的文件metadata的内存快照。
- - -
### 分布式文件系统存在的潜在问题
Q1. 如何快速定位文件位于哪台机器上？  
Q2. 若某台机器坏了，那台坏了的机器上的文件该如何访问？
- - - 
### HDFS架构
#### 1. 上述问题解决方案
Q1. client通过NameNode了解数据在哪些DataNode，从而进行查询；写文件的时候同样通过请教NameNode该去哪里写数据。  
Q2. HDFS写入数据块时，会写入到多个DataNode里面（备份）。（空间换安全性） 

#### 2. HDFS读写流程
1. Client根据配置文件将大文件切成固定大小的块。（切到最后只剩一点点数据的时候还是扩展成那么大的块？）
2. 若文件分为3块，每份文件要复制3份，client只需要向NameNode确认要写入的地方，并将3块文件写入即可，复制3份的任务由DataNode之间自动同步。
3. 

#### 3. 存在的潜在问题
##### Q1. NameNode重启，它内存中的信息就丢失了，文件的元数据怎么办？  
1. HDFS会把操作日志记录下来，存在editLog中，重启后先加载editLog，回放日志，达到重启前的状态。（WAL）
    1. 若NameNode运行很久，文件操作很多的话，editLog就会很大，下次重启则会需要非常长的时间，该怎么处理？
        1. NameNode不是每次都重头回放，它首先会加载fsImage，再在这个基础上进行回放editLog，完成后清空editLog，再把当前文件metadata的内存状态写入fsImage。
    2. 若NameNode很长时间都没有重启，editLog依然会很大，这该怎么处理呢？
        1. SecondaryNameNode会定期将editLog写入fsImage，然后清空editLog。（属于定时任务）
##### Q2. NameNode挂了，谁来接替它工作？  
1. Hadoop 2.x之前，是单点。
2. 2.x之后可以配置多个NameNode。以2个NameNode来说，一个active，一个standby。
    1. 2个NameNode通过JournalNode实时同步editLog，状态一致可以相互转换，standby NameNode实现了SecondaryNameNode的功能。
    2. active有操作后，editLog会被记录到JournalNode，standby会从JN中读取到变化并同步，同时standby会监听记录的变化。
    3. 若2.x只部署一个NameNode还是会启用SecondaryNameNode。

#### HDFS优缺点
1. 可存储海量数据，高可用，任何一台机器挂了都不会影响系统的使用，也不会造成数据丢失。
2. 不适合存储海量的【小文件】，会造成NameNode元数据爆炸，内存不够。
3. HDFS不提供编辑文件的功能，只能追加。若要改，只能覆盖。
4. HDFS不支持并发写入
5. 当初设计目的为做离线计算，查询效率不高，是秒级。


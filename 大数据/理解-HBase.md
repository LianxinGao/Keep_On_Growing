###1. Background
- HBase出现的原因：

- HBase和HDFS的区别：  
    - HDFS是文件系统，HBase是基于HDFS构建的NoSQL数据库。 
    - 可以理解为HBase为MySQL，HDFS为硬盘。  
    
- 使用HBase的原因：
    - 在HDFS上提供了HDFS不具备的**高并发随机写和支持实时查询**  
- - -
###2. HBase
1. 存储方式：**列式存储**  
优势：不用像行式存储那样，需要拿空间存储字段为空的column。  

    


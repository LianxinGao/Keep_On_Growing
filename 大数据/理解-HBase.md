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
2. 数据模型
- RowKey定位数据
- ColumnFamily列族（列的属性类别）
    - column：列（列族下具体的某个属性）
3. 数据存储
- 不管是更新还是删除操作，都是在数据库中新增一条记录并修改时间戳，系统读取的时候读最新的时间戳数据。
（疑问：旧版本数据何时清除呢？）
- HBase的本质是Key-value数据库
    - Key = RowKey + ColumnFamily + ColumnQualifier + TimeStamp + KeyType（key type：update/delete...）
    - Value = 要存的数据的ByteArray
- - -
###3. HBase API（）
1. org.apache.hadoop.hbase.HBaseConfiguration
管理HBase的配置信息.
```
val hbaseConf = HBaseConfiguration.create()
```
2. org.apache.hadoop.hbase.client.Admin
该接口用来管理HBase数据库的表信息（增查删改等）.
```
val conn = ConnectionFactory.createConnection(hbaseConf)
val admin = conn.getAdmin
```

   


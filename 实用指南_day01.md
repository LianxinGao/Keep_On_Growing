# Notes
1. 与集合不同，访问元组的索引从1开始
2. 数组展开标记：
  ```
  def max(values: Int*) = values.foldLeft(values(0)){Math.max}
  
  val numbers = Array(2, 1, 5, 3, 9, 7)
  max(numbers)  //将报类型不匹配的错误
  max(numbers:_*)  //正确执行，参数名后面的一系列符号告诉编译器将数组展开成所需的形式，以传送变长参数值
  ```

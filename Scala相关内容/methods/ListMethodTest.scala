object ListMethodTest {
  def main(args: Array[String]): Unit = {
    var lst = List("Avril", "Lavigne", "Kirito", "Asuna", "Lisa", "Violet")
    lst = "Names:" :: lst
    println(lst)
    println(lst.tail) // 返回除首个元素的列表
    println(lst.init) // 返回除最后一个元素的列表
    println(lst.dropRight(2))
    println(lst.count(s => s.length == 5))
    println(lst.filter(s => s.length == 5))
    println(lst.mkString(" "))
    println(lst.sortWith((s, t) => s.toLowerCase() < t.toLowerCase()))
  }
}

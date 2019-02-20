object ACTest extends App {

  @AccessControl
  def add(a: Int, b: Int): Int = {
    a + b
  }

}

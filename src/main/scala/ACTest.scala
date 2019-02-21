object ACTest extends App {


  case class SecurityContext(customerId: String, role: String)

  @AccessControl
  def add(param1: Int, b: Int)(implicit context: SecurityContext): Int = {
    param1 + b
  }

}

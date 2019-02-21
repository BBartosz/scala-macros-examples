import ACTest.SecurityContext

object Main extends App{
  Test.testMethod
  Test.methodWithArguments(1.0, 5.0)

  Dog("Bobby").sayHello

  println("Attempting to " + ACTest.add(1, 1)(SecurityContext("1001", "Admin")))

  println("The final result")
}


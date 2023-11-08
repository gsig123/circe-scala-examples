import io.circe._
import io.circe.generic.auto._
import io.circe.parser._

object Main extends App {
  case class Person(name: String, age: Int)

  val jsonStr = """{ "name": "John", "age": "thirty" }"""


  println("\n\n")
  val decoded = decode[Person](jsonStr) match {
    case Right(person) => println(s"Decoded person: $person")
    case Left(error) =>
      error match {
        case DecodingFailure(reason, history) =>
          println(s"Failed to decode: $reason")
          history.foreach(op => println(s"Operation: $op"))
        case _ => println("Some other error")
      }
  }

  println("\n\n")

}


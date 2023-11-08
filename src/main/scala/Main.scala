import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.parser._


object Main extends App {

  // Define some case classes
  case class Address(street: String, city: String)
  case class Person(name: String, age: Int, address: Address)

  // Create an instance of Person
  val person = Person("Bjartur", 29, Address("Ørestads Boulevard", "Copenhagen"))

  // Automatically Encode the Person instance to JSON
  val json = person.asJson
  println("\nJSON:")
  println(s"\n$json \n")


  val jsonString: String = """{"name": "Anders", "age": 19, "address": {"street": "Country road", "city": "Bogø"}}"""
  val decodedPerson = decode[Person](jsonString)
  println("\nCase class:")
  println(s"\n$decodedPerson\n")

}

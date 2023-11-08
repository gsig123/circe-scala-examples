import io.circe._
import io.circe.generic.semiauto._
import io.circe.syntax._
import io.circe.parser._


object Main extends App {
  // Define a case class
  case class User(id: Int, username: String, email: String)

  // Let's create a custom encoder for User that only includes id and username
  implicit val encodeUser: Encoder[User] = new Encoder[User] {
    final def apply(u: User): Json = Json.obj(
        ("id", Json.fromInt(u.id)),
        ("username", Json.fromString(u.username))
        // We're omitting the email field on purpose
      ) 
  }

  // Similarly, create a custom decoder that provides a default email if it's missing
  implicit val decodeUser: Decoder[User] = new Decoder[User] {
    final def apply(c: HCursor): Decoder.Result[User] = for {
      id <- c.downField("id").as[Int]
      username <- c.downField("username").as[String]
      // Assume email is optional in the JSON and provide a default
      email <- c.downField("email").as[Option[String]].map(_.getOrElse("default@email.com"))
    } yield {
      User(id, username, email)
    }
  }

  // Now let's test encoding and decoding
  val user = User(1, "bjartur", "bis@famly.co")

  // Encoding the user to JSON string
  val jsonString: String = user.asJson.toString()
  println("\n\nJSON:\n")
  println(s"$jsonString\n")

  // Decoding from a JSON string, assuming the JSON is missing email
  val decodedUser = decode[User]("""{"id": 1, "username": "Aske"}""")

  decodedUser match {
    case Right(user) => println(s"Decoded user: $user")
    case Left(error) => println(s"Failed to decode: $error")
  }
}

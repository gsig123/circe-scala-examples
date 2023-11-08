import io.circe.{Encoder, Decoder}
import io.circe.generic.semiauto.{deriveEncoder, deriveDecoder}
import io.circe.syntax._
import io.circe.parser._

object Main extends App {
  // Define some case classes
  case class Item(name: String, price: Double)
  case class Order(items: List[Item], total: Double)

  // Semi-automatic derivation of encoders and decoders
  implicit val itemEncoder: Encoder[Item] = deriveEncoder[Item]
  implicit val itemDecoder: Decoder[Item] = deriveDecoder[Item]
  
  implicit val orderEncoder: Encoder[Order] = deriveEncoder[Order]
  implicit val orderDecoder: Decoder[Order] = deriveDecoder[Order]

  // Now we can encode and decode using those codecs
  val order = Order(List(Item("Widget", 99.95)), 99.95)

  // Encoding order to JSON string
  val jsonString: String = order.asJson.toString()
  println("\n\nJSON:")
  println(s"$jsonString\n")

  // Decoding JSON back to Order
  val decodedOrder = decode[Order](jsonString)
  println("\n\nDecoded:")
  decodedOrder match {
    case Right(order) => println(s"Decoded order: $order")
    case Left(error) => println(s"Failed to decode: $error")
  }
  println("\n\n")


}

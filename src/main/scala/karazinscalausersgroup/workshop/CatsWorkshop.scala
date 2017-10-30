package karazinscalausersgroup.workshop

import cats._
import cats.implicits._

/**
  * @author Igor Wolkov
  */
object CatsWorkshop extends App {

  case class Record(amount: Long)

  implicit val intSemigroup: Semigroup[Record] =
    Semigroup[Long].imap(Record)(_.amount)

  val res = Record(1) |+| Record(2)

  println(res)

}

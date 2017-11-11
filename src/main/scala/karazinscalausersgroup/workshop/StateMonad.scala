package karazinscalausersgroup.workshop


object StateMonad extends App {
  // 7

  import cats.data.{EitherT, OptionT, StateT}
  import cats.instances.option._
  import cats.syntax.applicative._
  import cats.syntax.either._

  import scala.concurrent.duration._
  import scala.concurrent.{Await, Future}
  import scala.language.postfixOps

  trait Status
  case object Raw extends Status
  case object New extends Status
  case object Assigned extends Status
  case object Executed extends Status

  case class Transaction(uuid: String, accountUuid: String, amount: Long, status: Status)
  case class Account(uuid: String, balance: Long)


}

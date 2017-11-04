package karazinscalausersgroup.workshop

import cats.data.Writer
import cats.instances.vector._
import cats.syntax.applicative._
import cats.syntax.writer._

object WriterMonadFlow extends App {

  trait Record {
    def id: Int
    def amount: Long
  }

  case class Principal(id: Int, amount: Long) extends Record
  case class Interest(id: Int, amount: Long) extends Record

  val loan1 =
    Principal(1, 100) :: Interest(1, 100) ::
      Principal(1, 100) :: Interest(1, 100) :: Nil

  val loan2 =
    Principal(2, 100) :: Interest(2, 100) ::
      Principal(2, 100) :: Interest(2, 100) :: Nil


  trait Action {
    def amount: Long
  }

  case class CoverPrincipal(installment: Int, amount: Long) extends Action
  case class CoverInterest(installment: Int, amount: Long) extends Action

  def coverRecord(record: Record, amount: Long): Option[(Action, Long)] = {
    def getAction(record: Record, amount: Long): Action =
      record match {
        case _: Principal => CoverPrincipal(record.id, amount)
        case _: Interest  => CoverInterest(record.id, amount)
      }

    if (record.amount <= amount) Option((getAction(record, record.amount), amount - record.amount))
    else if (amount > 0)         Option((getAction(record, amount), 0))
    else                         None
  }

}

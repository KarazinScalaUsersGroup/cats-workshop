package karazinscalausersgroup.workshop

import cats.functor.Contravariant

import scala.language.{higherKinds, postfixOps}

/**
  * @author Igor Wolkov
  */
object CatsWorkshop extends App {

  case class Loan(amount: Long)
  case class Record(amount: Long)

  val loan = Loan(42)
  val records = Record(1) :: Record(2) :: Record(3) :: Nil

  val recordsToLoan: List[Record] => Loan =
    records => Loan(records.foldLeft(0L)((acc, rec) => acc + rec.amount))

  trait Balance[T] {
    def calculate(t: T): Long
  }

  implicit val balanceContravariant: Contravariant[Balance] = new Contravariant[Balance] {
    def contramap[A, B](fa: Balance[A])(f: (B) => A): Balance[B] =
      (t: B) => fa.calculate(f(t))
  }

  val loanBalance: Balance[Loan] =
    (t: Loan) => t.amount

  println(loanBalance.calculate(loan))

  val recordsBalance: Balance[List[Record]] =
    Contravariant[Balance].contramap(loanBalance)(recordsToLoan)

  println(recordsBalance.calculate(records))

}

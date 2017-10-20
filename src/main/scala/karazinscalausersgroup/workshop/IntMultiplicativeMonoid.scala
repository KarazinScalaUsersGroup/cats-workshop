package karazinscalausersgroup.workshop

import cats.kernel.Eq
import cats.kernel.laws.GroupLaws


/**
  * @author Igor Wolkov
  */
object IntMultiplicativeMonoid extends App {

  import cats.kernel.Monoid
  import cats.syntax.semigroup._

  implicit val multiplicativeMonoid: Monoid[Int] =
    new Monoid[Int] {
      def empty = 0
      def combine(x: Int, y: Int) = x * y
    }

  println(Monoid[Int].combine(3, 4))

  println(3 |+| 4)

  implicit val intEq: Eq[Int] =
    (x: Int, y: Int) => x == y

  GroupLaws[Int].monoid(Monoid[Int]).all.check()

}

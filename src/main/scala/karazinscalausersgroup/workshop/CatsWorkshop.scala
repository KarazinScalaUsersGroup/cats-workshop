package karazinscalausersgroup.workshop

import cats.kernel.Eq
import cats.kernel.laws.GroupLaws

/**
  * @author Igor Wolkov
  */
object CatsWorkshop extends App {
  import cats.kernel.Monoid
  import cats.syntax.semigroup._

  implicit val multiplicativeMonoid: Monoid[Int] =
    new Monoid[Int] {
      def empty = 1
      def combine(x: Int, y: Int) = x * y
    }

  println(Monoid[Int].combine(3, 4))

  println(3 |+| 4)

  // Check monoid laws:
  // 1. Ints are closed under multiplication
  // 2. Associative law
  // 3. Left and right unit law

  implicit val intEq: Eq[Int] =
    (x: Int, y: Int) => x == y

  GroupLaws[Int].monoid(Monoid[Int]).all.check()
}

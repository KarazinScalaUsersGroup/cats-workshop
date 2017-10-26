package karazinscalausersgroup.workshop

import cats.kernel.Eq
import cats.kernel.laws.GroupLaws

import scala.util.Random

/**
  * @author Igor Wolkov
  */
object CatsWorkshop extends App {
  import cats.kernel.Monoid
  import cats.syntax.semigroup._

  type Flow = String => String

  val unit: Flow = s => s
  val trim: Flow = s => s.trim
  val toUpperCase: Flow = s => s.toUpperCase
  val toLowerCase: Flow = s => s.toUpperCase

  implicit val flowMonoid: Monoid[Flow] =
    new Monoid[Flow] {
      def empty = unit

      def combine(x: Flow, y: Flow) =
        x compose y
    }

  val flow = trim |+| toUpperCase

  println(flow("  hello world  "))


  // Check monoid laws:
  // 1. Flow is closed under composition
  // 2. Associative law
  // 3. Left and right unit law

  // Dirty Eq for laws testing only
  implicit val flowEq: Eq[Flow] =
    (x: Flow, y: Flow) => {

      (1 to 100) forall { _ =>
        val s = Random.nextString(Random.nextInt(100))

        x(s) == y(s)
      }
    }

  GroupLaws[Flow].monoid(Monoid[Flow]).all.check()
}

package karazinscalausersgroup.workshop

/**
  * @author Igor Wolkov
  */
object MaxSemigroup extends App {
  import cats.kernel.{Eq, Semigroup}
  import cats.kernel.laws.GroupLaws
  import cats.syntax.semigroup._


  implicit val maxSemigroup: Semigroup[Int] =
    new Semigroup[Int] {
      override def combine(x: Int, y: Int) =
        Math.max(x, y)
    }

  println(42 |+| 54)
  println(54 |+| 42)

  implicit val intEq: Eq[Int] =
    (x: Int, y: Int) => x == y

  GroupLaws[Int].semigroup(Semigroup[Int]).all.check()

}

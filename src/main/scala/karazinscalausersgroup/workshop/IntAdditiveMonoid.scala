package karazinscalausersgroup.workshop


/**
  * @author Igor Wolkov
  */
object IntAdditiveMonoid extends App {

  import cats.instances.int._
  import cats.kernel.Monoid

  println(Monoid[Int].combine(3, 4))

}

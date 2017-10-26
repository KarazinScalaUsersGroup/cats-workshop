package karazinscalausersgroup.workshop

/**
  * @author Igor Wolkov
  */
object CatsWorkshop extends App {

  import cats.instances.int._
  import cats.instances.string._
  import cats.kernel.Semigroup
  import cats.kernel.Monoid

  // Hello world additive monoid for Strings
  // out of the box
  println(Semigroup[String].combine("Hello ", "world"))

  // Hello world additive monoid for Ints
  // out of the box
  println(Monoid[Int].combine(39, 3))

}

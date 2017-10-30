package karazinscalausersgroup.workshop

import cats.Functor

/**
  * @author Igor Wolkov
  */
object CatsWorkshop extends App {

  implicit val optionFunctor: Functor[Option] = new Functor[Option] {
    def map[A, B](value: Option[A])(func: A => B): Option[B] =
      value.map(func)
  }

  println(Functor[Option].map(Option(42))(_.toString))

}

package karazinscalausersgroup.workshop

import cats.Functor
import cats.instances.list._
import cats.instances.option._

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.{higherKinds, postfixOps}

/**
  * @author Igor Wolkov
  */
object CatsWorkshop extends App {

  // Scala functors out of the box
  println(List(1, 2, 3) map { _ * 2 })
  println(Option(42) map { "'" + _ + "'" })
  println(Await.result(Future("Hello world") map { _.toUpperCase }, 1 second) )

  val func1: Int => String = value => value.toString
  val func2: String => Double = value => value.toDouble
  println((func2 compose func1)(42))


  // Cats functors out of the box
  val list = 1 :: 2 :: 3 :: Nil
  println(Functor[List].map(list)(_ * 2))

  val option = Option(42)
  println(Functor[Option].map(option)(_.toString))

  val func = (x: Int) => x + 1
  val lifted = Functor[Option].lift(func)
  println(lifted(Option(42)))

}

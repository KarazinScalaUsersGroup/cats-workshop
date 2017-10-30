package karazinscalausersgroup.workshop

import cats.instances.future._
import cats.instances.option._
import cats.{Functor, Id}

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.duration._
import scala.language.{higherKinds, postfixOps}

/**
  * @author Igor Wolkov
  */
object CatsWorkshop extends App {

  trait Service[In, Out, C[_]] {
    val mapper: In => Out

    def apply[SC[_]](implicit cFunctor: Functor[C], oFunctor: Functor[SC]): C[SC[In]] => C[SC[Out]] =
      (cFunctor compose oFunctor).lift(mapper)
  }


  case class Incrementer[C[_]]() extends Service[Int, Int, C] {
    val mapper: Int => Int = value => value + 1
  }

  case class Multiplicator[C[_]](coeff: Int) extends Service[Int, Int, C] {
    val mapper: Int => Int = value => value * coeff
  }


  val productionIncrementer = Incrementer[Future]().apply[Option]
  val productionMultiplicator = Multiplicator[Future](coeff = 42).apply[Option]

  val productionFlow = productionIncrementer compose productionMultiplicator
  val productionValue: Future[Option[Int]] = Future.successful(Option(42))
  println(Await.result(productionFlow(productionValue), 1 second))


  val testMultiplicator = Multiplicator[Id](coeff = 42).apply[Option]
  val testIncrementer = Incrementer[Id]().apply[Option]

  val testFlow = testIncrementer compose testMultiplicator
  val testValue: Id[Option[Int]] = Option(3)
  println(testFlow(testValue))

}
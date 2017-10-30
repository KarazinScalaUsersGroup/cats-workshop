package karazinscalausersgroup.workshop

import cats.instances.future._
import cats.instances.option._
import cats.instances.try_._
import cats.{Functor, Id}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits._
import scala.language.{higherKinds, postfixOps}

/**
  * @author Igor Wolkov
  */
object CatsWorkshop extends App {

  trait Service[In, Out, C[_]] {
    val mapper: In => Out

    def apply[SC[_]](c: C[SC[In]])(implicit cFunctor: Functor[C], oFunctor: Functor[SC]): C[SC[Out]] =
      (cFunctor compose oFunctor).map(c)(mapper)
  }


  case class Incrementer[C[_]]() extends Service[Int, Int, C] {
    val mapper: Int => Int = value => value + 1
  }

  case class Multiplicator[C[_]](coeff: Int) extends Service[Int, Int, C] {
    val mapper: Int => Int = value => value * coeff
  }


  val productionIncrementer = new Incrementer[Future]()
  val productionMultiplicator = new Multiplicator[Future](coeff = 42)

  val productionFlow = productionMultiplicator(productionIncrementer(Future.successful(Option(2))))
  println(Await.result(productionFlow, 1 second))


  val testIncrementer = Incrementer[Id]()
  val testMultiplicator = new Multiplicator[Id](coeff = 42)

  val testFlow = testMultiplicator(testIncrementer(Option(2)))
  println(testFlow)


}
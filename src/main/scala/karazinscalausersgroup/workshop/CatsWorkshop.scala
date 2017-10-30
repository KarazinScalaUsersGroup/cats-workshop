package karazinscalausersgroup.workshop

import cats.{Functor, Id}
import cats.instances.future._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.language.{higherKinds, postfixOps}
import scala.concurrent.ExecutionContext.Implicits._

/**
  * @author Igor Wolkov
  */
object CatsWorkshop extends App {

  trait Service[In, Out, C[_]] {
    val mapper: In => Out

    def apply(c: C[In])(implicit functor: Functor[C]): C[Out] =
      functor.map(c)(mapper)
  }

  case class Incrementer[C[_]]() extends Service[Int, Int, C] {
    val mapper: Int => Int = value => value + 1
  }

  case class Multiplicator[C[_]](coeff: Int) extends Service[Int, Int, C] {
    val mapper: Int => Int = value => value * coeff
  }


  val productionIncrementer = Incrementer[Future]()
  val productionMultiplicator = Multiplicator[Future](coeff = 42)

  val productionFlow = productionMultiplicator(productionIncrementer(Future.successful(2)))

  // We have to use ugly Await
  println(Await.result(productionFlow, 1 second))

  // We don't need Future for testing business logic
  val testIncrementer = new Incrementer[Id]
  val testMultiplicator = new Multiplicator[Id](coeff = 42)

  val testFlow: Id[Int] = testIncrementer(testMultiplicator(2))

  println(testFlow)


}
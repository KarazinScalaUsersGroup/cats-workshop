package karazinscalausersgroup.workshop

import cats.{Functor, Id}

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.language.{higherKinds, postfixOps}

/**
  * @author Igor Wolkov
  */
object CatsWorkshop extends App {

  trait Service[In, Out, C[_]] {
    val mapper: In => Option[Out]

    // Future[Option[T]]?
    // I don't want to use pattern matching!
    def apply(c: C[In])(implicit functor: Functor[C]): C[Option[Out]] =
      functor.map(c)(mapper)
  }

  case class Incrementer[C[_]]() extends Service[Int, Int, C] {
    val mapper: Int => Option[Int] = value => Option(value + 1)
  }

  case class Multiplicator[C[_]](coeff: Int) extends Service[Int, Int, C] {
    val mapper: Int => Option[Int] = value => Option(value * coeff)
  }


  val productionIncrementer = Incrementer[Future]()
  val productionMultiplicator = Multiplicator[Future](coeff = 42)

  // Don't work
  // Broke the chain
  // val productionFlow = productionMultiplicator(productionIncrementer(Future.successful(2)))

  val testIncrementer = Incrementer[Id]()
  val testMultiplicator = Multiplicator[Id](coeff = 42)

  // Don't work
  // Broke the chain
  // val testFlow = testMultiplicator(testIncrementer(Future.successful(2)))

}
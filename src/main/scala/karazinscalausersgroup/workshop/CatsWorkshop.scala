package karazinscalausersgroup.workshop

import cats.instances.future._
import cats.instances.option._
import cats.{Functor, Id}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.language.{higherKinds, postfixOps}

/**
  * @author Igor Wolkov
  */
object CatsWorkshop extends App {


  import scala.concurrent.ExecutionContext.Implicits._



  trait Service[In, Out, C[_]] {
    val mapper: In => Out

    // Future[Option[T]]?
    def apply(c: C[Option[In]])(implicit cFunctor: Functor[C], oFunctor: Functor[Option]): C[Option[Out]] =
      (cFunctor compose oFunctor).map(c)(mapper)
  }


  case class Incrementer[C[_]]() extends Service[Int, Int, C] {
    val mapper: Int => Int = value => value + 1
  }

  case class Multiplicator[C[_]](coeff: Int) extends Service[Int, Int, C] {
    val mapper: Int => Int = value => value * coeff
  }


  val productionIncrementer = Incrementer[Future]()
  val productionMultiplicator = Multiplicator[Future](coeff = 42)

  val productionFlow = productionMultiplicator(productionIncrementer(Future.successful(Option(2))))
  println(Await.result(productionFlow, 1 second))

  val testIncrementer = Incrementer[Id]()
  val testMultiplicator = Multiplicator[Id](coeff = 42)

  val testFlow = testMultiplicator(testIncrementer(Option(2)))
  println(testFlow)


}
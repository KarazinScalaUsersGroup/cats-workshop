package karazinscalausersgroup.workshop

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.language.{higherKinds, postfixOps}
import scala.concurrent.ExecutionContext.Implicits._

/**
  * @author Igor Wolkov
  */
object CatsWorkshop extends App {

  trait Service[In, Out] {
    val mapper: In => Out

    def apply(c: Future[In]): Future[Out] =
      c.map(mapper)
  }

  case class Incrementer() extends Service[Int, Int] {
    val mapper: Int => Int = value => value + 1
  }

  case class Multiplicator(coeff: Int) extends Service[Int, Int] {
    val mapper: Int => Int = value => value * coeff
  }

  val productionIncrementer = Incrementer()
  val productionMultiplicator = Multiplicator(coeff = 42)

  val productionFlow = productionMultiplicator(productionIncrementer(Future.successful(2)))

  println(Await.result(productionFlow, 1 second))

}
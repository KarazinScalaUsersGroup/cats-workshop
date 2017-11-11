package karazinscalausersgroup.workshop

/**
  * @author Igor Wolkov
  */
object MonadTransformers extends App {
  import cats.data.{EitherT, OptionT}
  import cats.instances.either._
  import cats.instances.future._
  import cats.instances.option._
  import cats.syntax.applicative._

  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._
  import scala.concurrent.{Await, Future}
  import scala.language.postfixOps


}

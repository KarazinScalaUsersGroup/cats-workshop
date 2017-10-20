package karazinscalausersgroup.workshop

import cats.kernel.{Eq, Semigroup}
import cats.kernel.laws.GroupLaws
import org.scalacheck.Arbitrary

/**
  * @author Igor Wolkov
  */
object StatsSemigroup extends App {

  import cats.syntax.semigroup._
  import cats.instances.int._

  trait Stats {
    val value: Int
    val name: String
  }

  case class UserStats(value: Int) extends Stats {
    val name: String = "user"
  }

  case class LoadStats(value: Int) extends Stats {
    val name: String = "load"
  }

}

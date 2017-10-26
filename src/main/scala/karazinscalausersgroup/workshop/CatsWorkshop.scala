package karazinscalausersgroup.workshop

import cats.kernel.laws.GroupLaws
import cats.kernel.{Eq, Semigroup}
import org.scalacheck.Arbitrary

/**
  * @author Igor Wolkov
  */
object CatsWorkshop extends App {
  import cats.instances.int._
  import cats.syntax.semigroup._

  trait Stats {
    val value: Int
    val name: String
  }

  // Calculate some statistics data about users
  case class UserStats(value: Int) extends Stats {
    val name: String = "user"
  }

  // Calculate some statistics data about loading the system
  case class LoadStats(value: Int) extends Stats {
    val name: String = "load"
  }

  trait Updater[S <: Stats] {
    def update(stats: S, value: Int) : S
    def unit(value: Int): S
  }

  implicit val userStatsUpdater: Updater[UserStats] =
    new Updater[UserStats] {
      def update(stats: UserStats, value: Int) = stats.copy(value)
      def unit(value: Int) = UserStats(value)
    }

  implicit val loadStatsUpdater: Updater[LoadStats] =
    new Updater[LoadStats] {
      def update(stats: LoadStats, value: Int) = stats.copy(value)
      def unit(value: Int) = LoadStats(value)
    }

  implicit def statsSemigroup[S <: Stats](implicit updater: Updater[S]): Semigroup[S] =
    (x: S, y: S) => updater.update(x, Semigroup[Int].combine(x.value, y.value))

  println(
    UserStats(10) |+| UserStats(20)
  )

  println(
    LoadStats(10) |+| LoadStats(20)
  )


  // Check semigroup laws:
  // 1. Stats are closed under addition of values
  // 2. Associative law

  implicit def statsEq[S <: Stats]: Eq[S] =
    (x: S, y: S) => x.name == y.name && x.value == y.value

  // Generate Stats
  implicit def statsArbitrary[S <: Stats: Eq](implicit
                                              gen: Arbitrary[Int],
                                              updater: Updater[S]): Arbitrary[S] =
    Arbitrary {
      for {
        value <- gen.arbitrary
      } yield
        updater.unit(value)
    }

  GroupLaws[UserStats].semigroup(Semigroup[UserStats]).all.check()
  GroupLaws[LoadStats].semigroup(Semigroup[LoadStats]).all.check()

}

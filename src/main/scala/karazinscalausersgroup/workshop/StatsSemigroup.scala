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

  // Not monoid but semigroup!!! Monoid for statistics
  // It's necessary to go through the bad ideas to find the approach!
  //  case class Stats(name: String, value: Int)
  // 1
  //  implicit val statsMonoid1: Monoid[Stats] =
  //    new Monoid[Stats] {
  //      def empty = Stats("", 0)
  //
  //      def combine(x: Stats, y: Stats) =
  //        Stats(???, x.value + y.value)
  //    }

  // 2
  //  trait Stats {
  //    val name: String
  //    val value: Int
  //  }
  //
  //  case class UserStats(value: Int) extends Stats {
  //    val name: String = "user"
  //  }
  //
  //  case class LoadStats(value: Int) extends Stats {
  //    val name: String = "load"
  //  }
  //
  //
  //  implicit val userStatsMonoid: Monoid[UserStats] =
  //    new Monoid[UserStats] {
  //      def empty = UserStats(0)
  //
  //      def combine(x: UserStats, y: UserStats) =
  //        UserStats(x.value + y.value)
  //    }
  //
  //  implicit val loadStatsMonoid: Monoid[LoadStats] =
  //    new Monoid[LoadStats] {
  //      def empty = LoadStats(0)
  //
  //      def combine(x: LoadStats, y: LoadStats) =
  //        LoadStats(x.value + y.value)
  //    }

  // 3
  //  trait Stats {
  //    val value: Int
  //    val name: String
  //  }
  //
  //  case class UserStats(value: Int) extends Stats {
  //    val name: String = "user"
  //  }
  //
  //  case class LoadStats(value: Int) extends Stats {
  //    val name: String = "load"
  //  }
  //
  //  implicit def statsMonoid[S <: Stats]: Monoid[S] =
  //    new Monoid[S] {
  //      def empty = S(0) ???
  //
  //      def combine(x: S, y: S) =
  //        S(x.value + y.value)
  //    }

  // 4
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

  //  implicit def statsSemigroup[S <: Stats { def copy(value: Int) : S}]: Semigroup[S] =
  //    new Semigroup[S] {
  //      def combine(x: S, y: S): S =
  //        x.copy(Semigroup[Int].combine(x.value, y.value))
  //    }
  //
  //
  //  println(
  //    UserStats(10) |+| UserStats(20)
  //  )
  //
  //  // Don't work, and it's good
  ////  println(
  ////    UserStats(10) |+| LoadStats(20)
  ////  )

  // 5
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
    new Semigroup[S] {
      def combine(x: S, y: S): S =
        updater.update(x, Semigroup[Int].combine(x.value, y.value))
    }

  println(
    UserStats(10) |+| UserStats(20)
  )

  implicit def statsEq[S <: Stats]: Eq[S] =
    new Eq[S] {
      def eqv(x: S, y: S) =
        x.name == y.name && x.value == y.value
    }

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

package karazinscalausersgroup.workshop

/**
  * @author Igor Wolkov
  */
object RockPaperScissorsMagma extends App {

  import cats.syntax.semigroup._
  import cats.kernel.{Eq, Semigroup}
  import cats.kernel.laws.GroupLaws
  import org.scalacheck.Arbitrary

  trait Game
  case object Rock extends Game
  case object Paper extends Game
  case object Scissors extends Game

  implicit val flowSemigroup: Semigroup[Game] =
    new Semigroup[Game] {
      override def combine(x: Game, y: Game) =
        x match {
          case Rock     => if (y == Paper)    Paper    else Rock
          case Paper    => if (y == Scissors) Scissors else Paper
          case Scissors => if (y == Rock)     Rock     else Scissors
        }
    }

  println( (Rock: Game) |+| (Paper: Game) )
  println( (Paper: Game) |+| (Scissors: Game) )
  println( (Scissors: Game) |+| (Rock: Game) )

  implicit val gameEq: Eq[Game] =
    new Eq[Game] {
      def eqv(x: Game, y: Game) = x == y
    }

  implicit def gameArbitrary(implicit arbitraryInt: Arbitrary[Int]): Arbitrary[Game] =
    Arbitrary {
      for {
        int <- arbitraryInt.arbitrary
        if int > 0
      } yield {
        int % 3 match {
          case 0 => Rock
          case 1 => Paper
          case 2 => Scissors
        }
      }
    }

  val rock: Game = Rock
  val paper: Game = Paper
  val scissors: Game = Scissors

  GroupLaws[Game].semigroup(Semigroup[Game]).all.check()
}

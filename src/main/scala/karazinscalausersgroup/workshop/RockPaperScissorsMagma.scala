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

}

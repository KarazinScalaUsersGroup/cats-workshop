package karazinscalausersgroup.workshop

import cats.kernel.Eq
import cats.kernel.laws.GroupLaws

import scala.util.Random

/**
  * @author Igor Wolkov
  */
object FlowMonoid extends App {
  import cats.kernel.Monoid
  import cats.syntax.semigroup._


  type Flow = String => String

  val unit: Flow = s => s
  val trim: Flow = s => s.trim
  val toUpperCase: Flow = s => s.toUpperCase
  val toLowerCase: Flow = s => s.toUpperCase


}

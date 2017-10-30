package karazinscalausersgroup.workshop

import cats.functor.Contravariant
import cats.Show

import scala.language.{higherKinds, postfixOps}

/**
  * @author Igor Wolkov
  */
object CatsWorkshop extends App {

  // We need just types
  // to check contravariance
  trait Json
  trait User

  val json = new Json {}
  val user = new User {}
  val userToJson: User => Json = _ => json

  implicit val showJson: Show[Json] =
    Show.show(_ => "{}")

  println(Show[Json].show(json))

  implicit val userShow: Show[User] =
    Contravariant[Show].contramap(showJson)(userToJson)

  println(Show[User].show(user))

}
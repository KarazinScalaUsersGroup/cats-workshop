package karazinscalausersgroup.workshop

import cats.kernel.Semigroup

/**
  * @author Igor Wolkov
  */
object IncarantionSemigroup extends App {

  case class Data(incarnation: Int, payload: String)

  implicit val dataSemigroup: Semigroup[Data] =
    (x: Data, y: Data) =>
      if (x.incarnation > y.incarnation) x
      else                               y

  println(Semigroup[Data].combine(Data(1, "Hello"), Data(2, "world")))




}

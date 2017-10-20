

libraryDependencies ++= {
  val shapelessV = "2.3.2"
  val catsV      = "1.0.0-MF"
  val scalacheckV = "1.13.4"

  Seq(
    "org.typelevel"     %% "cats-core"      % catsV,
    "org.typelevel"     %% "cats-laws"      % catsV,
    "org.scalacheck"    %% "scalacheck"     % scalacheckV     % "test"

  )
}


libraryDependencies ++= {
  val catsV       = "1.0.0-RC1"
  val scalacheckV = "1.13.4"

  Seq(
    "org.typelevel"     %% "cats-core"      % catsV,
    "org.typelevel"     %% "cats-laws"      % catsV,
    "org.scalacheck"    %% "scalacheck"     % scalacheckV     % "test"

  )
}
ThisBuild / name := "http4s-server"
ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

val http4sVersion = "0.23.18"
val circeVersion = "0.14.5"
val catsEffectVersion = "3.4.9"

lazy val root = (project in file("."))
  .settings(
    name := "EngelMess",
    idePackagePrefix := Some("ru.vampa.engelmess"),
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-core" % http4sVersion,
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-ember-server" % http4sVersion,
      "org.http4s" %% "http4s-circe" % http4sVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "org.typelevel" %% "cats-effect" % catsEffectVersion
    )
  )

addCompilerPlugin("org.typelevel" % "kind-projector_2.13.2" % "0.13.2")
addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")

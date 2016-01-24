name := "macros_examples"

version := "1.0"

scalaVersion := "2.11.7"

lazy val macros_implementations = project

lazy val root = (project in file(".")).aggregate(macros_implementations).dependsOn(macros_implementations)

val paradiseVersion = "2.1.0-M5"

addCompilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.full)

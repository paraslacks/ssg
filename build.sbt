ThisBuild / scalaVersion := "2.13.8"

ThisBuild / organization := "jellon"

ThisBuild / version := "0.1.0-SNAPSHOT"

enablePlugins(Antlr4Plugin)

lazy val root = (project in file("."))
  .aggregate()
  .settings(
    name := "ssg"
  )

lazy val lookup = (project in file("./util/lookup"))
  .settings(
    name := "lookup",
    libraryDependencies ++= Dependencies.testLibs
  )

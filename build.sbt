ThisBuild / scalaVersion := "2.13.8"

ThisBuild / organization := "jellon"

ThisBuild / version := "0.1.0-SNAPSHOT"

enablePlugins(Antlr4Plugin)

lazy val root = (project in file("."))
  .aggregate()
  .settings(
    name := "ssg"
  )

import DependencyVersions._
import sbt._

object Dependencies {
  val slf4j: Seq[ModuleID] = Seq(
    "org.slf4j" % "slf4j-api" % slf4j_version,
    "org.slf4j" % "slf4j-simple" % slf4j_version,
    "org.clapper" %% "grizzled-slf4j" % "1.3.4"
  )

  val dependencyInjection: Seq[ModuleID] = Seq(
    "org.springframework" % "spring-context" % spring_version,
    "javax.inject" % "javax.inject" % inject_version,
    "javax.inject" % "javax.inject-tck" % inject_version
  )

  val guice: Seq[ModuleID] = Seq("com.google.inject" % "guice" % "5.1.0")

  val jackson: Seq[ModuleID] = Seq(
    "com.fasterxml.jackson.core" % "jackson-core" % jackson_version,
    "com.fasterxml.jackson.core" % "jackson-databind" % jackson_version
  )

  val yamlSnake: Seq[ModuleID] = Seq("org.yaml" % "snakeyaml" % "1.30")

  val antlr: Seq[ModuleID] = Seq("org.antlr" % "antlr4-runtime" % "4.10.1")
  val stringTemplate4: Seq[ModuleID] = Seq("org.antlr" % "ST4" % "4.3.3")
  val velocity: Seq[ModuleID] = Seq("org.apache.velocity" % "velocity-engine-core" % "2.3")

  // TEST
  val testLibs: Seq[ModuleID] = Seq(
    "org.scalatest" %% "scalatest" % "3.2.12" % Test
  )
}

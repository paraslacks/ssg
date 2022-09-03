ThisBuild / scalaVersion := "2.13.8"

ThisBuild / organization := "jellon"

ThisBuild / version := "0.0.1-SNAPSHOT"

enablePlugins(Antlr4Plugin)

lazy val root = (project in file("."))
  .aggregate(ioApi, ioSpi)
  .settings(
    name := "ssg"
  )

lazy val ioApi = (project in file("io/api"))
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "io.api",
    Compile / sourceGenerators += Def.task {
      val io: File = (Compile / Keys.sourceManaged).value / "jellon/ssg/io"
      val apiLoader: File = new File(io, "api/loader")
      val apiResource: File = new File(io, "api/resource")
      val implLoader: File = new File(io, "spi/loader")
      val implResource: File = new File(io, "spi/resource")
      val model: File = (Compile / Keys.sourceDirectory).value / "bootstrap/model.yaml"
      val templates: File = (Compile / Keys.sourceDirectory).value / "bootstrap/io-api-main.stg"
      streams.value.log.info(s"Generating file: IResource")
      val files: Seq[File] = BootStrapCodeGen.forEachInModel(apiLoader, templates, "interface", model, className => s"I$className") ++ Seq(
        BootStrapCodeGen.forModel(apiLoader, templates, "IResourceLoader", model),

        BootStrapCodeGen.forModel(apiResource, templates, "IResource", model),

        BootStrapCodeGen.forModel(implLoader, templates, "AggregateResourceLoader", model),
        BootStrapCodeGen.forModel(implLoader, templates, "BaseResourceLoaderAdapter", model),
        BootStrapCodeGen.forModel(implLoader, templates, "FilterResourceLoader", model),
        BootStrapCodeGen.forModel(implLoader, templates, "PrefixedResourceLoader", model),

        BootStrapCodeGen.forModel(implResource, templates, "ResourceAdapter", model),
        BootStrapCodeGen.forModel(implResource, templates, "ResourceLoaderResource", model),
      )

      streams.value.log.info(s"Generated files: $files")
      files
    }.taskValue,
    Compile / sourceGenerators += Def.task {
      val io: File = (Test / Keys.sourceManaged).value / "jellon/ssg/io"
      val apiLoader: File = new File(io, "api/loader")
      val apiResource: File = new File(io, "api/resource")
      val implLoader: File = new File(io, "spi/loader")
      val implResource: File = new File(io, "spi/resource")
      val model: File = (Compile / Keys.sourceDirectory).value / "bootstrap/model.yaml"
      val templates: File = (Compile / Keys.sourceDirectory).value / "bootstrap/io-api-test.stg"
      streams.value.log.info(s"Generating file: IResource")
      val files: Seq[File] = BootStrapCodeGen.forEachInModel(apiLoader, templates, "interface", model, modelName => s"$modelName", className => s"I${className}Tests.scala") ++ Seq(
        //        IoApiGen.forModel(apiLoader, model, templates, "IResourceLoader"),
        //
        //        IoApiGen.forModel(apiResource, model, templates, "IResource"),
        //
        //        IoApiGen.forModel(implLoader, model, templates, "AggregateResourceLoader"),
        //        IoApiGen.forModel(implLoader, model, templates, "BaseResourceLoaderAdapter"),
        //        IoApiGen.forModel(implLoader, model, templates, "FilterResourceLoader"),
        //        IoApiGen.forModel(implLoader, model, templates, "PrefixedResourceLoader"),
        //
        //        IoApiGen.forModel(implResource, model, templates, "ResourceAdapter"),
        //        IoApiGen.forModel(implResource, model, templates, "ResourceLoaderResource"),
      )

      streams.value.log.info(s"Generated files: $files")
      files
    }.taskValue,
    libraryDependencies ++= Dependencies.slf4j
      ++ Dependencies.testLibs,
  )

lazy val ioSpi = (project in file("io/impl"))
  .dependsOn(ioApi)
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "io.impl",
    libraryDependencies ++= Dependencies.slf4j,
  )

lazy val ioUnitTest = (project in file("io/unit-test"))
  .dependsOn(ioApi, ioSpi)
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "io.unit-test",
    libraryDependencies ++= Dependencies.testLibs,
  )

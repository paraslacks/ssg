import java.io.File

/** Use StringTemplate4 to generate source code for SSG */
object BootStrapCodeGen {
  import ST4Helper._
  import YamlHelper._

  type ClassNameFunction = String => String
  type FileNameFunction = String => String

  val scalaClassNameFunction: ClassNameFunction = className => className
  val scalaTraitNameFunction: ClassNameFunction = className => s"I$className"

  val scalaClassFileNameFunction: FileNameFunction = className => s"$className.scala"
  val scalaTraitFileNameFunction: ClassNameFunction = className => s"I$className.class"

  def forEachInModel(rootOutputDir: File, templateFile: File, templateName: String, modelFile: File, classNameFunc: ClassNameFunction, fileNameFunc: FileNameFunction): Seq[File] = {
    var result: List[File] = List.empty[File]

    val st = groupOf(templateFile)
    foreachInYaml(modelFile, (key, model) => {
      val className = classNameFunc(key)
      val fileName = fileNameFunc(className)

      val outputFile = st.getConfiguredTemplate(templateName, className, model)
        .generate(rootOutputDir, fileName)

      result = outputFile +: result
    })

    result
  }

  def forEachInModel(rootOutputDir: File, templateFile: File, templateName: String, modelFile: File, classNameFunc: ClassNameFunction): Seq[File] =
    forEachInModel(rootOutputDir, templateFile, templateName, modelFile, classNameFunc, scalaClassFileNameFunction)

  def forEachInModel(rootOutputDir: File, templateFile: File, templateName: String, modelFile: File): Seq[File] =
    forEachInModel(rootOutputDir, templateFile, templateName, modelFile, scalaClassNameFunction)

  def forModel(rootOutputDir: File, templateFile: File, templateName: String, modelFile: File, className: String, outputFileName: String): File = {
    val models = parseModel(modelFile)
    generate(
      templateFile = templateFile,
      templateName = templateName,
      className = className,
      models = models,
      rootOutputDir = rootOutputDir,
      outputFileName = outputFileName,
    )
  }

  def forModel(rootOutputDir: File, templateFile: File, templateName: String, modelFile: File, classNameFunc: ClassNameFunction, fileNameFunc: FileNameFunction): File =
    forModel(
      rootOutputDir = rootOutputDir,
      templateFile = templateFile,
      templateName = templateName,
      modelFile = modelFile,
      className = classNameFunc(templateName),
      outputFileName = fileNameFunc(templateName),
    )

  def forModel(rootOutputDir: File, templateFile: File, templateName: String, modelFile: File): File =
    forModel(
      rootOutputDir = rootOutputDir,
      templateFile = templateFile,
      templateName = templateName,
      modelFile = modelFile,
      classNameFunc = scalaClassNameFunction,
      fileNameFunc = scalaClassFileNameFunction,
    )
}

import YamlHelper.YamlMap
import org.stringtemplate.v4.{ST, _}
import org.stringtemplate.v4.misc.ErrorManager

import java.io.File

object ST4Helper {
  def groupOf(templateFile: File): STGroupFile =
    new STGroupFile(templateFile.toURI.toURL)

  def getConfiguredTemplate(templateFile: File, templateName: String, namedValues: Map[String, AnyRef]): ST =
    groupOf(templateFile)
      .getConfiguredTemplate(templateName, namedValues)

  def generate(templateFile: File, templateName: String, namedValues: Map[String, AnyRef], rootOutputDir: File, outputFileName: String): File =
    getConfiguredTemplate(templateFile, templateName, namedValues)
      .generate(rootOutputDir, outputFileName)

  def getConfiguredTemplate(templateFile: File, templateName: String, className: String, model: AnyRef): ST =
    groupOf(templateFile)
      .getConfiguredTemplate(templateName, className, model)

  def generate(templateFile: File, templateName: String, className: String, model: AnyRef, rootOutputDir: File, outputFileName: String): File =
    getConfiguredTemplate(templateFile, templateName, className, model)
      .generate(rootOutputDir, outputFileName)

  def getConfiguredTemplate(templateFile: File, templateName: String, className: String, models: YamlMap): ST =
    groupOf(templateFile)
      .getConfiguredTemplate(templateName, className, models)

  def generate(templateFile: File, templateName: String, className: String, models: YamlMap, rootOutputDir: File, outputFileName: String): File =
    getConfiguredTemplate(templateFile, templateName, className, models)
      .generate(rootOutputDir, outputFileName)

  private def assertFileWritable(file: File): File = {
    file.getParentFile.mkdirs()
    if (!file.getParentFile.isDirectory) {
      throw new IllegalStateException(s"unable to create directory path to: ${file.getAbsolutePath}")
    }

    file.createNewFile()
    if (!file.canWrite) {
      throw new IllegalStateException(s"unable to write: ${file.getAbsolutePath}")
    }

    file
  }

  implicit class STGroupFileExt(self: STGroupFile) {
    def getConfiguredTemplate(templateName: String, namedValues: Map[String, AnyRef]): ST = self
      .getInstanceOf(templateName)
      .addAll(namedValues)

    def getConfiguredTemplate(templateName: String, className: String, model: AnyRef): ST =
      getConfiguredTemplate(templateName, Map[String, AnyRef](
        "ClassName" -> className,
        "Model" -> model,
      ))

    def getConfiguredTemplate(templateName: String, className: String, models: YamlMap): ST =
      getConfiguredTemplate(templateName, Map[String, AnyRef](
        "ClassName" -> className,
        "ModelNames" -> models.keySet(),
        "Models" -> models.values(),
      ))
  }

  implicit class STExt(self: ST) {
    def addAll(namedValues: Map[String, AnyRef]): ST =
      namedValues.foldLeft(self)(
        (st, kv) => st.add(kv._1, kv._2)
      )

    def generate(outputFile: File): File = {
      self.write(outputFile, ErrorManager.DEFAULT_ERROR_LISTENER)
      outputFile
    }

    def generate(baseFile: File, pathAndName: String): File =
      generate(assertFileWritable(
        new File(baseFile, pathAndName)
      ))
  }
}

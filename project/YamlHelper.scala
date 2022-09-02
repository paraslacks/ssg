import org.yaml.snakeyaml.Yaml

import java.io.{File, FileInputStream}
import scala.collection.JavaConverters._

object YamlHelper {
  type YamlMap = java.util.Map[String, Object]

  implicit class YamlMapExt(self: YamlMap) {
    def foreach(f: (String, Object) => Unit): Unit = {
      for (key: String <- self.keySet().asScala) {
        f(key, self.get(key))
      }
    }
  }

  def parseModel(modelFile: File): YamlMap = {
    val fis = new FileInputStream(modelFile)
    try {
      new Yaml()
        .loadAs[YamlMap](fis, classOf[YamlMap])
    } finally {
      fis.close()
    }
  }

  def foreachInYaml(modelFile: File, f: (String, Object) => Unit): Unit =
    parseModel(modelFile)
      .foreach(f)
}

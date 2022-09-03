package jellon.ssg.io.impl.loader

import FileSystemResourceLoader.{fileSeparator, notUnix}
import jellon.ssg.io.spi.loader.BaseResourceLoaderAdapter

import java.io.{File, FileInputStream, FileOutputStream, InputStream, OutputStream}
import java.net.{URI, URL}
import scala.io.{BufferedSource, Source}
import scala.reflect.ClassTag

class FileSystemResourceLoader(val baseDir: File) extends BaseResourceLoaderAdapter {
  def this(pathname: String) = this(new File(pathname))

  override protected def tryOptSource(id: String, hint: String): Option[Source] = this
    .tryOptBufferedSource(id, hint)

  override protected def tryOptBufferedSource(id: String, hint: String): Option[BufferedSource] = this
    .tryOptFile(id, hint)
    .map(
      Source.fromFile(_)
    )

  override protected def tryOptFile(id: String, hint: String): Option[File] = {
    val localPath: String =
      if (notUnix && id.contains('/')) id.replace("/", fileSeparator)
      else id

    Option(new File(baseDir, localPath))
      .filter(
        _.exists
      )
  }

  override protected def tryOptUri(id: String, hint: String): Option[URI] = this
    .tryOptFile(id, hint)
    .map(
      _.toURI
    )

  override protected def tryOptUrl(id: String, hint: String): Option[URL] = this
    .tryOptUri(id, hint)
    .map(
      _.toURL
    )

  override protected def tryOptInputStream(id: String, hint: String): Option[InputStream] = this
    .tryOptFile(id, hint)
    .filter(
      file => file.isFile && file.canRead
    )
    .map(
      new FileInputStream(_)
    )

  override protected def tryOptOutputStream(id: String, hint: String): Option[OutputStream] = this
    .tryOptFile(id, hint)
    .filter(
      file => file.isFile && file.canWrite
    )
    .map(
      new FileOutputStream(_)
    )
}

object FileSystemResourceLoader extends FileSystemResourceLoader(".") {
  val fileSeparator: String = System.getProperty("file.separator")

  val notUnix: Boolean = "/" != fileSeparator

  def relativeResource(path: String, resource: String): String =
    s"$path$resource"

  def relativeResource(clz: Class[_], resource: String): String =
    relativeResource(s"${clz.getPackage.getName.replace(".", fileSeparator)}$fileSeparator", resource)

  def relativeResourceOf[A: ClassTag](resource: String): String =
    relativeResource(implicitly[ClassTag[A]].runtimeClass, resource)

  def relativeResource(self: AnyRef, resource: String): String = self match {
    case path: String => relativeResource(path, resource)
    case clz: Class[_] => relativeResource(clz, resource)
    case _ => relativeResource(self.getClass, resource)
  }
}

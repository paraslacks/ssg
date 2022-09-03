package jellon.ssg.io.impl.loader

import jellon.ssg.io.api.loader.IResourceLoader
import jellon.ssg.io.spi.loader.BaseResourceLoaderAdapter

import java.io.{InputStream, OutputStream}
import java.net.{URI, URL}
import scala.io.Source
import scala.reflect.ClassTag

class ClassLoaderResourceLoader(protected val classLoader: ClassLoader) extends BaseResourceLoaderAdapter {
  override protected def tryOptSource(id: String, hint: String): Option[Source] = this
    .optUrl(id, hint)
    .map(Source.fromURL)

  override protected def tryOptUrl(id: String, hint: String): Option[URL] =
    Option(classLoader.getResource(id))

  override protected def tryOptUri(id: String, hint: String): Option[URI] = this
    .tryOptUrl(id, hint)
    .map(_.toURI)

  override protected def tryOptInputStream(id: String, hint: String): Option[InputStream] =
    Option(classLoader.getResourceAsStream(id))

  override protected def tryOptOutputStream(id: String, hint: String): Option[OutputStream] = this
    .optUrl(id)
    .map(
      _.openConnection()
    )
    .map(
      _.getOutputStream
    )
}

object ClassLoaderResourceLoader {
  @`inline`
  def apply(classLoader: ClassLoader): IResourceLoader =
    new ClassLoaderResourceLoader(classLoader)

  @`inline`
  def apply(clz: Class[_]): IResourceLoader = this
    .apply(clz.getClassLoader)

  @`inline`
  def of[A: ClassTag]: IResourceLoader = this
    .apply(implicitly[ClassTag[A]].runtimeClass.asInstanceOf[Class[A]])

  lazy val optSystemResourceLoader: Option[IResourceLoader] =
    Option(ClassLoader.getSystemClassLoader)
      .map(
        new ClassLoaderResourceLoader(_)
      )

  lazy val systemResourceLoader = this
    .optSystemResourceLoader
    .getOrElse {
      throw new UnsupportedOperationException("SystemClassLoader is null")
    }
}

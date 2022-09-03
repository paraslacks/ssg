package jellon.ssg.io.impl.loader

import jellon.ssg.io.spi.loader.BaseResourceLoaderAdapter

import java.io.InputStream
import java.net.{URI, URL}
import scala.io.Source

object SystemResourceLoader extends BaseResourceLoaderAdapter {
  override protected def tryOptSource(id: String, hint: String): Option[Source] = this
    .optUrl(id, hint)
    .map(Source.fromURL)

  override protected def tryOptUrl(id: String, hint: String): Option[URL] =
    Option(ClassLoader.getSystemResource(id))

  override protected def tryOptUri(id: String, hint: String): Option[URI] = this
    .tryOptUrl(id, hint)
    .map(_.toURI)

  override protected def tryOptInputStream(id: String, hint: String): Option[InputStream] =
    Option(ClassLoader.getSystemResourceAsStream(id))
}

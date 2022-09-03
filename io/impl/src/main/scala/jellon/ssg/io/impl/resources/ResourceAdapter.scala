package jellon.ssg.io.impl.resources

import jellon.ssg.io.api.resource.IResource

import java.io.{File, InputStream, OutputStream}
import java.net.{URI, URL}
import scala.io.{BufferedSource, Source}

/**
 * Adapter pattern. Implements all [[jellon.ssg.io.api.resource.IResource]] methods with [[scala.Option#empty()]]
 *
 * @param id   information about where this resource came from
 * @param hint optional metadata such as a MIME type which should default to the empty string when unavailable
 */
abstract class ResourceAdapter(override val id: String, override val hint: String) extends IResource {
  override def optSource: Option[Source] = Option.empty

  override def optBufferedSource: Option[BufferedSource] = Option.empty

  override def optFile: Option[File] = Option.empty

  override def optUri: Option[URI] = Option.empty

  override def optUrl: Option[URL] = Option.empty

  override def optInputStream: Option[InputStream] = Option.empty

  override def optOutputStream: Option[OutputStream] = Option.empty
}

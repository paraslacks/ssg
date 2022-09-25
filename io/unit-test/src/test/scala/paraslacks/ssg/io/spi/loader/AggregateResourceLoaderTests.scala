package paraslacks.ssg.io.spi.loader

import paraslacks.ssg.io.api.loader.{IBufferedSourceLoader, defaultHint}
import org.scalatest.flatspec.AnyFlatSpec

import scala.io.BufferedSource

class AggregateResourceLoaderTests extends AnyFlatSpec {
  private val subject: IBufferedSourceLoader = new IBufferedSourceLoader() {
    def optBufferedSource(id: String, hint: String): Option[BufferedSource] = Option.empty
  }

  def test_optBufferedSource__string_string() = {
    // Given

    // When
    val actual = subject.optBufferedSource("", defaultHint)

    // Then
    assert(actual.isEmpty)
  }

  def test_ext_optBufferedSource__string() = {
    // Given

    // When
    val actual = subject.optBufferedSource("")

    // Then
    assert(actual.isEmpty)
  }

  def test_ext_toBufferedSource__string_string() = {
    // Given

    // Then
    assertThrows[UnsupportedOperationException] {
      // When
      subject.toBufferedSource("", defaultHint)
    }
  }

  def test_ext_toBufferedSource__string() = {
    // Given

    // Then
    assertThrows[UnsupportedOperationException](() => {
      // When
      subject.toBufferedSource("")
    })
  }
}

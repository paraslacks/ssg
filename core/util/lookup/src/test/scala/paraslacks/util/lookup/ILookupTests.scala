package paraslacks.util.lookup

import org.scalatest.funspec.AnyFunSpec

class ILookupTests extends AnyFunSpec {
  val subject: ILookup = new ILookup() {
    override def add[A](`type`: Class[A], implementation: A): ILookup = ???

    override def addAll(values: Seq[(Class[_], Seq[_])]): ILookup = ???

    val stringValues = Seq("foo", "bar")

    /**
     * @return all added instances of the given type
     */
    override def getAll[A](`type`: Class[A]): Seq[A] =
      if (classOf[String] == `type`) stringValues.asInstanceOf[Seq[A]]
      else Seq.empty[A]
  }

  describe("ILookup.opt[A](Class[A] type)") {
    it("should return None by default") {
      assertResult(Option.empty)(subject.opt(classOf[Void]))
    }

    it("should return the first instance from getAll") {
      assertResult(Option("foo"))(subject.opt(classOf[String]))
    }
  }
}

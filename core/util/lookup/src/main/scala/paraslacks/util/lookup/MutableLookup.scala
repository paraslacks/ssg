package paraslacks.util.lookup

class MutableLookup private(var elements: Map[Class[_], Seq[_]]) extends ILookup {
  def this() = this(Map.empty[Class[_], Seq[_]])

  override def add[A](`type`: Class[A], implementation: A): ILookup = {
    elements = elements + (`type` -> Seq(implementation))
    this
  }

  override def addAll(values: Seq[(Class[_], Seq[_])]): ILookup = {
    elements = elements ++ enforceTypes(values)
    this
  }

  override def getAll[A](`type`: Class[A]): Seq[A] = this
    .elements
    .getOrElse(`type`, Seq.empty)
    .asInstanceOf[Seq[A]]
}

object MutableLookup {
  def apply(): MutableLookup =
    new MutableLookup(Map.empty[Class[_], Seq[_]])

  def apply[A](`type`: Class[A], implementation: A): MutableLookup =
    new MutableLookup(
      Map[Class[_], Seq[_]](
        `type` -> Seq(implementation)
      )
    )

  def apply(values: Map[Class[_], Seq[_]]): MutableLookup =
    new MutableLookup(Map(enforceTypes(values.toSeq): _*))
}

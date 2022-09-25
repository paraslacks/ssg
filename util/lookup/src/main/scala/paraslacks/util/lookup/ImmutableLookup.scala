package paraslacks.util.lookup

class ImmutableLookup private(elements: Map[Class[_], Seq[_]]) extends ILookup {
  def this() = this(Map.empty[Class[_], Seq[_]])

  override def add[A](`type`: Class[A], implementation: A): ILookup =
    new ImmutableLookup(elements + (`type` -> Seq(implementation)))

  override def addAll(values: Seq[(Class[_], Seq[_])]): ILookup =
    new ImmutableLookup(elements ++ values)

  override def getAll[A](`type`: Class[A]): Seq[A] = this
    .elements
    .getOrElse(`type`, Seq.empty)
    .asInstanceOf[Seq[A]]
}

object ImmutableLookup {
  def apply(): ImmutableLookup =
    new ImmutableLookup(Map.empty[Class[_], Seq[_]])

  def apply[A](`type`: Class[A], implementation: A): ImmutableLookup =
    new ImmutableLookup(
      Map[Class[_], Seq[_]](
        `type` -> Seq(implementation)
      )
    )

  def apply(values: Map[Class[_], Seq[_]]): ImmutableLookup =
    new ImmutableLookup(Map(enforceTypes(values.toSeq): _*))
}

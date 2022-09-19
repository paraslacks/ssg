package jellon.util.lookup

import scala.reflect.ClassTag

object Lookup {
  def apply(): ILookup =
    ImmutableLookup()

  def apply[A](`type`: Class[A], implementation: A): ILookup =
    ImmutableLookup(`type`, implementation)

  def apply(values: Map[Class[_], Seq[_]]): ILookup =
    ImmutableLookup(values)

  implicit class ILookupExt(self: ILookup) {
    def add[A: ClassTag](implementation: A): ILookup = self
      .add(implicitly[ClassTag[A]].runtimeClass.asInstanceOf[Class[A]], implementation)

    def addAll(values: Map[Class[_], Seq[_]]): ILookup = self
      .addAll(values.toSeq)

    def opt[A: ClassTag]: Option[A] = self
      .opt(implicitly[ClassTag[A]].runtimeClass.asInstanceOf[Class[A]])

    def get[A: ClassTag]: Seq[A] = self
      .getAll(implicitly[ClassTag[A]].runtimeClass.asInstanceOf[Class[A]])
  }
}

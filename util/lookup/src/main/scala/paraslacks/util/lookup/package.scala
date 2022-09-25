package paraslacks.util

package object lookup {
  private[lookup] def enforceTypes(values: Seq[(Class[_], Seq[_])]): Seq[(Class[_], Seq[_])] = values
    .map(
      entry => (entry._1, entry._2.filter(entry._1.isInstance))
    )
}

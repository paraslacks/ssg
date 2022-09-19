package jellon.util.lookup;

import scala.Option;
import scala.Tuple2;
import scala.collection.immutable.Seq;

import java.util.NoSuchElementException;

public interface ILookup {
    <A> ILookup add(Class<A> type, A implementation);

    ILookup addAll(Seq<Tuple2<Class<?>, Seq<?>>> values);

    /**
     * @return an instance of the given type (order is not guaranteed)
     */
    default <A> Option<A> opt(Class<A> type) {
        return getAll(type).headOption();
    }

    /**
     * @return an instance of the given type (order is not guaranteed)
     * @throws NoSuchElementException if no instances were added
     */
    default <A> A get(Class<A> type) throws NoSuchElementException {
        return getAll(type)
                .head();
    }

    /**
     * @return all added instances of the given type
     */
    <A> Seq<A> getAll(Class<A> type);
}

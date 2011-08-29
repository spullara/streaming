package streaming;

import java.util.Iterator;

public abstract class Stream<T> {
  public abstract T next();

  // Map/reduce methods

  public Stream<T> f(final P<T> p) {
    return new Stream<T>() {
      @Override
      public T next() {
        T n;
        while ((n = Stream.this.next()) != null) {
          if (p.p(n)) return n;
        }
        return null;
      }
    };
  }

  public <V> Stream<V> t(final F<T, V> f) {
    return new Stream<V>() {
      @Override
      public V next() {
        T n = Stream.this.next();
        return n == null ? null : f.f(n);
      }
    };
  }

  public <V> V foldLeft(V v, F2<T, V, V> f) {
    T n;
    while ((n = next()) != null) {
      v = f.f(n, v);
    }
    return v;
  }

  public <V> V foldRight(V v, F2<T, V, V> f) {
    T n = next();
    return n == null ? v : f.f(n, foldRight(v, f));
  }

  // From methods

  public static <T> Stream<T> from(Iterable<T> iterable) {
    return from(iterable.iterator());
  }

  public static <T> Stream<T> from(final Iterator<T> iterator) {
    return new Stream<T>() {
      @Override
      public T next() {
        return iterator.hasNext() ? iterator.next() : null;
      }
    };
  }

  @SafeVarargs
  public static <T> Stream<T> from(final T... array) {
    return new Stream<T>() {
      int i = 0;
      @Override
      public T next() {
        return array.length == i ? null : array[i++];
      }
    };
  }
}

package streaming;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;

public class StreamTest {
  @Test
  public void testFilter() {
    Stream<Integer> s = Stream.from(asList(1, 2, 3)).f(new P<Integer>() {
      public boolean p(Integer i) {
        return i != 2;
      }
    });
    assertTrue(next(s, 1));
    assertTrue(next(s, 3));
  }

  @Test
  public void testFromVarargs() {
    Stream<Integer> s = Stream.from(1, 2, 3).f(new P<Integer>() {
      public boolean p(Integer i) {
        return i != 2;
      }
    });
    assertTrue(next(s, 1));
    assertTrue(next(s, 3));
  }

  @Test
  public void testTransform() {
    Stream<Integer> t = Stream.from(1, 2, 3).t(new F<Integer, Integer>() {
      @Override
      public Integer f(Integer i) {
        return i * 2;
      }
    });
    assertTrue(next(t, 2));
    assertTrue(next(t, 4));
    assertTrue(next(t, 6));
  }

  @Test
  public void testFoldLeft() {
    assertTrue(6 == Stream.from(1, 2, 3).foldLeft(0, new F2<Integer, Integer, Integer>() {
      @Override
      public Integer f(Integer integer, Integer integer1) {
        return integer + integer1;
      }
    }));
  }

  @Test
  public void testFoldRight() {
    assertTrue(0 == Stream.from(1, 2, 3).foldRight(6, new F2<Integer, Integer, Integer>() {
      @Override
      public Integer f(Integer integer, Integer integer1) {
        return integer1 - integer;
      }
    }));
  }

  public static <T> boolean next(Stream<T> s, T t) {
    return t.equals(s.next());
  }
}

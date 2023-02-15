package queue;

import java.util.Arrays;
import java.util.Objects;

public class ArrayQueueADT {
  private static final int DEFAULT_CAPACITY = 10;
  private Object[] array;
  private int head, tail, size;

  public ArrayQueueADT() {
    array = new Object[DEFAULT_CAPACITY];
  }

  public ArrayQueueADT(int capacity) {
    array = new Object[capacity];
  }

  // Pred: element != null && arrayQueueADT != null
  // Post: n' == n + 1 && a[n] == element && Immutable
  public static void enqueue(final ArrayQueueADT arrayQueueADT, final Object element) {
    Objects.requireNonNull(arrayQueueADT);
    Objects.requireNonNull(element);
    if (filledUp(arrayQueueADT)) {
      ensureCapacity(arrayQueueADT);
    }
    arrayQueueADT.array[arrayQueueADT.tail++ % arrayQueueADT.array.length] = element;
    ++arrayQueueADT.size;
  }

  // Pred: n > 0 && arrayQueueADT != null
  // Post: result == a[n] && Immutable
  public static Object element(final ArrayQueueADT arrayQueueADT) {
    Objects.requireNonNull(arrayQueueADT);
    assert arrayQueueADT.size > 0;
    return arrayQueueADT.array[arrayQueueADT.head % arrayQueueADT.array.length];
  }

  // Pred: n > 0 && arrayQueueADT != null
  // Post: result == a[n] && n' == n - 1 && Immutable
  public static Object dequeue(final ArrayQueueADT arrayQueueADT) {
    Objects.requireNonNull(arrayQueueADT);
    assert arrayQueueADT.size > 0;
    --arrayQueueADT.size;
    final int MOD = arrayQueueADT.array.length;
    final Object result = arrayQueueADT.array[arrayQueueADT.head % MOD];
    arrayQueueADT.array[arrayQueueADT.head % MOD] = null;
    arrayQueueADT.head = (arrayQueueADT.head + 1) % MOD;
    return result;
  }

  // Pred: arrayQueueADT != null
  // Post: result == n && Immutable
  public static int size(final ArrayQueueADT arrayQueueADT) {
    Objects.requireNonNull(arrayQueueADT);
    return arrayQueueADT.size;
  }

  // Pred: arrayQueueADT != null
  // Post: result == (n == 0) && Immutable
  public static boolean isEmpty(final ArrayQueueADT arrayQueueADT) {
    Objects.requireNonNull(arrayQueueADT);
    return arrayQueueADT.size == 0;
  }

  // Pred: arrayQueueADT != null
  // Post: n == 0
  public static void clear(final ArrayQueueADT arrayQueueADT) {
    Objects.requireNonNull(arrayQueueADT);
    Arrays.fill(arrayQueueADT.array, null);
    arrayQueueADT.array = new Object[DEFAULT_CAPACITY];
    arrayQueueADT.head = arrayQueueADT.tail = arrayQueueADT.size = 0;
  }

  private static boolean filledUp(final ArrayQueueADT arrayQueueADT) {
    Objects.requireNonNull(arrayQueueADT);
    return arrayQueueADT.size == arrayQueueADT.array.length;
  }

  private static void ensureCapacity(final ArrayQueueADT arrayQueueADT) {
    Objects.requireNonNull(arrayQueueADT);
    final int MOD = arrayQueueADT.array.length;
    final Object[] newElements = new Object[MOD << 1];
    for (int i = 0; i < MOD; i++) {
      newElements[i] = arrayQueueADT.array[arrayQueueADT.head++ % MOD];
    }
    arrayQueueADT.head = 0;
    arrayQueueADT.tail = MOD;
    arrayQueueADT.array = newElements;
  }
}

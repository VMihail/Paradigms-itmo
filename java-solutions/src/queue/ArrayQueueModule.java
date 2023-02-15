package queue;

import java.util.Arrays;
import java.util.Objects;

public class ArrayQueueModule {
  private static final int DEFAULT_CAPACITY = 10;
  private static boolean created;
  private static Object[] array = new Object[DEFAULT_CAPACITY];
  private static int head, tail, size;

  private ArrayQueueModule() {
    // pass
  }

  // Pred: capacity >= 0 && not created
  // Post: Immutable && created' = not created
  public static void init(int capacity) {
    if (created) {
      throw new IllegalStateException("Queue already created");
    }
    array = new Object[capacity];
    created = true;
  }

  // Pred: element != null
  // Post: n' == n + 1 && a[n] == element && Immutable
  public static void enqueue(Object element) {
    Objects.requireNonNull(element);
    if (filledUp()) {
      ensureCapacity();
    }
    array[tail++ % array.length] = element;
    ++size;
  }

  // Pred: n > 0
  // Post: result == a[n] && Immutable
  public static Object element() {
    assert size > 0;
    return array[head % array.length];
  }

  // Pred: n > 0
  // Post: result == a[n] && n' == n - 1 && Immutable
  public static Object dequeue() {
    assert size > 0;
    --size;
    final int MOD = array.length;
    final Object result = array[head % MOD];
    array[head % MOD] = null;
    head = (head + 1) % MOD;
    return result;
  }

  // Pred: true
  // Post: result == n && Immutable
  public static int size() {
    return size;
  }

  // Pred: true
  // Post: result == (n == 0) && Immutable
  public static boolean isEmpty() {
    return size == 0;
  }

  // Pred: true
  // Post: n == 0
  public static void clear() {
    created = false;
    Arrays.fill(array, null);
    array = new Object[DEFAULT_CAPACITY];
    head = tail = size = 0;
  }

  private static boolean filledUp() {
    return size == array.length;
  }

  private static void ensureCapacity() {
    final int MOD = array.length;
    final Object[] newElements = new Object[MOD << 1];
    for (int i = 0; i < MOD; i++) {
      newElements[i] = array[head++ % MOD];
    }
    head = 0;
    tail = MOD;
    array = newElements;
  }
}

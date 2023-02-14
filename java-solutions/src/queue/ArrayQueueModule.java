package queue;

import java.util.Objects;

public class ArrayQueueModule {
  private static final int DEFAULT_CAPACITY = 10;
  private static Object[] elements;
  private static int head, tail, size;

  static {
    elements = new Object[DEFAULT_CAPACITY];
  }

  public static int indexOf(Object element) {
    Objects.requireNonNull(element);
    int saveHead = head;
    for (int i = 0; i < size; i++) {
      if (elements[saveHead++ % elements.length].equals(element)) {
        return i;
      }
    }
    return -1;
  }

  public static int lastIndexOf(Object element) {
    Objects.requireNonNull(element);
    int result = -1;
    int saveHead = head;
    for (int i = 0; i < size; i++) {
      if (elements[saveHead++ % elements.length].equals(element)) {
        result = i;
      }
    }
    return result;
  }

  public static void enqueue(Object element) {
    Objects.requireNonNull(element);
    ensureCapacity();
    elements[tail++ % elements.length] = element;
    ++size;
  }

  public static Object element() {
    assert size > 0;
    return elements[head % elements.length];
  }

  public static Object dequeue() {
    assert size > 0;
    --size;
    final int MOD = elements.length;
    Object result = elements[head % MOD];
    elements[head % MOD] = null;
    head = (head + 1) % MOD;
    return result;
  }

  public static int size() {
    return size;
  }

  public static boolean isEmpty() {
    return size == 0;
  }

  public static void clear() {
    elements = new Object[DEFAULT_CAPACITY];
    head = tail = size = 0;
  }

  private static void ensureCapacity() {
    final int MOD = elements.length;
    if (size == MOD) {
      Object[] newElements = new Object[MOD << 1];
      for (int i = 0; i < MOD; i++) {
        newElements[i] = elements[head++ % MOD];
      }
      head = 0;
      tail = MOD;
      elements = newElements;
    }
  }
}

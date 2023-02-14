package queue;

import java.util.Objects;

public class ArrayQueueADT {
  private Object[] elements;
  private int head, tail, size;
  private static final int DEFAULT_CAPACITY = 10;

  public ArrayQueueADT() {
    elements = new Object[DEFAULT_CAPACITY];
  }

  public static void enqueue(final ArrayQueueADT queue, final Object element) {
    Objects.requireNonNull(element);
    ensureCapacity(queue);
    queue.elements[queue.tail++ % queue.elements.length] = element;
    ++queue.size;
  }

  public static Object element(final ArrayQueueADT queue) {
    assert queue.size > 0;
    return queue.elements[queue.head % queue.elements.length];
  }

  public static Object dequeue(final ArrayQueueADT queue) {
    assert queue.size > 0;
    --queue.size;
    final int MOD = queue.elements.length;
    Object result = queue.elements[queue.head % MOD];
    queue.elements[queue.head % MOD] = null;
    queue.head = (queue.head + 1) % MOD;
    return result;
  }

  public static int size(final ArrayQueueADT queue) {
    return queue.size;
  }

  public static boolean isEmpty(final ArrayQueueADT queue) {
    return queue.size == 0;
  }

  public static void clear(final ArrayQueueADT queue) {
    queue.elements = new Object[DEFAULT_CAPACITY];
    queue.head = queue.tail = queue.size = 0;
  }

  public static int indexOf(final ArrayQueueADT queue, Object element) {
    Objects.requireNonNull(element);
    int saveHead = queue.head;
    for (int i = 0; i < queue.size; i++) {
      if (queue.elements[saveHead++ % queue.elements.length].equals(element)) {
        return i;
      }
    }
    return -1;
  }

  public static int lastIndexOf(final ArrayQueueADT queue, Object element) {
    Objects.requireNonNull(element);
    int result = -1;
    int saveHead = queue.head;
    for (int i = 0; i < queue.size; i++) {
      if (queue.elements[saveHead++ % queue.elements.length].equals(element)) {
        result = i;
      }
    }
    return result;
  }

  private static void ensureCapacity(final ArrayQueueADT queue) {
    final int MOD = queue.elements.length;
    if (queue.size == MOD) {
      Object[] newElements = new Object[MOD << 1];
      for (int i = 0; i < MOD; i++) {
        newElements[i] = queue.elements[queue.head++ % MOD];
      }
      queue.head = 0;
      queue.tail = MOD;
      queue.elements = newElements;
    }
  }
}

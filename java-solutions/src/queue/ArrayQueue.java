package queue;

import java.util.Arrays;
import java.util.Objects;

public class ArrayQueue {
  private static final int DEFAULT_CAPACITY = 10;
  private  Object[] array;
  private  int head, tail, size;

  public ArrayQueue() {
    array = new Object[DEFAULT_CAPACITY];
  }

  public ArrayQueue(int capacity) {
    array = new Object[capacity];
  }

  public void enqueue(Object element) {
    Objects.requireNonNull(element);
    if (filledUp()) {
      ensureCapacity();
    }
    array[tail++ % array.length] = element;
    ++size;
  }

  public Object element() {
    assert size > 0;
    return array[head % array.length];
  }

  public Object dequeue() {
    assert size > 0;
    --size;
    final int MOD = array.length;
    final Object result = array[head % MOD];
    array[head % MOD] = null;
    head = (head + 1) % MOD;
    return result;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public void clear() {
    Arrays.fill(array, null);
    array = new Object[DEFAULT_CAPACITY];
    head = tail = size = 0;
  }

  private boolean filledUp() {
    return size == array.length;
  }

  private void ensureCapacity() {
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

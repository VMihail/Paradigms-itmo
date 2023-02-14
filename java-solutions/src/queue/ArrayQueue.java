package queue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.function.Predicate;

public class ArrayQueue extends AbstractQueue {
  private Object[] elements;
  private int head, tail;
  private static final int startLen = 10;

  private void expand() {
    final int MOD = elements.length;
    if (size == MOD) {
      Object[] newElements = new Object[2 * MOD];
      for (int i = 0; i < MOD; i++) {
        newElements[i] = elements[head++ % MOD];
      }
      head = 0;
      tail = MOD;
      elements = newElements;
    }
  }

  public ArrayQueue() {
    elements = new Object[startLen];
  }

  private ArrayQueue(Object[] elements, int head, int tail, int size) {
    this.elements = elements;
    this.head = head;
    this.tail = tail;
    super.size = size;
  }

  public int indexOf(Object element) {
    Objects.requireNonNull(element);
    int saveHead = head;
    for (int i = 0; i < size; i++) {
      if (elements[saveHead++ % elements.length].equals(element)) {
        return i;
      }
    }
    return -1;
  }

  public int lastIndexOf(Object element) {
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

  @Override
  protected void enqueueImpl(Object element) {
    expand();
    elements[tail++ % elements.length] = element;
  }

  @Override
  protected Object elementImpl() {
    return elements[head % elements.length];
  }

  @Override
  protected Object dequeueImpl() {
    final int MOD = elements.length;
    Object result = elements[head % MOD];
    elements[head % MOD] = null;
    head = (head + 1) % MOD;
    return result;
  }

  @Override
  protected void clearImpl() {
    elements = new Object[startLen];
    head = tail = 0;
  }

  private ArrayQueue copy() {
    Object[] array = new Object[elements.length];
    System.arraycopy(elements, 0, array, 0, elements.length);
    return new ArrayQueue(array, head, tail, size);
  }

  @Override
  protected int indexIfImpl(Predicate<Object> predicate) {
    // :NOTE: inefficient ~version of indexOf
    Queue queue = copy();
    int index = 0;
    while (!queue.isEmpty()) {
      Object remove = queue.dequeue();
      if (predicate.test(remove)) {
        return index;
      }
      ++index;
    }
    return -1;
  }

  @Override
  protected int lastIndexIfImpl(Predicate<Object> predicate) {
    Deque<Object> stack = new ArrayDeque<>();
    Queue queue = copy();
    while (!queue.isEmpty()) {
      stack.push(queue.dequeue());
    }
    int index = stack.size() - 1;
    while (!stack.isEmpty()) {
      Object remove = stack.pop();
      if (predicate.test(remove)) {
        return index;
      }
      --index;
    }
    return -1;
  }
}

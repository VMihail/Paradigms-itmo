package queue;

public class ArrayQueue extends AbstractQueue {
  private static final int DEFAULT_CAPACITY = 10;
  private Object[] array;
  private int head, tail;

  public ArrayQueue() {
    array = new Object[DEFAULT_CAPACITY];
  }

  public ArrayQueue(int capacity) {
    array = new Object[capacity];
  }

  @Override
  protected void enqueueImpl(Object element) {
    if (filledUp()) {
      ensureCapacity();
    }
    array[tail++ % array.length] = element;
    ++size;
  }

  @Override
  protected Object elementImpl() {
    return array[head % array.length];
  }

  @Override
  protected Object dequeueImpl() {
    final int MOD = array.length;
    final Object result = array[head % MOD];
    array[head % MOD] = null;
    head = (head + 1) % MOD;
    return result;
  }

  @Override
  protected void clearImpl() {
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

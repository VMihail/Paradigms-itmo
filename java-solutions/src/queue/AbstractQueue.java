package queue;

import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractQueue implements Queue {
  protected int size;

  @Override
  public void enqueue(final Object element) {
    Objects.requireNonNull(element);
    enqueueImpl(element);
  }

  @Override
  public Object element() {
    assert size > 0;
    return elementImpl();
  }

  @Override
  public Object dequeue() {
    assert size > 0;
    --size;
    return dequeueImpl();
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public void clear() {
    Arrays.fill(toArray(), null);
    clearImpl();
  }

  @Override
  public Object[] toArray() {
    final Object[] result = new Object[size];
    for (int i = 0; i < size; i++) {
      final Object nextElement = dequeue();
      result[i] = nextElement;
      enqueue(nextElement);
    }
    return result;
  }

  protected abstract void enqueueImpl(Object element);

  protected abstract Object elementImpl();

  protected abstract Object dequeueImpl();

  protected abstract void clearImpl();
}


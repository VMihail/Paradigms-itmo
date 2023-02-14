package queue;

import java.util.Objects;
import java.util.function.Predicate;

public abstract class AbstractQueue implements Queue {
  protected int size;

  @Override
  public void enqueue(Object element) {
    Objects.requireNonNull(element);
    enqueueImpl(element);
    ++size;
  }

  @Override
  public Object dequeue() {
    assert size > 0;
    --size;
    return dequeueImpl();
  }

  @Override
  public Object element() {
    assert size > 0;
    return elementImpl();
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
    size = 0;
    clearImpl();
  }

  @Override
  public int indexIf(Predicate<Object> predicate) {
    Objects.requireNonNull(predicate);
    return indexIfImpl(predicate);
  }

  @Override
  public int lastIndexIf(Predicate<Object> predicate) {
    Objects.requireNonNull(predicate);
    return lastIndexIfImpl(predicate);
  }

  protected abstract void enqueueImpl(Object element);

  protected abstract Object elementImpl();

  protected abstract Object dequeueImpl();

  protected abstract void clearImpl();

  protected abstract int indexIfImpl(Predicate<Object> predicate);

  protected abstract int lastIndexIfImpl(Predicate<Object> predicate);
}


package queue;

import java.util.Objects;

/**
 * @author VMihail (vmihail399@gmail.com)
 * created: 15.02.2023 22:19
 */
public class LinkedQueue extends AbstractQueue {
  private static class Node {
    private final Object element;
    private Node next;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Node node = (Node) o;
      return Objects.equals(element, node.element) && Objects.equals(next, node.next);
    }

    @Override
    public int hashCode() {
      return Objects.hash(element, next);
    }

    private Node(Object element, Node next) {
      this.element = element;
      this.next = next;
    }

    public void setNext(Node next) {
      this.next = next;
    }

    public Object getElement() {
      return element;
    }

    public Node getNext() {
      return next;
    }
  }

  private Node first, last;

  @Override
  protected void enqueueImpl(final Object element) {
    if (isEmpty()) {
      ++size;
      first = new Node(element, null);
      last = first;
      return;
    }
    Node nextNode = new Node(element, null);
    last.next = nextNode;
    last = nextNode;
    ++size;
  }

  @Override
  protected Object elementImpl() {
    return first.element;
  }

  @Override
  protected Object dequeueImpl() {
    final Object result = first.element;
    first = size == 0 ? last : first.next;
    return result;
  }

  @Override
  protected void clearImpl() {
    first = last;
  }
}

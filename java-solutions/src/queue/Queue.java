package queue;

import java.util.function.Predicate;

/*
 * enqueue – добавить элемент в очередь;
 * element – первый элемент в очереди;
 * dequeue – удалить и вернуть первый элемент в очереди;
 * size – текущий размер очереди;
 * isEmpty – является ли очередь пустой;
 * clear – удалить все элементы из очереди.
 *
 * n - старое значение, n' - новое значение
 *
 * Model:
 * [a1, a2, ... , an]
 * n - длина очереди
 * Inv:
 * n >= 0
 * forall i = 1..n: a[i] != null
 * Immutable: n' == n && forall i = 1..n: a[i] == a'[i]
 */
public interface Queue {
  /*
   * Pred: element != null
   * Post: n' = n + 1 && a[n'] == e && Immutable
   */
  void enqueue(Object element);

  /*
   * Pred: n > 0
   * Post: Result = a[1] && Immutable
   */
  Object element();

  /*
   * Pred: n > 0
   * Post: Result = a[1] && n' = n - 1 && Immutable
   */
  Object dequeue();

  /*
   * Pred: true
   * Post: Result = n && Immutable
   */
  int size();

  /*
   * Pred: true
   * Post: Result = (n == 0) && Immutable
   */
  boolean isEmpty();

  /*
   * Pred: true
   * Post: n' = 0 && forall i in 1..n : a[i] == null
   */
  void clear();

  Object[] toArray();
}

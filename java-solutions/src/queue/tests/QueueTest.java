package queue.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import queue.*;

/**
 * @author VMihail (vmihail399@gmail.com)
 * created: 15.02.2023 20:44
 */
public class QueueTest {
  @Test
  public void arrayQueueModuleTest() {
    ArrayQueueModule.init(20);
    final int size = 25;
    for (int i = 0; i < size; i++) {
      ArrayQueueModule.enqueue(i);
    }
    Assertions.assertEquals(size, ArrayQueueModule.size());
    Assertions.assertThrows(IllegalStateException.class, () -> ArrayQueueModule.init(4));
    while (!ArrayQueueModule.isEmpty()) {
      Assertions.assertEquals(ArrayQueueModule.element(), ArrayQueueModule.dequeue());
    }
    Assertions.assertTrue(ArrayQueueModule.isEmpty());
    ArrayQueueModule.clear();
    Assertions.assertDoesNotThrow(() -> ArrayQueueModule.init(20));
  }

  @Test
  public void ArrayQueueADTTest() {
    final int size = 25;
    final ArrayQueueADT arrayQueueADT = new ArrayQueueADT();
    for (int i = 0; i < size; i++) {
      ArrayQueueADT.enqueue(arrayQueueADT, i);
    }
    Assertions.assertEquals(size, ArrayQueueADT.size(arrayQueueADT));
    while (!ArrayQueueADT.isEmpty(arrayQueueADT)) {
      Assertions.assertEquals(ArrayQueueADT.element(arrayQueueADT), ArrayQueueADT.dequeue(arrayQueueADT));
    }
    Assertions.assertTrue(ArrayQueueADT.isEmpty(arrayQueueADT));
    ArrayQueueADT.clear(arrayQueueADT);
  }

  @Test
  public void ArrayQueueTest() {
    final int size = 25;
    final Queue arrayQueue = new ArrayQueue();
    for (int i = 0; i < size; i++) {
      arrayQueue.enqueue(i);
    }
    Assertions.assertEquals(size, arrayQueue.size());
    while (!arrayQueue.isEmpty()) {
      Assertions.assertEquals(arrayQueue.element(), arrayQueue.dequeue());
    }
    Assertions.assertTrue(arrayQueue.isEmpty());
    arrayQueue.clear();
  }

  @Test
  public void LinkedQueueTest() {
    final int size = 25;
    final Queue arrayQueue = new LinkedQueue();
    for (int i = 0; i < size; i++) {
      arrayQueue.enqueue(i);
    }
    Assertions.assertEquals(size, arrayQueue.size());
    while (!arrayQueue.isEmpty()) {
      Assertions.assertEquals(arrayQueue.element(), arrayQueue.dequeue());
    }
    Assertions.assertTrue(arrayQueue.isEmpty());
    arrayQueue.clear();
  }
}

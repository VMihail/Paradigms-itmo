package search;

public class BinarySearch {
  // Pred: forall i in [1, array.length - 1] : array[i - 1] >= array[i]
  // Post: (array[right] <= key and array[right] -> min) or (right == array.length if
  // forall i in [0, array.length - 1] : array[i] > key)
  public static int find(int[] array, int key) {
    int left = -1, right = array.length;
    // Inv: array[left] > key and array[right] <= key and (right + left) / 2 in [0, array.length - 1]
    while (right > left + 1) {
      int middle = Utils.getMiddle(left, right);
      // middle in [left, right] and result in [left, right]
      if (array[middle] > key) {
        // array[left] > key
        // left' = middle
        left = middle;
      } else {
        // array[right] <= key
        // right' = middle
        right = middle;
      }
    }
    // Inv: array[left] > key and array[right] <= key
    return right;
  }

  // Pred: forall i in [1, array.length - 1] : array[i - 1] >= array[i]
  // Post: (array[right] <= key and array[right] -> min) or (right == array.length if
  // forall i in [0, array.length - 1] : array[i] > key)
  public static int find(int[] array, int key, int left, int right) {
    // Inv: array[left] > key and array[right] <= key and (right + left) / 2 in [0, array.length - 1]
    if (right <= left + 1) {
      return right;
      // Inv: array[left] > key and array[right] <= key
    }
    int middle = Utils.getMiddle(left, right);;
    // middle in [left, right] and result in [left, right]
    if (array[middle] > key) {
      // array[left] > key
      // left' = middle
      left = middle;
    } else {
      // array[right] <= key
      // right' = middle
      right = middle;
    }
    // Inv: array[left] > key and array[right] <= key
    return find(array, key, left, right);
  }

  public static void main(String[] args) {
    int[] array = new int[args.length - 1];
    for (int i = 1; i < args.length; i++) {
      try {
        array[i - 1] = Integer.parseInt(args[i]);
      } catch (NumberFormatException e) {
        System.out.println("Invalid data format\nan ascending sorted array of integers expected");
      }
    }
    System.out.println(find(array, Integer.parseInt(args[0]), -1, array.length));
  }
}


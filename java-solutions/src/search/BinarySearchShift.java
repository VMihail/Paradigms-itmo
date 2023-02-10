package search;

public class BinarySearchShift {
  // Pred: существует такое j in [1; array.length - 1] : a[j] <= a[j - 1] &&
  // для любого i != j in [0; array.length - 2] : a[i] <= a[i + 1]
  // Post: right == min(array)
  public static int leftSearchShiftedSortedArray(int[] array) {
    int left = -1, right = array.length;
    // Inv: min(array) in (left; right)
    while (right > left + 1) {
      int middle = (right - left) / 2 + left;
      // middle in (left, right) && min(array) in (left, right)
      if (array[middle] > array[array.length - 1]) {
        // middle in (left, right) && min(array) in (left, right) && left in (left, right)
        left = middle;
        // left' == middle
      } else {
        // middle in (l, r) && min(array) in (l, r) && right in (l, r)
        right = middle;
        // right' == middle
      }
    }
    // Inv: min(array) == right' && right' in (left; right)
    return right;
  }

  // Pred: существует такое j in [1; array.length - 1] : a[j] <= a[j - 1] &&
  // для любого i != j in [0; array.length - 2] : a[i] <= a[i + 1]
  // Post: right == min(array)
  public static int recLeftSearchShiftedSortedArray(int[] array, int left, int right) {
    // Inv: min(array) in (left; right)
    if (right - left <= 1) {
      // Inv: min(array) == right' && min(array) in (left; right) => left' + 1 == right' == min(array)
      return right;
    }
    int middle = (right - left) / 2 + left;
    // middle in (left; right) && min(array) in (left; right)
    if (array[middle] > array[array.length - 1]) {
      // left' == middle
      return recLeftSearchShiftedSortedArray(array, middle, right);
    }
    // middle in (l, r) && min(array) in (l, r) && right in (l, r)
    return recLeftSearchShiftedSortedArray(array, left, middle);
    // right' == middle
  }

  public static void main(String[] args) {
    int[] array = new int[args.length];
    for (int i = 0; i < args.length; i++) {
      try {
        array[i] = Integer.parseInt(args[i]);
      } catch (NumberFormatException e) {
        System.out.println("Invalid data format\nan ascending sorted array of integers expected");
      }
    }
    System.out.println(leftSearchShiftedSortedArray(array));
  }

}

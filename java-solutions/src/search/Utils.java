package search;

public final class Utils {
  // Pred: left >= 0 and right >= 0 and left + right <= Integer.MAX_VALUE
  // Post: result == (left + right) / 2
  public static int getMiddle(int left, int right) {
    return ((right - left) >> 1) + left;
  }
}

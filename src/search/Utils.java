package search;

public final class Utils {
  public static int getMiddle(int left, int right) {
    return ((right - left) >> 1) + left;
  }
}

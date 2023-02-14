package expression.interfaces;

/**
 * @author VMihail (vmihail399@gmail.com)
 * created: 14.02.2023 13:14
 */
public interface Expression {
  int evaluate(int value);

  @Override
  String toString();
}

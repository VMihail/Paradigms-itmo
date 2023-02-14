package expression.operands;

import expression.interfaces.Expression;

/**
 * @author VMihail (vmihail399@gmail.com)
 * created: 14.02.2023 13:23
 */
public class Const implements Expression {
  private final int value;

  public Const(int value) {
    this.value = value;
  }

  @Override
  public int evaluate(int value) {
    return this.value;
  }

  @Override
  public String toString() {
    return Integer.toString(value);
  }
}

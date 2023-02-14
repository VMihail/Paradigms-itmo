package expression.operands;

import expression.interfaces.Expression;

/**
 * @author VMihail (vmihail399@gmail.com)
 * created: 14.02.2023 13:22
 */
public class Subtract extends AbstractOperand {
  private static final String OPERATOR = "-";

  public Subtract(Expression left, Expression right) {
    super(left, right, OPERATOR);
  }

  @Override
  public int evaluate(int value) {
    return left.evaluate(value) - right.evaluate(value);
  }
}

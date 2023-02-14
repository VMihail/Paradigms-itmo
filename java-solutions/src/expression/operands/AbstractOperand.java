package expression.operands;

import expression.interfaces.Expression;

/**
 * @author VMihail (vmihail399@gmail.com)
 * created: 14.02.2023 13:18
 */
public abstract class AbstractOperand implements Expression {
  protected final Expression left;
  protected final Expression right;
  protected final String operation;

  protected AbstractOperand(Expression left, Expression right, String operation) {
    this.left = left;
    this.right = right;
    this.operation = operation;
  }

  @Override
  public String toString() {
    return "(" + left + " " + operation + " " + right + ")";
  }
}

package expression.operands;

import expression.interfaces.Expression;

/**
 * @author VMihail (vmihail399@gmail.com)
 * created: 14.02.2023 13:27
 */
public class Variable implements Expression {
  private final String name;

  public Variable(String name) {
    this.name = name;
  }

  @Override
  public int evaluate(int value) {
    return value;
  }

  @Override
  public String toString() {
    return name;
  }
}

package expression.tests;

import expression.operands.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author VMihail (vmihail399@gmail.com)
 * created: 14.02.2023 13:24
 */
public class MainTest {
  @Test
  public void evaluateTest() {
    int result = new Subtract(
      new Multiply(
        new Const(2),
        new Variable("x")
      ),
      new Const(3)
    ).evaluate(5);
    Assertions.assertEquals(result, 7);
  }

  @Test
  public void toStringTest() {
    String result = new Subtract(
      new Multiply(
        new Const(2),
        new Variable("x")
      ),
      new Const(3)
    ).toString();
    Assertions.assertEquals(result, "((2 * x) - 3)");
  }
}

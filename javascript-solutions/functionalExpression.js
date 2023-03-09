"use strict";

    const cnst = (val) => () => val;

    const pi = cnst(Math.PI);
    const e = cnst(Math.E);

    const variable = (name) => (...values) => {
        if (name === "x") {
            return values[0];
        } else if (name === "y") {
            return values[1];
        } else if (name === "z") {
            return values[2];
        }
        return undefined;
    };

    const create = (operation) => {
        let result = (...operands) => (...argv) => operation(...operands.map((expr) => expr(...argv)));
        result.size = operation.length;
        return result;
    }

    const add = create((a, b) => a + b);
    const subtract = create((a, b) => a - b);
    const multiply = create((a, b) => a * b);
    const divide = create((a, b) => a / b);
    const negate = create((a) => -a);
    const sinh = create(Math.sinh);
    const cosh = create(Math.cosh)

    const expr = add(
        subtract(multiply(
                variable("x"),
                variable("x")), multiply(
                    cnst(2),
                    variable("x")
            )
        ),
        cnst(1)
    );
    /*const expr1 = cosh(e);
    alert(expr1());*/

    for (let i = 0; i < 10; i++) {
        println(expr(i));
    }

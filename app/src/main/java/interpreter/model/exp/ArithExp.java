package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public record ArithExp(ArithOp op, IExp left, IExp right) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        IValue leftValue = left.eval(symTable);
        if (!(leftValue instanceof IntValue first)) {
            throw new ExpressionException("first operand is not an integer");
        }

        IValue rightValue = right.eval(symTable);
        if (!(rightValue instanceof IntValue second)) {
            throw new ExpressionException("second operand is not an integer");
        }

        return switch (op) {
            case ADD -> new IntValue(first.val() + second.val());
            case SUB -> new IntValue(first.val() - second.val());
            case MUL -> new IntValue(first.val() * second.val());
            case DIV -> {
                if (second.val() == 0) {
                    throw new ExpressionException("division by zero");
                }
                yield new IntValue(first.val() / second.val());
            }
        };
    }

    @Override
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }
}

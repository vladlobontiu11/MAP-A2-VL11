package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record LogicExp(LogicOp op, IExp left, IExp right) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        IValue leftValue = left.eval(symTable);
        if (!(leftValue instanceof BoolValue first)) {
            throw new ExpressionException("first operand is not a boolean");
        }

        IValue rightValue = right.eval(symTable);
        if (!(rightValue instanceof BoolValue second)) {
            throw new ExpressionException("second operand is not a boolean");
        }

        return switch (op) {
            case AND -> new BoolValue(first.val() && second.val());
            case OR -> new BoolValue(first.val() || second.val());
        };
    }

    @Override
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }
}

package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public final class LogicExp implements IExp {
    private final LogicOp op;
    private final IExp left;
    private final IExp right;

    public LogicExp(LogicOp op, IExp left, IExp right) {
        if (op == null || left == null || right == null) {
            throw new NullPointerException("Logic operation and expressions cannot be null");
        }
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public LogicOp getOp() {
        return op;
    }

    public LogicOp op() {
        return op;
    }

    public IExp getLeft() {
        return left;
    }

    public IExp left() {
        return left;
    }

    public IExp getRight() {
        return right;
    }

    public IExp right() {
        return right;
    }

    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        IValue leftValue = left.eval(symTable);
        if (!(leftValue instanceof BoolValue)) {
            throw new ExpressionException("first operand is not a boolean");
        }
        BoolValue leftBoolean = (BoolValue) leftValue;

        IValue rightValue = right.eval(symTable);
        if (!(rightValue instanceof BoolValue)) {
            throw new ExpressionException("second operand is not a boolean");
        }
        BoolValue rightBoolean = (BoolValue) rightValue;

        if (op == LogicOp.AND) {
            return new BoolValue(leftBoolean.getVal() && rightBoolean.getVal());
        } else if (op == LogicOp.OR) {
            return new BoolValue(leftBoolean.getVal() || rightBoolean.getVal());
        }
        throw new ExpressionException("unknown logic operation");
    }

    @Override
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LogicExp)) {
            return false;
        }
        LogicExp otherExpression = (LogicExp) other;
        return op == otherExpression.op
                && left.equals(otherExpression.left)
                && right.equals(otherExpression.right);
    }

    @Override
    public int hashCode() {
        int result = op.hashCode();
        result = 31 * result + left.hashCode();
        result = 31 * result + right.hashCode();
        return result;
    }
}

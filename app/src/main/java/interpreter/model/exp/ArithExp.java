package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public final class ArithExp implements IExp {
    private final ArithOp op;
    private final IExp left;
    private final IExp right;

    public ArithExp(ArithOp op, IExp left, IExp right) {
        if (op == null || left == null || right == null) {
            throw new NullPointerException("Arithmetic operation and expressions cannot be null");
        }
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public ArithOp getOp() {
        return op;
    }

    public ArithOp op() {
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
        if (!(leftValue instanceof IntValue)) {
            throw new ExpressionException("first operand is not an integer");
        }
        IntValue leftNumber = (IntValue) leftValue;

        IValue rightValue = right.eval(symTable);
        if (!(rightValue instanceof IntValue)) {
            throw new ExpressionException("second operand is not an integer");
        }
        IntValue rightNumber = (IntValue) rightValue;

        if (op == ArithOp.ADD) {
            return new IntValue(leftNumber.getVal() + rightNumber.getVal());
        } else if (op == ArithOp.SUB) {
            return new IntValue(leftNumber.getVal() - rightNumber.getVal());
        } else if (op == ArithOp.MUL) {
            return new IntValue(leftNumber.getVal() * rightNumber.getVal());
        } else if (op == ArithOp.DIV) {
            if (rightNumber.getVal() == 0) {
                throw new ExpressionException("division by zero");
            }
            return new IntValue(leftNumber.getVal() / rightNumber.getVal());
        }
        throw new ExpressionException("unknown arithmetic operation");
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
        if (!(other instanceof ArithExp)) {
            return false;
        }
        ArithExp otherExpression = (ArithExp) other;
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

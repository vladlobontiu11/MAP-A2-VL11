package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExpressionTest {
    private final IDictionary<String, IValue> symTable = new MyDictionary<>();

    private static IExp number(int value) {
        return new ValueExp(new IntValue(value));
    }

    private static IExp bool(boolean value) {
        return new ValueExp(new BoolValue(value));
    }

    @Test
    void arithmeticOperatorsEvaluateCorrectly() throws Exception {
        assertEquals(new IntValue(7), new ArithExp(ArithOp.ADD, number(3), number(4)).eval(symTable));
        assertEquals(new IntValue(1), new ArithExp(ArithOp.SUB, number(4), number(3)).eval(symTable));
        assertEquals(new IntValue(12), new ArithExp(ArithOp.MUL, number(3), number(4)).eval(symTable));
        assertEquals(new IntValue(2), new ArithExp(ArithOp.DIV, number(8), number(4)).eval(symTable));
    }

    @Test
    void divisionByZeroThrows() {
        ExpressionException exception = assertThrows(ExpressionException.class,
                () -> new ArithExp(ArithOp.DIV, number(1), number(0)).eval(symTable));
        assertEquals("division by zero", exception.getMessage());
    }

    @Test
    void arithmeticRejectsNonIntegerOperands() {
        ExpressionException exception = assertThrows(ExpressionException.class,
                () -> new ArithExp(ArithOp.ADD, bool(true), number(1)).eval(symTable));
        assertEquals("first operand is not an integer", exception.getMessage());
    }

    @Test
    void logicOperatorsEvaluateCorrectly() throws Exception {
        assertEquals(new BoolValue(false),
                new LogicExp(LogicOp.AND, bool(true), bool(false)).eval(symTable));
        assertEquals(new BoolValue(true),
                new LogicExp(LogicOp.OR, bool(false), bool(true)).eval(symTable));
    }

    @Test
    void logicRejectsNonBooleanOperands() {
        ExpressionException exception = assertThrows(ExpressionException.class,
                () -> new LogicExp(LogicOp.AND, number(1), bool(true)).eval(symTable));
        assertEquals("first operand is not a boolean", exception.getMessage());
    }

    @Test
    void variableLookupRejectsUndefinedVariable() {
        ExpressionException exception = assertThrows(ExpressionException.class,
                () -> new VarExp("missing").eval(symTable));
        assertEquals("variable missing is not defined", exception.getMessage());
    }
}

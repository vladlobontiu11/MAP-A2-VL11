package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExpressionTest {
    private final IDictionary<String, IValue> symTable = new MyDictionary<>();
    private final IHeap heap = new MyHeap();

    // Makes an integer expression for the tests.
    private static IExp number(int value) {
        return new ValueExp(new IntValue(value));
    }

    // Makes a boolean expression for the tests.
    private static IExp bool(boolean value) {
        return new ValueExp(new BoolValue(value));
    }

    // Checks that all arithmetic operations give the right answer.
    @Test
    void arithmeticOperatorsEvaluateCorrectly() throws Exception {
        assertEquals(new IntValue(7), new ArithExp(ArithOp.ADD, number(3), number(4)).eval(symTable, heap));
        assertEquals(new IntValue(1), new ArithExp(ArithOp.SUB, number(4), number(3)).eval(symTable, heap));
        assertEquals(new IntValue(12), new ArithExp(ArithOp.MUL, number(3), number(4)).eval(symTable, heap));
        assertEquals(new IntValue(2), new ArithExp(ArithOp.DIV, number(8), number(4)).eval(symTable, heap));
    }

    // Checks that dividing by zero gives an error.
    @Test
    void divisionByZeroThrows() {
        ExpressionException exception = assertThrows(ExpressionException.class,
                () -> new ArithExp(ArithOp.DIV, number(1), number(0)).eval(symTable, heap));
        assertEquals("division by zero", exception.getMessage());
    }

    // Checks that arithmetic rejects a boolean operand.
    @Test
    void arithmeticRejectsNonIntegerOperands() {
        ExpressionException exception = assertThrows(ExpressionException.class,
                () -> new ArithExp(ArithOp.ADD, bool(true), number(1)).eval(symTable, heap));
        assertEquals("first operand is not an integer", exception.getMessage());
    }

    // Checks that AND and OR return the right boolean values.
    @Test
    void logicOperatorsEvaluateCorrectly() throws Exception {
        assertEquals(new BoolValue(false),
                new LogicExp(LogicOp.AND, bool(true), bool(false)).eval(symTable, heap));
        assertEquals(new BoolValue(true),
                new LogicExp(LogicOp.OR, bool(false), bool(true)).eval(symTable, heap));
    }

    // Checks that logic rejects an integer operand.
    @Test
    void logicRejectsNonBooleanOperands() {
        ExpressionException exception = assertThrows(ExpressionException.class,
                () -> new LogicExp(LogicOp.AND, number(1), bool(true)).eval(symTable, heap));
        assertEquals("first operand is not a boolean", exception.getMessage());
    }

    // Checks that looking up an unknown variable gives an error.
    @Test
    void variableLookupRejectsUndefinedVariable() {
        ExpressionException exception = assertThrows(ExpressionException.class,
                () -> new VarExp("missing").eval(symTable, heap));
        assertEquals("variable missing is not defined", exception.getMessage());
    }
}

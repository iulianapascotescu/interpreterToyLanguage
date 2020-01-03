package model.expression;
import exceptions.MyException;
import model.programState.InterfaceDictionary;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

public class RelationalExpression implements Expression {
    private Expression expression1,expression2;
    private String operation;

    public RelationalExpression(String operation,Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }
    public Expression getExpression1() { return expression1; }
    public Expression getExpression2() { return expression2; }
    public String getOperation() { return operation; }
    public String toString() {
        return "[" + expression1.toString() +operation+expression2.toString()+"]";
    }

    public Value evaluate(InterfaceDictionary<String, Value> symbolTable, InterfaceDictionary<Integer, Value> heap) throws MyException {
        Value v1=expression1.evaluate(symbolTable,heap);
        if(v1.getType().equals(new IntType()))
        {
            Value v2=expression2.evaluate(symbolTable,heap);
            if(v2.getType().equals(new IntType()))
            {
                IntValue value1=(IntValue)v1, value2=(IntValue)v2;
                int number1=value1.getValue(), number2=value2.getValue();
                switch (operation) {
                    case "<": return new BoolValue(number1 < number2);
                    case "<=": return new BoolValue(number1 <= number2);
                    case "==": return new BoolValue(number1 == number2);
                    case "!=": return new BoolValue(number1 != number2);
                    case ">": return new BoolValue(number1 > number2);
                    case ">=": return new BoolValue(number1 >= number2);
                    default: throw new MyException("LogicExpression: invalid operation");
                }
            }
            else throw new MyException("LogicExpression: Second expression is not an int\n");
        }
        else throw new MyException("LogicExpression: First expression is not an int\n");
    }
    public Type typeCheck(InterfaceDictionary<String,Type> typeEnvironment) throws MyException{
        Type type1, type2,type3;
        type1=expression1.typeCheck(typeEnvironment);
        type2=expression2.typeCheck(typeEnvironment);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new BoolType();
            } else
                throw new MyException("LogicExpression: second operand is not an integer");
        }else
            throw new MyException("LogicExpression: first operand is not an integer");
    }
}

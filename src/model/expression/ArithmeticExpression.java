package model.expression;
import exceptions.MyException;
import model.programState.InterfaceDictionary;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

public class ArithmeticExpression implements Expression{
    private Expression expression1;
    private Expression expression2;
    private char operation;

    public ArithmeticExpression(char operation, Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    public String toString(){
        return expression1.toString()+" "+operation+" "+expression2.toString();
    }
    public Expression getExpression1(){
        return expression1;
    }
    public Expression getExpression2(){
        return expression2;
    }

    public Value evaluate(InterfaceDictionary<String, Value> table, InterfaceDictionary<Integer, Value> heap) throws MyException {
        Value v1,v2;
        v1=expression1.evaluate(table,heap);
        if(v1.getType().equals(new IntType())){
            v2 = expression2.evaluate(table,heap);
            if(v2.getType().equals(new IntType())){
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1=i1.getValue(), n2=i2.getValue();
                switch (operation) {
                    case '+': return new IntValue(n1 + n2);
                    case '-': return new IntValue(n1 - n2);
                    case '*': return new IntValue(n1 * n2);
                    case '/': {
                        if (n2 == 0) throw new MyException("ArithmeticExpression: division by zero");
                        else return new IntValue(n1 / n2);
                    }
                    default: throw new MyException("ArithmeticExpression: wrong operator");
                }
            }
            else throw new MyException("ArithmeticExpression: second operator is not an integer");
        }
        else throw new MyException("ArithmeticExpression: first operand is not an integer");
    }

    public Type typeCheck(InterfaceDictionary<String,Type> typeEnvironment) throws MyException{
        Type type1, type2;
        type1=expression1.typeCheck(typeEnvironment);
        type2=expression2.typeCheck(typeEnvironment);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            } else
            throw new MyException("ArithmeticExpression: second operand is not an integer");
        }else
        throw new MyException("ArithmeticExpression: first operand is not an integer");
    }
}

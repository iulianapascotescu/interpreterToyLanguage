package model.expression;
import exceptions.MyException;
import model.programState.InterfaceDictionary;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class LogicExpression implements Expression {
    private Expression expression1;
    private Expression expression2;
    private int operation;

    public LogicExpression(Expression expression1, Expression expression2, int operation) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }
    public String toString(){
        return expression1.toString()+" "+String.valueOf(operation)+" "+expression2.toString();
    }
    public Expression getExpression1()
    {
        return expression1;
    }
    public Expression getExpression2()
    {
        return expression2;
    }
    public int getOperation(){
        return operation;
    }

    public Value evaluate(InterfaceDictionary<String, Value> table, InterfaceDictionary<Integer, Value> heap) throws MyException {
        Value v1,v2;
        v1 = expression1.evaluate(table,heap);
        if(v1.getType().equals(new BoolType())){
            v2 = expression2.evaluate(table,heap);
            if(v2.getType().equals(new BoolType())){
                BoolValue i1 = (BoolValue) v1;
                BoolValue i2 = (BoolValue) v2;
                boolean n1=i1.getValue(),n2=i2.getValue();
                if(operation=='|') return new BoolValue(n1||n2);
                else if(operation=='&') return new BoolValue(n1&&n2);
                else throw new MyException("LogicExpression: wrong operator");
            }
            else throw new MyException("LogicExpression: second operator is not a boolean");
        }
        else throw new MyException("LogicExpression: first operand is not a boolean");
    }
    public Type typeCheck(InterfaceDictionary<String,Type> typeEnvironment) throws MyException{
        Type type1, type2;
        type1=expression1.typeCheck(typeEnvironment);
        type2=expression2.typeCheck(typeEnvironment);
        if (type1.equals(new BoolType())) {
            if (type2.equals(new BoolType())) {
                return new BoolType();
            } else
                throw new MyException("LogicExpression: second operand is not a boolean");
        }else
            throw new MyException("LogicExpression: first operand is not a boolean");
    }
}

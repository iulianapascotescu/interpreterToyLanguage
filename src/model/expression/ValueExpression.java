package model.expression;
import exceptions.MyException;
import model.programState.InterfaceDictionary;
import model.type.Type;
import model.value.Value;

public class ValueExpression implements Expression{
    private Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }
    public String toString(){ return value.toString(); }
    public Value getValue(){
        return value;
    }
    public Value evaluate(InterfaceDictionary<String, Value> table, InterfaceDictionary<Integer, Value> heap) throws MyException {
        return value;
    }
    public Type typeCheck(InterfaceDictionary<String,Type> typeEnvironment) throws MyException{
        return value.getType();
    }
}

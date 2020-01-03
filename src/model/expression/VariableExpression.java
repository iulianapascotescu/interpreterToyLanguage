package model.expression;
import exceptions.MyException;
import model.programState.InterfaceDictionary;
import model.type.Type;
import model.value.Value;

public class VariableExpression implements Expression{
    private String id;

    public VariableExpression(String id) {
        this.id = id;
    }
    public String toString(){ return id; }
    public String getId()
    {
        return id;
    }
    public Value evaluate(InterfaceDictionary<String, Value> symbolTable, InterfaceDictionary<Integer, Value> heap) throws MyException {
        return symbolTable.get(id);
    }
    public Type typeCheck(InterfaceDictionary<String,Type> typeEnvironment) throws MyException{
        return typeEnvironment.get(id);
    }
}

package model.expression;
import exceptions.MyException;
import model.programState.InterfaceDictionary;
import model.type.Type;
import model.value.Value;

public interface Expression {
    Value evaluate(InterfaceDictionary<String,Value> table, InterfaceDictionary<Integer, Value> heap) throws MyException;
    Type typeCheck(InterfaceDictionary<String,Type> typeEnvironment) throws MyException;
}

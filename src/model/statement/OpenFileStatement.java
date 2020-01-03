package model.statement;
import exceptions.MyException;
import model.expression.Expression;
import model.programState.InterfaceDictionary;
import model.programState.ProgramState;
import model.type.StringType;
import model.type.Type;
import model.value.StringValue;
import model.value.Value;

import java.io.*;

public class OpenFileStatement implements InterfaceStatement{
    private Expression expression;

    public OpenFileStatement(Expression expression){
        this.expression=expression;
    }
    public String toString(){
        return "(open " + expression.toString() + ")";
    }

    public ProgramState execute(ProgramState state) throws MyException {
        Value value=this.expression.evaluate(state.getSymbolTable(),state.getHeap());
        if(value.getType().equals(new StringType()))
        {
            StringValue stringValue = (StringValue) value;
            if(state.getFileTable().isDefined(stringValue))
                throw  new MyException("OpenFileStatement: The key already exists in the File Table");
            try {
                BufferedReader fileDescriptor = new BufferedReader(new FileReader(stringValue.getValue()));
                state.getFileTable().put(stringValue,fileDescriptor);
            }
            catch (Exception exception) {
                throw new MyException(exception.toString());
            }
        }
        else throw new MyException("OpenFileStatement: Expression on OpenFile is not a StringType");
        return null;
    }
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnvironment) throws MyException{
        Type typeExp = expression.typeCheck(typeEnvironment);
        if (typeExp.equals(new StringType()))
            return typeEnvironment;
        else
            throw new MyException("OpenFileStatement: type of the expression is not String ");
    }
}

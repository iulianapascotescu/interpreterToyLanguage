package model.statement;
import exceptions.MyException;
import model.expression.Expression;
import model.programState.InterfaceDictionary;
import model.programState.ProgramState;
import model.type.StringType;
import model.type.Type;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.FileReader;

public class CloseFileStatement implements InterfaceStatement{
    private Expression expression;

    public CloseFileStatement(Expression expression) {
        this.expression = expression;
    }
    public String toString(){
        return "(close "+expression.toString()+")";
    }

    public ProgramState execute(ProgramState state) throws MyException {
        Value value=this.expression.evaluate(state.getSymbolTable(),state.getHeap());
        if(value.getType().equals(new StringType()))
        {
            StringValue stringValue = (StringValue) value;
            if(!state.getFileTable().isDefined(stringValue))
                throw  new MyException("CloseFileStatement: The key doesn't exist in the File Table");
            try {
                BufferedReader fileDescriptor = state.getFileTable().get(stringValue);
                fileDescriptor.close();
                state.getFileTable().remove(stringValue);
            }
            catch (Exception exception) {
                throw new MyException(exception.toString());
            }
        }
        else throw new MyException("CloseFileStatement: Expression on ReadFile is not a StringType");
        return null;
    }
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnvironment) throws MyException{
        Type typeExp = expression.typeCheck(typeEnvironment);
        if (typeExp.equals(new StringType()))
            return typeEnvironment;
        else
            throw new MyException("CloseFileStatement: type of the expression is not String ");
    }
}

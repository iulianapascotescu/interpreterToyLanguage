package model.statement;
import exceptions.MyException;
import model.expression.Expression;
import model.programState.InterfaceDictionary;
import model.programState.ProgramState;
import model.type.IntType;
import model.type.ReferenceType;
import model.type.StringType;
import model.type.Type;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;

public class ReadFileStatement implements InterfaceStatement {
    private Expression expression;
    private String variableName;

    public ReadFileStatement(Expression expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }
    public String toString(){
        return "(read "+variableName+" from "+expression.toString()+")";
    }

    public ProgramState execute(ProgramState state) throws MyException {
        if(state.getSymbolTable().isDefined(variableName) && state.getSymbolTable().get(variableName).getType().equals(new IntType()))
        {
            Value value=expression.evaluate(state.getSymbolTable(),state.getHeap());
            if(value.getType().equals(new StringType()))
            {
                StringValue fileName=(StringValue) value;
                try
                {
                    BufferedReader fileDescriptor = state.getFileTable().get(fileName);
                    String line = fileDescriptor.readLine();
                    IntValue newValue=new IntValue(0);
                    if(!line.isEmpty()) newValue=new IntValue(Integer.parseInt(line));
                    state.getSymbolTable().put(this.variableName,newValue);
                }
                catch (Exception exception) {
                    throw new MyException(exception.toString());
                }
            }
            else throw new MyException("ReadFileStatement: Expression on ReadFile is not a StringType");
        }
        else throw new MyException("ReadFileStatement: invalid variable name");
        return null;
    }
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnvironment) throws MyException{
        Type typeExp = expression.typeCheck(typeEnvironment);
        if (typeExp.equals(new StringType()))
            return typeEnvironment;
        else
            throw new MyException("ReadFileStatement: type of the expression is not String ");
    }
}

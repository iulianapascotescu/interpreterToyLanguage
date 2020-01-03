package model.statement;
import exceptions.MyException;
import model.programState.InterfaceDictionary;
import model.programState.ProgramState;
import model.type.Type;

public class NoOperationStatement implements InterfaceStatement {
    public NoOperationStatement() {}
    public String toString(){
        return  "";
    }
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnvironment) throws MyException {
        return typeEnvironment;
    }
}

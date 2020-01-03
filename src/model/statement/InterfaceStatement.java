package model.statement;
import exceptions.MyException;
import model.programState.InterfaceDictionary;
import model.programState.ProgramState;
import model.type.Type;

public interface InterfaceStatement {
    ProgramState execute(ProgramState state) throws MyException;
    InterfaceDictionary<String,Type> typeCheck(InterfaceDictionary<String, Type> typeEnvironment) throws MyException;
}

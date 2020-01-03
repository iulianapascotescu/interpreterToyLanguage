package model.statement;

import exceptions.MyException;
import model.programState.*;
import model.type.BoolType;
import model.type.Type;
import model.value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ForkStatement implements InterfaceStatement {
    private InterfaceStatement statement;

    public ForkStatement(InterfaceStatement stm){statement=stm;}
    public InterfaceStatement getStatement() {
        return statement;
    }
    public String toString() { return "fork(" +statement + ')'; }

    public ProgramState execute(ProgramState state) throws MyException {
        InterfaceStack<InterfaceStatement> newStack = new MyStack<InterfaceStatement>();
        newStack.push(statement);
        InterfaceDictionary<String, Value> cloneSymbolTable = new MyDictionary<String, Value>();
        cloneSymbolTable.setContent(state.getSymbolTable().getContent().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        state.setId(state.getId()+1);
        return new ProgramState(state.getId(),newStack,cloneSymbolTable,state.getOutput(),state.getFileTable(),state.getHeap(),state.getTypeEnvironment());
    }
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String,Type> typeEnvironment) throws MyException{
        InterfaceDictionary<String,Type> clone = new MyDictionary<String, Type>();
        clone.setContent(typeEnvironment.getContent());
        statement.typeCheck(clone);
        return typeEnvironment;
    }
}

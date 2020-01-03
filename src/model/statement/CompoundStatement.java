package model.statement;
import exceptions.MyException;
import model.programState.InterfaceDictionary;
import model.programState.InterfaceStack;
import model.programState.ProgramState;
import model.type.Type;

public class CompoundStatement implements InterfaceStatement {
    private InterfaceStatement first;
    private InterfaceStatement second;

    public CompoundStatement(InterfaceStatement first, InterfaceStatement second) {
        this.first = first;
        this.second = second;
    }
    public String toString() {
        return "("+first.toString() + " ; " + second.toString()+")";
    }
    public ProgramState execute(ProgramState state) throws MyException {
        InterfaceStack<InterfaceStatement> stack=state.getStack();
        stack.push(second);
        stack.push(first);
        return null;
    }
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String,Type> typeEnvironment) throws MyException{
        return second.typeCheck(first.typeCheck(typeEnvironment));
    }

}

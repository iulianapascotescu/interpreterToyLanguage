package model.statement;
import exceptions.MyException;
import model.expression.Expression;
import model.programState.InterfaceDictionary;
import model.programState.InterfaceList;
import model.programState.ProgramState;
import model.type.Type;
import model.value.Value;

public class PrintStatement implements InterfaceStatement {
    private Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }
    public String toString(){ return "print(" +expression.toString()+")";}

    public ProgramState execute(ProgramState state) throws MyException {
        InterfaceList<Value> output=state.getOutput();
        InterfaceDictionary<String,Value> symbolTable=state.getSymbolTable();
        output.add(expression.evaluate(symbolTable,state.getHeap()));
        return null;
    }
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String,Type> typeEnvironment) throws MyException{
        expression.typeCheck(typeEnvironment);
        return typeEnvironment;
    }
}

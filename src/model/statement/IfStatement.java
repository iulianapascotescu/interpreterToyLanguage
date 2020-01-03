package model.statement;
import exceptions.MyException;
import model.expression.Expression;
import model.programState.InterfaceDictionary;
import model.programState.InterfaceStack;
import model.programState.MyDictionary;
import model.programState.ProgramState;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class IfStatement implements InterfaceStatement {
    private Expression expression;
    private InterfaceStatement thenStatement;
    private InterfaceStatement elseStatement;

    public IfStatement(Expression e, InterfaceStatement t, InterfaceStatement el)
    {
        expression=e;
        thenStatement=t;
        elseStatement=el;
    }
    public String toString(){
        return "IF("+ expression.toString()+") THEN(" +thenStatement.toString() +")ELSE("+elseStatement.toString()+")";}

    public ProgramState execute(ProgramState state) throws MyException {
        InterfaceStack<InterfaceStatement> stack=state.getStack();
        if(expression.evaluate(state.getSymbolTable(),state.getHeap()).getType().equals(new BoolType()))
        {
            BoolValue v1=(BoolValue) expression.evaluate(state.getSymbolTable(),state.getHeap());
            if(v1.getValue()) stack.push(thenStatement);
            else stack.push(elseStatement);
        }
        else throw new MyException("IfStatement: The type of the expression is not bool!\n");
        return null;
    }

    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String,Type> typeEnvironment) throws MyException{
        InterfaceDictionary<String,Type> clone1 = new MyDictionary<String, Type>();
        clone1.setContent(typeEnvironment.getContent());
        InterfaceDictionary<String,Type> clone2 = new MyDictionary<String, Type>();
        clone2.setContent(typeEnvironment.getContent());
        Type typeExp=expression.typeCheck(typeEnvironment);
        if (typeExp.equals(new BoolType())) {
            thenStatement.typeCheck(clone1);
            elseStatement.typeCheck(clone2);
            return typeEnvironment;
        }
        else
            throw new MyException("IfStatement: The condition of IF has not the type bool");
    }
}

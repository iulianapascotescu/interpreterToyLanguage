package model.statement;
import model.expression.Expression;
import model.programState.InterfaceDictionary;
import model.programState.InterfaceStack;
import model.programState.ProgramState;
import model.type.Type;
import exceptions.MyException;
import model.value.Value;

public class AssignmentStatement implements InterfaceStatement {
    private String id;
    private Expression expression;

    public AssignmentStatement(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }
    public String toString(){ return id+"="+ expression.toString();}

    public ProgramState execute(ProgramState state) throws MyException{
        InterfaceDictionary<String, Value> symbolTable= state.getSymbolTable();
        Value value = expression.evaluate(symbolTable,state.getHeap());
        if (symbolTable.isDefined(id)) {
            Type typeId = (symbolTable.get(id)).getType();
            if ((value.getType()).equals(typeId)) symbolTable.put(id, value);
            else throw new MyException("AssignmentStatement: declared type of variable" + id + " and type of the assigned expression do not match");
        }
        else throw new MyException("AssignmentStatement: the used variable" +id + " was not declared before");
        return null;
    }
    public InterfaceDictionary<String,Type> typeCheck(InterfaceDictionary<String,Type> typeEnvironment) throws MyException{
        Type typeVar = typeEnvironment.get(id);
        Type typeExp = expression.typeCheck(typeEnvironment);
        if (typeVar.equals(typeExp))
            return typeEnvironment;
        else
            throw new MyException("AssignmentStatement: right hand side and left hand side have different types ");
    }

}



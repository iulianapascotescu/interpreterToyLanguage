package model.statement;
import exceptions.MyException;
import model.expression.Expression;
import model.programState.InterfaceDictionary;
import model.programState.ProgramState;
import model.type.ReferenceType;
import model.type.Type;
import model.value.ReferenceValue;
import model.value.Value;

public class HeapWritingStatement implements InterfaceStatement {
    private String name;
    private Expression expression;

    public HeapWritingStatement(String name, Expression expression) {
        this.name = name;
        this.expression = expression;
    }
    public String getName() {
        return name;
    }
    public Expression getExpression() {
        return expression;
    }
    public String toString() {
        return "[*" + name + '=' + expression +']';
    }

    public ProgramState execute(ProgramState state) throws MyException {
        InterfaceDictionary<String, Value> symbolTable=state.getSymbolTable();
        InterfaceDictionary<Integer,Value> heapTable=state.getHeap();
        Value expressionValue=expression.evaluate(symbolTable,heapTable);
        //the variable contains the heap address, the expression represents the new value that is going to be stored into the heap
        if(symbolTable.isDefined(name) && symbolTable.get(name).getType().equals(new ReferenceType(expressionValue.getType())))
        {
            ReferenceValue value=(ReferenceValue) symbolTable.get(name);
            if(heapTable.isDefined(value.getAddress()))
            {
                if(expressionValue.getType().equals(value.getLocationType()))
                    heapTable.put(value.getAddress(), expressionValue);
                else throw new MyException("HeapWritingStatement: incorrect types");
            }
            else throw new MyException("HeapWritingStatement: No such variable in the heapTable");
        }
        else throw new MyException("HeapWritingStatement: No such variable in the symTbl or the type is wrong");
        return null;
    }
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String,Type> typeEnvironment) throws MyException{
        Type typeVar = typeEnvironment.get(name);
        Type typExp = expression.typeCheck(typeEnvironment);
        if (typeVar.equals(new ReferenceType(typExp)))
            return typeEnvironment;
        else
            throw new MyException("HeapWritingStatement: right hand side and left hand side have different types ");
    }
}

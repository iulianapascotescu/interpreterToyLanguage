package model.statement;
import exceptions.MyException;
import model.expression.Expression;
import model.programState.InterfaceDictionary;
import model.programState.ProgramState;
import model.type.ReferenceType;
import model.type.Type;
import model.value.ReferenceValue;
import model.value.Value;

public class HeapAllocationStatement implements InterfaceStatement {
    private String name;
    private Expression expression;

    public HeapAllocationStatement(String name, Expression expression) {
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
        return "[*" + name + '=' + expression + ']';
    }

    public ProgramState execute(ProgramState state) throws MyException {
        InterfaceDictionary<String, Value> symbolTable=state.getSymbolTable();
        Value value=expression.evaluate(symbolTable,state.getHeap());
        if(symbolTable.isDefined(name) && symbolTable.get(name).getType().equals(new ReferenceType(value.getType())))
        {
            ReferenceType v = (ReferenceType) symbolTable.get(name).getType();
            if(value.getType().equals(v.getInner()))
            {
                int address=state.getHeap().getAddress();
                //create a new entry in the Heap table such that a new free address is generated and
                //it is associated to the result of the expression evaluation
                state.getHeap().put(address,value);
                Value newValue=new ReferenceValue(address,value.getType());
                symbolTable.put(name,newValue);
            }
            else throw new MyException("HeapAllocationStatement: Types are not equal");
        }
        else throw new MyException("HeapAllocationStatement: Name is not a variable in the SymTbl or it's type is not RefType!!");
        return null;
    }
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String,Type> typeEnvironment) throws MyException{
        Type typeVar = typeEnvironment.get(name);
        Type typExp = expression.typeCheck(typeEnvironment);
        if (typeVar.equals(new ReferenceType(typExp)))
            return typeEnvironment;
        else
            throw new MyException("HeapAllocationStatement: right hand side and left hand side have different types ");
    }
}

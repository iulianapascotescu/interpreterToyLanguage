package model.expression;
import exceptions.MyException;
import model.programState.InterfaceDictionary;
import model.type.ReferenceType;
import model.type.Type;
import model.value.ReferenceValue;
import model.value.Value;

public class HeapReadingExpression implements Expression {
    private Expression expression;

    public HeapReadingExpression(Expression expression) {
        this.expression = expression;
    }
    public Expression getExpression() {
        return expression;
    }
    public String toString() {
        return "[*"+ expression +']';
    }

    public Value evaluate(InterfaceDictionary<String, Value> table, InterfaceDictionary<Integer, Value> heap) throws MyException {
        Value value=expression.evaluate(table,heap);
        //if(value.getType().equals(new ReferenceType()))
        if(value instanceof ReferenceValue)
        {
            //Take the address component of the RefValue of the expression and use it to access heap and return the value associated to that address
           ReferenceValue refValue=(ReferenceValue) value;
           Integer address=refValue.getAddress();
           if(heap.isDefined(address)) return heap.get(address);
           else throw new MyException("HeapReadingExpression: The address is not a key in the heap table");
        }
        else throw new MyException("HeapReadingExpression: the expression is not of Reference Type");
    }
    public Type typeCheck(InterfaceDictionary<String,Type> typeEnvironment) throws MyException{
        Type type=expression.typeCheck(typeEnvironment);
        if (type instanceof ReferenceType) {
            ReferenceType referenceType =(ReferenceType) type;
            return referenceType.getInner();
        } else
            throw new MyException("HeapReadingExpression: the rH argument is not a Ref Type");
    }

}

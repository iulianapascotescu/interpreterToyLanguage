package model.statement;
import exceptions.MyException;
import model.expression.Expression;
import model.programState.InterfaceDictionary;
import model.programState.MyDictionary;
import model.programState.ProgramState;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class WhileStatement implements InterfaceStatement {
    private Expression expression;
    private InterfaceStatement statement;

    public WhileStatement(Expression expression,InterfaceStatement statement) {
        this.expression = expression;
        this.statement=statement;
    }
    public Expression getExpression() {
        return expression;
    }
    public String toString() {
        return "[ (while "+expression+") "+statement.toString()+"]";
    }

    public ProgramState execute(ProgramState state) throws MyException {
        Value value=expression.evaluate(state.getSymbolTable(),state.getHeap());
        if(value.getType().equals(new BoolType()))
        {
            BoolValue boolValue=(BoolValue) value;
            if(boolValue.getValue()) {
                state.getStack().push(new WhileStatement(expression,statement));
                state.getStack().push(statement);
            }
        }
        else throw new MyException("WhileStatement: The expression is not of type Bool");
        return null;
    }
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String,Type> typeEnvironment) throws MyException{
        InterfaceDictionary<String,Type> clone = new MyDictionary<String, Type>();
        clone.setContent(typeEnvironment.getContent());
        Type typeExp=expression.typeCheck(typeEnvironment);
        if (typeExp.equals(new BoolType())) {
            statement.typeCheck(clone);
            return typeEnvironment;
        }
        else
            throw new MyException("WhileStatement: The condition of while has not the type bool");
    }
}

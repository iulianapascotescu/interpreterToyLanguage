package model.statement;
import exceptions.MyException;
import model.programState.InterfaceDictionary;
import model.programState.ProgramState;
import model.type.*;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

public class VariableDeclarationStatement implements InterfaceStatement {
    private String name;
    private Type type;

    public VariableDeclarationStatement(String name, Type type) {
        this.name = name;
        this.type = type;
    }
    public String toString(){
        return type.toString()+" "+name;
    }

    public ProgramState execute(ProgramState state) throws MyException {
        InterfaceDictionary<String, Value> symbolTable=state.getSymbolTable();
        if(type.equals(new BoolType())) {
            BoolType boolType = new BoolType();
            symbolTable.put(name,boolType.defaultValue());
        }
        else if(type.equals(new IntType())) {
            IntType intType = new IntType();
            symbolTable.put(name,intType.defaultValue());
        }
        else if(type.equals(new StringType())){
            StringType stringType=new StringType();
            symbolTable.put(name,stringType.defaultValue());
        }
        else if(type instanceof ReferenceType){
            ReferenceType val = (ReferenceType)type;
            ReferenceType referenceType=new ReferenceType(val.getInner());
            symbolTable.put(name,referenceType.defaultValue());
        }
        return null;
    }
    public InterfaceDictionary<String,Type> typeCheck(InterfaceDictionary<String,Type> typeEnvironment) throws MyException{
        typeEnvironment.put(name,type);
        return typeEnvironment;
    }
}

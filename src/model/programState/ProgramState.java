package model.programState;
import exceptions.MyException;
import model.statement.InterfaceStatement;
import model.type.Type;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class ProgramState {
    private InterfaceStack<InterfaceStatement> executionStack;
    private InterfaceDictionary<String, Value> symbolTable;
    private InterfaceList<Value> output;
    private InterfaceDictionary<StringValue, BufferedReader> fileTable;
    private InterfaceDictionary<Integer,Value> heap;
    private InterfaceDictionary<String, Type> typeEnvironment;
    static int id=1;
    private int currentId;

    public InterfaceStack<InterfaceStatement> getStack(){
        return executionStack;
    }
    public InterfaceDictionary<String, Value> getSymbolTable(){
        return symbolTable;
    }
    public InterfaceList<Value> getOutput(){
        return output;
    }
    public InterfaceDictionary<StringValue, BufferedReader> getFileTable(){ return fileTable;}
    public InterfaceDictionary<Integer, Value> getHeap(){return this.heap;}
    public InterfaceDictionary<String, Type> getTypeEnvironment(){return typeEnvironment;}
    public ProgramState(int threadId, InterfaceStack<InterfaceStatement> stk,
                        InterfaceDictionary<String, Value> symbol,
                        InterfaceList<Value> ot, InterfaceDictionary<StringValue,BufferedReader> ft,
                        InterfaceDictionary<Integer,Value> hp,InterfaceDictionary<String, Type> tE){
        currentId=threadId;
        executionStack=stk;
        symbolTable=symbol;
        output=ot;
        fileTable=ft;
        heap=hp;
        typeEnvironment=tE;
    }
    public String toString()
    {
        return "\nId: "+String.valueOf(currentId)+
                "\nExecution Stack: \n"+executionStack.toString()+
                "\nSymbolTable: \n"+symbolTable.toString()+
                "\nOutput: \n"+output.toString()+
                "\nFile Table: \n"+fileTable.toString()+
                "\n Heap:\n"+heap.toString()+
                "\n TypeEnv:\n"+typeEnvironment.toString()+"\n";
    }
    public boolean isNotCompleted(){
        return !executionStack.empty();
    }
    public ProgramState oneStep() throws MyException{
        if(executionStack.empty())
            throw new MyException("ProgramState: Stack is empty");
        InterfaceStatement currentStatement=executionStack.pop();
        return  currentStatement.execute(this);
    }

    public int getId(){return id;}
    public int getCurrentId(){return currentId;}
    public void setId(int newId){id=newId;}
    public void setCurrentId(int id){currentId=id;}
}

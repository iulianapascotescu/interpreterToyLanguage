package main;
import controller.Controller;
import exceptions.MyException;
import model.expression.*;
import model.programState.*;
import model.statement.*;
import model.type.*;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;
import repository.Repository;
import repository.RepositoryInterface;
import view.ExitCommand;
import view.RunExample;
import view.TextMenu;
import view.View;

import java.io.BufferedReader;

public class Interpreter {
    public static void main(String[] args) throws MyException {
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        //int v; v=2;Print(v)
        InterfaceStatement ex1 =
                new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));

        //int a;int b; a=2+3*5;b=a+1;Print(b)
        InterfaceStatement ex2 =
                new CompoundStatement( new VariableDeclarationStatement("a",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new
                                ArithmeticExpression('*',new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                new CompoundStatement(new AssignmentStatement("b",new ArithmeticExpression('+',new VariableExpression("a"), new
                                        ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));

        //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        InterfaceStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new IntValue(2))),
                                      new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));

        //string a; a = 'test.in' int c; open(a); read(a,c) print(c) read(a,c) print(c) close(a)
        InterfaceStatement ex4 =
                new CompoundStatement(new VariableDeclarationStatement("a",new StringType()),
                new CompoundStatement(new VariableDeclarationStatement("c", new IntType()),
                new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new StringValue("test.txt"))),
                new CompoundStatement(new OpenFileStatement(new VariableExpression("a")),
                new CompoundStatement(new ReadFileStatement(new VariableExpression("a"),"c"),
                new CompoundStatement(new PrintStatement(new VariableExpression("c")),
                new CompoundStatement(new ReadFileStatement(new VariableExpression("a"),"c"),
                new CompoundStatement(new PrintStatement(new VariableExpression("c")), new CloseFileStatement(new VariableExpression("a")) ) ) ) )))));

        // int v; v=4; ref int a; (while (v>0) {new(a,v*2); print(a); v=v-1;}; print(*a);
        InterfaceStatement ex5 =
                new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(4))),
                new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new IntType())),
                new CompoundStatement(new WhileStatement(new RelationalExpression(">",new VariableExpression("v"),new ValueExpression(new IntValue(0))),
                new CompoundStatement(new HeapAllocationStatement("a",new ArithmeticExpression('*',new VariableExpression("v"),new ValueExpression(new IntValue(2)))),
                new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("a"))),
                        new AssignmentStatement("v",new ArithmeticExpression('-',new VariableExpression("v"),new ValueExpression(new IntValue(1))))))),
                                new CompoundStatement(new HeapWritingStatement("a",new VariableExpression("v")),new PrintStatement(new HeapReadingExpression(new VariableExpression("a"))))))));

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        InterfaceStatement ex6 = new CompoundStatement( new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new IntType()))),
                new CompoundStatement(new HeapAllocationStatement("a",new VariableExpression("v")),
                new CompoundStatement(new HeapWritingStatement("a",new VariableExpression("v")),
                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new VariableExpression("a"))))))));

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        InterfaceStatement ex7 = new CompoundStatement( new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new IntType()))),
                new CompoundStatement(new HeapAllocationStatement("a",new VariableExpression("v")),
                new CompoundStatement(new HeapWritingStatement("a",new VariableExpression("v")),
                new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                        new PrintStatement(new ArithmeticExpression('+',new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))),new ValueExpression(new IntValue(5))))))))));

        //Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        InterfaceStatement ex8 = new CompoundStatement( new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                new CompoundStatement(new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                new CompoundStatement(new HeapWritingStatement("v",new ValueExpression(new IntValue(30))),
                new PrintStatement(new ArithmeticExpression('+', new HeapReadingExpression(new VariableExpression("v")) ,new ValueExpression(new IntValue(5))))))));

        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a));
        InterfaceStatement ex9 = new CompoundStatement( new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                new CompoundStatement(new VariableDeclarationStatement("a",new ReferenceType(new ReferenceType(new IntType()))),
                new CompoundStatement(new HeapAllocationStatement("a",new VariableExpression("v")),
                new CompoundStatement(new HeapAllocationStatement("v",new ValueExpression(new IntValue(30))),
                new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a")))))))));

        // int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        InterfaceStatement ex10 =
                new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(4))),
                new CompoundStatement(new WhileStatement(new RelationalExpression(">",new VariableExpression("v"),new ValueExpression(new IntValue(0))),
                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                        new AssignmentStatement("v",new ArithmeticExpression('-',new VariableExpression("v"),new ValueExpression(new IntValue(1)))))),
                        new PrintStatement(new VariableExpression("v")))));

        //int v; Ref int a; v=10;new(a,22);fork(wH(a,30);v=32;print(v);print(rH(a)));print(v);print(rH(a))
        InterfaceStatement ex11 =
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new IntType())),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                new CompoundStatement(
                new HeapAllocationStatement("a", new ValueExpression(new IntValue(22))),
                new CompoundStatement(new ForkStatement(
                new CompoundStatement(new HeapWritingStatement("a", new ValueExpression(new IntValue(30))),
                        new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(32))),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                        new PrintStatement(new HeapReadingExpression(new VariableExpression("a"))))))),
                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                new PrintStatement(new HeapReadingExpression(new VariableExpression("a")))))))));

        //bool a; a=True; int b; b=4; print(a+b)
        InterfaceStatement ex12 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                        new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                                new CompoundStatement(new AssignmentStatement("b", new ValueExpression(new IntValue(4))),
                                        new PrintStatement(new ArithmeticExpression('+', new VariableExpression("a"), new VariableExpression("b")))))));
        InterfaceList<Value> output12 =new MyList<Value>();
        InterfaceStack<InterfaceStatement> stack12=new MyStack<InterfaceStatement>();
        stack12.push(ex12);
        InterfaceDictionary<String,Value> symbolTable12=new MyDictionary<String,Value>();
        InterfaceDictionary<StringValue, BufferedReader> fileTable12=new MyDictionary<StringValue, BufferedReader>();
        InterfaceDictionary<Integer,Value> heapTable12=new MyDictionary<Integer,Value>();
        InterfaceDictionary<String, Type> typeEnvironment12 = new MyDictionary<String, Type>();
        try {
            ex12.typeCheck(typeEnvironment12);
            ProgramState state12=new ProgramState(1,stack12,symbolTable12,output12,fileTable12,heapTable12,typeEnvironment12);
            RepositoryInterface repository12=new Repository("log12.txt");
            repository12.addProgramState(state12);
            Controller controller12=new Controller(repository12);
            menu.addCommand(new RunExample("12",ex12.toString(),controller12));
        }
        catch (MyException e){
            System.out.println(typeEnvironment12);
            System.out.println(e.getMessage());
        }

        InterfaceList<Value> output1 =new MyList<Value>();
        InterfaceStack<InterfaceStatement> stack1=new MyStack<InterfaceStatement>();
        stack1.push(ex1);
        InterfaceDictionary<String,Value> symbolTable1=new MyDictionary<String,Value>();
        InterfaceDictionary<StringValue, BufferedReader> fileTable1=new MyDictionary<StringValue, BufferedReader>();
        InterfaceDictionary<Integer,Value> heapTable1=new MyDictionary<Integer,Value>();
        InterfaceDictionary<String, Type> typeEnvironment1 = new MyDictionary<String, Type>();
        try {
            ex1.typeCheck(typeEnvironment1);
            ProgramState state1=new ProgramState(1,stack1,symbolTable1,output1,fileTable1,heapTable1,typeEnvironment1);
            RepositoryInterface repository1=new Repository("log1.txt");
            repository1.addProgramState(state1);
            Controller controller1=new Controller(repository1);
            menu.addCommand(new RunExample("1",ex1.toString(),controller1));
        }
        catch (MyException e){
            System.out.println(e.getMessage());
        }

        InterfaceList<Value> output2 =new MyList<Value>();
        InterfaceStack<InterfaceStatement> stack2=new MyStack<InterfaceStatement>();
        stack2.push(ex2);
        InterfaceDictionary<String,Value> symbolTable2=new MyDictionary<String,Value>();
        InterfaceDictionary<StringValue, BufferedReader> fileTable2=new MyDictionary<StringValue, BufferedReader>();
        InterfaceDictionary<Integer,Value> heapTable2=new MyDictionary<Integer,Value>();
        InterfaceDictionary<String, Type> typeEnvironment2 = new MyDictionary<String, Type>();
        try {
            ex2.typeCheck(typeEnvironment2);
            ProgramState state2=new ProgramState(1,stack2,symbolTable2,output2,fileTable2,heapTable2,typeEnvironment2);
            RepositoryInterface repository2=new Repository("log2.txt");
            repository2.addProgramState(state2);
            Controller controller2=new Controller(repository2);
            menu.addCommand(new RunExample("2",ex2.toString(),controller2));
        }
        catch (MyException e){
            System.out.println(e.getMessage());
        }

        InterfaceList<Value> output3 =new MyList<Value>();
        InterfaceStack<InterfaceStatement> stack3=new MyStack<InterfaceStatement>();
        stack3.push(ex3);
        InterfaceDictionary<String,Value> symbolTable3=new MyDictionary<String,Value>();
        InterfaceDictionary<StringValue, BufferedReader> fileTable3=new MyDictionary<StringValue, BufferedReader>();
        InterfaceDictionary<Integer,Value> heapTable3=new MyDictionary<Integer,Value>();
        InterfaceDictionary<String, Type> typeEnvironment3 = new MyDictionary<String, Type>();
        try {
            ex3.typeCheck(typeEnvironment3);
            ProgramState state3=new ProgramState(1,stack3,symbolTable3,output3,fileTable3,heapTable3,typeEnvironment3);
            RepositoryInterface repository3=new Repository("log3.txt");
            repository3.addProgramState(state3);
            Controller controller3=new Controller(repository3);
            menu.addCommand(new RunExample("3",ex3.toString(),controller3));
        }
        catch (MyException e){
            System.out.println(e.getMessage());
        }

        InterfaceList<Value> output4 =new MyList<Value>();
        InterfaceStack<InterfaceStatement> stack4=new MyStack<InterfaceStatement>();
        stack4.push(ex4);
        InterfaceDictionary<String,Value> symbolTable4=new MyDictionary<String,Value>();
        InterfaceDictionary<StringValue, BufferedReader> fileTable4=new MyDictionary<StringValue, BufferedReader>();
        InterfaceDictionary<Integer,Value> heapTable4=new MyDictionary<Integer,Value>();
        InterfaceDictionary<String, Type> typeEnvironment4 = new MyDictionary<String, Type>();
        try {
            ex4.typeCheck(typeEnvironment4);
            ProgramState state4=new ProgramState(1,stack4,symbolTable4,output4,fileTable4,heapTable4,typeEnvironment4);
            RepositoryInterface repository4=new Repository("log4.txt");
            repository4.addProgramState(state4);
            Controller controller4=new Controller(repository4);
            menu.addCommand(new RunExample("4",ex4.toString(),controller4));
        }
        catch (MyException e){
            System.out.println(e.getMessage());
        }

        InterfaceList<Value> output5 =new MyList<Value>();
        InterfaceStack<InterfaceStatement> stack5=new MyStack<InterfaceStatement>();
        stack5.push(ex5);
        InterfaceDictionary<String,Value> symbolTable5=new MyDictionary<String,Value>();
        InterfaceDictionary<StringValue, BufferedReader> fileTable5=new MyDictionary<StringValue, BufferedReader>();
        InterfaceDictionary<Integer,Value> heapTable5=new MyDictionary<Integer,Value>();
        InterfaceDictionary<String, Type> typeEnvironment5 = new MyDictionary<String, Type>();
        try {
            ex5.typeCheck(typeEnvironment5);
            ProgramState state5=new ProgramState(1,stack5,symbolTable5,output5,fileTable5,heapTable5,typeEnvironment5);
            RepositoryInterface repository5=new Repository("log5.txt");
            repository5.addProgramState(state5);
            Controller controller5=new Controller(repository5);
            menu.addCommand(new RunExample("5",ex5.toString(),controller5));
        }
        catch (MyException e){
            System.out.println(e.getMessage());
        }

        InterfaceList<Value> output6 =new MyList<Value>();
        InterfaceStack<InterfaceStatement> stack6=new MyStack<InterfaceStatement>();
        stack6.push(ex6);
        InterfaceDictionary<String,Value> symbolTable6=new MyDictionary<String,Value>();
        InterfaceDictionary<StringValue, BufferedReader> fileTable6=new MyDictionary<StringValue, BufferedReader>();
        InterfaceDictionary<Integer,Value> heapTable6=new MyDictionary<Integer,Value>();
        InterfaceDictionary<String, Type> typeEnvironment6 = new MyDictionary<String, Type>();
        try {
            ex6.typeCheck(typeEnvironment6);
            ProgramState state6=new ProgramState(1,stack6,symbolTable6,output6,fileTable6,heapTable6,typeEnvironment6);
            RepositoryInterface repository6=new Repository("log6.txt");
            repository6.addProgramState(state6);
            Controller controller6=new Controller(repository6);
            menu.addCommand(new RunExample("6",ex6.toString(),controller6));
        }
        catch (MyException e){
            System.out.println(e.getMessage());
        }

        InterfaceList<Value> output7 =new MyList<Value>();
        InterfaceStack<InterfaceStatement> stack7=new MyStack<InterfaceStatement>();
        stack7.push(ex7);
        InterfaceDictionary<String,Value> symbolTable7=new MyDictionary<String,Value>();
        InterfaceDictionary<StringValue, BufferedReader> fileTable7=new MyDictionary<StringValue, BufferedReader>();
        InterfaceDictionary<Integer,Value> heapTable7=new MyDictionary<Integer,Value>();
        InterfaceDictionary<String, Type> typeEnvironment7 = new MyDictionary<String, Type>();
        try {
            ex7.typeCheck(typeEnvironment7);
            ProgramState state7=new ProgramState(1,stack7,symbolTable7,output7,fileTable7,heapTable7,typeEnvironment7);
            RepositoryInterface repository7=new Repository("log7.txt");
            repository7.addProgramState(state7);
            Controller controller7=new Controller(repository7);
            menu.addCommand(new RunExample("7",ex7.toString(),controller7));
        }
        catch (MyException e){
            System.out.println(e.getMessage());
        }

        InterfaceList<Value> output8 =new MyList<Value>();
        InterfaceStack<InterfaceStatement> stack8=new MyStack<InterfaceStatement>();
        stack8.push(ex8);
        InterfaceDictionary<String,Value> symbolTable8=new MyDictionary<String,Value>();
        InterfaceDictionary<StringValue, BufferedReader> fileTable8=new MyDictionary<StringValue, BufferedReader>();
        InterfaceDictionary<Integer,Value> heapTable8=new MyDictionary<Integer,Value>();
        InterfaceDictionary<String, Type> typeEnvironment8 = new MyDictionary<String, Type>();
        try {
            ex8.typeCheck(typeEnvironment8);
            ProgramState state8=new ProgramState(1,stack8,symbolTable8,output8,fileTable8,heapTable8,typeEnvironment8);
            RepositoryInterface repository8=new Repository("log8.txt");
            repository8.addProgramState(state8);
            Controller controller8=new Controller(repository8);
            menu.addCommand(new RunExample("8",ex8.toString(),controller8));
        }
        catch (MyException e){
            System.out.println(e.getMessage());
        }

        InterfaceList<Value> output9 =new MyList<Value>();
        InterfaceStack<InterfaceStatement> stack9=new MyStack<InterfaceStatement>();
        stack9.push(ex9);
        InterfaceDictionary<String,Value> symbolTable9=new MyDictionary<String,Value>();
        InterfaceDictionary<StringValue, BufferedReader> fileTable9=new MyDictionary<StringValue, BufferedReader>();
        InterfaceDictionary<Integer,Value> heapTable9=new MyDictionary<Integer,Value>();
        InterfaceDictionary<String, Type> typeEnvironment9 = new MyDictionary<String, Type>();
        try {
            ex9.typeCheck(typeEnvironment9);
            ProgramState state9=new ProgramState(1,stack9,symbolTable9,output9,fileTable9,heapTable9,typeEnvironment9);
            RepositoryInterface repository9=new Repository("log9.txt");
            repository9.addProgramState(state9);
            Controller controller9=new Controller(repository9);
            menu.addCommand(new RunExample("9",ex9.toString(),controller9));
        }
        catch (MyException e){
            System.out.println(e.getMessage());
        }

        InterfaceList<Value> output10 =new MyList<Value>();
        InterfaceStack<InterfaceStatement> stack10=new MyStack<InterfaceStatement>();
        stack10.push(ex10);
        InterfaceDictionary<String,Value> symbolTable10=new MyDictionary<String,Value>();
        InterfaceDictionary<StringValue, BufferedReader> fileTable10=new MyDictionary<StringValue, BufferedReader>();
        InterfaceDictionary<Integer,Value> heapTable10=new MyDictionary<Integer,Value>();
        InterfaceDictionary<String, Type> typeEnvironment10 = new MyDictionary<String, Type>();
        try {
            ex10.typeCheck(typeEnvironment10);
            ProgramState state10=new ProgramState(1,stack10,symbolTable10,output10,fileTable10,heapTable10,typeEnvironment10);
            RepositoryInterface repository10=new Repository("log10.txt");
            repository10.addProgramState(state10);
            Controller controller10=new Controller(repository10);
            menu.addCommand(new RunExample("10",ex10.toString(),controller10));
        }
        catch (MyException e){
            System.out.println(e.getMessage());
        }

        InterfaceList<Value> output11 =new MyList<Value>();
        InterfaceStack<InterfaceStatement> stack11=new MyStack<InterfaceStatement>();
        stack11.push(ex11);
        InterfaceDictionary<String,Value> symbolTable11=new MyDictionary<String,Value>();
        InterfaceDictionary<StringValue, BufferedReader> fileTable11=new MyDictionary<StringValue, BufferedReader>();
        InterfaceDictionary<Integer,Value> heapTable11=new MyDictionary<Integer,Value>();
        InterfaceDictionary<String, Type> typeEnvironment11 = new MyDictionary<String, Type>();
        try {
            ex11.typeCheck(typeEnvironment11);
            ProgramState state11=new ProgramState(1,stack11,symbolTable11,output11,fileTable11,heapTable11,typeEnvironment11);
            RepositoryInterface repository11=new Repository("log11.txt");
            repository11.addProgramState(state11);
            Controller controller11=new Controller(repository11);
            menu.addCommand(new RunExample("11",ex11.toString(),controller11));
        }
        catch (MyException e) {
            System.out.println(e.getMessage());
        }

        menu.show();
    }
}

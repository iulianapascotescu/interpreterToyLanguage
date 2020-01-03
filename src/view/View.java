package view;

import controller.Controller;
import exceptions.MyException;
import model.expression.ArithmeticExpression;
import model.expression.ValueExpression;
import model.expression.VariableExpression;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IntValue;

import java.util.Scanner;

public class View {
    private Controller controller;
    private Scanner in;

    public View(Controller controller) {
        this.controller = controller;
        this.in=new Scanner(System.in);
    }

    public void start() throws MyException {
        //int v; v=2;Print(v)
        InterfaceStatement ex1= new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(2))), new PrintStatement(new
                        VariableExpression("v"))));

        //int a;int b; a=2+3*5;b=a+1;Print(b)
        InterfaceStatement ex2 = new CompoundStatement( new VariableDeclarationStatement("a",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new
                                ArithmeticExpression('*',new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b",new ArithmeticExpression('+',new VariableExpression("a"), new
                                        ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));

        //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        InterfaceStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new
                                        IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                                        VariableExpression("v"))))));


        while(true) {
            System.out.print("\nChoose a statement:\n");
            System.out.print("1. int v; v=2;Print(v)\n");
            System.out.print("2. int a;int b; a=2+3*5;b=a+1;Print(b)\n");
            System.out.print("3. bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)\n");
            /*
            int command = in.nextInt();
            if (command == 1)
                this.controller.getCurrentState().getStack().push(ex1);
            else if (command == 2)
                this.controller.getCurrentState().getStack().push(ex2);
            else if (command == 3)
                this.controller.getCurrentState().getStack().push(ex3);
            else if(command==0)
                break;
            else System.out.print("wrong command\n");

            do {
                System.out.print("\nChoose a method:\n");
                System.out.print("1. One Step\n");
                System.out.print("2. All Steps\n");
                command = in.nextInt();
                try {
                    if (command == 1)
                        this.controller.oneStep();
                    else if (command == 2)
                        this.controller.allStep();
                    else System.out.print("wrong method\n");
                }
                catch (MyException exeption){
                    System.out.print(exeption.getMessage());
                }
            }while(command!=2);

             */
        }
    }
}

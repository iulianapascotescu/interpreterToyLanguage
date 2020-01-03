package view;

import controller.Controller;
import exceptions.MyException;

public class RunExample extends Command {
    private Controller controller;
    public RunExample(String key, String desc, Controller ctr){
        super(key, desc);
        this.controller=ctr;
    }
    @Override
    public void execute() throws MyException {
        try{
            controller.allStep(); }
        catch (Exception exception) {
            System.out.print(exception.toString());
        }
    }
}

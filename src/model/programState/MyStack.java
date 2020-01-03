package model.programState;
import exceptions.MyException;

import java.util.Stack;

public class MyStack<T> implements InterfaceStack<T>{
    private Stack<T>  stack;

    public MyStack() {
        this.stack = new Stack<T>();
    }
    public void push(T v) {
        stack.push(v);
    }
    public boolean empty()
    {
        return stack.empty();
    }
    public String toString(){
        return stack.toString();
    }
    public T pop() throws MyException {
        try {
            return stack.pop();
        }
        catch (Exception exception){
            throw new MyException("MyStack: stack is empty");
        }
    }
}

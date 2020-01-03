package model.programState;
import exceptions.MyException;

public interface InterfaceStack<T> {
    public T pop() throws MyException;
    public void push(T v);
    public boolean empty();
}

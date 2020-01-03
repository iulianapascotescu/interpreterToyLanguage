package model.programState;
import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements InterfaceList<T>{
    private List<T> list;

    public MyList() {
        this.list = new ArrayList<T>(10);
    }
    public void add(T value){
        list.add(value);
    }
    public int size(){
        return list.size();
    }
    public T set(int index, T value){
        return list.set(index,value);
    }
    public T get(int index){
        return list.get(index);
    }
    public String toString(){
        return list.toString();
    }
}

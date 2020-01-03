package model.programState;
import java.util.*;

public class MyDictionary<T,K> implements InterfaceDictionary<T,K> {
    private HashMap<T,K> dictionary;
    private int address=1;

    public MyDictionary() {
        this.dictionary = new HashMap<T,K>(10);
    }
    public void remove(T key){
        dictionary.remove(key);
    }
    public int getAddress() { return address; }
    public int size(){
        return dictionary.size();
    }
    public K get(T key){
        return dictionary.get(key);
    }
    public boolean isEmpty(){
        return dictionary.isEmpty();
    }
    public K put(T key, K value){
        K oldValue = dictionary.put(key,value);
        address++;
        return oldValue;
    }
    public boolean isDefined(T key){ return dictionary.get(key)!=null; }
    public String toString(){
        return dictionary.toString();
    }
    public void setContent(Map<T,K> newContent) { this.dictionary = (HashMap<T, K>) newContent; }
    public Map<T, K> getContent() { return this.dictionary; }
}

package model.programState;
import java.util.Map;

public interface InterfaceDictionary<T,K> {
    public int size();
    public K get(T key);
    public boolean isEmpty();
    public K put(T key, K value);
    public boolean isDefined(T key);
    public void remove(T key);
    public int getAddress();
    void setContent(Map<T,K> newContent);
    Map<T, K> getContent();
}

package model.programState;

public interface InterfaceList<T> {
    public void add(T value);
    public int size();
    public T set(int index, T value);
    public T get(int index);
}

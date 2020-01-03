package model.type;
import model.value.Value;

public interface Type {
    public boolean equals(Object o);
    public Value defaultValue();
}

package model.type;
import model.value.IntValue;
import model.value.Value;

public class IntType implements Type {
    public IntType() {}
    public boolean equals(Object another){
        return another instanceof IntType;
    }
    public Value defaultValue() {
        return new IntValue(0);
    }
    public String toString() { return "int";}
}

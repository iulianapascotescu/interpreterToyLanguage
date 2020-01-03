package model.type;
import model.value.BoolValue;
import model.value.Value;

public class BoolType implements Type {
    public BoolType() {}
    public boolean equals(Object another){
        return another instanceof BoolType;
    }
    public Value defaultValue() {
        return new BoolValue(true);
    }
    public String toString() { return "bool";}
}

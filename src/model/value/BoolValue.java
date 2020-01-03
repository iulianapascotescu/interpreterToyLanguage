package model.value;
import com.sun.org.apache.xpath.internal.operations.Bool;
import model.type.Type;
import model.type.BoolType;

public class BoolValue implements Value {
    private boolean value;

    public BoolValue(boolean v){
        value=v;
    }
    public boolean getValue(){
        return this.value;
    }
    public String toString(){
        return String.valueOf(value);
    }
    public Type getType() {
        return new BoolType();
    }
    public boolean equals(Bool object) {
        return object.equals(value);
    }
}

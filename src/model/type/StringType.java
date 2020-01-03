package model.type;
import model.value.StringValue;
import model.value.Value;

public class StringType implements Type {
    public StringType(){}
    public String toString(){
        return "string ";
    }
    public boolean equals(Object another){
        return another instanceof StringType;
    }
    public Value defaultValue() {
        return new StringValue("");
    }
}

package model.value;
import model.type.StringType;
import model.type.Type;

public class StringValue implements Value {
    private String value;

    public StringValue(String value) {
        this.value = value;
    }
    public Type getType() {
        return new StringType();
    }
    public String getValue(){
        return value;
    }
    public String toString(){
        return value;
    }
    public boolean equals(String object){
        return object.equals(value);
    }
}

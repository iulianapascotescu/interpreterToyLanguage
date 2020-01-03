package model.type;
import model.value.ReferenceValue;
import model.value.Value;

public class ReferenceType implements Type{
    private Type inner;

    public ReferenceType(Type inner) {
        this.inner = inner;
    }
    //public ReferenceType(){}
    public Value defaultValue() {
        return new ReferenceValue(0,inner);
    }
    public Type getInner() {
        return inner;
    }
    public String toString() {
        return "[reference "+ inner +"]";
    }
    public boolean equals(Object another){
        if (another instanceof ReferenceType)
            return inner.equals(((ReferenceType) another).getInner());
        else
            return false;
    }
}

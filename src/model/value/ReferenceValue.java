package model.value;
import model.type.ReferenceType;
import model.type.Type;

public class ReferenceValue implements Value {
    private int address;
    private Type locationType;

    public ReferenceValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public Type getType() {
        return new ReferenceType(locationType);
    }
    public int getAddress() { return address; }
    public Type getLocationType() { return locationType; }
    public String toString() { return "[" +"address=" + address + ", locationType=" + locationType + ']'; }
}

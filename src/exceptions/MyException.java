package exceptions;

public class MyException extends Exception {
    private String message;
    public MyException(String m){
        message=m;
    }

    public MyException() {
    }

    public String getMessage(){
        return message;
    }
}

package BaseDades;

public class MyException extends Exception{

    public MyException(String string) {
        super(string);
    }

    public MyException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    } 

}

package result;

public class InternalServiceErrorResult extends Exception{

    public InternalServiceErrorResult(String message){
        super("Error: " + message);
    }
}

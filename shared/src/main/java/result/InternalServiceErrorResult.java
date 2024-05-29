package result;

public class InternalServiceErrorResult extends Exception{

    public InternalServiceErrorResult(String message){
        String stringBuilder = STR."Error: \{message}";
        super(stringBuilder);
    }
}

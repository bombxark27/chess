package result;

public class BadRequestResult extends Exception{

    public BadRequestResult(){
        super("Error: bad request");
    }
}

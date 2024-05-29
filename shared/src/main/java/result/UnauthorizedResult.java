package result;

public class UnauthorizedResult extends Exception{
    public UnauthorizedResult(){
        super("Error: unauthorized");
    }
}

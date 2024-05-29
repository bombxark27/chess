package result;

public class AlreadyTakenResult extends Exception{
    public AlreadyTakenResult(){
        super("Error: already taken");
    }
}

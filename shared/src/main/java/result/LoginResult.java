package result;

public class LoginResult extends Result{
    String authToken;
    String username;
    public LoginResult(String authToken, String username){
        super(true,"");
    }
}

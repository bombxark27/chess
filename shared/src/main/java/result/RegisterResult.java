package result;

public class RegisterResult extends Result{
    private String username;
    private String authToken;
    public RegisterResult(String username, String authToken){
        super(true,"");
        this.username = username;
        this.authToken = authToken;
    }


    public String getUsername(){
        return username;
    }

    public String getAuthToken(){
        return authToken;
    }
}

package result;

import model.GameData;

import java.util.ArrayList;

public class Result {
    private boolean success;
    private String message;
    private String username;
    private String authToken;
    private ArrayList<GameData> games;
    private int gameID;

    public Result(boolean success, String message){

    }
}

package service;


import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;

public class ClearService {
    public void clearDatabase(){
        new MemoryAuthDAO().clearAuth();
        new MemoryGameDAO().clearGame();
        new MemoryUserDAO().clearUser();
    }
}

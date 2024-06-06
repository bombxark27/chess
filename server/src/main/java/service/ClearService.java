package service;


import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import dataaccess.SQLUserDAO;

public class ClearService {
    public void clearDatabase(){
        new MemoryAuthDAO().clearAuth();
        new MemoryGameDAO().clearGame();
        new MemoryUserDAO().clearUser();
        try {
            new SQLAuthDAO().clearAuth();
            new SQLGameDAO().clearGame();
            new SQLUserDAO().clearUser();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

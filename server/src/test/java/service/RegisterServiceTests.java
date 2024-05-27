package service;

import dataaccess.DataAccessException;

import model.AuthData;
import model.UserData;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class RegisterServiceTests {
    private RegisterService service;
    private HashMap<String,AuthData> expectedAuthDAO;
    private Collection<UserData> expectedUserDAO;


    @BeforeEach
    public void setUp(){
        service = new RegisterService();
        UserData expectedUser = new UserData("reg23","password5","reg23@email.com");


    }

}

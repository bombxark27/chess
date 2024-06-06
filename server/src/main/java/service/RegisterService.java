package service;

import dataaccess.*;

import model.AuthData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;
import result.AlreadyTakenResult;
import result.BadRequestResult;


public class RegisterService {
    public AuthData register(UserData user) throws Exception{
        MemoryAuthDAO authDataAccess = new MemoryAuthDAO();
        MemoryUserDAO userDataAccess = new MemoryUserDAO();
        SQLUserDAO sqlUserDataAccess = new SQLUserDAO();
        SQLAuthDAO sqlAuthDataAccess = new SQLAuthDAO();
        String authToken;
        AuthData result;

        if (user.username() == null || user.password() == null || user.email() == null) {
            throw new BadRequestResult();
        }
        try {
            String hashedPassword = BCrypt.hashpw(user.password(), BCrypt.gensalt());
            UserData hashedUser = new UserData(user.username(),hashedPassword,user.email());
            userDataAccess.insertUser(hashedUser);
            authToken = authDataAccess.createAuth(user.username());
            sqlAuthDataAccess.insertAuth(authDataAccess.getAuth(authToken));
            result = authDataAccess.getAuth(authToken);
        } catch (DataAccessException e) {
            throw new AlreadyTakenResult();
        }

        return result;
    }
}

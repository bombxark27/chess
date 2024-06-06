package service;

import dataaccess.*;

import model.AuthData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;
import result.UnauthorizedResult;

public class LoginService {
    public AuthData login(UserData user) throws Exception{
        MemoryUserDAO userDataAccess = new MemoryUserDAO();
        MemoryAuthDAO authDataAccess = new MemoryAuthDAO();
        AuthData result;
        try {
            UserData existingUser = userDataAccess.getUser(user.username());
            if (!BCrypt.checkpw(user.password(), existingUser.password())){
                throw new UnauthorizedResult();
            }
            String authToken = authDataAccess.createAuth(existingUser.username());
            result = authDataAccess.getAuth(authToken);
        } catch (DataAccessException e) {
            throw new UnauthorizedResult();
        }

        return result;
    }
}

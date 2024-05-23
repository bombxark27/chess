package dataaccess;

import model.AuthData;

public class MemoryAuthDAO implements AuthDAO{

    @Override
    public AuthData createAuth(String username) {
        return null;
    }

    @Override
    public AuthData getAuth(String username) throws DataAccessException{
        return null;
    }

    @Override
    public AuthData deleteAuth(String username){
        return null;
    }

    @Override
    public AuthData clear(){
        return null;
    }
}

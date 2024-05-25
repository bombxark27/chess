package dataaccess;

import model.AuthData;
import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{
    private static HashMap<String,AuthData> authDataMap = new HashMap<String, AuthData>();

    @Override
    public AuthData createAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, username);
        authDataMap.put(authToken, authData);
        return authData;
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException{
        if (authDataMap.get(authToken) == null){
            throw new DataAccessException("Auth token not found");
        }
        else{
            return authDataMap.get(authToken);
        }
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        if (authDataMap.containsKey(authToken)){
            authDataMap.remove(authToken);
        }
        else{
            throw new DataAccessException("Auth token not found");
        }
    }

    @Override
    public void clear(){
        authDataMap.clear();
    }
}

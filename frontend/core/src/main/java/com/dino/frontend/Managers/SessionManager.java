package com.dino.frontend.Managers;

public class SessionManager {
    private static SessionManager instance;
    private boolean isGuest;

    private SessionManager(){
        this.isGuest = true;
    }

    public static SessionManager getInstance(){
        if(instance == null){
            instance = new SessionManager();
        }
        return instance;
    }

    public boolean isGuest(){
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }
}

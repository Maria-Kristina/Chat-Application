package com.example.user1.chatapplication;

/**
 * Created by M-K on 5.10.2016.
 */
public interface Observable {
    public void register(Observer o);
    public void deregister(Observer o);
    void notifyUsers(String message);
}

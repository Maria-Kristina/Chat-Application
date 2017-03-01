package com.example.user1.chatapplication;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by User1 on 4.10.2016.
 */
public class ChatHistory implements Observable{
/* to which ServerReader writes the incoming messages, needs to be observable and singleton */
    private List<String> messages;
    private final Set<Observer> users;

    private ChatHistory(){
        this.messages=new ArrayList<>();
        this.users = new HashSet<>();
    }

    public void insert (String message) {
        Log.d("chatHistory", message);
        messages.add(message);
        notifyUsers(message);
    }

    public static ChatHistory getInstance() {
        return ChatHistoryHolder.INSTANCE;
    }

    @Override
    public void register(Observer o) {
        users.add(o);
    }

    @Override
    public void deregister(Observer o) {
        users.remove(o);
    }

    @Override
    public void notifyUsers(String message) {
        for(Observer o: this.users) {
            o.update(message);
            //voisiko tässä kutsua jotain metodia, joka liikuttaa scrollviewtä
        }
    }

    private static class ChatHistoryHolder {
        private static final ChatHistory INSTANCE = new ChatHistory();
    }
}

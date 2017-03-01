/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Valtteri Pesu and Maria-Kristina Sillanmäki
 * 
 */

public class ChatHistory implements Observable{
    private final List<String> userNames;
    private final List<ChatMessage> messages;
    private final Set<Observer> users;
    
    private ChatHistory() {
        this.userNames = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.users = new HashSet<>();
        
    }
    
    public void insert (ChatMessage message) {
        messages.add(message);
        notifyUsers(message);
    }
    
    public static ChatHistory getInstance() {
        return ChatHistoryHolder.INSTANCE;
    }
    
    private static class ChatHistoryHolder {

        private static final ChatHistory INSTANCE = new ChatHistory();
    }
    
    @Override
    public String toString(){ //Palauttaa historian stringinä
        String s="";
        for (ChatMessage m : messages) {
            s = s+ m+"\n";
        }
        return s;
    }
    
    @Override
    public void register(Observer o) {  //Rekisteröi chättejä
        users.add(o);
        
    }

    @Override
    public void deregister(Observer o) {
        users.remove(o);
    }

    @Override
    public void notifyUsers(ChatMessage m) {  // Lähettää messaget käyttäjille????
        for(Observer o: this.users) {
            o.update(m);
        }
    }
    
    public void userListAdd (String userName){
        userNames.add(userName);
    }
    public void userListRemove (String userName) {
        userNames.remove(userName);
    }
    public String getUserList(){
        String s="";
        for (String m : userNames) {
            s = s+ m+"\n";
        }
        return s;
    }
}

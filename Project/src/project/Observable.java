/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author Valtteri Pesu and Maria-Kristina Sillanmäki
 */
public interface Observable {
    public void register(Observer o);
    public void deregister(Observer o);
    void notifyUsers(ChatMessage m);
}

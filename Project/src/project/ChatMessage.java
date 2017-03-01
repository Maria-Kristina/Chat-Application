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
class ChatMessage {
    private final String text;
    
    public ChatMessage(String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return this.text;
    }
    
}

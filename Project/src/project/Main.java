/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.IOException;

/**
 *
 * @author Valtteri Pesu and Maria-Kristina Sillanmäki
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        ChatServer cs = new ChatServer();
        cs.serve();
        //CommandInterpreter ci = new CommandInterpreter(System.in, System.out); 
        //ci.run();
    }
    
}

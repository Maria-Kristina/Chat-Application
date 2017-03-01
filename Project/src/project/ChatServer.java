
package project;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Valtteri Pesu and Maria-Kristina Sillanmäki
 */
public class ChatServer  {
    
    public void ChatServer(){
        //moi
    }
    
    public void serve(){
        try {
            ServerSocket ss = new ServerSocket(0,3);
            System.out.println("I have socket " + ss.getLocalPort());
        
        
            while(true){
                Socket s = ss.accept();
                CommandInterpreter i = new CommandInterpreter(s.getInputStream(), new PrintStream(s.getOutputStream()));
                Thread t = new Thread(i);
                t.start();
                
                //(new Thread(new CommandInterpreter(s.getInputStream(), new PrintStream(s.getOutputStream()))).start();
                //CommandInterpreter i = new CommandInterpreter(s.getInputStream(), new PrintStream(s.getOutputStream()));
                //
        
            }
        }
        
        catch(IOException ex) {
                Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    
    
}

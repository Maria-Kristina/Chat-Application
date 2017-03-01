/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.Runnable;

import java.util.List;

/**
 *
 * @author Valtteri Pesu and Maria-Kristina Sillanmäki
 */
class CommandInterpreter implements Runnable, Observer { //<--lisätään Observer
    
    public String quit ="/quit";
    public String user ="/user";
    public String history ="/history";
    public String list ="/list";
    public int stop = 0;
    private final InputStream in;
    private final PrintStream out;
    public String input;
    public String userName;
    public BufferedReader reader;
    ChatMessage m;
    ChatHistory h;
    
   

    public CommandInterpreter(InputStream in, PrintStream out) {
        this.userName="";
        this.in = in;
        this.out = out;
        this.reader = new BufferedReader(new InputStreamReader(in));
        this.h = ChatHistory.getInstance();
        
    }

    

    
    @Override
    public void run() {
        
        out.println("Welcome to the chatserver.");
        out.flush();
        try {
            user();
            h.register(this);
            
                while (stop == 0){

                    ask();
                    
                        if(input.equals(user)){
                            user();   
                        }

                        else if(input.equals(history)){
                            history();
                        }

                        else if(input.equals(list)){
                            list();
                        }

                        else if(input.equals(quit)){
                            quit();
                        }
                        else {
                            //out.println(userName+": "+input); 
                            input=userName+": "+input; //Tallentaa messagen muotoon "Käyttäjänimi: viesti"
                            h.insert(new ChatMessage (input)); //Lisää messagen historiaan
                        }
                        
                }
                h.userListRemove(userName);
                h.deregister(this);
                out.flush();
                
        }
        catch (IOException ex) {
            Logger.getLogger(CommandInterpreter.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    @Override
    public void update(ChatMessage m) { //metodi observer interfacesta
        out.println(m);
    }
    
    synchronized private void ask(){ 
        try {
        //Pyytää käyttäjältä syötettä
        input = reader.readLine();
        } catch (IOException ex) {
            out.flush();
            h.userListRemove(userName);
            h.deregister(this);
            stop++; 
        }
    }
    
    private void quit() throws IOException{ //Lopettaa sovelluksen, KESKEN
        /*out.flush();
        h.userListRemove(userName);
        h.deregister(this);
        in.close();
        out.close();*/
        stop++; 
    }

    private void user() throws IOException{ //Tämän pitää vielä tallentaa username johonkin
        if (userName != null){
            h.userListRemove(userName);
        }
        
        while (true){
            out.println("\nType a username: ");
            //out.flush();
            userName = reader.readLine(); //Vastaanottaa käyttäjältä usernamen
            
            if(userName.equals("") || userName.charAt(0)== '/'){
                out.println("Not accepted username");
                out.flush();
            } else {
                h.userListAdd(userName);
                out.println("\nUsername is: "+userName+"\n");
                out.flush();
                break;
            }
        }
    }

    private void history() { //Tulostaa historian
        out.println("\nHere is your chat history: \n"); 
        out.println(h+"\n"); //historia on string muodossa muuttujassa h
        out.flush();
    }

    private void list() { //Käyttäjien tulostusta täytyy hioa, muuten valmis
        out.println("\nThese users are online: ");
        out.println(h.getUserList());
        out.flush();
    }

}

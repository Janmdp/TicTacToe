package classes;

import javax.websocket.Session;

public class User {
    //fields
    private int id;
    private transient Session session;
    private String name;
    private String password;
    private boolean turn = false;
    private String jwtToken;

    //getters and setters
    public int getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Session getSession() { return session; }
    public void setSession(Session session) { this.session = session; }

    public boolean isTurn() { return turn; }
    public void setTurn(boolean turn) { this.turn = turn; }

    public String getJwtToken() { return jwtToken; }
    public void setJwtToken(String jwtToken) { this.jwtToken = jwtToken; }

    //constructors
    public User(){

    }

    public User(int id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }


    //methods

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}

package YummyList;

public class Member {
    private String username;
    private String password;
    private int points;

    public Member(String username, String password, int points){
        this.username = username;
        this.password = password;
        this.points = points;
    }

    public String getUsername(){ return username; }
    public int getPoints(){ return points; }

    public boolean checkPassword(String password){
        return this.password.equals(password);
    }

    public void addPoints(int amount){
        points += amount;
    }

    @Override
    public String toString(){
        return username + "," + password + "," + points;
    }
}
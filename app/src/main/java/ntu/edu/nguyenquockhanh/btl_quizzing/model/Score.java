package ntu.edu.nguyenquockhanh.btl_quizzing.model;

public class Score {
    int id;
    String username;
    int score;

    public Score(int id, String name, int score) {
        this.id = id;
        this.username = name;
        this.score = score;
    }

    public int getId() {
        return id;
    }


    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }
}

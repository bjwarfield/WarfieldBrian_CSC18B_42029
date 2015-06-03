package ShootEmUp_V2.Users;

import ShootEmUp_V2.Util.DBConnect;
import java.util.ArrayList;

public class Session {

    private int userID;
    private int sessionID;
    private ArrayList<Score> scores;
    private boolean submitted = false;

    public Session() {
        this.userID = User.getUserID();
        this.sessionID = DBConnect.newSession(userID);
        this.scores = new ArrayList<>();
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getSessionScore() {
        int total = 0;
        for (Score score : scores) {
            total += score.getPoints();
        }
        return total;
    }

    public void addScore(int levelID, boolean win, int points, int shots, int shotsHit, float quickHit) {
        scores.add(new Score(levelID, win, points, shots, shotsHit, quickHit));
    }

    public void submitSession() {
        if (!submitted) {
            DBConnect.submitSession(sessionID, scores);
            this.submitted = true;
        }
    }
}

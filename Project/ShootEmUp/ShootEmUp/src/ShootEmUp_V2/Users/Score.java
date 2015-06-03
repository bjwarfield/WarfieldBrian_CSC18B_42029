package ShootEmUp_V2.Users;

//score data collection
public class Score {

    private final int levelID;
    private final boolean win;
    private final int points;
    private final int shots;
    private final int shotsHit;
    private final float quickHit;

    Score(int levelID, boolean win, int points, int shots, int shotsHit, float quickHit) {
        this.levelID = levelID;
        this.win = win;
        this.points = points;
        this.shots = shots;
        this.shotsHit = shotsHit;
        this.quickHit = quickHit;
    }

    public int getLevelID() {
        return levelID;
    }

    public boolean isWin() {
        return win;
    }

    public int getPoints() {
        return points;
    }

    public int getShots() {
        return shots;
    }

    public int getShotsHit() {
        return shotsHit;
    }

    public float getQuickHit() {
        return quickHit;
    }

}

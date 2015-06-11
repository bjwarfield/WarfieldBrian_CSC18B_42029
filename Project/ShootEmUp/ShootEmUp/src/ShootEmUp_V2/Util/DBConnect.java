package ShootEmUp_V2.Util;

import ShootEmUp_V2.Users.Score;
import ShootEmUp_V2.Users.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.digest.DigestUtils;

public class DBConnect {

//    //laptop xampp server from desktop    
//    static final String DB_URL = "jdbc:mysql://192.168.1.3:3306/shootem";
//    static final String DB_USER = "b00t";
//    static final String DB_PASS = "hardb00t";

//    //local xampp Server
//    static final String DB_URL = "jdbc:mysql://localhost:3306/shootem";
//    static final String DB_USER = "root";
//    static final String DB_PASS = "";

    //class server
    static final String DB_URL = "jdbc:mysql://209.129.8.4:3306/42029";
    static final String DB_USER = "42029";
    static final String DB_PASS = "42029csc18b";
    
    
//    public static void DBConnect(String query) {
//        Connection connection = null;
//        Statement statement = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//
//            statement = connection.createStatement();
//
//            resultSet = statement.executeQuery(query);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                resultSet.close();
//                statement.close();
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    //check for existing email
    //@param email to check
    //@return number in rows including email
    public static int checkEmail(String email) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int numRows = 0;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stmt = conn.prepareStatement("SELECT count(*) FROM `bw1780661_entity_user` WHERE `email` = ?;");

            stmt.setString(1, email.trim());
            rs = stmt.executeQuery();

            while (rs.next()) {
                numRows = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(rs, stmt, conn);
        }

        return numRows;
    }

    //add user information to database
    //@param fName User First Name
    //@param lName User Last Name
    //@param handle User handle
    //@param email User email
    //@param pass User password
    //@return number of updated records
    public static int register(String fName, String lName, String handle, String email, String pass) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int numRows = 0;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stmt = conn.prepareStatement("INSERT INTO `bw1780661_entity_user` (`first_name`, `last_name`, `handle`, `email`, `password`, date_created) VALUES(?, ?, ?, ?, ?, now());");

            stmt.setString(1, fName.trim());
            stmt.setString(2, lName.trim());
            stmt.setString(3, handle.trim());
            stmt.setString(4, email.trim());
            stmt.setString(5, DigestUtils.sha1Hex(pass.trim()));

            numRows = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(rs, stmt, conn);
        }
        return numRows;
    }

    //check login credentials, login if match
    //@param email User emaul
    //@param pass User password
    //return ArrayList {true, userID, Handle} if login match, (false, errorMessageString} if not
    public static boolean login(String email, String pass) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int numRows = 0;
        int id = 1;
        String handle = null;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stmt = conn.prepareStatement("SELECT `user_id`, `handle` FROM `bw1780661_entity_user` WHERE `email` = ? AND `password` =  ?;");
            stmt.setString(1, email.trim());
            stmt.setString(2, DigestUtils.sha1Hex(pass.trim()));

            rs = stmt.executeQuery();

            while (rs.next()) {
                numRows++;
                id = rs.getInt("user_id");
                handle = rs.getString("handle");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(rs, stmt, conn);
        }
        if (numRows == 1) {
            User.login(id, handle);
            return true;
        } else {
            return false;
        }
    }

    
    //create new game session
    //@param userID current user ID#
    //@return sessionID number
    public static int newSession(int userID) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int sessionID = 0;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stmt = conn.prepareStatement("INSERT INTO `bw1780661_entity_game_session` (user_id) VALUES (?);", Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, userID);
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            rs.next();
            sessionID = rs.getInt(1);

        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } finally {
            closeConnections(rs, stmt, conn);
        }
        return sessionID;
    }

    
    //submit game session to DB
    //@param sessionID session ID number
    //@param scores arrayList of scores in the session
    public static void submitSession(int sessionID, ArrayList<Score> scores) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Integer> scoreIDs = new ArrayList<>();
        
        
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stmt = conn.prepareStatement("INSERT INTO `bw1780661_entity_score` ( level_id, win, score, shots, shots_hit, quick_hit) VALUES (?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
            for(Score score:scores){
                stmt.setInt(1, score.getLevelID());
                stmt.setBoolean(2, score.isWin());
                stmt.setInt(3, score.getPoints());
                stmt.setInt(4, score.getShots());
                stmt.setInt(5, score.getShotsHit());
                stmt.setFloat(6, score.getQuickHit());
                scoreIDs.add(stmt.executeUpdate());
            }
            
            stmt = conn.prepareCall("INSERT INTO `bw1780661_xref_session_score` (session_id, score_id) VALUES (?,?);");
            
            for(int scoreID : scoreIDs){
                stmt.setInt(1, sessionID);
                stmt.setInt(2, scoreID);
                stmt.executeUpdate();
            }
            
            
        }catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } finally {
            closeConnections(rs, stmt, conn);
        }
       
    }

    //close database connection variables
    //
    private static void closeConnections(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

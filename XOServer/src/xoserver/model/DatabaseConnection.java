/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoserver.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.ClientDriver;

/**
 *
 * @author Taha
 */
public class DatabaseConnection {

    private Connection con;
    private static DatabaseConnection databaseObject;
    private PreparedStatement pst;
    private PreparedStatement pst1;
    private Statement stmt;
    private ResultSet rs;
    public static Vector<String> playerList;

    private DatabaseConnection() {
        playerList = new Vector<>();
    }

    public static DatabaseConnection getDatabaseInstance() {
        if (databaseObject == null) {
            databaseObject = new DatabaseConnection();
        }
        return databaseObject;
    }

    public void openConnection() {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/UsersDatabase", "amr", "amr");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addUser(String username, String password) {
        try {
            pst = con.prepareStatement("insert into AMR.users (Username,password) VALUES (?,?)");
            pst.setString(1, username);
            pst.setString(2, password);
            pst.executeUpdate();
            pst.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkUserExistance(String user) {
        try {
            pst = con.prepareStatement("select username from AMR.users where USERNAME=?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.setString(1, user);
            rs = pst.executeQuery();
            if (!rs.next()) {
                pst.close();
                rs.close();
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean checkUserPassword(String user, String pass) {
        try {
            String databasePass;
            pst = con.prepareStatement("select password from AMR.users where USERNAME=?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.setString(1, user);
            rs = pst.executeQuery();
            rs.next();
            databasePass = rs.getString(1);

            if (databasePass.equals(pass)) {
                pst.close();
                rs.close();
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void signInPlayer(String user) {
        try {
            pst = con.prepareStatement("update AMR.users set STATUS=? where username=?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pst.setBoolean(1, true);
            pst.setString(2, user);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int numOnlinePlayers() {
        int online = 0;
        try {

            pst = con.prepareStatement("select * from AMR.users where status=true", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = pst.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                ++online;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return online;
    }

    public int numOfflinePlayers() {
        int offline = 0;
        try {

            pst = con.prepareStatement("select * from AMR.users where status = false", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = pst.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                ++offline;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return offline;
    }

    public int getScore(String username) {
        int score = 0;
        try {
            pst = con.prepareStatement("select score from AMR.users where username = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.setString(1, username);
            rs = pst.executeQuery();
            if (rs.next()) {
                score = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return score;
    }

  
    public void setPlaying(String user) {
        try {
            pst = con.prepareStatement("update AMR.users set PLAYSTATUS=? where username=?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.setBoolean(1, true);
            pst.setString(2, user);
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setNotPlaying(String user) {
        try {
            pst = con.prepareStatement("update AMR.users set PLAYSTATUS=? where username=?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.setBoolean(1, false);
            pst.setString(2, user);
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setScore(String user, int scr) {
        try {
            pst1 = con.prepareStatement("update AMR.users set SCORE=? where username=?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst1.setInt(1, scr);
            pst1.setString(2, user);
            pst1.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isOnline(String username) {
        boolean onlineStatus = false;
        try {
            pst = con.prepareStatement("select STATUS from AMR.users where username = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.setString(1, username);
            rs = pst.executeQuery();
            rs.next();
            onlineStatus = rs.getBoolean(1);
            rs.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return onlineStatus;
    }

    public String getOnlinePlayersList() {
        String players = null;
        playerList.clear();
        try {
            pst = con.prepareStatement("select USERNAME from AMR.users where status = true and playstatus = false",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // solved rollback exception and
            // delay time
            rs = pst.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                playerList.add(rs.getString(1));
            }

            for (String s : playerList) {
                if (players == null) {
                    players = "PLIST#" + s;
                } else {
                    players = players + ("#" + s);
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(players);
        try {
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return players;
    }

    public String getPlayingPlayersList() {
        String players = null;
        playerList.clear();
        try {
            pst = con.prepareStatement("select USERNAME from AMR.users where status = true and playstatus = true",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // solved rollback exception and
            // delay time
            rs = pst.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                playerList.add(rs.getString(1));
            }

            for (String s : playerList) {
                if (players == null) {
                    players = "PLIST#" + s;
                } else {
                    players = players + ("#" + s);
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return players;
    }

    public void signOutPlayer(String user) {
        try {
            pst = con.prepareStatement("update AMR.users set STATUS=?,PLAYSTATUS=? where username=?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pst.setBoolean(1, false);
            pst.setBoolean(2, false);
            pst.setString(3, user);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setOpeningServer() {
        try {
            pst = con.prepareStatement("update AMR.users set STATUS=?,PLAYSTATUS=?", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            pst.setBoolean(1, false);
            pst.setBoolean(2, false);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * ************* USER RECORDING TABLE Queries ***********************
     */
    public void setRecord(String sender, String mainPlayer, String secondPlayer, String gameMoves) {
        try {
            pst = con.prepareStatement(
                    "insert into AMR.userrecording (mainuser,secuser,record,sender) VALUES (?,?,?,?)");
            pst.setString(1, mainPlayer);
            pst.setString(2, secondPlayer);
            pst.setString(3, gameMoves);
            pst.setString(4, sender);
            pst.executeUpdate();
            // pst.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getRecord(String reqSender) {
        boolean firstTimeFlag = true;
        String gameMoves = "GETREC#";
        ArrayList<String> recList = new ArrayList<>();
        int i = 0;
        try {
            pst = con.prepareStatement(
                    "select mainuser, secuser, record,sender from AMR.userrecording where sender = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // solved rollback exception and
            // delay time
            pst.setString(1, reqSender);
            rs = pst.executeQuery();
            rs.afterLast();
            while (rs.previous() && i < 5) {
                if (firstTimeFlag) {
                    gameMoves += rs.getString(4) + "@";
                    firstTimeFlag = false;
                }
                gameMoves += rs.getString(1) + "#" + rs.getString(2) + "#" + rs.getString(3) + "!";
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gameMoves;
    }
}

package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

// message_id integer primary key auto_increment,
// posted_by integer,
// message_text varchar(255),
// time_posted_epoch long,
// foreign key (posted_by) references Account(account_id)

public class MessageDAO {
    public Message postMessage(Message message) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";
            PreparedStatement prepState = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prepState.setInt(1, message.posted_by);
            prepState.setString(2, message.message_text);
            prepState.setLong(3, message.time_posted_epoch);
            prepState.executeUpdate();
            ResultSet result = prepState.getGeneratedKeys();
            if (result.next()) {
                int generated_message_id = (int) result.getLong(1);
                return new Message(generated_message_id, message.posted_by, message.message_text, message.time_posted_epoch);                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public List<Message> getMessages() {
        Connection conn = ConnectionUtil.getConnection();
        ArrayList<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement prepState = conn.prepareStatement(sql);
            ResultSet result = prepState.executeQuery();
            while (result.next()) {
                Message message = new Message(result.getInt("message_id"), result.getInt("posted_by"), result.getString("message_text"), result.getLong("time_posted_epoch"));
                messages.add(message);
            }
            return messages;          
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Message getMessage(int ID) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id=?";
            PreparedStatement prepState = conn.prepareStatement(sql);
            prepState.setInt(1, ID);
            ResultSet result = prepState.executeQuery();
            if (result.next()){
                return new Message(ID, result.getInt("posted_by"), result.getString("message_text"), result.getLong("time_posted_epoch"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Message deleteMessage(int ID) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            Message message = this.getMessage(ID);
            if (message == null) return message;
            String sql = "DELETE FROM message WHERE message_id=?";
            PreparedStatement prepState = conn.prepareStatement(sql);
            prepState.setInt(1, ID);
            prepState.executeUpdate();
            return message;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Message patchMessage(int ID, String text) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            Message message = this.getMessage(ID);
            if (message == null) return message;
            String sql = "UPDATE message SET message_text=? WHERE message_id=?";
            PreparedStatement prepState = conn.prepareStatement(sql);
            prepState.setString(1, text);
            prepState.setInt(2, ID);
            prepState.executeUpdate();            
            message = this.getMessage(ID);
            return message;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public List<Message> getMessagesByAccount(int ID) {
        Connection conn = ConnectionUtil.getConnection();
        ArrayList<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by=?";
            PreparedStatement prepState = conn.prepareStatement(sql);
            prepState.setInt(1, ID);
            ResultSet result = prepState.executeQuery();
            while (result.next()) {
                Message message = new Message(result.getInt("message_id"), result.getInt("posted_by"), result.getString("message_text"), result.getLong("time_posted_epoch"));
                messages.add(message);
            }
            return messages;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

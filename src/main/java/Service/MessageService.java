package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    
    private MessageDAO messageDAO;
    public MessageService() {
        this.messageDAO = new MessageDAO();
    }
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }
    public Message addMessage(Message message) {
        return this.messageDAO.postMessage(message);
    }
    public List<Message> getMessages() {
        return this.messageDAO.getMessages();
    }
    public Message getMessage(int ID) {
        return this.messageDAO.getMessage(ID);
    }
    public Message deleteMessage(int ID) {
        return this.messageDAO.deleteMessage(ID);
    }
    public Message patchMessage(int ID, String text) {
        return this.messageDAO.patchMessage(ID, text);
    }
    public List<Message> getMessagesByAccount(int ID) {
        return this.messageDAO.getMessagesByAccount(ID);
    }
}

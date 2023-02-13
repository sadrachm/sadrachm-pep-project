package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        // app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::register);
        app.post("/login", this::login);
        app.post("/messages", this::postMessage);
        app.get("/messages", this::getMessages);
        app.get("/messages/{message_id}", this::getMessage);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }
    private void register(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        if (account.getUsername() == "" || account.getPassword().length() < 4) {
            ctx.status(400);
            return;
        }
        Account addedAccount = accountService.addAccount(account);
        if (addedAccount!=null) {
            ctx.json(mapper.writeValueAsString(addedAccount));
        } else {
            ctx.status(400);
        }      
    }
    private void login(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account foundAccount = accountService.getAccountByUserAndPass(account);
        if (foundAccount!= null) {
            ctx.json(mapper.writeValueAsString(foundAccount));
        } else ctx.status(401);        
    }
    private void postMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        if (message.message_text.length() == 0 || message.message_text.length() > 255) {
            ctx.status(400);
            return;
        }
        Message addedMessage = messageService.addMessage(message);
        if (addedMessage!=null) {
            ctx.json(mapper.writeValueAsString(addedMessage));
        }else {
            ctx.status(400);
        }
    }
    private void getMessages(Context ctx) {
        ctx.json(messageService.getMessages());
    }
    private void getMessage(Context ctx) throws JsonProcessingException {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        ctx.json(messageService.getMessage(message_id));
    }


}
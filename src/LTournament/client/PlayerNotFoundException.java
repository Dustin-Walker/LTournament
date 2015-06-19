package LTournament.client;

/**
 * Created by user on 6/19/15.
 */
public class PlayerNotFoundException extends Exception {

    public PlayerNotFoundException(String message){
        super(message);
    }

    public PlayerNotFoundException(String message, Throwable throwable){
        super(message, throwable);
    }

    public PlayerNotFoundException() {
    }
}

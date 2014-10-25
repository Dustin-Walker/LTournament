package LTournament.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import LTournament.client.LTournamentService;

public class LTournamentServiceImpl extends RemoteServiceServlet implements LTournamentService {
    // Implementation of sample interface method
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }
}
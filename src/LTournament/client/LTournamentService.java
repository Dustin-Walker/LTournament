package LTournament.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("LTournamentService")
public interface

        LTournamentService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);

    /**
     * Utility/Convenience class.
     * Use LTournamentService.App.getInstance() to access static instance of LTournamentServiceAsync
     */
    public static class App {
        private static LTournamentServiceAsync ourInstance = GWT.create(LTournamentService.class);

        public static synchronized LTournamentServiceAsync getInstance() {
            return ourInstance;
        }
    }
}

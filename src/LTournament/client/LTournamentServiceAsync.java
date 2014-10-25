package LTournament.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LTournamentServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);
}

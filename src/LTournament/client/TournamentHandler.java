package LTournament.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

/**
 * Created by user on 6/4/15.
 * This class handles events from the user.
 */
public class TournamentHandler {


    public boolean addPlayer(){
        // TODO This method should make the API call to League's servers
        GUI.rosterListLabel.setText("WORDS");
        return true;
    }

    public boolean resetPlayerList(){
        return true;
    }

    public boolean startTeamPhase(){
        return true;
    }

    public boolean startTournamentPhase(){
        return true;
    }




}

package LTournament.client;

import com.google.gwt.http.client.*;
import com.google.gwt.user.client.ui.*;

/**
 * Created by user on 6/4/15.
 * This class handles events from the user.
 */
public class TournamentHandler {

    // URLs for REST calls
    private static final String BY_NAME_URL = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/";
    private static final String leagueEntries_URL = "https://na.api.pvp.net/api/lol/na/v2.5/league/by-summoner/";
    //API Key goes here
    private static final String APIKEY = "?api_key=0fe5e184-13db-40a8-9100-bcc29c664cd2";

    public boolean addPlayer(){
        // TODO This method should make the API call to League's servers


        // Use the player name as the hash key
        String playerName = GUI.getPlayerName();

        if (Tournament.summonerNameList.containsKey(playerName)){
            // This is a duplicate entry
            GUI.setBootstrapAlert("<div class=\"alert alert-danger\" role=\"alert\"><strong>Warning!</strong>\nThis player is already in the game.</div>");
        } else {
            // This is a new entry
            GUI.rosterListLabel.setText("test");
            String summonerNameURL = BY_NAME_URL+playerName+APIKEY;
            // Send request to the server
            RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, summonerNameURL);
            builder.setCallback(new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    switch (response.getStatusCode()){
                        case 404: GUI.setBootstrapAlert("<div class=\"alert alert-danger text-center\" role=\"alert\"><strong>Warning!</strong><br />Player not found.</div>");
                            break;
                        case 200: GUI.setBootstrapAlert("<div class=\"alert alert-success text-center\" role=\"alert\"><strong>Success!</strong><br />Player successfully added.</div>");
                            break;
                    }
                    GUI.rosterListLabel.setText("words");
                }

                @Override
                public void onError(Request request, Throwable exception) {
                    GUI.rosterListLabel.setText("error");
                }
            });
            try {
                builder.send();
            } catch (RequestException e) {
                e.printStackTrace();
            }
        }



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

package LTournament.client;

import com.google.gwt.http.client.*;

import java.util.ArrayList;

/**
 * Created by user on 6/4/15.
 * This class handles events from the user.
 */
public class TournamentHandler {

    // URLs for REST calls
    private static final String BY_NAME_URL = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/";
    private static final String BY_ID_URL = "https://na.api.pvp.net/api/lol/na/v2.5/league/by-summoner/";
    //API Key goes here
    private static final String APIKEY = "?api_key=0fe5e184-13db-40a8-9100-bcc29c664cd2";

    // Non-GWT objects
    ArrayList<Player> playerDataList = new ArrayList<Player>();

    /**
     * This method makes two calls to the league of legends API. The first call uses the summoner name to gather
     * more information, specifically the summoner ID number. The second call uses the summoner ID number to obtain
     * the rest of the information used to build the player object.
     * @return true for a successful call, false if unsuccessful
     */
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
                        case 404: // Player not found
                            GUI.setBootstrapAlert("<div class=\"alert alert-danger text-center\" role=\"alert\"><strong>Warning!</strong><br />Player not found.</div>");
                            break;
                        case 429: // Rate limit exceeded
                            break;
                        case 500: // Server error
                            break;
                        case 503: // Server error
                            break;
                        case 200: // Success
                            GUI.setBootstrapAlert("<div class=\"alert alert-success text-center\" role=\"alert\"><strong>Success!</strong><br />Player successfully added.</div>");
                            if(!GUI.playerPanel.isVisible())
                                GUI.playerPanel.setVisible(true);
                            String responseText = response.getText();
                            // Create new player object using response
                            Player player = new Player(responseText);
                            playerDataList.add(player);
                            String summonerByIDURL = BY_ID_URL +player.getPlayerID()+"/entry"+APIKEY;
                            // Continue to build the player data with the next call
                            RequestBuilder builder1 = new RequestBuilder(RequestBuilder.GET, summonerByIDURL);
                            builder1.setCallback(new RequestCallback() {
                                @Override
                                public void onResponseReceived(Request request, Response response) {
                                    final int rowCount = GUI.rosterTableRowCount();
                                    String responseText1 = response.getText();
                                    final Player localPlayer = playerDataList.get(playerDataList.size()-1);
                                    // TODO Remake the player icon section, put it into its own method
                                    // TODO Add player to the GUI
                                }

                                @Override
                                public void onError(Request request, Throwable exception) {
                                    // Something went very wrong if things ever end up here
                                }
                            });
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

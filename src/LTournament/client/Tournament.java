package LTournament.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by user on 5/28/15.
 * This class contains methods for generating the tournament.
 */
public class Tournament {
    /**
     * This method adds a player to the roster table and to the internal player list.
     * This method is called by both keyboard and mouse click events for entry of player data.
     */

  /*          //Non-GWT objects
    ArrayList playerDataList = new ArrayList();
    ArrayList<String> summonerNameList = new ArrayList<String>();
    final private String[] teamNameArray = {"Team 1", "Team 2", "Team 3", "Team 4", "Team 5", "Team 6", "Team 7",
            "Team 8", "Team 9", "Team 10", "Team 11", "Team 12", "Team 13", "Team 14", "Team 15", "Team 16",
            "Team 17", "Team 18", "Team 19", "Team 20"};
    private ArrayList<String> teamNameList = new ArrayList<String>();
    // URLs for REST calls
    private static final String summonerByName_URL = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/";
    private static final String leagueEntries_URL = "https://na.api.pvp.net/api/lol/na/v2.5/league/by-summoner/";
    //API Key goes here
    private static final String APIKEY = "?api_key=581e4a04-deb0-4e70-898f-765ad96e2016";

    public Player addPlayerEvent(String name){
        summonerNameList.add(name);
        String url = summonerByName_URL+name+APIKEY;
        // Send request to server and catch any errors
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
        builder.setCallback(new RequestCallback() {
            @Override
            public void onResponseReceived(Request request, Response response) {
                if (response.getStatusCode() == 404){
                    // TODO Style this box better
                    DialogBox dbox = LTournament.alertWidget("Please try again", "Summoner not found");
                    dbox.show();
                    dbox.addStyleName("missing-player-popup");
                }
                if (response.getStatusCode() == 429){
                    // TODO rate limit exceeded error
                    //NEEDS TO BE TESTED - doesnt work
                    //LTournament.alertWidget("Adding player failed", "You are trying to add players too quickly.");
                }
                if (response.getStatusCode() == 503 || response.getStatusCode() == 500){
                    // TODO error about server
                    //NEEDS TO BE TESTED - doesnt work
                    //LTournament.alertWidget("Adding player failed", "Server currently unavailable.");
                }
                if (200 == response.getStatusCode()){
                if(!playerPanel.isVisible())
                    playerPanel.setVisible(true);
                int row = rosterTable.getRowCount();
                String playerName = response.getText();
                Player newPlayerData = new Player(playerName);
                    playerDataList.add(newPlayerData);
                    String leagueEntryURL = leagueEntries_URL+newPlayerData.getPlayerID()+"/entry"+APIKEY;
                    RequestBuilder builder2 =new RequestBuilder(RequestBuilder.GET, leagueEntryURL);
                    builder2.setCallback(new RequestCallback() {
                        @Override
                        public void onResponseReceived(Request request, Response response) {
                            final int row = rosterTable.getRowCount();
                            String getResponse = response.getText();
                            final Player localPlayerData = playerDataList.get(playerDataList.size()-1);
                            String playerRankIcon="";
                            // TODO Move this into its own method
                            if(getResponse.contains("CHALLENGER")){
                                localPlayerData.setRank("CHALLENGER");
                                playerRankIcon = ("<img src=\"img/challenger_icon_24.png\" >");
                            } else if(getResponse.contains("DIAMOND")){
                                localPlayerData.setRank("DIAMOND");
                                playerRankIcon = ("<img src=\"img/diamond_icon_24.png\" >");
                            } else if(getResponse.contains("PLATINUM")){
                                localPlayerData.setRank("PLATINUM");
                                playerRankIcon = ("<img src=\"img/platinum_icon_24.png\" >");
                            } else if(getResponse.contains("GOLD")){
                                localPlayerData.setRank("GOLD");
                                playerRankIcon = ("<img src=\"img/gold_icon_24.png\" >");
                            } else if (getResponse.contains("SILVER")) {
                                localPlayerData.setRank("SILVER");
                                playerRankIcon = ("<img src=\"img/silver_icon_24.png\" >");
                            } else if (getResponse.contains("BRONZE")) {
                                localPlayerData.setRank("BRONZE");
                                playerRankIcon = ("<img src=\"img/bronze_icon_24.png\" >");
                            } else {
                                localPlayerData.setRank("UNRANKED");
                                playerRankIcon = ("<img src=\"img/unranked_icon_24.png\" >");
                            }
                            rosterTable.setHTML(row-1,0,playerRankIcon );
                            playerDataList.get(playerDataList.size() - 1).setRank(localPlayerData.getRank());
                            final Button removePlayerButton = new Button();
                            removePlayerButton.addStyleName("remove-button");
                            rosterTable.setWidget(row-1, 2, removePlayerButton);
                            removePlayerButton.addClickHandler(new ClickHandler() {
                                @Override
                                public void onClick(ClickEvent event) {
                                    int rowIndex = rosterTable.getCellForEvent(event).getRowIndex();
                                    setCreatorButtonVis();
                                    rosterTable.removeRow(rowIndex);
                                    summonerNameList.remove(localPlayerData.toString());
                                    playerDataList.remove(localPlayerData);
                                    //controlPanel.add(new Label(playerName));
                                    if(playerDataList.isEmpty()){
                                        playerPanel.setVisible(false);
                                    }
                                }
                            });
                        }
                        @Override
                        public void onError(Request request, Throwable exception) {
                            // TODO Add something to the error section when this REST call fails.
                        }
                    });
                    try {
                        builder2.send();
                    } catch (RequestException e) {
                        e.printStackTrace();
                    }
                    rosterTable.setText(row, 1, newPlayerData.getSummonerName());
                    rosterTable.getRowFormatter().addStyleName(row, "player-list-entry");
                    newPlayerNameTextBox.setText("");
                    setCreatorButtonVis();
                }
            }
            @Override
            public void onError(Request request, Throwable exception) {
                // TODO Generate error message for when REST calls fail. Maybe a popup window.
            }
        });
        try {
            builder.send();
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * This method generates teams based on a randomization algorithm. These teams are teams of 5 to be used
     * for the Summoner's Rift map and any other 5 player per team map. Teams of 3 for the other map.
     * @param players Collection of all the players to put into teams
     * @return List of teams containing players
     */
    private HashMap generateTeams(HashMap players){
        return new HashMap();
    }

}
package LTournament.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.ui.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * Created by user on 6/4/15.
 * This class handles events from the user.
 */
public class TournamentHandler {

    private final String BY_ID_URL = "https://na.api.pvp.net/api/lol/na/v2.5/league/by-summoner/";
    //API Key goes here
    private final String APIKEY = "?api_key=0fe5e184-13db-40a8-9100-bcc29c664cd2";
    // Bootstrap alerts
    final String playerNotFoundWarning = "<div class=\"alert alert-danger text-center\" role=\"alert\"><strong>Warning!</strong><br />Player not found.</div>";
    final String rateLimitWarning = "<div class=\"alert alert-danger text-center\" role=\"alert\"><strong>Slow down!</strong><br />You are sending too many requests.</div>";
    final String serverErrorWarning = "<div class=\"alert alert-danger text-center\" role=\"alert\"><strong>Warning!</strong><br />The remote server encountered an error.</div>";
    final String successAlert = "<div class=\"alert alert-success\" role=\"alert\">Success!<br />Player added.</div>";
    final String duplicatePlayerWarning = "<div class=\"alert alert-danger\" role=\"alert\"><strong>Warning!</strong>\nThis player is<br />already in the game.</div>";
    final String maxPlayersWarning = "<div class=\"alert alert-danger\" role=\"alert\"><strong>Warning!</strong>\nYou have reached<br />the maximum number of<br />supported players..</div>";
    final String successfulTeamCreation = "<div class=\"alert alert-success\" role=\"alert\">Success!<br />Teams created.</div>";;

    // Non-GWT objects
    ArrayList<Player> playerDataList = new ArrayList<Player>();
    ArrayList<Team> teams = new ArrayList<Team>();



    /**
     * This method makes two calls to the league of legends API. The first call uses the summoner name to gather
     * more information, specifically the summoner ID number. The second call uses the summoner ID number to obtain
     * the rest of the information used to build the player object.
     */
    public void addPlayer(){
        // TODO This method should make the API call to League's servers


        // Use the player name as the hash key
        final String playerName = GUI.getPlayerName();

        // This lines prevents an error where the reset button was not clearing the playerDataList which was causing a bug
        if (Tournament.summonerNameList.isEmpty())
            playerDataList.clear();

        if (Tournament.summonerNameList.containsKey(playerName)){
            // This is a duplicate entry
            GUI.setBootstrapAlert(duplicatePlayerWarning);
        } else if (playerDataList.size()>=100){
            // This system supports 100 players at most.
            GUI.setBootstrapAlert(maxPlayersWarning);
        } else {
            String BY_NAME_URL = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/";
            String summonerNameURL = BY_NAME_URL +playerName+APIKEY;
            // Send request to the server
            RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, summonerNameURL);
            builder.setCallback(new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    switch (response.getStatusCode()){
                        case 404: // Player not found
                            GUI.setBootstrapAlert(playerNotFoundWarning);
                            break;
                        case 429: // Rate limit exceeded
                            GUI.setBootstrapAlert(rateLimitWarning);
                            break;
                        case 500: // Server error
                            GUI.setBootstrapAlert(serverErrorWarning);
                            break;
                        case 503: // Server error
                            GUI.setBootstrapAlert(serverErrorWarning);
                            break;
                        case 200: // Success
                            if(!GUI.playerPanel.isVisible())
                                GUI.playerPanel.setVisible(true);
                            String responseText = response.getText();
                            // Create new player object using response
                            Player player = new Player(responseText);
                            playerDataList.add(player);
                            Tournament.summonerNameList.put(playerName, player);
                            String summonerByIDURL = BY_ID_URL +player.getPlayerID()+"/entry"+APIKEY;
                            // Continue to build the player data with the next call
                            RequestBuilder builder1 = new RequestBuilder(RequestBuilder.GET, summonerByIDURL);
                            builder1.setCallback(new RequestCallback() {
                                @Override
                                public void onResponseReceived(Request request, Response response) {
                                    int playerListSize = playerDataList.size()-1;
                                    int row = playerListSize/10;
                                    int column = playerListSize%10;
                                    final Player localPlayer = playerDataList.get(playerListSize);
                                    localPlayer.setRank(response.getText());
                                    final HorizontalPanel playerPanel = new HorizontalPanel();
                                    final Label nameLabel = new Label(localPlayer.getSummonerName());
                                    nameLabel.addStyleName("h4");
                                    playerPanel.add(nameLabel);
                                    final Button removePlayerButton = new Button();
                                    removePlayerButton.setStyleName("btn btn-default remove-btn");
                                    removePlayerButton.setHTML("<span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span>");
                                    playerPanel.add(removePlayerButton);
                                    removePlayerButton.addClickHandler(new ClickHandler() {
                                        @Override
                                        public void onClick(ClickEvent event) {
                                            final int rowIndex = playerDataList.indexOf(localPlayer) / 10;
                                            final int colIndex = playerDataList.indexOf(localPlayer) % 10;
                                            GUI.rosterTable.removeCell(rowIndex, colIndex);
                                            playerDataList.remove(localPlayer);
                                            Tournament.summonerNameList.remove(playerName);
                                            if (Tournament.summonerNameList.isEmpty())
                                                playerPanel.setVisible(false);
                                            if (playerDataList.size() < 6 && GUI.matchmakingBy3.isVisible())
                                                GUI.matchmakingBy3.setVisible(false);
                                            if (playerDataList.size() < 10 && GUI.matchmakingBy5.isVisible())
                                                GUI.matchmakingBy5.setVisible(false);
                                        }
                                    });
                                    GUI.rosterTable.setWidget(row, column, playerPanel);
                                    GUI.rosterTable.getCellFormatter().setStyleName(row, column, localPlayer.getRank().name());
                                    GUI.setBootstrapAlert(successAlert);

                                    // Display the matchmaking buttons if appropriate
                                    if(playerDataList.size()>=6)
                                        GUI.matchmakingBy3.setVisible(true);
                                    if(playerDataList.size()>=10)
                                        GUI.matchmakingBy5.setVisible(true);
                                }

                                @Override
                                public void onError(Request request, Throwable exception) {
                                    GUI.setBootstrapAlert(serverErrorWarning);
                                }
                            });
                            try {
                                builder1.send();
                            } catch (RequestException e) {
                                e.printStackTrace();
                                GUI.setBootstrapAlert(serverErrorWarning);
                            }
                    }
                }
                @Override
                public void onError(Request request, Throwable exception) {
                    GUI.setBootstrapAlert(serverErrorWarning);
                }
            });
            try {
                builder.send();
            } catch (RequestException e) {
                e.printStackTrace();
                GUI.setBootstrapAlert(serverErrorWarning);
            }
        }
    }

    /**
     * This method creates teams of players.
     * @param playersPerTeam Number of players on each team
     */
    public void createTeams(int playersPerTeam){
        assert !(playerDataList.isEmpty());

        // Sort players by rank, pop off stack onto teams
        Collections.sort(playerDataList);

        // Set up player stack
        Stack<Player> playerStack = new Stack<Player>();
        for (Player player : playerDataList) { playerStack.push(player); }

        // Set up teams
        for (int i = 0; i <= playerStack.size()/playersPerTeam; i++) {
            Team team = new Team();
            for (int j = 0; j < playersPerTeam; j++) {
                Player localPlayer = playerStack.pop();
                team.put(localPlayer.getSummonerName(), localPlayer);
            }
            team.teamName = team.randomTeamName();
            teams.add(team);
        }

        // Reserve player team
        if (!(playerStack.isEmpty())){
            Team team = new Team();
            for (int i = 0; i < playerStack.size(); i++) {
                Player localPlayer = playerStack.pop();
                team.put(localPlayer.getSummonerName(), localPlayer);
            }
            team.teamName = "Surplus Players";
            teams.add(team);
        }


        GUI.setBootstrapAlert("Number of teams: "+String.valueOf(teams.size()));


    }

    // TODO Create method to swap players on teams
    /**
     * This method sets up the team panels on the display.
     */
    public void createTeamsOnGUI(){
        FlexTable teamTable = new FlexTable();
        for (int index = 0, teamsSize = teams.size(), column = 0, row = 0; index < teamsSize; index++, column++) {
            Team team = teams.get(index);
            if (column >= 9) {
                column = 0;
               // teamTable.insertRow(row);
                row++;
            }
            VerticalPanel teamPanel = new VerticalPanel();
            Label teamNameLabel = new Label(team.teamName);
            HTML teamHTML = new HTML();
            final String listOpener = "<ul class=\"list-group\">";
            final String listCloser = "</ul>";
            final String listItemOpener = "<li class=\"list-group-item ";
         //   final String listColorStyleOpener = "style=\"background-color:";
        //    final String listColorStyleCloser = "\">";
            final String listItemCloser = "</li>";
            String teamHTMLString = listOpener;
            for (Player player : team.values()) {
                teamHTMLString = teamHTMLString.concat(listItemOpener);
           //     teamHTMLString = teamHTMLString.concat(listColorStyleOpener);
                teamHTMLString = teamHTMLString.concat(player.getRank().name()+"\">");
            //    teamHTMLString = teamHTMLString.concat(listColorStyleCloser);
                teamHTMLString = teamHTMLString.concat(player.getSummonerName());
                teamHTMLString = teamHTMLString.concat(listItemCloser);
            }
            teamHTMLString = teamHTMLString.concat(listCloser);
            teamHTML.setHTML(teamHTMLString);
            teamPanel.add(teamNameLabel);
            teamPanel.add(teamHTML);
            teamTable.setWidget(row, column, teamPanel);
        }
        GUI.middleMainPanel.remove(GUI.playerPanel);
        GUI.middleMainPanel.setWidth("100%");
        GUI.middleMainPanel.add(teamTable);
    //    GUI.setBootstrapAlert(successfulTeamCreation);

    }

    private void createSamplePlayerData(){
        for (int i = 0; i < 50; i++) {
            Player player = new Player("gold");
            player.setSummonerNameSample("Player"+i);
            playerDataList.add(player);
        }
    }

}
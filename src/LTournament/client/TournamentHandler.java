package LTournament.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.ui.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by user on 6/4/15.
 * This class handles events from the user.
 */
public class TournamentHandler {

    private final String APIKEY = "?api_key=0fe5e184-13db-40a8-9100-bcc29c664cd2";

    // Non-GWT objects
    ArrayList<Player> playerDataList = new ArrayList<Player>();
    ArrayList<Team> teams = new ArrayList<Team>();
    HashMap<String, Player> playerHashMap = new HashMap<String, Player>();


    public boolean playersAreOnSameTeam(){
        return playerHashMap.get(firstPlayerToSwap()).getTeam().equals(playerHashMap.get(secondPlayerToSwap()).getTeam());
    }

    /**
     * This method makes two calls to the league of legends API. The first call uses the summoner name to gather
     * more information, specifically the summoner ID number. The second call uses the summoner ID number to obtain
     * the rest of the information used to build the player object.
     */
    public void addPlayer(){
        // Use the player name as the hash key
        final String playerName = GUI.getPlayerName();

        // This lines prevents an error where the reset button was not clearing the playerDataList which was causing a bug
        if (Tournament.summonerNameList.isEmpty())
            playerDataList.clear();

        if (Tournament.summonerNameList.containsKey(playerName)){
            // This is a duplicate entry
            GUI.setBootstrapAlert(bootstrapAlerts.duplicatePlayerWarning);
        } else if (playerDataList.size()>=100){
            // This system supports 100 players at most.
            GUI.setBootstrapAlert(bootstrapAlerts.maxPlayersWarning);
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
                            GUI.setBootstrapAlert(bootstrapAlerts.playerNotFoundWarning);
                            break;
                        case 429: // Rate limit exceeded
                            GUI.setBootstrapAlert(bootstrapAlerts.rateLimitWarning);
                            break;
                        case 500: // Server error
                            GUI.setBootstrapAlert(bootstrapAlerts.serverErrorWarning);
                            break;
                        case 503: // Server error
                            GUI.setBootstrapAlert(bootstrapAlerts.serverErrorWarning);
                            break;
                        case 200: // Success
                            if(!GUI.playerPanel.isVisible())
                                GUI.playerPanel.setVisible(true);
                            String responseText = response.getText();
                            // Create new player object using response
                            Player player = new Player(responseText);
                            playerDataList.add(player);
                            playerHashMap.put(player.getSummonerName(), player);
                            Tournament.summonerNameList.put(playerName, player);
                            final String BY_ID_URL = "https://na.api.pvp.net/api/lol/na/v2.5/league/by-summoner/";
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
                                    GUI.setBootstrapAlert(bootstrapAlerts.successAlert);

                                    // Display the matchmaking buttons if appropriate
                                    if(playerDataList.size()>=6)
                                        GUI.matchmakingBy3.setVisible(true);
                                    if(playerDataList.size()>=10)
                                        GUI.matchmakingBy5.setVisible(true);
                                }

                                @Override
                                public void onError(Request request, Throwable exception) {
                                    GUI.setBootstrapAlert(bootstrapAlerts.serverErrorWarning);
                                }
                            });
                            try {
                                builder1.send();
                            } catch (RequestException e) {
                                e.printStackTrace();
                                GUI.setBootstrapAlert(bootstrapAlerts.serverErrorWarning);
                            }
                    }
                }
                @Override
                public void onError(Request request, Throwable exception) {
                    GUI.setBootstrapAlert(bootstrapAlerts.serverErrorWarning);
                }
            });
            try {
                builder.send();
            } catch (RequestException e) {
                e.printStackTrace();
                GUI.setBootstrapAlert(bootstrapAlerts.serverErrorWarning);
            }
        }
    }

    /**
     * This method sets up the teams of players
     * @param playersPerTeam Number of players on each team
     */
    public void createTeams(int playersPerTeam){
        assert !(playerDataList.isEmpty());

        int numberOfTeams = playerDataList.size() / playersPerTeam;

        // Sort players by rank and set up a stack
        Collections.sort(playerDataList);
        Stack<Player> playerStack = new Stack<Player>();
        for (Player player : playerDataList) { playerStack.push(player); }

        // Set up the teams
        for (int i = 0; i < numberOfTeams; i++) {
            Team team = new Team();
            team.teamName = team.randomTeamName();
            teams.add(team);
        }

        for (int i = 0, teamIndex=0; i < numberOfTeams*playersPerTeam; i++, teamIndex++){
            // Reset the teamIndex
            if (teamIndex>=numberOfTeams)   teamIndex=0;

            Player player = playerStack.pop();

            player.setTeam(teams.get(teamIndex));

            teams.get(teamIndex).put(player.getSummonerName(), player);

        }

        // Reserve team
        if (!(playerStack.isEmpty())){
            Team team = new Team();
            team.teamName = "Surplus Players";
            for (Player player : playerStack){
                player.setTeam(team);
                team.put(player.getSummonerName(), player);
            }
            teams.add(team);
        }

        GUI.setBootstrapAlert("Number of teams: " + String.valueOf(teams.size()));

    }

    /**
     * This method sets up the team panels on the display.
     */
    public void createTeamsOnGUI(){
        for (int index = 0, teamsSize = teams.size(), column = 0, row = 0; index < teamsSize; index++, column++) {
            Team team = teams.get(index);
            if (column >= 9) {
                column = 0;
                row++;
            }
            VerticalPanel teamPanel = new VerticalPanel();
            // Team panel will now include a header and a button for each player
            teamPanel.add(new Label(team.teamName));
            for(Player player : team.values()){
                // Label for each player
                Button playerButton = new Button();
                playerButton.setHTML("<h4>" + player.getSummonerName() + "</h4>");
                playerButton.setTitle(player.getSummonerName());
                playerButton.setStyleName("btn btn-default btn-block active " + player.getRank().name());
                playerButton.addClickHandler(swapHandler);

                teamPanel.add(playerButton);
            }
            GUI.teamTable.setWidget(row, column, teamPanel);
        }
        if (!(GUI.teamTable.isAttached())){ GUI.middleMainPanel.add(GUI.teamTable); }
    }

    private ClickHandler swapHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            // Set the player as active in teh swap
            setPlayersToSwap(event.getRelativeElement().getTitle());
            createTeamsOnGUI();
        }
    };

    public void setPlayersToSwap(String playerToSwap) {
        // If the player player is not set
        if(!isFirstPlayerSet()){
            this.playersToSwap[0] = playerToSwap;
        } else {
            // If the first player is set but the second player is not set
            if (!isSecondPlayerSet()) {
                this.playersToSwap[1] = playerToSwap;
                if (playersAreOnSameTeam()){
                    GUI.setBootstrapAlert(bootstrapAlerts.sameTeamAlert(playerToSwap));
                    resetPlayerSwap();
                    return;
                }
            }
        }
        GUI.setBootstrapAlert(bootstrapAlerts.setPlayerSwap(playerToSwap));
    }

    private String[] playersToSwap = new String[2];

    private boolean isFirstPlayerSet(){ return playersToSwap[0]!=null; }
    private boolean isSecondPlayerSet(){ return playersToSwap[1]!=null; }

    public void resetPlayerSwap(){
        playersToSwap[0]=null;
        playersToSwap[1]=null;
        bootstrapAlerts.resetTradeStatus();
        // Catch error where after a trade is reset, the trade button can still be pressed.
    }

    private String firstPlayerToSwap(){ return playersToSwap[0];}

    private String secondPlayerToSwap(){ return playersToSwap[1];}

    public boolean playerTradeStatus(){
        return playersToSwap[0]!=null && playersToSwap[1]!=null;
    }

    // TODO Look into why the swap button could be pressed again after a successful trade
    public void swapPlayers(){

        if (playersAreOnSameTeam())
            return;

        if(!isFirstPlayerSet() || !isSecondPlayerSet())
            return;

        // Find the teams
        Team firstTeam = null;
        Team secondTeam = null;
        Player firstPlayer = null;
        Player secondPlayer = null;
        for (Team team : teams){
            if (team.containsKey(firstPlayerToSwap())){
                firstTeam = team;
                firstPlayer = team.get(firstPlayerToSwap());
            }
            if (team.containsKey(secondPlayerToSwap())){
                secondTeam = team;
                secondPlayer = team.get(secondPlayerToSwap());
            }
            if (firstTeam != null && secondTeam != null)
                break;
        }
        assert firstTeam != null && secondTeam != null && firstPlayer != null && secondPlayer != null;

        // Move the players from team to team
        firstTeam.put(secondPlayerToSwap(), secondPlayer);
        secondTeam.put(firstPlayerToSwap(), firstPlayer);

        // Delete the players from the original teams
        firstTeam.remove(firstPlayerToSwap());
        secondTeam.remove(secondPlayerToSwap());

        // Update player team status
        firstPlayer.setTeam(secondTeam);
        secondPlayer.setTeam(firstTeam);

        resetPlayerSwap();

    }


}
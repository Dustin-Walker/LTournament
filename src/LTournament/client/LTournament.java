package LTournament.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class LTournament implements EntryPoint {
    //Create GWT widgets
    private VerticalPanel mainTopPanel = new VerticalPanel();
    private HorizontalPanel addPanel = new HorizontalPanel();
    private HorizontalPanel footerPanel = new HorizontalPanel();
    private HorizontalPanel headerPanel = new HorizontalPanel();
    private HorizontalPanel rosterTableHeader = new HorizontalPanel();
    private HorizontalPanel middleMainPanel = new HorizontalPanel();
    private VerticalPanel teamListPanel = new VerticalPanel();
    private VerticalPanel playerListPanel = new VerticalPanel();
    private FlexTable rosterTable = new FlexTable();
    private TextBox newPlayerNameTextBox = new TextBox();
    private Button addPlayerButton = new Button();
    private Button resetRosterButton = new Button();
    private Button createTeamsButton = new Button();
    private Label addPlayerNameLabel = new Label();
    private Label rosterListLabel = new Label();
    private DockPanel dockPanel = new DockPanel();
    private HorizontalPanel bracketPanel = new HorizontalPanel();
    final Label teamWarning = new Label("Not enough players for two teams. Add more players.");

    //Stole this online for an error box but doesn't work
    public static DialogBox alertWidget(final String header, final String content) {
        final DialogBox box = new DialogBox();
        final VerticalPanel panel = new VerticalPanel();
        box.setText(header);
        panel.add(new Label(content));
        final Button buttonClose = new Button("Close",new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                box.hide();
            }
        });
        // few empty labels to make widget larger
        final Label emptyLabel = new Label("");
        emptyLabel.setSize("auto", "25px");
        panel.add(emptyLabel);
        panel.add(emptyLabel);
        buttonClose.setWidth("90px");
        panel.add(buttonClose);
        panel.setCellHorizontalAlignment(buttonClose, HasAlignment.ALIGN_RIGHT);
        box.add(panel);
        return box;
    }
    // TODO Convert this team warning into a popup panel
    DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Style.Unit.EM);

    //Non-GWT objects
    ArrayList<PlayerData> playerDataList = new ArrayList<PlayerData>();
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

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        // TODO Do something about the left over players

        // Assemble Add Player panel
        addPanel.add(addPlayerNameLabel);
        addPanel.add(newPlayerNameTextBox);
        addPanel.add(addPlayerButton);
        addPanel.add(resetRosterButton);
        addPlayerButton.addStyleName("add-button");
        resetRosterButton.addStyleName("reset-button");
        newPlayerNameTextBox.addStyleName("player-name-textbox");

        // Assemble the player list table header panel
        rosterListLabel.setText("Player List");
        rosterTableHeader.add(rosterListLabel);

        // Assemble the footer panel
        Grid footGrid = new Grid(1,1);
        final String leagueCopyright = "League of Legends Tournament System isn't endorsed by Riot Games and doesn't reflect the views or opinions of Riot Games or anyone officially involved in producing or managing League of Legends. League of Legends and Riot Games are trademarks or registered trademarks of Riot Games, Inc. League of Legends © Riot Games, Inc.";
        footGrid.setText(0,0,leagueCopyright);
        footGrid.setStylePrimaryName("footer");
        footerPanel.addStyleName("footerpanel");
        footerPanel.add(footGrid);
        footerPanel.setWidth("100%");

        // Assemble the playerListPanel
        playerListPanel.add(rosterTableHeader);
        playerListPanel.add(addPanel);
        playerListPanel.add(rosterTable);
        playerListPanel.addStyleName("player-list-panel");
        playerListPanel.setWidth("60%");
        playerListPanel.setHeight("80%");

        // Assemble the header panel
        headerPanel.add(new Label("League of Legends Tournament System"));
        headerPanel.addStyleName("header");
        headerPanel.setWidth("100%");

        // Assemble the team list panel
        teamListPanel.add(createTeamsButton);
        createTeamsButton.setText("Create Teams");
        createTeamsButton.addStyleName("create-teams-button");

        // Assemble the bracket panel
        bracketPanel.add(new Label("Bracket Panel"));
        bracketPanel.addStyleName("team-list");

        // Assemble the middle panel
        middleMainPanel.add(playerListPanel);
        //middleMainPanel.add(teamListPanel);
        teamListPanel.addStyleName("team-list");
        middleMainPanel.addStyleName("seam");
        middleMainPanel.addStyleName("middle-main");
        //middleMainPanel.add(bracketPanel);
        middleMainPanel.setWidth("100%");

        // Assemble the dock panel
        dockPanel.add(headerPanel, DockPanel.NORTH);
        dockPanel.add(footerPanel, DockPanel.SOUTH);
        dockPanel.add(middleMainPanel, DockPanel.WEST);

        // Assemble the dock layout panel
        dockLayoutPanel.addNorth(headerPanel,3);
        dockLayoutPanel.addSouth(footerPanel,3);
        dockLayoutPanel.add(middleMainPanel);

        // Associate main panel with HTML host page
        RootLayoutPanel rp = RootLayoutPanel.get();
        rp.add(dockLayoutPanel);

        // Get some CSS goin
        rosterTable.setStyleName("roster-table");

        //Initialize the team name list
        Collections.addAll(teamNameList, teamNameArray);

        //Listen for mouse events on Reset button
        resetRosterButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                rosterTable.removeAllRows();
                playerDataList.clear();
                summonerNameList.clear();
            }
        });

        // TODO DRY
        addPlayerButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (!summonerNameList.contains(newPlayerNameTextBox.getText().toLowerCase().trim())) {
                    addPlayerEvent();
                    summonerNameList.add(newPlayerNameTextBox.getText().toLowerCase().trim());
                }  
            }
        });

        newPlayerNameTextBox.addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {
                if(event.getNativeKeyCode()== KeyCodes.KEY_ENTER){
                    if (!summonerNameList.contains(newPlayerNameTextBox.getText().toLowerCase().trim())) {
                        addPlayerEvent();
                        summonerNameList.add(newPlayerNameTextBox.getText().toLowerCase().trim());
                    }
                }
            }
        });

        createTeamsButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                int numTeams = playerDataList.size() / 5;
                int leftoverPlayers = playerDataList.size() % 5;
                VerticalPanel teamStackPanel = new VerticalPanel();
                if(numTeams<2){
                    if(!teamWarning.isAttached())
                        teamListPanel.add(teamWarning);
                } else {
                    teamWarning.setVisible(false);
                    createTeamsButton.setVisible(false);
                    ArrayList<ArrayList<PlayerData>> teamList = generateRandomTeams();
                    for(ArrayList<PlayerData> team : teamList){
                        VerticalPanel teamPanel = new VerticalPanel();
                        for(PlayerData player : team){
                            Label playerLabel = new Label(player.getSummonerName());
                            playerLabel.addStyleName("team-player");
                            teamPanel.add(playerLabel);
                        }
                        Label teamLabel = new Label();
                        teamLabel.setText(randomTeamName());
                        teamLabel.addStyleName("team-name");
                        teamStackPanel.add(teamLabel);
                        teamStackPanel.add(teamPanel);
                    }
                    teamListPanel.add(teamStackPanel);
                    addPlayerButton.setEnabled(false);
                    resetRosterButton.setEnabled(false);
                    newPlayerNameTextBox.setEnabled(false);
                    for(int i=0;i<rosterTable.getRowCount();i++)
                        rosterTable.getCellFormatter().setVisible(i, 2, false);
                    // TODO Figure out how to make remove player buttons disabled instead of invisible
                    // TODO Change CSS of player list panel show the whole panel when disabled
                }
            }
        });
    }

    /**
     * This method pulls a random team name from the team name list. The method checks to make sure
     * duplicates do not occur.
     * @return String containing a random team name
     */
    private String randomTeamName() {
        Random rnd = new Random();
        String returnString = "";
        returnString = teamNameList.remove(rnd.nextInt(teamNameList.size()));
        return returnString;
    }

    /**
     * No clue what any of this means. IDEA must have created this.
     */
    private static class MyAsyncCallback implements AsyncCallback<String> {
        private Label label;

        public MyAsyncCallback(Label label) {
            this.label = label;
        }

        public void onSuccess(String result) {
            label.getElement().setInnerHTML(result);
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!");
        }
    }

    /**
     * This method generates teams based on a randomization algorithm. These teams are teams of 5 to be used
     * for the Summoner's Rift map and any other 5 player per team map.
     * @return List of teams
     */
    private ArrayList<ArrayList<PlayerData>> generateRandomTeams(){
        // TODO Convert method to accept a parameter specifying 3 or 5 players per team and handle that
        // TODO Add a radio button specifying 3 or 5 players per team on the team list panel
        Random rnd = new Random();
        ArrayList<ArrayList<PlayerData>> teamList = new ArrayList<ArrayList<PlayerData>>();
        int next=0;
        for (int i = 0, teamListSize = playerDataList.size() / 5; i < teamListSize; i++) {
            ArrayList<PlayerData> team = new ArrayList<PlayerData>();
            int j = 0;
            while (j < 5) {
                next = rnd.nextInt(playerDataList.size());
                team.add(playerDataList.get(next));
                playerDataList.remove(next);
                j++;
            }
            teamList.add(team);
        }
        return teamList;
    }


    /**
     * This method adds a player to the roster table and to the internal player list.
     * This method is called by both keyboard and mouse click events for entry of player data.
     */
    private void addPlayerEvent(){
        String name = newPlayerNameTextBox.getText();
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
                    int row = rosterTable.getRowCount();
                    String playerName = response.getText();
                    PlayerData newPlayerData = new PlayerData(playerName);
                    if(playerDataList.contains(newPlayerData)){
                        // generate error window
                    } else {
                        playerDataList.add(newPlayerData);
                        String leagueEntryURL = leagueEntries_URL+newPlayerData.getPlayerID()+"/entry"+APIKEY;
                        RequestBuilder builder2 =new RequestBuilder(RequestBuilder.GET, leagueEntryURL);
                        builder2.setCallback(new RequestCallback() {
                            @Override
                            public void onResponseReceived(Request request, Response response) {
                                final int row = rosterTable.getRowCount();
                                String getResponse = response.getText();
                                final PlayerData localPlayerData = playerDataList.get(playerDataList.size()-1);
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
                                        rosterTable.removeRow(rowIndex);
                                        playerDataList.remove(localPlayerData);
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
                    }
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
    }

}
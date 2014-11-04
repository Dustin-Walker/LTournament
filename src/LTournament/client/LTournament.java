package LTournament.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.ArrayList;
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
    final Label teamWarning = new Label("Not enough players to create two teams. Add more players.");

    //Non-GWT objects
    ArrayList<PlayerData> playerDataList = new ArrayList<PlayerData>();
    // URLs for REST calls
    private static final String summonerByName_URL = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/";
    private static final String leagueEntries_URL = "https://na.api.pvp.net/api/lol/na/v2.5/league/by-summoner/";
    //API Key goes here
    private static final String APIKEY = "";

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        // TODO Assemble panels using void methods to clean up the onModuleLoad() method
        // TODO Create algorithm to create teams
        // TODO Update the team list panel
        // TODO Prevent user from adding duplicate entries to player list

        // Assemble Add Player panel
        addPanel.add(addPlayerNameLabel);
        addPanel.add(newPlayerNameTextBox);
        addPanel.add(addPlayerButton);
        addPanel.add(resetRosterButton);
        addPanel.addStyleName("addpanel");
        addPlayerButton.addStyleName("add-button");
        resetRosterButton.addStyleName("reset-button");
        newPlayerNameTextBox.addStyleName("player-name-textbox");

        // Assemble the player list table header panel
        rosterListLabel.setText("Player List");
        rosterTableHeader.add(rosterListLabel);
        rosterTableHeader.addStyleName("rostertableheader");

        // Assemble the footer panel
        Grid footGrid = new Grid(1,1);
        footGrid.setText(0,0,"League of Legends Tournament System isn't endorsed by Riot Games and doesn't reflect the views or opinions of Riot Games or anyone officially involved in producing or managing League of Legends. League of Legends and Riot Games are trademarks or registered trademarks of Riot Games, Inc. League of Legends © Riot Games, Inc.");
        footGrid.setStylePrimaryName("footer");
        footerPanel.addStyleName("footerpanel");
        footerPanel.add(footGrid);
        footerPanel.setWidth("100%");

        // Assemble the playerListPanel
        playerListPanel.add(rosterTableHeader);
        playerListPanel.add(addPanel);
        playerListPanel.add(rosterTable);
        playerListPanel.addStyleName("player-list-panel");

        // Assemble the header panel
        headerPanel.add(new Label("League of Legends Tournament System"));
        headerPanel.addStyleName("header");
        headerPanel.setWidth("100%");

        // Assemble the team list panel
        //teamListPanel.add(new Label("Team List"));
        teamListPanel.add(createTeamsButton);
        createTeamsButton.setText("Create Teams");
        createTeamsButton.addStyleName("create-teams-button");

        // Assemble the bracket panel
        bracketPanel.add(new Label("Bracket Panel"));
        bracketPanel.addStyleName("team-list");

        // Assemble the middle panel
        middleMainPanel.add(playerListPanel);
        middleMainPanel.add(teamListPanel);
        teamListPanel.addStyleName("team-list");
        middleMainPanel.addStyleName("seam");
        middleMainPanel.addStyleName("middle-main");
        middleMainPanel.add(bracketPanel);
        middleMainPanel.setWidth("100%");

        // Assemble the dock panel
        dockPanel.add(headerPanel, DockPanel.NORTH);
        dockPanel.add(footerPanel, DockPanel.SOUTH);
        dockPanel.add(middleMainPanel, DockPanel.WEST);

        // Associate main panel with HTML host page
        RootPanel.get("playerRoster").add(dockPanel);

        // Get some CSS goin
        rosterTable.setStyleName("roster-table");

        //Listen for mouse events on Reset button
        resetRosterButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                rosterTable.removeAllRows();
                playerDataList.clear();
            }
        });

        addPlayerButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                String name = newPlayerNameTextBox.getText();
                String url = summonerByName_URL+name+APIKEY;
                // Send request to server and catch any errors
                RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
                builder.setCallback(new RequestCallback() {
                    @Override
                    public void onResponseReceived(Request request, Response response) {
                        if (200 == response.getStatusCode()){
                            int row = rosterTable.getRowCount();
                            String playerName = response.getText();
                            PlayerData d = new PlayerData(playerName);
                            playerDataList.add(d);
                            String leagueEntryURL = leagueEntries_URL+d.getPlayerID()+"/entry"+APIKEY;
                            RequestBuilder builder2 =new RequestBuilder(RequestBuilder.GET, leagueEntryURL);
                            builder2.setCallback(new RequestCallback() {
                                @Override
                                public void onResponseReceived(Request request, Response response) {
                                    final int row = rosterTable.getRowCount();
                                    String s = response.getText();
                                    final PlayerData dd = playerDataList.get(playerDataList.size()-1);
                                    if(s.contains("CHALLENGER")){
                                        dd.setRank("CHALLENGER");
                                        String cIcon = ("<img src=\"img/challenger_icon_24.png\" >");
                                        rosterTable.setHTML(row-1,0, cIcon );
                                    } else if(s.contains("DIAMOND")){
                                        dd.setRank("DIAMOND");
                                        String cIcon = ("<img src=\"img/diamond_icon_24.png\" >");
                                        rosterTable.setHTML(row-1,0, cIcon );
                                    } else if(s.contains("PLATINUM")){
                                        dd.setRank("PLATINUM");
                                        String cIcon = ("<img src=\"img/platinum_icon_24.png\" >");
                                        rosterTable.setHTML(row-1,0, cIcon );
                                    } else if(s.contains("GOLD")){
                                        dd.setRank("GOLD");
                                        String cIcon = ("<img src=\"img/gold_icon_24.png\" >");
                                        rosterTable.setHTML(row-1,0, cIcon );
                                    } else if (s.contains("SILVER")) {
                                        dd.setRank("SILVER");
                                        String cIcon = ("<img src=\"img/silver_icon_24.png\" >");
                                        rosterTable.setHTML(row-1,0, cIcon );
                                    } else if (s.contains("BRONZE")) {
                                        dd.setRank("BRONZE");
                                        String cIcon = ("<img src=\"img/bronze_icon_24.png\" >");
                                        rosterTable.setHTML(row-1,0, cIcon );
                                    } else {
                                        dd.setRank("UNRANKED");
                                        String cIcon = ("<img src=\"img/unranked_icon_24.png\" >");
                                        rosterTable.setHTML(row-1,0,cIcon );
                                    }
                                    playerDataList.get(playerDataList.size()-1).setRank(dd.getRank());
                                    final Button removePlayerButton = new Button();
                                    removePlayerButton.addStyleName("remove-button");
                                    rosterTable.setWidget(row-1, 2, removePlayerButton);
                                    removePlayerButton.addClickHandler(new ClickHandler() {
                                        @Override
                                        public void onClick(ClickEvent event) {
                                            int rowIndex = rosterTable.getCellForEvent(event).getRowIndex();
                                            rosterTable.removeRow(rowIndex);
                                            playerDataList.remove(dd);
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
                            rosterTable.setText(row, 1, d.getSummonerName());
                            rosterTable.getRowFormatter().addStyleName(row, "player-list-entry");
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
        });

        newPlayerNameTextBox.addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {
                if(event.getNativeKeyCode()== KeyCodes.KEY_ENTER){
                    String name = newPlayerNameTextBox.getText();
                    String url = summonerByName_URL+name+APIKEY;
                    // Send request to server and catch any errors
                    RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
                    builder.setCallback(new RequestCallback() {
                        @Override
                        public void onResponseReceived(Request request, Response response) {
                            if (200 == response.getStatusCode()){
                                int row = rosterTable.getRowCount();
                                String playerName = response.getText();
                                PlayerData d = new PlayerData(playerName);
                                playerDataList.add(d);
                                String leagueEntryURL = leagueEntries_URL+d.getPlayerID()+"/entry"+APIKEY;
                                RequestBuilder builder2 =new RequestBuilder(RequestBuilder.GET, leagueEntryURL);
                                builder2.setCallback(new RequestCallback() {
                                    @Override
                                    public void onResponseReceived(Request request, Response response) {
                                        final int row = rosterTable.getRowCount();
                                        String s = response.getText();
                                        final PlayerData dd = playerDataList.get(playerDataList.size()-1);
                                        if(s.contains("CHALLENGER")){
                                            dd.setRank("CHALLENGER");
                                            String cIcon = ("<img src=\"img/challenger_icon_24.png\" >");
                                            rosterTable.setHTML(row-1,0, cIcon );
                                        } else if(s.contains("DIAMOND")){
                                            dd.setRank("DIAMOND");
                                            String cIcon = ("<img src=\"img/diamond_icon_24.png\" >");
                                            rosterTable.setHTML(row-1,0, cIcon );
                                        } else if(s.contains("PLATINUM")){
                                            dd.setRank("PLATINUM");
                                            String cIcon = ("<img src=\"img/platinum_icon_24.png\" >");
                                            rosterTable.setHTML(row-1,0, cIcon );
                                        } else if(s.contains("GOLD")){
                                            dd.setRank("GOLD");
                                            String cIcon = ("<img src=\"img/gold_icon_24.png\" >");
                                            rosterTable.setHTML(row-1,0, cIcon );
                                        } else if (s.contains("SILVER")) {
                                            dd.setRank("SILVER");
                                            String cIcon = ("<img src=\"img/silver_icon_24.png\" >");
                                            rosterTable.setHTML(row-1,0, cIcon );
                                        } else if (s.contains("BRONZE")) {
                                            dd.setRank("BRONZE");
                                            String cIcon = ("<img src=\"img/bronze_icon_24.png\" >");
                                            rosterTable.setHTML(row-1,0, cIcon );
                                        } else {
                                            dd.setRank("UNRANKED");
                                            String cIcon = ("<img src=\"img/unranked_icon_24.png\" >");
                                            rosterTable.setHTML(row-1,0,cIcon );
                                        }
                                        playerDataList.get(playerDataList.size()-1).setRank(dd.getRank());
                                        final Button removePlayerButton = new Button();
                                        removePlayerButton.addStyleName("remove-button");
                                        rosterTable.setWidget(row-1, 2, removePlayerButton);
                                        removePlayerButton.addClickHandler(new ClickHandler() {
                                            @Override
                                            public void onClick(ClickEvent event) {
                                                int rowIndex = rosterTable.getCellForEvent(event).getRowIndex();
                                                rosterTable.removeRow(rowIndex);
                                                playerDataList.remove(dd);

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
                                rosterTable.setText(row, 1, d.getSummonerName());
                                rosterTable.getRowFormatter().addStyleName(row, "player-list-entry");
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
            }});

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
                    createTeamsButton.setVisible(false);
                    ArrayList<ArrayList<PlayerData>> teamList = generateRandomTeams();
                    int j=0;
                    for(ArrayList<PlayerData> team : teamList){
                        j++;
                        VerticalPanel teamPanel = new VerticalPanel();
                        for(PlayerData player : team){
                            Label playerLabel = new Label(player.getSummonerName());
                            playerLabel.addStyleName("team-player");
                            teamPanel.add(playerLabel);
                        }
                        // TODO Replace team # with value from TeamNameList
                        Label teamLabel = new Label();
                        teamLabel.setText("Team #"+j);
                        teamLabel.addStyleName("team-name");
                        teamStackPanel.add(teamLabel);
                        teamStackPanel.add(teamPanel);
                    }
                    teamListPanel.add(teamStackPanel);
                }
            }
        });
    }

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

}
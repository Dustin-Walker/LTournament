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
  /*  //Create GWT widgets
    private VerticalPanel mainTopPanel = new VerticalPanel();
    private VerticalPanel addPanel = new VerticalPanel();
    private HorizontalPanel footerPanel = new HorizontalPanel();
    private HorizontalPanel headerPanel = new HorizontalPanel();
    private HorizontalPanel rosterTableHeader = new HorizontalPanel();
    private HorizontalPanel middleMainPanel = new HorizontalPanel();
    private VerticalPanel teamListPanel = new VerticalPanel();
    private VerticalPanel controlPanel = new VerticalPanel();
    private FlexTable rosterTable = new FlexTable();
    private TextBox newPlayerNameTextBox = new TextBox();
    private Button addPlayerButton = new Button();
    private Button resetRosterButton = new Button();
    private Button createTeamsButton = new Button();
    private Button matchmakingBy3 = new Button();
    private Button matchmakingBy5 = new Button();
    private Button startTeamPicker = new Button();
    private Label addPlayerNameLabel = new Label();
    private Label rosterListLabel = new Label();
    private DockPanel dockPanel = new DockPanel();
    private HorizontalPanel bracketPanel = new HorizontalPanel();
    final Label teamWarning = new Label("Not enough players for two teams. Add more players.");
    private HorizontalPanel playerPanel = new HorizontalPanel();
    private Label playerPanelHeader = new Label("Player Roster");

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
    ArrayList<Player> playerList = new ArrayList<Player>();
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
*/
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        GUI.assembleStartUp();
        RootLayoutPanel rp = RootLayoutPanel.get();
        rp.add(GUI.dockLayoutPanel);

/*        // TODO Do something about the left over players

        // Assemble Add Player panel
        addPanel.add(addPlayerNameLabel);
        addPanel.add(newPlayerNameTextBox);
        addPlayerButton.addStyleName("add-button");
        resetRosterButton.addStyleName("reset-button");
        newPlayerNameTextBox.addStyleName("player-name-textbox");
        addPlayerButton.addStyleName("btn btn-default");
        resetRosterButton.addStyleName("btn btn-default");
        addPlayerButton.setHTML("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>Add Player");
        resetRosterButton.setHTML("<span class=\"glyphicon glyphicon-repeat\" aria-hidden=\"true\"></span>Reset All");
        // TODO Confirm reset all

        // Assemble the player list table header panel
        rosterListLabel.setText("Player List");
        rosterTableHeader.add(rosterListLabel);
        rosterListLabel.addStyleName("control-panel-header");

        // Assemble the footer panel
        Grid footGrid = new Grid(1,1);
        final String leagueCopyright = "League of Legends Tournament System isn't endorsed by Riot Games and doesn't reflect the views or opinions of Riot Games or anyone officially involved in producing or managing League of Legends. League of Legends and Riot Games are trademarks or registered trademarks of Riot Games, Inc. League of Legends © Riot Games, Inc.";
        footGrid.setText(0, 0, leagueCopyright);
        footGrid.setStylePrimaryName("footer");
        footerPanel.addStyleName("footerpanel");
        footerPanel.add(footGrid);
        footerPanel.setWidth("100%");

        // Assemble the controlPanel
        controlPanel.add(rosterTableHeader);
        controlPanel.add(addPanel);
        addPanel.add(addPlayerButton);
        controlPanel.add(resetRosterButton);
        controlPanel.addStyleName("control-panel");
      //  controlPanel.setWidth("100%");
        controlPanel.add(matchmakingBy3);
        matchmakingBy3.setText("Automatically create 3 player teams");
        matchmakingBy3.setHTML("Random Twisted Treelined<span class=\"glyphicon glyphicon-play\" aria-hidden=\"true\"></span>");
        controlPanel.add(matchmakingBy5);
        matchmakingBy5.setText("Automatically create 5 player teams");
        matchmakingBy5.setHTML("Random Summoner's Rift<span class=\"glyphicon glyphicon-play\" aria-hidden=\"true\"></span>");
        controlPanel.add(startTeamPicker);
        controlPanel.setHeight("100%");
        startTeamPicker.setText("Create teams");
        startTeamPicker.setHTML("Custom Team Builder<span class=\"glyphicon glyphicon-hand-right\" aria-hidden=\"true\"></span>");
        startTeamPicker.addStyleName("startTeamPicker-btn");
        matchmakingBy3.setVisible(false);
        matchmakingBy5.setVisible(false);
        startTeamPicker.setVisible(false);

        // Style the buttons
        matchmakingBy3.addStyleName("btn btn-default");
        matchmakingBy5.addStyleName("btn btn-default");
        startTeamPicker.addStyleName("btn btn-default");

        // Style the panels
        playerPanel.addStyleName("list-group");

        playerPanel.add(playerPanelHeader);
        //playerPanelHeader.addStyleName("panel-heading");
        //rosterTable.addStyleName("panel-body ");
        rosterTable.addStyleName("table");

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

        // Player panel assembly
        playerPanel.setHeight("100px");
        playerPanel.setWidth("100px");
        //playerPanel.add(new Label("TEST"));
        playerPanel.add(rosterTable);
        //playerPanel.addStyleName("player-list-panel");

        // Assemble the middle panel
       // middleMainPanel.add(controlPanel);
        middleMainPanel.add(playerPanel);
        playerPanel.setVisible(false);
        //middleMainPanel.add(teamListPanel);
        teamListPanel.addStyleName("team-list");
        middleMainPanel.addStyleName("seam");
        middleMainPanel.addStyleName("middle-main");
        //middleMainPanel.add(bracketPanel);
      //  middleMainPanel.setWidth("70%");

        // Assemble the dock panel
        dockPanel.add(headerPanel, DockPanel.NORTH);
        dockPanel.add(footerPanel, DockPanel.SOUTH);
        dockPanel.add(middleMainPanel, DockPanel.EAST);
        dockPanel.add(controlPanel, DockPanel.WEST);

        // Assemble the dock layout panel
        dockLayoutPanel.addNorth(headerPanel, 3);
        dockLayoutPanel.addSouth(footerPanel,3);
        dockLayoutPanel.addEast(middleMainPanel, 60);
        dockLayoutPanel.addWest(controlPanel, 20);

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
                playerPanel.setVisible(false);
                rosterTable.removeAllRows();
                playerList.clear();
                summonerNameList.clear();
            }
        });

        // TODO DRY
        addPlayerButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (!summonerNameList.contains(newPlayerNameTextBox.getText().toLowerCase().trim())) {
                    addPlayerEvent();
                }  
            }
        });

        newPlayerNameTextBox.addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {
                if(event.getNativeKeyCode()== KeyCodes.KEY_ENTER){
                    if (!summonerNameList.contains(newPlayerNameTextBox.getText().toLowerCase().trim())) {
                        addPlayerEvent();
                    }
                }
            }
        });

        createTeamsButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                int numTeams = playerList.size() / 5;
                int leftoverPlayers = playerList.size() % 5;
                VerticalPanel teamStackPanel = new VerticalPanel();
                if(numTeams<2){
                    if(!teamWarning.isAttached())
                        teamListPanel.add(teamWarning);
                } else {
                    teamWarning.setVisible(false);
                    createTeamsButton.setVisible(false);
                    ArrayList<ArrayList<Player>> teamList = generateRandomTeams();
                    for(ArrayList<Player> team : teamList){
                        VerticalPanel teamPanel = new VerticalPanel();
                        for(Player player : team){
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
        });*/
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

/*

    private void setCreatorButtonVis(){
        if (playerList.size() >= 6 && !matchmakingBy3.isVisible()){
            matchmakingBy3.setVisible(true);
            startTeamPicker.setVisible(true);
        } 
        if (playerList.size() >= 10 && !matchmakingBy5.isVisible()){
            matchmakingBy5.setVisible(true);
        }

    }
*/
    // TODO Make a new column of players on the player panel every few entries
    // TODO FontAwesome



}
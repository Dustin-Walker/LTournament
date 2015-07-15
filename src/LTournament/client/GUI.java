package LTournament.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

/**
 * Created by user on 5/28/15.
 * This class contains the GUI.
 */
public class GUI {

    // TODO change position of roster table

    public static VerticalPanel addPanel = new VerticalPanel();
    private static HorizontalPanel footerPanel = new HorizontalPanel();
    private static HorizontalPanel headerPanel = new HorizontalPanel();
  //  private static HorizontalPanel controlPanelHeader = new HorizontalPanel();
    private static HTML controlPanelHeaderHTML = new HTML();
    public static VerticalPanel middleMainPanel = new VerticalPanel();
    private static VerticalPanel teamListPanel = new VerticalPanel();
    private static VerticalPanel controlPanel = new VerticalPanel();
    public static FlexTable rosterTable = new FlexTable();
    private static TextBox newPlayerNameTextBox = new TextBox();
    private static Button addPlayerButton = new Button();
    private static Button resetRosterButton = new Button();
    public static Button matchmakingBy3 = new Button();
    public static Button matchmakingBy5 = new Button();
    private static Label playerPanelHeader = new Label("Player Roster");
    private static Label addPlayerNameLabel = new Label();
  //  public static Label controlPanelHeaderLabel = new Label();
    private static DockPanel dockPanel = new DockPanel();
    private static HorizontalPanel bracketPanel = new HorizontalPanel();
    public static VerticalPanel playerPanel = new VerticalPanel();
    public static DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Style.Unit.EM);
    private static TournamentHandler tournamentHandler = new TournamentHandler();
    private static HTML bootstrapAlert = new HTML("");
    private static VerticalPanel eastPanel = new VerticalPanel();
    private static ScrollPanel middleScrollPanel = new ScrollPanel();
    public static FlexTable teamTable = new FlexTable();


    public static void assembleStartUp(){
        assembleAddPanel();
        assembleFooterPanel();
        assembleControlPanel();
        assemblePlayerPanel();
        assembleHeaderPanel();
        assembleBracketPanel();
        assembleMiddlePanel();
        assembleDockLayoutPanel();
        styleForStartup();
        addPlayerKeyHandler();
        addPlayerButtonHandler();
        resetButtonHandler();
        matchmakingBy3ClickHandler();
        matchmakingBy5ClickHandler();
    }

    public static String getPlayerName(){
        return newPlayerNameTextBox.getText().trim().toLowerCase();
    }
    public static void emptyPlayerNameInputBox(){
        newPlayerNameTextBox.setText("");
    }

    public static int rosterTableRowCount(){
        return rosterTable.getRowCount();
    }


    public static void addPlayerKeyHandler(){
        newPlayerNameTextBox.addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {

                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    tournamentHandler.addPlayer();
                    emptyPlayerNameInputBox();
                }
            }
        });
    }

    public static void matchmakingBy3ClickHandler(){
        matchmakingBy3.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                tournamentHandler.createTeams(3);
                tournamentHandler.createTeamsOnGUI();
                phase2stateChange();
            }
        });
    }

    public static void matchmakingBy5ClickHandler(){
        matchmakingBy5.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                tournamentHandler.createTeams(5);
                tournamentHandler.createTeamsOnGUI();
                phase2stateChange();
            }
        });
    }

    public static void addPlayerButtonHandler(){
        addPlayerButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                tournamentHandler.addPlayer();
            }
        });
    }

    public static void resetButtonHandler(){
        resetRosterButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                playerPanel.setVisible(false);
                rosterTable.removeAllRows();
                Tournament.summonerNameList.clear();
                matchmakingBy5.setVisible(false);
                matchmakingBy3.setVisible(false);
                final String resetAlert = "<div class=\"alert alert-success\" role=\"alert\">Success!<br />The list of players has been reset.</div>";
                GUI.setBootstrapAlert(resetAlert);
            }
        });
    }

    private static void assembleAddPanel(){
        // Assemble Add Player panel
        addPanel.add(addPlayerNameLabel);
        addPanel.add(newPlayerNameTextBox);
        addPlayerButton.addStyleName("add-button");
        newPlayerNameTextBox.addStyleName("player-name-textbox");
        addPlayerButton.addStyleName("btn btn-default");
        resetRosterButton.addStyleName("btn btn-default");
        addPlayerButton.setHTML("<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>Add Player");
        resetRosterButton.setHTML("<span class=\"glyphicon glyphicon-repeat\" aria-hidden=\"true\"></span>Reset All");
    }

    private static void assembleFooterPanel(){
        Grid footGrid = new Grid(1,1);
        final String leagueCopyright = "League of Legends Tournament System isn't endorsed by Riot Games and doesn't reflect the views or opinions of Riot Games or anyone officially involved in producing or managing League of Legends. League of Legends and Riot Games are trademarks or registered trademarks of Riot Games, Inc. League of Legends © Riot Games, Inc.";
        footGrid.setText(0, 0, leagueCopyright);
        footGrid.setStylePrimaryName("footer");
        footerPanel.addStyleName("footerpanel");
        footerPanel.add(footGrid);
        footerPanel.setWidth("100%");
    }

    private static void assembleControlPanel(){
        controlPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        final String phase1HeaderHTML = "<div class=\"panel panel-info\"><div class=\"panel-heading\"><h4>LTournament Information</h4></div><div class=\"panel-body\">Welcome to the League of Legends Tournament System. Begin with adding players by in-game summoner name.</div></div>";
        controlPanelHeaderHTML.setHTML(phase1HeaderHTML);
        controlPanel.add(controlPanelHeaderHTML);
        controlPanel.add(bootstrapAlert);
        controlPanel.add(addPanel);
        addPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        addPanel.add(addPlayerButton);
        controlPanel.add(resetRosterButton);
        controlPanel.addStyleName("control-panel");
        controlPanel.add(matchmakingBy3);
        matchmakingBy3.setHTML("Start Twisted Treeline<span class=\"glyphicon glyphicon-play\" aria-hidden=\"true\"></span>");
        controlPanel.add(matchmakingBy5);
        matchmakingBy5.setHTML("Start Summoner's Rift<span class=\"glyphicon glyphicon-play\" aria-hidden=\"true\"></span>");
        controlPanel.setHeight("100%");
        matchmakingBy3.setVisible(false);
        matchmakingBy5.setVisible(false);
    }

    public static void setBootstrapAlert(String s){
        bootstrapAlert.setHTML(s);
    }

    private static void styleForStartup(){
        // Style the buttons
        matchmakingBy3.addStyleName("btn btn-default");
        matchmakingBy5.addStyleName("btn btn-default");
        playerPanel.addStyleName("list-group");
        rosterTable.addStyleName("table");
    }

    private static void assemblePlayerPanel() {
        playerPanel.add(playerPanelHeader);
        playerPanel.setHeight("100px");
        playerPanel.setWidth("100px");
        playerPanel.add(rosterTable);
        assembleRosterTable();
    }

    private static void assembleRosterTable(){
        // Start with 10 rows
        for (int i = 0; i < 10; i++) {
            rosterTable.insertRow(i);
        }
    }

    private static void assembleHeaderPanel(){
        headerPanel.add(new Label("League of Legends Tournament System"));
        headerPanel.addStyleName("header");
        headerPanel.setWidth("100%");
    }

    private static void assembleBracketPanel(){
        bracketPanel.add(new Label("Bracket Panel"));
        bracketPanel.addStyleName("team-list");
    }

    // TODO Set up scroll panel wrapper for the middle main panel

    private static void assembleMiddlePanel(){
        middleMainPanel.add(playerPanel);
        playerPanel.setVisible(false);
        teamListPanel.addStyleName("team-list");
        middleMainPanel.addStyleName("seam");
        middleMainPanel.addStyleName("middle-main");
    //    middleScrollPanel.add(middleMainPanel);

     //   middleScrollPanel.setSize("400px", "400px");

    }

    private static void assembleDockPanel() {
        // Assemble the dock panel
        dockPanel.add(headerPanel, DockPanel.NORTH);
        dockPanel.add(footerPanel, DockPanel.SOUTH);
        dockPanel.add(middleScrollPanel, DockPanel.EAST);
        dockPanel.add(controlPanel, DockPanel.WEST);
    }

    private static void assembleDockLayoutPanel() {
        dockLayoutPanel.addNorth(headerPanel, 3);
        dockLayoutPanel.addSouth(footerPanel, 3);
        dockLayoutPanel.addEast(eastPanel, 0);
        dockLayoutPanel.addWest(controlPanel, 24);
        dockLayoutPanel.add(middleMainPanel);
       // dockLayoutPanel.add(middleScrollPanel);
    }

    private static void phase2stateChange(){
        addPanel.removeFromParent();
        addPlayerButton.removeFromParent();
        resetRosterButton.removeFromParent();
        matchmakingBy5.removeFromParent();
        matchmakingBy3.removeFromParent();
        String phase2HeaderHTML = "<div class=\"panel panel-info\"><div class=\"panel-heading\"><h4>LTournament Information</h4></div><div class=\"panel-body\">This is the team selection phase. <strong>Click on two players to swap them.</strong> When you are ready, click the proceed button to begin the tournament.</div></div>";
        controlPanelHeaderHTML.setHTML(phase2HeaderHTML);
        GUI.middleMainPanel.remove(GUI.playerPanel);
        GUI.middleMainPanel.setWidth("100%");
        setBootstrapAlert(bootstrapAlerts.successfulTeamCreation);

        Button resetTradeButton = new Button("Reset trade");
        resetTradeButton.addClickHandler(tradeResetButtonHandler);
        controlPanel.add(resetTradeButton);

        Button tradeButton = new Button("Swap players");
        tradeButton.addClickHandler(tradeClickHandler);
        controlPanel.add(tradeButton);
    }

    private static ClickHandler tradeClickHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {

            if (tournamentHandler.playerTradeStatus()) {

                // Handle the bootstrap alert system
                setBootstrapAlert(bootstrapAlerts.tradeButtonAlert());

                // Handle the tournamentHandler system
                tournamentHandler.swapPlayers();

                // Re-draw the GUI
                drawTeamTables();
            } else {
                setBootstrapAlert(bootstrapAlerts.swapNotReady);
            }
          //  tournamentHandler.resetPlayerSwap();
        }
    };

    private static void drawTeamTables() {
        teamTable.removeFromParent();
        tournamentHandler.createTeamsOnGUI();
    }

    public static void highlightPlayerForTrade(){

    }

    public static ClickHandler tradeResetButtonHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            // Handle the bootstrap alert system
            bootstrapAlerts.resetTradeStatus();
            setBootstrapAlert(bootstrapAlerts.tradeResetAlert);

            // Handle the tournamentHandler system
            tournamentHandler.resetPlayerSwap();
        }
    };




}

package LTournament.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.*;

/**
 * Created by user on 5/28/15.
 * This class contains the GUI.
 */
public class GUI {

    public static VerticalPanel addPanel = new VerticalPanel();
    private static HorizontalPanel footerPanel = new HorizontalPanel();
    private static HorizontalPanel headerPanel = new HorizontalPanel();
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
    private static VerticalPanel bracketPanel = new VerticalPanel();
    public static VerticalPanel playerPanel = new VerticalPanel();
    public static DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Style.Unit.EM);
    public static TournamentHandler tournamentHandler = new TournamentHandler();
    private static HTML bootstrapAlert = new HTML("");
    private static VerticalPanel eastPanel = new VerticalPanel();
    public static FlexTable teamTable = new FlexTable();
    private static Button resetTradeButton = new Button("Reset trade");
    private static Button tradeButton = new Button("Swap players");
    private static Button moveToBracketPhaseButton = new Button("Begin the tournament");
    private static Button undoBracketActionButton = new Button("Undo last action");
    private static Tournament tournament = new Tournament();
    private static HorizontalPanel bracketHolderPanel = new HorizontalPanel();
    private static VerticalPanel pickWinnerPanel = new VerticalPanel();

    public static void assembleStartUp(){
        assembleAddPanel();
        assembleFooterPanel();
        assembleControlPanel();
        assemblePlayerPanel();
        assembleHeaderPanel();

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
                phase2StateChange();
            }
        });
    }

    public static void matchmakingBy5ClickHandler(){
        matchmakingBy5.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                tournamentHandler.createTeams(5);
                tournamentHandler.createTeamsOnGUI();
                phase2StateChange();
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
                TournamentHandler.summonerNameList.clear();
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

    public static Grid bracketGrid;

    private static void assembleBracketPanel(){
        bracketPanel.add(new Label("Bracket Panel"));
        int numberOfTeams = tournamentHandler.teams.size();
        int rows = 2*numberOfTeams - 1;
        int columns = 2*((int) Math.ceil(Math.log(numberOfTeams)/Math.log(2)))+2;
        //int columns = 4;
        bracketGrid = new Grid(rows, columns);
        //bracketGrid = new Grid(10, 10);
        tournament.populateTree();
        bracketPanel.add(bracketGrid);

    }

    // TODO Set up scroll panel wrapper for the middle main panel

    private static void assembleMiddlePanel(){
        middleMainPanel.add(playerPanel);
        playerPanel.setVisible(false);
        teamListPanel.addStyleName("team-list");
        middleMainPanel.addStyleName("seam");
        middleMainPanel.addStyleName("middle-main");
    }

    /**
     * The dock layout panel is the fundamental panel
     */
    private static void assembleDockLayoutPanel() {
        dockLayoutPanel.addNorth(headerPanel, 3);
        dockLayoutPanel.addSouth(footerPanel, 3);
        dockLayoutPanel.addEast(eastPanel, 0);
        dockLayoutPanel.addWest(controlPanel, 24);
        dockLayoutPanel.add(middleMainPanel);
    }

    // TODO Implement a state design pattern to handle GUI state changes
    private static void phase2StateChange(){
        addPanel.removeFromParent();
        addPlayerButton.removeFromParent();
        resetRosterButton.removeFromParent();
        matchmakingBy5.removeFromParent();
        matchmakingBy3.removeFromParent();
        String phase2HeaderHTML = "<div class=\"panel panel-info\"><div class=\"panel-heading\"><h4>LTournament Information</h4></div><div class=\"panel-body\">This is the team selection phase. <strong>Click on two players to swap them.</strong> When you are ready, click the proceed button to begin the tournament.</div></div>";
        controlPanelHeaderHTML.setHTML(phase2HeaderHTML);
        GUI.middleMainPanel.remove(GUI.playerPanel);
        GUI.middleMainPanel.setWidth("100%");
        setBootstrapAlert(bootstrapAlerts.SUCCESSFUL_TEAM_CREATION);

        resetTradeButton.addClickHandler(tradeResetButtonHandler);
        controlPanel.add(resetTradeButton);

        tradeButton.addClickHandler(tradeClickHandler);
        controlPanel.add(tradeButton);

        moveToBracketPhaseButton.addClickHandler(moveToPhase3Handler);
        controlPanel.add(moveToBracketPhaseButton);
    }

    private static ClickHandler moveToPhase3Handler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            phase3StateChange();
        }
    };

    private static void phase3StateChange(){
        // Clear the values from phase 2
        bootstrapAlerts.resetTradeStatus();
        tournamentHandler.resetPlayerSwap();
        resetTradeButton.removeFromParent();
        tradeButton.removeFromParent();
        moveToBracketPhaseButton.removeFromParent();
        teamTable.removeFromParent();
        tournamentHandler.removeSurplusTeam();

        // Set up phase 3 interface
        String phase3HeaderHTML = "Begin the tournament by having the teams shown on the right play against each other.";
        controlPanelHeaderHTML.setHTML(phase3HeaderHTML);
        setBootstrapAlert(bootstrapAlerts.BEGIN_TOURNAMENT);

        // Set up GUI panels
        assembleBracketPanel();
        bracketHolderPanel.add(bracketPanel);
        assemblePickWinnerPanel();
        bracketHolderPanel.add(pickWinnerPanel);
        middleMainPanel.add(bracketHolderPanel);

        //undoBracketActionButton.addClickHandler(undoLastAction);
        //controlPanel.add(undoBracketActionButton);


    }

    public static HorizontalPanel teamDisplayPanel = new HorizontalPanel();

    private static VerticalPanel team1Panel = new VerticalPanel();
    private static VerticalPanel team2Panel = new VerticalPanel();

    private static void assemblePickWinnerPanel(){
        pickWinnerPanel.add(new Label("Pick a winner when the match is over."));
        teamDisplayPanel.add(team1Panel);
        teamDisplayPanel.add(team2Panel);
        pickWinnerPanel.add(teamDisplayPanel);
        pickWinnerPanel.add(confirmWinnerButton);
        team2Panel.addStyleName("team2Style");
        updateTeamPanels(tournament.activeBracketStack.pop(), tournament.activeBracketStack.pop());

        setBootstrapAlert("Number of beginning brackets: " + tournament.activeBracketStack.size());
    }

    public static void updateTeamPanels(Team team1, Team team2){
        // Assemble both panels simultaneously in parts
        team1Panel.clear();
        team2Panel.clear();
        tournament.clearPendingWinningTeam();

        // Set titles equal to team names
         team1Panel.setTitle(team1.teamName);
         team2Panel.setTitle(team2.teamName);

        // what
        setBootstrapAlert("private updateTeamPanels->team names are : " + team1.teamName + " and " + team2.teamName);

        // Start with team name
        team1Panel.add(new Label(team1.teamName));
        team2Panel.add(new Label(team2.teamName));

        // Iterate through players on each team and list them below the team name
        for (Player player : team1.values()){
            team1Panel.add(new Label(player.getSummonerName()));
        }
        for (Player player : team2.values()){
            team2Panel.add(new Label(player.getSummonerName()));
        }

        Button team1button = new Button("Select team as winner", team1SelectorHandler);
        Button team2button = new Button("Select team as winner", team2SelectorHandler);

        team1Panel.add(team1button);
        team2Panel.add(team2button);

    }

    public static void updateTeamPanels(Bracket bracket1, Bracket bracket2){

        setBootstrapAlert("public updateTeamPanels->team names are : " + bracket1.getTeam().teamName + " and " + bracket2.getTeamName());

        tournament.setPendingMatchBrackets(bracket1, bracket2);

        updateTeamPanels(bracket1.getTeam(), bracket2.getTeam());

        tournament.setPendingColumn(bracket1.getColumn() + 2);
        tournament.setPendingRow((bracket1.getRow() + bracket2.getRow()) / 2);
    }

    private static ClickHandler team1SelectorHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            tournament.setPendingWinningTeam(team1Panel.getTitle());
            setBootstrapAlert(bootstrapAlerts.matchWinner(team1Panel.getTitle()));
        }
    };

    private static ClickHandler team2SelectorHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            tournament.setPendingWinningTeam(team2Panel.getTitle());
            setBootstrapAlert(bootstrapAlerts.matchWinner(team2Panel.getTitle()));
        }
    };

    private static ClickHandler confirmWinnerHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            if (tournament.getPendingWinningTeam() == null)
                setBootstrapAlert(bootstrapAlerts.NO_PENDING_WINNING_TEAM);
            else {
                // tournament.nextRoundBracketStack.push(tournament.getPendingWinningTeam());
                Bracket bracket = new Bracket(tournament.getPendingWinningTeam().getTeam(), tournament.getPendingColumn(), tournament.getPendingRow());
                bracketGrid.setHTML(bracket.getRow(), bracket.getColumn(), bracket.getTeamName());

                tournament.nextRoundBracketStack.push(bracket);
                tournament.matchPending = false;

                // Check for win state
                if (tournament.activeBracketStack.size() == 0 && tournament.nextRoundBracketStack.size() == 1 && !tournament.matchPending){
                    setWinState();
                    return;
                }

                // Check is activeStack is empty
                /*
                if (tournament.activeBracketStack.size() == 0){
                    // Move to next column of teams
                    // tournament.activeBracketStack.clear();
                    // tournament.activeBracketStack.addAll(tournament.nextRoundBracketStack);
                    while(!tournament.nextRoundBracketStack.isEmpty())
                        tournament.activeBracketStack.push(tournament.nextRoundBracketStack.pop());
                    // tournament.nextRoundBracketStack.clear();
                    setBootstrapAlert("active round stack size: " + tournament.activeBracketStack.size());
                    if (tournament.activeBracketStack.size() == 1){
                        // setWinState();
                        return;
                    }
                    Bracket bracket1 = tournament.activeBracketStack.pop();
                    Bracket bracket2 = tournament.activeBracketStack.pop();
                    setBootstrapAlert("team names are : " + bracket1.getTeamName() + " and " + bracket2.getTeamName());
                    team1Panel.clear();
                    team2Panel.clear();
                    updateTeamPanels(bracket1, bracket2);
                }*/

                // Allow one team to skip if there is an odd number of teams on the stack
                if (tournament.activeBracketStack.size() == 1) {
                    Bracket lastBracket = tournament.activeBracketStack.pop();
                    Bracket bracket1 = new Bracket(lastBracket.getTeam(), lastBracket.getColumn() + 2, lastBracket.getRow() + 1);
                    bracketGrid.setHTML(bracket1.getRow(), bracket1.getColumn(), bracket1.getTeamName());
                    tournament.nextRoundBracketStack.push(bracket1);
                    assert tournament.activeBracketStack.size() == 0;
                    // tournament.clearPendingMatchBrackets();
                    // setBootstrapAlert("next round stack size: " + tournament.nextRoundBracketStack.size());
                    // setBootstrapAlert("active round stack size: " + tournament.activeBracketStack.size());
                }
                if (tournament.activeBracketStack.size() == 0){
                    // Move to next column of teams
                    // tournament.activeBracketStack.clear();
                    // tournament.activeBracketStack.addAll(tournament.nextRoundBracketStack);
                    while(!tournament.nextRoundBracketStack.isEmpty())
                        tournament.activeBracketStack.push(tournament.nextRoundBracketStack.pop());
                    // tournament.nextRoundBracketStack.clear();
                    setBootstrapAlert("active round stack size: " + tournament.activeBracketStack.size());
                  //  if (tournament.activeBracketStack.size() == 1){
                        // setWinState();
                //        return;
                //    }
                    Bracket bracket1 = tournament.activeBracketStack.pop();
                    Bracket bracket2 = tournament.activeBracketStack.pop();
                    setBootstrapAlert("team names are : " + bracket1.getTeamName() + " and " + bracket2.getTeamName());
                 //   team1Panel.clear();
                 //   team2Panel.clear();
                    updateTeamPanels(bracket1, bracket2);
                    tournament.matchPending = true;
                   // updateTeamPanels(tournament.activeBracketStack.pop(), tournament.activeBracketStack.pop());
                } else if (tournament.activeBracketStack.size() >= 2){
                    // Otherwise, pop two teams off the stack
                    updateTeamPanels(tournament.activeBracketStack.pop(), tournament.activeBracketStack.pop());
                    tournament.matchPending = true;
                }


            }
        }
    };

    private static void setWinState(){
        setBootstrapAlert(bootstrapAlerts.TOURNAMENT_FINISHED);
        team1Panel.getWidget(team1Panel.getWidgetCount()-1).setVisible(false);
        team2Panel.getWidget(team2Panel.getWidgetCount()-1).setVisible(false);
        confirmWinnerButton.setVisible(false);
    }

    private static Button confirmWinnerButton = new Button("Confirm?", confirmWinnerHandler);

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
                setBootstrapAlert(bootstrapAlerts.SWAP_NOT_READY);
            }
        }
    };

    private static void drawTeamTables() {
        teamTable.removeFromParent();
        tournamentHandler.createTeamsOnGUI();
    }

    public static ClickHandler tradeResetButtonHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            // Handle the bootstrap alert system
            bootstrapAlerts.resetTradeStatus();
            setBootstrapAlert(bootstrapAlerts.TRADE_RESET_ALERT);

            // Handle the tournamentHandler system
            tournamentHandler.resetPlayerSwap();
        }
    };
}

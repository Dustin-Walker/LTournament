package LTournament.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.*;

/**
 * Created by user on 5/28/15.
 * This class contains the GUI.
 */
public class GUI {

    private static VerticalPanel mainTopPanel = new VerticalPanel();
    private static VerticalPanel addPanel = new VerticalPanel();
    private static HorizontalPanel footerPanel = new HorizontalPanel();
    private static HorizontalPanel headerPanel = new HorizontalPanel();
    private static HorizontalPanel rosterTableHeader = new HorizontalPanel();
    private static HorizontalPanel middleMainPanel = new HorizontalPanel();
    private static VerticalPanel teamListPanel = new VerticalPanel();
    private static VerticalPanel controlPanel = new VerticalPanel();
    private static FlexTable rosterTable = new FlexTable();
    private static TextBox newPlayerNameTextBox = new TextBox();
    private static Button addPlayerButton = new Button();
    private static Button resetRosterButton = new Button();
    private static Button createTeamsButton = new Button();
    private static Button matchmakingBy3 = new Button();
    private static Button matchmakingBy5 = new Button();
    private static Button startTeamPicker = new Button();
    private static Label playerPanelHeader = new Label("Player Roster");
    private static Label addPlayerNameLabel = new Label();
    private static Label rosterListLabel = new Label();
    private static DockPanel dockPanel = new DockPanel();
    private static HorizontalPanel bracketPanel = new HorizontalPanel();
    final static Label teamWarning = new Label("Not enough players for two teams. Add more players.");
    private static HorizontalPanel playerPanel = new HorizontalPanel();
    public static DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Style.Unit.EM);

    public static void assembleStartUp(){
        assembleAddPanel();
        assemblePlayerListPanelHeader();
        assembleFooterPanel();
        assembleControlPanel();
        assemblePlayerPanel();
        assembleHeaderPanel();
        assembleTeamListPanel();
        assembleBracketPanel();
        assembleMiddlePanel();
        assembleDockPanel();
        assembleDockLayoutPanel();
        styleForStartup();
    }

    private static void assembleAddPanel(){
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
    }

    private static void assemblePlayerListPanelHeader(){
        rosterListLabel.setText("Player List");
        rosterTableHeader.add(rosterListLabel);
        rosterListLabel.addStyleName("control-panel-header");
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
    }

    private static void styleForStartup(){
        // Style the buttons
        matchmakingBy3.addStyleName("btn btn-default");
        matchmakingBy5.addStyleName("btn btn-default");
        startTeamPicker.addStyleName("btn btn-default");
        playerPanel.addStyleName("list-group");
        rosterTable.addStyleName("table");
    }

    private static void assemblePlayerPanel() {
        playerPanel.add(playerPanelHeader);
        playerPanel.setHeight("100px");
        playerPanel.setWidth("100px");
        playerPanel.add(rosterTable);
    }

    private static void assembleHeaderPanel(){
        headerPanel.add(new Label("League of Legends Tournament System"));
        headerPanel.addStyleName("header");
        headerPanel.setWidth("100%");
    }

    private static void assembleTeamListPanel(){
        teamListPanel.add(createTeamsButton);
        createTeamsButton.setText("Create Teams");
        createTeamsButton.addStyleName("create-teams-button");
    }

    private static void assembleBracketPanel(){
        bracketPanel.add(new Label("Bracket Panel"));
        bracketPanel.addStyleName("team-list");
    }

    private static void assembleMiddlePanel(){
        middleMainPanel.add(playerPanel);
        playerPanel.setVisible(false);
        teamListPanel.addStyleName("team-list");
        middleMainPanel.addStyleName("seam");
        middleMainPanel.addStyleName("middle-main");
    }

    private static void assembleDockPanel(){
        // Assemble the dock panel
        dockPanel.add(headerPanel, DockPanel.NORTH);
        dockPanel.add(footerPanel, DockPanel.SOUTH);
        dockPanel.add(middleMainPanel, DockPanel.EAST);
        dockPanel.add(controlPanel, DockPanel.WEST);
    }

    private static void assembleDockLayoutPanel() {
        dockLayoutPanel.addNorth(headerPanel, 3);
        dockLayoutPanel.addSouth(footerPanel, 3);
        dockLayoutPanel.addEast(middleMainPanel, 60);
        dockLayoutPanel.addWest(controlPanel, 20);
    }

}

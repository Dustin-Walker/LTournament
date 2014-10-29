package LTournament.client;

import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import java.util.ArrayList;

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
    private Label addPlayerNameLabel = new Label();
    private Label rosterListLabel = new Label();
    private DockPanel dockPanel = new DockPanel();

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

        // TODO Fix remove button
        // TODO Add buffers to CSS
        // TODO Go cray on CSS
        // TODO Create panels for team list section
        // TODO Create algorithm to create teams
        // TODO Update the team list panel

        // Assemble Add Player panel
        addPanel.add(addPlayerNameLabel);
        addPanel.add(newPlayerNameTextBox);
        addPanel.add(addPlayerButton);
        addPanel.add(resetRosterButton);
        addPanel.addStyleName("addpanel");
        addPlayerButton.addStyleName("add-button");
        resetRosterButton.addStyleName("reset-button");

        // Assemble the header panel
        rosterListLabel.setText("Player List");
        rosterTableHeader.add(rosterListLabel);
        rosterTableHeader.addStyleName("rostertableheader");

        // Assemble the footer panel
        Grid footGrid = new Grid(1,1);
        footGrid.setText(0,0,"League of Legends Tournament System isn't endorsed by Riot Games and doesn't reflect the views or opinions of Riot Games or anyone officially involved in producing or managing League of Legends. League of Legends and Riot Games are trademarks or registered trademarks of Riot Games, Inc. League of Legends © Riot Games, Inc.");
        footGrid.setStylePrimaryName("footer");
        footerPanel.addStyleName("footerpanel");
        footerPanel.add(footGrid);

        // Assemble the playerListPanel
        playerListPanel.add(rosterTableHeader);
        playerListPanel.add(addPanel);
        playerListPanel.add(rosterTable);
        playerListPanel.addStyleName("roster-table");

        // Assemble the header panel
        headerPanel.add(new Label("League of Legends Tournament System"));
        headerPanel.addStyleName("header");

        // Assemble the middle panel
        middleMainPanel.add(playerListPanel);
        middleMainPanel.add(teamListPanel);
        teamListPanel.addStyleName("team-list");
        middleMainPanel.addStyleName("seam");
        middleMainPanel.addStyleName("middle-main");

        // Assemble the dock panel
        teamListPanel.add(new Label("Team List"));
        dockPanel.add(headerPanel, DockPanel.NORTH);
        dockPanel.add(footerPanel, DockPanel.SOUTH);
        dockPanel.add(middleMainPanel, DockPanel.CENTER);
        dockPanel.setCellHorizontalAlignment(middleMainPanel, HasHorizontalAlignment.ALIGN_CENTER);
        dockPanel.setCellHorizontalAlignment(headerPanel, HasHorizontalAlignment.ALIGN_CENTER);
        dockPanel.setCellHorizontalAlignment(footerPanel, HasHorizontalAlignment.ALIGN_CENTER);



        // Associate main panel with HTML host page
        RootPanel.get("playerRoster").add(dockPanel);

        // Get some CSS goin
        rosterTable.setStyleName("roster-table");
        //Listen for mouse events on Reset button
        resetRosterButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                rosterTable.removeAllRows();
            }
        });

        // Listen for mouse events on the Add button
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
                                        rosterTable.getRowFormatter().addStyleName(row-1,"challenger");
                                    } else if(s.contains("DIAMOND")){
                                        dd.setRank("DIAMOND");
                                        rosterTable.getRowFormatter().addStyleName(row-1,"diamond");
                                    } else if(s.contains("PLATINUM")){
                                        dd.setRank("PLATINUM");
                                        rosterTable.getRowFormatter().addStyleName(row-1,"platinum");
                                    } else if(s.contains("GOLD")){
                                        dd.setRank("GOLD");
                                        rosterTable.getRowFormatter().addStyleName(row-1,"gold");
                                    } else if (s.contains("SILVER")) {
                                        dd.setRank("SILVER");
                                        rosterTable.getRowFormatter().addStyleName(row-1,"silver");
                                    } else if (s.contains("BRONZE")) {
                                        dd.setRank("BRONZE");
                                        rosterTable.getRowFormatter().addStyleName(row-1,"bronze");
                                    } else {
                                        dd.setRank("UNRANKED");
                                        rosterTable.getRowFormatter().addStyleName(row-1,"unranked");
                                    }
                                    playerDataList.get(playerDataList.size()-1).setRank(dd.getRank());
                                    final Button removePlayerButton = new Button();
                                    removePlayerButton.addStyleName("remove-button");
                                    rosterTable.setWidget(row-1, 1, removePlayerButton);
                                    removePlayerButton.addClickHandler(new ClickHandler() {
                                        @Override
                                        public void onClick(ClickEvent event) {
                                            rosterTable.removeRow(row-1);
                                            playerDataList.remove(dd);

                                        }
                                    });
                                    //System.out.println("test1: "+playerDataList.get(playerDataList.size()-1).getRank());
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
                            //System.out.println("test2: "+playerDataList.get(playerDataList.size()-1).getRank());
                            rosterTable.setText(row, 0, d.getSummonerName());
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

        //Listen for keyboard events in the input box, pressing ENTER
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
                                       // System.out.println(s);
                                        final PlayerData dd = playerDataList.get(playerDataList.size()-1);
                                        if(s.contains("CHALLENGER")){
                                            dd.setRank("CHALLENGER");
                                            rosterTable.getRowFormatter().addStyleName(row-1,"challenger");
                                        } else if(s.contains("DIAMOND")){
                                            dd.setRank("DIAMOND");
                                            rosterTable.getRowFormatter().addStyleName(row-1,"diamond");
                                        } else if(s.contains("PLATINUM")){
                                            dd.setRank("PLATINUM");
                                            rosterTable.getRowFormatter().addStyleName(row-1,"platinum");
                                        } else if(s.contains("GOLD")){
                                            dd.setRank("GOLD");
                                            rosterTable.getRowFormatter().addStyleName(row-1,"gold");
                                        } else if (s.contains("SILVER")) {
                                            dd.setRank("SILVER");
                                            rosterTable.getRowFormatter().addStyleName(row-1,"silver");
                                        } else if (s.contains("BRONZE")) {
                                            dd.setRank("BRONZE");
                                            rosterTable.getRowFormatter().addStyleName(row-1,"bronze");
                                        } else {
                                            //System.out.println("NO RANK AVAIlABLE");
                                            dd.setRank("UNRANKED");
                                            rosterTable.getRowFormatter().addStyleName(row-1,"unranked");
                                        }
                                        final Button removePlayerButton = new Button();
                                        removePlayerButton.addStyleName("remove-button");
                                        rosterTable.setWidget(row-1, 1, removePlayerButton);
                                        removePlayerButton.addClickHandler(new ClickHandler() {
                                            @Override
                                            public void onClick(ClickEvent event) {
                                                rosterTable.removeRow(row-1);
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
                                rosterTable.setText(row, 0, d.getSummonerName());
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

}
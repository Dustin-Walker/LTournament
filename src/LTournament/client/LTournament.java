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

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        GUI.assembleStartUp();
        RootLayoutPanel rp = RootLayoutPanel.get();
        rp.add(GUI.dockLayoutPanel);
/*
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


*/





}
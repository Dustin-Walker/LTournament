package LTournament.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.sun.org.apache.xpath.internal.operations.*;

import java.lang.String;
import java.math.MathContext;
import java.util.*;

/**
 * Created by user on 5/28/15.
 * This class contains methods for handling the tournament bracket.
 * This object resembles a binary tree.
 */
public class Tournament {

    // Parent nodes draw lines to children

    private ArrayList<Team> getTeams(){
        return GUI.tournamentHandler.getTeams();
    }
    private ArrayList<Team> teams = getTeams();
    private int size = teams.size();
    public Stack<Team> activeTeamStack = new Stack<>();
    public Stack<Team> nextRoundStack = new Stack<>();
    public boolean matchPending = false;

    private int size(){
        return this.size;
    }

    private int height(){
        return (int) Math.ceil(Math.log(teams.size()) / Math.log(2) + 1);
    }

    /**
     * This method populates the tournament tree with bracket nodes
     */
    public void populateTree(){
        // Create the initial column and call a recursive function on the set
        ArrayList<Team> teams = getTeams();
        int treeHeight = (int) Math.ceil(Math.log(teams.size())/Math.log(2))+1; // +1 is for the root node
        int numberOfRows = teams.size()*2-1;
        ArrayList<Bracket> initialBracketList = new ArrayList<>();
        for (int i = 0, j = 0; i < numberOfRows; i+=2, j++) {
            Bracket bracket = new Bracket(teams.get(j), 1, i);
            initialBracketList.add(bracket);
           // GUI.bracketGrid.setHTML(i, 1, teams.get(j).teamName);
        }

        for (Bracket bracket : initialBracketList){
            GUI.bracketGrid.setHTML(bracket.getRow(), bracket.getColumn(), bracket.getTeamName());
        }

        activeTeamStack.addAll(teams);

    }

    /**
     * Method handles interaction with the GUI display of the tree
     */
    public void updateGrid(ArrayList<Stack<Bracket>> treeNodesByHeight){
        for (Stack<Bracket> bracketStack : treeNodesByHeight){
            for (Bracket bracket : bracketStack){
                bracket.updateGUI();
            }
        }
    }

    public Team getPendingWinningTeam() {
        return pendingWinningTeam;
    }

    public void setPendingWinningTeam(String pendingWinningTeam) {
        for (Team team : teams)
            if (team.teamName.equals(pendingWinningTeam)){
                this.pendingWinningTeam = team;
                break;
            }
    }

    public void clearPendingWinningTeam(){
        this.pendingWinningTeam = null;
    }

    private Team pendingWinningTeam = null;

}
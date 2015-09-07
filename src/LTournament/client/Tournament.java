package LTournament.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.sun.org.apache.xpath.internal.operations.*;
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
    private Bracket root;
    private ArrayList<Team> teams = getTeams();
    private int size = teams.size();
    private Team pendingMatchWinner = null;
    public Stack<Team> activeTeamStack = new Stack<>();

    private int size(){
        return this.size;
    }

    public void preorderTraversal(){
         // TODO Create preorderTraversal method
    }

    public void postorderTraversal(){
         // TODO Create postorderTraversal method
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
        int numberOfColumns = treeHeight*2-1;
        ArrayList<Bracket> bracketList = new ArrayList<>();
        for (int i = 0, j = 0; i < numberOfColumns; i+=2, j++) {
            Bracket bracket = new Bracket(teams.get(j), 1, i);
            bracketList.add(bracket);
           // GUI.bracketGrid.setHTML(i, 1, teams.get(j).teamName);
        }

        for (Bracket bracket : bracketList){
            GUI.bracketGrid.setHTML(bracket.getRow(), bracket.getColumn(), bracket.getTeamName());
        }

        activeTeamStack.addAll(teams);

    }

    private void getMatch(Bracket bracket){
        assert bracket.getLeft()!=null;
        if (bracket.getRight() != null) {
            // Create GUI interaction panel

            // Attach click handlers

            // Update bootstrap
        } else {
            // Dont display to the GUI
            bracket.setTeam(bracket.getLeft().getTeam());
        }
    }

    private ClickHandler teamPanelClickHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            // Obtain team and set team to active state
        }
    };

    private void getNextMatch(){
        // TODO Create getNextMatch method
        // Sets up teams for the next match
        // Tree traversal and grab children nodes that are not null
    }

    private boolean isTournamentOver(){
        // TODO Create isTournamentOver method
        // Method determines when the tournament is over
        // Include a method to set a team as the winner
        return true;
    }

    /**
     * Gets the winner of the tournament if the tournament is over
     * @return Team object that won the tournament
     */
    private Team getWinner(){
        // TODO Create getWinner method
        // Throw exception if the tournament is not finished
        return null;
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


}
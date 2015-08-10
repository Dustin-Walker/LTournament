package LTournament.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.sun.org.apache.xpath.internal.operations.*;

import java.lang.reflect.Array;
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
    private void populateTree(){
        // TODO Create populateTree method

        /*
            Every node should be empty except for the final level
            All teams go on the final level as leaf nodes
        */

        int treeHeight = (int) Math.ceil(Math.log(teams.size())/Math.log(2))+1;

        ArrayList<Stack<Bracket>> treeNodesByHeight = new ArrayList<Stack<Bracket>>(treeHeight);

        // Bottom-up tree population

        // Set up leaf nodes
        for (Team team : teams) treeNodesByHeight.get(treeHeight-1).push(new Bracket(team));


        // Connect child nodes to parent node
        for (int i = treeHeight-1; i > 0; i--){
            // Calculate row for bracket and add that as well
            for (int j = treeHeight-1; j < treeNodesByHeight.get(i).size()/2; j--){
                if (i==1){
                    Bracket bracket = new Bracket(null, treeNodesByHeight.get(i).pop(), treeNodesByHeight.get(i).pop());
                    root = bracket;
                    treeNodesByHeight.get(i-1).push(bracket);
                    continue;
                }
                treeNodesByHeight.get(i-1).push(new Bracket(null, treeNodesByHeight.get(i).pop(), treeNodesByHeight.get(i).pop()));
            }
            if (!treeNodesByHeight.get(i).isEmpty()){
                treeNodesByHeight.get(i-1).push(new Bracket(null, treeNodesByHeight.get(i).pop(), null));
            }
        }


    }

    private void getMatch(Bracket bracket){
        assert bracket.getLeft()!=null;
        if (bracket.getRight()==null){
            // Dont display to the GUI
            bracket.setTeam(bracket.getLeft().getTeam());
        } else {
            // Create GUI interaction panel

            // Attach click handlers

            // Update bootstrap
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
        // Sets a team as the winner
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
    public void updateGrid(){

    }


}
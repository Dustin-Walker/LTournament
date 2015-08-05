package LTournament.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

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
    private Bracket root = new Bracket(0, null);

    private ArrayList<Team> teams = getTeams();

    public int size(){
        return size(root);
    }

    private int size(Bracket node){
        if (node==null) return 0;
        else return node.getSubtreeSize();
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
            Use a random number generator to match teams
            All teams go on the final level as leaf nodes
        */

    }

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


}
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

    private int pendingRow;
    private int pendingColumn;

    // Parent nodes draw lines to children

    private ArrayList<Team> getTeams(){
        return GUI.tournamentHandler.getTeams();
    }
    private ArrayList<Team> teams = getTeams();
    private int size = teams.size();
 //   public Stack<Team> activeTeamStack = new Stack<>();
 //   public Stack<Team> nextRoundStack = new Stack<>();
    public boolean matchPending = false;
    public Stack<Bracket> activeBracketStack = new Stack<>();
    public Stack<Bracket> nextRoundBracketStack = new Stack<>();

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

     //   activeTeamStack.addAll(teams);
        activeBracketStack.addAll(initialBracketList);

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

    public Bracket getPendingWinningTeam() {
        return pendingWinningBracket;
    }

    public void setPendingWinningTeam(String pendingWinningTeam) {
        /*for (Team team : teams)
            if (team.teamName.equals(pendingWinningTeam)){
                this.pendingWinningBracket.setTeam(team);
                break;
            }*/
        /*for (Bracket bracket : activeBracketStack){
            if (bracket.getTeam().teamName.equals(pendingWinningTeam)){
                this.pendingWinningBracket = bracket;
                break;
            }
        }*/
        if (pendingWinningTeam.equals(pendingMatchBrackets[0].getTeamName()))
            pendingWinningBracket = pendingMatchBrackets[0];

        if (pendingWinningTeam.equals(pendingMatchBrackets[1].getTeamName()))
            pendingWinningBracket = pendingMatchBrackets[1];
    }

    public void clearPendingWinningTeam(){
        this.pendingWinningBracket = null;
    }

    private Bracket pendingWinningBracket = null;

    private Bracket[] pendingMatchBrackets = new Bracket[2];

    public void setPendingMatchBrackets(Bracket bracket1, Bracket bracket2){
        pendingMatchBrackets[0] = bracket1;
        pendingMatchBrackets[1] = bracket2;
    }

    public Bracket[] getPendingMatchBrackets(){
        return  pendingMatchBrackets;
    }

    public void clearPendingMatchBrackets(){
        pendingMatchBrackets = null;
    }

    public int getPendingRow() {
        return pendingRow;
    }

    public int getPendingColumn() {
        return pendingColumn;
    }

    public void setPendingColumn(int pendingColumn) {
        this.pendingColumn = pendingColumn;
    }

    public void setPendingRow(int pendingRow) {
        this.pendingRow = pendingRow;
    }
}
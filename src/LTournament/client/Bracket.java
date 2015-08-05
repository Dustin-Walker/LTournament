package LTournament.client;

/**
 * Created by user on 5/28/15.
 * This class represents a bracket in the tournament bracket.
 * This object will closely resemble a node from the tree data structure.
 */
public class Bracket {

    private Bracket left;
    private Bracket right;
    private Bracket parent;
    private Team team;
    private int subtreeSize;
    private String teamName;
    private boolean visited;
    private boolean knockedOut = false;
    // TODO Create background color and other fields for HTML display

    public Bracket(Team team, int N, Bracket parent){
        this.team = team;
        this.subtreeSize = N;
        this.teamName = team.teamName;
        this.parent = parent;
    }

    public Bracket(int N, Bracket parent){
        this.subtreeSize = N;
        this.parent = parent;
        this.team = null;
        this.teamName = null;
    }

    public void setKnockedOut(boolean knockedOut) {
        this.knockedOut = knockedOut;
        if (knockedOut){
            // Change background color to knocked out color
        }
    }

    public Bracket getParent() {
        return parent;
    }

    public void setParent(Bracket parent) {
        this.parent = parent;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setSubtreeSize(int subtreeSize) {
        this.subtreeSize = subtreeSize;
    }

    public int getSubtreeSize() {
        return subtreeSize;
    }

    public String getTeamName() {
        return teamName;
    }

    public Team getTeam() {
        return team;
    }

    public Bracket getLeft() {
        return left;
    }

    public void setLeft(Bracket left) {
        this.left = left;
    }

    public Bracket getRight() {
        return right;
    }

    public void setRight(Bracket right) {
        this.right = right;
    }

    /**
     * This method removes this node and children from the tree.
     */
    public void removeFromParent(){
        if (parent != null){
            if (parent.left == this)
                parent.left = null;
            else if (parent.right == this)
                parent.right = null;
            this.parent = null;
        }
    }

    /**
     * Method draws the lines on the grid to connect the graph.
     * @param column Column of cell to draw
     * @param row Row of cell to draw
     */
    public void drawBracketLines(int column, int row){
        return; // TODO Create drawBracketLines method
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

}
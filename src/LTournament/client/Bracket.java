package LTournament.client;

/**
 * Created by user on 5/28/15.
 * This class represents a bracket in the tournament bracket.
 */
public class Bracket {


    private Team team;
    private String teamName;
    private int row;
    private int column;

    public int getColumn(){
        return column;
    }

    public Bracket(Team team, int column, int row){
        this.team = team;
        this.column = column;
        this.row = row;
        this.teamName = this.team.teamName;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }

    public Team getTeam() {
        return team;
    }

    public int getRow() {
        return row;
    }

}
package LTournament.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by user on 5/28/15.
 * This class represents a Team which contains many players.
 */
public class Team extends HashMap<String, Player> {

    // Use player ID as hash key

    public String teamName;

    /**
     * This method pulls a random team name from the team name list. The method checks to make sure
     * duplicates do not occur.
     * @return String containing a random team name
     */
    public String randomTeamName() {
        if (teamNameList.isEmpty())
            Collections.addAll(teamNameList, teamNameArray);
        Random rnd = new Random();
        String returnString = "";
        returnString = teamNameList.remove(rnd.nextInt(teamNameList.size()));
        return returnString;
    }

    final private String[] teamNameArray = {
            "Team 1", "Team 2", "Team 3", "Team 4", "Team 5",
            "Team 6", "Team 7", "Team 8", "Team 9", "Team 10",
            "Team 11", "Team 12", "Team 13", "Team 14", "Team 15",
            "Team 16", "Team 17", "Team 18", "Team 19", "Team 20"
    };
    private ArrayList<String> teamNameList = new ArrayList<String>();




}

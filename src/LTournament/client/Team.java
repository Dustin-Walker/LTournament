package LTournament.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by user on 5/28/15.
 * This class represents a Team which contains many players.
 * The key should be the player name and the value should be the corresponding player object.
 */
public class Team extends HashMap<String, Player> {

    public Team(String name){
        this.teamName = name;
    }

    public String teamName;

    public Team() {

    }
}

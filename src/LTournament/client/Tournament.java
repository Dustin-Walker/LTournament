package LTournament.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by user on 5/28/15.
 * This class contains methods for handling the tournament bracket.
 */
public class Tournament {

    static HashMap<String, Player> summonerNameList = new HashMap<String, Player>();

    /**
     * This method generates teams based on a randomization algorithm. These teams are teams of 5 to be used
     * for the Summoner's Rift map and any other 5 player per team map. Teams of 3 for the other map.
     * @param players Collection of all the players to put into teams
     * @return List of teams containing players
     */
    private HashMap generateTeams(HashMap players){
        return new HashMap();
    }

}
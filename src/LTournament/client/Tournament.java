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

    public ClickHandler undoLastAction = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            // TODO Implement undo action handler

        }
    };

    // TODO Draw brackets
    public void drawBrackets(){
        GUI.tournamentHandler.getTeams();
        // Use CanvasElement, Canvas and Context2D to connect things
    }

}
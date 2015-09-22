package LTournament.client;

/**
 * Created by Dustin on 7/1/15.
 */
public class bootstrapAlerts {

    // Phase 1
    public final static String PLAYER_NOT_FOUND_WARNING = "<div class=\"alert alert-danger text-center\" role=\"alert\"><strong>Warning!</strong><br />Player not found.</div>";
    public final static String RATE_LIMIT_WARNING = "<div class=\"alert alert-danger text-center\" role=\"alert\"><strong>Slow down!</strong><br />You are sending too many requests.</div>";
    public final static String SERVER_ERROR_WARNING = "<div class=\"alert alert-danger text-center\" role=\"alert\"><strong>Warning!</strong><br />The remote server encountered an error.</div>";
    public final static String PLAYER_ADDED_SUCCESSFULLY = "<div class=\"alert alert-success\" role=\"alert\">Success!<br />Player added.</div>";
    public final static String DUPLICATE_PLAYER_WARNING = "<div class=\"alert alert-danger\" role=\"alert\"><strong>Warning!</strong>\nThis player is<br />already in the game.</div>";
    public final static String MAX_PLAYERS_WARNING = "<div class=\"alert alert-danger\" role=\"alert\"><strong>Warning!</strong>\nYou have reached<br />the maximum number of<br />supported players..</div>";

    // Phase 2
    public final static String SUCCESSFUL_TEAM_CREATION = "<div class=\"alert alert-success\" role=\"alert\">Success!<br />Teams created.</div>";
    public final static String TRADE_RESET_ALERT = "<div class=\"alert alert-info\" role=\"alert\"><strong>Trade reset.</strong></div>";
    private static String[] swapPlayerName = new String[2];
    private static boolean isFirstPlayerSetForTrade = false;
    private static boolean isSecondPlayerSetForTrade = false;
    public final static String SWAP_NOT_READY = "<div class=\"alert alert-danger\" role=\"alert\"><strong>Warning!</strong>\nTrade not completed. One of the players is not set or you tried to trade players that are on the same team.</div>";
    public static String PLAYER_NAME_LENGTH = "<div class=\"alert alert-danger\" role=\"alert\"><strong>Warning!</strong>\nThe name you entered is too long. The maximum size for a player name is 16 characters.</div>";

    public static String sameTeamAlert(String playerName){
        isSecondPlayerSetForTrade = true;
        swapPlayerName[1] = playerName;
        return  "<div class=\"alert alert-warning text-center\" role=\"alert\">"+
                makeBold(swapPlayerName[0])+" and "+makeBold(swapPlayerName[1])+" are already on the same team.";
    }

    public static void resetTradeStatus(){
        isFirstPlayerSetForTrade=false;
        isSecondPlayerSetForTrade=false;
        swapPlayerName[0]=null; swapPlayerName[1]=null;
    }

    public static String tradeButtonAlert(){
        final String htmlOpener = "<div class=\"alert alert-success text-center\" role=\"alert\">";
        final String contentMiddle = " and ";
        final String htmlCloser = "</div>";

        return htmlOpener + makeBold("Success! ") + "\n" + makeBold(swapPlayerName[0]) + contentMiddle
                + makeBold(swapPlayerName[1]) + " have been traded." + htmlCloser;
    }

    public static String setPlayerSwap(String playerName) {
        final String htmlOpener = "<div class=\"alert alert-success text-center\" role=\"alert\">";
        final String contentMiddle = " and ";
        final String htmlCloser = "</div>";

        // If the first player has not been set to be traded, use this string
        if (!isFirstPlayerSetForTrade){
            final String contentCloser = " is ready to be traded.";
            isFirstPlayerSetForTrade = true;
            swapPlayerName[0] = playerName;
            return htmlOpener + makeBold(swapPlayerName[0]) + contentCloser + htmlCloser;
        }

        // If the first player is set and the second player is not set
        if (!isSecondPlayerSetForTrade){
            final String contentCloser = " are ready to be traded.";
            isSecondPlayerSetForTrade = true;
            swapPlayerName[1] = playerName;
            return htmlOpener + makeBold(swapPlayerName[0]) + contentMiddle + makeBold(swapPlayerName[1]) + contentCloser + htmlCloser;
        }

        // If both players are set
        final String contentCloser = " are already set to be traded.\nUse the swap button or reset button\nto continue.";
        return htmlOpener + makeBold(swapPlayerName[0]) + contentMiddle + makeBold(swapPlayerName[1]) + contentCloser + htmlCloser;
    }

    // Phase 3
    public final static String BEGIN_TOURNAMENT = "<div class=\"alert alert-info\" role=\"alert\">You can now begin the tournament.</div>";
    public final static String MATCH_ONGOING = "<div class=\"alert alert-info\" role=\"alert\">Once the match is over, select a winner by clicking on the team name and then clicking confirm.</div>";
    public final static String TOURNAMENT_FINISHED =  "<div class=\"alert alert-success\" role=\"alert\">The tournament is finished!</div>";
    public final static String NO_PENDING_WINNING_TEAM = "<div class=\"alert alert-info\" role=\"alert\">No team has been selected as a winner. Please click the appropriate button for the team that won the match before clicking the confirm button.</div>";

    public static String matchWinner(String teamName){
        return "<div class=\"alert alert-warning text-center\" role=\"alert\">" + makeBold(teamName) + " has been selected as the winner. Click the confirm button to continue.</div>";
    }

    private static String makeBold(String s){
        return "<strong>" + s + "</strong>";
    }
}

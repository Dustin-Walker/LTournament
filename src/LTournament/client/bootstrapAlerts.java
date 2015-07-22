package LTournament.client;

/**
 * Created by Dustin on 7/1/15.
 */
public class bootstrapAlerts {

    // Bootstrap alerts
    public final static String playerNotFoundWarning = "<div class=\"alert alert-danger text-center\" role=\"alert\"><strong>Warning!</strong><br />Player not found.</div>";
    public final static String rateLimitWarning = "<div class=\"alert alert-danger text-center\" role=\"alert\"><strong>Slow down!</strong><br />You are sending too many requests.</div>";
    public final static String serverErrorWarning = "<div class=\"alert alert-danger text-center\" role=\"alert\"><strong>Warning!</strong><br />The remote server encountered an error.</div>";
    public final static String successAlert = "<div class=\"alert alert-success\" role=\"alert\">Success!<br />Player added.</div>";
    public final static String duplicatePlayerWarning = "<div class=\"alert alert-danger\" role=\"alert\"><strong>Warning!</strong>\nThis player is<br />already in the game.</div>";
    public final static String maxPlayersWarning = "<div class=\"alert alert-danger\" role=\"alert\"><strong>Warning!</strong>\nYou have reached<br />the maximum number of<br />supported players..</div>";
    public final static String successfulTeamCreation = "<div class=\"alert alert-success\" role=\"alert\">Success!<br />Teams created.</div>";

    public final static String tradeResetAlert = "<div class=\"alert alert-info\" role=\"alert\"><strong>Trade reset.</strong></div>";

    private static String[] swapPlayerName = new String[2];

    private static boolean isFirstPlayerSetForTrade = false;
    private static boolean isSecondPlayerSetForTrade = false;
    public static String swapNotReady = "<div class=\"alert alert-danger\" role=\"alert\"><strong>Warning!</strong>\nTrade not completed. One of the players is not set or you tried to trade players that are on the same team.</div>";

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

    private static String makeBold(String s){
        return "<strong>" + s + "</strong>";
    }
}

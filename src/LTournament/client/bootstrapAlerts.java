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

    private static String[] swapPlayerName = new String[2];

    private static boolean isFirstPlayerSetForTrade = false;

    public static String setPlayerSwap(String activeSwapPlayerName) {
        // If the first player has not been set to be traded, use this string
        if (!(isFirstPlayerSetForTrade)){
            final String htmlOpener = "<div class=\"alert alert-success text-center\" role=\"alert\">";
            final String contentOpener = "<strong>";
            final String contentCloser = " is ready to be traded.";
            final String htmlCloser = "</div>";
            isFirstPlayerSetForTrade = true;
            swapPlayerName[0] = activeSwapPlayerName;
            return htmlOpener + contentOpener + activeSwapPlayerName + contentCloser + htmlCloser;
        }

        if (isFirstPlayerSetForTrade){
            final String htmlOpener = "<div class=\"alert alert-success text-center\" role=\"alert\">";
            final String contentOpener = "<strong>";
            final String contentMiddle = " and ";
            final String contentCloser = " are ready to be traded.";
            final String htmlCloser = "</div>";
            isFirstPlayerSetForTrade = false;
            swapPlayerName[1] = activeSwapPlayerName;
            return htmlOpener + contentOpener + swapPlayerName[0] + contentMiddle + swapPlayerName[1] + contentCloser + htmlCloser;
        }

        return "<div class=\"alert alert-danger text-center\" role=\"alert\"><strong>Something went wrong!</strong>.</div>";

    }
}

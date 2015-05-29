package LTournament.client;

/**
 * Created by User on 10/2/2014.
 * This class represents a player in the tournament.
 * This is an overlay type, used to manipulate a JSON object.
 */
public class Player {
    /**
     * Use this constructor when player submits player name
     * @param response Response to REST call to Riot API for summoner data
     */
    public Player(String response){
        this.setPlayerID(response);
        this.setSummonerName(response);
    }

    // Members
    // TODO Change default value for tier
    public String tier = "BRONZE";
    private String playerID;
    private String summonerName;
    private String rank;

    public void setPlayerID(String response){
        this.playerID = response.split("[:]")[2].split("[,]")[0];
    }

    public void setSummonerName(String response){
        this.summonerName = response.split("[:]")[3].split("[,]")[0].replace("\"", "");
    }

    public String getPlayerID() {
        return playerID;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String newRank) {
        this.rank = newRank;
    }
}

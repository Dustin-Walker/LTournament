package LTournament.client;

/**
 * Created by User on 10/2/2014.
 * This class represents a player in the tournament.
 * This is an overlay type, used to manipulate a JSON object.
 */
public class Player implements Comparable<Player>{
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
    private Rank rank;

    public void setPlayerID(String response){
        this.playerID = response.split("[:]")[2].split("[,]")[0];
    }

    public void setSummonerName(String response){
        this.summonerName = response.split("[:]")[3].split("[,]")[0].replace("\"", "");
    }

    public void setSummonerNameSample(String name){
        this.summonerName = name;
    }

    public String getPlayerID() {
        return playerID;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(String serverResponse){
        if (serverResponse.contains("CHALLENGER"))
            this.rank = Rank.CHALLENGER;
        else if (serverResponse.contains("MASTER"))
            this.rank = Rank.MASTER;
        else if (serverResponse.contains("DIAMOND"))
            this.rank = Rank.DIAMOND;
        else if (serverResponse.contains("PLATINUM"))
            this.rank = Rank.PLATINUM;
        else if (serverResponse.contains("GOLD"))
            this.rank = Rank.GOLD;
        else if (serverResponse.contains("SILVER"))
            this.rank = Rank.SILVER;
        else if (serverResponse.contains("BRONZE"))
            this.rank = Rank.BRONZE;
        else
            this.rank = Rank.UNRANKED;
    }

    @Override
    public int compareTo(Player o) {
        return rank.compareTo(o.getRank());
    }
}

import java.util.ArrayList;
import java.util.HashMap;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Given the standings in a sports division at some point during the season, determine which teams
 * have been mathematically eliminated from winning their division.
 *
 * @author bwu2018
 *
 */
public class BaseballElimination {

    /**
     * Number of teams.
     */
    private int numberOfTeams;

    /**
     * Map with teams as keys and ids as values.
     */
    private HashMap<String, Integer> teamToID;

    /**
     * Map with ids as keys and teams as values.
     */
    private HashMap<Integer, String> idToTeam;

    /**
     * Wins.
     */
    private int[] wins;

    /**
     * Losses.
     */
    private int[] loses;

    /**
     * Remaining games.
     */
    private int[] remaining;

    /**
     * Remaining games against each team in the division.
     */
    private int[][] games;

    /**
     * Create a baseball division from given filename.
     * @param filename
     *      given file
     */
    public BaseballElimination(String filename) {
        In in = new In(filename);
        this.numberOfTeams = in.readInt();
        this.teamToID = new HashMap<String, Integer>(numberOfTeams);
        this.idToTeam = new HashMap<Integer, String>(numberOfTeams);
        this.wins = new int[numberOfTeams];
        this.loses = new int[numberOfTeams];
        this.remaining = new int[numberOfTeams];
        this.games = new int[numberOfTeams][numberOfTeams];

        for (int i = 0; i < numberOfTeams; i++) {
            String team = in.readString();
            teamToID.put(team, i);
            idToTeam.put(i, team);
            wins[i] = in.readInt();
            loses[i] = in.readInt();
            remaining[i] = in.readInt();
            for (int j = 0; j < numberOfTeams; j++) {
                games[i][j] = in.readInt();
            }
        }

    }

    /**
     * Returns number of teams.
     *
     * @return number of teams
     */
    public int numberOfTeams() {
        return numberOfTeams;
    }

    /**
     * Returns an iterable of all team names.
     *
     * @return team names
     */
    public Iterable<String> teams() {
        return teamToID.keySet();
    }

    /**
     * Throws exception if not a valid team.
     *
     * @param team
     *            a team
     */
    private void isValidTeam(String team) {
        if (team == null) {
            throw new IllegalArgumentException();
        }
        if (!teamToID.containsKey(team)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns wins of team.
     *
     * @param team
     *            a team
     * @return wins of team
     */
    public int wins(String team) {
        isValidTeam(team);
        return wins[teamToID.get(team)];
    }

    /**
     * Returns losses of team.
     *
     * @param team
     *            a team
     * @return losses of team
     */
    public int losses(String team) {
        isValidTeam(team);
        return loses[teamToID.get(team)];
    }

    /**
     * Returns number of remaining games.
     *
     * @param team
     *            a team
     * @return number of remaining games
     */
    public int remaining(String team) {
        isValidTeam(team);
        return remaining[teamToID.get(team)];
    }

    /**
     * Returns number of remaining games between two teams.
     *
     * @param team1
     *            a team
     * @param team2
     *            a team
     * @return number of remaining games
     */
    public int against(String team1, String team2) {
        isValidTeam(team1);
        isValidTeam(team2);
        return games[teamToID.get(team1)][teamToID.get(team2)];
    }

    /**
     * Returns true if team has been trivially eliminated (total games won and remaining is less
     * than another team's wins).
     *
     * @param x
     *            id of a team
     * @return true if trivially eliminated
     */
    private boolean triviallyEliminated(int x) {
        for (int i = 0; i < numberOfTeams; i++) {
            if (wins[x] + remaining[x] < wins[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a flow network.
     *
     * @param numberOfTeams
     *            number of teams
     * @param maxWins
     *            wins + remaining games of given team
     * @return a flow network
     */
    private FlowNetwork createNetwork(int numberOfTeams, int maxWins) {
        // calculate number of matches
        int numMatches = numberOfTeams * (numberOfTeams - 1) / 2;
        FlowNetwork network = new FlowNetwork(numberOfTeams + numMatches + 2); // +2 because source
                                                                               // and sink
        int vertex = 1;
        int teamVertex = numMatches + 1;
        for (int col = 0; col < games.length; col++) {
            for (int row = col + 1; row < games[col].length; row++) {
                // edge from source to match vertex
                network.addEdge(new FlowEdge(0, vertex, games[col][row]));

                // edge from vertex to team1
                network.addEdge(
                        new FlowEdge(vertex, numMatches + 1 + col, Double.POSITIVE_INFINITY));
                // edge from vertex to team2
                network.addEdge(
                        new FlowEdge(vertex, numMatches + 1 + row, Double.POSITIVE_INFINITY));
                vertex++;
            }
            // edge from teams to sink
            int capacity = maxWins - wins[teamVertex - numMatches - 1];
            if (maxWins - wins[teamVertex - numMatches - 1] < 0) {
                capacity = 0;
            }
            network.addEdge(new FlowEdge(teamVertex, numberOfTeams + numMatches + 1, capacity));
            teamVertex++;
        }
        return network;
    }

    /**
     * Returns true if team has been eliminated.
     *
     * @param team
     *            a team
     * @return if team has been eliminated
     */
    public boolean isEliminated(String team) {
        isValidTeam(team);
        if (triviallyEliminated(teamToID.get(team))) {
            return true;
        }
        int maxWins = wins(team) + remaining(team);
        int numMatches = numberOfTeams * (numberOfTeams - 1) / 2;
        FlowNetwork network = createNetwork(numberOfTeams, maxWins);
        FordFulkerson fordFulkerson = new FordFulkerson(network, 0, numberOfTeams + numMatches + 1);
        for (FlowEdge e : network.adj(0)) {
            if (e.flow() != e.capacity()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a subset R of teams that eliminates given team; null if not eliminated.
     * @param team
     *      a team
     * @return subset R of teams that eliminates given team; null if not eliminated
     */
    public Iterable<String> certificateOfElimination(String team) {
        isValidTeam(team);
        if (!isEliminated(team)) {
            return null;
        }
        ArrayList<String> certificate = new ArrayList<String>();
        int maxWins = wins(team) + remaining(team);
        int numMatches = numberOfTeams * (numberOfTeams - 1) / 2;
        if (triviallyEliminated(teamToID.get(team))) {
            for (int i = 0; i < numberOfTeams; i++) {
                if (wins(idToTeam.get(i)) > maxWins) {
                    certificate.add(idToTeam.get(i));
                    return certificate;
                }
            }
        }

        FlowNetwork network = createNetwork(numberOfTeams, maxWins);
        FordFulkerson fordFulkerson = new FordFulkerson(network, 0, numberOfTeams + numMatches + 1);
        for (int i = 0; i < numberOfTeams; i++) {
            if (fordFulkerson.inCut(i + numMatches + 1)) {
                certificate.add(idToTeam.get(i));
            }
        }
        return certificate;
    }

    /**
     * Reads in a sports division from an input file and prints whether each team is mathematically
     * eliminated and a certificate of elimination for each team that is eliminated.
     *
     * @param args
     *      input file
     */
    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(
                "baseball-testing-files/teams4b.txt");
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}

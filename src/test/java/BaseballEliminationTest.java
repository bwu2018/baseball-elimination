import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Brandon Wu
 *
 */
public class BaseballEliminationTest {

    BaseballElimination baseballEliminationTeams4;
    BaseballElimination baseballEliminationTeams5;
    BaseballElimination baseballEliminationTeams7;
    BaseballElimination baseballEliminationTeams5b;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        baseballEliminationTeams4 = new BaseballElimination("baseball-testing-files/teams4.txt");
        baseballEliminationTeams5 = new BaseballElimination("baseball-testing-files/teams5.txt");
        baseballEliminationTeams7 = new BaseballElimination("baseball-testing-files/teams7.txt");
        baseballEliminationTeams5b = new BaseballElimination("baseball-testing-files/teams5b.txt");
    }

    /**
     * Test method for {@link BaseballElimination#numberOfTeams()}.
     */
    @Test
    public void testNumberOfTeams() {
        assertEquals(4, baseballEliminationTeams4.numberOfTeams());
    }

    @Test
    public void testNumberOfTeams5() {
        assertEquals(5, baseballEliminationTeams5.numberOfTeams());
    }

    @Test
    public void testNumberOfTeams7() {
        assertEquals(7, baseballEliminationTeams7.numberOfTeams());
    }

    /**
     * Test method for {@link BaseballElimination#teams()}.
     */
    @Test
    public void testTeams() {
        String[] teams4Expected = { "Atlanta", "Philadelphia", "New_York", "Montreal" };
        ArrayList<String> teams4ExpectedList = new ArrayList<String>();
        for (String team : teams4Expected) {
            teams4ExpectedList.add(team);
        }
        for (String actualTeam : baseballEliminationTeams4.teams()) {
            assertTrue(actualTeam + " is not in the expected list",
                    teams4ExpectedList.contains(actualTeam));
        }
    }

    /**
     * Test method for {@link BaseballElimination#wins(java.lang.String)}.
     */
    @Test
    public void testWinsTeams4() {
        String[] teams4Expected = { "Atlanta", "Philadelphia", "New_York", "Montreal" };
        int[] teams4ExpectedWins = { 83, 80, 78, 77 };
        for (int i = 0; i < teams4ExpectedWins.length; i++) {
            assertEquals(teams4ExpectedWins[i], baseballEliminationTeams4.wins(teams4Expected[i]));
        }
    }

    @Test
    public void testWinsTeams5() {
        String[] teams5Expected = { "New_York", "Baltimore", "Boston", "Toronto", "Detroit" };
        int[] teams5ExpectedWins = { 75, 71, 69, 63, 49 };
        for (int i = 0; i < teams5ExpectedWins.length; i++) {
            assertEquals(teams5ExpectedWins[i], baseballEliminationTeams5.wins(teams5Expected[i]));
        }
    }

    @Test
    public void testWinsTeams7() {
        String[] teams7Expected = { "U.S.A.", "England", "France", "Germany", "Ireland", "Belgium",
                "China" };
        int[] teams7ExpectedWins = { 14, 12, 16, 13, 11, 12, 13 };
        for (int i = 0; i < teams7ExpectedWins.length; i++) {
            assertEquals(teams7ExpectedWins[i], baseballEliminationTeams7.wins(teams7Expected[i]));
        }
    }

    /**
     * Test method for {@link BaseballElimination#losses(java.lang.String)}.
     */
    @Test
    public void testLosses() {
        String[] teams4Expected = { "Atlanta", "Philadelphia", "New_York", "Montreal" };
        int[] teams4ExpectedLosses = { 71, 79, 78, 82 };
        for (int i = 0; i < teams4Expected.length; i++) {
            assertEquals(teams4ExpectedLosses[i],
                    baseballEliminationTeams4.losses(teams4Expected[i]));
        }
    }

    @Test
    public void testLosses5() {
        String[] teams5Expected = { "New_York", "Baltimore", "Boston", "Toronto", "Detroit" };
        int[] teams5ExpectedLosses = { 59, 63, 66, 72, 86 };
        for (int i = 0; i < teams5Expected.length; i++) {
            assertEquals(teams5ExpectedLosses[i],
                    baseballEliminationTeams5.losses(teams5Expected[i]));
        }
    }

    @Test
    public void testLosses7() {
        String[] teams7Expected = { "U.S.A.", "England", "France", "Germany", "Ireland", "Belgium",
                "China" };
        int[] teams7ExpectedLosses = { 5, 3, 2, 3, 3, 4, 2 };
        for (int i = 0; i < teams7Expected.length; i++) {
            assertEquals(teams7ExpectedLosses[i],
                    baseballEliminationTeams7.losses(teams7Expected[i]));
        }
    }

    /**
     * Test method for {@link BaseballElimination#remaining(java.lang.String)}.
     */
    @Test
    public void testRemaining() {
        String[] teams4Expected = { "Atlanta", "Philadelphia", "New_York", "Montreal" };
        int[] teams4ExpectedRemaining = { 8, 3, 6, 3 };
        for (int i = 0; i < teams4Expected.length; i++) {
            assertEquals(teams4ExpectedRemaining[i],
                    baseballEliminationTeams4.remaining(teams4Expected[i]));
        }
    }

    @Test
    public void testRemaining5() {
        String[] teams5Expected = { "New_York", "Baltimore", "Boston", "Toronto", "Detroit" };
        int[] teams5ExpectedRemaining = { 28, 28, 27, 27, 27 };
        for (int i = 0; i < teams5Expected.length; i++) {
            assertEquals(teams5ExpectedRemaining[i],
                    baseballEliminationTeams5.remaining(teams5Expected[i]));
        }
    }

    @Test
    public void testRemaining7() {
        String[] teams7Expected = { "U.S.A.", "England", "France", "Germany", "Ireland", "Belgium",
                "China" };
        int[] teams7ExpectedRemaining = { 9, 7, 7, 5, 5, 7, 2 };
        for (int i = 0; i < teams7Expected.length; i++) {
            assertEquals(teams7ExpectedRemaining[i],
                    baseballEliminationTeams7.remaining(teams7Expected[i]));
        }
    }

    /**
     * Test method for {@link BaseballElimination#against(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testAgainstTeams4() {
        String[] teams4Expected = { "Atlanta", "Philadelphia", "New_York", "Montreal" };
        int[] teams4ExpectedAgainstAtlanta = { 0, 1, 6, 1 };
        int[] teams4ExpectedAgainstMontreal = { 1, 2, 0, 0 };
        for (int i = 0; i < teams4Expected.length; i++) {
            assertEquals(teams4ExpectedAgainstAtlanta[i],
                    baseballEliminationTeams4.against("Atlanta", teams4Expected[i]));
            assertEquals(teams4ExpectedAgainstMontreal[i],
                    baseballEliminationTeams4.against("Montreal", teams4Expected[i]));
        }
    }

    @Test
    public void testAgainstTeams5() {
        String[] teams5Expected = { "New_York", "Baltimore", "Boston", "Toronto", "Detroit" };
        int[] teams5ExpectedAgainstNewYork = { 0, 3, 8, 7, 3 };
        int[] teams5ExpectedAgainstBaltimore = { 3, 0, 2, 7, 7 };
        for (int i = 0; i < teams5Expected.length; i++) {
            assertEquals(teams5ExpectedAgainstNewYork[i],
                    baseballEliminationTeams5.against("New_York", teams5Expected[i]));
            assertEquals(teams5ExpectedAgainstBaltimore[i],
                    baseballEliminationTeams5.against("Baltimore", teams5Expected[i]));
        }
    }

    @Test
    public void testAgainstTeams7() {
        String[] teams7Expected = { "U.S.A.", "England", "France", "Germany", "Ireland", "Belgium",
                "China" };
        int[] teams7ExpectedAgainstUSA = { 0, 1, 2, 3, 1, 2, 0 };
        int[] teams7ExpectedAgainstEngland = { 1, 0, 2, 1, 2, 1, 0 };
        for (int i = 0; i < teams7Expected.length; i++) {
            assertEquals(teams7ExpectedAgainstUSA[i],
                    baseballEliminationTeams7.against("U.S.A.", teams7Expected[i]));
            assertEquals(teams7ExpectedAgainstEngland[i],
                    baseballEliminationTeams7.against("England", teams7Expected[i]));
        }
    }

    /**
     * Test method for {@link BaseballElimination#isEliminated(java.lang.String)}.
     */
    @Test
    public void testIsEliminatedTeams4() {
        assertFalse(baseballEliminationTeams4.isEliminated("Atlanta"));
        assertTrue(baseballEliminationTeams4.isEliminated("Philadelphia"));
        assertFalse(baseballEliminationTeams4.isEliminated("New_York"));
        assertTrue(baseballEliminationTeams4.isEliminated("Montreal"));
    }

    @Test
    public void testIsEliminatedTeams5() {
        assertFalse(baseballEliminationTeams5.isEliminated("New_York"));
        assertFalse(baseballEliminationTeams5.isEliminated("Baltimore"));
        assertFalse(baseballEliminationTeams5.isEliminated("Boston"));
        assertFalse(baseballEliminationTeams5.isEliminated("Toronto"));
        assertTrue(baseballEliminationTeams5.isEliminated("Detroit"));
    }

    @Test
    public void testIsEliminatedTeams7() {
        assertFalse(baseballEliminationTeams7.isEliminated("U.S.A."));
        assertFalse(baseballEliminationTeams7.isEliminated("England"));
        assertFalse(baseballEliminationTeams7.isEliminated("France"));
        assertFalse(baseballEliminationTeams7.isEliminated("Germany"));
        assertTrue(baseballEliminationTeams7.isEliminated("Ireland"));
        assertFalse(baseballEliminationTeams7.isEliminated("Belgium"));
        assertTrue(baseballEliminationTeams7.isEliminated("China"));
    }

    /**
     * Test method for {@link BaseballElimination#certificateOfElimination(java.lang.String)}.
     */
    @Test
    public void testCertificateOfEliminationTriviallyEliminatedTeams4() {
        String[] expectedEliminationCertificate = { "Atlanta" };
        int index = 0;
        for (String actualEliminationTeam : baseballEliminationTeams4
                .certificateOfElimination("Montreal")) {
            assertEquals(expectedEliminationCertificate[index], actualEliminationTeam);
        }
    }

    @Test
    public void testCertificateOfEliminationTriviallyEliminatedTeams5b() {
        String[] expectedEliminationCertificate = { "New_York" };
        int index = 0;
        for (String actualEliminationTeam : baseballEliminationTeams5b
                .certificateOfElimination("Detroit")) {
            assertEquals(expectedEliminationCertificate[index], actualEliminationTeam);
        }
    }

    @Test
    public void testCertificateOfEliminationTriviallyEliminatedTeams7() {
        String[] expectedEliminationCertificate = { "France" };
        int index = 0;
        for (String actualEliminationTeam : baseballEliminationTeams7
                .certificateOfElimination("China")) {
            assertEquals(expectedEliminationCertificate[index], actualEliminationTeam);
        }
    }

    /**
     * Test method for {@link BaseballElimination#certificateOfElimination(java.lang.String)}.
     */
    @Test
    public void testCertificateOfEliminationNonTriviallyEliminatedTeams4() {
        String[] expectedEliminationCertificate = { "Atlanta", "New_York" };
        ArrayList<String> teams4ExpectedList = new ArrayList<String>();
        for (String team : expectedEliminationCertificate) {
            teams4ExpectedList.add(team);
        }
        for (String actualEliminationTeam : baseballEliminationTeams4
                .certificateOfElimination("Philadelphia")) {
            assertTrue(actualEliminationTeam + " is not in the expected list",
                    teams4ExpectedList.contains(actualEliminationTeam));

        }

    }

    @Test
    public void testCertificateOfEliminationNonTriviallyEliminatedTeams5() {
        String[] expectedEliminationCertificate = { "New_York", "Baltimore", "Boston", "Toronto" };
        ArrayList<String> teams5ExpectedList = new ArrayList<String>();
        for (String team : expectedEliminationCertificate) {
            teams5ExpectedList.add(team);
        }
        for (String actualEliminationTeam : baseballEliminationTeams5
                .certificateOfElimination("Detroit")) {
            assertTrue(actualEliminationTeam + " is not in the expected list",
                    teams5ExpectedList.contains(actualEliminationTeam));

        }

    }

    @Test
    public void testCertificateOfEliminationNonTriviallyEliminatedTeams7() {
        String[] expectedEliminationCertificate = { "U.S.A.", "France", "Germany" };
        ArrayList<String> teams7ExpectedList = new ArrayList<String>();
        for (String team : expectedEliminationCertificate) {
            teams7ExpectedList.add(team);
        }
        for (String actualEliminationTeam : baseballEliminationTeams7
                .certificateOfElimination("Ireland")) {
            assertTrue(actualEliminationTeam + " is not in the expected list",
                    teams7ExpectedList.contains(actualEliminationTeam));

        }

    }
}

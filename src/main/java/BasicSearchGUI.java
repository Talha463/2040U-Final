import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Player {
    String name;
    int number;
    String team;
    int points;
    int rebounds;
    int assists;

    public Player(String name, int number, String team, int points, int rebounds, int assists) {
        this.name = name;
        this.number = number;
        this.team = team;
        this.points = points;
        this.rebounds = rebounds;
        this.assists = assists;
    }

    // Displays stats
    public String toString() {
        return name + " (#" + number + ", " + team + ") - Pts: " + points + ", Reb: " + rebounds + ", Ast: " + assists;
    }
}

public class BasicSearchGUI extends JFrame {
    private JTextField searchField;
    private JTextArea resultArea;
    private List<Player> players;

    // Frame of program
    public BasicSearchGUI() {
        setTitle("NBA Stats Search");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for search bar
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout(5, 5));

        searchField = new JTextField();
        JButton searchButton = new JButton("Search");

        inputPanel.add(new JLabel("Search Player, Number, Team, or Stats:"), BorderLayout.WEST);
        inputPanel.add(searchField, BorderLayout.CENTER);
        inputPanel.add(searchButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.NORTH);

        // Text area to display results
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // Initialize player data
        players = new ArrayList<>();
        addSampleData();

        // Button event listener
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPlayers();
            }
        });
    }

    // Sample player data can modify to add from another program
    private void addSampleData() {
        players.add(new Player("LeBron James", 23, "Lakers", 28, 8, 7));
        players.add(new Player("Stephen Curry", 30, "Warriors", 30, 5, 6));
        players.add(new Player("Giannis Antetokounmpo", 34, "Bucks", 29, 11, 5));
        players.add(new Player("Nikola Jokic", 15, "Nuggets", 25, 12, 9));
        players.add(new Player("Kevin Durant", 35, "Suns", 27, 7, 5));
        players.add(new Player("Luka Doncic", 77, "Mavericks", 32, 9, 8));
    }

    // Search method
    private void searchPlayers() {
        String query = searchField.getText().trim().toLowerCase();
        if (query.isEmpty()) {
            resultArea.setText("Please enter a search term.");
            return;
        }

        StringBuilder result = new StringBuilder("Results:\n");
        boolean found = false;

        for (Player p : players) {
            if (matchesQuery(p, query)) {
                result.append(p.toString()).append("\n");
                found = true;
            }
        }

        if (!found) {
            result.append("No players found.");
        }

        resultArea.setText(result.toString());
    }

    // Check if player matches search query
    private boolean matchesQuery(Player p, String query) {
        // Check name, team, and number
        if (p.name.toLowerCase().contains(query) ||
                p.team.toLowerCase().contains(query) ||
                String.valueOf(p.number).equals(query)) {
            return true;
        }

        // Check if query is a number and matches stats
        if (query.matches("\\d+")) {
            int num = Integer.parseInt(query);
            return p.points == num || p.rebounds == num || p.assists == num;
        }

        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BasicSearchGUI gui = new BasicSearchGUI();
            gui.setVisible(true);
        });
    }
}

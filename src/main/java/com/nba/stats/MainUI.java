package com.nba.stats;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * Represents the user interface for searching and displaying NBA player statistics.
 */
public class MainUI extends JFrame {
    private JTextField searchField;  // Input field for searching players
    private JButton searchButton;    // Button to trigger search
    private JTextArea resultArea;    // Display area for player stats

    /**
     * Initializes the UI components and sets up event handling.
     */
    public MainUI() {
        setTitle("NBA Stats Lookup"); // Window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel containing search input and button
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Enter Player Name:"));

        searchField = new JTextField(15);  // Text field for player name
        topPanel.add(searchField);

        searchButton = new JButton("Search");
        topPanel.add(searchButton);

        add(topPanel, BorderLayout.NORTH);

        // Display area for player stats
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.BOLD, 14)); // Improve readability
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // Auto-focus on the search field when the UI starts
        SwingUtilities.invokeLater(() -> searchField.requestFocus());

        // Search button event listener
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = searchField.getText().trim(); // Get input

                if (playerName.isEmpty()) {
                    resultArea.setText("Please enter a player's name.");
                    return;
                }

                // Fetch player stats
                Map<String, Object> stats = StatsFetcher.fetchStats(playerName);

                if (stats == null) {
                    resultArea.setText(" No stats found for \"" + playerName + "\".");
                } else {
                    resultArea.setText(String.format(
                            "🏀 Player: %s\n📊 Points: %d\n🔄 Rebounds: %d\n🎯 Assists: %d",
                            stats.get("player"), stats.get("points"), stats.get("rebounds"), stats.get("assists")
                    ));
                }
            }
        });

        setSize(450, 350); // Adjust window size for better visibility
        setVisible(true);
    }

//    this launches the NBA stats Ui application
    public static void main(String[] args) {
        new MainUI();
    }
}

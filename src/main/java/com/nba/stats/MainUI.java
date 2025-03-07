package com.nba.stats;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * NBA Stats UI with Tabs for Bookmarked Players, Teams, Login, and Register
 */
public class MainUI extends JFrame {
    private JTextField searchField;
    private JButton searchButton;
    private JTextArea resultArea;
    private JTabbedPane tabbedPane;

    public MainUI() {
        setTitle("NBA Stats Lookup");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Create the tabbed panel
        tabbedPane = new JTabbedPane();

        // Add the tabs
        tabbedPane.addTab("Search Player", createSearchTab());
        tabbedPane.addTab("Bookmarked Players", new JPanel());  // Blank tab
        tabbedPane.addTab("Bookmarked Teams", new JPanel());    // Blank tab
        tabbedPane.addTab("Login", createLoginTab());          // Placeholder Login UI
        tabbedPane.addTab("Register", createRegisterTab());    // Placeholder Register UI

        add(tabbedPane);
        setVisible(true);
    }

    /**
     * Creates the Player Search tab UI
     */
    private JPanel createSearchTab() {
        JPanel searchPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout());

        topPanel.add(new JLabel("Enter Player Name:"));
        searchField = new JTextField(15);
        topPanel.add(searchField);

        searchButton = new JButton("Search");
        topPanel.add(searchButton);

        searchPanel.add(topPanel, BorderLayout.NORTH);

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        searchPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = searchField.getText().trim();
                if (playerName.isEmpty()) {
                    resultArea.setText("Please enter a player's name.");
                    return;
                }

                Map<String, Object> stats = StatsFetcher.fetchStats(playerName);
                if (stats == null) {
                    resultArea.setText("No stats found for \"" + playerName + "\".");
                } else {
                    resultArea.setText(String.format(
                            "Player: %s\nPoints: %d\nRebounds: %d\nAssists: %d",
                            stats.get("player"), stats.get("points"), stats.get("rebounds"), stats.get("assists")
                    ));
                }
            }
        });

        return searchPanel;
    }

    /**
     * Creates a simple Login UI tab
     */
    private JPanel createLoginTab() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2, 5, 5));

        loginPanel.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        loginPanel.add(usernameField);

        loginPanel.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        loginPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginPanel.add(new JLabel());  // Empty cell for alignment
        loginPanel.add(loginButton);

        return loginPanel;
    }

    /**
     * Creates a simple Register UI tab
     */
    private JPanel createRegisterTab() {
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridLayout(4, 2, 5, 5));

        registerPanel.add(new JLabel("Full Name:"));
        JTextField nameField = new JTextField();
        registerPanel.add(nameField);

        registerPanel.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        registerPanel.add(usernameField);

        registerPanel.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        registerPanel.add(passwordField);

        JButton registerButton = new JButton("Register");
        registerPanel.add(new JLabel());  // Empty cell for alignment
        registerPanel.add(registerButton);

        return registerPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainUI());
    }
}

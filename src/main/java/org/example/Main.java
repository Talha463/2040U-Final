package org.example;
import java.util.*;

class Player {
    String name;
    String team;
    double points;
    double assists;
    double rebounds;

    public Player(String name, String team, double points, double assists, double rebounds) {
        this.name = name;
        this.team = team;
        this.points = points;
        this.assists = assists;
        this.rebounds = rebounds;
    }

    @Override
    public String toString() {
        return name + " (" + team + ") - PTS: " + points + ", AST: " + assists + ", REB: " + rebounds;
    }
}

class NBAStats {
    private List<Player> players;

    public NBAStats() {
        players = new ArrayList<>();
    }

    public void addPlayer(String name, String team, double points, double assists, double rebounds) {
        players.add(new Player(name, team, points, assists, rebounds));
    }

    public List<Player> searchByName(String name) {
        List<Player> result = new ArrayList<>();
        for (Player p : players) {
            if (p.name.toLowerCase().contains(name.toLowerCase())) {
                result.add(p);
            }
        }
        return result;
    }

    public List<Player> searchByTeam(String team) {
        List<Player> result = new ArrayList<>();
        for (Player p : players) {
            if (p.team.equalsIgnoreCase(team)) {
                result.add(p);
            }
        }
        return result;
    }

    public void displayTopPlayers(int count) {
        for (int i = 0; i < Math.min(count, players.size()); i++) {
            System.out.println(players.get(i));
        }
    }
}

public class Main {
    public static void main(String[] args) {
        NBAStats stats = new NBAStats();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("NBA Stats - Search and Sort");
            System.out.println("1. Add Player");
            System.out.println("2. Search Player by Name");
            System.out.println("3. Search Players by Team");
            System.out.println("4. Show Top Players");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter player name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter team name: ");
                    String team = scanner.nextLine();
                    System.out.print("Enter points per game: ");
                    double points = scanner.nextDouble();
                    System.out.print("Enter assists per game: ");
                    double assists = scanner.nextDouble();
                    System.out.print("Enter rebounds per game: ");
                    double rebounds = scanner.nextDouble();
                    scanner.nextLine();
                    stats.addPlayer(name, team, points, assists, rebounds);
                    break;
                case 2:
                    System.out.print("Enter player name: ");
                    name = scanner.nextLine();
                    List<Player> playersByName = stats.searchByName(name);
                    playersByName.forEach(System.out::println);
                    break;
                case 3:
                    System.out.print("Enter team name: ");
                    team = scanner.nextLine();
                    List<Player> playersByTeam = stats.searchByTeam(team);
                    playersByTeam.forEach(System.out::println);
                    break;
                case 4:
                    System.out.print("Enter number of top players to show: ");
                    int count = scanner.nextInt();
                    stats.displayTopPlayers(count);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }
}

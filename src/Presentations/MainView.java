package Presentations;

import Models.Player;
import utils.Dailog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainView extends JFrame {

	private static final long serialVersionUID = 1;
    private JComboBox<String> gameComboBox;
    private JTextField playerCountField;
    private List<Player> players;
    private JButton startButton;
    private Dailog dailog;
    
    public MainView() {
    	dailog = new Dailog(this);
        setTitle("Main");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        JPanel gamePanel = new JPanel();
        gamePanel.add(new JLabel("Select Game:"));
        gameComboBox = new JComboBox<>(new String[]{"Pok deng", "King"});
        gamePanel.add(gameComboBox);
        add(gamePanel);

        JPanel playerCountPanel = new JPanel();
        playerCountPanel.add(new JLabel("Number of Players:"));
        playerCountField = new JTextField(2);
        playerCountPanel.add(playerCountField);
        add(playerCountPanel);

        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGameSetup();
            }
        });
        add(startButton);

        players = new ArrayList<>();

        setVisible(true);
    }

    private void startGameSetup() {
        String selectedGame = (String) gameComboBox.getSelectedItem();
        int playerCount;
        try {
            playerCount = Integer.parseInt(playerCountField.getText());
            if (playerCount <= 1) {
                JOptionPane.showMessageDialog(this, "Please enter a number of players", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (playerCount > 4) {
                JOptionPane.showMessageDialog(this, "Please enter a number of players less than or equal to 4.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number of players.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0; i < playerCount; i++) {
            String playerName = JOptionPane.showInputDialog(this, "Enter name for Player " + (i + 1) + ":");
            if (playerName == null) {
                int response = dailog.conFirmExitGame();
                if (response == JOptionPane.YES_OPTION || response == JOptionPane.CLOSED_OPTION) {
                    System.exit(0);
                } else {
                    i--;
                    continue;
                }
            } else if (!playerName.trim().isEmpty()) {
                players.add(new Player(playerName));
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid name.", "Error", JOptionPane.ERROR_MESSAGE);
                i--;
            }
        }

        JOptionPane.showMessageDialog(this, "Game setup complete. Starting " + selectedGame + " with " + playerCount + " players.");
        new GameView(selectedGame, players,null);
    }
}

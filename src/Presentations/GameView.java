package Presentations;

import Models.*;
import utils.Dailog;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameView extends JFrame {
    private static final long serialVersionUID = 1L;
    private String selectedGame;
    private List<Player> players;
    private Deck deck;
    private DeckView deckView;
    private List<PlayerView> playerViews;
    private int currentPlayer = 0;
    private boolean canDrawCards = true;
    private Dailog dailog;
    
    // Game Initial
    public GameView(String selectedGame, List<Player> players, Deck deck) {
    	this.dailog = new Dailog(this);
        this.selectedGame = selectedGame;
        this.players = players;
        this.deck = deck == null ? new Deck(null) : deck;
        this.playerViews = new ArrayList<PlayerView>();
        setLayout();
        setInfo();
        setDeckView();
        setPlayerView();
        showSetupDialog();
        setCardValuesBaseOnGame();
        gameStart();
        setVisible(true);
    }
    
    private void setCardValuesBaseOnGame() {
        if (selectedGame.equalsIgnoreCase("Pok deng")) {
            Rank.Jack.setValue(10);
            Rank.Queen.setValue(10);
            Rank.King.setValue(10);
        } else if (selectedGame.equalsIgnoreCase("King")) {
        	Rank.Jack.setValue(11);
            Rank.Queen.setValue(12);
            Rank.King.setValue(13);
        }
    }
    
    private void gameStart() {
    	canDrawCards = false;
    	
        for (Player player : players) {
        	player.clear();
            if (deck.getCards().size() < 2) {
                JOptionPane.showMessageDialog(this, "Not enough cards in the deck to start the game!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (int i = 0; i < 2; i++) {
                Card drawnCard = deck.drawCard();
                player.addCard(drawnCard);
            }
        }
        updateState();
    }

    
    private void showSetupDialog() {
    	this.deck.shuffle();
        String[] options = {"1", "2", "3"};
        String selectedOption = (String) JOptionPane.showInputDialog(this, "Select number of piles:", 
            "SetCard", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (selectedOption == null) {
            int response = dailog.conFirmExitGame();
            if (response == JOptionPane.YES_OPTION || response == JOptionPane.CLOSED_OPTION) {
                System.exit(0);
            } else {
                showSetupDialog();
            }
            return;
        }
        int numberOfPiles = Integer.parseInt(selectedOption);
        if(numberOfPiles == 1) {
        	this.deck.toString();
        }else {
        	deviceDeck(numberOfPiles);
        }
        
    }
    
    private void deviceDeck(int numberOfPiles) {
    	List<Deck> piles = new ArrayList<Deck>();
    	piles.addAll(this.deck.splitDeckIntoPiles(numberOfPiles));
        List<Integer> order = new ArrayList<>();
        for (int i = 1; i <= numberOfPiles; i++) {
            order.add(i);
        }

        List<Integer> selectedOrder = new ArrayList<>();
        while (!order.isEmpty()) {
            String[] orderOptions = order.stream().map(String::valueOf).toArray(String[]::new);
            String selectedOrderOption = (String) JOptionPane.showInputDialog(this, "Select pile to combine next:", 
                "Setup", JOptionPane.QUESTION_MESSAGE, null, orderOptions, orderOptions[0]);
            if (selectedOrderOption == null) {
                int response = dailog.conFirmExitGame();
                if (response == JOptionPane.YES_OPTION || response == JOptionPane.CLOSED_OPTION) {
                    System.exit(0);
                }else {
                	continue;
                }
            }
            int selectedPile = Integer.parseInt(selectedOrderOption);
            selectedOrder.add(selectedPile);
            
            order.remove((Integer) selectedPile);
        }

        combinePiles(piles, selectedOrder);
    }


    private void combinePiles(List<Deck> piles, List<Integer> order) {
      for (int index : order) {
    	  this.deck.addCard(piles.get(index - 1).getCards());
      }
      System.out.println("After combinePiles : " + this.deck.showCards());
    }

    private void setLayout() {
        setTitle("Game View");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void setDeckView() {
        deckView = new DeckView(deck, e -> drawCardForCurrentPlayer(),canDrawCards);
        add(deckView, BorderLayout.LINE_START);
    }

    private void setPlayerView() {
    	JPanel playersPanel = new JPanel();
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
        for (Player player : players) {
            PlayerView playerView = new PlayerView(player);
            playerViews.add(playerView);

            JPanel playerPanel = new JPanel();
            playerPanel.setLayout(new BorderLayout());
            playerPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.BLACK), player.getName()));
            playerPanel.add(playerView, BorderLayout.CENTER);
            playersPanel.add(playerPanel);
        }
        add(playersPanel, BorderLayout.CENTER);
    }

    private void setInfo() {
        JPanel gameInfoPanel = new JPanel();
        gameInfoPanel.add(new JLabel("Selected Game: " + selectedGame));
        gameInfoPanel.add(new JLabel("CurrentPlayer: " + players.get(currentPlayer).getName()));
        add(gameInfoPanel, BorderLayout.NORTH);
    }

    private void drawCardForCurrentPlayer() {
        if (!deck.getCards().isEmpty()) {
            Card drawnCard = deck.drawCard();
            players.get(currentPlayer).addCard(drawnCard);
            currentPlayer = (currentPlayer + 1) % players.size();
        } else {
            JOptionPane.showMessageDialog(this, "Deck is empty!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        updateState();
    }

    private void updateState() {
        deckView.updateDeck();
        for (PlayerView playerView : playerViews) {
            playerView.updateCards();
        }
        ((JLabel) ((JPanel) getContentPane().getComponent(0)).getComponent(1)).setText("Current Player: " + players.get(currentPlayer).getName());
        checkDrawCardButtonState();
    
        if(!canDrawCards) {
        	 Timer timer = new Timer(2000, e -> checkPoint());
             timer.setRepeats(false);
             timer.start();
        }
    }
    
    private void checkDrawCardButtonState() {
        deckView.setDrawCardButtonEnabled(canDrawCards);
    }
    
    private void checkPoint() {
    	Player winner = null;
        int highestScore = 0;

        for (Player player : players) {
            int playerScore = player.getScore();
            if (playerScore > highestScore) {
                highestScore = playerScore;
                winner = player;
            }
        }

        if (winner != null) {
            int response = JOptionPane.showOptionDialog(this, "The winner is " + winner.getName() + " with a score of " + highestScore + "!\nDo you want to start a new game?", "Game Over",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (response == JOptionPane.YES_OPTION) {
                resetGame();
            } else {
                System.exit(0);
            }
        }
    }
    
    private void resetGame() {
        this.deck = new Deck(null);
        this.playerViews.clear();
        this.players.forEach(Player::clear);
        dispose();
        new GameView(this.selectedGame, this.players, this.deck);
    }
}

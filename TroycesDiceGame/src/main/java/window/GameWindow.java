package main.java.window;
import java.awt.*;
import javax.swing.*;

public class GameWindow extends JFrame{

    // Access the GameInstance singleton
    private static GameWindow instance;
    private GameInstance game;
    
    private GameWindow(){
        game = GameInstance.getInstance();

        this.setTitle("Troyes Dice");
        this.setSize(1500,1000);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());


        JPanel menu = new JPanel();
        menu.setLayout(null);
        menu.setBackground(new Color(255, 243, 230));

        JLabel statusLabel = new JLabel("Game Status: Not Started", SwingConstants.CENTER);
        statusLabel.setBounds(675, 410, 150, 60);

        JButton startButton = new JButton("Start Game");
        startButton.setBounds(675, 350, 150, 60);   

        startButton.addActionListener( (e) -> {
                clearPanel(menu);
                this.numberPlayers(menu);
            });

        menu.add(startButton);
        menu.add(statusLabel);

        this.add(menu, BorderLayout.CENTER);

        this.setVisible(true);
    }

    public static GameWindow getInstance() {
        if (instance == null) {
            instance = new GameWindow();
        }
        return instance;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameWindow.getInstance();
            }
        });
    }

    private void clearPanel(JPanel panel){
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }


    private void numberPlayers(JPanel panel){
        JLabel playersLabel = new JLabel("Number of Players:");
        playersLabel.setBounds(555, 350, 110, 60);

        JTextField playersText = new JTextField("2");
        playersText.setBounds(675, 365, 150, 30);

        JButton confirmed = new JButton("Confirmed");
        confirmed.setBounds(675, 410, 150, 60);   

        confirmed.addActionListener( (e) -> {
                clearPanel(panel);
                Integer nbPlayers = Integer.parseInt(playersText.getText());
                if (nbPlayers >= 2){
                    this.clearPanel(panel);
                    this.addPlayers(panel, nbPlayers);
                }
            });

        panel.add(playersLabel);
        panel.add(playersText);
        panel.add(confirmed);
    }

    private void addPlayers(JPanel panel, Integer id){
        if (id == 0){
            this.clearPanel(panel);
            //TO DO next state
        }
        else{
            JLabel player = new JLabel("Player " + id);
            player.setBounds(675, 290, 80, 60);

            JLabel nameLabel = new JLabel("Name:");
            nameLabel.setBounds(625, 350, 50, 60);

            JTextField nameText = new JTextField("Player " + id);
            nameText.setBounds(675, 365, 150, 30);

            JButton confirmed = new JButton("Confirmed");
            confirmed.setBounds(675, 410, 150, 60); 

            confirmed.addActionListener( (e) -> {
                clearPanel(panel);
                String namePlayer = nameText.getText();
                //TO DO add namePlayer to the list of players
                this.clearPanel(panel);
                this.addPlayers(panel, id-1);

            });

            panel.add(player);
            panel.add(nameLabel);
            panel.add(nameText);
            panel.add(confirmed);
        }  
    }
}

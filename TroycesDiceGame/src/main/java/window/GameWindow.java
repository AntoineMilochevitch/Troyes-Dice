package main.java.window;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GameWindow extends JFrame{

    // Access the GameInstance singleton
    private static GameWindow instance;
    private GameInstance game;
    
    private GameWindow(){
        game = GameInstance.getInstance();

        setTitle("Game");
        setSize(300,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2,1));

        JLabel statusLabel = new JLabel("Game Status: Not Started", SwingConstants.CENTER);

        JButton startButton = new JButton("Start Game");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Game Status : Started");
                game.startGame();
            }
        });

        add(startButton);
        add(statusLabel);
        setVisible(true);
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
}
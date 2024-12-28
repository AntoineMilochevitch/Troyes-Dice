package main.java.window;
import java.awt.*;
import javax.swing.*;

public class CircularPanel extends JPanel{

    private Color color;
    private ImageIcon arrowImage;

    private CircularPanel(Color colorPanel, String arrowColor){
        this.color = colorPanel;
        this.setOpaque(false);
        this.setLayout(null);

        

        // Chemin de l'image
        String imagePath = arrowColor + "arrow.png";
        this.arrowImage = new ImageIcon(getClass().getResource("/" + imagePath));

        // Vérifier si l'image est bien chargée
        if (this.arrowImage.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.err.println("Erreur : Impossible de charger l'image : " + imagePath);
            return;
        }

        int arrowSize = 50; // Taille de la flèche
        Image scaledArrow = arrowImage.getImage().getScaledInstance(arrowSize, arrowSize, Image.SCALE_SMOOTH);

        this.arrowImage = new ImageIcon(scaledArrow);
        JLabel arrowLabel = new JLabel(this.arrowImage);
        arrowLabel.setBounds(120, 185, 50, 50);
        this.add(arrowLabel);
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Définir une couleur de fond pour le cercle
        g2d.setColor(this.color);
        g2d.fillOval(0, 0, getWidth(), getHeight());

        // Optionnel : Dessiner un contour
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(1, 1, getWidth() - 3, getHeight() - 3);

        // Dessiner un cercle semi-transparent autour de la flèche
        g2d.setColor(new Color(255, 255, 255, 170)); // Blanc semi-transparent
        g2d.fillOval(100, 170, 80, 80);
    }

    @Override
    public Dimension getPreferredSize() {
        // Définir une taille carrée pour garantir un cercle
        return new Dimension(300, 300);
    }

    public static void main(String[] args) {
        // Créer une fenêtre
        JFrame frame = new JFrame("Panel Circulaire");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);

        // Ajouter le panel circulaire
        CircularPanel circularPanel = new CircularPanel(Color.YELLOW, "red");
        frame.add(circularPanel);

        // Rendre la fenêtre visible
        frame.setVisible(true);
    }
}
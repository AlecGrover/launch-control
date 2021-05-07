package display.panels;

import betterAWT.Circle;
import util.constants.window.Dimensions;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class TitlePanel extends JPanel {

    private static final int STARS = 1000;
    private final Circle[] stars = new Circle[STARS];

    private static BufferedImage titleCard = null;

    // Opens the title image, title and title font made by me in Aseprite
    // I'm a programmer, not an artist, but I'm pretty happy with how it
    // turned out! Source files in the submission as required.
    static {
        try {
            titleCard = ImageIO.read(new File("res/Title(200pct).png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Sets the background and spawns the stars
    public TitlePanel() {
        this.setBackground(new Color(0x4c408e));

        Random r = new Random();

        for(int i = 0; i < STARS; i++) {
            stars[i] = new Circle(r.nextInt(Dimensions.WIDTH), r.nextInt(Dimensions.HEIGHT), 1);
        }
    }

    // Draws the stars, title image, tutorial background, and tutorial
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        for (Circle c : stars) { g2.draw(c); }
        if (titleCard == null) return;
        g2.drawImage(titleCard, null, (int) (Dimensions.WIDTH / 2f - titleCard.getWidth() / 2f), (int) (Dimensions.HEIGHT / 2f - titleCard.getHeight() / 2f));

        g2.fillRect((int) (Dimensions.WIDTH / 2f - titleCard.getWidth() / 2f), (int) (Dimensions.HEIGHT / 2f + 0.66f * titleCard.getHeight()), titleCard.getWidth(), titleCard.getHeight() + 8);
        String[] blurbs = {"Welcome to Launch Control!", "", "Controls:", "Spacebar - Thrust", "Left/Right Arrow Keys - Rotate", "Objective:", "Place your rocket into orbit before you run out of fuel!"};
        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        g2.setColor(Color.BLACK);
        for (int i = 0; i < blurbs.length; i++) {
            g2.drawString(blurbs[i], Dimensions.WIDTH / 2f - g2.getFontMetrics().stringWidth(blurbs[i]) / 2f,
                    Dimensions.HEIGHT / 2f + 0.75f * titleCard.getHeight() + 16 * (i + 1));
        }

    }

}

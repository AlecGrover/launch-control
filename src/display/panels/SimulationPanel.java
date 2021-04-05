package display.panels;

import objects.Earth;
import objects.Rocket;
import util.constants.window.Dimensions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import static util.constants.timer.TimerConstants.FRAME_DURATION;

public class SimulationPanel extends JPanel implements ActionListener {

    private final Rocket rocket = new Rocket();

    public SimulationPanel() {
        Timer timer = new Timer(FRAME_DURATION, this);
        timer.start();
        rocket.setMaps(this.getActionMap(), this.getInputMap());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform transform = g2.getTransform();
        g2.translate(Dimensions.WIDTH / 2, Dimensions.HEIGHT / 2);
        Earth.draw(g2);
        rocket.draw(g2);
        g2.setTransform(transform);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}

package core;

import display.panels.SimulationPanel;

import javax.swing.*;

import static util.constants.window.Dimensions.*;

public class Main {

    private static JFrame frame = new JFrame();
    private static SimulationPanel panel = new SimulationPanel();

    public static void main(String[] args) {

        initializeWindow();

    }

    private static void initializeWindow() {

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(DIMENSIONS);

        frame.getContentPane().add(panel);

        frame.setVisible(true);

    }

}

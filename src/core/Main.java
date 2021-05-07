package core;

import display.panels.SimulationPanel;
import display.panels.TitlePanel;
import util.minim.MinimHelper;

import javax.swing.*;

import ddf.minim.AudioPlayer;
import ddf.minim.*;
import ddf.minim.spi.MinimServiceProvider;


import java.awt.*;
import java.awt.event.ActionEvent;

import static util.constants.window.Dimensions.*;

/*
* The Main class is what actually runs the program, as one would expect. Main
* has several key components to be aware of:
*   *   Layout Definition
*   *   Panel Definitions
*   *   Music Playing
*   *   Button ActionMapping
*   *   State Control
* Each of these will be explained with inline comments at their location
* */

public class Main {

    // Fields relating to the JFrame display and its contents
    private static final JFrame frame = new JFrame();
    private static SimulationPanel simulatorPanel = new SimulationPanel();
    private static final TitlePanel titlePanel = new TitlePanel();
    private static final JButton launch = new JButton("Launch");
    private static final JButton quit = new JButton("Quit");
    private static final JButton quit0 = new JButton("Quit");
    private static final JButton menuButton = new JButton("Menu");
    private static final JPanel menuButtons = new JPanel();
    private static final JPanel simulatorButtons = new JPanel();

    // Fields relating to the Minim audio player that plays the music loop
    private final static Minim mi = new Minim(new MinimHelper());
    /*
    * For the music I threw together a quick looping audio segment using an
    * online MIDI sequencer then tried to smooth the loop in Audacity. I
    * have never made music before, so hopefully it isn't too hard to listen
    * to. Source files are in the submission per instructions.
    * */
    private final static AudioPlayer music = mi.loadFile("res/LaunchControlLoopTest1.mp3");

    /*
    * I got docked some marks for not clearly defining FSM logic in the last
    * assignment, so while it's only a suggestion in this assignment I feel
    * like it's my best bet to mention that the two enum values in Screens
    * are the two states that the simulation can be in. The requirements
    * called for an intro screen, the simulation screen, and an ending screen,
    * but the concept of this simulation is a long term model, so I've switched
    * using a final screen for an alert on the simulation screen that goes live
    * when the rocket's fuel runs out (marking the point at which the user can
    * no longer effect the rocket's orbit).
    * */
    private enum Screens {
        MENU,
        SIMULATOR
    }

    private static Screens currentScreen = Screens.MENU;

/* This static binds all the buttons to their actions and establishes the
 * button panels used for each screen. I used a menu button instead of a
 * restart button for the same reason I modified the way the end screen
 * would work, I felt it was a bit more fluid for the unique nature of this
 * simulation, but it would take only trivial code changes to switch it to
 * relaunch. The buttons are not focusable to avoid drawing control away
 * from the rocket keyboard controls later.
 * */
    static {
        AbstractAction cycle = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cycleActiveScreen();
            }
        };
        AbstractAction quitAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
        menuButtons.setLayout(new FlowLayout());
        menuButtons.add(launch);
        menuButtons.add(quit);
        menuButtons.setFocusable(false);
        launch.setAction(cycle);
        quit.setAction(quitAction);
        launch.setText("Launch");
        quit.setText("Quit");
        launch.setFocusable(false);
        quit.setFocusable(false);

        simulatorButtons.setLayout(new FlowLayout());
        simulatorButtons.add(menuButton);
        simulatorButtons.add(quit0);
        simulatorButtons.setFocusable(false);
        menuButton.setAction(cycle);
        menuButton.setText("Menu");
        quit0.setAction(quitAction);
        quit0.setText("Quit");

    }

    // Main starts the music loop and triggers window initialization
    public static void main(String[] args) {

        music.loop();
        initializeWindow();

    }

    // initializeWindow performs some basic frame setup and layout
    // management
    private static void initializeWindow() {

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(DIMENSIONS);
        frame.setTitle("Launch Control");
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(titlePanel, BorderLayout.CENTER);
        frame.getContentPane().add(menuButtons, BorderLayout.SOUTH);
        frame.setVisible(true);

    }

    /*
    * cycleActiveScreen handles the transitions between states, it removes
    * the previous panels and replaces them with new panels of the appropriate
    * type. It then revalidates the frame and repaints to remove the previous
    * content and display the new panel.
    * */
    private static void cycleActiveScreen() {
        if (currentScreen == Screens.MENU) {
            currentScreen = Screens.SIMULATOR;
            Container cp = frame.getContentPane();
            cp.remove(titlePanel);
            simulatorPanel = new SimulationPanel();
            cp.add(simulatorPanel, BorderLayout.CENTER);
            simulatorPanel.grabFocus();
            cp.remove(menuButtons);
            cp.add(simulatorButtons, BorderLayout.SOUTH);
        }
        else {
            currentScreen = Screens.MENU;
            Container cp = frame.getContentPane();
            cp.remove(simulatorPanel);
            cp.add(titlePanel, BorderLayout.CENTER);
            cp.remove(simulatorButtons);
            cp.add(menuButtons, BorderLayout.SOUTH);
        }
        frame.invalidate();
        frame.repaint();
        frame.validate();
    }

}

package view;

import model.*;
import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Graphical user interface (GUI) view for Battleships.
 */
public class View extends JFrame implements Observer {
    private Model model;
    private Controller controller;
    private JButton[][] gridButtons;
    private JLabel attemptsLabel;

    /**
     * Constructor for View.
     * @param model the game model
     * @param controller the game controller (can be set later)
     */
    public View(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;

        model.addObserver(this);

        initializeGUI();
    }

    /**
     * Initializes the GUI components.
     */
    private void initializeGUI() {
        setTitle("Battleships Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(model.getBoard().getSize(), model.getBoard().getSize()));
        gridButtons = new JButton[model.getBoard().getSize()][model.getBoard().getSize()];

        for (int row = 0; row < model.getBoard().getSize(); row++) {
            for (int col = 0; col < model.getBoard().getSize(); col++) {
                JButton button = new JButton();
                final int r = row;
                final int c = col;
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (controller != null) {
                            controller.processGuess(r, c);
                        }
                    }
                });
                gridButtons[row][col] = button;
                boardPanel.add(button);
            }
        }

        attemptsLabel = new JLabel("Attempts: 0");
        add(attemptsLabel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);

        setSize(600, 600);
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    /**
     * Update method called when the Model changes.
     */
    @Override
    public void update(Observable o, Object arg) {
        refresh();
    }

    /**
     * Refreshes the board display according to the current model state.
     */
    public void refresh() {
        Board board = model.getBoard();

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                CellStatus status = board.getCellStatus(row, col);
                JButton button = gridButtons[row][col];

                switch (status) {
                    case HIT:
                        button.setBackground(Color.RED);
                        button.setEnabled(false);
                        break;
                    case MISS:
                        button.setBackground(Color.BLUE);
                        button.setEnabled(false);
                        break;
                    default:
                        // Do nothing if still empty
                        break;
                }
            }
        }
        attemptsLabel.setText("Attempts: " + model.getTries());
    }

    /**
     * Displays a popup message to the user.
     */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * Disables all buttons on the board.
     */
    public void disableBoard() {
        for (int row = 0; row < gridButtons.length; row++) {
            for (int col = 0; col < gridButtons[row].length; col++) {
                gridButtons[row][col].setEnabled(false);
            }
        }
    }

    /**
     * Allows setting the controller after View initialization.
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }
}

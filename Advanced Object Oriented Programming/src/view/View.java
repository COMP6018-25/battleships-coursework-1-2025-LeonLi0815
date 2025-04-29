package view;

import model.*;
import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

public class View extends JFrame implements Observer {
    private Model model;
    private Controller controller;
    private JButton[][] gridButtons;
    private JLabel attemptsLabel;

    public View(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;

        model.addObserver(this);

        initializeGUI();
    }

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
                        controller.processGuess(r, c);
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

    @Override
    public void update(Observable o, Object arg) {
        refresh();
    }

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
                        // Keep empty buttons white and enabled
                        break;
                }
            }
        }
        attemptsLabel.setText("Attempts: " + model.getTries());
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void disableBoard() {
        for (int row = 0; row < gridButtons.length; row++) {
            for (int col = 0; col < gridButtons[row].length; col++) {
                gridButtons[row][col].setEnabled(false);
            }
        }
    }
    // 在 View.java 补充这个方法（在构造器下面）
    public void setController(Controller controller) {
        this.controller = controller;
    }

}

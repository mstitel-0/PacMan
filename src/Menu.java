import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import java.util.ArrayList;


public class Menu extends JFrame {

    private Menu gameMenu;
    public Menu(){
        gameMenu = this;
        JButton playBtn = new JButton();
        JButton exitBtn = new JButton();
        JButton scoreBtn = new JButton();

        ImageIcon playIcon = new ImageIcon("src/Images/NEW GAME_2 (2).png");
        ImageIcon highScores = new ImageIcon("src/Images/HIGH SCORES_2 (2).png");
        ImageIcon exit = new ImageIcon("src/Images/EXIT_2 (2).png");
        playBtn.setIcon(playIcon);
        exitBtn.setIcon(exit);
        scoreBtn.setIcon(highScores);

        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        scoreBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               JFrame scoreFrame = new JFrame("High scores");
               scoreFrame.setLayout(new BorderLayout());
                ArrayList<String>records = new ArrayList<>();
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("HighScores.txt"));
                    String line;
                    while ( (line = reader.readLine() )!= null){
                            records.add(line);
                    }
                } catch (FileNotFoundException ex) {

                } catch (IOException ex) {

                }

                JList scoreList = new JList();
                scoreList.setModel(new HighScoreModel(records));

               JButton scoreExitBtn = new JButton("Back");
               scoreExitBtn.setPreferredSize(new Dimension(100,50));
               scoreExitBtn.addActionListener(new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       scoreFrame.dispose();
                       setVisible(true);
                   }
               });

               JScrollPane scrollPane = new JScrollPane(scoreList);

               JPanel scorePanel = new JPanel();
               scorePanel.setLayout(new BorderLayout());
               scorePanel.add(scrollPane);
               scorePanel.add(scoreExitBtn,BorderLayout.SOUTH);

                KeyStroke endShortCut = KeyStroke.getKeyStroke(KeyEvent.VK_Q,KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK);
                InputMap shortCutInput = scorePanel.getInputMap();
                shortCutInput.put(endShortCut,"shortcut");
                ActionMap performShortCut = scorePanel.getActionMap();
                performShortCut.put("shortcut", new AbstractAction() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                scoreFrame.dispose();
                                setVisible(true);
                            }
                        }
                );

               scoreFrame.add(scorePanel);


               scoreFrame.setSize(500,500);
               scoreFrame.setLocationRelativeTo(null);
               scoreFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               pack();
               scoreFrame.setVisible(true);
               setVisible(false);
            }
        });

        playBtn.addActionListener(new ActionListener() {
            int sizeFirst,sizeSecond;
            boolean firstClick = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame choiceFrame = new JFrame();
                choiceFrame.setLayout(new BorderLayout());

                JSlider choiceSlider = new JSlider(JSlider.HORIZONTAL,10,100,30);
                JButton choiceBtn = new JButton("Confirm");
                JTextField choiceText = new JTextField();

                choiceText.setText("Size of the map is: ");

                choiceText.setHorizontalAlignment(JTextField.CENTER);
                choiceText.setEditable(false);

                choiceSlider.setMajorTickSpacing(20);
                choiceSlider.setMinorTickSpacing(10);
                choiceSlider.setPaintTicks(false);
                choiceSlider.setPaintLabels(true);

                choiceFrame.add(choiceBtn,BorderLayout.PAGE_END);
                choiceFrame.add(choiceSlider,BorderLayout.CENTER);
                choiceFrame.add(choiceText,BorderLayout.PAGE_START);
                choiceFrame.setSize(200,200);
                choiceFrame.setLocationRelativeTo(null);
                pack();
                choiceFrame.setVisible(true);

                choiceSlider.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        if (firstClick) {
                            int choice = choiceSlider.getValue();
                            choiceText.setText("Size of the map is: " + Integer.toString(choice));
                        }
                        else {
                            int choice = choiceSlider.getValue();
                            choiceText.setText("Size of the map is: " + sizeFirst+"x" + Integer.toString(choice));
                        }
                    }
                });

                choiceBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (firstClick ) {
                            sizeFirst = choiceSlider.getValue();
                            choiceText.setText("Size of the map is: " + sizeFirst + "x" + choiceSlider.getValue());
                            choiceBtn.setText("Start");
                            firstClick = false;
                        } else {
                            sizeSecond = choiceSlider.getValue();
                                MyTableModel gameTableModel = new MyTableModel(sizeFirst, sizeSecond);
                                GameWindow gameFrame = new GameWindow(gameTableModel,gameMenu);

                                choiceFrame.setVisible(false);
                                setVisible(false);
                        }
                    }
                });
                choiceFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        sizeFirst = 0;
                        sizeSecond = 0;
                        firstClick = true;
                    }
                });
                choiceFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowDeactivated(WindowEvent e) {
                        sizeFirst = 0;
                        sizeSecond = 0;
                        firstClick = true;
                    }
                });
            }
        });

        JPanel menuPanel = new JPanel();

        menuPanel.setLayout(new GridLayout(3,1));

        menuPanel.add(playBtn);
        menuPanel.add(scoreBtn);
        menuPanel.add(exitBtn);

        add(menuPanel);

        setPreferredSize(new Dimension(400,400));
        setTitle("PacMan Menu");
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }
}

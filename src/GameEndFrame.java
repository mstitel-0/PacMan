import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GameEndFrame extends JFrame {

    private int highScore;

    private JTextField nickNameField;

    private String nickName;

    private Menu gameMenu;


    public GameEndFrame(int score,Menu menu) {
        this.highScore = score;
        this.gameMenu = menu;
        this.nickNameField = new JTextField();
        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nickName = nickNameField.getText();
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("HighScores.txt",true));
                    String result = nickName + "-" + String.valueOf(highScore);
                    writer.write(result);
                    writer.newLine();
                    writer.close();
                } catch (IOException ex) {

                }
                dispose();
                gameMenu.setVisible(true);
            }
        });

        setTitle("Enter tour nickname");
        add(nickNameField);
        add(submit);
        setLayout(new GridLayout(2,1));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

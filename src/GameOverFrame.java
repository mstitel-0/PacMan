import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverFrame extends JFrame {

    private JButton backButton;

    private JLabel gameOverLabel;

    private Menu gameMenu;

    public GameOverFrame(Menu menu){

        this.backButton = new JButton("Back");
        this.gameOverLabel = new JLabel();
        gameOverLabel.setText("Game over!");
        this.gameMenu = menu;



        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                gameMenu.setVisible(true);
            }
        });

        setTitle("Game over");
        add(gameOverLabel);
        add(backButton);
        setLayout(new GridLayout(2,1));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

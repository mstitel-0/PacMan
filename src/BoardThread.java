import javax.swing.*;

public class BoardThread implements Runnable {

    private boolean isActive = true;

    private MyTableModel mapModel;

    private GameWindow gameFrame;

    private GameTable gameTable;

    private JLabel gameLabel;

    private int dotsCount = 1;

    private int score;

    private boolean openWindow = false;

    private int boostCount = 0;

    private Menu gameMenu;


    public BoardThread(MyTableModel model, GameWindow frame, GameTable table, JLabel label,Menu menu) {
        this.mapModel = model;
        this.gameFrame = frame;
        this.gameTable = table;
        this.gameLabel = label;
        this.gameMenu = menu;
    }

    @Override
    public void run() {
        while (isActive){
                if (dotsCount == 0 && !openWindow) {
                    gameFrame.dispose();
                    GameEndFrame gameEnd = new GameEndFrame(gameTable.isDoublePoints()? score*2 : score,gameMenu);
                    openWindow = true;
                }
                if (gameTable.getLives() == 0 && !openWindow){
                    gameFrame.dispose();
                    GameOverFrame gameOver = new GameOverFrame(gameMenu);
                    openWindow = true;
                }
                countScore();
                gameTable.getCellRender().setAnimation(!gameTable.getCellRender().isAnimation());
                gameTable.repaint();
                countRemainDots();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }

        }
    }
    public void countRemainDots(){
        dotsCount = 0;
        for (int i = 0; i < mapModel.getGameBoard().length; i++) {
            for (int j = 0; j < mapModel.getGameBoard()[i].length; j++) {
                if (mapModel.getGameBoard()[i][j] == 0){
                    dotsCount++;

                }
            }
        }

        if (gameFrame.getBoostText()== null){
            gameLabel.setText("Score :"+ score +" Dots left :" + dotsCount + " Lives left : " + gameTable.getLives());
        }else {
            gameLabel.setText(gameFrame.getBoostText());
            boostCount++;
            if (boostCount == 20){
                boostCount = 0;
                gameFrame.setBoostText(null);
            }
        }


    }
    public void countScore(){
        this.score = gameTable.getDotsEaten() *10;
    }
    public void stop(){
        this.isActive = false;
    }
}

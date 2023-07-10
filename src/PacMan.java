import javax.swing.*;
import java.util.Random;

public class PacMan implements Runnable{

    private int positionRow;

    private int positionColumn;
    private int directionRow = 0;

    private int directionColumn;

    private int[][]map;

    private GameTable gameTable;
    private MyTableModel mapTableModel;

    private GameWindow gameFrame;

    private boolean isActive;

    private int dotsCount;
    private int dotsEaten;

    private int respawnRow;

    private int respawnColumn;




    public PacMan(int[][] gameBoard, GameTable gameTable , MyTableModel tableModel, GameWindow frame, int posRow, int posColumn) {
        this.map = gameBoard;
        this.gameTable = gameTable;
        this.gameFrame = frame;
        this.mapTableModel = tableModel;
        this.positionRow = posRow;
        this.positionColumn = posColumn;
        this.isActive = true;
        this.dotsCount =1;
        this.dotsEaten = 0;
        this.respawnRow = posRow;
        this.respawnColumn = posColumn;
    }

    public void setDirections(int dirRow,int dirColumn) {
        this.directionRow = dirRow;
        this.directionColumn = dirColumn;
    }

    @Override
    public void run() {
            while (isActive) {
                synchronized (mapTableModel) {
                    boolean isMirrowMove = false;
                    boolean death = false;
                    mapTableModel.setMoveOnBoard(positionRow, positionColumn, 3);
                    //left mirror
                    if (positionColumn == 0 && positionColumn + directionColumn < 0) {
                        isMirrowMove = true;
                        if (mapTableModel.getGameBoard()[positionRow][map[0].length - 1] != 1) {
                            positionColumn = map[0].length - 1;
                        }
                        if (mapTableModel.getGameBoard()[positionRow][positionColumn] == 0) {
                            gameTable.setDotsEaten(gameTable.getDotsEaten()+1);
                        }
                        mapTableModel.setMoveOnBoard(positionRow, positionColumn, 2);
                    }
                    //right mirror
                    if (positionColumn == mapTableModel.getGameBoard()[0].length - 1 && positionColumn + directionColumn > mapTableModel.getGameBoard()[0].length - 1) {
                        isMirrowMove = true;
                        if (mapTableModel.getGameBoard()[positionRow][0] != 1) {
                            positionColumn = 0;
                        }
                        if (mapTableModel.getGameBoard()[positionRow][positionColumn] == 0) {
                            gameTable.setDotsEaten(gameTable.getDotsEaten()+1);
                        }
                        mapTableModel.setMoveOnBoard(positionRow, positionColumn, 2);
                    }
                    //top mirror
                    if (positionRow == 0 && positionRow + directionRow < 0) {
                        isMirrowMove = true;
                        if (mapTableModel.getGameBoard()[mapTableModel.getGameBoard().length - 1][positionColumn] != 1) {
                            positionRow = map.length - 1;
                        }
                        if (mapTableModel.getGameBoard()[positionRow][positionColumn] == 0) {
                            gameTable.setDotsEaten(gameTable.getDotsEaten()+1);
                        }
                        mapTableModel.setMoveOnBoard(positionRow, positionColumn, 2);
                    }
                    //bottom mirror
                    if (positionRow == mapTableModel.getGameBoard().length - 1 && positionRow + directionRow > mapTableModel.getGameBoard().length - 1) {
                        isMirrowMove = true;
                        if (mapTableModel.getGameBoard()[0][positionColumn] != 1) {
                            positionRow = 0;
                        }
                        if (mapTableModel.getGameBoard()[positionRow][positionColumn] == 0) {
                            gameTable.setDotsEaten(gameTable.getDotsEaten()+1);
                        }
                        mapTableModel.setMoveOnBoard(positionRow, positionColumn, 2);
                    }

                    //death
                    if (!isMirrowMove && mapTableModel.getGameBoard()[positionRow + directionRow][positionColumn + directionColumn] > 3 && mapTableModel.getGameBoard()[positionRow + directionRow][positionColumn + directionColumn] < 10) {
                        if (gameTable.isShield()){
                            gameTable.setShield(false);
                        }else {
                            gameTable.setLives(gameTable.getLives() - 1);
                            death = true;
                        }
                    }
                    if (death || gameTable.isKilled()) {
                        mapTableModel.setMoveOnBoard(positionRow, positionColumn, 0);
                        positionRow = respawnRow;
                        positionColumn = respawnColumn;
                        mapTableModel.setMoveOnBoard(positionRow, positionColumn, 2);
                        gameTable.setKilled(false);
                    }
                    //colision
                    else if (!isMirrowMove && !death && mapTableModel.getGameBoard()[positionRow + directionRow][positionColumn + directionColumn] == 1) {
                        mapTableModel.setMoveOnBoard(positionRow, positionColumn, 2);
                    }
                    //move
                    else if (!isMirrowMove && !death) {
                        if (mapTableModel.getGameBoard()[positionRow + directionRow][positionColumn + directionColumn] == 0) {
                            gameTable.setDotsEaten(gameTable.getDotsEaten()+1);
                        }
                        if (mapTableModel.getGameBoard()[positionRow + directionRow][positionColumn + directionColumn] == 10){
                            getBoosted();
                        }
                        mapTableModel.setMoveOnBoard(positionRow + directionRow, positionColumn + directionColumn, 2);
                        positionRow += directionRow;
                        positionColumn += directionColumn;
                    }
                    try {
                        Thread.sleep(80);
                    } catch (InterruptedException e) {

                    }

                    mapTableModel.notify();

                    try {
                        mapTableModel.wait();
                    } catch (InterruptedException e) {

                    }

                }

        }
    }

    public void getBoosted(){
        Random rand = new Random();
        int boost = rand.nextInt(5);
        switch (boost){
            case 0: gameTable.setLives(gameTable.getLives()+1);  gameFrame.setBoostText("+ 1 life!"); break;//+1 live
            case 1: gameTable.setDoublePoints(true); gameFrame.setBoostText("x2 points!"); ;break;//x2 score
            case 2: gameFrame.killGhost(); gameFrame.setBoostText("Look at the ghost!"); ;break;//kills ghost / or freeze
            case 3: gameTable.setShield(true); gameFrame.setBoostText("You have a shield!"); ;break;//tanks nexr hit
            case 4: gameFrame.sleepGhosts(); gameFrame.setBoostText("Sleeping time"); ;break;//freezes map
            case 5: gameTable.setSpeeded(true); gameFrame.setBoostText("Speed up!"); ;break;
            default:break;
        }
    }
}

import java.awt.*;
import java.util.Random;

public class Ghost implements Runnable{
    private int[][]map ;

    private boolean isActiveGhost;

    private int directionRow;

    private int directionColumn;

    private MyTableModel mapModel;

    private GameTable gameTable;

    private int posRow;

    private int posColumn;

    private int prevCellValue;

    private GameWindow gameFrame;

    private static int counter =4;

    private int value;

    private int time;

    public void setTime(int time) {
        this.time = time;
    }

    private int timePassed;

    private boolean unSleep=false;

    public void setUnSleep(boolean unSleep) {
        this.unSleep = unSleep;
    }

    private int counterSpeed = 10;

    public Ghost(int[][] map, MyTableModel mapTableModel, int startRow, int startposCol , GameTable table , GameWindow frame) {

        this.map = map;
        this.mapModel = mapTableModel;
        this.posRow = startRow;
        this.posColumn = startposCol;
        this.gameTable = table;
        this.gameFrame = frame;
        this.value = counter;
        counter++;
        Random rand = new Random();
        int position = rand.nextInt(5);
        switch (position){
            case 0: placeGhostCenter(); break;
            case 1: placeGhostLeft();break;
            case 2: placeGhostRight();break;
            case 3: placeGhostTopLeft();break;
            case 4: placeGhostTopRight();break;
            default:break;
        }
        if (gameTable.getAmountOfGhosts()>2){
            time = 15;
        }else this.time = 50;
        isActiveGhost = true;
        if (counter == 9){
            counter = 4;
        }
    }

    @Override
    public void run() {


        while (isActiveGhost) {
                synchronized (mapModel) {
                    if (timePassed == 1500){
                        timePassed = 0;
                        mapModel.setMoveOnBoard(posRow, posColumn, dropBoost());
                    }
                    else mapModel.setMoveOnBoard(posRow, posColumn, prevCellValue);

                    if (gameTable.isSpeeded()){
                        time = 5;
                        counterSpeed--;
                        if (counterSpeed == 0 ){
                            gameTable.setSpeeded(false);
                            counterSpeed = 10;
                        }

                    }

                    Random randMove = new Random();
                    int move = randMove.nextInt(4);
                        if (move == 0) {
                            //w
                            directionRow = -1;
                            directionColumn = 0;


                        } else if (move == 1) {
                            //s
                            directionRow = 1;
                            directionColumn = 0;

                        } else if (move == 2) {
                            //d
                            directionRow = 0;
                            directionColumn = 1;

                        } else {
                            //a
                            directionRow = 0;
                            directionColumn = -1;

                        }

                    timePassed += time;

                    //borders
                    if ((posColumn == 0 && posColumn + directionColumn < 0) || (posColumn == gameTable.getFinalGameBoard()[0].length - 1 && posColumn + directionColumn > gameTable.getFinalGameBoard()[0].length - 1) || (posRow == 0 && posRow + directionRow < 0) || (posRow == map.length - 1 && posRow + directionRow > gameTable.getFinalGameBoard().length - 1)) {
                        mapModel.setMoveOnBoard(posRow, posColumn, value);

                    }
                    //kill
                    else if (gameTable.getFinalGameBoard()[posRow + directionRow][posColumn + directionColumn] == 2) {
                        if (gameTable.isShield()){
                            gameTable.setShield(false);
                        }else {
                            gameTable.setLives(gameTable.getLives() - 1);
                            gameTable.setKilled(true);
                        }
                    }
                    //collision
                    else if (gameTable.getFinalGameBoard()[posRow + directionRow][posColumn + directionColumn] == 1) {
                        mapModel.setMoveOnBoard(posRow, posColumn, value);

                    }
                    //move
                    else {
                        if (gameTable.getFinalGameBoard()[posRow + directionRow][posColumn + directionColumn] != 2 && gameTable.getFinalGameBoard()[posRow + directionRow][posColumn + directionColumn] < 4 || gameTable.getFinalGameBoard()[posRow + directionRow][posColumn + directionColumn] == 10) {
                            prevCellValue = gameTable.getFinalGameBoard()[posRow + directionRow][posColumn + directionColumn];
                        } else  prevCellValue = 0;
                        mapModel.setMoveOnBoard(posRow + directionRow, posColumn + directionColumn, value);
                        posRow += directionRow;
                        posColumn += directionColumn;
                    }

                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {

                    }
                    if (unSleep){
                        time = 50;
                        unSleep = false;
                    }
                    mapModel.notify();
                    try {
                        mapModel.wait();
                    } catch (InterruptedException e) {

                    }
                }
        }
    }
    public void placeGhostCenter(){
        boolean isPlaced = false;
        for (int i = 1; i<mapModel.getGameBoard().length ;i++) {
            for (int j = mapModel.getGameBoard()[i].length / 2; j < j+1; j++) {
                if (mapModel.getGameBoard()[i][j] == 0){
                    this.posRow = i;
                    this.posColumn = j;
                    mapModel.setMoveOnBoard(i,j, value);
                    isPlaced= true;
                    break;
                }
            }
            if (isPlaced) break;
        }
    }
    public void placeGhostLeft(){
        boolean isPlaced = false;
        for (int i = mapModel.getRowCount()/2; i<mapModel.getGameBoard().length ;i++) {
            for (int j = mapModel.getGameBoard()[i].length - (mapModel.getGameBoard()[i].length - 1); j < j+1; j++) {
                if (mapModel.getGameBoard()[i][j] == 0){
                    this.posRow = i;
                    this.posColumn = j;
                    mapModel.setMoveOnBoard(i,j, value);
                    isPlaced= true;
                    break;
                }
            }
            if (isPlaced) break;
        }
    }

    public void placeGhostRight() {
        boolean isPlaced = false;
        for (int i = mapModel.getRowCount() / 2; i < mapModel.getGameBoard().length; i++) {
            for (int j = mapModel.getGameBoard()[i].length - 1; j > j - 1; j--) {
                if (mapModel.getGameBoard()[i][j] == 0) {
                    this.posRow = i;
                    this.posColumn = j;
                    mapModel.setMoveOnBoard(i, j, value);
                    isPlaced = true;
                    break;
                }
            }
            if (isPlaced) break;
        }
    }
        public void placeGhostTopRight(){
            boolean isPlaced = false;
            for (int i = 0; i < mapModel.getGameBoard().length ;i++) {
                for (int j = mapModel.getGameBoard()[i].length - 1; j > j-1; j--) {
                    if (mapModel.getGameBoard()[i][j] == 0){
                        this.posRow = i;
                        this.posColumn = j;
                        mapModel.setMoveOnBoard(i,j, value);
                        isPlaced= true;
                        break;
                    }
                }
                if (isPlaced) break;
            }
    }
    public void placeGhostTopLeft(){
        boolean isPlaced = false;
        for (int i = 0; i<mapModel.getGameBoard().length ;i++) {
            for (int j = mapModel.getGameBoard()[i].length - (mapModel.getGameBoard()[i].length - 1); j < j+1; j++) {
                if (mapModel.getGameBoard()[i][j] == 0){
                    this.posRow = i;
                    this.posColumn = j;
                    mapModel.setMoveOnBoard(i,j, value);
                    isPlaced= true;
                    break;
                }
            }
            if (isPlaced) break;
        }
    }

    public int dropBoost(){
      Random rand = new Random();
      int chance = rand.nextInt(4);
      if (chance == 0){
          return 10;
      }
      else return prevCellValue;
    }

    public void stop(){
        this.isActiveGhost = false;
    }



}

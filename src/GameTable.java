import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class GameTable extends JTable {
    private int rows;
    private int columns;

    private int[][] gameBoard = new int[50][50];

    private int[][] finalGameBoard = new int[100][100];

    private int[][]Square = {
            {1,1},
            {1,1}
    };
    static int[][] figure1 = {
            {1,0,0,0,1},
            {0,0,1,0,0},
            {0,1,1,1,0},
            {0,0,1,0,0},
            {1,0,0,0,1}
    };
    static int[][] figure2 = {
            {0,0,0,0,0},
            {0,1,0,1,0},
            {0,1,0,1,0},
            {0,1,0,1,0},
            {0,0,0,0,0}
    };
    static int [][]figure3 =  {
            {0,0,0,1,0},
            {0,1,0,1,0},
            {0,0,1,0,0},
            {0,1,0,1,0},
            {0,0,0,0,0}
    };
    static int[][]figure4 =  {
            {0,0,0,0,0},
            {1,0,1,1,0},
            {0,0,1,1,0},
            {1,0,0,0,0},
            {1,1,0,1,1}
    };
    static int[][]figure5 =  {
            {0,0,0,0,0},
            {0,1,1,1,0},
            {0,0,0,1,0},
            {0,1,0,1,0},
            {0,0,0,0,0}
    };
    static int[][]figure6 =  {
            {0,0,0,0,0},
            {0,1,1,1,0},
            {0,0,1,0,0},
            {0,0,1,0,0},
            {0,1,1,1,0}
    };
    static int[][]figure7 =  {
            {1,0,1,1,0},
            {1,0,0,0,0},
            {0,0,1,0,1},
            {0,0,0,0,1},
            {1,1,1,0,1}
    };

    static int[][]figure8 =  {
            {0,0,0,0,0},
            {0,1,1,1,0},
            {0,1,0,0,0},
            {0,1,1,1,0},
            {0,0,0,0,0}
    };

    private int pacRowPos;


    private int pacColumnPos;

    private int ghostRowPos;

    private int ghostColumnPos;

    private CellRendering cellRender;

    private MyTableModel tableModel;

    private int lives;

    private boolean isKilled = false;

    private int dotsEaten = 0;

    private int amountOfGhosts;

    private boolean doublePoints =false;

    public void setDoublePoints(boolean doublePoints) {
        this.doublePoints = doublePoints;
    }

    public boolean isDoublePoints() {
        return doublePoints;
    }

    private boolean shield = false;

    public void setShield(boolean shield) {
        this.shield = shield;
    }

    public boolean isShield() {
        return shield;
    }

    public void setDotsEaten(int dotsEaten) {
        this.dotsEaten = dotsEaten;
    }

    public int getDotsEaten() {
        return dotsEaten;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setKilled(boolean killed) {
        isKilled = killed;
    }

    public boolean isKilled() {
        return isKilled;
    }

    public int[][] getFinalGameBoard() {
        return finalGameBoard;
    }

    public int getPacRowPos() {
        return pacRowPos;
    }

    public int getGhostRowPos() {
        return ghostRowPos;
    }

    public int getGhostColumnPos() {
        return ghostColumnPos;
    }

    public int getPacColumnPos() {
        return pacColumnPos;
    }

    public int getAmountOfGhosts() {
        return amountOfGhosts;
    }

    private boolean isSpeeded = false;

    public boolean isSpeeded() {
        return isSpeeded;
    }

    public void setSpeeded(boolean speeded) {
        isSpeeded = speeded;
    }

    public GameTable(MyTableModel gameTableModel) {
        this.tableModel = gameTableModel;
        this.rows = gameTableModel.getRowCount();
        this.columns = gameTableModel.getColumnCount();
        this.setModel(gameTableModel);

        generateNewMap(this , gameBoard);
        placePacMan();

        genAmountOfGhosts();
        this.lives = 3;

        gameTableModel.setGameBoard(finalGameBoard);

    }

    public void generateNewMap(JTable gameMap,int[][] gameBoard ){
        divider(gameBoard);
        this.finalGameBoard = cutMap(fullMap(gameBoard),this.getRowCount(),this.getColumnCount());
        this.cellRender = new CellRendering();
        gameMap.setDefaultRenderer(Object.class, cellRender);

    }

    public void placePacMan(){
        boolean isPlaced = false;
        for (int i = finalGameBoard.length-1; i >= 0 ; i--) {
            for (int j = finalGameBoard[0].length/2; j > ( finalGameBoard[0].length /2 )-1; j--) {
                if (finalGameBoard[i][j] == 0){
                    this.pacRowPos = i;
                    this.pacColumnPos = j;
                    finalGameBoard[i][j] = 2;
                    isPlaced = true;
                    break;
                }
            }
            if (isPlaced == true){
                break;
            }
        }
    }

    public void divider(int[][] gameBoard) {
        int rowPieces = gameBoard.length / 5;
        int columnPieces = gameBoard[0].length / 5;

        ArrayList<int[][]> figures= new ArrayList<>();
        figures.add(figure1);
        figures.add(figure2);
        figures.add(figure3);
        figures.add(figure4);
        figures.add(figure5);
        figures.add(figure6);
        figures.add(figure7);
        figures.add(figure8);

        //divides 5x5
        ArrayList<Integer>shapes = new ArrayList<>();
        int indx =0 ;

        for (int i = 0; i < rowPieces; i++) {
            for (int j = 0; j < columnPieces; j++) {
                boolean isSame = true;
                int[][] figure;
                int chunkRow = i * 5;
                int chunkCol = j * 5;
                Random rand = new Random();
                figure = null;

                 while (isSame){
                int nowFigure = rand.nextInt(8);
                figure = figures.get(nowFigure);
                shapes.add(nowFigure);
                if ( indx >1 &&  (nowFigure == shapes.get(indx - 1)) || (indx > 9) && nowFigure == shapes.get(indx - 10 ))  {
                    isSame = true;
                }else isSame=false;

            }
                    //fills 5x5
                for (int x = 0; x < figure.length; x++) {
                    for (int y = 0; y < figure[0].length; y++) {
                        gameBoard[chunkRow + x ][chunkCol + y ] += figure[x][y];
                    }
                }
                    indx++;
            }
        }
    }
    public int[][] fullMap (int[][] gameBoard){
        int primeRows = gameBoard.length;
        int primeColumns = gameBoard[0].length;

        int[][] fullGameBoard = new int[primeRows*2][primeColumns*2];

        //copy
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                fullGameBoard[i][j] = gameBoard[i][j];
            }
        }

        //mirror
        for (int i = 0; i < primeRows; i++) {
            for (int j = 0; j < primeColumns; j++) {
                fullGameBoard[i][j] = gameBoard[i][j];
                fullGameBoard[i][primeColumns * 2 - j -1] = gameBoard[i][j];
            }
        }
        for (int i = 0; i < primeRows; i++) {
            for (int j = 0; j < primeColumns*2; j++) {
                fullGameBoard[primeRows*2-i-1][j] = fullGameBoard[i][j];
            }
        }

        return fullGameBoard;
    }

    public int[][] cutMap(int[][] fullGameBoard , int row , int column){


        int centerRow = (fullGameBoard.length - row) / 2 ;
        int centerColumn = (fullGameBoard[0].length - column) /2 ;
        int[][] finalMap = new int [row][column];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                finalMap[i][j] = fullGameBoard[centerRow + i][centerColumn + j];
            }
        }

        return finalMap;
    }
    public void genAmountOfGhosts(){
        int size = tableModel.getColumnCount() * tableModel.getRowCount();
        if (size <700){
            this.amountOfGhosts = 1;
        }
        else if (size < 1500 && size >700){
            this.amountOfGhosts = 2;
        }
        else if (size<2200 && size >1500){
            this.amountOfGhosts = 3;
        }
        else if (size<4000 && size>2200){
            this.amountOfGhosts = 4;
        }
        else if (size<4500 && size>4000){
            this.amountOfGhosts = 5;
        }
        else this.amountOfGhosts = 6;

    }


    public CellRendering getCellRender() {
        return cellRender;
    }

    public int getLives() {
        return lives;
    }

}

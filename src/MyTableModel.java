import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {

     private int [][] gameBoard;

    public void setGameBoard(int[][] gameBoard) {
        this.gameBoard = gameBoard;
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }
    public void setMoveOnBoard(int row,int column,int value){
        this.gameBoard[row][column] = value;
    }

    public MyTableModel(int rows, int columns) {
        this.gameBoard = new int[rows][columns];
    }

    @Override
    public int getRowCount() {
        return this.gameBoard.length;
    }

    @Override
    public int getColumnCount() {
        return this.gameBoard[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return gameBoard[rowIndex][columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

}

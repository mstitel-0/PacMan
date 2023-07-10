import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameWindow extends JFrame {

    private int dirRow;
    private int dirColumn;


    private GameTable table;

    private ArrayList<Thread>ghostThreads;

    private ArrayList<Ghost>ghostsList;

    private JPanel gamePanel;

    private JLabel statusLabel;

    private String boostText;

    private Menu gameMenu;


    public void setBoostText(String boostText) {
        this.boostText = boostText;
    }

    public String getBoostText() {
        return boostText;
    }

    public GameWindow(MyTableModel gameTableModel,Menu menu) {
        gameMenu = menu;

        GameTable gameTable = new GameTable(gameTableModel);
        this.table = gameTable;

        for (int i = 0; i < gameTable.getColumnCount(); i++) {
            gameTable.getColumnModel().getColumn(i).setPreferredWidth(12);
        }
        gameTable.setRowSelectionAllowed(false);
        gameTable.setShowGrid(false);
        gameTable.setIntercellSpacing(new Dimension(0, 0));

        PacMan pacMan = new PacMan(gameTable.getFinalGameBoard(), gameTable, gameTableModel, this, gameTable.getPacRowPos(), gameTable.getPacColumnPos());
        Thread pacManThread = new Thread(pacMan);

        JPanel panel = new JPanel(new BorderLayout());
        this.gamePanel = panel;

        JLabel gameLabel = new JLabel();
        this.statusLabel = gameLabel;

        int ghosts = gameTable.getAmountOfGhosts();
        this.ghostThreads = new ArrayList<>();
        this.ghostsList = new ArrayList<>();
        for (int i = 0; i < ghosts; i++) {
            ghostsList.add(new Ghost(gameTable.getFinalGameBoard(),gameTableModel,gameTable.getGhostRowPos(), gameTable.getGhostColumnPos(),gameTable,this));
            ghostThreads.add(new Thread(ghostsList.get(i)));
            ghostThreads.get(i).start();
        }

        BoardThread board = new BoardThread(gameTableModel,this,gameTable,gameLabel,gameMenu);
        Thread boardThread = new Thread(board);


        boardThread.start();
        pacManThread.start();

        gamePanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                sizeChanged();
            }
        });
        gamePanel.add(gameTable,BorderLayout.CENTER);
        gamePanel.add(gameLabel,BorderLayout.PAGE_END);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                boardThread.interrupt();
                pacManThread.interrupt();
                for (int i = 0; i < ghostThreads.size(); i++) {
                    ghostThreads.get(i).interrupt();
                    ghostsList.get(i).stop();
                }
            }
        });
        gamePanel.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
                    gameTable.getCellRender().setPacManRotateSide(2);
                    dirRow = -1;
                    dirColumn = 0;
                    pacMan.setDirections(dirRow, dirColumn);
                }
                if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    gameTable.getCellRender().setPacManRotateSide(3);
                    dirRow = 1;
                    dirColumn = 0;
                    pacMan.setDirections(dirRow, dirColumn);
                }
                if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    gameTable.getCellRender().setPacManRotateSide(4);
                    dirRow = 0;
                    dirColumn = 1;
                    pacMan.setDirections(dirRow, dirColumn);
                }
                if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
                    gameTable.getCellRender().setPacManRotateSide(1);
                    dirRow = 0;
                    dirColumn = -1;
                    pacMan.setDirections(dirRow, dirColumn);
                }
            }
        });

        setTitle("PacMan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(gamePanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);


        KeyStroke endShortCut = KeyStroke.getKeyStroke(KeyEvent.VK_Q,KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK);
        InputMap shortCutInput = gamePanel.getInputMap();
        shortCutInput.put(endShortCut,"shortcut");
        ActionMap performShortCut = gamePanel.getActionMap();
        performShortCut.put("shortcut", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                boardThread.interrupt();
                pacManThread.interrupt();
                for (int i = 0; i < ghostThreads.size(); i++) {
                    ghostThreads.get(i).interrupt();
                    ghostsList.get(i).stop();
                }
                menu.setVisible(true);
            }
        }
        );

    }

    public void sizeChanged() {
        int width =gamePanel.getWidth();
        int height = gamePanel.getHeight();
        int rowCount = table.getRowCount();
        int columnCount = table.getColumnCount();
        int cellWidth = width / columnCount;
        int cellHeight = height / rowCount;

        for (int i = 0; i < columnCount; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(cellWidth);
        }
        table.getCellRender().setCellHeight(cellHeight);
        table.getCellRender().setCellWidth(cellWidth);
        table.setRowHeight(cellHeight);
        table.getCellRender().setImWidth(cellWidth);
        table.getCellRender().setImHeight(cellHeight);

        statusLabel.setSize(new Dimension(gamePanel.getWidth()-table.getWidth(),gamePanel.getHeight()-table.getHeight()));
    }
    public void killGhost(){
        if (table.getAmountOfGhosts() > 1) {
            ghostsList.get(ghostsList.size() - 1).stop();
        }
    }

    public void sleepGhosts(){
        for(Ghost ghost :ghostsList){
            ghost.setTime(800);
            ghost.setUnSleep(true);
        }
    }

}



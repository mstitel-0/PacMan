import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class CellRendering extends DefaultTableCellRenderer {

    private int cellHeight;

    private int cellWidth;

    private int rotateSide;

    private int imWidth;

    private boolean animation;

    private int imHeight;

    private static ArrayList<Image>images;

    public void setPacManRotateSide(int pacManRotateSide) {
        this.rotateSide = pacManRotateSide;
    }

    public void setImWidth(int width){this.imWidth = width;}

    public void setImHeight(int height){this.imHeight = height;}
    private int val;
    public void setCellHeight(int cellHeight) {
        this.cellHeight = cellHeight;
    }

    public void setCellWidth(int cellWidth) {
        this.cellWidth = cellWidth;
    }

    public void setAnimation(boolean animation) {
        this.animation = animation;
    }

    public boolean isAnimation() {
        return animation;
    }



    public CellRendering() {
        this.imHeight = getHeight();
        this.imWidth = getWidth();
        this.animation = false;
        this.rotateSide = 4;
    }


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
        Graphics graphic = c.getGraphics();
        if (value.equals(0)) {
            val = 0;
           setBackground(Color.BLACK);
           setForeground(Color.BLACK);
           setIcon(null);
        } else if (value.equals(1)) {
            val = 1;
            setForeground(Color.BLUE);
            setBackground(Color.BLUE);
            setIcon(null);
        }
        else if (value.equals(2)) {

            val = 2;
            setBackground(Color.BLACK);
            setForeground(Color.BLACK);
            ImageIcon pacManIcon = new ImageIcon();
            if (rotateSide == 2 ){
                if (!animation) {
                    pacManIcon = new ImageIcon("src/Images/pacmanUp (1).png");
                }
                if (animation){
                    pacManIcon = new ImageIcon("src/Images/pacmanUp (2).png");
                }
            }
            else if (rotateSide == 3){
                if (!animation) {
                    pacManIcon = new ImageIcon("src/Images/pacmanDown.png");
                }
                if (animation){
                    pacManIcon = new ImageIcon("src/Images/pacmanDown 2.png");
                }
            }
            else if (rotateSide == 1){
                if (!animation) {
                    pacManIcon = new ImageIcon("src/Images/pacmanLeft.png");
                }
                if (animation){
                    pacManIcon = new ImageIcon("src/Images/pacmanLeft2.png");
                }
            }
            else {
                if (!animation) {
                    pacManIcon = new ImageIcon("src/Images/pacmanRIght.png");
                }
                if (animation) {
                    pacManIcon = new ImageIcon("src/Images/pacmanRIght2.png");
                }
            }
            Image pacManImage = pacManIcon.getImage().getScaledInstance(imWidth == 0?10 : imWidth,imHeight == 0?10:imHeight,Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(pacManImage));

        }
        else if (value.equals(3)){
            val = 3;
            setBackground(Color.BLACK);
            setForeground(Color.BLACK);
            setIcon(null);
        }
        else if (value.equals(4)){
            val = 4;
            setBackground(Color.BLACK);
            setForeground(Color.black);
            ImageIcon ghostIcon = new ImageIcon("src/Images/ghost (1).png");
            Image ghostImage = ghostIcon.getImage().getScaledInstance(imWidth == 0?10 : imWidth,imHeight == 0?10:imHeight,Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(ghostImage));
        }
        else if (value.equals(5)){
            val = 5;
            setBackground(Color.BLACK);
            setForeground(Color.black);
            ImageIcon ghostIcon = new ImageIcon("src/Images/ghost (2).png");
            Image ghostImage = ghostIcon.getImage().getScaledInstance(imWidth == 0?10 : imWidth,imHeight == 0?10:imHeight,Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(ghostImage));
        }
        else if (value.equals(6)){
            val = 6;
            setBackground(Color.BLACK);
            setForeground(Color.black);
            ImageIcon ghostIcon = new ImageIcon("src/Images/ghost (3).png");
            Image ghostImage = ghostIcon.getImage().getScaledInstance(imWidth == 0?10 : imWidth,imHeight == 0?10:imHeight,Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(ghostImage));
        }
        else if (value.equals(7)){
            val = 7;
            setBackground(Color.BLACK);
            setForeground(Color.black);
            ImageIcon ghostIcon = new ImageIcon("src/Images/ghost (4).png");
            Image ghostImage = ghostIcon.getImage().getScaledInstance(imWidth == 0?10 : imWidth,imHeight == 0?10:imHeight,Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(ghostImage));
        }
        else if (value.equals(8)){
            val = 8;
            setBackground(Color.BLACK);
            setForeground(Color.black);
            ImageIcon ghostIcon = new ImageIcon("src/Images/ghost (5).png");
            Image ghostImage = ghostIcon.getImage().getScaledInstance(imWidth == 0?10 : imWidth,imHeight == 0?10:imHeight,Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(ghostImage));
        }
        else if (value.equals(9)){
            val = 9;
            setBackground(Color.BLACK);
            setForeground(Color.black);
            ImageIcon ghostIcon = new ImageIcon("src/Images/ghost (6).png");
            Image ghostImage = ghostIcon.getImage().getScaledInstance(imWidth == 0?10 : imWidth,imHeight == 0?10:imHeight,Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(ghostImage));
        }
        else {
            val = 10;
            setBackground(Color.BLACK);
            setForeground(Color.black);
            ImageIcon ghostIcon = new ImageIcon("src/Images/boostIcon.png");
            Image ghostImage = ghostIcon.getImage().getScaledInstance(imWidth == 0?10 : imWidth,imHeight == 0?10:imHeight,Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(ghostImage));
        }

        return super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
    }

   

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (val == 0 ){
            int dotSize = 5;
            int x = (getWidth() - dotSize) / 2;
            int y = (getHeight()- dotSize) / 2;

            g.setColor(Color.WHITE);
            g.fillOval(x, y, dotSize, dotSize);
        }
    }

}

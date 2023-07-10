import javax.swing.*;

public class BoardInput extends Exception{
    public BoardInput() {
        super();
    }
    public void boardInputMessage(){
        JOptionPane.showMessageDialog(null,"Inappropriate map size","Error",JOptionPane.ERROR_MESSAGE);
    }
}

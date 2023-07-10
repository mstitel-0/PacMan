import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

public class HighScoreModel extends AbstractListModel {

    private ArrayList<String> players;

    public HighScoreModel(ArrayList<String>playersArr) {
        if (playersArr.size()>1) {
            Collections.sort(playersArr, new DescendingCompare());
        }
        this.players = playersArr;
    }


    @Override
    public int getSize() {
        return players.size();
    }

    @Override
    public Object getElementAt(int index) {
        return players.get(index);
    }
}

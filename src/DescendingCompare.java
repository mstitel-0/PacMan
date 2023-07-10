import java.util.Comparator;

public class DescendingCompare implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
       int score1 = Integer.parseInt(o1.split("-")[1]);
       int score2 = Integer.parseInt(o2.split("-")[1]);
       return Integer.compare(score2,score1);
    }
}

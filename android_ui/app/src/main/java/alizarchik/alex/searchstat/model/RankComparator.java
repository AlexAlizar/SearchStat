package alizarchik.alex.searchstat.model;
import java.util.Comparator;

/**
 * Created by Olesia on 06.03.2018.
 */

public class RankComparator implements Comparator<GenStatDataItem> {

    @Override
    public int compare(GenStatDataItem g1, GenStatDataItem g2) {
        if(g1.getRank() < g2.getRank()){
            return 1;
        } else {
            return -1;
        }
    }
}

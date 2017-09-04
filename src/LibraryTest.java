import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LibraryTest {
    public static void main(String[] args) {
        Integer[] x1 = new Integer[] {1,2,3,4,5,6,7,8,9};
        Integer[] y1 = new Integer[] {11,12,13,14,15,16,17,18,19};

        List<Integer[]> a  = Arrays.asList(x1, y1, x1, y1);
        System.out.println(a.size());
        for (Integer[] pairs : a)
            System.out.println(pairs[0] + " " + pairs[1]);
    }
}

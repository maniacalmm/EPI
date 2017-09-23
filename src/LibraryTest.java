import java.util.*;

public class LibraryTest {
    public static void main(String[] args) {
        /*
        Integer[] x1 = new Integer[] {1,2,3,4,5,6,7,8,9};
        Integer[] y1 = new Integer[] {11,12,13,14,15,16,17,18,19};

        List<Integer[]> a  = Arrays.asList(x1, y1, x1, y1);
        System.out.println(a.size());
        for (Integer[] pairs : a)
            System.out.println(pairs[0] + " " + pairs[1]);
        */



        List<Integer> list1 = Arrays.asList(1,2,3,4,5,6,7);

        Iterator<Integer> listIter = list1.iterator();

        while(listIter.hasNext())
            System.out.println(listIter.next());



        while(listIter.hasNext())
            System.out.println(listIter.next());
    }
}

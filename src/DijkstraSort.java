import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DijkstraSort {

    public static void sort(int[] toSort) {
       sort(toSort, 0, toSort.length - 1);
    }

    private static void sort(int[] toSort, int lo, int hi) {
        if (lo >= hi) return;
        int equal = lo, lt = lo, gt = hi;
        int p = toSort[equal];
        while(equal <= gt) {
            if (toSort[equal] < p) swap(toSort, lt++, equal++);
            else if (toSort[equal] > p) swap(toSort, gt--, equal);
            else equal++;
        }

        for (int e : toSort)
            System.out.print(e + " ");
        System.out.println();
        sort(toSort, lo, lt-1);
        sort(toSort, gt + 1, hi);
    }

    public static void swap(int[] toSort, int i, int j) {
        int tmp = toSort[i];
        toSort[i] = toSort[j];
        toSort[j] = tmp;
    }

    public static void partition(List<Integer> A, int lo, int hi) {
        int eq = lo, lt = lo, gt = hi;
        int pivotValue = A.get(eq);

        while(eq <= gt) {
            if (A.get(eq) < pivotValue) Collections.swap(A, lo++, eq++);
            else if (A.get(eq) > pivotValue) Collections.swap(A, eq, gt--);
            else eq++;
        }

        System.out.println(A);
    }

    public static void main(String[] args) {
        int[] a = new int[] {9,8,7,6,5,4,3,2,1,0};
        //sort(a);
        List<Integer> b = Arrays.asList(6, 8, 6,4 ,4 , 1, 3, 5);
        partition(b, 0, b.size() - 1);
    }

}

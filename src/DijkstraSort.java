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


    public static void main(String[] args) {
        int[] a = new int[] {3,3,4,4,1,3,4,3,1,3,4,3,1,3,4,1,3,4};
        sort(a);

    }
}

import java.util.*;

public class Ch5Arrays {
    public static void evenOdd(int[] A) {
        // given an array of int, put the even number first
        // the advantage of array is that we can efficiently operate from both ends
        final int BIT_MASK = 0xFFFF;
        int nextEven = 0, nextOdd = A.length - 1;
        while(nextEven < nextOdd) {
            if (A[nextEven] % 2 == 0)
                nextEven++;
            else {
                int tmp = A[nextEven];
                A[nextEven] = A[nextOdd];
                A[nextOdd--] = tmp;
            }
        }
    }

/***************************************************/
    public static enum Color {RED, BLACK, BLUE, fuckingwhite};

    public static void dutchFlagParition1(int pivotIndex, List<Color> A) {
        Color p = A.get(pivotIndex);
        // First pass: group elements smaller than pivot
        int smaller = 0;
        for (int i = 0; i < A.size(); i++) {
            if (A.get(i).ordinal() < p.ordinal())
                Collections.swap(A, smaller++, i);
        }

        //Second pass: group elements larger than pivot
        int larger = A.size() - 1;
        for (int j = A.size() - 1; j >= 0 && A.get(j).ordinal() >= p.ordinal(); j--) {
            if (A.get(j).ordinal() > p.ordinal())
                Collections.swap(A, larger--, j);
        }
    }

    public static void dutchFlagPartition2(int pivotIndex, List<Color> A) {
        int smaller = 0, equal = 0, larger = A.size();
        Color p = A.get(pivotIndex);
        while(equal < larger) {
            if (A.get(equal).ordinal() < p.ordinal())
                Collections.swap(A, smaller++, equal++);
            else if (A.get(equal).ordinal() > p.ordinal())
                Collections.swap(A, --larger, equal++);
            else equal++;
        }
    }

/***************************************************/

public static void main(String[] args) {
    List<Color> a = new ArrayList<>();
    a.add(Color.BLUE);
    a.add(Color.RED);
    a.add(Color.fuckingwhite);
    for (Color e : a)
        System.out.println(e.ordinal());

}


}

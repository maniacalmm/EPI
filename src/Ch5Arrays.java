public class Arrays {
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





}

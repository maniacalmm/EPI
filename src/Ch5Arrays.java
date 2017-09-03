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
    public static List<Integer> plusOne(List<Integer> A) {
        int n = A.size() - 1;
        A.set(n, A.get(n) + 1); // increase the LSB

        for (int i = n; i > 0 && A.get(i) != 10; i--) {
            A.set(i, 0);
            A.set(i - 1, A.get(i - 1) + 1);
        }

        if (A.get(0) == 10) {
            A.set(0, 1);
            A.add(0);
        }
        return A;
    }

    public static String binaryAdd(String A, String B) {
        // 011 001
        int nA = A.length() - 1;
        int nB = B.length() - 1;

        if (nA > nB) {
            for (int i = nA - nB; i > 0; i--)
                B = "0" + B;
        } else if (nA < nB) {
            for (int j = nB - nA; j > 0; j--)
                A = "0" + A;
        }

        System.out.println(A + " " + B);

        String result = "";
        boolean cin = false;
        for (int i = nA; i >= 0; i--) {
            //System.out.println("before: result " + result + " cin: " + cin);
            if (A.charAt(i) == B.charAt(i)) {
                if (A.charAt(i) == '1') {
                    if (cin) result = "1" + result;
                    else result = "0" + result;

                    cin = true;
                } else {
                    if (cin) result = "1" + result;
                    else result = "0" + result;

                    cin = false;
                }
            } else {
                if (cin) {
                    result = "0" + result;
                    cin = true;
                } else {
                    result = "1" + result;
                    cin = false;
                }
            }
            //System.out.println("after: result " + result + " cin: " + cin);
        }

        if (cin) result = "1" + result;
        return result;
    }


/***************************************************/
    public static List<Integer> multiply(List<Integer> num1, List<Integer> num2) {
        final int sign = (num1.get(0) < 0) ^ (num2.get(0) < 0) ? -1 : 1;
        num1.set(0, Math.abs(num1.get(0)));
        num2.set(0, Math.abs(num2.get(0)));

        List<Integer> result =
                new ArrayList<>(Collections.nCopies(num1.size() + num2.size(), 0));

        for (int e : result)
            System.out.print(e);
        System.out.println();

        for (int i = num1.size() - 1; i >= 0; i--) {
            for (int j = num2.size() - 1; j >= 0; j--) {
                result.set(i + j + 1, result.get(i + j + 1) + num1.get(i) * num2.get(j));
                result.set(i + j, result.get(i + j) + result.get(i + j + 1) / 10);
                result.set(i + j + 1, result.get(i + j + 1) % 10);
            }
        }

        // Remove the leading zeros;
        int firstNotZero = 0;
        while(firstNotZero < result.size() && result.get(firstNotZero) == 0)
            firstNotZero++;
        result = result.subList(firstNotZero, result.size());
        if (result.isEmpty()) {
            return  Arrays.asList(0);
        }

        result.set(0, result.get(0) * sign);
        return result;
    }

/***************************************************/

    public static void main(String[] args) {
        //List<Color> a = new ArrayList<>();
        //a.add(Color.BLUE);
        //a.add(Color.RED);
        //a.add(Color.fuckingwhite);
        //System.out.println(binaryAdd("111111", "001"));
        //System.out.println();
        Integer[] a = new Integer[] {1,2,3,4,5,6};
        Integer[] b = new Integer[] {6,5,4,3,2,1};
        List<Integer> A = Arrays.asList(a);
        List<Integer> B = Arrays.asList(b);

        for (int e : multiply(A, B))
            System.out.print(e);
        System.out.println();


    }


}

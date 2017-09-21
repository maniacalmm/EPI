import java.util.*;

public class Ch11Searching {

    public static int bsearch(int t, List<Integer> A) {
        int L = 0, U = A.size() - 1;
        while(L <= U) {
            int M = (L + U) / 2; // potential overflow issue
            // int M = L + (U - L) / 2

            if(A.get(M) < t) {
                L = M + 1;
            } else if (A.get(M) > t){
                U =  M - 1;
            } else {
                return M;
            }
        }

        return -1; // didn't find
    }

    public static class Student {
        public String name;
        public double GPA;

        Student(String name, double GPA) {
            this.name = name;
            this.GPA = GPA;
        }
    }

    private static final Comparator<Student> compGPA = new Comparator<Student>() {
        @Override
        public int compare(Student a, Student b) {
            if (Double.compare(a.GPA, b.GPA) != 0) return Double.compare(a.GPA, b.GPA);
            return a.name.compareTo(b.name);
        }};

    public static boolean searchStudent(List<Student> students, Student target, Comparator<Student> compGPA) {
        return Collections.binarySearch(students, target, compGPA) >= 0;
    }

    public static int searchFirstOfK(List<Integer> A, int k) {
        int L = 0, U = A.size() - 1;

        while(L <= U) {
            int M = L + (U - L) / 2;
            //System.out.println(L + " " + M + " " + U);
            if (A.get(M) < k) {
                L = M + 1;
            } else if (A.get(M) > k) {
                U = M - 1;
            } else {
                U = M;
                if (L == U) return M; // straight forward manner
            }
        }

        return -1;
    }

    public static int serachEntryEqualToitsIndex(List<Integer> A) {
        int L = 0, U = A.size() - 1;

        while(L <= U) {
            int M = L + (U - L) / 2;
            if (A.get(M) < M) L = M + 1;
            else if (A.get(M) > M) U = U - 1;
            else return M;
        }
        return -1;
    }


    public static int searchSmallest(List<Integer> A) { // for cycle array
        int L = 0, U = A.size() - 1;

        while(L < U) {
            int M = L + (U - L) / 2;
            if (A.get(M) < A.get(U)) U = M;
            else if (A.get(M) > A.get(U)) L = M + 1;
                // no == scenario, all key distinct
        }

        return L;
    }

    public static int squareRoot(int k) {
        int L = 0, U = k;
        while(L < U) {
            int M = L + (U - L) / 2;
            if (M * M < k) L = M + 1;
            else if (M * M > k) U = M - 1;
            else return M;
        }

        return L - 1;
    }

    public static double squareRoot(double x) {
        double L, U;

        if (x < 1.0) {
            L = x;
            U = 1.0;
        } else {
            L = 1.0;
            U = x;
        }

        while(compare(L, U) != Ordering.EQUAL) {
            // we dont need baby step increment anymore, just divide and divide
            double M = L + 0.5 * (U - L);
            double MS = M * M;
            if (compare(MS, x) == Ordering.LARGER) {
                U = M;
            } else {
                L = M;
            }
            System.out.println(L + " " + U);
        }

        return L;
    }

    private static enum Ordering {SMALLER, EQUAL, LARGER}

    private static Ordering compare(double a, double b) {
        final double EPSILON = 0.0001;
        // Uses normalization for precision problem.
        double diff = (a - b) / b;
        return diff < -EPSILON
                ? Ordering.SMALLER :
                (diff > EPSILON ? Ordering.LARGER: Ordering.EQUAL);
    }

    // to calculate x / y
    public static double division(double x, double y) {
        double L, U;
        if (y < 1.0) {
            double tmp = y;
            double factor = 10;
            while(tmp < 1) {
                tmp *= factor;
                factor *= 10;
            }
            L = x;
            U = x * factor;
        } else {
            L = 1.0;
            U = x;
        }

        while(compareDiv(L, U) != 0) {
            double M = L + (U - L) * 0.5;
            if (compareDiv(M*y, x) > 0) U = M;
            else L = M; // this is important, otherwise it does not converge
                        // since only M satisfies compareDIv(M*y, x) == 0, not L
            System.out.println(M);
        }

        return L;
    }

    private static int compareDiv(double a, double b) {
        double EPSILON = 0.00001;
        if ((a - b) < -EPSILON) return -1;
        else if ((a - b) > EPSILON) return 1;
        else return 0;
    }

    public static boolean matrixSearch(List<List<Integer>> A, int x) {
        int i = 0, j = A.size() - 1;
        while(A.get(i).get(j) != x) {
            if (A.get(i).get(j) > x) j--;
            else if (A.get(i).get(j) < x) i++;

            if (j < 0 || i < 0) return false;
        }

        return true;
    }

    // this can easily be replaced with a size-2 array
    private static class MinMax {
        public Integer min;
        public Integer max;

        public MinMax(Integer min, Integer max) {
            this.min = min;
            this.max = max;
        }

        private static MinMax minMax (Integer a, Integer b) {
            return Integer.compare(b, a) < 0 ? new MinMax(b, a) : new MinMax(a, b);
        }
    }



    public static MinMax findMinMax(List<Integer> A) {
        if (A.size() <= 1) {
            return new MinMax(A.get(0), A.get(0));
        }

        MinMax globalMinMax = MinMax.minMax(A.get(0), A.get(1));

        for (int i = 2; i < A.size(); i += 2) {
            MinMax localMinMax = MinMax.minMax(A.get(i), A.get(i + 1));

            globalMinMax = new MinMax(Math.min(globalMinMax.min, localMinMax.min),
                                      Math.max(globalMinMax.max, localMinMax.max));
        }


        if (A.size() % 2 != 0) {
            globalMinMax = new MinMax(Math.min(globalMinMax.min, A.get(A.size() - 1)),
                                        Math.max(globalMinMax.max, A.get(A.size() - 1)));
        }

        return globalMinMax;


    }


    public static void main(String[] args) {
        List<Integer> a = new ArrayList<>(Arrays.asList(-14,-10,2,108,108,243,285,285,285,401));
        /*
        System.out.println(searchFirstOfK(a, 108));
        System.out.println(searchFirstOfK(a, 285));
        System.out.println(searchFirstOfK(a, 2));
        */

        //System.out.println(squareRoot(1776786527.0));
        System.out.println(division(52,13));

    }
}

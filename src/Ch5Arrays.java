import java.util.*;

public class Ch5Arrays {
    public static void evenOdd(int[] A) {
        // given an array of int, put the even number first
        // the advantage of array is that we can efficiently operate from both ends
        final int BIT_MASK = 0xFFFF;
        int nextEven = 0, nextOdd = A.length - 1;
        while (nextEven < nextOdd) {
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
    public static enum Color {
        RED, BLACK, BLUE, fuckingwhite
    }

    ;

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
        while (equal < larger) {
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
        while (firstNotZero < result.size() && result.get(firstNotZero) == 0)
            firstNotZero++;
        result = result.subList(firstNotZero, result.size());
        if (result.isEmpty()) {
            return Arrays.asList(0);
        }

        result.set(0, result.get(0) * sign);
        return result;
    }

    /***************************************************/
    public static boolean reached = false;

    public static boolean advance(int[] game) {
        advance(game, 0);
        return reached;
    }

    private static void advance(int[] game, int position) {
        if (position == game.length - 1) {
            reached = true;
            return;
        }
        for (int i = game[position]; i > 0; i--) {
            advance(game, position + i);
        }
    }

    public static boolean canReachEnd(List<Integer> maxAdvancedSteps) {
        int furthestReachSoFar = 0, lastIndex = maxAdvancedSteps.size() - 1;
        for (int i = 0; i <= furthestReachSoFar && furthestReachSoFar < lastIndex; i++) {
            // i <= furthestReachSoFar --> this line is important
            furthestReachSoFar = Math.max(furthestReachSoFar, i + maxAdvancedSteps.get(i));
            // furthest point can be reach from any point is i + maxAdvancedSteps.get(i)
        }

        return furthestReachSoFar == lastIndex;
    }

    /***************************************************/
    public static int deleteDuplicates(List<Integer> A) {
        if (A.isEmpty()) return 0;

        int writeIndex = 1;
        for (int i = 1; i < A.size() - 1; i++) {
            if (!A.get(writeIndex - 1).equals(A.get(i)))
                A.set(writeIndex++, A.get(i));
        }

        return writeIndex;
    }

    /***************************************************/
    public static double computeMaxProfit(List<Double> prices) {
        double maxProfit = 0, minPrice = Double.POSITIVE_INFINITY;

        for (double price : prices) {
            maxProfit = Math.max(maxProfit, price - minPrice);
            minPrice = Math.min(minPrice, price);
        }

        return maxProfit;
    }

    public static int findLongestSubarray(List<Integer> A) {
        int longest = 0;
        int id = 0;
        int start = id;
        for (int i = 1; i < A.size(); i++) {
            if (A.get(i) != A.get(i - 1)) {
                longest = Math.max(longest, i - start);
                start = i;
            }

            if (i == A.size() - 1) {
                //System.out.println(i + " " + start);
                longest = Math.max(longest, i - start + 1);
            }
        }
        return longest;
    }

    public static void rearrage(List<Integer> A) {
        for (int i = 0; i < A.size(); i++) {
            if ((i % 2 == 0 && A.get(i - 1) < A.get(i)
                    || (i % 2) != 0 && A.get(i - 1) > A.get(i)))
                Collections.swap(A, i - 1, i);
        }
    }

    public static List<Integer> generatePrimes(int n) {
        List<Integer> primes = new ArrayList<>();
        List<Boolean> isPrime = new ArrayList<>(Collections.nCopies(n + 1, true));

        isPrime.set(0, false);
        isPrime.set(1, false);

        for (int p = 2; p <= n; p++) {
            if (isPrime.get(p)) {
                primes.add(p);

                for (int i = p; i < n; i += p) {
                    isPrime.set(p, false);
                }
            }
        }

        return primes;
    }

    public static void applyPermutation(List<Integer> perm, List<Integer> A) {
        for (int i = 0; i < perm.size(); i++) {
            if (perm.get(i) < 0) continue;

            int po = perm.get(i);
            int currVal = A.get(i);
            while (po >= 0) {
                int nextVal = A.get(po);
                int nextPo = perm.get(po);
                A.set(po, currVal);
                perm.set(po, -1);
                currVal = nextVal;
                po = nextPo;
            }
        }

        for (int a : A)
            System.out.print(a + " ");
        System.out.println();
    }

    public static void applyPermuationBook(List<Integer> perm, List<Integer> A) {
        for (int i = 0; i < A.size(); ++i) {
            int next = i;
            while (perm.get(next) > 0) {
                Collections.swap(A, i, perm.get(next));
                int tmp = perm.get(next);
            }
        }
    }

    public static List<Integer> nextPermutation(List<Integer> perm) {
        int inversion_point = perm.size() - 2;
        while (inversion_point >= 0
                && perm.get(inversion_point) >= perm.get(inversion_point + 1))
            inversion_point--;

        if (inversion_point == -1) return Collections.emptyList();

        for (int i = perm.size() - 1; i > inversion_point; i--) {
            if (perm.get(i) > perm.get(inversion_point)) {
                Collections.swap(perm, inversion_point, i);
                break;
            }
        }

        Collections.reverse(perm.subList(inversion_point + 1, perm.size()));
        return perm;
    }

    public static void randomSampling(int k, List<Integer> A) {
        Random rand = new Random();
        for (int i = 0; i < k; i++) {
            Collections.swap(A, i, i + rand.nextInt(A.size() - i));
        }
    }

    public static List<Integer> onlineRandomSample(Iterator<Integer> sequence, int k) {
        List<Integer> runningSample = new ArrayList<>(k);

        for (int i = 0; i < k && sequence.hasNext(); i++) {
            runningSample.add(sequence.next());
        }

        int numSeenSoFar = k;
        Random randIdxGen = new Random();
        while (sequence.hasNext()) {
            // core idea: replace one of the old element with the newly added one
            Integer x = sequence.next();
            numSeenSoFar++;
            final int idxToReplace = randIdxGen.nextInt(numSeenSoFar);
            if (idxToReplace < k) {
                runningSample.set(idxToReplace, x);
            }
        }
        return runningSample;
    }

    public static List<Integer> computeRandomPermuatation(int n) {
        List<Integer> permutation = new ArrayList<>(n);
        for (int i = 0; i < n; i++)
            permutation.add(i);
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            Collections.swap(permutation, i, rand.nextInt(n - i) + i);
        }

        return permutation;
    }


    public static List<Integer> randomSubset(int n, int k) {
        /**
         * By using a hashTable, we can get O(k) complexity, instead of O(n) during initial initalization
         */
        Map<Integer, Integer> changedElements = new HashMap<>();
        Random rand = new Random();

        for (int i = 0; i < k; i++) {
            int toReplace = rand.nextInt(n - i) + i;
            Integer p1 = changedElements.get(toReplace);
            Integer p2 = changedElements.get(i);

            if (p1 == null && p2 == null) {
                changedElements.put(toReplace, i);
                changedElements.put(i, toReplace);
            } else if (p1 == null && p2 != null) {
                changedElements.put(toReplace, p2);
                changedElements.put(i, toReplace);
            } else if (p1 != null && p2 == null) {
                changedElements.put(i, p1);
                changedElements.put(toReplace, i);
            } else {
                changedElements.put(i, p1);
                changedElements.put(toReplace, p2);
            }
        }

        List<Integer> result = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            result.add(changedElements.get(i));
        }

        return result;
    }

    public static int nonuniformRandomNumberGeneration(List<Integer> values, List<Double> probabilities) {
        List<Double> prefixSumOfProbabilities = new ArrayList<>();
        prefixSumOfProbabilities.add(0.0);

        for (double p : probabilities)
            prefixSumOfProbabilities.add(prefixSumOfProbabilities.get(prefixSumOfProbabilities.size() - 1) + p);
        // above is the accumulation of probabilites and results a mapping relation to the items of that probability


        Random rand = new Random();
        final double unform1 = rand.nextDouble();

        int it = Collections.binarySearch(prefixSumOfProbabilities, unform1);

        if (it < 0) {
            final int intervalIdx = (Math.abs(it) - 1) - 1;
            return values.get(intervalIdx);
        } else {
            return values.get(it);
        }
    }

    public static boolean isValidSudoku(List<List<Integer>> partialAssignment) {
        for (int i = 0; i < partialAssignment.size(); i++) {
            if (hasDuplicate(partialAssignment, i, i+1, 0, partialAssignment.size()))
                return false;
        }

        for (int i = 0; i < partialAssignment.size(); i++) {
            if (hasDuplicate(partialAssignment, 0, partialAssignment.size(), i, i + 1))
                return false;
        }

        int regionSize = (int) Math.sqrt(partialAssignment.size());

        // a little tricky to think about region checking, for small regions, we only need to check every
        // element in one go, without having to check col and rows separately.
        for (int i = 0; i < regionSize; i++) {
            for (int j = 0; j < regionSize; j++) {
                if (hasDuplicate(partialAssignment, regionSize * i, regionSize * (i + 1),
                                                    regionSize * j, regionSize * (j + 1)));
            }
        }
        return true;
    }

    private static boolean hasDuplicate(List<List<Integer>> partial, int startRow, int endRow, int startCol, int endCol) {
        List<Boolean> isPresent = new ArrayList<>(Collections.nCopies(partial.size() + 1, false));

        for (int i = startRow; i < endRow; i++) {
            for (int j = startCol; j < endCol; j++) {
                if (partial.get(i).get(j) != 0 && isPresent.get(partial.get(i).get(j)))
                    return true;
                isPresent.set(partial.get(i).get(j), true);
            }
        }
        return false;
    }

    public static List<Integer> spiralOrder(List<List<Integer>> list) {
        List<Integer> result = new ArrayList<>();
        int i = 0, j = 0;

        // traverse right
        while(result.size() < list.size() * list.size()) {
            while (j < list.size() && list.get(i).get(j) != -1) {
                result.add(list.get(i).get(j));
                list.get(i).set(j, -1);
                j++;
            }
            j--;
            i++;
            System.out.println("right: " + i + j);
            // traverse down
            while (i < list.size() && list.get(i).get(j) != -1) {
                result.add(list.get(i).get(j));
                list.get(i).set(j, -1);
                i++;
            }
            i--;
            j--;
            System.out.println("down: " + i + j);
            // traverse left
            while (j >= 0 && list.get(i).get(j) != -1) {
                result.add(list.get(i).get(j));
                list.get(i).set(j, -1);
                j--;
            }
            j++;
            i--;
            System.out.println("left: " + i + j);
            // traverse up
            while (i >= 0 && list.get(i).get(j) != -1) {
                result.add(list.get(i).get(j));
                list.get(i).set(j, -1);
                i--;
            }
            i++;
            j++;
            System.out.println("up: " + i + j);
            //break;
        }

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
        /*
        Integer[] a = new Integer[] {1,2,3,4,5,6};
        Integer[] b = new Integer[] {6,5,4,3,2,1};
        List<Integer> A = Arrays.asList(a);
        List<Integer> B = Arrays.asList(b);

        for (int e : multiply(A, B))
            System.out.print(e);
        System.out.println();
        */

        //Integer[] game = new Integer[] {1,1,1,1,2,1,2,2,2,2,2,2};
        //System.out.println(findLongestSubarray(Arrays.asList(game)));

        //applyPermutation(Arrays.asList(new Integer[] {2,0,1, 3}),
        //        Arrays.asList(new Integer[] {1,2,3,4}));

        /*
        List<Integer> a = Arrays.asList(new Integer[] {0,1,2,3});
        while (!a.isEmpty()) {
            for (int b : a) {
                System.out.print(b);
            }
            System.out.println();
            a = nextPermutation(a);
        }
        */

        /*
        for (int i = 0; i < 5; i++) {
            List<Integer> l = computeRandomPermuatation(10);
            System.out.println(l.toString());
        }
        */

        List<List<Integer>> list = Arrays.asList(
                Arrays.asList(1,2,3,4),
                Arrays.asList(5,6,7,8),
                Arrays.asList(9,10,11,12),
                Arrays.asList(13,14,15,16));


        List<List<Integer>> list1 = Arrays.asList(
                Arrays.asList(1,2,3),
                Arrays.asList(4,5,6),
                Arrays.asList(7,8,9));

        System.out.println(spiralOrder(list).toString());
        System.out.println(spiralOrder(list1).toString());


    }


}

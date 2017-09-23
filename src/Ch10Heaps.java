import java.util.*;

public class Ch10Heaps {

    public static class StringComparator implements Comparator<String> {
        public int compare(String s1, String s2) {
            return Integer.compare(s1.length(), s2.length());
        }

    }

    public static List<String> topK(int k, Iterator<String> iter) {
        PriorityQueue<String> minHeap =
                new PriorityQueue<>(k, new StringComparator());

        while(iter.hasNext()) {
            minHeap.add(iter.next());
            if (minHeap.size() > k) minHeap.poll();
        }

        return new ArrayList<>(minHeap); // give a collections, and it will generate an Arraylist
    }

    private static class ArrayEntry {
        public Integer value;
        public Integer arraryId;

        public ArrayEntry (Integer value, Integer arraryId) {
            this.value = value;
            this.arraryId = arraryId;
        }
    }

    public static List<Integer> mergeSortedArrays (List<List<Integer>> sortedArrays) {
        List<Iterator<Integer>> iters = new ArrayList<>();
        for (List<Integer> array : sortedArrays)
            iters.add(array.iterator());

        PriorityQueue<ArrayEntry> minHeap = new PriorityQueue<>(sortedArrays.size(),
                new Comparator<ArrayEntry>() {
            public int compare(ArrayEntry a1, ArrayEntry a2) {
                return Integer.compare(a1.value, a2.value);
            }});

        for (int i = 0; i < sortedArrays.size(); i++) {
            if (iters.get(i).hasNext())
                minHeap.add(new ArrayEntry(iters.get(i).next(), i));
        }

        List<Integer> result = new ArrayList<>();
        // the moral of the stroy is that we only use extra k memory, k, number of files
        // and also, time is nlog(k)
        while(!minHeap.isEmpty()) {
            ArrayEntry headEntry = minHeap.poll();
            result.add(headEntry.value);
            if (iters.get(headEntry.arraryId).hasNext()) {
                minHeap.add(new ArrayEntry(iters.get(headEntry.arraryId).next(), headEntry.arraryId));
            }
        }

        return result;
    }


    private static enum SubarrayType {INCREASING, DECREASING}

    public static List<Integer> sortKthIncreasingDecreasingArray(List<Integer> A) {
        List<List<Integer>> sortedSubArrays = new ArrayList<>();
        SubarrayType subarrayType = SubarrayType.INCREASING;
        int startIdx = 0;
        for (int i = 1; i <= A.size(); i++) {
            if (i == A.size() || (A.get(i - 1) < A.get(i) && subarrayType == SubarrayType.DECREASING)
                              || (A.get(i) > A.get(i - 1) && subarrayType == SubarrayType.INCREASING)) {
                List<Integer> subList = A.subList(startIdx, i); // sublist i, exclusive
                if (subarrayType == SubarrayType.DECREASING) {
                    Collections.reverse(subList);
                }
                sortedSubArrays.add(subList);
                subarrayType = (subarrayType == SubarrayType.DECREASING
                                                ? SubarrayType.INCREASING
                                                : SubarrayType.DECREASING);
            }
        }

        return mergeSortedArrays(sortedSubArrays);
    }

    // a partially sorted array with a length of K, the moral of the story is that, we can sort it with
    // nlog(k) instead of nlog(n), and if k << n, then we can improve the performance
    public static void sortApproximatelySortedData(Iterator<Integer> sequence, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // default priority queue is minHeap, natural order

        for (int i = 0; i < k && sequence.hasNext(); i++) {
            minHeap.add(sequence.next());
        }

        while(sequence.hasNext()) {
            minHeap.add(sequence.next());
            Integer smallest = minHeap.remove(); // remove head, kinda like poll()
            System.out.println(smallest);
        }

        while(!minHeap.isEmpty()) System.out.println(minHeap.remove());
    }

    public static class Star implements Comparable<Star> {
        private double x, y, z;

        public Star (double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        private double distance() {
            return Math.sqrt(x*x + y*y + z*z);
        }

        @Override
        public int compareTo(Star other) {
            return Double.compare(this.distance(), other.distance());
        }
    }

    public static List<Star> findClosestKStars(int k, Iterator<Star> stars) {
        PriorityQueue<Star> maxHeap = new PriorityQueue<>(k, Collections.reverseOrder());
        while(stars.hasNext()) {
            maxHeap.add(stars.next());
            if (maxHeap.size() == k + 1) maxHeap.remove();
        }

        List<Star> result = new ArrayList<>();
        while(!maxHeap.isEmpty()) result.add(maxHeap.remove());
        Collections.sort(result);
        return result;
    }

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    public static void onlineMedian(Iterator<Integer> sequence) {
        // this one is actually supper cool
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(DEFAULT_INITIAL_CAPACITY,Collections.reverseOrder());
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(DEFAULT_INITIAL_CAPACITY);

        while(sequence.hasNext()) {
            minHeap.add(sequence.next());

            if (minHeap.size() - maxHeap.size() >= 2) {
                maxHeap.add(minHeap.remove());
            }
            double Median = minHeap.size() == maxHeap.size()
                    ? (double) ((minHeap.peek() + maxHeap.peek())) / 2.0
                    : (minHeap.peek());
            System.out.println("Median: " + Median);
        }

    }

    private static class HeapEntry {
        public Integer index;
        public Integer value;

        public HeapEntry(Integer index, Integer value) {
            this.index = index;
            this.value = value;
        }
    }


    private static class Compare implements Comparator<HeapEntry> {
        @Override
        public int compare(HeapEntry o1, HeapEntry o2) {
            return Integer.compare(o1.value, o2.value);
        }

        public static final Compare COMPARE_HEAP_ENTRIES = new Compare(); // actually kind of a cool tricks
    }

    // a heap for heap, a heap within another heap
    public static List<Integer> KLargestInBinaryHeap(List<Integer> A, int k) {
        // A is the list representation of a maxHeap
        if (k <= 0) return Collections.EMPTY_LIST;

        PriorityQueue<HeapEntry> candidateMaxHeap = new PriorityQueue<>(
                DEFAULT_INITIAL_CAPACITY, Compare.COMPARE_HEAP_ENTRIES);
        candidateMaxHeap.add(new HeapEntry(0, A.get(0)));
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            Integer candidiateIdx = candidateMaxHeap.peek().index;
            result.add(candidateMaxHeap.remove().value);

            // the problem is that we only know that the upper level is greater or equal compares to the lower ones
            // by introducing this heap thing, we can differentiate between left and right
            Integer left = candidiateIdx * 2 + 1; // zero based index
            if (left < A.size()) candidateMaxHeap.add(new HeapEntry(left, A.get(left)));
            Integer right = candidiateIdx * 2 + 2;
            if (right < A.size()) candidateMaxHeap.add(new HeapEntry(right, A.get(right)));
        }

        return result;
    }

    private static class ValueWithRank {
        public Integer value;
        public Integer rank;

        public ValueWithRank(Integer value, Integer rank) {
            this.value = value;
            this.rank = rank;
        }
    }


    public static class Compare1 implements Comparator<ValueWithRank> {
        @Override
        public int compare(ValueWithRank o1, ValueWithRank o2) {
            return Integer.compare(o2.rank, o1.rank); // this is the reverse maxHeap order
        }

        public static final Compare1 COMPARE_VALUEWITHRANK = new Compare1();
    }

    public static class StackUsingHeap {
        private int timeStamp = 0;

        private PriorityQueue<ValueWithRank> maxHeap = new PriorityQueue<>(Compare1.COMPARE_VALUEWITHRANK);

        public void push(Integer x) {
            maxHeap.add(new ValueWithRank(x, timeStamp++));
        }

        public Integer pop() {
            return maxHeap.remove().value;
        }

        public Integer peek() {
            return maxHeap.peek().value;
        }
    }

    public static void main(String[] args) {
        List<Integer> runningMedian = Arrays.asList(1,0,3,5,2,0,1);
        onlineMedian(runningMedian.iterator());
    }


}

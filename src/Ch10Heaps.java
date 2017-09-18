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
}

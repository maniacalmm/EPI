import java.util.*;

public class Ch12Hashing {

    public static int stringHash(String s, int modulus) {
        int kMult = 997;
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            val = (val * kMult + c) % modulus;
        }

        return val;
    }

    public static List<List<String>> findAnagram(List<String> dictionary) {
        Map<String, List<String>> sortedStringToAnagrams = new HashMap<>();

        for (String s : dictionary) {
            char[] charTmp = s.toCharArray();
            Arrays.sort(charTmp);
            String sortedCharArray = new String(charTmp);
            if (sortedStringToAnagrams.containsKey(sortedCharArray)) {
                sortedStringToAnagrams.put(sortedCharArray, new ArrayList<String>());
            }
            sortedStringToAnagrams.get(sortedCharArray).add(s);
        }

        List<List<String>> result = new ArrayList<>();

        for (List<String> s : sortedStringToAnagrams.values()) {
            if (s.size() > 1)
                result.add(s);
        }

        return result;
    }

    public static List<ContactList> mergeContactLists(List<ContactList> contacts) {
        return new ArrayList<>(new HashSet(contacts));
    }

    public static class ContactList {
        public List<String> names;

        ContactList(List<String> names) {
            this.names = names;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof ContactList)) {
                return false;
            }

            // many weird code
            return this == obj
                    ? true
                    : new HashSet(names).equals(new HashSet(((ContactList) obj).names));
        }

        @Override
        public int hashCode() {
            return new HashSet(names).hashCode();
        }
    }

    public static boolean canFormPalindrome (String s) {
        // neat code;
        Set<Character> charsWithOddFrequency = new HashSet<>();

        for (int i = 0; i < s.length(); i++) {
            if (charsWithOddFrequency.contains(s.charAt(i))) charsWithOddFrequency.remove(s.charAt(i));
            else charsWithOddFrequency.add(s.charAt(i));
        }

        return charsWithOddFrequency.size() <= 1;
    }

    public static boolean isLetterConstrcutibleFromMagazine(String letterText, String MagText) {
        Map<Character, Integer> charFrequencyForLetter = new HashMap<>();

        for (int i = 0; i < letterText.length(); i++) {
            Character c = letterText.charAt(i);
            if (charFrequencyForLetter.containsKey(c))
                charFrequencyForLetter.put(c, charFrequencyForLetter.get(c) + 1);
            else
                charFrequencyForLetter.put(c, 1);
        }

        for (char c : MagText.toCharArray()) {
            if (charFrequencyForLetter.containsKey(c)) {
                charFrequencyForLetter.put(c, charFrequencyForLetter.get(c) - 1);
                if (charFrequencyForLetter.get(c) == 0) {
                    charFrequencyForLetter.remove(c);
                    if (charFrequencyForLetter.isEmpty())
                        break;
                }
            }
        }

        return charFrequencyForLetter.isEmpty();
    }

    public class LRUCache { // LRU --> least recently used
        // i do not quiet get this problem
        LinkedHashMap<Integer, Integer> isbnToPrice;

        LRUCache(final int capacity) {
            this.isbnToPrice = new LinkedHashMap<Integer, Integer>(capacity, 1.0f, true) {
                @Override
                // reserve the latest insertion
                protected boolean removeEldestEntry(Map.Entry<Integer, Integer> e) {
                    return this.size() > capacity;
                }
            };
        }

        public Integer lookup(Integer key) {
            if (!isbnToPrice.containsValue(key))
                return null;
            return isbnToPrice.get(key);
        }

        public void insert(Integer key, Integer value) {
            isbnToPrice.get(key); // what is point of this?
            if(!isbnToPrice.containsValue(key)) // we don't update
                isbnToPrice.put(key, value);
        }

        public Boolean erase(Object key) {
            return isbnToPrice.remove(key) != null;
        }
    }

    public static Ch9BinaryTree.BinaryTree<Integer>  LCA(
            Ch9BinaryTree.BinaryTree<Integer> node0,
            Ch9BinaryTree.BinaryTree<Integer> node1) {
        Set<Ch9BinaryTree.BinaryTree<Integer>> hash = new HashSet<>();

        while(node0 != null || node1 != null) {
            if (node0 != null) {
                if (!hash.add(node0)) // if node0 is already in hash, return false
                    return node0;
                node0 = node0.parent;
            }

            // in this case, the longest distance node climbed is the
            // lowest node to LCA
            if (node1 != null) {
                if (!hash.add(node1))
                    return node1;
                node1 = node1.parent;
            }
        }
       // Map<String, Integer> a = new HashMap<>();

        throw new IllegalArgumentException("node1 and node2 not in the same tree");
    }

    public static class MapCompare implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
           return Integer.compare(o1.getValue(), o2.getValue());
        }

        public static final MapCompare MAP_COMPARE_BY_VALUE = new MapCompare();
    }

    public static List<String> kMostFrequent(List<String> stringPoll, int k) {
        Map<String, Integer> mp = new HashMap<>();
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(k, MapCompare.MAP_COMPARE_BY_VALUE);

        // this takes n time
        for (String s : stringPoll) {
            if (!mp.containsKey(s)) {
                mp.put(s, 1);
            } else {
                mp.put(s, mp.get(s) + 1);
            }
        }

        for (Map.Entry<String, Integer> ME : mp.entrySet()) {
            if (pq.size() < k) pq.add(ME);
            else {
                if (ME.getValue() > pq.peek().getValue()) {
                    pq.remove();
                    pq.add(ME);
                }
            }
        }

        List<String> result = new ArrayList<>();
        while(!pq.isEmpty()) {
            Map.Entry<String, Integer> ME = pq.remove();
            result.add("string: " + ME.getKey() + " count: " + ME.getValue());
        }

        Collections.reverse(result);

        return result;
    }

    public static int findNearestRepetition(List<String> paragrah) {
        Map<String, Integer> record = new HashMap<>();

        int idx = 0;
        int err = Integer.MAX_VALUE; // signed number
        for (String s : paragrah) {
            if (record.containsKey(s)) {
                if (idx - record.get(s) < err) err = idx - record.get(s);
            }
            record.put(s, idx++);
        }

        return err;
    }

    public static class Subarray {
        public Integer start;
        public Integer end;

        public Subarray(Integer start, Integer end) {
            this.start = start;
            this.end = end;
        }
    }

    public static Subarray findSmallestSubarrayCoveringSet(List<String> paragrah, Set<String> keywords) {
        // Implementation is a little tricky to follow, or maybe my energy level is just running low now...
        Map<String, Integer> keywordToCover = new HashMap<>();
        for (String keyword : keywords) {
            keywordToCover.put(keyword, keywordToCover.containsKey(keyword)?
                                        keywordToCover.get(keyword) + 1 : 1);
            // a set of keywords, every keyword should be distinctive, thus i'm not sure why they have that ? clause
        }

        Subarray result = new Subarray(-1, -1);

        int reminingToCover = keywords.size();
        // find the first subarray that covers all of the keywords
        for (int left = 0, right = 0; right < paragrah.size(); right++) {
            Integer keywordCount = keywordToCover.get(paragrah.get(right));
            if (keywordCount != null) {
                keywordToCover.put(paragrah.get(right), --keywordCount);
                if (keywordCount >= 0)
                    --reminingToCover;
            }


            while(reminingToCover == 0) {
                if ((result.start == -1 && result.end == -1) || right - left < result.end - result.start) {
                    result.start = left;
                    result.end = right;
                }

                keywordCount = keywordToCover.get(paragrah.get(left));

                if(keywordCount != null) {
                    keywordToCover.put(paragrah.get(left), ++keywordCount);
                    if (keywordCount >= 0) {
                        ++reminingToCover; // because we are going to left++, thus, leaving the covered range
                    }
                }

                left++;
            }

        }

        return result;

    }


    public static int longestContainedRange(List<Integer> A) {
        Set<Integer> unprocessedEntries = new HashSet<>(A); // nice trick

        int maxIntervalSize = 0;
        while(!unprocessedEntries.isEmpty()) {

            // this is another trick
            // we are iterative while updating at the same time
            int a = unprocessedEntries.iterator().next();

            unprocessedEntries.remove(a);

            int lowerBound = a - 1;
            while(unprocessedEntries.contains(lowerBound)) {
                unprocessedEntries.remove(lowerBound);
                lowerBound--;
            }

            int upperBound = a + 1;
            while (unprocessedEntries.contains(upperBound)) {
                unprocessedEntries.remove(upperBound);
                upperBound++;
            }
            int err = upperBound - lowerBound - 1;
            maxIntervalSize = err > maxIntervalSize ? err : maxIntervalSize;
        }

        return maxIntervalSize;
    }




    public static void main(String[] args) {
        /*
        LinkedHashMap<Integer, Integer> haha = new LinkedHashMap<>();
        haha.put(1,2);

        System.out.println(haha.remove(0));
        */

        /*
        List<String> test = Arrays.asList("a", "b", "c", "d", "a", "b","a", "e", "e");
        System.out.println(kMostFrequent(test, 3));
        */


        List<String> test = Arrays.asList("All", "work", "and", "no", "play", "makes","for", "no", "work",
                                            "no", "fun", "and", "no", "result");

        System.out.println(findNearestRepetition(test));
    }

}

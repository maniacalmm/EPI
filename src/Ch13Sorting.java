import sun.awt.image.ImageWatched;

import java.util.*;

public class Ch13Sorting {

    public static class Student implements Comparable<Student> {
        public String name;
        public Double GPA;

        @Override // now the order of name, is the natural order of Student class
        public int compareTo(Student o) {
            return name.compareTo(o.name);
        }

        Student(String name, double GPA) {
            this.name = name;
            this.GPA = GPA;
        }
    }

    public static void sortByName(List<Student> studentList) {
        Collections.sort(studentList);
    }

    public static void sortByGPA(List<Student> studentList) {
        Collections.sort(studentList, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return Double.compare(o2.GPA, o1.GPA); // reverse order here
            }
        });
    }

    //13.1
    public static List<Integer> intersectTwoSortedArrays_Binary(List<Integer> A, List<Integer> B) {
        // A and B are already sorted;
        // use binary search to quickly examine the elements, better than brute-force search O(mn)
        // this way, we can get nlog(m) or mlog(n);
        List<Integer> intersectionAB = new ArrayList<>();

        for (int i = 0; i < A.size(); i++) {
            if ((i == 0 || A.get(i) != A.get(i - 1)) && Collections.binarySearch(B, A.get(i)) >= 0)
                intersectionAB.add(A.get(i));
        }

        return intersectionAB;

    }

    public static List<Integer> intersectTwoSortedArrays(List<Integer> A, List<Integer> B) {
        // O(m + n)
        int iA = 0, iB = 0;
        List<Integer> result = new ArrayList<>();

        while(iA < A.size() && iB < B.size()) {
            if (A.get(iA) < B.get(iB)) {
                iA++;
            } else if (A.get(iA) > B.get(iB)) {
                iB++;
            } else {
                if (iA == 0 || A.get(iA) != A.get(iA - 1)) {
                    result.add(A.get(iA));
                }
                iA++;
                iB++;
            }
        }

        return result;
    }


    public static void mergeTWoSrotedArrays(List<Integer> A, int m, List<Integer> B, int n) {
        // O(m + n) time, O(1) space
        int a  = m - 1;
        int b = n - 1;
        int writeIdx = m + n - 1;
        while(a > 0 && b > 0) {
            // A is the longer one
            A.set(writeIdx--, A.get(a) > B.get(b) ? A.get(a--) : B.get(b--));
        }

        while(b >= 0) {
            A.set(writeIdx--, B.get(b--));
        }
    }

    public static class Event {
        public int start, finish;

        public Event(int start, int finish) {
            this.start = start;
            this.finish = finish;
        }
    }

    private static class Endpoint implements Comparable<Endpoint> {
        public int time;
        public boolean isStart;
        @Override
        public int compareTo(Endpoint o) {
            if (time != o.time) {
                return Integer.compare(time, o.time);
            }
            return isStart && !o.isStart ? -1 : !isStart && o.isStart ? 1 : 0;
        }

        Endpoint(int t, boolean is) {
            time = t;
            isStart = is;
        }
    }

    public static int findMaxSimulataneousEvents(List<Event> A) {
        // dead-on sweeping line algorithm
        List<Endpoint> E = new ArrayList<>();

        for (Event event : A) {
            E.add(new Endpoint(event.start, true));
            E.add(new Endpoint(event.finish, false));
        }

        Collections.sort(E);

        int maxNumSimultaneousEvents = 0, numSmultaneousEvents = 0;

        for (Endpoint endpoint : E) {
            if (endpoint.isStart) numSmultaneousEvents++;
            else if (!endpoint.isStart) numSmultaneousEvents--;

            if (numSmultaneousEvents > maxNumSimultaneousEvents)
                maxNumSimultaneousEvents = numSmultaneousEvents;
        }

        return maxNumSimultaneousEvents;
    }

    private static class Interval {
        public int left, right;

        public Interval(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }

    public static List<Interval> addInterval(List<Interval> disjointIntervals, Interval newInterval) {
        List<Interval> result = new ArrayList<>();

        int idx = 0;
        for (Interval iteva : disjointIntervals) {
            if (iteva.right < newInterval.left) { // totally at left
                result.add(iteva);
            } else if (iteva.right >= newInterval.left && iteva.left <= newInterval.left) { // intersection
                newInterval.left = Math.min(iteva.left, newInterval.left);
                newInterval.right = Math.max(iteva.right, newInterval.right);
            } else {
                break;
            }
            idx++;
        }

        result.add(newInterval);

        for (int i = idx; i < disjointIntervals.size(); i++) {
            result.add(disjointIntervals.get(i));
        }

        return result;
    }

    private static class IntervalOC implements Comparable<IntervalOC> {
        public EndpointOC left = new EndpointOC();
        public EndpointOC right = new EndpointOC();

        private static class EndpointOC {
            public boolean isClosed;
            public int val;
        }

        @Override
        public int compareTo(IntervalOC o) {
            if (Integer.compare(left.val, o.left.val) != 0)
                return left.val - o.left.val;

            if (left.isClosed && !o.left.isClosed)
                return -1;

            return (!left.isClosed && o.left.isClosed) ? 1 : 0;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof Interval))
                return false;

            if (this == obj) return true;

            IntervalOC that = (IntervalOC) obj;
            return left.val == that.left.val && left.isClosed == that.left.isClosed;
        }

        @Override
        public int hashCode() {
            return Objects.hash(left.val, left.isClosed);
        }
    }

    public static List<IntervalOC> unionOfIntervals(List<IntervalOC> intervals) {
        if (intervals.isEmpty()) return Collections.EMPTY_LIST;

        Collections.sort(intervals);

        List<IntervalOC> result = new ArrayList<>(Arrays.asList(intervals.get(0)));

        for (IntervalOC i : intervals) {
            if (!result.isEmpty()
                    && (i.left.val < result.get(result.size() - 1).right.val
                        || (i.left.val == result.get(result.size() - 1).right.val
                            && (i.left.isClosed
                                || result.get(result.size() - 1).right.isClosed)))) {
                if (i.right.val > result.get(result.size() - 1).right.val
                        || (i.right.val == result.get(result.size() - 1).right.val
                            && i.right.isClosed)) {
                    result.get(result.size() - 1).right = i.right;
                }
            } else {
                result.add(i);
            }
        }

        return result;
    }

    private static class Person {
        public Integer age;
        public String name;

        public Person(Integer k, String n) {
            age = k;
            name = n;
        }

        public String toString() {
            return name + " " + age;
        }
    }

    public static void groupByAge (List<Person> people) {
        Map<Integer, Integer> ageToCount = new HashMap<>();
        for (Person p : people) {
            if (!ageToCount.containsKey(p.age)) {
                ageToCount.put(p.age, 1);
            } else {
                ageToCount.put(p.age, ageToCount.get(p.age) + 1);
            }
        }

        Map<Integer, Integer> position = new HashMap<>();

        int indx = 0;
        for (Map.Entry<Integer, Integer> entry : ageToCount.entrySet()) {
            position.put(entry.getKey(), indx);
            indx += entry.getValue();
        }

        System.out.println(position.size());
        System.out.println(ageToCount.size());

        int iter = 0;
        while(!position.isEmpty()) {
            Map.Entry<Integer, Integer> from = position.entrySet().iterator().next(); // this is actually a very neat trick
            Integer toAge = people.get(from.getValue()).age;
            Integer toValue = position.get(toAge);

            Collections.swap(people, from.getValue(), toValue);
            Integer count = ageToCount.get(toAge) - 1;
            ageToCount.put(toAge, count);

            if (count > 0) position.put(toAge, toValue + 1);
            else position.remove(toAge);
        }

        System.out.println(people);
    }

    public static Ch7LinkedList.ListNode<Integer> insertionSort(final Ch7LinkedList.ListNode<Integer> L) {
        Ch7LinkedList.ListNode<Integer> dummy = new Ch7LinkedList.ListNode<>(0, L);
        Ch7LinkedList.ListNode<Integer> iter = L;

        while (iter != null && iter.next != null) {
            if (iter.data > iter.next.data) { // we find a node that's larger than the next one
                Ch7LinkedList.ListNode<Integer> target = iter.next, pre = dummy;
                // every advance, we iterate through the whole thing
                while(pre.next.data < target.next.data) pre = pre.next; // find the place we need to insert

                // switching operation
                Ch7LinkedList.ListNode<Integer> tmp = pre.next;
                pre.next = target;
                iter.next = target.next;
                target.next = tmp;
            } else {
                iter = iter.next;
            }
        }

        return dummy.next;
    }

    public static Ch7LinkedList.ListNode<Integer> stableSortList(Ch7LinkedList.ListNode<Integer> L) {
        if (L == null || L.next == null) return L;

        Ch7LinkedList.ListNode<Integer> fast = L, slow = L, preSlow = null;

        while(fast != null && fast.next != null) {
            preSlow = slow;
            fast = fast.next.next;
            slow = slow.next;
        }

        preSlow.next = null;
        //System.out.println("1st: " + L.data + " 2st: " + slow.data);

        return mergeTwoList(stableSortList(L), stableSortList(slow));


    }

    private static Ch7LinkedList.ListNode<Integer> mergeTwoList(Ch7LinkedList.ListNode<Integer> L1,
                                                                Ch7LinkedList.ListNode<Integer> L2) {
        Ch7LinkedList.ListNode<Integer> dummy = new Ch7LinkedList.ListNode<>(0, null);

        Ch7LinkedList.ListNode<Integer> iter1 = L1, iter2 = L2, iter = dummy;

        while(iter1 != null && iter2 != null) {
            if (iter1.data < iter2.data) {
                iter.next = iter1;
                iter1 = iter1.next;
            } else {
                iter.next = iter2;
                iter2 = iter2.next;
            }

            iter = iter.next;
        }

        iter.next = iter1 == null ? iter2 : iter1;

        return dummy.next;
    }

    private static void printList(Ch7LinkedList.ListNode<Integer> L) {
        Ch7LinkedList.ListNode<Integer> iter = L ;
        while(iter != null) {
            System.out.print(iter.data + " ");
            iter = iter.next;
        }
        System.out.println();

    }

    public static void main(String[] args) {
        /*
        List<Integer> A = Arrays.asList(2,3,3,5,7,11);
        List<Integer> B = Arrays.asList(3,3,7,15,31);

        System.out.println(intersectTwoSortedArrays(A, B));
        */

        /*
        List<Person> p = Arrays.asList(new Person( 14, "greg")
                                    ,  new Person(12, "john"),
                                        new Person(11, "andy"),
                                        new Person(13, "jim"),
                                        new Person(12, "Phil"),
                                        new Person(13, "bob"),
                                        new Person(13, "Chip"),
                                        new Person(14, "tim"));

        groupByAge(p);
        */

        Ch7LinkedList.ListNode<Integer> L = new Ch7LinkedList.ListNode<>(5,
                                            new Ch7LinkedList.ListNode<>(8,
                                                    new Ch7LinkedList.ListNode<>(4,
                                                            new Ch7LinkedList.ListNode<>(10,
                                                                    new Ch7LinkedList.ListNode<>(2,
                                                                            new Ch7LinkedList.ListNode<>(9, null))))));
        printList(L);
        Ch7LinkedList.ListNode<Integer> LL = stableSortList(L);
        printList(LL);

    }

}

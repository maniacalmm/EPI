import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ch7LinkedList {

    public static class ListNode<T> {
        public T data;
        public ListNode<T> next;

        public ListNode(T data, ListNode<T> next) {
            this.data = data;
            this.next = next;
        }
    }

    public static ListNode<Integer> mergeTwoSortedLists(ListNode<Integer> L1, ListNode<Integer> L2) {
        ListNode<Integer> sentinel = new ListNode<>(0, null);
        ListNode<Integer> current = sentinel;
        ListNode<Integer> p1 = L1, p2 = L2;

        while(p1 != null && p2 != null) {
            if (p1.data < p2.data) {
                current.next = p1;
                current = current.next;
                p1 = p1.next;
             } else {
                current.next = p2;
                current = current.next;
                p2 = p2.next;
            }
        }

        if (p1 == null) current.next = p2;
        else current.next = p1;
        return sentinel.next;
    }

    public static ListNode<Integer> reverseSublist(ListNode<Integer> L, int start, int finish) {
        ListNode<Integer> dummyHead = new ListNode<>(0, L);
        ListNode<Integer> Head = dummyHead;

        int k = 1;
        while(k < start) {
            Head = Head.next;
            k++;
        }

        ListNode<Integer> Iter = Head.next; // Head is actually the node before what we want to reverse
                                            // Iter is the actual head

        while(start++ < finish) {
            ListNode<Integer> tmp = Iter.next;
            Iter.next = tmp.next;
            tmp.next = Head.next;
            Head.next = tmp;
        }

        return dummyHead.next;
    }

    public static ListNode<Integer> hasCycle(ListNode<Integer> head) {
        ListNode<Integer> fast = head, slow = head;

        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                int cycleLen = 0;
                do {
                    cycleLen++;
                    fast = fast.next;
                } while(fast != slow);

                ListNode<Integer> cycleLenIter = head;
                while(cycleLen > 0) {
                    cycleLenIter = cycleLenIter.next;
                    cycleLen--;
                }

                ListNode<Integer> iter = head;
                while(iter != cycleLenIter) {
                    iter = iter.next;
                    cycleLenIter = cycleLenIter.next;
                }

                return iter;
            }
        }

        return null;
    }

    public static ListNode<Integer> overlappingNoCycle(ListNode<Integer> L1, ListNode<Integer> L2) {
        int L1len = 0, L2len = 0;
        ListNode<Integer> head1 = L1, head2 = L2;

        while(head1 != null) {
            L1len++;
            head1 = head1.next;
        }

        while(head2 != null) {
            L2len++;
            head2 = head2.next;
        }

        head1 = L1; head2 = L2;
        if (L1len > L2len) {
            int diff = L1len - L2len;
            while(diff-- > 0) head1 = head1.next;
        } else {
            int diff = L2len - L1len;
            while(diff-- > 0) head2 = head2.next;
        }

        while(head1 != head2) {
            head1 = head1.next;
            head2 = head2.next;
        }

        return head1;
    }


    public static void deletionFormList(ListNode<Integer> nodeToDelete) {
        ListNode<Integer> nextNode = nodeToDelete.next;
        nodeToDelete.data = nextNode.data;
        nodeToDelete.next = nextNode.next;
    }

    public static void main(String[] args) {
        List<Integer> test = Arrays.asList(1,2,3,4,5);
        List<Integer> test1 = new ArrayList<>();
        test1.add(5);
        System.out.println(test1);
    }
}

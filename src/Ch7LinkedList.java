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

    public static ListNode<Integer> removeKthLast(ListNode<Integer> L, int k) {
        ListNode<Integer> dummyHead = new ListNode<>(0, L);
        ListNode<Integer> first = dummyHead;

        while(k-- > 0)
            first = first.next;

        ListNode<Integer> second = dummyHead;
        while(first != null) {
            first = first.next;
            second = second.next;
        }

        second.next = second.next.next; // deletion in action
        return second;
    }

    public static ListNode<Integer> removeDuplicates(ListNode<Integer> L) {
        ListNode<Integer> iter = L;

        while(iter != null) {
            if (iter.next != null && iter.data == iter.next.data)
                iter.next = iter.next.next;
            else {
                iter = iter.next;
            }
        }
        return L;
    }

    public static ListNode<Integer> cyclicRightShift(ListNode<Integer> L, int k) {
        if (L == null)
            return L;

        ListNode<Integer> tail = L;
        int n  = 1;
        while(tail.next != null) {
            n++;
            tail = tail.next;
        }

        k %= n;
        if (k == 0) return L;

        tail.next = L.next;
        int stepsToNewHead = n - k;
        ListNode<Integer> newTail = tail;
        while(stepsToNewHead-- > 0)
            newTail = newTail.next;

        ListNode<Integer> newHead = newTail.next;
        newTail.next = null;

        return newHead;
    }

    public static ListNode<Integer> evenOddMerge(ListNode<Integer> L) {
        if (L == null) return L;
        ListNode<Integer> even  = L.next;
        ListNode<Integer> odd = even.next;
        ListNode<Integer> evenIter = even;
        ListNode<Integer> oddIter = odd;

        while(evenIter.next != null && oddIter.next != null) {
            if (evenIter.next != null && evenIter.next.next != null) {
                evenIter.next = evenIter.next.next;
                evenIter = evenIter.next;
            }
            if (oddIter.next != null && oddIter.next.next != null) {
                oddIter.next = oddIter.next.next;
                oddIter = oddIter.next;
            }

            System.out.println("even: " + evenIter.data + " odd: " + oddIter.data);
        }

        evenIter.next = odd;
        oddIter.next = null;
        return L;
    }

    public static ListNode<Integer> evenOddMergebook(ListNode<Integer> L) {
        if (L == null) return L;

        ListNode<Integer> evenDummy = new ListNode<>(0, null),
                          oddDummy = new ListNode<>(0, null);

        List<ListNode<Integer>> tails = Arrays.asList(evenDummy, oddDummy);
        // this can be thought as java's version of tuple

        int turn = 0;
        for (ListNode<Integer> iter = L; iter != null; iter = iter.next) {
            tails.get(turn).next = iter;
            tails.set(turn, tails.get(turn).next);
            turn = turn ^ 1;
        }

        tails.get(1).next = null;
        tails.get(0).next = oddDummy.next;

        return evenDummy.next;
    }

    public static boolean isLinkedListAPalindrome(ListNode<Integer> L) {
        if (L == null) return false;

        int len = 1;
        ListNode<Integer> dum = L;
        ListNode<Integer> iter = L;
        for (ListNode<Integer> d = L; d.next != null; d = d.next, len++);

        if(len == 1) return true;
        if (len == 2) return L.data == L.next.data;
        if (len == 3) return L.data == L.next.next.data;

        len = len % 2 == 1? len / 2 + 1 : len / 2;
        while(len-- > 1) iter = iter.next;

        iter.next = reverseLinkedList(iter.next);

        for (ListNode<Integer> see = L; see != null; see = see.next)
            System.out.println(see.data);

        ListNode<Integer> first = L;
        iter = iter.next;
        while(iter.next != null) {
            if (first.data != iter.data) return false;

            first = first.next;
            iter = iter.next;
        }

        return true;
    }

    private static ListNode<Integer> reverseLinkedList(ListNode<Integer> L) {
        ListNode<Integer> dummy = new ListNode<>(0, L); // sentinel node
        ListNode<Integer> head = L;
        while(head.next != null) {
            ListNode<Integer> tmp = head.next;
            head.next = tmp.next;
            tmp.next = dummy.next;
            dummy.next = tmp;
        }

        return dummy.next;
    }


    public static boolean isLikedListAPalindromeBook(ListNode<Integer> L) {
        ListNode<Integer> slow = L, fast = L;

        while(fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        ListNode<Integer> first = L;
        ListNode<Integer> second = reverseLinkedList(slow.next);

        while(first != null && second != null) {
            if (first.data != second.data)
                return false;

            first = first.next;
            second = second.next;
        }

        return true;
    }

    public static void main(String[] args) {
        /*
        List<Integer> test = Arrays.asList(1,2,3,4,5);
        List<Integer> test1 = new ArrayList<>();
        test1.add(5);
        System.out.println(test1);
        */

        ListNode<Integer> t = new ListNode<>(3, new ListNode<>(1,new ListNode<>(2,
                new ListNode<>(1, new ListNode<>(3,null)))));
        ListNode<Integer> L = new ListNode<>(-1, t);

        /*
        for (ListNode<Integer> dum = L; dum != null; dum = dum.next)
            System.out.println(dum.data);
            */

        //ListNode<Integer> t1  = evenOddMerge(L);

        /*
        ListNode<Integer> t1  = evenOddMergebook(t);

        for (ListNode<Integer> dum = t1; dum != null; dum = dum.next)
            System.out.println(dum.data);
        */

        //System.out.println(isLinkedListAPalindrome(t));
        System.out.println(isLikedListAPalindromeBook(t));
    }
}

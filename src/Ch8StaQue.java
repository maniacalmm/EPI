import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class Ch8StaQue {
    // this is a queue example
    public static void printLinkedListInReverse(Ch7LinkedList.ListNode<Integer> head) {
        Deque<Integer> nodes = new ArrayDeque<>();
        while (head != null) {
            nodes.push(head.data);
        }

        while(!nodes.isEmpty())
            System.out.println(nodes.poll());
    }

    private static class ElementWithCachedMax {
        public Integer element;
        public Integer max; // caching

        public ElementWithCachedMax(Integer Element, Integer max) {
            this.element = element;
            this.max = max;
        }
    }

    public static class Stack {
        //store (element, cached max) pair
        private Deque<ElementWithCachedMax> elementWthCahedMax = new LinkedList<>();

        public boolean empty() {
            return elementWthCahedMax.isEmpty();
        }

        public Integer max() {
            if (empty()) throw new IllegalStateException("max(): empty stack");
            return elementWthCahedMax.peek().max;
        }

        public Integer pop() {
            if(empty()) throw new IllegalStateException("pop(): empty stack");
            return elementWthCahedMax.removeFirst().element;
        }

        public void push(Integer x) {
            elementWthCahedMax.addFirst(new ElementWithCachedMax(x, Math.max(x, empty()? x : max())));
        }
    }
}

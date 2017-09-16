import java.util.*;

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

    public static class StackVer1 {
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


    private static class MaxWithCount {
        public Integer max;
        public Integer count;

        public MaxWithCount(Integer max, Integer count) {
            this.max = max;
            this.count = count;
        }
    }

    public static class StackVer2 {
        private Deque<Integer> element = new ArrayDeque<>();
        private Deque<MaxWithCount> cachedMaxWithCount = new ArrayDeque<>();

        public boolean empty() {
            return element.isEmpty();
        }

        public Integer max() {
            if(empty()) throw new IllegalStateException("max(): empty stack");
            return cachedMaxWithCount.peekFirst().max;
        }

        public Integer pop() {
            if (empty()) throw new IllegalStateException("pop(): empty stack");

            Integer popped = element.pop();
            if (popped == cachedMaxWithCount.peek().max)
                cachedMaxWithCount.peek().count--;
            if (cachedMaxWithCount.peek().count == 0)
                cachedMaxWithCount.pop();
            return popped;
        }

        public void push(Integer x) {
            element.addLast(x);
            if (x.equals(cachedMaxWithCount.peek().max))
                cachedMaxWithCount.peek().count++;
            else if (Integer.compare(x, cachedMaxWithCount.peek().max) > 0)
                cachedMaxWithCount.addFirst(new MaxWithCount(x, 1));

        }

    }

    public static int eval(String RPNExpression) {
        Deque<Integer> valStack = new ArrayDeque<>();
        String deliminter = ",";
        String[] symbols = RPNExpression.split(deliminter);

        for (String element : symbols) {
            if (element.length() == 1 && "+-*/".contains(element)) { // make sure it's an operator
                int val1 = valStack.pop();
                int val2 = valStack.pop();
                switch (element) {
                    case "+":
                        valStack.push(val1 + val2);
                        break;
                    case "-":
                        valStack.push(val1 - val2);
                        break;
                    case "/":
                        valStack.push(val1 / val2);
                        break;
                    case "*":
                        valStack.push(val1 * val2);
                        break;
                    default:
                        throw new IllegalArgumentException("Malformed RPN at: " + element);
                }
            } else {    // token is a number
                valStack.push(Integer.parseInt(element));
            }
        }
        return valStack.pop();
    }


    public static boolean isWellFormed(String s) {
        Deque<Character> leftChars = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char token = s.charAt(i);
            if (token == '(' || token == '{' || token =='[') {
                leftChars.addFirst(token);
            } else {
                if (leftChars.isEmpty()) return false;

                if (token == ')' && leftChars.peekFirst() != '(') return false;
                if (token == '}' && leftChars.peekFirst() != '{') return false;
                if (token == ']' && leftChars.peekFirst() != '[') return false;

                leftChars.removeFirst();
            }
        }
        return leftChars.isEmpty(); // well formed ones should emptyed the stack
    }


    public static String shortestEquivalentPath(String path) {
        if (path.equals(""))
            throw new IllegalArgumentException("Empty string is not a legal path");

        Deque<String> pathNames = new ArrayDeque<>();

        if (path.startsWith("/")) // starts with "/", which is an absolute path
            pathNames.addFirst("/");

        String[] processed = path.split("/");

        // stacking tokens
        for (String token : processed) {
            System.out.println(token);
            if (token.equals("..")) {
                if (pathNames.isEmpty() || pathNames.peekFirst().equals("..")) {
                    // the only situation that peekFirst().equals("..") is that token starts with .. // thus, isEmpty()
                    pathNames.addFirst(token);
                } else {
                    if (pathNames.peek().equals("/"))
                        throw new IllegalArgumentException("Path error, try to go beyond root");
                    pathNames.removeFirst(); // pathName is not empty, and the previous one is not ..
                }

            } else if(!token.equals(".") && !token.isEmpty()) { // must be a name
                pathNames.addFirst(token);
            }
        }

        // rebuilding path from stack
        StringBuilder sb = new StringBuilder();
        if (!pathNames.isEmpty()) {
            Iterator<String> it = pathNames.descendingIterator();
            String prev = it.next();
            sb.append(prev);
            while(it.hasNext()) {
                if(!prev.equals("/"))
                    sb.append("/");
                prev = it.next();
                sb.append(prev);
            }
        }

        return sb.toString();
    }

    private class PostingListNode {
        Integer order = -1;
        PostingListNode nextNode;
        PostingListNode PositingNode;

        public PostingListNode(PostingListNode nextNode, PostingListNode PostingNode) {
            this.nextNode = nextNode;
            this.PositingNode = PostingNode;
        }
    }

    public static void setJumpOrder(PostingListNode L) {
        setJumpOrderHelper(L, 0);
    }

    private static int setJumpOrderHelper(PostingListNode L, int order) {
        if (L != null && L.order == -1) {
            L.order = order++;
            order  = setJumpOrderHelper(L.PositingNode, order);
            order  = setJumpOrderHelper(L.nextNode, order);
        }

        return order;
    }

    public static void setJumpOrderStack (PostingListNode L) {
        Deque<PostingListNode> s  = new ArrayDeque<>(); // BFS, mimicing a queue, but why the reverse order
        int order = 0;
        if (L != null) s.addLast(L);

        // addFirst and removeFirst could easily just do the same,
        // maybe they were just try to show a different flavor or something

        while(!s.isEmpty()) {
            PostingListNode curr = s.removeLast();
            if (curr.order == -1) {
                curr.order = order++;
                if (curr.nextNode != null)
                    s.addLast(curr.nextNode);
                if (curr.PositingNode != null)
                    s.addLast(curr.PositingNode);
            }
        }
    }

    public static class BuildingWithHeight {
        public Integer id;
        public Integer height;

        public BuildingWithHeight(Integer id, Integer height) {
            this.id = id;
            this.height = height;
        }

        // building are presented with a east-to-west
        public static Deque<BuildingWithHeight> examineBuildsWithSunset(Iterator<Integer> sequence) {
            int buildingIdx = 0;
            Deque<BuildingWithHeight> buildingWithSunset = new ArrayDeque<>();
            while(sequence.hasNext()) {
                Integer buildHeight = sequence.next();

                while(!buildingWithSunset.isEmpty() &&
                        (Integer.compare(buildHeight, buildingWithSunset.getLast().height)) > 0) {
                    buildingWithSunset.removeLast();
                }

                buildingWithSunset.addLast(new BuildingWithHeight(buildHeight++, buildHeight));
            }

            return buildingWithSunset;
        }
    }

    // Basically, deque is great for both stack and queue
    // is Linkedlist doubly linked in java?
    // poll and remove only different in, if queue is empty, poll return null, and remove throw exception

    public class QueueWithMaxIntro {
        private Deque<Integer> data = new ArrayDeque<>();
        public void enqueue (Integer x) {data.addLast(x);}
        public Integer dequeue() {return data.removeFirst();}

        public Integer max() {
            if (!data.isEmpty())
                return Collections.max(data); // n
            throw new IllegalStateException("cannot performe max() on empty queue");
        }
    }

    // Binary tree node
    public class BinaryTreeNode<T> {
        public T data;
        public BinaryTreeNode<T> left, right;
    }

    //Queue question 1, BFS
    public static List<List<Integer>> binaryTreeDepthOrder (BinaryTreeNode<Integer> tree) {
        Queue<BinaryTreeNode<Integer>> currDepthNodes = new LinkedList<BinaryTreeNode<Integer>>();
        currDepthNodes.add(tree);
        List<List<Integer>> result = new ArrayList<>();

        while(!currDepthNodes.isEmpty()) {
            Queue<BinaryTreeNode<Integer>> nextDepthNodes = new LinkedList<BinaryTreeNode<Integer>>();
            List<Integer> thisLevel = new ArrayList<>();
            while(!currDepthNodes.isEmpty()) {
                BinaryTreeNode<Integer> curr = currDepthNodes.poll();
                if (curr != null) {
                    thisLevel.add(curr.data);
                    nextDepthNodes.add(curr.left);
                    nextDepthNodes.add(curr.right);
                }
            }

            if(!thisLevel.isEmpty()) result.add(thisLevel);
            currDepthNodes = nextDepthNodes;
        }

        return result;
    }
    // identical to the above code
    /*
    public static List<List<Integer>> BFS (BinaryTreeNode<Integer> tree) {
        Queue<BinaryTreeNode<Integer>> currDepthNode = new LinkedList<>();
        currDepthNode.add(tree);
        List<List<Integer>> result = new ArrayList<>();

        while(!currDepthNode.isEmpty()) {
            Queue<BinaryTreeNode<Integer>> nextDepthNode = new LinkedList<>();
            List<Integer> thisLevel = new ArrayList<>();
            while (!currDepthNode.isEmpty()) {
                BinaryTreeNode<Integer> node = currDepthNode.poll();
                if (node != null) {
                    thisLevel.add(node.data);
                    nextDepthNode.add(node.left);
                    nextDepthNode.add(node.right);
                }
            }

            if (!thisLevel.isEmpty()) result.add(thisLevel);
            currDepthNode = nextDepthNode;
        }

        return result;
    }
    */


    public static class CircleQueue {
        private int head = 0, tail = 0, numQueueElements = 0;
        private static final int SCALE_FACTOR = 2;
        private Integer[] entries;

        public CircleQueue (int cap) {entries = new Integer[cap];}

        public void enqueue(Integer x) {
            if (numQueueElements == entries.length) {
                // API makes life such much easier
                Collections.rotate(Arrays.asList(entries), -head);
                head  = 0;
                tail = numQueueElements;
                entries = Arrays.copyOf(entries, numQueueElements * SCALE_FACTOR);
            }

            entries[tail] = x;
            tail = (tail + 1) % entries.length;
            numQueueElements++;
        }

        public Integer dequeue() {
            if (numQueueElements != 0) {
                numQueueElements--;
                Integer ret = entries[head];
                head = (head + 1) % entries.length;
                return ret;
            }

            throw new NoSuchElementException("Dequeue called on an empty queue.");
        }

        public Integer size() {
            return numQueueElements; // this is better, because just use head and tail can be a little tricky
        }
    }

    public static class QueueUsingStack {
        private Deque<Integer> enqueue = new ArrayDeque<>();
        private Deque<Integer> dequeue = new ArrayDeque<>();

        public void enqueue(Integer x) {enqueue.push(x);}

        public Integer dequeue() {
            Integer ret;
            while(!enqueue.isEmpty()) dequeue.push(enqueue.pop());

            if (!dequeue.isEmpty()) return dequeue.removeFirst();
            throw new NoSuchElementException("cannot pop empty queue");
        }
    }


    // Arrays.asList returns a wrapped version of the original arrays, of which mutation transfers.
    public static void main(String[] args) {
        String path = "sc//./../tc/awk/././";
        System.out.println(shortestEquivalentPath(path));

        Integer[] example = new Integer[] {1,2,3,4,5};
        for (int i : example) System.out.print(i + " ");
        System.out.println();
        Collections.rotate(Arrays.asList(example), 3);
        for (int i : example) System.out.print(i + " ");
        System.out.println();
    }


}

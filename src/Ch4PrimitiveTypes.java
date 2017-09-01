public class Ch4PrimitiveTypes {
    public static int swapBits(int x, int i, int j) {
        // Extract the i-th and j-th bits, and see if they differ
        if (((x >>> i) & 1) != ((x >>> j) & 1)) {
            int bitmask = (1 << i) | (1 << j);
            x = x^bitmask;
        }
        return x;
    }

    public static long reverseBits(long x) {
        // for this problem, we can use swapBit, or caching.
        return 1L;
    }

    public static void main(String[] args) {
        int input = 0b101000101011111;
        Ch4PrimitiveTypes sb = new Ch4PrimitiveTypes();
        System.out.println("input: " + Integer.toBinaryString(input));
        System.out.println("output: " + Integer.toBinaryString(sb.swapBits(input, 9, 3)));

    }
}
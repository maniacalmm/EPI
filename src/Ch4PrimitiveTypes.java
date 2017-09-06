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

    /**************************************************************/
    public static long multiply(long x, long y) {
        long sum = 0;
        while( x != 0) {
            if ((x & 1) != 0) {
                sum = add(sum, y);
            }

            x >>>= 1;
            y <<= 1;
        }
        return sum;
    }

    private static long add(long a, long b) {
        // unfinished, don't want to yet.
        long sum = 0, carryin = 0, k = 1, tmpA = a, tmpB = b;
        return sum;
    }
    /**************************************************************/

    public static double power(double x, int y) {
        double result = 1.0;
        long power = y;
        if (y < 0) {
            power = -power;
            x = 1.0 / x;
        }

        while(power != 0) {
            if ((power & 1) != 0) {
                result *= x;
            }

            x *= x;
            power >>>= 1;
        }
        return result;
    }

    /**************************************************************/
    public static long reverse(int x) {
        long result = 0;
        long xRemaining = Math.abs(x);
        while(xRemaining > 0) {
            result = 10 * result + xRemaining % 10;
            xRemaining /= 10;
        }

        return x > 0? result : -result;
    }

    /**************************************************************/
    public static boolean isPalindromeNumber(int x) {
        if (x <= 0) { // negative numbers are not Palindrome
            return x == 0;
        }

        final int numDigits = (int) (Math.floor(Math.log10(x))) + 1; // I used to do this as well
        int msdMask = (int) Math.pow(10, numDigits - 1);

        for (int i = 0; i < (numDigits / 2); i++) {
            if (x / msdMask != x % 10)
                return false;
            x %= msdMask;
            x /= 10;
            msdMask /= 100;
        }

        return true;
    }

    /**************************************************************/
    public static class Rectangle {
        int x, y, width, height;

        public Rectangle(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public static Rectangle intersectRectangle(Rectangle R1, Rectangle R2) {
            if (!isIntersect(R1, R2))
                return new Rectangle(0,0, -1, -1);
            return new Rectangle(
                    Math.max(R1.x, R2.x), Math.max(R1.y, R2.y),
                    Math.min(R1.x + R1.width, R2.x + R2.width) - Math.max(R1.x, R2.x),
                    Math.min(R1.y + R1.height, R2.y + R2.height) - Math.max(R1.y, R2.y));
        }

        public static boolean isIntersect(Rectangle r1, Rectangle r2) {
            if (r1.x + r1.width <= r2.x || r2.x + r2.width <= r1.x) return false;
            else if (r1.y + r1.height <= r2.y || r2.y + r2.height <= r1.y) return false;
            else return true;
        }
    }


    /**************************************************************/
    public static void main(String[] args) {
        int input = 0b101000101011111;
        Ch4PrimitiveTypes sb = new Ch4PrimitiveTypes();
        System.out.println("input: " + Integer.toBinaryString(input));
        System.out.println("output: " + Integer.toBinaryString(sb.swapBits(input, 9, 3)));

    }
}
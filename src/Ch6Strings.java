import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ch6Strings {
    public static String intToString(int x) {
        boolean isNegative = false;

        if (x < 0) isNegative = true;

        StringBuilder  s = new StringBuilder();

        while(x != 0){
            s.append((char) ('0' + Math.abs(x % 10)));
            x /= 10;
        }

        if (isNegative) s.append('-');
        s.reverse();
        return s.toString();
    }

    public static int stringToInt(String A) {
        int result = 0;
        for (int i = A.charAt(0) == '-'? 1 : 0 ; i < A.length(); i++) {
            result = (A.charAt(i) - '0') + result * 10;
        }
        return A.charAt(0) == '-'? -result : result;
    }

    public static String convertBase(String a, int b1, int b2) {
        StringBuilder s  = new StringBuilder();
        int decimal = 0;
        int result = 0;
        for (int i = 0; i < a.length(); i++) {
            int digit = a.charAt(i) - '0';
            if (digit > 9) digit = a.charAt(i) - 55;
            decimal = (digit) + decimal * b1;
        }

        System.out.println("decimal: " + decimal);

        while(decimal > 0) {
            int digit = decimal % b2;
            if (digit > 9) digit += 55;
            else digit += '0';
            s.append((char) digit);
            decimal /= b2;
        }

        s.reverse();
        return s.toString();
    }

    public static String covertBaseBook(String numAsString, int b1, int b2) {
        boolean isNegative = numAsString.startsWith("-");
        int numAsInt = 0;
        for (int i = (isNegative ? 1 : 0); i < numAsString.length(); i++) {
            numAsInt *= b1;
            numAsInt += Character.isDigit(numAsString.charAt(i))
                            ? numAsString.charAt(i) - '0'
                            : numAsString.charAt(i) - 'A' + 10;
        }

        return (isNegative? "-" : "") + (numAsInt == 0 ? "0" : constructFromBase(numAsInt, b2));
    }

    private static String constructFromBase(int numAsint, int b2) {
        StringBuilder s = new StringBuilder();
        while(numAsint > 0) {
            int digit = numAsint % b2;
            digit = (digit > 9 ? digit + 'A' - 10 : digit + '0');
            s.append((char)digit);
        }
        s.reverse();
        return s.toString();
    }

    public static int ssDecodeColID(String x) {
        int result = 0;
        for (int i = 0; i < x.length(); i++) {
            int tmp = x.charAt(i) - 'A' + 1;
            result = tmp + result * 26;
        }

        return result;
    }


    public static String replaceAndRemoveBook(int size, char[] s) {
        // EPI does this double iteration, first pass from the beginning they deleted every b
        // and the second iteration from the end they replaced every a with double d
        //       a c b b a a
        //       _ _ c _ _ _ _

        // remove every b and count a

        StringBuilder result = new StringBuilder();
        int writeIdx = 0, aCount = 0;
        for (int i = 0; i < size; i++) {
            if (s[i] != 'b') s[writeIdx++] = s[i];
            if (s[i] == 'a') aCount++;
        }

       //Backward iteration: replace a with dd starting from the end
        int curIdx = writeIdx - 1;
        writeIdx = writeIdx + aCount - 1;
        final int finalSize = writeIdx + 1;

        while(curIdx >= 0) {
            if(s[curIdx] == 'a') {
                s[writeIdx--] = 'd';
                s[writeIdx--] = 'd';
            } else {
                s[writeIdx--] = s[curIdx];
            }

            --curIdx;
        }

        for (int i = 0; i < s.length && s[i] != 0; i++) {
            result.append((char) s[i]);
        }

        return result.toString();
    }

    public static String printArray(char[] s) {
        String str = "";

        for (int i = 0; i < s.length && s[i] != 0; i++) {
            str += s[i];
        }

        return str;
    }

    public static boolean isPalindrome(String s) {
        int start = 0, end = s.length() - 1;

        while(start < end) {
            while (isLetter(s.charAt(start)))
                start++;
            while(isLetter(s.charAt(end)))
                end--;

            System.out.println(s.charAt(start) + " " + s.charAt(end));
            if (s.charAt(start) != s.charAt(end) &&
                    s.charAt(start) - 32 != s.charAt(end) &&
                    s.charAt(start) + 32 != s.charAt(end))
                return false;
            start++; end--;
        }

        return true;
    }

    private static boolean isLetter(char c) {
        if (! ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <='z')))
            return true;
        return false;
    }

    public static String reverseWords(char[] input) {
        stringReverse(input, 0, input.length - 1);

        int i = 0;
        int j = 0;
        while (j < input.length) {
            if (input[j] == ' ') {
                stringReverse(input, i, j - 1);
                i = j + 1; j = i;
            } else j++;
        }

        return Arrays.toString(input);
    }


    private static void stringReverse(char[] s, int i, int j) {
        while (i < j) {
           char tmp = s[i];
           s[i] = s[j];
           s[j] = tmp;
           i++; j--;
        }
    }



    public static List<String> phoneMnemonic(String digit) {
        List<String> code = Arrays.asList("0", "1", "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ");
        List<String> result = new ArrayList<>();
        PM(digit, "", code, result);
        return result;
    }

    private static void PM(String digit, String acry, List<String> code, List<String> result) {
        if (digit.length() == 0) {
            result.add(acry);
            return;
        }
        for (int i = 0; i < code.get(digit.charAt(0) - '0').length(); i++) {
            PM(digit.substring(1), acry + code.get(digit.charAt(0) - '0').charAt(i), code, result);
        }
    }

    public static String lookAnySay(int n) {
        String result = "1";
        for (int i = 0; i < n; i++) {
            result = lookAnySayHelper(result);
        }
        return result;
    }

    private static String lookAnySayHelper(String x) {
        String result = "";
        int start = 0;
        int end = 1;
        while(end < x.length()) {
            if (x.charAt(end) == x.charAt(end - 1)) {
                end++;
            } else {
                result += Integer.toString(end - start) + x.charAt(end - 1);
                start = end;
                end++;
            }
        }
        result += Integer.toString(end - start) + x.charAt(end - 1);
        return result;
    }

    public static List<String> getValidAddress (String s) {
        List<String> result = new ArrayList<>();

        for (int i = 1; i < 4 && i < s.length(); i++) {
            String first = s.substring(0, i);
            if (isValidPart(first)) {
                for (int j = 1; j < 4 && i + j < s.length(); j++) {
                    String second = s.substring(i, i + j);
                    if (isValidPart(second)) {
                        for (int k = 1; k < 4 && i + j + k < s.length(); k++) {
                            String third = s.substring(i + j, i + j + k);
                            String fourth = s.substring(i + j + k);
                            if (isValidPart(third) && isValidPart(fourth))
                                result.add(first + "." + second + "." + third + "." + fourth);
                        }
                    }
                }
            }
        }

        return result;
    }

    private static boolean isValidPart(String s) {
        if (s.length() > 3) return false;
        if (s.startsWith("0") && s.length() > 1) return false;

        int val = Integer.parseInt(s);
        return val >= 0 && val <= 255;
    }

    public static void main(String[] args) {
        /*
        String b = "12345";
        int a = 38472034;

        char[] s = new char[20];
        s[0] = 'a';
        s[1] = 'c';
        s[2] = 'b';
        s[3] = 'b';
        s[4] = 'a';
        s[5] = 'a';

        char[] s1 = Arrays.copyOf(s,  s.length);

        //System.out.println(intToString(a));
        //System.out.println(stringToInt(b));

        //System.out.println(convertBase("615", 7, 13));
        //System.out.println(ssDecodeColID("ZZ"));
        //System.out.println(replaceAndRemoveBook(6, s));

        //System.out.println(isPalindrome("A man, a plan, a canal, Panama."));
        String input = "bob likes alice";
        System.out.println(reverseWords(input.toCharArray()));
        */

        /*
        List<String> s = phoneMnemonic("2276696");

        for (String a : s)
            if (a.equals("ACRONYM"))
                System.out.println("gotta");
        */

        System.out.println(lookAnySay(8));

    }
}

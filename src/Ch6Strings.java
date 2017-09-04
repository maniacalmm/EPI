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



    public static void main(String[] args) {
        String b = "12345";
        int a = 38472034;

        //System.out.println(intToString(a));
        //System.out.println(stringToInt(b));

        System.out.println(convertBase("615", 7, 13));
    }
}

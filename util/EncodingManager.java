//Alexander Weaver
//Last update: 4-28-2015 3:59pm
package Util;

import java.math.BigInteger;

public class EncodingManager {
    
    public byte[] textToValuesASCII(String text) {
        int length = text.length();
        byte[] values = new byte[length];
        for(int i = 0; i < length; i++) {
            values[i] = (byte)text.charAt(i);
        }
        return values;
    }
    
    public String textToHexASCII(String text) {
        char[] chars = text.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            hex.append(Integer.toHexString((int) chars[i]));
        }
        return hex.toString();
    }
    
    public BigInteger hexToBigInteger(String hex) {
        BigInteger result = new BigInteger(hex, 16);
        return result;
    }
    
    public String bigIntegerToHex(BigInteger value) {
        String result = value.toString(16);
        return result;
    }
    
    public String hexToTextASCII(String hex) {
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < hex.length(); i += 2) {
            String str = hex.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
}
    
    public String valuesToTextASCII(byte[] values) {
        int length = values.length;
        StringBuilder sb = new StringBuilder();
        char current;
        for(int i = 0; i < length; i++) {
            current = (char)values[i];
            sb.append(current);
        }
        return sb.toString();
    }
    
    public String valuesToTextASCII(short[] values) {
        int length = values.length;
        StringBuilder sb = new StringBuilder();
        char current;
        for(int i = 0; i < length; i++) {
            if(values[i] > 127) {
                values[i] %= 128;   //Ensures value is in correct range
            }
            current = (char)values[i];
            sb.append(current);
        }
        return sb.toString();
    }
    
    public String valuesToTextASCII(int[] values) {
        int length = values.length;
        StringBuilder sb = new StringBuilder();
        char current;
        for(int i = 0; i < length; i++) {
            if(values[i] > 127) {
                values[i] %= 128;   //Ensures value is in correct range
            }
            current = (char)values[i];
            sb.append(current);
        }
        return sb.toString();
    }
}

//Alexander Weaver
//Last update: 5-11-2015 1:53pm
package Util;

import java.math.BigInteger;

public class EncodingManager {
    
    public String textToHexASCII(String text) {
        char[] chars = text.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            String hexChar = Integer.toHexString((int)chars[i]);
            if(hexChar.length() == 1) {
                hexChar = '0' + hexChar;
            }
            hex.append(hexChar);
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
    
    public String[] separate(String text, int sectionLength) {
        int length = text.length();
        String[] separatedText = new String[length/sectionLength + 1];
        for(int i = 0; i < separatedText.length; i++) {
            try {
                separatedText[i] = text.substring(i*32, i*32 + 31);
            } catch(StringIndexOutOfBoundsException e) {
                separatedText[i] = text.substring(i*32);
            }
        }
        return separatedText;
    }
}

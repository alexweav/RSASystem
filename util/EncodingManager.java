//Alexander Weaver
//Last update: 6-1-2015 4:33pm
package Util;

import java.math.BigInteger;

//Manages encoding/padding of strings
public class EncodingManager {
    
    //Takes a string in ASCII
    //Converts each character to its hex code, and returns the concatenated hex string
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
    
    //Converts a hex string to a BigInteger format
    public BigInteger hexToBigInteger(String hex) {
        BigInteger result = new BigInteger(hex, 16);
        return result;
    }
    
    //Converts a BigInteger to a hex string format
    public String bigIntegerToHex(BigInteger value) {
        String result = value.toString(16);
        return result;
    }
    
    //Takes a string of hexadecimal values
    //Groups every two values and converts them to their corresponding ASCII character
    //returns a string of concatenated characters
    public String hexToTextASCII(String hex) {
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < hex.length(); i += 2) {
            String str = hex.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
}
    
    //Takes a byte array of numerical values
    //Converts each byte to its corresponding ASCII character
    //Returns a string of concatenated characters
    public String valuesToTextASCII(byte[] values) {
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
    
    //Takes an int array of numerical values
    //Converts each int to its corresponding ASCII character
    //Returns a string of concatenated characters
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
    
    //Takes a string and an integer section length
    //Separates the string into smaller sub strings of length sectionLength
    //Returns an array of all substrings
    public String[] separate(String text, int sectionLength) {
        int length = text.length();
        if(length == 0) {
            return new String[0];
        }
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
    
    //Takes an array of strings
    //Joins them into a single string
    public String joinStrings(String[] strings) {
        int length = strings.length;
        String joined = "";
        for(int i = 0; i < length; i++) {
            joined += strings[i];
        }
        return joined;
    }
}

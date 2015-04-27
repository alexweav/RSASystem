//Alexander Weaver
//Last update: 4-27-2015 11:17am
package Util;

public class EncodingManager {
    
    public byte[] textToValuesASCII(String text) {
        int length = text.length();
        byte[] values = new byte[length];
        for(int i = 0; i < length; i++) {
            values[i] = (byte)text.charAt(i);
        }
        return values;
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

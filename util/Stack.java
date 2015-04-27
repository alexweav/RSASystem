//Alexander Weaver
//Last update: 4-27-2015 1:35pm
package util;

import java.util.ArrayList;
import java.util.List;

public class Stack<T> {
    
    private List<T> data = new ArrayList<>();
    
    public void push(T ob) {
        data.add(ob);
    }
    
    public T pop() {
        int length = data.size();
        T poppedData = data.get(length);
        data.remove(length);
        return poppedData;
    }
}

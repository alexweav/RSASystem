//Alexander Weaver
//Last update: 4-24-2015 10:58am
package Util;

//Exception indicating bad class parameters before execution of a function
public class BadParameterException extends Exception{
    public BadParameterException () {

    }

    public BadParameterException (String message) {
        super (message);
    }

    public BadParameterException (Throwable cause) {
        super (cause);
    }

    public BadParameterException (String message, Throwable cause) {
        super (message, cause);
    }
}

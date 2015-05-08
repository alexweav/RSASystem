//Alexander Weaver
//Last update: 5-21-2015 5:21pm
package Interface;

//main class
public class RSASystem {
    
    //main method for program. manages arguments.
    public static void main(String[] argv) {
        int numArgs = argv.length;
        if(numArgs > 0 && "controller".equals(argv[0])) {
            System.out.println("Executing controller code.");
            (new Controller()).run();
        } else if(numArgs > 0 && "text".equals(argv[0])) {
            System.out.println("Initiating program with text-based interface.");
            (new TUI()).init();
        } else {
            System.out.println("No recognized argument. Defaulting to text interface.");
            (new TUI()).init();
        }
    }
}

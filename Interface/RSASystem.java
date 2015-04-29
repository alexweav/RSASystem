//Alexander Weaver
//Last update: 4-29-2015 1:15am
package Interface;

public class RSASystem {
    
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

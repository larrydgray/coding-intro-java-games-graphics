// A tiny Turing machine, the idea Alan Turing described in 1936: a tape, a head
// that reads and writes one symbol at a time, and a table of rules.
// This machine adds 1 to a binary number, and the code is organized so you can
// find the three basic building blocks of programming in it:
//   EXPRESSIONS AND STATEMENTS, DECISIONS, and LOOPS.
//   javac Turing.java
//   java Turing            (starts with 1011, which is 11)
//   java Turing 111        (or give it your own binary number)
public class Turing {

    static StringBuilder tape;   // the tape; '_' means a blank square
    static int head;             // which square the head is on
    static String state;         // SCAN: run right to the end.  CARRY: add one going left.

    public static void main(String[] args) {

        // ===== EXPRESSIONS AND STATEMENTS =====
        // Each line below is a statement. The right-hand side of each = is an
        // expression that gets calculated, and the result changes a variable.
        String input = args.length > 0 ? args[0] : "1011";
        tape = new StringBuilder("_" + input + "_");
        head = 1;
        state = "SCAN";
        int steps = 0;

        // ===== LOOPS (do-while) =====
        // A do-while checks its condition AFTER each pass, so the body always
        // runs at least once: here, we always show the starting tape.
        do {
            show(steps);
            step();
            steps = steps + 1;
        } while (!state.equals("HALT"));
        show(steps);

        // ===== LOOPS (while) =====
        // A while loop checks BEFORE each pass, so it might run zero times.
        // We use two to trim the blank squares off both ends of the answer.
        String answer = tape.toString();
        while (answer.startsWith("_")) {
            answer = answer.substring(1);
        }
        while (answer.endsWith("_")) {
            answer = answer.substring(0, answer.length() - 1);
        }
        System.out.println("Answer: " + answer);
    }

    // ===== DECISIONS =====
    // One step of the machine: look at the symbol under the head and decide what to do.
    static void step() {
        char symbol = tape.charAt(head);

        // switch/case picks one path by the machine's current state
        switch (state) {
            case "SCAN":
                // if/else picks one path by a condition
                if (symbol == '_') {          // reached the right end
                    state = "CARRY";
                    head--;
                } else {
                    head++;                   // keep moving right
                }
                break;
            case "CARRY":
                if (symbol == '1') {          // 1 + 1 = 0, carry the 1 to the left
                    tape.setCharAt(head, '0');
                    head--;
                } else {                      // 0 (or blank) becomes 1 and we are done
                    tape.setCharAt(head, '1');
                    state = "HALT";
                }
                break;
        }
    }

    // ===== LOOPS (for) =====
    // A for loop is a compact loop for counting: start, condition, step.
    static void show(int step) {
        System.out.println("step " + step + "  state " + state);
        System.out.println("  " + tape);
        String pointer = "  ";
        for (int i = 0; i < head; i++) {
            pointer = pointer + " ";
        }
        System.out.println(pointer + "^  (the head)");
    }
}

// A tiny stored-program computer, the idea John von Neumann described in 1945:
// the program and its data live in the SAME memory, and the CPU repeats
// fetch -> decode -> execute over and over.
//   javac VonNeumann.java
//   java VonNeumann
public class VonNeumann {
    // Instruction = operation * 100 + memory address
    static final int HALT = 0, LOAD = 1, ADD = 2, STORE = 3;

    public static void main(String[] args) {
        int[] memory = new int[16];

        // The program (addresses 0-3) ...
        memory[0] = LOAD * 100 + 8;   // load the number at address 8
        memory[1] = ADD * 100 + 9;    // add the number at address 9
        memory[2] = STORE * 100 + 10; // store the result at address 10
        memory[3] = HALT * 100;       // stop
        // ... and its data (addresses 8-9), together in one memory
        memory[8] = 25;
        memory[9] = 17;

        int accumulator = 0; // the CPU's one working register
        int pc = 0;          // program counter: which instruction is next

        while (true) {
            int instruction = memory[pc];          // FETCH
            pc++;
            int op = instruction / 100;            // DECODE
            int address = instruction % 100;
            System.out.println("pc=" + (pc - 1) + "  op=" + op + "  address=" + address
                    + "  accumulator=" + accumulator);
            if (op == HALT) {
                break;
            } else if (op == LOAD) {               // EXECUTE
                accumulator = memory[address];
            } else if (op == ADD) {
                accumulator += memory[address];
            } else if (op == STORE) {
                memory[address] = accumulator;
            }
        }
        System.out.println("Result stored at address 10: " + memory[10]);
        // Try it: change the numbers, or add a second ADD instruction.
    }
}

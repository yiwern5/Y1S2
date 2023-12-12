///////////////////////////////////
// This is the main shift register class.
// Notice that it implements the ILFShiftRegister interface.
// You will need to fill in the functionality.
///////////////////////////////////

/**
 * class ShiftRegister
 * @author
 * Description: implements the ILFShiftRegister interface.
 */
public class ShiftRegister implements ILFShiftRegister {
    ///////////////////////////////////
    // Create your class variables here
    ///////////////////////////////////
    // TODO:
        int tap;
        int[] shiftRegister;
    ///////////////////////////////////
    // Create your constructor here:
    ///////////////////////////////////
    ShiftRegister(int size, int tap) {
        // TODO:
        if (tap < 0 || tap > size - 1) {
            System.out.println("Error");
        } else {
            shiftRegister = new int[size];
            this.tap = tap;
        }
    }

    ///////////////////////////////////
    // Create your class methods here:
    ///////////////////////////////////
    /**
     * setSeed
     * @param seed
     * Description:
     */
    @Override
    public void setSeed(int[] seed) {
        // TODO:
        for (int j : seed) {
            if (j < 0 || j > 1) {
                System.out.println("Error");
            }
        }
        int[] newSeed = new int[seed.length];
        for (int i = 0; i < newSeed.length; i++) {
            newSeed[i] = seed[seed.length - 1 - i];
        }
        shiftRegister = newSeed;
    }

    /**
     * shift
     * @return
     * Description:
     */
    @Override
    public int shift() {
        // TODO:
        int tapBit = shiftRegister.length - 1 - tap;
        int leastSigBit = shiftRegister[0] ^ shiftRegister[tapBit];
        for (int i = 0; i < shiftRegister.length - 1; i++) {
            shiftRegister[i] = shiftRegister[i + 1];
        }
        shiftRegister[shiftRegister.length - 1] = leastSigBit;
        return leastSigBit;
    }
    /**
     * generate
     * @param k Number of bits
     * @return
     * Description: Extracts k bits from the shift register. It executes the shift operation
     * k times, saving the k bits returned. It then converts these k bits from binary into an integer.
     */
    @Override
    public int generate(int k) {
        // TODO:
        int[] returnedBits = new int[k];
        for (int i = 0; i < k; i++) {
            returnedBits[i] = shift();
        }
        return toDecimal(returnedBits);
    }
    /**
     * Returns the integer representation for a binary int array.
     * @param array Binary int array
     * @return integer
     */
    private int toDecimal(int[] array) {
        // TODO:
        int v = 0;
        for (int j : array) {
            v = v * 2 + j;
        }
        return v;
    }
}
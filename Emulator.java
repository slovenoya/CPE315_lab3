import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Emulator class contains the essential data for MIPS code. 
 * Data including : PC, Register Table, RAM of 8192 ints
 * 
 */

public class Emulator {
    private static final String SUPPORTED_REGS = 
        "$0,$v0,$v1,$a0,$a1,$a2,$a3,$t0,$t1,$t2,$t3,"
        + "$t4,$t5,$t6,$t7,$s1,$s2,$s3,$s4,$s5,$s6," 
        + "$s7,$t8,$t9,$sp,$ra";
    private static final String[] SUPPORTED_REG_LIST = SUPPORTED_REGS.split(",");
    private static final int SUPPORTED_REGS_NUM = SUPPORTED_REG_LIST.length;
    private static final int MEM_SIZE = 8192;

    private int PC; 
    private Map<Integer, Integer> registerIdToValue = new HashMap<>();      //first int : id of register, second int: value of register
    private Map<String, Integer> registerNameToId = new HashMap<>();    //first int : name of register, second int: id of register
    private int [] memory;

    public Emulator() {
        setUpRegisterNameId();
        initializeEmulator();
    }

    public void initializeEmulator() {
        this.PC = 0;
        initializeRegTable();
        initializeMemory();
    }

    private void initializeRegTable() {
        for (int i = 0; i < SUPPORTED_REGS_NUM; i++) {
            this.registerIdToValue.put(i, 0);
        }
    }

    private void initializeMemory() {
        this.memory = new int[MEM_SIZE];
    }

    public int getPC() {
        return PC;
    }

    public void movePC(int move) {
        PC += move;
    }

    public void writeReg(String regName, int value) {
        registerIdToValue.replace(registerNameToId.get(regName), value);
    }

    public int readReg(String regName) {
        return registerIdToValue.get(registerNameToId.get(regName));
    }

    public int[] readMemory(int low, int high) {
        if (isOutOfBound(low) || isOutOfBound(high)) {
            return null;
        }
        if (low > high) {
            return null;
        }
        int[] memoryChunck = new int[high - low];
        for (int i = low; i < high; i++) {
            memoryChunck[i] = memory[i];
        }
        return memoryChunck;
    }

    public void storeMemory(int address, int value) {
        if (isOutOfBound(address)) {

        } else {
            memory[address] = value;
        }
    }

    private boolean isOutOfBound(int address) {
        return (address < 0 || address >= MEM_SIZE);
    }

    private void setUpRegisterNameId() {
        for (int i = 0; i < SUPPORTED_REGS_NUM; i++) {
            registerNameToId.put(SUPPORTED_REG_LIST[i], i);
        }
    }

}

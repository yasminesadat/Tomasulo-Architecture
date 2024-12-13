package com.tomasulo.classes;

import java.util.Arrays;

public class Cache {

    private static class CacheLine {
        int tag;
        boolean valid;
        byte[] data;

        CacheLine(int blockSize) {
            this.data = new byte[blockSize];
            this.valid = false; // Initially invalid
            this.tag = -1;
        }

    }

    private final int cacheSize;
    private final int blockSize;
    private final int numberOfLines;
    private final CacheLine[] cacheLines;

    public Cache(int cacheSize, int blockSize) {
        if (blockSize <= 0 || cacheSize <= 0 || cacheSize % blockSize != 0) {
            throw new IllegalArgumentException("Invalid cache size or block size.");
        }
        this.cacheSize = cacheSize;
        this.blockSize = blockSize;
        this.numberOfLines = cacheSize / blockSize;
        this.cacheLines = new CacheLine[numberOfLines];
        for (int i = 0; i < numberOfLines; i++) {
            cacheLines[i] = new CacheLine(blockSize);
        }
    }

    
    public int getBlockSize() {
        return blockSize;
    }

    // Handle load/store operations
    public Object access(int address, Object data, int size, String operation) {
        int tag = address / cacheSize;
        int index = (address / blockSize) % numberOfLines;

        CacheLine cacheLine = cacheLines[index];

        // Calculate block offset
        int blockOffset = address % blockSize;

        if (data == null) { // Load operation
            if (cacheLine.valid && cacheLine.tag == tag) {
                // Cache hit: Read data from cache
                return extractData(cacheLine.data, blockOffset, size, operation);
            } else {
                // Cache miss: Load data (simulated here as null for the sake of example)
                return null;
            }
        } else { // Store operation
            byte[] dataBytes = toByteArray(data, size);
            cacheLines[index].tag = tag;
            cacheLines[index].data = storeData(cacheLine.data , dataBytes, blockOffset);
            cacheLines[index].valid = true;
            return null;
        }
    }

    // Extract data from cache based on operation type
    private Object extractData(byte[] block, int offset, int size, String operation) {
        byte[] extracted = Arrays.copyOfRange(block, offset, offset + size);
        if (operation.equals(InstructionType.LOAD_SINGLE_PRECISION)) {
            return bytesToFloat(extracted); // Single precision (4-byte float)
        } else if (operation.equals(InstructionType.LOAD_DOUBLE_PRECISION) || operation.equals(InstructionType.LOAD_DOUBLE_WORD)) {
            return bytesToDouble(extracted); // Double precision (8-byte double)
        } else if (operation.equals(InstructionType.LOAD_WORD)) {
            return bytesToInt(extracted); // Integer (4-byte)
        } else {
            throw new IllegalArgumentException("Unsupported load operation: " + operation);
        }
    }

    private byte[] storeData(byte[] block, byte[] data, int offset) {
        System.arraycopy(data, 0, block, offset, data.length);
        return block;
    }


    public void storeCacheLine(int address, byte[] memoryBlock) {
        if (memoryBlock.length != blockSize) {
            throw new IllegalArgumentException("Block size mismatch.");
        }
        int lineIndex = (address / blockSize) % numberOfLines; // Calculate the line index
        int tag = address / (blockSize * numberOfLines); // Calculate the tag

        // Store the block in the cache line
        cacheLines[lineIndex].data = memoryBlock.clone();
        cacheLines[lineIndex].tag = tag;
        cacheLines[lineIndex].valid = true;

        System.out.println("Stored cache line at index " + lineIndex + " with tag " + tag);
    }

    // Convert data to byte array based on size
    private byte[] toByteArray(Object data, int size) {
        if (size == 4) {
            if (data instanceof Float) {
                return floatToBytes((Float) data);
            } else if (data instanceof Integer) {
                return intToBytes((Integer) data);
            } else {
                throw new IllegalArgumentException("Unsupported data type for size 4.");
            }
        } else if (size == 8) {
            if (data instanceof Double) {
                return doubleToBytes((Double) data);
            } else {
                throw new IllegalArgumentException("Unsupported data type for size 8.");
            }
        }
        throw new IllegalArgumentException("Invalid size.");
    }

    // Utility methods for type conversions

    private byte[] intToBytes(int value) {
        return new byte[] {
            (byte) (value >> 24),
            (byte) (value >> 16),
            (byte) (value >> 8),
            (byte) value
        };
    }
  
    private int bytesToInt(byte[] bytes) {   // treat bytes as unsigned values 
        return ((bytes[0] & 0xFF) << 24) |
               ((bytes[1] & 0xFF) << 16) |
               ((bytes[2] & 0xFF) << 8) |
               (bytes[3] & 0xFF);
    }

    private byte[] floatToBytes(float value) {
        return intToBytes(Float.floatToIntBits(value));
    }

    private float bytesToFloat(byte[] bytes) {
        return Float.intBitsToFloat(bytesToInt(bytes));
    }

    private byte[] doubleToBytes(double value) {
        long bits = Double.doubleToLongBits(value);
        return new byte[] {
            (byte) (bits >> 56),
            (byte) (bits >> 48),
            (byte) (bits >> 40),
            (byte) (bits >> 32),
            (byte) (bits >> 24),
            (byte) (bits >> 16),
            (byte) (bits >> 8),
            (byte) bits
        };
    }

    private double bytesToDouble(byte[] bytes) {
        long bits = ((long) (bytes[0] & 0xFF) << 56) |
                    ((long) (bytes[1] & 0xFF) << 48) |
                    ((long) (bytes[2] & 0xFF) << 40) |
                    ((long) (bytes[3] & 0xFF) << 32) |
                    ((long) (bytes[4] & 0xFF) << 24) |
                    ((long) (bytes[5] & 0xFF) << 16) |
                    ((long) (bytes[6] & 0xFF) << 8) |
                    (bytes[7] & 0xFF);
        return Double.longBitsToDouble(bits);
    }

    // For debugging purposes
    public void displayCache() {
        System.out.println("Cache State:");
        for (int i = 0; i < cacheLines.length; i++) {
            CacheLine line = cacheLines[i];
            System.out.println("Index: " + i + ", Valid: "+line.valid+", Tag: " + line.tag + ", Data: " + Arrays.toString(line.data));
        }
    }

    public static void main(String[] args) {
        Cache cache = new Cache(64, 16); 

        // Store single-precision float (S.S)
        //cache.access(0, 3.14f, 4, InstructionType.STORE_SINGLE_PRECISION); // S.S
        //System.out.println("Read S.S: " + cache.access(0, null, 4, InstructionType.LOAD_SINGLE_PRECISION)); // L.S

        // Store double-precision float (S.D)
        cache.access(8, 3.141592653589793, 8, InstructionType.STORE_DOUBLE_PRECISION); // S.D
        System.out.println("Read S.D: " + cache.access(8, null, 8, InstructionType.LOAD_DOUBLE_PRECISION)); // L.D
        cache.displayCache();
        // Store integer (SW)
        cache.access(4, 42, 4, InstructionType.STORE_WORD); // SW
        System.out.println("Read SW: " + cache.access(4, null, 4, InstructionType.LOAD_WORD)); // LW

        // Store double-precision (SD)
        cache.access(16, 6.283185307179586, 8, InstructionType.STORE_DOUBLE_WORD); // SD
        System.out.println("Read SD: " + cache.access(16, null, 8, InstructionType.LOAD_DOUBLE_PRECISION)); // L.D

        // Display cache state
        cache.displayCache();
    }
}
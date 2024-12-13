package com.tomasulo.classes;

public class CacheMemoryHandler {

    // This method checks if a cache miss occurs and loads the block from memory to cache.
    public static Object loadDataFromMemoryIfCacheMiss(int address, Cache cache, Memory memory, int size, String operation) {
            int blockSize = cache.getBlockSize();
            // Calculate the block address and block size based on cache and block size
            int blockStartAddress = (address / blockSize) * blockSize;

            // Read the block from memory into the cache
            byte[] memoryBlock = new byte[blockSize];
            for (int i = 0; i < blockSize; i++) {
                memoryBlock[i] = memory.readByte(blockStartAddress + i);
            }
            // Now that we have the block, we need to store it in the cache
            cache.storeCacheLine(address, memoryBlock);
            // Now return the extracted data
            return cache.access(address, null, size, operation);  // Now we return the data from the cache after loading it
    }
    public static void main(String[] args) {
        int memorySize = 20;
        int cacheBlockSize = 5;
        int cacheSize = 10;

        Memory memory = new Memory(memorySize);
        Cache cache = new Cache(cacheSize,cacheBlockSize);

        int testAddress = 5; // Address to test
        int dataSize = 4; // Size of data to access
        String operation = "LW";

        System.out.println("Testing cache miss scenario:");
        cache.displayCache();
        memory.writeInt(5, 200);
        memory.displayMemory();
        loadDataFromMemoryIfCacheMiss(testAddress, cache, memory, dataSize, operation);
        cache.displayCache();
        System.out.println(cache.access(5, null, dataSize, operation));
    }
}


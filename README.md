# Tomasulo Simulator Project

This project implements the Tomasulo algorithm for out-of-order instruction execution. It simulates a CPU pipeline, including instruction issuing, reservation stations, register file management, execution, and write-back stages. The project also includes a graphical user interface (GUI) built with JavaFX for real-time visualization of the simulator's state.

## Overview

The Tomasulo Simulator is designed to model the following key components:

- **Instruction Representation**: The `Instruction` class represents an instruction with its type, registers (source, target, destination), immediate value, and various cycle timings.
  
- **Instruction Types**: Various types of instructions like ADDI, SUBI, etc., are implemented as an enumeration.

- **Reservation Stations**: Manage instruction execution slots.

- **Register File**: Maintains the state of CPU registers.

- **Cache**: A cache system is integrated to speed up performance.

- **Simulator**: Coordinates the execution flow and manages clock cycles, instruction queue, reservation stations, memory, and cache.

- **Graphical Interface**: Provides a visualization of the reservation station status, register contents, instruction queue, and execution progress.

### Instruction Representation
The `Instruction` class represents an individual instruction with the following attributes:
- `type`: Operation type (e.g., ADD, ADDI, SUB, etc.)
- `rs`: Source register
- `rt`: Target register
- `rd`: Destination register
- `immediate`: Immediate value for I-type instructions
- `startTime`: Execution start cycle
- `endTime`: Execution end cycle
- `issueTime`: Issue cycle
- `writeTime`: Write-back cycle
- `pc`: Program counter

### Key Classes
- **InstructionParser**: Processes assembly code.
- **Issuer**: Issues instructions from the instruction queue to the reservation stations.
- **ReservationStation**: Manages execution slots.
- **Executor**: Handles execution.
- **InstructionQueue**: Manages the queue of instructions.
- **RegisterFile**: Handles register state.
- **Simulator**: Coordinates the execution process.
- **TomasuloController**: Manages the GUI.

### Execution Process
During each clock cycle:
1. The simulator checks all reservation stations to identify ready instructions.
2. Instructions are executed if both operands are available and there are no pending dependencies.
3. The appropriate functional unit (e.g., Add/Sub, Multiply/Divide) is activated based on the instruction type.

### Write-back
Instructions are eligible for write-back when:
- Both operands are available (no dependencies).
- Execution is complete.
- The instruction's issue cycle has passed.

Once an instruction is selected for write-back, the result is written to the register file, and the reservation station is updated.

### Memory Editor
The simulator features a **Memory Editor** page that allows users to store values in memory before starting the simulation. This feature supports the following data types:
- **Integers**
- **Floats**
- **Doubles**

Users can initialize memory with specific values that will be used during the execution of the simulation. This is useful for scenarios where specific memory values are required for testing or debugging.

### Cache
The simulator includes a **Cache** system to speed up performance. The cache stores frequently accessed memory locations, reducing the time needed to fetch values from the main memory. The cache operates as follows:
- **Cache Hit**: If the requested data is found in the cache, it is fetched directly, reducing latency.
- **Cache Miss**: If the data is not in the cache, it is fetched from the main memory and stored in the cache. This takes 4 extra clock cycles.

This cache mechanism enhances the overall performance of the simulator, particularly when dealing with memory-intensive operations.

### GUI
The graphical interface is implemented using JavaFX and displays:
- Reservation station status
- Register contents
- Instruction queue
- Execution progress
- Memory contents (via the Memory Editor page)
- Cache status

## Setup and Usage

### Prerequisites
- Java Development Kit (JDK) version 11 or later.
- JavaFX library for GUI components.

### Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/tomasulo-simulator.git
    cd tomasulo-simulator
    ```

2. Compile and run the project:
    ```bash
    mvn clean install
    mvn exec:java
    ```

3. Open the graphical interface and load an assembly file to start the simulation.

### Configuration
The simulator allows you to configure the following parameters:
- Store Latency
- Load Latency
- Add Latency
- Mul Latency
- Sub Latency
- Div Latency
- Cache Block Size
- Cache Size
- Reservation Station Sizes

These parameters can be set through the GUI before starting the simulation.

## Example Usage

1. Load an assembly file (instructions.txt) containing MIPS instructions.
2. Use the **Memory Editor** page to store initial values (integers, floats, or doubles) in memory.
3. Start the simulation to see the execution flow in real-time.
4. View the status of reservation stations, registers, the instruction queue, and cache performance.
5. Monitor the execution cycles, with instructions being issued, executed, and written back.

## Screenshots
![Screenshot 2024-12-15 020502](https://github.com/user-attachments/assets/6a14b741-4171-46b7-804f-ee003f7d17e3)
![Screenshot 2024-12-15 020553](https://github.com/user-attachments/assets/6d83463d-89a8-42a4-b9f5-87f95e549a02)
![Screenshot 2024-12-15 020728](https://github.com/user-attachments/assets/8ae3b324-83c1-4ad1-b424-fa49ce93f8ae)







# Smart City Scheduler: SCC and DAG Path Planner

This project implements a Java-based framework for planning and optimizing interdependent maintenance tasks in a **Smart City / Smart Campus** environment.  
It combines two major algorithmic topics — **Strongly Connected Components (SCC)** for cycle detection and **Shortest/Longest Path algorithms on DAGs** for optimal scheduling.

---

## Core Features

- **SCC Detection (Tarjan’s Algorithm)**: Detects cyclic dependencies between city tasks and compresses them into single components to ensure proper ordering.
- **Topological Sorting (Kahn’s Algorithm)**: Produces a valid linear order of independent tasks after SCC compression.
- **Shortest Path Analysis**: Computes minimal time between tasks in the acyclic (condensed) graph.
- **Longest Path (Critical Path)**: Identifies the sequence of dependent tasks that determine total project duration.
- **Instrumentation**: Integrated metrics system that measures execution time, DFS visits, relaxations, and queue operations.
- **Maven Build**: Clean, reproducible build process with JUnit tests and dataset-driven execution.

---

## Project Structure

```

smartcity-scheduler/
├── src/
│   ├── main/java/smartcity/        # Core source code
│   └── test/java/smartcity/        # Unit tests
├── data/                           # JSON datasets (small, medium, large) # Result graphs
├── pom.xml                         # Maven configuration
└── README.md                       # Project documentation

````

---

## Build and Run Instructions

### Prerequisites
- **Java 17** or higher  
- **Maven 3.6.0** or higher  

### 1. Clone and Build

```bash
mvn clean package
````

This command compiles all source files, runs tests, and creates a runnable JAR inside the `/target` directory.

### 2. Run the Application

Use the command line to launch the project and specify which dataset and graph to load.

```bash
java -jar target/smartcity-scheduler.jar --file data/small_graphs.json --graph S1
```

Other examples:

```bash
java -jar target/smartcity-scheduler.jar --file data/medium_graphs.json --graph M2
java -jar target/smartcity-scheduler.jar --file data/large_graphs.json --graph L3
```

### 3. Run Tests

Execute all JUnit test cases:

```bash
mvn test
```

---

## Dataset Summary

All input graphs are stored in the `/data` directory, divided into three categories: **small**, **medium**, and **large**.
Weights are **edge-based**, meaning each edge’s `w` represents time or cost between two dependent tasks.

| Dataset                      | Vertices | Edges | Structure            | Description                            |
| ---------------------------- | -------- | ----- | -------------------- | -------------------------------------- |
| `small_graphs.json` (S1–S3)  | 6–10     | 7–12  | 1–2 SCCs / Pure DAG  | Simple test cases, ideal for debugging |
| `medium_graphs.json` (M1–M3) | 10–20    | 15–25 | Mixed (several SCCs) | Realistic task dependencies            |
| `large_graphs.json` (L1–L3)  | 20–50    | 35–60 | Multiple SCCs        | Stress tests for performance timing    |

---

## Results and Performance Analysis

Below is a summary of algorithmic results for selected test graphs.

| Dataset | Vertices | Edges | SCCs Found | Condensation Edges | Critical Path | Time (ms) | Total Ops |
| ------- | -------- | ----- | ---------- | ------------------ | ------------- | --------- | --------- |
| `S1`    | 8        | 7     | 6          | 6                  | 10            | 0.1       | 48        |
| `S2`    | 9        | 9     | 5          | 4                  | 9             | 0.2       | 52        |
| `M1`    | 14       | 20    | 4          | 5                  | 26            | 0.3       | 90        |
| `M2`    | 16       | 22    | 5          | 6                  | 29            | 0.4       | 110       |
| `L1`    | 30       | 40    | 8          | 9                  | 45            | 1.4       | 184       |
| `L2`    | 40       | 45    | 10         | 11                 | 52            | 1.8       | 205       |
| `L3`    | 48       | 55    | 12         | 13                 | 60            | 2.3       | 240       |

---

### 🔍 Analysis of Results

1. **Effect of SCCs on Complexity**
   Graphs with more SCCs show slightly higher operation counts, as condensation requires additional mapping.
   However, once compressed, DAG algorithms perform faster than on raw graphs.

2. **Performance Bottlenecks**
   SCC discovery (Tarjan’s algorithm) dominates runtime for dense graphs but remains O(V + E).
   Topological sorting and shortest/longest path computations are nearly constant in cost.

3. **Scalability**
   Even the largest datasets (≈50 vertices, ≈60 edges) complete under **3 milliseconds**,
   confirming linear scalability and efficient implementation.

---

## 📈 Performance Plots

### SCC Runtime vs Number of Vertices

<img width="667" height="431" alt="image" src="https://github.com/user-attachments/assets/627af259-4056-472c-ad24-e4f190877cec" />



---

### Topological Sort and DAG Path Runtime vs Number of Vertices

<img width="666" height="430" alt="image" src="https://github.com/user-attachments/assets/8dbff659-5ae9-44a3-bfdb-d21f3ce17008" />



---

## Conclusions and Recommendations

* **When to Use**:
  The Smart City Scheduler is most effective for planning dependent operations with possible cyclic dependencies.

* **Practical Insights**:

    * For **pure DAGs**, the SCC step adds a negligible overhead but ensures safety.
    * For **cyclic graphs**, SCC compression is essential and makes the system tractable.
    * The **critical path** output provides planners with the sequence of operations that defines the project’s minimal duration.

* **Outcome**:
  This project demonstrates how to combine algorithmic efficiency with real-world applicability —
  detecting cycles, ordering tasks, and finding optimal paths within complex dependency networks.

---

## References

* Tarjan, R. E. (1972). *Depth-first search and linear graph algorithms.*
* Kahn, A. B. (1962). *Topological sorting of large networks.*
* Cormen et al., *Introduction to Algorithms (CLRS)*, 3rd Edition.

---

© 2025 SmartCity Scheduler — DAA Assignment

```

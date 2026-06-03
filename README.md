# LearningConcurrencyInJava

## 📖 Overview

This project is a structured collection of examples and experiments designed to explore Java concurrency from fundamentals to advanced mechanisms.

It not only demonstrates how to use concurrency APIs, but more importantly explains how they work internally, including CPU-level behavior, memory models, synchronization, and high-level abstractions.

The goal is to build a complete understanding of:

> How concurrency works → Why it behaves this way → When to use each approach

---

## 📂 Project Structure

```
src/
├── AQS                      # AbstractQueuedSynchronizer (custom locks, synchronizers)
├── atomic                   # Atomic classes (CAS, AtomicInteger, LongAdder, etc.)
├── base                     # Fundamental concepts (CPU cache, locality, memory behavior)
├── BlockingQueueDemo        # BlockingQueue and producer-consumer patterns
├── Executor                 # Thread pool and Executor framework demos
├── HashMap                  # HashMap concurrency issues & ConcurrentHashMap comparison
├── JMMCharacteristicsIssues # Java Memory Model (visibility, ordering, reordering)
├── sync                     # Synchronization (synchronized, Lock, etc.)
├── Unsafe                   # Low-level operations (CAS, memory, thread control)
```

---

## 🚀 Key Topics Covered

### 1. Base Concepts (Foundation Layer)

This module focuses on fundamental concepts from computer architecture that directly impact concurrency behavior.

Topics include:

* Temporal Locality (time locality)
* Spatial Locality (space locality)
* CPU cache behavior
* Memory access patterns
* Performance fundamentals

These concepts form the theoretical foundation for understanding concurrency, especially in relation to the Java Memory Model (JMM) and CPU-level optimizations.

---

### 2. Unsafe & Low-Level Mechanisms

* Direct memory access
* CAS (Compare-And-Swap)
* Thread parking (park/unpark)
* Foundation of Atomic and AQS

---

### 3. Atomic Operations

* AtomicInteger / AtomicReference
* CAS-based lock-free programming
* LongAdder vs AtomicLong (high concurrency optimization)

---

### 4. AQS (AbstractQueuedSynchronizer)

* Custom lock implementation
* Understanding how:

    * ReentrantLock
    * Semaphore
    * CountDownLatch
      are built internally

---

### 5. Synchronization

* synchronized vs ReentrantLock
* Lock design and trade-offs
* Blocking vs non-blocking strategies

---

### 6. Java Memory Model (JMM)

* Visibility issues
* Instruction reordering
* Happens-before rules
* Memory consistency problems

---

### 7. BlockingQueue

* Producer-consumer pattern
* Thread coordination using queues
* Different queue implementations and behaviors

---

### 8. Executor Framework

* ThreadPoolExecutor internals:

    * Core threads → Queue → Max threads → Rejection
* ScheduledThreadPoolExecutor:

    * Delayed tasks
    * Periodic execution
* ForkJoinPool:

    * Divide-and-conquer
    * Work-stealing algorithm

---

### 9. HashMap Concurrency Issues

* Data inconsistency under concurrent access
* JDK 1.7 infinite loop issue (resize + head insertion)
* JDK 1.8 improvements (tail insertion + tree structure)
* Comparison with ConcurrentHashMap

---

## 🧠 Concurrency Knowledge Chain

This project follows a bottom-up learning path:

```
CPU / Cache / Locality
        ↓
    Unsafe (CAS, Memory Ops)
        ↓
    Atomic & AQS
        ↓
    Lock / Synchronization
        ↓
    ThreadPool / Executor
        ↓
    Concurrent Collections
```

---

## ⚠️ Important Notes

* Some demos intentionally demonstrate unsafe behavior (e.g., HashMap concurrency issues)
* Results may vary due to thread scheduling and system conditions
* Unsafe APIs are used for educational purposes only
* Parallel execution is not always faster — overhead must be considered

---

## 🎯 Learning Goals

* Understand concurrency at system level (not just API usage)
* Build intuition about performance and trade-offs
* Learn how high-level abstractions are built from low-level primitives
* Identify and avoid common concurrency pitfalls

---

## 💡 Key Insight

> Concurrency is not just about “making things faster” —
> it is about balancing correctness, performance, and complexity.

---

## 🚧 Future Improvements

* Performance benchmarks (Atomic vs LongAdder, thread pool tuning)
* Visual diagrams (AQS, thread pool workflow)
* Real-world scenarios (rate limiting, scheduling systems)
* ForkJoin vs ThreadPoolExecutor comparisons

---

## 👨‍💻 Author

This project is built as part of a deep dive into Java concurrency,
focusing on both theoretical understanding and practical implementation.

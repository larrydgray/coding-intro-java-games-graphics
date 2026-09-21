# Introduction to Data Structures and Java Collections

An outline/guide (the author's own words: compiled from four data structures textbooks, and
explicitly labeled "version 0.1 alpha") introducing the concepts behind this module's code —
read this first, then see [`ds.lists`](src/net/sf/sdz/ds/lists/README.md) and
[`ds.iterator`](src/net/sf/sdz/ds/iterator/README.md) for the actual `SingleLinkedList`
implementation and its tests.

## An Object Is a Data Structure

Objects are data: in OOP, an object packages *data* and *related program logic* together. Java's
primitives (`byte`, `short`, `int`, `long`, `float`, `double`, `boolean`, `char`) are the most
basic data types; a `String` is an object built from `char`s. All primitives ultimately break
down to bytes, so the most basic data structure is arguably a series of bytes — an **array**.

### Primitive sizes

| Type | Size | Range / notes |
|---|---|---|
| `boolean` | ~1 byte (JVM-dependent) | on/off |
| `byte` | 1 byte | -128 to 127 |
| `short` | 2 bytes | -32,768 to 32,767 |
| `int` | 4 bytes | -2,147,483,648 to 2,147,483,647 |
| `long` | 8 bytes | -9.2 quintillion to 9.2 quintillion |
| `float` | 4 bytes | 6-7 significant digits |
| `double` | 8 bytes | 15 significant digits |
| `char` | 2 bytes | 0 to 65535 (Unicode) |
| reference | 4 bytes (32-bit JVM) / 8 bytes (64-bit JVM) | |

A Java reference is like a pointer in that its value is a memory location, but unlike a C
pointer you can never see, alter, or do math on that value directly — you can only assign it
`null`, `new` it, or copy another reference's value. Method references (`Object::methodName`,
via lambdas) are a special kind of object reference too, though not first-class functions the
way JavaScript treats them.

For estimating the size or timing of something without a profiler, see
[`sdz-profiling`](../sdz-profiling/src/net/sf/sdz/profiling/README.md) (`ProfilingMemory`/
`ProfilingTime`) — the same `Runtime`-subtraction and `currentTimeMillis()`-bracketing
techniques mentioned in the original article.

## Code Efficiency

Big-O notation describes worst-case time/space complexity; best/average/worst-case analysis and
IDE profiler tools help reveal bottlenecks. As a newcomer, don't over-worry about this — the
Collections API is already well-optimized for most needs. Priority order: make it **work**,
**right**, **fast/small**, **cheap**.

## Objects and Arrays

An object is a collection of primitive/reference values — like a data record (name:value pairs
in a given order). A **class** is the blueprint; static members belong to the class itself and
aren't copied per-instance, while instance fields are. An **array** is a linear, contiguous,
zero-indexed list of a single type — an array of primitives is a sequence of numbers, an array
of objects is like a database table (each object a row). Arrays and objects are the two most
basic data structures everything else builds on.

## Files and Streams

A file is also a data structure at its core — a series of bytes, converted to/from primitives
or objects as it's read or written. A **stream** is not itself a data structure; it's just a
flow of data connecting files, memory, and other computers to each other. See
[`sdz-data`](../sdz-data/src/net/sf/sdz/data/files/README.md) for the file I/O examples this
leads into.

## Complex Data Structures, at a Glance

- **Basic structures:** Arrays, Lists, Maps (hash table), Sets, Heaps (priority queue),
  Dictionaries (hash table), Trees, Graphs.
- **Access patterns:** random access vs. sequential.
- **Operations:** searching, sorting, traversing, recursion, randomization.

### Synchronized vs. not

Java originally had synchronized (thread-safe) collection classes, which carried an efficiency
cost — hence `Vector` (synchronized) vs. `ArrayList` (not).

### Arrays

Where you'll start with data structures generally. `Arrays` (search/sort utility methods),
`ArrayList` (a growable array wrapper with insert/delete and a `ListIterator`). You can build
stacks, queues, and even trees on top of arrays.

### Iterator vs. loops

`Iterator` lets you step through a list with `hasNext()`/`next()` instead of a manual `for`
loop. For sorting, objects need `Comparable`/`compareTo()`. Compare with this module's own
hand-rolled `Iterator<E>` interface in [`ds.iterator`](src/net/sf/sdz/ds/iterator/README.md).

### Lists, Queues, Stacks

- **List:** `ArrayList`/`Vector` wrap a static array; `LinkedList` (and its double-linked,
  circularly-linked, and sorted variants) are dynamic — they grow/shrink as needed. This
  module's `SingleLinkedList` is exactly this category.
- **Queue:** first-in-first-out (FIFO) — e.g. a keyboard input buffer, a game's production
  worklist. A **priority queue** is effectively several queues, one per priority level, worked
  highest-priority-first.
- **Stack:** last-in-first-out (LIFO) — like a stack of papers where you only ever work from the
  top. Java's own exception call-trace is a stack. The author previously used a stack-based
  algorithm to generate a volcanic-island-like 3D surface for the Java Games and Graphics
  project.
- **Heap:** not a memory heap — a balanced tree with the largest (or smallest) value at the top.

### Hash Tables, Maps, Sets

Hash tables/maps/dictionaries all store key:value pairs (keys generally unique) and are fast at
lookup. Java's flavors: `HashMap`, `Hashtable`, `LinkedHashMap`, `TreeMap`, `WeakHashMap`,
`EnumMap`. A **Set** contains no duplicates (per `equals()`) and at most one `null` — arguably
more a data *constraint* than a structure in its own right. Java's flavors: `HashSet`,
`LinkedHashSet`, `TreeSet`, `EnumSet`.

### Trees

A branching structure — common in nature (river systems, lungs, ridgelines) and in software
(file systems, XML/HTML DOM). Java varieties mentioned: general, binary, AA, threaded,
balanced/AVL, non-binary, B-trees, binary search trees, multi-way search trees, (2,4) trees,
red-black trees, splay trees (bottom-up/top-down), plus `TreeMap`/`TreeSet`.

### Graphs

The author's pet peeve: "Graph" might have been better named Net/Network/Mesh/Web — a graph is
like a tree except branches can reconnect to other branches, so traversal can loop. Real-world
analogues: spider webs, highway systems, electrical grids, the internet. Leads into path-finding
(shortest path, critical path, backtracking) — directly useful for AI and game coding.

### Searching and Sorting

- **Searching:** linear/sequential, binary, interpolation, tree search. `Arrays` has built-ins;
  linear search is easy to hand-code with a loop or iterator.
- **Sorting:** Bubble, Selection, Insertion, Shell, Merge, Tree Selection (Tournament), Heap,
  Quick, Non-recursive Quick, Bucket, Radix — each with different best/worst/average-case
  performance, which is why so many exist. `Arrays` includes a built-in quicksort. Bubble sort
  is simple to hand-code and fast on nearly-sorted data.

### Recursion

A function calling itself — used heavily in sorting, tree traversal, and more. Anything
recursive can also be written iteratively with loops/conditions. **Indirect recursion**: function
A calls B, which calls A back.

---
*Ported from softwaredeveloperzone.com. Framed by the original author as a rough outline/guide
("version 0.1 alpha") rather than a polished reference — treat it as a map of the territory, not
the last word.*

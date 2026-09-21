# Profiling Java Efficiency

You don't need an IDE or a paid profiler to get a rough sense of memory or time cost — plain
`Runtime` and `System.currentTimeMillis()` calls get you there for quick, informal checks.

## Reference numbers (from the article)

Rough primitive sizes to keep in mind when eyeballing results:

| Type | Size |
|---|---|
| `boolean` | ~1 byte (JVM-dependent) |
| `byte` | 1 byte |
| `short` | 2 bytes |
| `int` | 4 bytes |
| `long` | 8 bytes |
| `float` | 4 bytes |
| `double` | 8 bytes |
| `char` | 2 bytes |
| object reference | 4 bytes (32-bit JVM) / 8 bytes (64-bit JVM) |

A modern 64-bit JVM's minimum object overhead is 16 bytes (a 12-byte header, padded to a
multiple of 8). Actual `Integer` object size in practice tends to land around 16–32 bytes
depending on JVM/measurement method — sources disagree, which is itself the point: **measure
on your own JVM rather than trusting a number from an article.**

## ProfilingMemory

Estimates per-object memory cost: record used memory (`Runtime.totalMemory() -
Runtime.freeMemory()`) before and after allocating a batch of objects, subtract, divide by the
count.

```java
long before = getBytesUsed();
makeListOfInts();       // allocates numberObjects Integers
long after = getBytesUsed();
long perObject = (after - before) / numberObjects;
```

**Caveat carried over from the article:** this only works cleanly in an isolated test — if
anything else in the JVM is allocating objects while you measure (other threads, background
work), the reading gets noisy. For a real application, pull the allocation you want to measure
out into its own small standalone test rather than measuring in place.

## ProfilingTime

Same bracketing idea, but for wall-clock time: capture `System.currentTimeMillis()` before and
after an operation and subtract. Here it times building a list of random `Integer`s and then
sorting it (`Collections.sort`), at 10,000,000 elements by default — the article's own
measurements showed a 10x increase in object count costing roughly 77x on build time and 20x on
sort time, illustrating that costs don't scale linearly and are worth actually measuring rather
than assuming.

---
*Ported from softwaredeveloperzone.com. The original article reused the class name `Profiling`
for both variants shown here; renamed to `ProfilingMemory`/`ProfilingTime` to allow both in the
same package.*

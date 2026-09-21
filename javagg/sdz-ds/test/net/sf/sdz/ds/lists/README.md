# TestNG SingleLinkedList Tests

`SingleLinkedListNGTest` is a full TestNG suite for
[`SingleLinkedList`](../../../../../src/net/sf/sdz/ds/lists/SingleLinkedList.java), covering
every `LinkedList`/`Iterator` method against the empty/one-item/two-item/many-item cases:
`insertHead`, `insertTail`, `removeHead`, `removeTail`, `reset`, `next`, `nextElement`,
`insertAfter`, `insertBefore`, `delete`, `remove`, `hasNext`, `size`, `empty`, `head`, `tail`,
`get`, `set`.

`@BeforeMethod setUpMethod()` seeds a fresh 3-element list (`1, 2, 3` via three `insertHead`
calls, so head-to-tail order ends up `1, 2, 3`) before each test that needs one.

**Setup:** this requires the TestNG library on the classpath — it isn't currently in this
project's `lib/` folder (only `jdom-2.0.6.1.jar` is there). Download `testng-x.y.z.jar` (and its
transitive deps — TestNG needs a few, e.g. `jcommander`) from
[testng.org](https://testng.org/) or Maven Central and add it to `lib/`, same as the
[SQLite/H2 setup](../../../../../../sdz-data/src/net/sf/sdz/data/db/README.md) for the JDBC
examples.

Ported largely as-is from the original NetBeans-generated test class — it uses raw
`SingleLinkedList` (no generic type parameter) throughout, matching the author's style rather
than being tightened up to `SingleLinkedList<Integer>`.

---
*Ported from softwaredeveloperzone.com.*

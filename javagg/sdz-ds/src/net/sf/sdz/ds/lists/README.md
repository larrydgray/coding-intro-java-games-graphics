# SingleLinkedList, Iterator and Unit Test

First article in a data structures series: a **singly** linked list — nodes with only
forward-pointing pointers. This list is also an `Iterator` (design pattern), with a `reset()`
method to move the position pointer back to the first node. The list only exposes each node's
element `E`, never the nodes themselves, and works with any element type `E` (including
primitives, via autoboxing).

Good uses for a linked list: building blocks for other data structures (**Stacks**, **Queues**,
possibly **Graphs**). It can grow until you run out of memory, and insertions/removals are
efficient — unlike an array, there's no shifting of other elements. The trade-off: finding the
nth element is less efficient than with an array, since you have to walk the list from the head.

## Interfaces

- **`Nodes<E>`** — base interface for node types in any dynamic structure (linked list, tree,
  graph): `getElement`/`setElement`, `getNext`/`setNext`.
- **`Position<E>`** — `getElement()` on the current node; laid groundwork for a later `Sequence`
  data structure (not part of this article).
- **[`net.sf.sdz.ds.iterator.Iterator<E>`](../iterator/Iterator.java)** — the classic
  Iterator design pattern: `hasNext()`/`next()`. Deliberately a custom interface in its own
  package rather than `java.util.Iterator`, so `SingleLinkedList` can implement both this and
  its own `nextElement()`-based traversal (see below) without a naming collision — imported
  explicitly (`import net.sf.sdz.ds.iterator.Iterator;`) where used.
- **`LinkedList<E>`** — the core contract: `size()`/`empty()` (List-pattern basics),
  `head()`/`tail()` (peek without removing), `removeHead()`/`removeTail()` (remove and return),
  `insertHead()`/`insertTail()`/`insertBefore()`/`insertAfter()` (the last two relative to the
  current traversal position), `get()`/`set()` (read/replace at current position),
  `remove()`/`delete()` (remove at current position, with/without returning the value),
  `reset()` (traversal pointer back to head), and `nextElement()` (advance the traversal
  pointer, `boolean`-returning — see below for how this differs from `Iterator.next()`).

## Classes

**`Node<E>`** implements `Nodes<E>`: holds an element and a pointer to the next node. Two
constructors — empty, or given an element and next node (tail nodes get `next = null`).

**`SingleLinkedList<E>`** implements both `LinkedList<E>` and `Iterator<E>`. Internally tracks
`head`, `tail`, a traversal `position`, a `temp` scratch node used during insert/remove, and a
`size` counter kept in sync by every mutating method.

There are two parallel ways to traverse the list, and they behave subtly differently:

- **`nextElement()`** (from `LinkedList`) — advances `position` and returns `false` once you've
  moved past the last node (`position` becomes `null`).
- **`next()`** (from `Iterator`, paired with `hasNext()`) — internally calls `nextElement()` and
  returns the element at the new position via a `currentElement` field. Because any node's
  element may legitimately be `null`, `next()` alone can't tell you whether you've reached the
  end — you must check `hasNext()` first. Calling `next()` past the end just returns a stream of
  nulls forever.

Most insert/remove operations have to special-case whether they're operating on an empty list,
a single-element list, the head, the tail, or a middle element — that branching is the bulk of
the implementation's complexity (see `insertAfter`, `insertBefore`, `delete`, `remove`,
`removeHead`, `removeTail`).

> **Fixed vs. the original article:** `delete()`'s head/tail branches used to fall through into
> shared cleanup code after calling `removeHead()`/`removeTail()` (which already update
> `position` and `size` themselves), patched over with a `size++` to cancel the double
> decrement. The fall-through also re-ran `position = position.getNext()`, which — whenever the
> new head wasn't also the tail (i.e. any list of 3+ elements) — advanced `position` one node
> too far, silently skipping the new head. `remove()` never had this problem since its
> equivalent branches `return` immediately instead of falling through. `delete()` now does the
> same. Covered by a regression test in `SingleLinkedListNGTest.testDelete()`.

Unit tests for this class are covered in the follow-up article, ported alongside this one — see
[`SingleLinkedListNGTest`](../../../../../../test/net/sf/sdz/ds/lists/SingleLinkedListNGTest.java)
under `sdz-ds/test/`.

---
*Ported from softwaredeveloperzone.com.*

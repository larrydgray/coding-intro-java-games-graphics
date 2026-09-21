# Handling Keyboard Input and Mouse Events

Three input examples, converted from the original article's Java **Applets** (which relied on
`appletviewer` — removed since JDK 11 — and the legacy pre-1.1 AWT event model) to plain
`java.awt.Frame` windows using the modern `KeyListener`/`MouseListener`/`MouseMotionListener`
interfaces. Each is a standalone `main()` you can just run.

## Keyboard Input

`KeyTest` opens a `Frame` and prints which key was pressed (special-casing UP arrow and F1) to
the console, mirroring the result in a `Label` at the bottom of the window (standing in for the
applet's status bar in the original).

## Mouse Move and Click Input

`MouseTest` logs press/release/move/drag/enter/exit events with their `(x, y)` position to the
console.

## Mouse Drag and Drop: Stick Man

Same sprite hierarchy as the original article, unchanged since it only depends on
`java.awt.Graphics`, not the Applet API:

- `Sprite` — abstract base: `visible`/`active` flags, `suspend()`/`restore()`, abstract
  `paint(Graphics)`/`update()`.
- `Sprite2D` — adds position (`locx`/`locy`), `Color`, and fill state.
- `StickManSprite` — draws a vector stick figure (head, torso, two arms, two legs) at
  `(x, y)` sized `w` × `h`, filled or outlined depending on `fill`.
- `DragStickMan` — adds `draggable` state, `inside(x, y)` hit-testing against the bounding box,
  `translate(x, y)` to move it, and `grow()`/`shrink()` to resize it by one pixel per call.

`DragStickManFrame` (replacing the original `DragStickManApplet`) ties it together in a
`Frame`: mouse-down inside the stick man starts a drag, mouse-drag translates it by the delta
since the last event, mouse-up ends the drag; Right arrow grows the stick man, Left arrow
shrinks it.

> The original applet's `paint()` had a bug — `int width = (bounds().width = stringWidth) / 2;`
> assigned into a copy of the bounds instead of centering the title text. Since this class was
> already being rewritten for the Frame conversion (not just ported verbatim), that's fixed here
> using `getInsets()` and `getWidth()` to properly center the string over the window.

Like the original, this doesn't double-buffer, so you may see some flicker while dragging —
not addressed here since the source didn't either.

---
*Ported from softwaredeveloperzone.com; entry points rewritten from Applet to AWT Frame.*

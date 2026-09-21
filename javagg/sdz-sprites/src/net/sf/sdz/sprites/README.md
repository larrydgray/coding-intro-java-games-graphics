# Organizing Sprites, Tiles, Textures etc on Image Strips or Grids

A tool for organizing sprite/tile images into image grids or strips — useful for game sprites,
simulation sprites, textures, or even CSS sprite sheets for web pages. Buffers hold images,
which can be divided into a grid of columns and rows; each cell (sprite) is a fixed pixel size
determined by the buffer's dimensions divided by its column/row count.

## Features (as of this 0.1 Alpha version)

- Load and save single images or sprite sets.
- Load images into one of 10 buffers.
- Paste from one buffer into a grid or image strip.
- Divide a blank image into columns, or columns and rows.
- Sprite size is derived from the grid's pixel size and its column/row count.
- Copy, paste, move, and clear sprites on a single grid or strip.

Planned for future versions (not in this release): undo/redo, copy/paste/move *between*
buffers, a utility class for loading images and grabbing sprites/tiles/textures in your own
apps, a `Sprites` class extending this buffer concept into a sprite library, and a `Maps` class
for layered game maps (terrain, roads/rivers, units/buildings, etc).

## The Classes

- **`SpriteOrganizer`** — the main `JFrame`; entry point that wires everything together.
- **`ImagePanel`** — the viewing/editing panel: draws the current buffer's image, an optional
  gridline overlay, and the selection box. Hosts the inner `StatsPanel` (via
  `SpriteOrganizer.StatsPanel`) which shows buffer info at the bottom of the frame.
- **`ImagePanelKeyListener`** — keyboard shortcuts (see below).
- **`ImagePanelMouseListener`** — mouse click handling (see below).
- **`ImageBuffers`** — the core model: an array of `ImageBuffer` (each holding a `BufferedImage`
  plus grid/selection state), with all the copy/paste/move/delete/load/save operations.

## Running It

Compile all five classes and run `SpriteOrganizer.main()`. It expects an icon file named
`sprite.png` in the working directory (`setIconImage(...)`) — the article's own screenshot
shows a small space-invader icon for this; supply your own `sprite.png` (the actual image
wasn't recoverable from the article text export, only referenced by URL).

## Controls

**Keyboard** (`ImagePanelKeyListener`):

| Key | Action |
|---|---|
| `1`–`9`, `0` | Select buffer 0–9 (buffers are 0-indexed internally; `1` = buffer 0, ..., `0` = buffer 9 — the status bar may display 1-based numbers even though it's really buffer 0) |
| `g` | Turn the current buffer into a grid — prompts for columns and rows |
| `o` | Toggle the gridline overlay on/off |
| `c` | Clear a cell — prompts for column and row (0-indexed) |
| `m` | Move a cell — prompts for from-column/row and to-column/row |
| `p` | Paste the current buffer into a cell of another buffer — prompts for destination buffer, column, row |
| `l` | Load an image into the current buffer — prompts for a filename (same folder as the app) |
| `s` | Save the current buffer to an image file — prompts for a filename |
| `n` | Create a new blank buffer — prompts for width and height in pixels |

**Mouse** (`ImagePanelMouseListener`), once a cell is selected:

| Click | Action |
|---|---|
| Left-click | Select a cell (shows the selection box) |
| Shift + left-click | Copy the selected sprite onto the clicked cell |
| Right-click | Clear the clicked cell |
| Shift + right-click | Move the selected sprite onto the clicked cell |

## Walkthrough (from the article)

Say you want 50×50 pixel sprites — e.g. six frames of a stick man walking right, drawn in any
paint program and saved as `stickman1.png` .. `stickman6.png` in the app's folder. Load each
into buffers 1–6 (keys `2`–`7`). Switch to buffer 0 (key `1`), press `n` and enter `300`/`150`
for a blank canvas sized for 6 columns × 3 rows of 50×50 cells, then press `g` and enter
`6`/`3` to lay down the grid. Go to each source buffer in turn and press `p`, giving `0` as the
destination buffer and the target column/row (`0,0` then `1,0`, `2,0`, etc. to lay the frames
out left to right). From there you can rearrange with select + shift-click (copy) or
shift-right-click (move), clear a cell with right-click, and finally press `s` while buffer 0 is
in view to save the assembled sprite strip to a file.

---
*Ported from softwaredeveloperzone.com. Two small quirks preserved from the original source:*
*`ImagePanel.paintComponent` writes `if (buffers.buffer() == null) ; else g.drawImage(...);` —*
*an empty-statement `if` that reads oddly but is logically correct (draws only when there's a*
*buffer to draw); and `ImagePanelKeyListener`'s `case 'o':` is missing a `break` before falling*
*into `default:`, which is harmless here since `default` does nothing.*

# Coding Using Java — Example Games

Thirteen simple games built on a small shared engine (`GameScreen`,
`ScreenBuffer`, `Cell`, `FontType`, `MiniGraphics`, `InputHandler`), adapted
from the `javagg` (Java Games and Graphics) project.

Most render on a grid of colored characters — like an old text-mode
console — which keeps the games short and easy to read for beginners; a
few layer in pixel-precise vector graphics (`MiniGraphics`, including a
real turtle-graphics API) on top for things a character grid can't do,
like a rotating ship or a curved snake trail.

## Games

- **WalkAndCollect.java** — move the `@` with the arrow keys or WASD,
  grab the `$` to win, avoid the `g` (goblin).
- **CatchTheFallingItem.java** — slide the `=` paddle left/right to catch
  the falling `o`. Each catch scores a point.
- **ShootTheTargets.java** — slide the `^` turret left/right, press SPACE
  to fire a bullet straight up, and hit the big `X` targets drifting
  across the screen. Each hit scores a point and respawns the target.
- **RoomToRoom.java** — a 2x2 grid of rooms (Meadow, Desert, Cave, Lake).
  Walk the `@` off the edge of one room and you appear on the opposite
  edge of the next one, like the overworld screens in old Zelda-style
  games.
- **SideScrollerShooter.java** — fly the `>` through a world much wider
  than the screen; the camera follows you and keeps you roughly centered.
  Press SPACE to shoot. Red `<` enemies always fly straight at you;
  magenta `W` enemies patrol back and forth between two points.
- **MoonBuggy.java** — the buggy `o^o` drives forward across the moon on
  its own; you only control jump timing. Press SPACE to leap over
  craters in the ground. Land in one while grounded and it's game over;
  reach the far side and you win.
- **TopDownHelicopter.java** — fly the `H` in any of 8 directions through
  a world bigger than the screen in both directions; the camera keeps
  you centered horizontally and vertically. Press SPACE to shoot in
  whichever direction you last moved. Orange `B` buildings sit still on
  the ground; magenta `X` enemy choppers patrol back and forth along a
  fixed horizontal or vertical line.
- **TextAdventure.java** (+ `adventure.xml`) — "Portals Adventure": type
  commands (`n`/`s`/`e`/`w`/`enter`/`l`/`r`, `look`, `examine`, `get`,
  `help`, `quit`) to explore a small hand-written world loaded from XML.
  The only example that isn't Timer-driven — it reacts to typed input
  instead of ticking — and the first to use `ScreenBuffer`'s terminal
  `print`/`println` scrollback instead of redrawing the whole screen
  every frame.
- **Asteroids.java** — old-style vector Asteroids, drawn entirely with
  `MiniGraphics.line()` outlines (no fills) instead of the character
  grid. Rotate with arrows/A-D, thrust with up/W, fire with SPACE.
  Asteroids split into two smaller ones when shot, everything wraps
  around the screen edges, and you get 3 lives with a brief blinking
  invulnerability after each hit. The first example to use pixel
  coordinates (`GameScreen.getPixelWidth/Height()`) instead of the
  column/row grid.
- **Snake.java** — the classic Nibbles/Snake: steer with arrows/WASD, eat
  the red `$` to grow and score, don't hit a wall or your own tail. The
  snake moves on its own every tick (no need to hold a key), and a
  direct reversal is ignored so you can't steer into your own neck. All
  the grid logic (cells, collision, growth) works the same as any other
  grid game here, but the body is rendered by walking the turtle through
  the snake's cell path from tail to head every frame — the trail it
  leaves behind (with `MiniGraphics`'s pen width) *is* the snake, which
  is about as natural a use of turtle graphics as this course gets.
- **Snake2.java** — Snake with continuous free-angle steering (closer to
  Slither.io/Tron than Nibbles) instead of Snake.java's grid-locked 90°
  turns — genuinely different architecture, not just a different look.
  Left/Right turn, Up/Down speed up or slow down within a range (starts
  slower than a plain fixed pace), and each food eaten adds 2
  segments/points instead of 1. The play field is ringed by a thick blue
  "water" band, drawn below the HUD text rather than under it — touch it
  and it's an instant death, same as Snake.java's walls, but food always
  spawns a wide margin away from it, so reaching food never forces a
  turn tighter than the snake can actually make. This is the course's
  clearest turtle graphics example: the turn keys call the turtle's own
  `left()`/`right()` directly, turning its real internal heading, and
  the game reads the turtle's own position/angle back out (new
  `MiniGraphics.getTurtleX/Y/Angle()` getters) as the authoritative
  state for collision and the head marker. The body itself is drawn
  with plain thick line segments between recorded positions rather than
  through that same turtle's pen — reusing one pen as both "live
  steering state" and "stateless replay of history" would corrupt the
  heading steering depends on. Trail-trimming and the self-collision
  safe zone are both measured by real path distance rather than a fixed
  point count, which matters once speed varies tick to tick.
- **Artillery.java** — 2-player turn-based artillery duel (Scorched Earth
  style, not a Worms clone — each tank stays put, no movement or weapon
  variety) on destructible, randomly generated hills, hotseat on one
  keyboard. Arrows/WASD adjust aim angle and power, SPACE fires; a hit
  craters the terrain and damages any tank in the blast, tapering with
  distance. This is the course's real turtle graphics example — the
  thick aim-line and the radiating explosion burst are both drawn with
  `MiniGraphics`'s turtle API (`jumpTo`, `setAngle`, `forward`), which
  suits "point in a direction and draw a line from here" much better
  than it suited Asteroids/Othello. The small rotating cannon on the
  active tank still uses Asteroids-style direct rotated-vertex math,
  since turtle mode is awkward for several independently-rotating shapes
  sharing one pen. Added real pen-width support to `MiniGraphics` along
  the way (`line(..., width, color)`, `penWidth(int)` on the turtle)
  since a 1-pixel line was too thin to read as an aim indicator.
- **Othello.java** — Othello/Reversi against a computer opponent, on a
  green board with thin brown grid lines and round black/white stones —
  the grid lines and stones are drawn with `MiniGraphics` (thin pixel
  lines, filled circles) on top of the plain green character cells,
  rather than as character-grid blocks. Move the `v`/`>` selection
  pointers with arrows/WASD, SPACE to place a stone on a highlighted
  legal square. The "AI" isn't a real search — it scores each of its
  legal moves by flip count plus a fixed positional weight table
  (corners great, the squares next to a corner a trap, edges solid) and
  plays the best-scoring one, which is usually enough to be a real
  opponent without minimax or lookahead.

## Running

No build tool needed — plain `javac`/`java`:

```bash
javac *.java
java WalkAndCollect
```

or

```bash
javac *.java
java CatchTheFallingItem
```

## Engine files

`GameScreen`, `ScreenBuffer`, `Cell`, `FontType`, and `MiniGraphics` are the
reusable engine — you shouldn't need to edit them to build a new game.
`InputHandler` tracks which keys are currently held down; call
`isDown(KeyEvent.VK_LEFT)` etc. on it during your game loop.

## License

See [LICENSE](LICENSE). These examples are for enrolled students' personal
educational use, on their own computer or within their own Replit project — see
the license for what that does and doesn't cover.

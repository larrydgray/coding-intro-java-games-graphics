# Sound Effects and Musical Notes

Two independent demos: playing WAV sound effects via `javax.sound.sampled`, and playing random
MIDI notes via `javax.sound.midi`.

Useful sources for effect files: [freesound.org](https://freesound.org) (user-uploaded sound
effects) and [SoX](http://sox.sourceforge.net/) (command-line tool to convert whatever format
you find there into `.wav` for use here).

## Playing WAV Sound Effects

`PlaySoundEffects` loads a handful of `.wav` files (`bulldozer.wav`, `drill.wav`,
`mine_dig.wav`, `mine_place_explosion.wav` — plain mechanical/environmental sound effects,
not voice lines, so it reads as a generic effects demo rather than quoting a specific game;
retheme freely) each into its own `Clip`, and shows a Swing panel with one button per
effect that starts the clip, waits 3 seconds, then stops it:

```java
AudioInputStream in = AudioSystem.getAudioInputStream(new File("bulldozer.wav"));
Clip clip = AudioSystem.getClip();
clip.open(in);
...
clip.start();
Thread.sleep(3000);
clip.stop();
```

The `.wav` files themselves need to be supplied separately (they're not part of this repo) and
placed on the working directory when running the demo. Each file loads independently — missing
ones are skipped (with a note on the console) rather than the whole app failing to start over
one missing file, and if none are found the window still opens with a label saying what's
needed instead of nothing appearing at all. (The original article's set was a small
beaver-trapping-game effect set distributed as a linked `wav.zip` download that was never
recovered when this was ported — `swim1.wav`/`pond1.wav`/`splash1.wav`/`walking1.wav`/
`trapcatch1.wav`/`water1.wav`/`beaverwalk1.wav`/`trapplace1.wav`, the last of which had its
button commented out in the original source since it threw an exception due to its exact
format, never resolved. The four files above are simply a different set with the same
one-button-per-effect shape; swap in whatever `.wav` files you like the same way.) There's also
a lot of repetition here (one file/stream/clip/button per effect) that the original article
calls out as ripe for refactoring into a single reusable "sound effect" object — left as-is to
match the source.

## Playing MIDI Musical Notes

`SoundNotes` opens the system's default `Synthesizer` and lists all available `Instrument`s
(typically 128, General MIDI-style) to the console. A small Swing panel lets you:

- **Play** — play 10 random notes (`noteOn(note, 90)`, each explicitly turned off with
  `noteOff` after playing for 200-790ms) through MIDI channel 1.
- **Prev** / **Next** — step through instruments one at a time, loading each into the synth and
  updating a label with its name.
- **Random Instrument** — jump straight to any of the 128 instruments instead of stepping.

> The original source called `noteOn(note, 400)` — 400 is out of MIDI velocity's valid 0-127
> range — and never called `noteOff` per note, letting up to 10 random notes ring on top of
> each other by the time all 10 had fired. Together that produced loud, dissonant noise rather
> than 10 distinct notes; fixed to a normal velocity (90) with an explicit `noteOff` per note.
> The original's duration range (`Math.random()*100+1`, so 10-1000ms) could also cut a note off
> in as little as 10ms, before its envelope had time to finish, causing an audible click - raised
> to 200-790ms.

If notes still sound like continuous static regardless of instrument even after those fixes,
and a plain non-MIDI tone plays cleanly, that's most likely your environment rather than this
code - real-time MIDI synthesis is far more sensitive to system audio timing than a pre-rendered
clip. Confirmed cause here was running inside a VirtualBox VM: switching the VM's audio driver
to Windows Audio Session helped somewhat, and running on bare metal was completely clean.

Untried extensions the original article notes: adjusting note velocity/volume, using multiple
voices for chords, switching soundbanks (only bank 0 is used here), and playing multiple
instruments at once across different MIDI channels.

> Both classes use a couple of deprecated-but-still-functional Swing APIs from the original
> (`frame.show()` instead of `setVisible(true)`, and accessing `EXIT_ON_CLOSE` through an
> instance rather than statically) — kept as-is for fidelity to the source.

---
*Ported from softwaredeveloperzone.com.*

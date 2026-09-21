# Sound Effects and Musical Notes

Two independent demos: playing WAV sound effects via `javax.sound.sampled`, and playing random
MIDI notes via `javax.sound.midi`.

Useful sources for effect files: [freesound.org](https://freesound.org) (user-uploaded sound
effects) and [SoX](http://sox.sourceforge.net/) (command-line tool to convert whatever format
you find there into `.wav` for use here).

## Playing WAV Sound Effects

`PlaySoundEffects` loads a handful of `.wav` files (a small beaver-trapping-game effect set —
`swim1.wav`, `pond1.wav`, `splash1.wav`, `walking1.wav`, `trapcatch1.wav`, `water1.wav`,
`beaverwalk1.wav`) each into its own `Clip`, and shows a Swing panel with one button per
effect that starts the clip, waits 3 seconds, then stops it:

```java
AudioInputStream in = AudioSystem.getAudioInputStream(new File("swim1.wav"));
Clip clip = AudioSystem.getClip();
clip.open(in);
...
clip.start();
Thread.sleep(3000);
clip.stop();
```

The `.wav` files themselves need to be supplied separately (they were originally distributed
as a linked `wav.zip` download, not part of the article's inline code) and placed on the
working directory when running the demo. `trapplace1.wav` is loaded but its button is commented
out in the original source — one file apparently threw an exception due to its exact format,
which was never resolved. There's also a lot of repetition here (one file/stream/clip/button
per effect) that the original article calls out as ripe for refactoring into a single
reusable "sound effect" object — left as-is to match the source.

## Playing MIDI Musical Notes

`SoundNotes` opens the system's default `Synthesizer` and lists all available `Instrument`s
(typically 128, General MIDI-style) to the console. A small Swing panel lets you:

- **Play** — play 10 random notes (`noteOn(note, 400)`) of random duration through MIDI channel 1.
- **Prev** / **Next** — cycle through instruments, loading each into the synth and updating a
  label with its name.

Untried extensions the original article notes: adjusting note velocity/volume, using multiple
voices for chords, switching soundbanks (only bank 0 is used here), and playing multiple
instruments at once across different MIDI channels.

> Both classes use a couple of deprecated-but-still-functional Swing APIs from the original
> (`frame.show()` instead of `setVisible(true)`, and accessing `EXIT_ON_CLOSE` through an
> instance rather than statically) — kept as-is for fidelity to the source.

---
*Ported from softwaredeveloperzone.com.*

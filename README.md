# Introduction to Coding Using Java: Games and Graphics - Example Programs

The example programs for the class
[Introduction to Coding Using Java: Games and Graphics](https://diamondstatebusiness.com/learning/programming/java/games-graphics)
from Diamond State Business Services, taught by Larry D. Gray.

Read the code, run it, change it, and see what happens. The goal isn't to master
Java - it's to get your feet wet.

## What's in here

| Folder | What it is |
|---|---|
| `basics/` | Small console programs: `HelloWorld`, `VonNeumann`, `Turing`, `IODemo`, `NetworkDemo` |
| `coding-using-java/` | Thirteen games built on a small shared engine (they open a window) |
| `javagg/` | The open-source Java Games and Graphics project (`jgg-*` modules) and the Software Developer Zone article examples (`sdz-*` modules) |
| `javafx/` | `HelloFX`, a tiny JavaFX window |
| `run.sh` | Builds and runs the `javagg` apps |

## What you need

1. **Java 21 with JavaFX.** Download **Azul Zulu, JDK FX, version 21** from
   [azul.com/downloads](https://www.azul.com/downloads/?package=jdk-fx). A plain JDK
   has no JavaFX, so make sure you choose the *JDK FX* package. Check it worked:

   ```
   java -version
   java --list-modules | grep javafx
   ```

   The second command should list modules such as `javafx.controls`. (In Windows
   Command Prompt or PowerShell, use `findstr` instead of `grep`.)

2. **Windows only: Git for Windows**, which includes **Git Bash**. The run scripts
   are shell scripts, so run them from Git Bash (not Command Prompt or PowerShell).
   On Linux you don't need anything extra. macOS is not tested.

## Get the files

Click **Code -> Download ZIP** on this page, then extract it to a short folder such as
`C:\java-class`. Or, if you use Git: `git clone` this repository. Avoid folders with
spaces or special characters in their names.

## Run things

Open a terminal in the folder (in Git Bash on Windows). `br` means *build and run*.

**Console programs**

```
cd basics
javac HelloWorld.java
java HelloWorld
```

**The games** (each opens a window)

```
cd coding-using-java
./run.sh list
./run.sh br Snake
```

**The javagg apps**

```
./run.sh list
./run.sh br awt3d
```

**The SDZ article examples**

```
cd javagg
./runsdz.sh list
./runsdz.sh br xmlreader
```

**JavaFX**

```
cd javafx
javac HelloFX.java
java HelloFX
```

## Good to know

- The sound examples need an audio device, and the graphics ones need a normal desktop
  (they will not open a window on a remote or headless server).
- **Database examples (`h2`, `sqlite`)** create small database files in
  `javagg/sdz-data/data/`. If you run one twice you'll get an error, because the example
  inserts the same rows again. Delete `h2test.db.mv.db` and `test2.db` in that folder
  before running them again.
- The scripts rebuild what they run, so any change you make to a source file shows up on
  the next `./run.sh br ...`.
- `java: command not found` means Java isn't installed or isn't on your PATH. A missing
  `javafx` module means you installed a plain JDK - install the **JDK FX** package.

## Licenses

- `coding-using-java/` (the games): course material for enrolled students - see
  [`coding-using-java/LICENSE`](coding-using-java/LICENSE).
- `javagg/` (including the Software Developer Zone examples): MIT - see
  [`LICENSE-MIT`](LICENSE-MIT).
- Third-party libraries in `javagg/lib/`: see [`NOTICE`](NOTICE).

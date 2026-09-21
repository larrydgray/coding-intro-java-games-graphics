#!/usr/bin/env bash
# ─────────────────────────────────────────────
#  SDZ Build & Run Script
#  Usage:
#    ./runsdz.sh list
#    ./runsdz.sh build+run <app>
#    ./runsdz.sh clean
#  Run from anywhere; lives in examples/javagg next to the sdz-* modules.
# ─────────────────────────────────────────────

# -- Portability: Linux, macOS-like shells, and Git Bash on Windows ----------
case "$(uname -s)" in
  MINGW*|MSYS*|CYGWIN*) PATHSEP=";"; winpath() { cygpath -m "$1"; } ;;
  *)                    PATHSEP=":"; winpath() { printf '%s' "$1"; } ;;
esac
# Write one quoted, Windows-safe path per line (for javac @argfiles)
write_list() { while IFS= read -r f; do printf '"%s"\n' "$(winpath "$f")"; done; }
# Only point at a Replit display when one exists
if [[ -S /tmp/.X11-unix/X1 ]]; then export DISPLAY=":1"; fi

# ── Paths ────────────────────────────────────
ROOT="$(cd "$(dirname "$0")" && pwd)"
OUT="$ROOT/out/sdz"
LIB="$ROOT/lib"

# ── App definitions ──────────────────────────
# APPS order is the display order.  Each app maps to:
#   MOD[app]  – module directory (compiled from <module>/src)
#   MAIN[app] – fully qualified main class
#   CWD[app]  – working dir for the run (data apps read relative files)
#   FX[app]   – "yes" if it is a JavaFX app (needs the JavaFX modules)
#   DESC[app] – one-line description
declare -A MOD MAIN CWD FX DESC
APPS=()
def() { # def name module main desc [cwd] [fx]
  APPS+=("$1"); MOD[$1]="$2"; MAIN[$1]="$3"; DESC[$1]="$4"; CWD[$1]="$5"; FX[$1]="${6:-no}"
}

def iso3d        sdz-3d-swing  net.sf.sdz.iso3d.Iso3D                         "Isometric 3D surface (Swing)"
def isotest      sdz-3d-swing  net.sf.sdz.iso3d.IsoTest                       "Iso3D test harness"
def iso3dfx      sdz-3d-fx     net.sf.sdz.iso3dfx.Simple3DFX                  "Isometric 3D surface (JavaFX/FXML)" "" yes
def mouse        sdz-input     net.sf.sdz.input.MouseTest                     "Mouse input test"
def keys         sdz-input     net.sf.sdz.input.KeyTest                       "Keyboard input test"
def stickman     sdz-input     net.sf.sdz.input.DragStickManFrame             "Drag the stick man"
def sprites      sdz-sprites   net.sf.sdz.sprites.SpriteOrganizer             "Sprite organizer"
def notes        sdz-sound     net.sf.sdz.sound.SoundNotes                    "MIDI/sampled note playback"
def effects      sdz-sound     net.sf.sdz.sound.PlaySoundEffects              "Sound effects player"
def proftime     sdz-profiling net.sf.sdz.profiling.ProfilingTime             "Timing profiler"
def profmem      sdz-profiling net.sf.sdz.profiling.ProfilingMemory           "Memory profiler"

# sdz-data programs read/write files relative to the working dir → run in sdz-data/data
D=net.sf.sdz.data
def textreader   sdz-data $D.files.AFileReader                    "Text file reader"                 data
def appender     sdz-data $D.files.AFileAppender                  "Text file appender"               data
def delimited    sdz-data $D.files.DelimitedFileReader            "Delimited file reader"            data
def typeddelim   sdz-data $D.files.TypedDelimitedFileReader       "Typed delimited file reader"      data
def props        sdz-data $D.files.PropertiesFileReader           "Properties file reader"           data
def propsreader  sdz-data $D.files.PropertiesReader               "Properties reader"                data
def propsrw      sdz-data $D.files.PropertiesReaderWriter         "Properties reader/writer"         data
def xmlreader    sdz-data $D.files.XMLReader                      "XML reader (adventure.xml)"       data
def xmlser       sdz-data $D.files.SerializeToXML                 "Serialize to XML"                 data
def xmldeser     sdz-data $D.files.DeserializeFromXML             "Deserialize from XML"             data
def objrw        sdz-data $D.files.ObjectWriterReader             "Object writer/reader"             data
def recordrw     sdz-data $D.files.RecordReaderWriter             "Record reader/writer"             data
def sheetreader  sdz-data $D.files.SheetReader                    "Sheet reader"                     data
def sheetrw      sdz-data $D.files.SheetReaderWriter              "Sheet reader/writer"              data
def filerw       sdz-data $D.files.AFileReaderWriter              "File reader/writer"               data
def filewriter   sdz-data $D.files.AFileWriter                    "File writer"                      data
def raf1         sdz-data $D.files.ReadWriteArrayRandomAccessFile  "Random-access array file v1"     data
def raf2         sdz-data $D.files.ReadWriteArrayRandomAccessFile2 "Random-access array file v2"     data
def raf3         sdz-data $D.files.ReadWriteArrayRandomAccessFile3 "Random-access array file v3"     data
def raf4         sdz-data $D.files.ReadWriteArrayRandomAccessFile4 "Random-access array file v4"     data
def raf5         sdz-data $D.files.ReadWriteArrayRandomAccessFile5 "Random-access array file v5"     data
def raf6         sdz-data $D.files.ReadWriteArrayRandomAccessFile6 "Random-access array file v6"     data
def h2           sdz-data $D.db.ReadWriteH2                       "H2 database (needs H2 jar in lib/)"     data
def sqlite       sdz-data $D.db.ReadWriteSQLite                   "SQLite database (needs sqlite jar in lib/)" data

# ── Helpers ───────────────────────────────────
list_apps() {
  echo ""
  echo "Available apps:"
  local app
  for app in "${APPS[@]}"; do
    printf "  %-12s %-14s %s\n" "$app" "(${MOD[$app]})" "${DESC[$app]}"
  done
  echo ""
  echo "Commands:"
  echo "  ./runsdz.sh build+run <app>   compile + launch in one step (alias: br)"
  echo "  ./runsdz.sh build     <app>   compile the app's module only"
  echo "  ./runsdz.sh run       <app>   run only (must build first)"
  echo "  ./runsdz.sh build-all         compile every sdz module"
  echo "  ./runsdz.sh list              show this list"
  echo "  ./runsdz.sh clean             remove compiled sdz output"
  echo ""
  echo "Extra args after <app> are passed to the program."
  echo "Examples:"
  echo "  ./runsdz.sh br iso3d"
  echo "  ./runsdz.sh br iso3dfx"
  echo "  ./runsdz.sh br xmlreader"
  echo ""
}

require_app() {
  if [[ -z "$1" || -z "${MAIN[$1]}" ]]; then
    echo "✗ Unknown app: ${1:-<none>}"
    list_apps
    exit 1
  fi
}

# Classpath: module output + any jars in lib/
classpath() {
  local cp="$(winpath "$OUT/$1")" jar
  for jar in "$LIB"/*.jar; do
    [[ -f "$jar" ]] && cp="$cp${PATHSEP}$(winpath "$jar")"
  done
  echo "$cp"
}

# JavaFX: use the JDK's bundled FX (Zulu FX) unless PATH_TO_FX / --module-path is supplied.
fx_args() {
  if [[ -n "$PATH_TO_FX" ]]; then
    echo "--module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml"
  else
    echo "--add-modules javafx.controls,javafx.fxml"
  fi
}

build_module() {
  local mod="$1" fx="$2"
  local src_dir="$ROOT/$mod/src"
  local out_dir="$OUT/$mod"

  if [[ ! -d "$src_dir" ]]; then
    echo "✗ No source directory: $src_dir"
    return 1
  fi

  rm -rf "$out_dir"
  mkdir -p "$out_dir"

  local list
  list="$(mktemp)"
  find "$src_dir" -name "*.java" | write_list > "$list"

  echo "► Compiling $mod ($(wc -l < "$list") files) → $out_dir"
  # shellcheck disable=SC2046
  if javac -nowarn -d "$(winpath "$out_dir")" -cp "$(classpath "$mod")" $( [[ "$fx" == yes ]] && fx_args ) @"$(winpath "$list")"; then
    # Copy non-java resources (fxml, images, ...) next to the classes
    (cd "$src_dir" && find . -type f -not -name "*.java" -not -name "*.class" -not -name "*.md" \
       -exec cp --parents {} "$out_dir" \;)
    echo "✓ $mod compiled"
    rm -f "$list"
  else
    rm -f "$list"
    echo "✗ Compilation of $mod failed"
    return 1
  fi
}

build_app() {
  require_app "$1"
  build_module "${MOD[$1]}" "${FX[$1]}"
}

build_all() {
  local app mod fx failed=0
  declare -A seen
  for app in "${APPS[@]}"; do
    mod="${MOD[$app]}"
    [[ -n "${seen[$mod]}" ]] && continue
    seen[$mod]=1
    build_module "$mod" "${FX[$app]}" || failed=1
  done
  return $failed
}

run_app() {
  local app="$1"; shift
  require_app "$app"

  local mod="${MOD[$app]}"
  if [[ ! -d "$OUT/$mod" ]]; then
    echo "✗ Not compiled yet. Run: ./runsdz.sh build $app"
    exit 1
  fi

  local workdir="$ROOT/$mod"
  [[ -n "${CWD[$app]}" ]] && workdir="$ROOT/$mod/${CWD[$app]}"

  local -a java_opts=()
  # shellcheck disable=SC2207
  [[ "${FX[$app]}" == yes ]] && java_opts+=($(fx_args))

  echo "► Running ${MAIN[$app]}  (cwd: ${workdir#$ROOT/})"
  cd "$workdir" && java "${java_opts[@]}" -cp "$(classpath "$mod")" "${MAIN[$app]}" "$@"
}

clean_all() {
  echo "► Cleaning $OUT ..."
  rm -rf "$OUT"
  echo "✓ Done"
}

# ── Main ──────────────────────────────────────
CMD="$1"; shift

case "$CMD" in
  list)                       list_apps ;;
  build)                      build_app "$1" ;;
  build-all)                  build_all ;;
  run)                        run_app "$@" ;;
  "build+run"|build-run|br)   build_app "$1" && run_app "$@" ;;
  clean)                      clean_all ;;
  *)
    echo "SDZ Master Runner"
    list_apps
    ;;
esac

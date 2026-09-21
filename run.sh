#!/usr/bin/env bash
# ─────────────────────────────────────────────
#  JavaGG Master Build & Run Script
#  Usage:
#    ./run.sh list
#    ./run.sh build+run <app>
#    ./run.sh clean
# ─────────────────────────────────────────────

export DISPLAY=":1"
export JAVA_TOOL_OPTIONS="-Dawt.useSystemAAFontSettings=off -Dswing.aatext=false"

# ── Paths ────────────────────────────────────
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT="$SCRIPT_DIR/javagg"
OUT="$ROOT/out"
LIB="$ROOT/lib"
FONT_CONFIG="$SCRIPT_DIR/fontconfig.properties"

# ── Module definitions ────────────────────────
# Format: "app_name|src_dir|main_class|needs_lib"
declare -A SRC MAIN NEEDS_LIB DEPS
SRC[awt3d]="jgg-awt3d/src"
MAIN[awt3d]="net.sf.javagg.awt3d.Awt3DDemo"
NEEDS_LIB[awt3d]="no"

SRC[bitmap]="jgg-bitmap-editor/src"
MAIN[bitmap]="net.sf.javagg.bitmapedit.BitmapEditor"
NEEDS_LIB[bitmap]="no"

SRC[gamescreen]="jgg-gamescreen/src"
MAIN[gamescreen]="net.sf.javagg.gamescreen.DemoGraphics"
NEEDS_LIB[gamescreen]="no"

SRC[terminal]="jgg-gamescreen/src"
MAIN[terminal]="net.sf.javagg.gamescreen.Demo"
NEEDS_LIB[terminal]="no"

SRC[imagetool]="jgg-imagetool/src"
MAIN[imagetool]="net.sf.javagg.imagetool.ImageStripEditor"
NEEDS_LIB[imagetool]="no"
DEPS[imagetool]="bitmap"

SRC[island3d]="jgg-island3d/src"
MAIN[island3d]="net.sf.javagg.island3d.MapFrame"
NEEDS_LIB[island3d]="no"

SRC[rpg]="jgg-rpg/src"
MAIN[rpg]="net.sourceforge.javagg.rpg.Game1Universe"
NEEDS_LIB[rpg]="yes"

SRC[mapwalker]="jgg-rpg/src"
MAIN[mapwalker]="net.sourceforge.javagg.rpg.MapWalkerUI"
NEEDS_LIB[mapwalker]="yes"

# ── Helpers ───────────────────────────────────
list_apps() {
  echo ""
  echo "Available apps:"
  echo "  awt3d      - 3D AWT cube/shape demo"
  echo "  bitmap     - Bitmap editor"
  echo "  gamescreen - Game screen demo (DemoGraphics)"
  echo "  terminal   - GameScreen demo (Demo)"
  echo "  imagetool  - Image strip editor"
  echo "  island3d   - 3D island/terrain generator"
  echo "  mapwalker  - RPG map walker demo (world/dungeon/cave/mine tile maps)"
  echo ""
  echo "Commands:"
  echo "  ./run.sh build+run <app>   ← compile + launch in one step (most common)"
  echo "  ./run.sh build     <app>   compile only"
  echo "  ./run.sh run       <app>   run only (must build first)"
  echo "  ./run.sh list              show this list"
  echo "  ./run.sh clean             remove all compiled output"
  echo ""
  echo "Examples:"
  echo "  ./run.sh build+run bitmap"
  echo "  ./run.sh build+run island3d"
  echo "  ./run.sh build+run mapwalker"
  echo ""
}

build_app() {
  local app="$1"
  if [[ -z "${SRC[$app]}" ]]; then
    echo "✗ Unknown app: $app"
    list_apps
    exit 1
  fi

  local src_dir="$ROOT/${SRC[$app]}"
  local out_dir="$OUT/$app"
  mkdir -p "$out_dir"

  # Build dependencies first
  if [[ -n "${DEPS[$app]}" ]]; then
    for dep in ${DEPS[$app]}; do
      if [[ ! -d "$OUT/$dep" ]]; then
        echo "  (building dependency: $dep)"
        build_app "$dep"
      fi
    done
  fi

  # Build classpath
  local cp="$out_dir"
  if [[ -n "${DEPS[$app]}" ]]; then
    for dep in ${DEPS[$app]}; do
      cp="$cp:$OUT/$dep"
    done
  fi
  if [[ "${NEEDS_LIB[$app]}" == "yes" && -d "$LIB" ]]; then
    for jar in "$LIB"/*.jar; do
      cp="$cp:$jar"
    done
  fi

  echo "► Compiling $app ..."
  echo "  Source: $src_dir"
  echo "  Output: $out_dir"

  # Find all .java files
  find "$src_dir" -name "*.java" > /tmp/javagg_sources.txt
  local count
  count=$(wc -l < /tmp/javagg_sources.txt)
  echo "  Files:  $count .java files found"

  # Compile the optional font preloader when a source tree provides one.
  if [[ -f "$ROOT/FontPreloader.java" ]]; then
    javac -d "$out_dir" "$ROOT/FontPreloader.java" 2>/dev/null
  fi
  javac -d "$out_dir" -cp "$cp" @/tmp/javagg_sources.txt 2>&1
  if [[ $? -eq 0 ]]; then
    echo "✓ Compiled successfully → $out_dir"

    # Copy non-java resources (images, xml, etc.)
    echo "  Copying resources..."
    cd "$src_dir" && find . -not -name "*.java" -not -name "*.class" -type f | while read f; do
      mkdir -p "$out_dir/$(dirname "$f")"
      cp "$f" "$out_dir/$f" 2>/dev/null
    done
    echo "✓ Resources copied"
  else
    echo "✗ Compilation failed"
    exit 1
  fi
}

run_app() {
  local app="$1"
  if [[ -z "${MAIN[$app]}" ]]; then
    echo "✗ Unknown app: $app"
    list_apps
    exit 1
  fi

  local out_dir="$OUT/$app"
  if [[ ! -d "$out_dir" ]]; then
    echo "✗ Not compiled yet. Run: ./run.sh build $app"
    exit 1
  fi

  # Build classpath
  local cp="$out_dir"
  if [[ -n "${DEPS[$app]}" ]]; then
    for dep in ${DEPS[$app]}; do
      cp="$cp:$OUT/$dep"
    done
  fi
  if [[ "${NEEDS_LIB[$app]}" == "yes" && -d "$LIB" ]]; then
    for jar in "$LIB"/*.jar; do
      cp="$cp:$jar"
    done
  fi

  # Also add root data/images dirs to classpath for resource loading
  cp="$cp:$ROOT"

  echo "► Running ${MAIN[$app]} ..."
  local -a java_opts
  java_opts=(-Dsun.java2d.fontpath=/usr/share/fonts/truetype/dejavu
            -Djavagg.root="$ROOT")
  if [[ -f "$FONT_CONFIG" ]]; then
    java_opts+=("-Dsun.awt.fontconfig=$FONT_CONFIG")
  fi

  if [[ -f "$ROOT/FontPreloader.java" ]]; then
    java "${java_opts[@]}" -cp "$cp" FontPreloader "${MAIN[$app]}"
  else
    java "${java_opts[@]}" -cp "$cp" "${MAIN[$app]}"
  fi
}

clean_all() {
  echo "► Cleaning $OUT ..."
  rm -rf "$OUT"
  echo "✓ Done"
}

# ── Main ──────────────────────────────────────
CMD="$1"
APP="$2"

case "$CMD" in
  list)
    list_apps
    ;;
  build)
    build_app "$APP"
    ;;
  run)
    run_app "$APP"
    ;;
  "build+run"|build-run|br)
    build_app "$APP" && run_app "$APP"
    ;;
  clean)
    clean_all
    ;;
  *)
    echo "JavaGG Master Runner"
    list_apps
    ;;
esac

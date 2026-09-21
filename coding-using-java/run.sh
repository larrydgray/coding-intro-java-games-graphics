#!/usr/bin/env bash
# Coding Using Java — Example Games helper
#
# Usage:
#   ./run.sh list
#   ./run.sh compile
#   ./run.sh build <GameName>       compile everything, then launch a game
#   ./run.sh build+run <GameName>   same as build
#   ./run.sh run <GameName>         launch an already-compiled game
#   ./run.sh clean

set -u

# -- Portability: Linux, macOS-like shells, and Git Bash on Windows ----------
case "$(uname -s)" in
  MINGW*|MSYS*|CYGWIN*) PATHSEP=";"; winpath() { cygpath -m "$1"; } ;;
  *)                    PATHSEP=":"; winpath() { printf '%s' "$1"; } ;;
esac
# Write one quoted, Windows-safe path per line (for javac @argfiles)
write_list() { while IFS= read -r f; do printf '"%s"\n' "$(winpath "$f")"; done; }
# Only point at a Replit display when one exists
if [[ -S /tmp/.X11-unix/X1 ]]; then export DISPLAY=":1"; fi
export JAVA_TOOL_OPTIONS="-Dawt.useSystemAAFontSettings=off -Dswing.aatext=false"

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
OUT_DIR="$SCRIPT_DIR/out"

list_games() {
  echo ""
  echo "Available games:"
  find "$SCRIPT_DIR" -maxdepth 1 -type f -name '*.java' -print0 \
    | xargs -0 grep -l "public static void main" 2>/dev/null \
    | sed 's|.*/||; s|\.java$||' \
    | sort \
    | sed 's/^/  /'
  echo ""
  echo "Commands:"
  echo "  ./run.sh list"
  echo "  ./run.sh compile"
  echo "  ./run.sh build+run <GameName>"
  echo "  ./run.sh run <GameName>"
  echo "  ./run.sh clean"
  echo ""
}

compile_all() {
  mkdir -p "$OUT_DIR"
  echo "► Compiling the shared engine and example games..."
  javac -d "$OUT_DIR" "$SCRIPT_DIR"/*.java
  echo "✓ Compiled successfully → $OUT_DIR"
}

run_game() {
  local game="$1"
  if [[ -z "$game" || ! -f "$SCRIPT_DIR/$game.java" ]]; then
    echo "✗ Choose one of the game names shown by: ./run.sh list"
    exit 1
  fi
  if [[ ! -f "$OUT_DIR/$game.class" ]]; then
    echo "✗ $game is not compiled yet. Run: ./run.sh build+run $game"
    exit 1
  fi

  echo "► Running $game ..."
  cd "$SCRIPT_DIR"
  local -a font_opts=()
  [[ -d /usr/share/fonts/truetype/dejavu ]] && font_opts+=(-Dsun.java2d.fontpath=/usr/share/fonts/truetype/dejavu)
  java ${font_opts[@]+"${font_opts[@]}"} -cp "$(winpath "$OUT_DIR")" "$game"
}

clean_all() {
  rm -rf "$OUT_DIR"
  echo "✓ Removed compiled output"
}

case "${1:-help}" in
  list)
    list_games
    ;;
  compile)
    compile_all
    ;;
  build|build+run|build-run|br)
    compile_all && run_game "${2:-}"
    ;;
  run)
    run_game "${2:-}"
    ;;
  clean)
    clean_all
    ;;
  help|*)
    list_games
    ;;
esac
// Course example © 2026 Larry D. Gray.
// For enrolled-student educational use; see README.md.

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * Tracks which keys are currently held down. Attach an instance to your
 * GameScreen with {@code screen.addKeyListener(new InputHandler())}, then
 * ask it {@code isDown(KeyEvent.VK_LEFT)} etc. on every game tick to see
 * what the player is pressing right now.
 */
public class InputHandler extends KeyAdapter {

    private final Set<Integer> pressed = new HashSet<>();
    private final Set<Integer> pressedLastCheck = new HashSet<>();

    @Override
    public void keyPressed(KeyEvent e) {
        pressed.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressed.remove(e.getKeyCode());
    }

    public boolean isDown(int keyCode) {
        return pressed.contains(keyCode);
    }

    /**
     * True only on the tick where a key transitions from up to down —
     * useful for one-shot actions (confirm, fire-once) where isDown
     * would otherwise trigger repeatedly while the key is held. Call at
     * most once per game tick per key, since calling it "consumes" the
     * transition.
     */
    public boolean wasPressed(int keyCode) {
        boolean now = pressed.contains(keyCode);
        boolean before = pressedLastCheck.contains(keyCode);
        if (now) {
            pressedLastCheck.add(keyCode);
        } else {
            pressedLastCheck.remove(keyCode);
        }
        return now && !before;
    }
}

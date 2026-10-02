package net.sf.sdz.sound;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Loads a set of WAV sound effects into Clips and plays each one on a
 * button press. Needs the .wav files listed in EFFECTS under a "sound"
 * folder next to this class's working directory - a mix of construction
 * and nature-ambience effects, no voice lines, bundled in this repo and
 * edited in Audacity from freesound.org source material. Each file loads
 * independently, so whichever are actually present get a button; missing
 * ones are just skipped (with a note on the console) rather than the whole
 * app failing to start over one missing file.
 *
 * The original article hand-wrote one file/stream/clip/button/listener per
 * effect (and called that repetition out as ripe for refactoring) for a
 * handful of effects; with a full set of 20 here, that would mean ~20
 * nearly-identical copy-pasted blocks, so this builds them from one small
 * list instead - the refactor the original article suggested but didn't do.
 */
public class PlaySoundEffects {

    // filename (under sound/), button label
    private static final String[][] EFFECTS = {
        {"bird.wav", "Bird"},
        {"birds1.wav", "Birds 1"},
        {"birds2.wav", "Birds 2"},
        {"bulldozer.wav", "Bulldozer"},
        {"campfire1.wav", "Campfire"},
        {"cave_drip1.wav", "Cave Drip 1"},
        {"cavedrip2.wav", "Cave Drip 2"},
        {"crickets.wav", "Crickets"},
        {"crickets1.wav", "Crickets 1"},
        {"dump_backup.wav", "Dump Truck Backup"},
        {"excavator.wav", "Excavator"},
        {"fire_start1.wav", "Fire Start 1"},
        {"fire_start2.wav", "Fire Start 2"},
        {"frogs1.wav", "Frogs"},
        {"jack-hammer.wav", "Jackhammer"},
        {"tree_frog.wav", "Tree Frog"},
        {"water1.wav", "Water 1"},
        {"water_shore.wav", "Water (Shore)"},
        {"wind1.wav", "Wind"},
        {"wind_cold.wav", "Wind (Cold)"}
    };

    public PlaySoundEffects() {
        JFrame aFrame = new JFrame("Sound Effects");
        JPanel contentPanel = new JPanel(new BorderLayout());
        aFrame.setContentPane(contentPanel);

        JCheckBox fullLengthCheckBox = new JCheckBox("Play full length (instead of stopping after 3 seconds)");
        contentPanel.add(fullLengthCheckBox, BorderLayout.NORTH);

        JPanel aPanel = new JPanel();
        // A plain FlowLayout would try to lay all 20 buttons out in a single
        // row when the frame packs to its preferred size - a grid wraps them
        // into a sane-sized window instead.
        aPanel.setLayout(new GridLayout(0, 4, 5, 5));
        contentPanel.add(aPanel, BorderLayout.CENTER);

        for (String[] effect : EFFECTS) {
            String fileName = effect[0];
            String label = effect[1];
            Clip clip = loadClip(fileName);
            if (clip != null) {
                JButton button = new JButton(label);
                aPanel.add(button);
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        try {
                            // Rewind first - otherwise pressing the same
                            // button again resumes from wherever playback
                            // was last stopped (or does nothing at all once
                            // it's reached the end) instead of restarting.
                            clip.setFramePosition(0);
                            clip.start();
                            if (!fullLengthCheckBox.isSelected()) {
                                Thread.sleep(3000);
                                clip.stop();
                            }
                            // "full length" just starts it and returns -
                            // Clip stops itself once it reaches the end of
                            // its own data, no manual stop() needed.
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

        if (aPanel.getComponentCount() == 0) {
            aPanel.setLayout(new java.awt.FlowLayout());
            aPanel.add(new JLabel("<html>No .wav files found in the sound/ folder.<br>"
                    + "See PlaySoundEffects.EFFECTS for the full list of filenames<br>"
                    + "needed, placed in a \"sound\" folder next to this class.</html>"));
        }

        aFrame.pack();
        aFrame.setDefaultCloseOperation(aFrame.EXIT_ON_CLOSE);
        aFrame.show();
    }

    private Clip loadClip(String fileName) {
        try {
            AudioInputStream in = AudioSystem.getAudioInputStream(new File("sound/" + fileName));
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            return clip;
        } catch (Exception e) {
            System.out.println("sound/" + fileName + " not found or couldn't be loaded - its button will be skipped ("
                    + e.getMessage() + ").");
            return null;
        }
    }

    public static void main(String args[]) {
        new PlaySoundEffects();
    }
}

package net.sf.sdz.sound;

import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Loads a handful of WAV sound effects into Clips and plays each one on a
 * button press, via a Swing panel with one button per effect. Needs the
 * .wav files (bulldozer.wav, drill.wav, mine_dig.wav,
 * mine_place_explosion.wav - mechanical/environmental sound effects, not
 * voice lines, so they read as generic sound-effect demos rather than
 * quoting a specific game; retheme freely) on the working directory - each
 * one loads independently, so whichever files you actually have get a
 * button; missing ones are just skipped (with a note on the console)
 * rather than the whole app failing to start over one missing file.
 */
public class PlaySoundEffects {

    public PlaySoundEffects() {
        JFrame aFrame = new JFrame("Sound Effects");
        JPanel aPanel = new JPanel();
        aFrame.setContentPane(aPanel);

        Clip bulldozerClip = loadClip("bulldozer.wav");
        Clip drillClip = loadClip("drill.wav");
        Clip mineDigClip = loadClip("mine_dig.wav");
        Clip explosionClip = loadClip("mine_place_explosion.wav");

        if (bulldozerClip != null) {
            JButton bulldozerButton = new JButton("Bulldozer");
            aPanel.add(bulldozerButton);
            bulldozerButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        bulldozerClip.start();
                        Thread.sleep(3000);
                        bulldozerClip.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        if (drillClip != null) {
            JButton drillButton = new JButton("Drill");
            aPanel.add(drillButton);
            drillButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        drillClip.start();
                        Thread.sleep(3000);
                        drillClip.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        if (mineDigClip != null) {
            JButton mineDigButton = new JButton("Mine Dig");
            aPanel.add(mineDigButton);
            mineDigButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        mineDigClip.start();
                        Thread.sleep(3000);
                        mineDigClip.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        if (explosionClip != null) {
            JButton explosionButton = new JButton("Explosion");
            aPanel.add(explosionButton);
            explosionButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        explosionClip.start();
                        Thread.sleep(3000);
                        explosionClip.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        if (aPanel.getComponentCount() == 0) {
            aPanel.add(new JLabel("<html>No .wav files found in this folder.<br>"
                    + "Place bulldozer.wav, drill.wav, mine_dig.wav,<br>"
                    + "and mine_place_explosion.wav here and restart.</html>"));
        }

        aFrame.pack();
        aFrame.setDefaultCloseOperation(aFrame.EXIT_ON_CLOSE);
        aFrame.show();
    }

    private Clip loadClip(String fileName) {
        try {
            AudioInputStream in = AudioSystem.getAudioInputStream(new File(fileName));
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            return clip;
        } catch (Exception e) {
            System.out.println(fileName + " not found or couldn't be loaded - its button will be skipped ("
                    + e.getMessage() + ").");
            return null;
        }
    }

    public static void main(String args[]) {
        new PlaySoundEffects();
    }
}

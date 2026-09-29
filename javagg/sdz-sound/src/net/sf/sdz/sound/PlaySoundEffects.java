package net.sf.sdz.sound;

import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Loads a handful of WAV sound effects into Clips and plays each one on a
 * button press, via a Swing panel with one button per effect. Needs the
 * .wav files (swim1.wav, pond1.wav, splash1.wav, walking1.wav,
 * trapcatch1.wav, water1.wav, beaverwalk1.wav) on the working directory -
 * each one loads independently, so whichever files you actually have get a
 * button; missing ones are just skipped (with a note on the console) rather
 * than the whole app failing to start over one missing file.
 */
public class PlaySoundEffects {

    public PlaySoundEffects() {
        JFrame aFrame = new JFrame("Sound Effects");
        JPanel aPanel = new JPanel();
        aFrame.setContentPane(aPanel);

        Clip pondClip = loadClip("pond1.wav");
        Clip swimClip = loadClip("swim1.wav");
        Clip splashClip = loadClip("splash1.wav");
        Clip walkingClip = loadClip("walking1.wav");
        Clip waterClip = loadClip("water1.wav");
        Clip trapCatchClip = loadClip("trapcatch1.wav");
        //Clip trapPlaceClip = loadClip("trapplace1.wav");
        Clip beaverClip = loadClip("beaverwalk1.wav");

        if (pondClip != null) {
            JButton pondButton = new JButton("Pond");
            aPanel.add(pondButton);
            pondButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        pondClip.start();
                        Thread.sleep(3000);
                        pondClip.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        if (swimClip != null) {
            JButton swimButton = new JButton("Swim");
            aPanel.add(swimButton);
            swimButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        swimClip.start();
                        Thread.sleep(3000);
                        swimClip.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        if (splashClip != null) {
            JButton splashButton = new JButton("Splash");
            aPanel.add(splashButton);
            splashButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        splashClip.start();
                        Thread.sleep(3000);
                        splashClip.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        if (walkingClip != null) {
            JButton walkingButton = new JButton("Walking");
            aPanel.add(walkingButton);
            walkingButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        walkingClip.start();
                        Thread.sleep(3000);
                        walkingClip.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        if (waterClip != null) {
            JButton waterButton = new JButton("Water");
            aPanel.add(waterButton);
            waterButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        waterClip.start();
                        Thread.sleep(3000);
                        waterClip.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        if (trapCatchClip != null) {
            JButton trapCatchButton = new JButton("TrapCatch");
            aPanel.add(trapCatchButton);
            trapCatchButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        trapCatchClip.start();
                        Thread.sleep(3000);
                        trapCatchClip.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        // trapplace1.wav is left out here too - see the README (one file
        // apparently threw an exception due to its exact format, which was
        // never resolved in the original source).
        if (beaverClip != null) {
            JButton beaverButton = new JButton("Beaver");
            aPanel.add(beaverButton);
            beaverButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        beaverClip.start();
                        Thread.sleep(3000);
                        beaverClip.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        if (aPanel.getComponentCount() == 0) {
            aPanel.add(new JLabel("<html>No .wav files found in this folder.<br>"
                    + "Place swim1.wav, pond1.wav, splash1.wav, walking1.wav,<br>"
                    + "trapcatch1.wav, water1.wav, beaverwalk1.wav here and restart.</html>"));
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

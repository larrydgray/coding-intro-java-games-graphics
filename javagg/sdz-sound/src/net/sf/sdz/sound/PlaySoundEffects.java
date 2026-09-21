package net.sf.sdz.sound;

import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Loads a handful of WAV sound effects into Clips and plays each one on a
 * button press, via a Swing panel with one button per effect. Needs the
 * .wav files (swim1.wav, pond1.wav, splash1.wav, walking1.wav,
 * trapcatch1.wav, water1.wav, beaverwalk1.wav) on the working directory.
 */
public class PlaySoundEffects {

    public PlaySoundEffects() {
        try {
            File swimFile = new File("swim1.wav");
            File pondFile = new File("pond1.wav");
            File splashFile = new File("splash1.wav");
            File walkingFile = new File("walking1.wav");
            File trapCatchFile = new File("trapcatch1.wav");
            File trapPlaceFile = new File("trapplace1.wav");
            File waterFile = new File("water1.wav");
            File beaverWalkFile = new File("beaverwalk1.wav");
            AudioInputStream swimIn = AudioSystem.getAudioInputStream(swimFile);
            AudioInputStream pondIn = AudioSystem.getAudioInputStream(pondFile);
            AudioInputStream splashIn = AudioSystem.getAudioInputStream(splashFile);
            AudioInputStream walkingIn = AudioSystem.getAudioInputStream(walkingFile);
            AudioInputStream waterIn = AudioSystem.getAudioInputStream(waterFile);
            AudioInputStream trapCatchIn = AudioSystem.getAudioInputStream(trapCatchFile);
            //AudioInputStream trapPlaceIn = AudioSystem.getAudioInputStream(trapPlaceFile);
            AudioInputStream beaverIn = AudioSystem.getAudioInputStream(beaverWalkFile);
            Clip pondClip = AudioSystem.getClip();
            Clip swimClip = AudioSystem.getClip();
            Clip splashClip = AudioSystem.getClip();
            Clip walkingClip = AudioSystem.getClip();
            Clip waterClip = AudioSystem.getClip();
            Clip trapCatchClip = AudioSystem.getClip();
            //Clip trapPlaceClip = AudioSystem.getClip();
            Clip beaverClip = AudioSystem.getClip();
            JFrame aFrame = new JFrame("Sound Effects");
            JPanel aPanel = new JPanel();
            JButton pondButton = new JButton("Pond");
            JButton swimButton = new JButton("Swim");
            JButton splashButton = new JButton("Splash");
            JButton walkingButton = new JButton("Walking");
            JButton waterButton = new JButton("Water");
            JButton trapCatchButton = new JButton("TrapCatch");
            //JButton trapPlaceButton = new JButton("TrapPlace");
            JButton beaverButton = new JButton("Beaver");
            aFrame.setContentPane(aPanel);
            aPanel.add(pondButton);
            aPanel.add(swimButton);
            aPanel.add(splashButton);
            aPanel.add(walkingButton);
            aPanel.add(waterButton);
            aPanel.add(trapCatchButton);
            //aPanel.add(trapPlaceButton);
            aPanel.add(beaverButton);
            pondClip.open(pondIn);
            swimClip.open(swimIn);
            splashClip.open(splashIn);
            walkingClip.open(walkingIn);
            waterClip.open(waterIn);
            trapCatchClip.open(trapCatchIn);
            beaverClip.open(beaverIn);
            aFrame.pack();
            aFrame.setDefaultCloseOperation(aFrame.EXIT_ON_CLOSE);
            aFrame.show();
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
            /*trapPlaceButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        clip.open(trapPlaceIn);
                        clip.start();
                        Thread.sleep(3000);
                        clip.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });*/
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new PlaySoundEffects();
    }
}

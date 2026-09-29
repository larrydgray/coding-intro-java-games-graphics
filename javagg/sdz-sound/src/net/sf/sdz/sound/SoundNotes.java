package net.sf.sdz.sound;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Plays random MIDI notes through the system synthesizer's default
 * soundbank. Play plays 10 random notes of random duration; Prev/Next step
 * through the 128 available instruments one at a time, Random jumps to any
 * one of them (shown in a label), also printing the full instrument list
 * to the console on startup.
 */
public class SoundNotes {

    Instrument[] instr = null;
    Synthesizer synth = null;
    boolean skip = false;
    int duration = 0;
    int note = 0;
    int instrument = 0;
    MidiChannel[] mc = null;

    public static void main(String[] args) {
        new SoundNotes();
    }

    public SoundNotes() {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            instr = synth.getDefaultSoundbank().getInstruments();
            for (int i = 0; i < instr.length; i++) {
                System.out.println(instr[i]);
            }
        } catch (MidiUnavailableException mue) {
            mue.printStackTrace();
        }
        mc = synth.getChannels();
        JFrame frame = new JFrame("Sound1");
        JPanel pane = new JPanel();
        JButton playButton = new JButton("Play");
        JButton prevButton = new JButton("Prev");
        JButton nextButton = new JButton("Next");
        JButton randomButton = new JButton("Random Instrument");
        JLabel instrumentLabel = new JLabel(instr[instrument].toString());
        frame.getContentPane().add(pane);
        pane.add(playButton);
        pane.add(prevButton);
        pane.add(nextButton);
        pane.add(randomButton);
        pane.add(instrumentLabel);
        frame.pack();
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.show();
        synth.loadInstrument(instr[instrument]);
        mc[1].programChange(instr[instrument].getPatch().getProgram());
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                play10Notes();
            }
        });
        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                instrument--;
                if (instrument == -1) instrument = 127;
                synth.loadInstrument(instr[instrument]);
                mc[1].programChange(instr[instrument].getPatch().getProgram());
                instrumentLabel.setText(instr[instrument].toString());
                frame.pack();
            }
        });
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                instrument++;
                if (instrument == 128) instrument = 0;
                synth.loadInstrument(instr[instrument]);
                mc[1].programChange(instr[instrument].getPatch().getProgram());
                instrumentLabel.setText(instr[instrument].toString());
                frame.pack();
            }
        });
        randomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                instrument = (int) (Math.random() * instr.length);
                synth.loadInstrument(instr[instrument]);
                mc[1].programChange(instr[instrument].getPatch().getProgram());
                instrumentLabel.setText(instr[instrument].toString());
                frame.pack();
            }
        });
    }

    void play10Notes() {
        for (int i = 0; i < 10; i++) {
            note = (int) (Math.random() * 127 + 1);
            // Was Math.random()*100+1 (1-100), so duration*10 could be as
            // short as 10ms - cutting a note off that fast, before its
            // attack/decay envelope finishes, is what causes an audible
            // pop/click. 20-79 -> 200-790ms gives the envelope room to
            // actually finish while still varying per note.
            duration = (int) (Math.random() * 60 + 20);
            try {
                // Velocity must be 0-127 (it's a 7-bit MIDI value); 400 was
                // out of range and landed at or past max loudness. And with
                // no noteOff below, each note kept ringing under the next
                // one - by note 10 you'd have up to 10 pitches stacked on
                // top of each other, which reads as loud, dissonant static
                // rather than 10 distinct notes.
                mc[1].noteOn(note, 90);
                Thread.sleep(duration * 10);
                mc[1].noteOff(note);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        try {
            Thread.sleep(1000);
            mc[1].allNotesOff();
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}

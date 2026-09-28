// Author Larry Gray CPL Common Public License  Software Developer Zone
package net.sf.sdz.sprites;

import java.awt.Dialog;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Keyboard shortcuts for the Sprite Organizer: 0-9 selects a buffer, l/g/c/m/n/p/s/o
 * trigger load/split/clear/move/new/paste/save/toggle-grid operations, prompting
 * for any needed parameters via input dialogs on the {@link ImagePanel}. 'h'
 * shows a summary of all of these.
 */
public class ImagePanelKeyListener extends KeyAdapter {
    // fields
    ImageBuffers buffers = null;
    ImagePanel imagePanel = null;
    // shared across load and save so the chooser reopens wherever you last
    // left it instead of always starting back at the working directory.
    private static File lastDirectory = null;

    //constructor
    public ImagePanelKeyListener(ImageBuffers buffers, ImagePanel imagePanel) {
        this.buffers = buffers;
        this.imagePanel = imagePanel;
    }

    //listener utility methods
    void changeBuffer(int buffer) {
        buffers.setBuffer(buffer);
        imagePanel.repaint();
        imagePanel.statsPanel.updateStats();
    }

    void clearSprite(int c, int r) {
        try {
            buffers.clearSprite(c, r);
            imagePanel.repaint();
        } catch (RuntimeException ex) {
            showActionError(ex);
        }
    }

    void moveSprite(int fc, int fr, int tc, int tr) {
        try {
            buffers.moveSprite(fc, fr, tc, tr);
            imagePanel.repaint();
        } catch (RuntimeException ex) {
            showActionError(ex);
        }
    }

    void loadImage() {
        JFileChooser chooser = new JFileChooser(lastDirectory);
        chooser.setDialogTitle("Load Image Into Buffer " + (buffers.currentBuffer + 1));
        chooser.setFileFilter(new FileNameExtensionFilter(
                "Image files (*.png, *.jpg, *.jpeg, *.gif)", "png", "jpg", "jpeg", "gif"));
        int result = chooser.showOpenDialog(imagePanel);
        if (result != JFileChooser.APPROVE_OPTION) {
            System.out.println("The user canceled");
        } else {
            lastDirectory = chooser.getCurrentDirectory();
            buffers.loadImage(chooser.getSelectedFile().getAbsolutePath(), imagePanel);
            imagePanel.repaint();
        }
    }

    void saveImage() {
        JFileChooser chooser = new JFileChooser(lastDirectory);
        chooser.setDialogTitle("Save Buffer " + (buffers.currentBuffer + 1) + " As");
        chooser.setFileFilter(new FileNameExtensionFilter("PNG images (*.png)", "png"));
        int result = chooser.showSaveDialog(imagePanel);
        if (result != JFileChooser.APPROVE_OPTION) {
            System.out.println("The user canceled");
            return;
        }
        lastDirectory = chooser.getCurrentDirectory();
        File file = chooser.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".png")) {
            file = new File(file.getParentFile(), file.getName() + ".png");
        }
        if (file.exists()) {
            int overwrite = JOptionPane.showConfirmDialog(imagePanel,
                    file.getName() + " already exists. Overwrite?",
                    "Confirm Overwrite", JOptionPane.YES_NO_OPTION);
            if (overwrite != JOptionPane.YES_OPTION) {
                return;
            }
        }
        buffers.saveImage(file.getAbsolutePath());
        imagePanel.repaint();
    }

    void splitImage() {
        buffers.setCols(imagePanel.inputCols());
        buffers.setRows(imagePanel.inputRows());
        imagePanel.statsPanel.updateStats();
    }

    void newBuffer(int x, int y) {
        buffers.newBuffer(x, y);
        imagePanel.statsPanel.updateStats();
        imagePanel.repaint();
    }

    void pasteToBuffer(int buffer, int col, int row) {
        try {
            buffers.pasteToBuffer(buffer, col, row);
            imagePanel.repaint();
        } catch (RuntimeException ex) {
            showActionError(ex);
        }
    }

    private void showActionError(RuntimeException ex) {
        JOptionPane.showMessageDialog(imagePanel, ex.getMessage(), "Can't Do That Yet", JOptionPane.WARNING_MESSAGE);
    }

    // kept modeless and reused so 'h' doesn't stack up copies, and so you can
    // leave it open on screen for reference while still using the app.
    private JDialog helpDialog = null;

    void showHelp() {
        if (helpDialog == null) {
            String help =
                    "BUFFERS\n" +
                    "  1-9, 0   Select buffer 1-10\n" +
                    "  n        New blank buffer (asks width/height in pixels)\n" +
                    "  l        Load an image file into the current buffer\n" +
                    "  s        Save the current buffer to a PNG file\n\n" +
                    "GRID (do this before c/m/p or clicking on a buffer)\n" +
                    "  g        Split the current buffer into a grid (asks cols/rows)\n" +
                    "  o        Toggle the grid overlay on/off\n\n" +
                    "SPRITES\n" +
                    "  c        Clear a cell (asks col/row)\n" +
                    "  m        Move a cell to another cell (asks from, then to)\n" +
                    "  p        Paste this whole buffer into a cell of another buffer\n" +
                    "           (asks destination buffer#, col, row)\n\n" +
                    "MOUSE (on the image area, after 'g')\n" +
                    "  Click              Select a cell\n" +
                    "  Shift+Click        Copy the selected sprite onto the clicked cell\n" +
                    "  Right-Click        Clear the clicked cell\n" +
                    "  Shift+Right-Click  Move the selected sprite onto the clicked cell\n\n" +
                    "  h        Show this help";
            JTextArea textArea = new JTextArea(help);
            textArea.setEditable(false);
            textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
            textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            Window owner = SwingUtilities.getWindowAncestor(imagePanel);
            helpDialog = new JDialog(owner, "Sprite Organizer Help", Dialog.ModalityType.MODELESS);
            helpDialog.getContentPane().add(textArea);
            helpDialog.pack();
            helpDialog.setResizable(false);
            helpDialog.setLocationRelativeTo(owner);
        }
        helpDialog.setVisible(true);
        helpDialog.toFront();
    }

    // main listener methods that use utility methods
    public void keyPressed(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
        char keyTyped = e.getKeyChar();
        switch (keyTyped) {
            case '1':
                changeBuffer(0);
                break;
            case '2':
                changeBuffer(1);
                break;
            case '3':
                changeBuffer(2);
                break;
            case '4':
                changeBuffer(3);
                break;
            case '5':
                changeBuffer(4);
                break;
            case '6':
                changeBuffer(5);
                break;
            case '7':
                changeBuffer(6);
                break;
            case '8':
                changeBuffer(7);
                break;
            case '9':
                changeBuffer(8);
                break;
            case '0':
                changeBuffer(9);
                break;
            case 'l':
                loadImage();
                break;
            case 'g':
                splitImage();
                break;
            case 'c':
                clearSprite(imagePanel.inputCol(), imagePanel.inputRow());
                break;
            case 'm':
                moveSprite(imagePanel.inputCol(), imagePanel.inputRow(), imagePanel.inputCol(), imagePanel.inputRow());
                break;
            case 'n':
                newBuffer(imagePanel.inputX(), imagePanel.inputY());
                break;
            case 'p':
                pasteToBuffer(imagePanel.inputBuffer(), imagePanel.inputCol(), imagePanel.inputRow());
                break;
            case 's':
                saveImage();
                break;
            case 'o':
                buffers.flipGrid();
                imagePanel.repaint();
                break;
            case 'h':
                showHelp();
                break;
            default:
                break;
        }
    }
}

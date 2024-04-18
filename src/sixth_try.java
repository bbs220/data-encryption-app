import javax.swing.*;
import javax.swing.filechooser.FileFilter;

// custom new stylish material ui 
// https://jar-download.com/artifacts/io.github.vincenzopalazzo/material-ui-swing/1.1.4
import mdlaf.MaterialLookAndFeel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

class file_handle_select extends FileFilter {
    public boolean accept(File file) {
        if (file.getName().endsWith(".txt"))
            return true;
        if (file.getName().endsWith(".java"))
            return true;
        return file.isDirectory();
    }

    public String getDescription() {
        return "Text file";
    }
}

class file_handle_save_txt extends FileFilter {
    public boolean accept(File f) {
        if (f.isDirectory())
            return true;
        String s = f.getName();
        return s.endsWith(".txt");
    }

    public String getDescription() {
        return "*.txt";
    }
}

public class sixth_try {
    JFileChooser j_fi_choose;
    File file;
    FileInputStream fis;
    FileOutputStream fos;
    FileChannel fi_in_chan, fi_out_chan;
    long file_size;
    ByteBuffer bb_0, bb_1;
    long key = 0;
    String file_ext = "";

    public sixth_try() {

        // window
        JFrame jj = new JFrame("Java Project");
        jj.setSize(new Dimension(500, 800));
        jj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jj.setLocationRelativeTo(null);
        jj.setResizable(false);
        jj.setVisible(true);
        jj.setLayout(null);

        // context with icon
        URL link_add = getClass().getResource("resources/images/add_file.png");
        Image img_g = Toolkit.getDefaultToolkit().getImage(link_add);
        JLabel jb_l = new JLabel("Choose file for encrypt/decrypt", new ImageIcon(img_g), SwingConstants.LEFT);
        jb_l.setBounds(40, 150, 450, 40);
        jb_l.setFont(new Font("monospaced", Font.BOLD, 20));
        jb_l.setForeground(Color.darkGray);
        jj.getContentPane().add(jb_l);

        // title
        JLabel jb_ll = new JLabel("Project on Data Encryption and Decryption");
        jb_ll.setForeground(Color.darkGray);
        jb_ll.setFont(new Font("impact", Font.PLAIN, 25));
        jb_ll.setBounds(25, 15, 450, 30);
        jj.getContentPane().add(jb_ll);

        // text field
        JTextField j_txt_field = new JTextField(30);
        j_txt_field.setEditable(false);
        j_txt_field.setBounds(50, 200, 380, 40);
        j_txt_field.setFont(new Font("serif", Font.BOLD, 18));
        j_txt_field.setForeground(Color.black);
        jj.getContentPane().add(j_txt_field);

        // browse button
        URL link_browse = getClass().getResource("resources/images/browse.png");
        Image img_br = Toolkit.getDefaultToolkit().getImage(link_browse);
        JButton browse = new JButton("Browse", new ImageIcon(img_br));
        browse.setForeground(Color.black);
        browse.setBounds(70, 450, 150, 40);
        browse.setFont(new Font("monospaced", Font.ITALIC + Font.BOLD, 20));
        jj.getContentPane().add(browse);

        // encrypt button
        URL link_en = getClass().getResource("resources/images/en.png");
        Image img_en = Toolkit.getDefaultToolkit().getImage(link_en);
        JButton en = new JButton("Encrypt", new ImageIcon(img_en));
        en.setForeground(Color.blue);
        en.setEnabled(false);
        en.setBounds(70, 300, 150, 40);
        en.setFont(new Font("monospaced", Font.BOLD + Font.ITALIC, 20));
        jj.getContentPane().add(en);

        // decrypt button
        URL link_de = getClass().getResource("resources/images/de.png");
        Image img_de = Toolkit.getDefaultToolkit().getImage(link_de);
        JButton de = new JButton("Decrypt", new ImageIcon(img_de));
        de.setForeground(Color.red);
        de.setEnabled(false);
        de.setBounds(250, 300, 150, 40);
        de.setFont(new Font("monospaced", Font.BOLD + Font.ITALIC, 20));
        jj.getContentPane().add(de);

        // cancel button
        URL link_can = getClass().getResource("resources/images/cancel.png");
        Image img_can = Toolkit.getDefaultToolkit().getImage(link_can);
        JButton cancel = new JButton("Cancel", new ImageIcon(img_can));
        cancel.setForeground(Color.black);
        cancel.setBounds(250, 450, 150, 40);
        cancel.setFont(new Font("monospaced", Font.ITALIC + Font.BOLD, 20));
        jj.getContentPane().add(cancel);

        // file chooser
        j_fi_choose = new JFileChooser();
        j_fi_choose.setFileFilter(new file_handle_select());

        // dark/light mode
        URL link_mode = getClass().getResource("resources/images/mode.png");
        Image img_gg = Toolkit.getDefaultToolkit().getImage(link_mode);
        JToggleButton tgl_btn = new JToggleButton("Dark / Light", new ImageIcon(img_gg));
        tgl_btn.setForeground(Color.black);
        tgl_btn.setFont(new Font("impact", Font.PLAIN, 14));
        tgl_btn.setBounds(2, 677, 120, 35);
        tgl_btn.setBorder(BorderFactory.createLineBorder(Color.black));

        // dark light button logic
        ItemListener il = event -> {
            int state = event.getStateChange();
            if (state == ItemEvent.DESELECTED) {
                jj.getContentPane().setBackground(Color.white);
            } else {
                jj.getContentPane().setBackground(Color.black);
            }
        };
        tgl_btn.addItemListener(il);
        jj.getContentPane().add(tgl_btn);

        // help menu
        JMenuBar mb = new JMenuBar();
        JMenu options = new JMenu("Options");
        JMenuItem instructions = new JMenuItem("Instructions");
        JMenuItem credits = new JMenuItem("Credits");
        JMenuItem about = new JMenuItem("About");
        options.add(instructions);
        options.add(credits);
        options.add(about);
        mb.add(options);
        jj.setJMenuBar(mb);

        // instruction menu button logic
        instructions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jf_a = new JFrame("Instructions");
                jf_a.setVisible(true);
                jf_a.setLayout(null);
                jf_a.setResizable(false);
                jf_a.setLocationRelativeTo(jj);
                jf_a.setSize(new Dimension(800, 795));
                jf_a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JLabel j_b_1 = new JLabel("1) Please use same numeric key for encryption as well as decryption.");
                j_b_1.setFont(new Font("dialog", Font.BOLD, 20));
                j_b_1.setBounds(10, 10, 1000, 30);
                j_b_1.setForeground(Color.black);
                JLabel j_b_2 = new JLabel("2) Otherwise you will end up corrupting your base files.");
                j_b_2.setBounds(10, 70, 1000, 30);
                j_b_2.setFont(new Font("dialog", Font.BOLD, 20));
                j_b_2.setForeground(Color.black);
                JLabel j_b_3 = new JLabel(
                        "3) For now this app works with text files (.txt) only, but you can try images too.");
                j_b_3.setBounds(10, 130, 1000, 30);
                j_b_3.setFont(new Font("dialog", Font.BOLD, 20));
                j_b_3.setForeground(Color.black);
                jf_a.getContentPane().add(j_b_1);
                jf_a.getContentPane().add(j_b_2);
                jf_a.getContentPane().add(j_b_3);
                URL link_flow = getClass().getResource("resources/images/flow.png");
                Image img_flow = Toolkit.getDefaultToolkit().getImage(link_flow);
                JLabel j_b_flow = new JLabel(new ImageIcon(img_flow));
                j_b_flow.setBorder(BorderFactory.createLineBorder(Color.black));
                j_b_flow.setBounds(40, 190, 700, 526);
                jf_a.getContentPane().add(j_b_flow);
            }
        });

        // credits menu button logic
        credits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jf_b = new JFrame("Credits");
                jf_b.setLayout(null);
                jf_b.setResizable(false);
                jf_b.setSize(new Dimension(880, 240));
                jf_b.setVisible(true);
                jf_b.setLocationRelativeTo(jj);
                jf_b.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JLabel j_b_4 = new JLabel(
                        "Made with : Launch4j, a tool for converting my project into a portable self-contained executable.");
                j_b_4.setForeground(Color.red);
                j_b_4.setBounds(10, 10, 880, 40);
                j_b_4.setFont(new Font("dialog", Font.PLAIN, 20));
                JLabel j_b_5 = new JLabel(
                        "Made with : MaterialUI, a modern library for Java Swing based on Google's Material UI.");
                j_b_5.setBounds(10, 70, 880, 40);
                j_b_5.setFont(new Font("dialog", Font.PLAIN, 20));
                j_b_5.setForeground(Color.MAGENTA);
                JLabel j_b_6 = new JLabel(
                        "Made with : Flaticons.com, a website for icons for almost any use UI element.");
                j_b_6.setBounds(10, 130, 880, 40);
                j_b_6.setFont(new Font("dialog", Font.PLAIN, 20));
                j_b_6.setForeground(Color.blue);
                jf_b.getContentPane().add(j_b_4);
                jf_b.getContentPane().add(j_b_5);
                jf_b.getContentPane().add(j_b_6);
            }
        });

        // about menu button logic
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jf_c = new JFrame("About");
                jf_c.setLayout(null);
                jf_c.setResizable(false);
                jf_c.setLocationRelativeTo(jj);
                jf_c.setSize(new Dimension(460, 200));
                jf_c.setVisible(true);
                jf_c.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JLabel j_b_7 = new JLabel("Made By");
                j_b_7.setBounds(175, 10, 600, 30);
                j_b_7.setFont(new Font("dialog", Font.BOLD, 20));
                j_b_7.setForeground(Color.black);
                JLabel j_b_8 = new JLabel("Buddhabhushan Sawant");
                j_b_8.setBounds(40, 70, 600, 30);
                j_b_8.setFont(new Font("monospaced", Font.BOLD + Font.ITALIC, 30));
                j_b_8.setForeground(Color.darkGray);
                jf_c.getContentPane().add(j_b_7);
                jf_c.getContentPane().add(j_b_8);
            }
        });

        // browse button logic
        browse.addActionListener(e -> {
            if (e.getSource() == browse) {
                int result = j_fi_choose.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    jb_l.setText("File -> " + j_fi_choose.getSelectedFile().getName());
                    j_txt_field.setText(j_fi_choose.getSelectedFile().getPath());
                    file = j_fi_choose.getSelectedFile();
                    de.setEnabled(true);
                    en.setEnabled(true);
                } else {
                    j_txt_field.setText("No file selected");
                    de.setEnabled(false);
                    en.setEnabled(false);
                }
            }
        });

        // encrypt button logic
        en.addActionListener(e -> {
            if (e.getSource() == en) {
                try {
                    key = input_key();
                    if (key >= 1) {
                        JOptionPane.showMessageDialog(null, "Save the encoded file", "Encryption Done",
                                JOptionPane.INFORMATION_MESSAGE);
                        convert(-key);
                    } else
                        JOptionPane.showMessageDialog(null, "Non-Zero Key Only", "Warning!",
                                JOptionPane.WARNING_MESSAGE);
                } catch (Exception io_ee) {
                }
            }
        });

        // decrypt button logic
        de.addActionListener(e -> {
            if (e.getSource() == de) {
                try {
                    key = input_key();
                    if (key >= 1) {
                        JOptionPane.showMessageDialog(null, "Save the decoded file", "Decryption Done",
                                JOptionPane.INFORMATION_MESSAGE);
                        convert(key);
                    } else
                        JOptionPane.showMessageDialog(null, "Non-Zero key Only", "Warning!",
                                JOptionPane.WARNING_MESSAGE);
                } catch (Exception ex) {
                }
            }
        });

        // cancel button logic
        cancel.addActionListener(e -> {
            if (e.getSource() == cancel) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {

        // better ui
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // calling construct
        SwingUtilities.invokeLater(sixth_try::new);
    }

    private long input_key() throws IOException {

        // entering key logic
        long k = 0;
        String key = JOptionPane.showInputDialog(null, "Enter the Key", "Public Key", JOptionPane.QUESTION_MESSAGE);
        long intKey = (long) Integer.parseInt(key);
        long temp = intKey;
        checking: {
            do {
                k = k + (intKey % 10);
                intKey = intKey / 10;
            } while (intKey >= 10);
            k = k + intKey;
            if (k > 32) {
                intKey = temp / 10;
                break checking;
            }
        }
        return k;
    }

    private void convert(long public_key) {

        // secure key logic
        long Key = public_key;
        try {
            JFileChooser jFileChooser = new JFileChooser();
            // jFileChooser.addChoosableFileFilter(new file_handle_save_j());
            jFileChooser.addChoosableFileFilter(new file_handle_save_txt());
            jFileChooser.setSelectedFile(new File("new-file.txt"));
            int respond = jFileChooser.showSaveDialog(null);
            if (respond == JFileChooser.APPROVE_OPTION) {
                String extension = jFileChooser.getFileFilter().getDescription();
                // if (extension.equals("*.java")) {
                // file_ext = ".java";
                // }
                if (extension.equals("*.txt")) {
                    file_ext = ".txt";
                }
            }

            // file io logic
            fis = new FileInputStream(file);
            fos = new FileOutputStream(jFileChooser.getSelectedFile() + file_ext);
            fi_in_chan = fis.getChannel();
            fi_out_chan = fos.getChannel();
            file_size = fi_in_chan.size();
            bb_0 = ByteBuffer.allocate((int) file_size);
            bb_1 = ByteBuffer.allocate((int) file_size);
            fi_in_chan.read(bb_0);
            bb_0.rewind();
            for (int i = 0; i < file_size; i++) {
                long data = ((long) bb_0.get());
                bb_1.put((byte) (data + Key));
            }

            // file closure
            bb_1.rewind();
            fi_out_chan.write(bb_1);
            fi_in_chan.close();
            fis.close();
            fi_out_chan.close();
            fos.close();
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        } catch (Exception ee) {
            System.out.println(ee);
        }
    }
}
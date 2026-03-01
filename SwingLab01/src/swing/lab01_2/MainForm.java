package swing.lab01_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainForm {
    private JPanel mainPanel;
    private JLabel timeLabel;
    private JButton startButton;
    private JButton stopButton;
    private JButton resetButton;
    private JLabel mouseLabel;

    // logika stopera
    private Timer timer;
    private long startTimeMillis;
    private long elapsedMillis;
    private boolean running;

    public MainForm() {
        running = false;
        elapsedMillis = 0;
        timeLabel.setText("00:00.0");

        // Timer – co 10 ms wywoluje onTimerTick()
        timer = new Timer(10, e -> onTimerTick());

        // obsluga przyciskow (ActionListener)
        startButton.addActionListener(e -> { onStart(); mainPanel.requestFocusInWindow(); });
        stopButton.addActionListener(e -> { onStop(); mainPanel.requestFocusInWindow(); });
        resetButton.addActionListener(e -> { onReset(); mainPanel.requestFocusInWindow(); });

        // przygotowanie panelu do obslugi klawiatury
        mainPanel.setFocusable(true);

        // obsluga klawiatury (KeyListener)
        mainPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_SPACE) {
                    toggleStartStop();
                } else if (code == KeyEvent.VK_R) {
                    onReset();
                } else if (code == KeyEvent.VK_S) {
                    onStop();
                } else if (code == KeyEvent.VK_ESCAPE) {
                    Window window = SwingUtilities.getWindowAncestor(mainPanel);
                    if (window != null) {
                        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
                    }
                }
            }
        });

        // obsluga myszy – MouseListener
        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    onReset();
                    mouseLabel.setText("Podwojny klik – reset");
                } else if (e.getClickCount() == 1) {
                    mouseLabel.setText("Klik: x=" + e.getX() + ", y=" + e.getY());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mainPanel.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mainPanel.setBackground(null);
            }
        });

        // obsluga ruchu myszy – MouseMotionListener
        mainPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseLabel.setText("Mysz: x=" + e.getX() + ", y=" + e.getY());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                mouseLabel.setText("Przeciaganie: x=" + e.getX() + ", y=" + e.getY());
            }
        });
    }

    private void updateTimeLabel() {
        long total = elapsedMillis;

        if (running) {
            long now = System.currentTimeMillis();
            total += (now - startTimeMillis);
        }

        int totalSeconds = (int) (total / 1000);
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        int hundredths = (int) (total % 1000) / 10;

        String text = String.format("%02d:%02d.%02d", minutes, seconds, hundredths);
        timeLabel.setText(text);
    }

    private void onTimerTick() {
        updateTimeLabel();
    }

    private void onStart() {
        if (running) {
            return;
        }
        running = true;
        startTimeMillis = System.currentTimeMillis();
        timer.start();
        updateTimeLabel();
    }

    private void onStop() {
        if (!running) {
            return;
        }
        running = false;
        timer.stop();
        long now = System.currentTimeMillis();
        elapsedMillis += (now - startTimeMillis);
        updateTimeLabel();
    }

    private void onReset() {
        running = false;
        timer.stop();
        elapsedMillis = 0;
        updateTimeLabel();
    }

    private void toggleStartStop() {
        if (running) {
            onStop();
        } else {
            onStart();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("SwingLab02 – Stoper");
        frame.setContentPane(new MainForm().mainPanel);
        frame.setSize(400, 250);
        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // WindowListener – potwierdzenie zamkniecia
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        frame,
                        "Na pewno zamknac stoper?",
                        "Zamykanie",
                        JOptionPane.YES_NO_OPTION
                );
                if (result == JOptionPane.YES_OPTION) {
                    frame.dispose();
                }
            }
        });

        frame.setVisible(true);
    }
}

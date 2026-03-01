package swing.lab01;

import javax.swing.*;
import java.util.Locale;

public class MainForm {
    private JPanel mainPanel;
    private JLabel helloLabel;
    private JButton kliknijMnieButton;
    private JTextField nameTextField;
    private JCheckBox duzymiLiteramiCheckBox;
    private JComboBox wybierzJezyk;
    private JRadioButton studentRadioButton;
    private JRadioButton teacherRadioButton;

    public MainForm() {
        ButtonGroup group = new ButtonGroup();
        group.add(studentRadioButton);
        group.add(teacherRadioButton);
        kliknijMnieButton.addActionListener(e -> {
            String name = nameTextField.getText();
            boolean upperLetter = duzymiLiteramiCheckBox.isSelected();
            String language = wybierzJezyk.getSelectedItem().toString();
            String tellYourName;
            String welcomeText;
            String studentMessage;
            String teacherMessage;
            String groupMessage;
            String welcomeMessage;
            if (language.equals("Polski")) {
                tellYourName = "Podaj imie";
                welcomeText = "Cześć ";
                studentMessage = " Powitanie studenta";
                teacherMessage = " Powitanie prowadząceego";
            }
            else if (language.equals("English")) {
                tellYourName = "Enter your name";
                welcomeText = "Hello ";
                studentMessage = " Welcome student";
                teacherMessage = " Welcome teacher";
            }
            else if (language.equals("Deutsch")) {
                tellYourName = "Gib deinen Namen ein";
                welcomeText = "Hallo ";
                studentMessage = " Willkommen Student";
                teacherMessage = " Willkommen Lehrer";
            }
            else {
                throw new RuntimeException("Nieznany język");
            }

            if (studentRadioButton.isSelected()) {
                groupMessage = studentMessage;
            } else if (teacherRadioButton.isSelected()) {
                groupMessage = teacherMessage;
            }
            else {
                throw new RuntimeException("Nie wybrano grupy");
            }
            welcomeMessage = welcomeText + name + groupMessage + "!";

            if (name.isEmpty() && upperLetter) {
                JOptionPane.showMessageDialog(mainPanel, tellYourName.toUpperCase());
            } else if (name.isEmpty()) {
                JOptionPane.showMessageDialog(mainPanel, tellYourName);
            } else if (upperLetter) {
                JOptionPane.showMessageDialog(mainPanel, (welcomeMessage).toUpperCase());
            } else {
                JOptionPane.showMessageDialog(mainPanel, welcomeMessage);
            }

        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Moje pierwsze okno Swing");
        frame.setContentPane(new MainForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class calculadora extends JFrame {

    private JTextField displayField;
    private JButton[] numberButtons;
    private JButton[] operationButtons;
    private JButton equalsButton;
    private JButton clearButton;

    private double firstNumber;
    private String operation;

    public calculadora() {
        initComponents();
        setupLayout();
        setupEventHandlers();
    }

    private void initComponents() {
        displayField = new JTextField(10);
        displayField.setEditable(false);
        displayField.setHorizontalAlignment(SwingConstants.RIGHT);

        numberButtons = new JButton[10];
        for (int i = 0; i < numberButtons.length; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
        }

        operationButtons = new JButton[4];
        operationButtons[0] = new JButton("+");
        operationButtons[1] = new JButton("-");
        operationButtons[2] = new JButton("*");
        operationButtons[3] = new JButton("/");

        equalsButton = new JButton("=");
        clearButton = new JButton("C");
    }

    private void setupLayout() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 10, 10));

        // Primera fila: display
        panel.add(displayField);
        panel.add(clearButton);

        // Filas de números (7-9, +)
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(operationButtons[0]); // +

        // Filas de números (4-6, -)
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(operationButtons[1]); // -

        // Filas de números (1-3, *)
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(operationButtons[2]); // *

        // Fila inferior (0, /, =)
        panel.add(numberButtons[0]);
        panel.add(operationButtons[3]); // /
        panel.add(equalsButton);

        add(panel);
        pack(); // Ajustar tamaño automáticamente
    }

    private void setupEventHandlers() {
        // Números y operaciones
        for (JButton button : numberButtons) {
            button.addActionListener(new NumberButtonListener());
        }

        for (JButton button : operationButtons) {
            button.addActionListener(new OperationButtonListener());
        }

        // Igual (=)
        equalsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateResult();
            }
        });

        // Limpiar (C)
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearDisplay();
            }
        });
    }

    private class NumberButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String buttonText = ((JButton) e.getSource()).getText();
            displayField.setText(displayField.getText() + buttonText);
        }
    }

    private class OperationButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            firstNumber = Double.parseDouble(displayField.getText());
            operation = ((JButton) e.getSource()).getText();
            displayField.setText("");
        }
    }

    private void calculateResult() {
        double secondNumber = Double.parseDouble(displayField.getText());
        double result = 0;

        switch (operation) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "*":
                result = firstNumber * secondNumber;
                break;
            case "/":
                if (secondNumber == 0) {
                    JOptionPane.showMessageDialog(this, "Error: división por cero", "Error", JOptionPane.ERROR_MESSAGE);
                    clearDisplay();
                    return;
                }
                result = firstNumber / secondNumber;
                break;
        }

        displayField.setText(String.valueOf(result));
    }

    private void clearDisplay() {
        displayField.setText("");
        firstNumber = 0;
        operation = "";
    }
}

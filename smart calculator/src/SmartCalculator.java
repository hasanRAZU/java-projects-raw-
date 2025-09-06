import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class SmartCalculator extends JFrame implements ActionListener {
    private JTextField display;
    private ArrayList<String> history = new ArrayList<>();
    private String operator = "";
    private double num1 = 0, num2 = 0, result = 0;

    public SmartCalculator() {
        setTitle("Smart Calculator");
        setSize(500, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5,5));

        // Display
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        // Buttons Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(11,4,5,5)); // extra rows for separators

        String[][] rows = {
                {"X", "Clear", "Off", "History"},
                {"BMI", "Age", "Km->Mile", "Mile->Km"},
                {"C->F", "F->C", "Feet->Inch", "Inch->Feet"},
                {"+", "-", "*", "/"},
                {"%", "√", ".", "0"},
                {"1", "2", "3", "4"},
                {"5", "6", "7", "8"},
                {"9", "10", "0", "="}
        };

        for(int i=0;i<rows.length;i++){
            for(String text: rows[i]){
                JButton btn = new JButton(text);
                btn.setFont(new Font("Arial", Font.PLAIN, 16));
                btn.addActionListener(this);
                panel.add(btn);
            }
            // Add separator after row 1, 3, 5
            if(i==0 || i==2 || i==4){
                for(int s=0;s<4;s++){
                    JSeparator sep = new JSeparator();
                    sep.setForeground(Color.BLACK);
                    panel.add(sep);
                }
            }
        }

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String cmd = e.getActionCommand();
        try{
            switch(cmd){
                // Controls
                case "X":
                    if(display.getText().length()>0)
                        display.setText(display.getText().substring(0, display.getText().length()-1));
                    break;
                case "Clear":
                    display.setText("");
                    operator="";
                    break;
                case "Off":
                    System.exit(0);
                    break;
                case "History":
                    JOptionPane.showMessageDialog(this, String.join("\n", history), "History", JOptionPane.INFORMATION_MESSAGE);
                    break;

                // Extra features
                case "BMI":
                    String hStr = JOptionPane.showInputDialog("Enter height in feet:");
                    String wStr = JOptionPane.showInputDialog("Enter weight in kg:");
                    double hFt = Double.parseDouble(hStr);
                    double w = Double.parseDouble(wStr);
                    double hM = hFt * 0.3048;
                    result = w / (hM*hM);
                    String cat;
                    if(result<18.5) cat="Underweight";
                    else if(result<24.9) cat="Normal";
                    else if(result<29.9) cat="Overweight";
                    else cat="Obese";
                    display.setText(String.format("%.2f (%s)", result, cat));
                    history.add("BMI: "+String.format("%.2f", result)+" ("+cat+")");
                    break;

                case "Age":
                    String birth = JOptionPane.showInputDialog("Enter DOB (YYYY-MM-DD):");
                    LocalDate dob = LocalDate.parse(birth);
                    LocalDate today = LocalDate.now();
                    Period age = Period.between(dob, today);
                    display.setText("Age: "+age.getYears());
                    history.add("Age: "+age.getYears()+" years");
                    break;

                // Conversions
                case "Km->Mile":
                    double km = Double.parseDouble(display.getText());
                    result = km*0.621371;
                    display.setText(String.format("%.4f", result));
                    history.add(km+" km = "+result+" miles");
                    break;
                case "Mile->Km":
                    double mile = Double.parseDouble(display.getText());
                    result = mile*1.60934;
                    display.setText(String.format("%.4f", result));
                    history.add(mile+" miles = "+result+" km");
                    break;
                case "C->F":
                    double c = Double.parseDouble(display.getText());
                    result = (c*9/5)+32;
                    display.setText(String.format("%.2f", result));
                    history.add(c+" °C = "+result+" °F");
                    break;
                case "F->C":
                    double f = Double.parseDouble(display.getText());
                    result = (f-32)*5/9;
                    display.setText(String.format("%.2f", result));
                    history.add(f+" °F = "+result+" °C");
                    break;
                case "Feet->Inch":
                    double ft = Double.parseDouble(display.getText());
                    result = ft*12;
                    display.setText(String.format("%.2f", result));
                    history.add(ft+" ft = "+result+" inch");
                    break;
                case "Inch->Feet":
                    double inch = Double.parseDouble(display.getText());
                    result = inch/12;
                    display.setText(String.format("%.2f", result));
                    history.add(inch+" inch = "+result+" ft");
                    break;
                case "√":
                    double val = Double.parseDouble(display.getText());
                    result = Math.sqrt(val);
                    display.setText(String.format("%.4f", result));
                    history.add("√"+val+" = "+result);
                    break;

                // Arithmetic operators
                case "+": case "-": case "*": case "/": case "%":
                    num1 = Double.parseDouble(display.getText());
                    operator = cmd;
                    display.setText("");
                    break;
                case "=":
                    num2 = Double.parseDouble(display.getText());
                    switch(operator){
                        case "+": result=num1+num2; break;
                        case "-": result=num1-num2; break;
                        case "*": result=num1*num2; break;
                        case "/": result=num2!=0? num1/num2:0; break;
                        case "%": result=(num1*num2)/100; break;
                    }
                    display.setText(String.valueOf(result));
                    history.add(num1+" "+operator+" "+num2+" = "+result);
                    break;

                // Numbers
                default:
                    display.setText(display.getText()+cmd);
            }
        }catch(Exception ex){
            display.setText("Error");
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(SmartCalculator::new);
    }
}

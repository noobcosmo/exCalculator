import java.awt.*;
import java.awt.event.*;
import javax.swing.JTextField;

public class CalculatorFrame extends Frame implements ActionListener {
    private JTextField showBox = new JTextField();
    private JTextField entryBox = new JTextField();

    private int status = 0;
    private int opr0 = 0;
    private int opr1 = 0;
    private String[] numS = {"", ""};
    private String resultS = "";
    private String signS0 = "";
    private String signS1 = "";

    CalculatorFrame() {
        this.setLayout(new GridLayout(7, 1));
        this.setSize(300, 400);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setFont(new Font("SansSerif", Font.PLAIN, 18));
        showBox.setFont(new Font("SansSerif", Font.PLAIN, 16));
        entryBox.setFont(new Font("SansSerif", Font.BOLD, 28));
        showBox.setHorizontalAlignment(JTextField.RIGHT);
        entryBox.setHorizontalAlignment(JTextField.RIGHT);

        GridLayout[] g1 = new GridLayout[7];
        GridLayout[] g2 = new GridLayout[2];
        Panel[] p1 = new Panel[7];
        Panel[] p2 = new Panel[2];
        for (int i = 0; i < 7; i++) {
            p1[i] = new Panel();
            this.add(p1[i]);
            g1[i] = new GridLayout(1, 4);
            p1[i].setLayout(g1[i]);
        }
        for (int i = 0; i < 2; i++) {
            p2[i] = new Panel();
            p1[1].add(p2[i]);
            g2[i] = new GridLayout(1, 2);
            p2[i].setLayout(g2[i]);
        }

        p1[0].add(entryBox);
        p2[1].add(showBox);
        entryBox.setText("0");
        showBox.setText("");
        Button[] btn = new Button[22];
        String[] btnLabel = {"√x", "x^2", "CE", "C", "<＝", "÷", "7", "8", "9", "×", "4", "5", "6", "-", "1", "2",
                "3", "+", "±", "0", ".", "＝"};
        for (int i = 0; i < 2; i++) {
            btn[i] = new Button(btnLabel[i]);
            p2[0].add(btn[i]);
            btn[i].addActionListener(this);
        }
        for (int i = 2, k = 2; i < btn.length; i += 4, k++) {
            for (int j = i; j < i + 4; j++) {
                btn[j] = new Button(btnLabel[j]);
                p1[k].add(btn[j]);
                btn[j].addActionListener(this);
            }
        }
        this.setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if (s.equals("C"))
            clear();
        else if (s.equals("0") || s.equals("1") || s.equals("2") || s.equals("3") || s.equals("4") || s.equals("5") ||
                s.equals("6") || s.equals("7") || s.equals("8") || s.equals("9")) {
            switch (status) {
                case 0:
                    numS[0] = s;
                    status = 1;
                    break;
                case 1:
                    if (!numS[0].equals("0"))
                        numS[0] += s;
                    else
                        numS[0] = s;
                    break;
                case 2:
                    numS[1] = s;
                    status = 3;
                    break;
                case 3:
                    if (!numS[1].equals("0"))
                        numS[1] += s;
                    else
                        numS[1] = s;
                    break;
                default:
                    break;
            }
        } else if (s.equals("±")) {
            switch (status) {
                case 0:
                    break;
                case 1:
                    negate(0, 0);
                    break;
                case 2:
                    negate(0, 1);
                    status = 3;
                    break;
                case 3:
                    if (numS[1].equals(""))
                        numS[1] = "0";
                    else
                        negate(1, 1);
                    break;
                default:
                    break;
            }
        } else if (s.equals(".")) {
            boolean dotStatus;
            switch (status) {
                case 0:
                    numS[0] = "0.";
                    status = 1;
                    break;
                case 1:
                    dotStatus = numS[0].contains(".");
                    if (!dotStatus)
                        numS[0] += s;
                    break;
                case 2:
                    numS[1] = "0.";
                    status = 3;
                    break;
                case 3:
                    dotStatus = numS[1].contains(".");
                    if (!dotStatus)
                        numS[1] += s;
                    break;
                default:
                    break;
            }
        } else if (s.equals("<＝")) {
            switch (status) {
                case 0:
                    break;
                case 1:
                    numS[0] = numS[0].substring(0, numS[0].length() - 1);
                    if (numS[0].equals(""))
                        numS[0] = "0";
                    break;
                case 2:
                    break;
                case 3:
                    numS[1] = numS[1].substring(0, numS[1].length() - 1);
                    if (numS[1].equals(""))
                        numS[1] = "0";
                    break;
                default:
                    break;
            }
        } else if (s.equals("+")) {
            switch (status) {
                case 0:
                    numS[0] = "0";
                    opr0 = 0;
                    signS0 = s;
                    status = 2;
                    break;
                case 1:
                    checkDot(0);
                    opr0 = 0;
                    signS0 = s;
                    status = 2;
                    break;
                case 2:
                    opr0 = 0;
                    signS0 = s;
                    break;
                case 3:
                    checkDot(1);
                    opr1 = 0;
                    signS1 = s;
                    status = 4;
                    break;
                default:
                    break;
            }
        } else if (s.equals("-")) {
            switch (status) {
                case 0:
                    numS[0] = "0";
                    opr0 = 1;
                    signS0 = s;
                    status = 2;
                    break;
                case 1:
                    checkDot(0);
                    opr0 = 1;
                    signS0 = s;
                    status = 2;
                    break;
                case 2:
                    opr0 = 1;
                    signS0 = s;
                    break;
                case 3:
                    checkDot(1);
                    opr1 = 1;
                    signS1 = s;
                    status = 4;
                    break;
                default:
                    break;
            }
        } else if (s.equals("×")) {
            switch (status) {
                case 0:
                    numS[0] = "0";
                    opr0 = 2;
                    signS0 = s;
                    status = 2;
                    break;
                case 1:
                    checkDot(0);
                    opr0 = 2;
                    signS0 = s;
                    status = 2;
                    break;
                case 2:
                    opr0 = 2;
                    signS0 = s;
                    break;
                case 3:
                    checkDot(1);
                    opr1 = 2;
                    signS1 = s;
                    status = 4;
                    break;
                default:
                    break;
            }
        } else if (s.equals("÷")) {
            switch (status) {
                case 0:
                    numS[0] = "0";
                    opr0 = 3;
                    signS0 = s;
                    status = 2;
                    break;
                case 1:
                    checkDot(0);
                    opr0 = 3;
                    signS0 = s;
                    status = 2;
                    break;
                case 2:
                    opr0 = 3;
                    signS0 = s;
                    break;
                case 3:
                    checkDot(1);
                    opr1 = 3;
                    signS1 = s;
                    status = 4;
                    break;
                default:
                    break;
            }
        } else if (s.equals("x^2")) {
            double n;
            switch (status) {
                case 0:
                    numS[0] = "0";
                    opr0 = 4;
                    signS0 = "^2";
                    status = 16;
                    break;
                case 1:
                    checkDot(0);
                    opr0 = 4;
                    signS0 = "^2";
                    status = 16;
                    break;
                case 2:
                    n = Double.valueOf(numS[0]);
                    numS[1] = calculating(n, 4);
                    status = 3;
                    break;
                case 3:
                    n = Double.valueOf(numS[1]);
                    if (n < 0)
                        status = -1;
                    else
                        numS[1] = calculating(n, 4);
                    break;
                default:
                    break;
            }
        } else if (s.equals("√x")) {
            double n;
            switch (status) {
                case 0:
                    numS[0] = "0";
                    opr0 = 5;
                    signS0 = "√";
                    status = 15;
                    break;
                case 1:
                    checkDot(0);
                    opr0 = 5;
                    signS0 = "√";
                    status = 15;
                    break;
                case 2:
                    n = Double.valueOf(numS[0]);
                    if (n < 0)
                        status = -1;
                    else {
                        numS[1] = calculating(n, 5);
                        status = 3;
                    }
                    break;
                case 3:
                    n = Double.valueOf(numS[1]);
                    if (n < 0)
                        status = -1;
                    else
                        numS[1] = calculating(n, 5);
                    break;
                default:
                    break;
            }
        } else if (s.equals("＝")) {
            switch (status) {
                case 0:
                case 1:
                    break;
                case 2:
                    numS[1] = numS[0];
                    status = 10;
                    break;
                case 3:
                    status = 10;
                    break;
                default:
                    break;
            }
        } else if (s.equals("CE")) {
            switch (status) {
                case 0:
                case 1:
                case 2:
                    clear();
                    break;
                case 3:
                    opr1 = 0;
                    numS[1] = "";
                    signS1 = "";
                    break;
                default:
                    break;
            }
        }
        switch (status) {
            case 4:
                numS[0] = calculating(0, 0);
                textShow();
                opr0 = opr1;
                signS0 = signS1;
                status = 2;
                break;
            case 10:
                numS[0] = calculating(0, 0);
                textShow();
                status = 1;
                break;
            case 15:
            case 16:
                resultS = calculating(0, 0);
                textShow();
                numS[0] = resultS;
                status = 1;
                break;
            default:
                textShow();
                break;
        }
    }

    private void textShow() {
        switch (status) {
            case 0:
                entryBox.setText("0");
                showBox.setText("");
                break;
            case 1:
                entryBox.setText(numS[0]);
                showBox.setText("");
                break;
            case 2:
                entryBox.setText(numS[0] + signS0);
                showBox.setText("");
                break;
            case 3:
                entryBox.setText(numS[1]);
                showBox.setText(numS[0] + signS0);
                break;
            case 4:
                entryBox.setText(numS[0]);
                showBox.setText(numS[0] + signS0);
                break;
            case 15:
                entryBox.setText(resultS);
                showBox.setText(signS0 + numS[0]);
                break;
            case 16:
                entryBox.setText(resultS);
                showBox.setText(numS[0] + signS0);
                break;
            case 10:
                entryBox.setText(numS[0]);
                showBox.setText("");
                break;
            case -1:
                clear();
                entryBox.setText("NaN");
                showBox.setText("Invalid input");
                break;
            default:
                status = -1;
                textShow();
                break;
        }
    }

    private void clear() {
        status = 0;
        opr0 = 0;
        opr1 = 0;
        numS[0] = "";
        numS[1] = "";
        resultS = "";
        signS0 = "";
        signS1 = "";
    }

    private void negate(int n0, int n1) {
        boolean signStatus;
        if (!(numS[n0].equals("0"))) {
            signStatus = numS[n0].contains("-");
            if (!signStatus) {
                numS[n1] = "-" + numS[n0];
            } else {
                numS[n1] = numS[n0].substring(1);
            }
        }
    }

    private void checkDot(int i) {
        if (i == 0 && numS[i].equals(""))
            numS[i] = "0";
        else if (numS[i].substring(numS[i].length() - 1).equals("."))
            numS[i] = numS[i].substring(0, numS[i].length() - 1);
    }

    private String calculating(double numTem, int oprTem) {
        double n0;
        double n1;
        String r;
        Calculator calculator;

        if (numS[0].substring(numS[0].length() - 1).equals("E"))
            status = -1;
        switch (status) {
            case 4:
            case 10:
                n0 = Double.valueOf(numS[0]);
                n1 = Double.valueOf(numS[1]);
                calculator = new Calculator(n0, opr0, n1);
                break;
            case 15:
            case 16:
                n0 = Double.valueOf(numS[0]);
                calculator = new Calculator(n0, opr0);
                break;
            default:
                calculator = new Calculator(numTem, oprTem);
                break;
        }

        r = String.valueOf(calculator.result);
        if (r.substring(r.length() - 2).equals(".0"))
            r = r.substring(0, r.length() - 2);
        return r;
    }
}

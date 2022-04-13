import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Use JFrame to create the interface of my app
 */
public class UI extends JFrame {
    JPanel panel = new JPanel();
    int count = 0;
    int range = 0;
    boolean sign = false;
    JTextField countField, rangeField;
    JLabel countLabel, rangeLabel;
    JRadioButton signField, unsignField;
    JRadioButton addLabel, subLabel, mulLabel, divLabel;

    Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
    ops op;
    JLabel question;
    JTextField ansField;
    int currentPage = 0;
    boolean endFlag = false;
    JLabel correctLabel = new JLabel();
    JButton nextButton = new JButton("next");
    ImageIcon bg = new ImageIcon("ArithmeticTrain/sun.jpg");

    int correctCount = 0;
    long startTime, endTime;
    JButton report = new JButton("report");

    UI() {
        this.setTitle("Mental arithmetic practice"); // set title
        this.setSize(800, 500);
        panel.setLayout(null);
        panel.setSize(500, 500);

        this.add(panel);

        /*
        show text box for total number of questions
         */
        countLabel = new JLabel("count: ");
        countLabel.setBounds(300 - 100, 50, 80, 30);
        countLabel.setFont(new Font("Font.PLAIN", Font.PLAIN, 20));
        countField = new JTextField(10);
        countField.setBounds(380 - 100, 50, 100, 30);
        countField.setFont(new Font("Font.BOLD", Font.BOLD, 20));

        /*
        show text box for range
         */
        panel.add(countLabel);
        panel.add(countField);
        panel.setOpaque(false);
        rangeLabel = new JLabel("range:");
        rangeLabel.setBounds(300 - 100, 100, 80, 30);
        rangeLabel.setFont(new Font("Font.PLAIN", Font.PLAIN, 20));
        rangeField = new JTextField(10);
        rangeField.setBounds(380 - 100, 100, 100, 30);
        rangeField.setFont(new Font("Font.BOLD", Font.BOLD, 20));

        panel.add(rangeLabel);
        panel.add(rangeField);

        /*
        show option of signed or unsigned
         */
        signField = new JRadioButton("signed");
        signField.setBounds(300 - 100, 150, 130, 30);
        signField.setFont(new Font("Font.PLAIN", Font.PLAIN, 20));
        signField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unsignField.setSelected(false);
            }
        });

        unsignField = new JRadioButton("unsigned");
        unsignField.setBounds(430 - 100, 150, 130, 30);
        unsignField.setFont(new Font("Font.PLAIN", Font.PLAIN, 20));
        unsignField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signField.setSelected(false);
            }
        });

        panel.add(signField);
        panel.add(unsignField);
        JButton confirm = new JButton();
        confirm.setBounds(280 - 100, 250, 120, 50);
        confirm.setFont(new Font("Font.BOLD", Font.BOLD, 20));
        confirm.setText("confirm");
        panel.add(confirm);
        JLabel errorLabel = new JLabel("Illegal input");
        errorLabel.setBounds(300 - 100, 400, 200, 100);
        errorLabel.setFont(new Font("", Font.BOLD, 30));
        errorLabel.setForeground(Color.RED);

        /*
        get operator
         */
        addLabel = new JRadioButton("add");
        subLabel = new JRadioButton("sub");
        mulLabel = new JRadioButton("mul");
        divLabel = new JRadioButton("div");

        addLabel.setBounds(280 - 100, 200, 70, 30);
        addLabel.setFont(new Font("", Font.PLAIN, 20));
        subLabel.setBounds(360 - 100, 200, 70, 30);
        subLabel.setFont(new Font("", Font.PLAIN, 20));
        mulLabel.setBounds(430 - 100, 200, 70, 30);
        mulLabel.setFont(new Font("", Font.PLAIN, 20));
        divLabel.setBounds(500 - 100, 200, 70, 30);
        divLabel.setFont(new Font("", Font.PLAIN, 20));
        panel.add(addLabel);
        panel.add(subLabel);
        panel.add(mulLabel);
        panel.add(divLabel);

        /*
        add action listener, if user selects one option, set other to false
         */
        addLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subLabel.setSelected(false);
                divLabel.setSelected(false);
                mulLabel.setSelected(false);
            }
        });
        subLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addLabel.setSelected(false);
                divLabel.setSelected(false);
                mulLabel.setSelected(false);
            }
        });
        mulLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subLabel.setSelected(false);
                divLabel.setSelected(false);
                addLabel.setSelected(false);
            }
        });
        divLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subLabel.setSelected(false);
                addLabel.setSelected(false);
                mulLabel.setSelected(false);
            }
        });
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String countString = countField.getText();
                String rangeString = rangeField.getText();
                if (pattern.matcher(countString).matches() && pattern.matcher(rangeString).matches()
                        && (signField.isSelected() || unsignField.isSelected())
                        && (addLabel.isSelected() || subLabel.isSelected()
                        || mulLabel.isSelected() || divLabel.isSelected())) {
                    count = Integer.valueOf(countString);
                    range = Integer.valueOf(rangeString);
                    if (signField.isSelected()) {
                        sign = true;
                    } else {
                        sign = false;
                    }
                    if (addLabel.isSelected()) op = ops.add;
                    else if (subLabel.isSelected()) op = ops.sub;
                    else if (mulLabel.isSelected()) op = ops.mul;
                    else if (divLabel.isSelected()) op = ops.div;
                    panel.removeAll();
                    repaint();
                    trainUI();
                } else {
                    panel.add(errorLabel);
                    repaint();
                }
            }
        });

        JButton reset = new JButton();
        reset.setBounds(410 - 100, 250, 120, 50);
        reset.setFont(new Font("Font.BOLD", Font.BOLD, 20));
        reset.setText("reset");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countField.setText("");
                rangeField.setText("");
                signField.setSelected(false);
                unsignField.setSelected(true);
            }
        });
        panel.add(reset);
        panel.setOpaque(false);
        JLabel bgLabel = new JLabel(bg);
        bgLabel.setSize(bg.getIconWidth() / 2, bg.getIconWidth() / 2);
        bgLabel.setBounds(500, 0, this.getWidth() / 2, this.getHeight() / 2);
        panel.add(bgLabel, Integer.valueOf(Integer.MIN_VALUE));

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void trainUI() {
        startTime = System.currentTimeMillis();
        ExpGen expGen = new ExpGen(op, range, count, sign);
        Map<String, Integer> mp = expGen.getQuestions(); // put String(expression) and int(answer) to mp
        ArrayList<String> questions = new ArrayList<String>();
        for (Map.Entry<String, Integer> entry : mp.entrySet()) {
            questions.add(entry.getKey());
        }
        question = new JLabel();
        ansField = new JTextField();
        JButton confirm = new JButton("confirm");

        JLabel bgLabel = new JLabel(bg);
        bgLabel.setSize(bg.getIconWidth() / 2, bg.getIconWidth() / 2);
        bgLabel.setBounds(500, 0, this.getWidth() / 2, this.getHeight() / 2);
        panel.add(bgLabel, Integer.valueOf(Integer.MIN_VALUE));

        question.setText(questions.get(currentPage) + "  = ");
        question.setBounds(250 - 100, 150, 300, 30);
        question.setFont(new Font("", Font.BOLD, 30));
        panel.add(question);


        ansField.setBounds(550 - 150, 150, 100, 30);
        ansField.setFont(new Font("", Font.BOLD, 30));
        panel.add(ansField);

        confirm.setFont(new Font("", Font.PLAIN, 20));
        confirm.setBounds(320 - 100, 250, 120, 30);
        panel.add(confirm);
        correctLabel.setFont(new Font("", Font.PLAIN, 20));

        nextButton.setFont(new Font("", Font.PLAIN, 20));
        nextButton.setBounds(320 - 100, 350, 120, 30);
        report.setBounds(220,360,100,40);
        report.setFont(new Font("", Font.BOLD, 20));


        report.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                panel.add(bgLabel, Integer.valueOf(Integer.MIN_VALUE));
                long time = (endTime - startTime) / 1000;
                JLabel timeLabel = new JLabel("You take " + time + "s to solve all questions");
                JLabel correctLabel = new JLabel("Accuracy: " + correctCount +  "/" + count);
                timeLabel.setBounds(100,100,400,30);
                correctLabel.setBounds(100,150,400,30);
                timeLabel.setFont(new Font("",Font.BOLD, 20));
                correctLabel.setFont(new Font("",Font.BOLD, 20));
                panel.add(timeLabel);
                panel.add(correctLabel);

                if(time < count * 10 && 1.0 * correctCount == count){
                    JLabel goodLabel = new JLabel("You are very good!");
                    goodLabel.setBounds(100,200,400,30);
                    goodLabel.setFont(new Font("",Font.BOLD, 20));
                    panel.add(goodLabel);
                }
                else if(time < count * 10 && 1.0 * correctCount / count >= 0.6){
                    JLabel goodLabel = new JLabel("Not bad!");
                    goodLabel.setBounds(100,200,400,30);
                    goodLabel.setFont(new Font("",Font.BOLD, 20));
                    panel.add(goodLabel);
                }
                else{
                    JLabel goodLabel = new JLabel("You need more practice!");
                    goodLabel.setBounds(100,200,400,30);
                    goodLabel.setFont(new Font("",Font.BOLD, 20));
                    panel.add(goodLabel);
                }
                repaint();
            }
        });
        /*
        Output different results based on the user's answer and whatever the user complete every questions
         */
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ansString = ansField.getText();  //user's answer
                int ans = Integer.parseInt(ansString);
                if (currentPage < count) {
                    /*
                    use my map to compare user's answer and the correct answer store in my map
                     */
                    if (pattern.matcher(ansString).matches()) {
                        if (ans == mp.get(questions.get(currentPage))) {    //mp is the map of questions and answer
                            correctLabel.setBounds(280 - 100, 280, 500, 30);
                            correctLabel.setText("Congratulations, you're right");
                            correctCount++;
                        } else {
                            correctLabel.setBounds(250 - 100, 280, 500, 30);
                            correctLabel.setText("Sorry, you're wrong, answer is " + mp.get(questions.get(currentPage)));
                        }
                    } else {
                        correctLabel.setText("Sorry, you're wrong, answer is " + mp.get(questions.get(currentPage)));
                    }
                    panel.remove(confirm);
                    panel.add(correctLabel);
                    if (currentPage < count - 1) {
                        panel.add(nextButton);
                    } else {
                        endTime = System.currentTimeMillis();
                        JLabel finish = new JLabel("Finish!");
                        finish.setBounds(320 - 100, 320, 300, 40);
                        finish.setFont(new Font("", Font.BOLD, 30));
                        panel.add(report);
                        panel.add(finish);
                    }
                    repaint();
                }
            }
        });
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPage++;
                question.setText(questions.get(currentPage) + "  = ");
                ansField.setText("");
                panel.add(confirm);
                panel.remove(correctLabel);
                panel.remove(nextButton);
                repaint();
            }
        });

    }

    /**
     * call UI and pop out my app
     */
    public static void main(String[] args) {
        UI test = new UI();
    }

}

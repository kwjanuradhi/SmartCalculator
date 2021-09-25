import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/**
 * A calculator program that can use to solve the complex arithmetic expressions.
 *
 * @author  : K.W.J.ANURADHI
 * @version : V03 24TH SEPTEMBER 2021
 */
public class SmartCalculator extends JFrame implements ActionListener

{
    
    JTextField display; // Screen to display the expressions and the results.
    JButton[] buttons; // Buttons array for the left panel
    // Right panel buttons.
    JButton backSpace;
    JButton clear;
    JButton openBracket;
    JButton closeBracket;
    // Bottom panel buttons.
    JButton factorial;
    JButton off;
    JButton calc;
    
    //Buttons names for right panel.
    String[] buttonNames = {"1","2","3","+","4","5","6","-","7","8","9","*","+/-","0",".","/"};
    //Buttons command array.
    String[] buttonCommands = {"CMD_ONE","CMD_TWO","CMD_THREE","CMD_ADD","CMD_FOUR","CMD_FIVE","CMD_SIX","CMD_MINUS","CMD_SEVEN","CMD_EIGHT","CMD_NINE","CMD_MUL",
                               "CMD_SIGN","CMD_ZERO","CMD_DOT","CMD_DIV"};
                               
    
    /**
     * Constructor for objects of class SmartCalculator
     */
    public SmartCalculator()
    {
        super("My PROG5001 - Calculator(1.0)");
        setSize(510, 510); // Set the default size of calculator window.
        setMinimumSize(new Dimension(510, 510)); // Set the minimum size of calculator window.
        //Event listener for identify the resizing calculator window.
        this.addComponentListener(new ComponentAdapter() {
            // Method call after resized the calculator window.
            public void componentResized(ComponentEvent e) {
               Dimension size = getSize();// Get the current size of the window
               setSize(size.height,size.height); // Resize the window to maintain the aspect ratio.
            }
        });
        CreateCalculatorGUI(); // Create calculator GUI.
    }
    
    /**
     * Check the character is an operator or not.
     * @param c This is the character which is used to check if it is an operator or not.
     * @return int This returns -1 for the non operators and value more than 0 for operators.
     */
    private int isOperator(char c) {
        //Check the character is operator or not.
        switch (c) {
            case '+':                
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
        }
        return -1; // Returns -1 for the non operators
    }
    
     /**
     * Check the character is a numeric value or not.
     * @param phrase This is the character which is used to check if it is a numeric value or not.
     * @return boolean This returns false for the non numeric values and true for others.
     */
     public boolean isNumeric(String phrase){
     try{ 
         // Try to convert the phrase to int
         Integer.parseInt(phrase);
         return true; // return true if the exception not occured.
         }
         catch(Exception e){
            return false;// return false if the exception occured.
         }
    }
    
    /**
     * This method is used to change the current sign of the numeric value(positive or negative).
     * @param expression This is the expression which is used to modify the sign of most last number.
     * @return String This returns updated expression.
     */
    private String changeNumericSign(String expression){
        
        for (int i = expression.length()-1; i >= 0; i--) {
            char currentCharacter = expression.charAt(i); //get the currentCharacter from the expression.
             // check the currentCharacter is not a numeric value.
             if (!isNumeric(String.valueOf(currentCharacter))) {
                 //check the currentCharacter is an operator.
                 if(isOperator(currentCharacter) > 0){
                    // get the numeric phrase which needs to change the sign. 
                    String numericExp = expression.substring(i + 1,expression.length());
                    int value = Integer.parseInt(numericExp); // convert phrase to the numeric value.
                    // check the currentCharacter for the minus.
                    if (currentCharacter == '-'){
                        if(i != 0){
                            char previousCharacter = expression.charAt(i -1);// get the previous character from the expression.
                            // check the previousCharacter is a numeric or not.
                            if(isNumeric(String.valueOf(previousCharacter))){
                                // if it is numeric change the sign
                                return expression.substring(0, i+1 ) + -value;
                            }else{
                                return expression.substring(0, i) + value;
                            }
                        }else{
                            return expression.substring(0, i) + value;
                        } 
                    }else{
                        return expression.substring(0, i+1 ) + -value;
                    }
                 }else{    
                     return expression;
                 }
             }
        }
        return "-" + expression;
    }
      
    /**
     * This method is used to create the GUI for calculator.
     */
    public void CreateCalculatorGUI() {
        
        JPanel panelTop = new JPanel();// Top panel.
        panelTop.setBackground(Color.gray);
        GridLayout panelTopLayout = new GridLayout(0,1);
        panelTopLayout.setHgap(20);
        panelTop.setLayout(panelTopLayout);
        panelTop.setBorder(BorderFactory.createLineBorder(Color.white,10));// set the panel border to keep the gap between the panels.
        
        JPanel panelLeft = new JPanel();// Left panel.
        GridLayout panelLeftLayout = new GridLayout(4, 4);
        panelLeftLayout.setHgap(10);
        panelLeftLayout.setVgap(10);
        panelLeft.setBackground(Color.white); // Set the background color of the panel. 
        panelLeft.setLayout(panelLeftLayout);
        panelLeft.setBorder(BorderFactory.createLineBorder(Color.white,10,true));
        
        JPanel panelRight = new JPanel();// Right and center panel.
        GridLayout panelRightLayout = new GridLayout(4, 1);
        panelRightLayout.setHgap(10);
        panelRightLayout.setVgap(10);
        panelRight.setBackground(Color.white);
        panelRight.setLayout(panelLeftLayout);
        panelRight.setBorder(BorderFactory.createLineBorder(Color.white,10,true));

        JPanel panelBottom = new JPanel();// Bottom panel.
        FlowLayout bottomLayout = new FlowLayout();// Flow layout.
        bottomLayout.setHgap(12);
        bottomLayout.setVgap(5);
        panelBottom.setBackground(Color.white);
        bottomLayout.setAlignment(FlowLayout.LEADING);
        panelBottom.setLayout(bottomLayout);

        BorderLayout mainLayout = new BorderLayout(); // JFrame layout.
        setLayout(mainLayout);
        add(panelTop, BorderLayout.NORTH);
        add(panelLeft, BorderLayout.CENTER);
        add(panelRight, BorderLayout.EAST);
        add(panelBottom, BorderLayout.SOUTH);
        
        // Create the text field for display
        display = new JTextField("0");
        Font displayFont = new Font ("SansSerif", Font.BOLD, 24);
        display.setFont(displayFont);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setPreferredSize(new Dimension(100,35));
        panelTop.add(display);
        
        // Create JButton array for left panel.
        buttons = new JButton[16];
        
        for (int i= 0; i< buttons.length; i++) {
             buttons[i] = new JButton();
             buttons[i].setPreferredSize(new Dimension(50,50));
             buttons[i].setBackground(new Color(200,200,200)); // Set the background color for the buttons.
             buttons[i].setBorder(BorderFactory.createLineBorder(new Color(167,167,167),1,true)); // Set the border color for the buttons.
             buttons[i].setOpaque(true);
             buttons[i].setText(buttonNames[i]);
             buttons[i].setActionCommand(buttonCommands[i]);
             buttons[i].addActionListener(this);
             buttons[i].setFont(new Font ("SansSerif", Font.BOLD, 24));// Set the font and the size of buttons.
             panelLeft.add(buttons[i]);
        }
        
        backSpace = new JButton("<<");
        backSpace.setPreferredSize(new Dimension(145,60));
        backSpace.setBackground(new Color(200,200,200));// Set the background color for the button.
        backSpace.setBorder(BorderFactory.createLineBorder(new Color(167,167,167),1,true)); // Set the border color for the button
        backSpace.setOpaque(true); // Set to true inorder to change the button color for Mac
        backSpace.addActionListener(this);
        backSpace.setActionCommand("CMD_BACKSP");
        backSpace.setFont(new Font ("SansSerif", Font.BOLD, 24));// Set the font and the size of buttons.
        panelRight.add(backSpace);
        
        clear = new JButton("C");
        clear.addActionListener(this);
        clear.setPreferredSize(new Dimension(150,100));
        clear.setBackground(new Color(200,200,200));
        clear.setBorder(BorderFactory.createLineBorder(new Color(167,167,167),1,true));
        clear.setOpaque(true);
        clear.setActionCommand("CMD_CLEAR");
        clear.setFont(new Font ("SansSerif", Font.BOLD, 24));
        panelRight.add(clear);
        
        openBracket = new JButton("(");
        openBracket.addActionListener(this);
        openBracket.setPreferredSize(new Dimension(150,100));
        openBracket.setBackground(new Color(200,200,200));
        openBracket.setBorder(BorderFactory.createLineBorder(new Color(167,167,167),1,true));
        openBracket.setOpaque(true);
        openBracket.setActionCommand("CMD_OPBR");
        openBracket.setFont(new Font ("SansSerif", Font.BOLD, 24));
        panelRight.add(openBracket);
        
        closeBracket = new JButton(")");
        closeBracket.addActionListener(this);
        closeBracket.setPreferredSize(new Dimension(150,100));
        closeBracket.setBackground(new Color(200,200,200));
        closeBracket.setBorder(BorderFactory.createLineBorder(new Color(167,167,167),1,true));
        closeBracket.setOpaque(true);
        closeBracket.setActionCommand("CMD_CLBR");
        closeBracket.setFont(new Font ("SansSerif", Font.BOLD, 24));
        panelRight.add(closeBracket);
        
        calc = new JButton("=");
        calc.setPreferredSize(new Dimension(235,60));
        calc.setBackground(new Color(200,200,200));
        calc.setBorder(BorderFactory.createLineBorder(new Color(167,167,167),1,true));
        calc.setOpaque(true);
        calc.addActionListener(this);
        calc.setActionCommand("CMD_CAL");
        calc.setFont(new Font ("SansSerif", Font.BOLD, 24));
        panelBottom.add(calc);
        
        factorial = new JButton("!");
        factorial.setPreferredSize(new Dimension(70,60));
        factorial.setBackground(new Color(200,200,200));
        factorial.setBorder(BorderFactory.createLineBorder(new Color(167,167,167),1,true));
        factorial.setOpaque(true);
        factorial.addActionListener(this);
        factorial.setActionCommand("CMD_FACTORIAL");
        factorial.setFont(new Font ("SansSerif", Font.BOLD, 24));
        panelBottom.add(factorial);
        
        off =new JButton("OFF");
        off.setPreferredSize(new Dimension(160,60));
        off.setBackground(new Color(243,172,142));
        off.setBorder(BorderFactory.createLineBorder(new Color(237,126,51),1,true));
        off.setOpaque(true);
        off.addActionListener(this);
        off.setActionCommand("CMD_OFF");
        off.setOpaque(true);
        off.setFont(new Font ("SansSerif", Font.BOLD, 24));
        panelBottom.add(off);
        
        } 
        
     /**
     * This method is used to trigger when the button is clicked.
     * @param e This is the button ActionEvent.
     */
    public void actionPerformed(ActionEvent e) {
        String expression; // infix expression.
        expression = display.getText(); // get the value of text field to the expression.
        String cmd = e.getActionCommand(); // get the button ActionCommand.

      if (cmd.equals("CMD_ONE")){
          // if the expression includes only the 0, initialize the expression with command character.
          if(expression.equals("0")){
              expression = "1";
            } else{ 
              expression = expression + "1";  
            }
      }else
      if (cmd.equals("CMD_TWO")){
          if(expression.equals("0")){
              expression = "2";
            } else{ 
              expression = expression + "2";  
            }
      }else
      if (cmd.equals("CMD_THREE")){
         if(expression.equals("0")){
              expression = "3";
            } else{ 
              expression = expression + "3";  
            }
      }else
      if (cmd.equals("CMD_FOUR")){
          if(expression.equals("0")){
              expression = "4";
            } else{ 
              expression = expression + "4";  
            }
      }else
      if (cmd.equals("CMD_FIVE")){
         if(expression.equals("0")){
              expression = "5";
            } else{ 
              expression = expression + "5";  
            }
      }else
      if (cmd.equals("CMD_SIX")){
         if(expression.equals("0")){
              expression = "6";
            } else{ 
              expression = expression + "6";  
            }
      }else
      if (cmd.equals("CMD_SEVEN")){
         if(expression.equals("0")){
              expression = "7";
            } else{ 
              expression = expression + "7";  
            }
      }else
      if (cmd.equals("CMD_EIGHT")){
          if(expression.equals("0")){
              expression = "8";
            } else{ 
              expression = expression + "8";  
            }
      }else
      if (cmd.equals("CMD_NINE")){
          if(expression.equals("0")){
              expression = "9";
            } else{ 
              expression = expression + "9";  
            }
      }else
      if (cmd.equals("CMD_ZERO")){
          if(expression.equals("0")){
              expression = "0";
            } else{ 
              expression = expression + "0";  
            }
      }else
      if (cmd.equals("CMD_DOT")){
          expression = expression + ".";
      }else
      if (cmd.equals("CMD_SIGN")){
          expression = changeNumericSign(expression); // change the sign of last numeric phrase of the expression.
      }else
      if (cmd.equals("CMD_EQUAL")){
          expression = expression + "=";
      }else
      if (cmd.equals("CMD_ADD")){
          expression = expression + "+";
      }else
      if (cmd.equals("CMD_MINUS")){
          expression = expression + "-";
      }else
      if (cmd.equals("CMD_MUL")){
          expression = expression + "*";
      }else
      if (cmd.equals("CMD_DIV")){
          expression = expression + "/";
      }else
      if (cmd.equals("CMD_FACTORIAL")){
          expression = expression + "!";
      }else
      if (cmd.equals("CMD_BACKSP")){
          int backSpace = expression.length();
          if( backSpace !=0){
            expression = expression.substring(0, backSpace-1); // remove the last character from expression.
            }
            if(expression.equals("")){
              expression = "0";
            } 
      }else
      if (cmd.equals("CMD_CLEAR")){
          expression = "0";
      }else
      if (cmd.equals("CMD_OPBR")){
          if(expression.equals("0")){
              expression = "(";
          }else{
              expression = expression + "(";
          }
      }else
      if (cmd.equals("CMD_CLBR")){
          expression = expression + ")";
      }else
      if (cmd.equals("CMD_OFF")){ 
          this.dispose();
      }
      display.setText(expression);
      if (cmd.equals("CMD_CAL")){
          processExpression(expression); //solve the expression input by the user.
      }
    }

     /**
     * This method is used to check the invalid expression.
     * @param infix This is the expression which entered by the user.
     * @return boolean This returns expression is valid or not.
     */
    public boolean isInvalidExpressions(String infix){
        for (int i = 0; i < infix.length(); i++) {
            char currentCharacter = infix.charAt(i);
            //Check two operators at one place (1+*2)
            if(isOperator(currentCharacter) > 0){
                if(i < infix.length()){
                    char nextCharactor = infix.charAt(i + 1); // get the next character 
                    // Check the next character is operator.
                    if(isOperator(nextCharactor)> 0){
                        display.requestFocus(); // request focus on display textfield for selection.
                        display.setSelectionStart(i);// set initial selection start point.
                        display.setSelectionEnd(i + 2);// set selection end point.
                        return true;
                    }    
                }
            }
            //Check two dots at one place (1..2)
            if( currentCharacter == '.'){
                 if(i < infix.length()){
                    char nextCharactor = infix.charAt(i + 1);
                    if(nextCharactor == '.'){
                        display.requestFocus();
                        display.setSelectionStart(i );
                        display.setSelectionEnd(i + 2);
                        return true;
                    }          
                 }
                
            }  
        }
        return false;
    }
    
    /**
     * This method is used to create factorial expression.
     * @param infix This is the expression which need to check for the factorial expression.
     * @return String This returns expression with factorial expression.
     */
    public String convertFactorialExpressions(String infix){
         
         for (int i = 0; i < infix.length(); i++) {
             char c = infix.charAt(i);
             // find is there any factorial in the expression.
             if(c == '!'){
                 String factorialExpression = "";
                 char numberCharactor = infix.charAt(i-1); //get the factorial number as a character.
                 int factoialNumber = Integer.parseInt(String.valueOf(numberCharactor)); // convert the factorial number to int.
                 //Create factorial expression.
                 for(int x = factoialNumber; x > 0; x--){
                     if(factoialNumber == x){
                         factorialExpression = "("  + x ;
                     }else if(x == 1){
                         factorialExpression = factorialExpression + "*" + x + ")";
                     }else{
                          factorialExpression = factorialExpression + "*" + x;
                     } 
                 }
                 // return the expression with concatanate with factorial expression.
                 return infix.substring(0,i - 1) +  factorialExpression + infix.substring(i+ 1, infix.length());
             }
         }
         return infix;
     }
     
     /**
     * This method is used to process the user enterd expression and calculate the result.
     * @param expression This is the expression which need to solve.
     */
    public void processExpression(String expression){
       // Check the expression is valid 
       if(!isInvalidExpressions(expression)){
            String formatedExpression = convertFactorialExpressions(expression); // convert factorial expression.
            String postFixExpression = convert(formatedExpression);// convert to postfix expression.
            double result = evaluate(postFixExpression);// calculate the result
            display.setText(Double.toString(result));// show result in the display
        }
    }
     
     /**
     * This method is used to convert the infix expression to the postfix expression.
     * @param inFixExpression This is the expression which need convert to the postfix.
     * @return String This returns postfix expression.
     */
    public String convert(String inFixExpression) {
        Stack<Character> stack = new Stack();
        String postFixExpression = "";
        for (int i = 0; i < inFixExpression.length(); i++) {
            char currentCharacter = inFixExpression.charAt(i);
            //Check currentCharacter is an operator
            if (isOperator(currentCharacter) > 0) {
                while (!stack.isEmpty() && (isOperator(currentCharacter) <= isOperator(stack.peek()))) {
                    postFixExpression = postFixExpression + stack.pop();
                }
                stack.push(currentCharacter); // push the operator to stack
            } else 
            if (currentCharacter == '(') {
                stack.push(currentCharacter); // push the open paranthesis to stack
            } else
            if (currentCharacter == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postFixExpression = postFixExpression + stack.pop();
                }
                stack.pop(); //take out the parenthesis from the stack
            } else {
                postFixExpression = postFixExpression + currentCharacter;
            }            
        }
        while (!stack.isEmpty()) {
            postFixExpression = postFixExpression + stack.pop();
        }
        return postFixExpression;
    }
    
     /**
     * This method is used to evaluate the postfix expression.
     * @param postFixExpression This is the expression which need evaluate.
     * @return double This returns result of the expression.
     */
    public double evaluate(String postFixExpression) {
        Stack<Double> stack = new Stack();
        double result = 0;
        for (int i = 0; i < postFixExpression.length(); i++) {
            char currentCharacter = postFixExpression.charAt(i);
            //Check currentCharacter is an operator
            if (isOperator(currentCharacter) > 0) {
                double secondOperand = Double.parseDouble("" + stack.pop()); // get second operand from stack.
                double firstOperand = Double.parseDouble("" + stack.pop()); // get first operand from stack.
                if (currentCharacter == '+') {
                    result = firstOperand + secondOperand;
                } else
                if (currentCharacter == '-') {
                    result = firstOperand - secondOperand;
                } else
                if (currentCharacter == '*') {
                    result = firstOperand * secondOperand;
                } else
                if (currentCharacter == '/') {
                    result = firstOperand / secondOperand;
                }                
                stack.push(result); // push the result to stack
            } else {
                stack.push(Double.parseDouble("" + currentCharacter));  // push operand to the stack              
            }            
        }
        result = stack.pop(); // get the result out of the stack.
        return result;
  }
  
  public void keyPressed(KeyEvent e) {    
  }
  
  public void keyReleased(KeyEvent e){
  }
  
  public void keyTyped(KeyEvent e){
  } 
  
  public static void main (String[] args){
      // Create calculator gui object
      SmartCalculator calculatorGui = new SmartCalculator();
      calculatorGui.setVisible(true);// set JForm visible.
  }
}


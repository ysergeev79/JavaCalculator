
/*
	 ============================================================================
	 Name        : JCalcGUI
	 Author      : Yuliya Volodina

	 This program is a GUI, that displays a calculator, implementing user-interaction.
	  It utilizes the Table Layout Manger to organize the JButtons, and allows the user to press on buttons in order
	  to generate a mathematical expression that gets printed onto a JTextField, and then allows the program to calculate 
	  the expression and return the value to another JTextField. The infix expression is parsed from the input text field and enqueued to the input queue.  
	 This program works with operators such as, '-','+','x','%','/' and integer variables.
	 It utilizes the classes Stack ( to hold the operators), Queue (for the input and output queues),
	  Listnode, and Stacknode.
	 ============================================================================
	 */

import acm.program.*;
import acm.gui.TableLayout;

import java.awt.event.*;
import javax.swing.*;



public class JCalcGUI extends Program {
	
	
	private static final long serialVersionUID = -7733971412588556657L;
	
	Queue inputQ = new Queue(); // create an input queue
	Queue outputQ = new Queue(); // create an output queue
	Stack opStack = new Stack(); // create an operator stack
	
	
	JTextField input_expression = new JTextField(); // text field for input expression
	JTextField answer = new JTextField(); // text field for output calculated value
	
	String expr = "";
	
	
	
	char operators[]= {'-','+','x','/','%','^'}; //char array that holds all possible operators that will 
	                                             //function as dilineators in the parser
	
	
	public void run (){
		
		setDisplay();
		addActionListeners();
		
		
	}
	

	public void setDisplay(){ // set the calculator's text fields and JButtons
		
		setLayout( new TableLayout(8,4));
		add(input_expression, "gridwidth = 4");
		add(answer,"gridwidth = 4");
		add(new JButton("C"));
		add(new JButton("%"));
		add(new JButton("/"));
		add(new JButton("x"));
		add(new JButton("7"));
		add(new JButton("8"));
		add(new JButton("9"));
		add(new JButton("-"));
		add(new JButton("4"));
		add(new JButton("5"));
		add(new JButton("6"));
		add(new JButton("+"));
		add(new JButton("1"));
		add(new JButton("2"));
		add(new JButton("3"));
		add(new JButton("="));
		add(new JButton("0"));
		add(new JButton("."));
		add(new JButton("^"));
		
		
	}
	
	/* Inspired by Professor Ferrie's notes.
	 * (non-Javadoc)
	 * @see acm.program.Program#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e){ 
		
		String keyPressed = e.getActionCommand(); // get the String value of the pressed button
		
		switch (keyPressed){
		
		case "C": // if the pressed button is "C", then clear the input and output textfields, and set the input and output Queues' fronts and rears to null
		
		expr = "";
		input_expression.setText(expr);
		answer.setText(expr);
		inputQ.clear();
		outputQ.clear();
		break;
		
		case "=": // if the "=" is pressed, then parse the expression by calling the parse(String args) function, and print the evaluated expression to the answer text field to 6 decimal places precision
			
	    input_expression.setText(expr+"=");
		parse(expr);
		answer.setText(String.format("%6f", doExpression()));
		break;
			
		
			
		default:
		
		expr +=keyPressed; //if any numerical value or operator value is pressed, print their string value onto the input text field
		input_expression.setText(expr);
		break;
		
		
		}
	}
	
	
	/* Source: Professor Ferrie's notes
	 * This method parses the input expression at the operators
	 * @param String arg
	 * @return void
	 */
	
	public void parse (String arg){
		
		int i,j, p;
		p=0;
		for(i=0; i<arg.length();i++){ //iterate through the input expression
			for(j=0;j<operators.length;j++){ //iterate through all possible operator delimiters, 
				                                 // and if a substring character is equal to one of them
				                                    //enqueue the operands before it and the operator.
				if(arg.charAt(i)==operators[j]){ 
					inputQ.enqueue(arg.substring(p,i));
					inputQ.enqueue(arg.substring(i,i+1));
					p=i+1;
				}
			}
		}
		
		inputQ.enqueue(arg.substring(p)); // enqueue remaining substrings
		
	}
	
	/*
	 * This method once called, in turn calls a function to convert the infix expression to a postfix, and then calculates the expression
	 * @param void
	 * @ return double
	 */
	public double doExpression() {
		
		InfixToPostfix(inputQ,outputQ,opStack); // convert infix expression to postfix
      
		return  calculate_expression(outputQ, opStack); //evaluate the postfix expression
		 

}
	
	
/*
 * The method getOperatorPrecedence determines the priority of an operator, when used to compare two operators
 * @param: String operator
 * @return: int (priority value)	
 */
static int hasHigherPrecedence(String operator) {
    switch(operator) {
        case "+"  :
        case "-":
            return 1;
        case "x":
        case "/":
        case "%":
            return 2;
        case "(":
        case ")":
            return 3;
        case "^":
             return 4;
        
        default:
            return -1;
    }
}


/*
 * isCharOperator determines whether the token is an operator or not
 * @param: String element (token)
 * @return boolean (true if operator)
 */


 static boolean isCharOperator(String element){
    
	if(element.equals("+")|| element.equals("-") || element.equals("x") || element.equals("/") || element.equals("%") || element.equals("(") || element.equals(")") || element.equals("^")){
		return true;
	}
        return false;
}







/*
 * The method InfixToPostfix, follows the Shunting Yard algorithm inspired by Edsger Dijkstra to convert
 * an infix mathematical expression to a postfix expression
 * @param: Queue input, Queue output, Stack opStack
 * @return: void
 */

static void InfixToPostfix(Queue inputQ, Queue outputQ, Stack opStack){
	
	   String data = null;
	   String data2Add = null;
	  while(!inputQ.isEmpty()){ // check that the input Queue is not empty
		  
		   data = inputQ.dequeue(); // dequeue the first element in queue to analyze
	      
		if(isCharOperator(data)){ // check if data is an operator
			
			if( data.equals(")")){ ///if the dequeued data is a closing parenthesis
				                    //as long as the stack is not empty and the top operator is not an opening parenthesis, pop the next data and enqueue to output queue
				
				while (!opStack.isEmpty() && !(opStack.top.operator).equals("(")){
					  data2Add = opStack.pop();
					//System.out.print("toAdd"+ toAdd);
					outputQ.enqueue(data2Add);
				}
				opStack.pop(); //pop off the opening parenthesis
				
				
			}else{
			
			
			
		    //If the top of the stack is not empty, and its top operator is not an opening parenthesis and has a higher priority than 'data'
			//pop the operator off the stack and enqueue it onto output queue
			while (!opStack.isEmpty() && !(opStack.top.operator).equals("(") && hasHigherPrecedence(opStack.top.operator) >= hasHigherPrecedence(data)){
	    		
				data2Add = opStack.pop(); 
	    		outputQ.enqueue(data2Add);
	    		
	    	 }
	    	    //If the stack is empty or 'data' is of higher priority than the element on top of stack, then
			    //push data onto the stack
	    	    opStack.push(data);
			} 
	    	
	    	
	    }	
		else { // if 'data' is a number, enqueue it onto output queue
	    	
	    	outputQ.enqueue(data);
	    	
	    	
	    }
	}
	    //enqueue onto output queue the remainder operators that are left on the stack except for parentheses
	    while (!opStack.isEmpty()) { 
          
           data2Add = opStack.pop();
           
           if (!data2Add.equals("(") && !data2Add.equals(")"))
               
       
               outputQ.enqueue(data2Add);
	    	  
	    
	    	
	    } 
	    	
}

/*
 * The method calculate_expression, evaluates the postfix expression in float precision, and prints out the answer
 * @param: Queue outputQ, Stack opStack
 * @return void
 * Inspired by Parsun Lala tutorial notes
 */


static double calculate_expression(Queue outputQ, Stack opStack ) {
	
	
	while (!outputQ.isEmpty()){ //loop through the outputQ and analyze each token
		
		float answer = 0;
		float op2 =0; // second operand
		float op1=0;  // first operand
		
				
		String token =outputQ.dequeue(); 
		
		
		if(!isCharOperator(token)) { //if the token is not an operator, push it onto the stack
			opStack.push(token);
			
		}else{
			
			if(token.equals("-")){
				 op2 = Float.valueOf(opStack.pop());// pop off an operand, if the stack is then empty, then the token is a unary operator '-'
				if(opStack.isEmpty()){
				answer = -1*op2;
				
			}else{
				 op1= Float.valueOf(opStack.pop());//if the stack is not empty, perform the addition function on both operands
				answer = op1-op2;
			}
			}
			
			if(token.equals("+")){ // pop off two operands and apply the addition
				op2=Float.valueOf(opStack.pop());
		         op1= Float.valueOf(opStack.pop());
		         answer= op1+op2;
					
				
				 
					
			}
			
			
				 
			if(token.equals("x")) { //pop  off both operands if the token if a "x" and multiply them
			         op2=Float.valueOf(opStack.pop());
			         op1= Float.valueOf(opStack.pop());
					answer = op1*op2;
					 
			}
            if ( token.equals("/")){//pop  off both operands if the token is a "/" and divide them
            	
            	 op2=Float.valueOf(opStack.pop());
		         op1= Float.valueOf(opStack.pop());
				
				answer = op1/op2;
			}
            if (token.equals("%")){ //pop  off both operands if the token if a "%" and find their modulus
            	 op2=Float.valueOf(opStack.pop());
		        op1= Float.valueOf(opStack.pop());
				answer = op1%op2;
			}
            if (token.equals("^")){ //pop  off both operands if the token if a "^" and find the value of op1 to the power of op2
            	 op2=Float.valueOf(opStack.pop());
		        op1= Float.valueOf(opStack.pop());
				answer = (float)Math.pow((double)op1, (double)op2);
			}
           
			opStack.push(String.valueOf(answer)); //push the answer onto the stack
			
		}
	}
		//pop off the answer and print it
		
		return Float.valueOf(opStack.pop());
			
					
			
	}		

			



} // end of class 
	
	


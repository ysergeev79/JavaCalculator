

/*
 * The Stack class that holds the operators and controls the functions push(arg), pop(), and isEmpty()
 */
	
	public  class Stack {

		
		 Stacknode top = null; // create the top of the stack
		
		
		
		/*
		 * The push method, adds the an argument (operator) to the top of the stack
		 * @param: String arg
		 * @return: void
		 * Reference: Professor Ferrie's notes
		 */
		
		 void  push(String arg) {
			Stacknode node = new Stacknode(); // create a new node
			node.operator = arg; // assign the argument to the new node
			if(top!= null) // if the stack is not empty, make the next node the one that was previously the top one
				           //else if the stack was empty, make the next node, underneath the top, point to null
				node.next = top;
			else
				node.next = null;
			top = node;
		}

		 /*
		  * The pop() method, gets rid of the operator on top of the stack
		  * @param: null
		  * @return String
		  * Reference: Professor Ferrie's notes 
		  */
		 String pop(){
			if (top == null)
				return null;
			String operator = top.operator; // if the stack is not empty, make the next node, the new top, and pop off the operator
			top = top.next;
			
			return operator;
		}
		
		/*
		 * Checks if the stack is empty, by analyzing its top
		 * @param: null\
		 * @return: boolean; true if empty, false if not empty
		 */
		 boolean isEmpty() {
			 
			    return (top == null);
			 }

		 
		 
	}

	
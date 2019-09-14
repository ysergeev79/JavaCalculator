

/*
 * The class Queue to hold the input and output queues
 * controls the methods enqueue(arg), dequeue(), and isEmpty()
 * Reference: http://math.hws.edu/javanotes/c9/s3.html
 */
public class Queue {
	
	

	Listnode front = null; // front of queue
	Listnode rear = null; // rear of queue
	
	
	
/*
 * Method enqueue, adds a new element onto a queue
 * @param: Sring arg
 * @return: void
 */
 
public void enqueue(String arg ) {
    Listnode newNode = new Listnode();  // create a node to hold the new element.
    newNode.payload = arg; 
    if (front == null) {
          // If the front of queue is empty, the new node becomes the only node, and therefore
    	  // the queue's front and rear
          
       front = newNode;
       rear = newNode;
      
    } 
    else {
        // The new node becomes the rear the queue.
      
     rear.next = newNode;
     rear = newNode;
    
  }
}
  
/*
 * The method dequeue, takes off an element from the front of the queue
 * @param: null
 * @return: String
 */


public String dequeue() {
   
	if ( front == null){
		
		
       return null; 
	}
	String firstInQ = front.payload; 


    front= front.next;  // The  second element becomes the first one
                 
    if (front == null) {
          // The queue is emptied if the front is null, and thus there is no rear.  
    }
 
    return firstInQ;
     
 }
 
 /*
  * The method determines if the queue is empty by checking the front of the queue
  * @return: boolean : true if empty
  */
 boolean isEmpty() {
	
    return (front == null);
    
 }
 
 /*
  * This method clears a queue by setting it's  front and rear to null. 
  * 
  */
 public void clear(){
	 front = null;
	 rear = null;
 }
	
}
	
		
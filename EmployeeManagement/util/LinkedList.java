package util;
import util.LinearNode;
import util.LinkedListADT;

public class LinkedList<T> implements LinkedListADT<T> {	
	private int size; // the current number of elements in the list
	private LinearNode<T> front; // pointer to the first element
	private LinearNode<T> last; // pointer to the last element



	// -----------------------------------------------------------------
	// Creates an empty list.
	// -----------------------------------------------------------------
	public LinkedList() {
		this.size = 0;
		this.last = null;
		this.front = null;
	}

	public LinearNode<T> getFront() {
		return this.front;
	}

	public void setFront(LinearNode<T> node) {
		front = node;
	}

	public int size() {
		return size;
	}

	// Increments the size of the linked list
	public void incrementSize() {
		size++;
	}

	// Decrements the size of the linked list
	public void decrementSize() {
		size--;
	}




	// Adds one element to the end of this list
	public void add(T element) {
		LinearNode<T> node = new LinearNode<T>(element);

		if (size() == 0) {
			this.last = node; // this is the last and the
			this.front = node; // first node
			this.size++;
		} else {
			last.setNext(node); // add node to the end of the list
			last = node; // now make this the new last node.
			this.size++;
		}
	}

	// an element at a specific position in the list
	//call this in the driver class
	public void add(T element, int position) {
		if (position < 1 || position > size() + 1) {
			System.out.println("Invalid position");
			return;
		}

		LinearNode<T> newNode = new LinearNode<>(element);

		if (position == 1) {
			//add to the front
			newNode.setNext(front);
			front = newNode;
			if (size() == 0) {
				last = newNode; //if its first element its both front and last
			}
			size++;
		} else {
			LinearNode<T> current = front;
			int count = 1;
			while (count < position - 1) {
				current = current.getNext();
				count++;
			}
			newNode.setNext(current.getNext());
			current.setNext(newNode);
			if (newNode.getNext() == null) {
				last = newNode; //if the new node is inserted at the end, update the last pointer
			}
			count++;
		}
	}

	
	



	//checks if the element is already pre-existing in the list 
	public boolean contains(T element) {
		if (!isEmpty()) {
			for (LinearNode<T> current = this.front; current != null; current = current.getNext()) {
				if (current.getElement().equals(element)) {
					return true; //found duplicate
				}
			}
		}
		return false; //no duplicate found
	}



	//removes and returns the first element from this list
	public T remove() {
		LinearNode<T> temp = null;
		T result = null;
		if (isEmpty()) {
			System.out.println("There are no nodes in the list");
		} // end if
		else {
			result = this.front.getElement();
			temp = this.front;
			this.front = this.front.getNext();
			temp.setNext(null); // dereference the original first element
			size--;
		} // end else
		return result;
	}




	//-------------------------------------------------------------
	//3. (1) polymorphic method by element details - remove employee
	public T remove(T element) {
		LinearNode<T> current = this.front;
		LinearNode<T> previous = this.front;
		T result = null;

		// Check if list is empty
		if (isEmpty()) {
			System.out.println("There are no nodes in the list");
			return null;
		}

		//If the FIRST node matches the element, have to use separate if input is first employee due to errors
		if (current.getElement().equals(element)) {
			result = current.getElement(); // Save the element to return it like temp
			this.front = current.getNext(); //go to next node 

			current.setNext(null); //dereference the first node so garbage collection deletes it!
			size--; 
			return result;
		}

		//search through list
		while (current != null) {
			if (current.getElement().equals(element)) {
				result = current.getElement(); //save element to return it like a temp

				//if removing a NON first element, then bypass to next node (SET previous to next, and next to get next)
				previous.setNext(current.getNext());

				current.setNext(null); //garbage collection
				size--;
				return result; // Return the removed element
			}

			// Move to the next node
			previous = current;  // previous will move with current
			current = current.getNext(); // current moves to the next node
		}

		// If no matching element was found
		return null;
	}
	//----------------------------------------------------------

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//3. (2) polymorphic method by index - remove employee
	public T remove(int index) {
		if (index < 0 || index >= size()) {
			System.out.println("Index out of bounds");
			return null;
		}

		//if  the index is 0, use the existing remove() method for the first element
		if (index == 0) {
			return remove(); // This will remove the first element
		}

		// Traverse to the node at the specified index
		LinearNode<T> current = this.front;
		LinearNode<T> previous = null;
		T result = null;

		//search for element at the specified index
		for (int i = 0; i < index; i++) {
			previous = current;
			current = current.getNext();
		}

		//save the element to return it
		result = current.getElement();
		previous.setNext(current.getNext()); // Remove the node by bypassing it
		current.setNext(null); // Dereference the removed node
		size--;
		return result;
	}
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++




	// Returns true if this list contains no elements
	public boolean isEmpty() {
		if (this.front == null)
			return true;
		else
			return false;
	}

	//return the element at the specified index
	public T get(int index) {
		LinearNode<T> current = this.front;
		int counter = 0;

		while (current != null && counter < index) {
			current = current.getNext();
			counter++;
		}

		if (current == null) {
			return null;
		} else {
			return current.getElement();
		}
	}



	// Returns a string representation of this list

	public String toString() {
		LinearNode<T> current = null;
		String fullList = "";

		for (current = this.front; current != null; current = current.getNext()) {
			fullList = fullList + "\n" + current.getElement().toString();
		} // end for

		return fullList + "\n";
	}

}

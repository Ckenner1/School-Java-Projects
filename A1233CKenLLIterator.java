package cis233.a1;// LinkedListIterator class; maintains "current position"
//
// CONSTRUCTION: Package visible only, with a ListNode
//
// ******************PUBLIC OPERATIONS*********************
// void advance( )        --> Advance
// boolean isValid( )     --> True if at valid position in list
// AnyType retrieve       --> Return item in current position


public class A1233CKenLLIterator<AnyType extends Comparable<? super AnyType>>
{
    /**
     * Construct the list iterator
     * @param theNode any node in the linked list.
     */
    A1233CKenLLIterator( A1233CKenListNode<AnyType> theNode )
    {
        current = theNode;
    }

    /**
     * Test if the current position is a valid position in the list.
     * @return true if the current position is valid.
     */
    public boolean isValid( )
    {
        return current != null;
    }

    /**
     * Return the item stored in the current position.
     * @return the stored item or null if the current position
     * is not in the list.
     */
    public Comparable retrieve( )
    {
        return isValid( ) ? current.element : null;
    }

    /**
     * Advance the current position to the next node in the list.
     * If the current position is null, then do nothing.
     */
    public void advance( )
    {
        if( isValid( ) )
            current = current.next;
    }

    A1233CKenListNode<AnyType> current;    // Current position
}
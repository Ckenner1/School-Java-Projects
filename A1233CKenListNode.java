package cis233.a1;

/**
 * Created by Jason2008 on 4/4/2016.
 */
// Basic node stored in a linked list
// Note that this class is not accessible outside
// of package weiss.nonstandard

class A1233CKenListNode<AnyType extends Comparable<? super AnyType>>
{
    // Constructors
    public A1233CKenListNode( AnyType theElement )
    {
        this( theElement, null );
    }

    public A1233CKenListNode( AnyType theElement, A1233CKenListNode<AnyType> n )
    {
        element = theElement;
        next    = n;
    }

    public Comparable element;
    public A1233CKenListNode<AnyType> next;
}
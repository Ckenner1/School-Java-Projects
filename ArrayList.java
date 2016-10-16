import weiss.util.*;

public class A2232CKen<AnyType extends Comparable<? super AnyType>>
        extends AbstractCollection<AnyType> implements List<AnyType>
{

    private static final int DEFAULT_CAPACITY = 10;
    private static final int NOT_FOUND = -1;

    private AnyType[] theItems = (AnyType [])new Comparable [ DEFAULT_CAPACITY ];
    private int theSize;
    private int modCount = 0;

    /**
     * Construct an empty ArrayList.
     */
    public A2232CKen( )
    {
        clear( );
    }
    /**
     * Construct an ArrayList with same items as another Collection.
     */
    public A2232CKen(Collection<? extends AnyType> other )
    {
        theItems = (AnyType [])new Comparable [other.size()];
        for( AnyType obj : other )
            add( obj );
    }

    public A2232CKen(int array_size)
    {
        theItems = (AnyType [])new Comparable [array_size];
    }

    /**
     * Returns the number of items in this collection.
     * @return the number of items in this collection.
     */
    public int size( )
    {
        return theSize;
    }

    /**
     * Returns the item at position idx.
     * @param idx the index to search in.
     * @throws ArrayIndexOutOfBoundsException if index is out of range.
     */
    public AnyType get( int idx )
    {
        if( idx < 0 || idx >= size( ) )
            throw new ArrayIndexOutOfBoundsException( "Index " + idx + "; size " + size( ) );
        return theItems[ idx ];
    }

    /**
     * Changes the item at position idx.
     * @param idx the index to change.
     * @param newVal the new value.
     * @return the old value.
     * @throws ArrayIndexOutOfBoundsException if index is out of range.
     */
    public AnyType set( int idx, AnyType newVal )
    {
        if( idx < 0 || idx >= size( ) )
            throw new ArrayIndexOutOfBoundsException( "Index " + idx + "; size " + size( ) );
        AnyType old = theItems[ idx ];
        theItems[ idx ] = newVal;
        addSort(theItems);

        return old;
    }

    /**
     * Tests if some item is in this collection.
     * @param x any object.
     * @return true if this collection contains an item equal to x.
     */
    public boolean contains( Object x )
    {
        return findPos( x ) != NOT_FOUND;
    }

    /**
     * Returns the position of first item matching x in this collection,
     * or NOT_FOUND if not found.
     * @param x any object.
     * @return the position of first item matching x in this collection,
     * or NOT_FOUND if not found.
     */
    private int findPos( Object x )
    {
        for( int i = 0; i < size( ); i++ )
            if( x == null )
            {
                if( theItems[ i ] == null )
                    return i;
            }
            else if( x.equals( theItems[ i ] ) )
                return i;

        return NOT_FOUND;

    }
    /**
     * Adds an item to this collection, at the end.
     * Sorts collection to move element into the proper place,
     * if an index is provided the method inserts the element at the give index
     * then sorts the list.
     * @param element any object.
     * @return true.
     */
    public boolean add(AnyType element )
    {
        if( theItems.length == size( ) )
        {
            AnyType [ ] old = theItems;
            theItems = (AnyType []) new Comparable[ theItems.length * 2 + 1 ];
            for( int i = 0; i < size( ); i++ )
                theItems[ i ] = old[ i ];
        }

        theItems[ theSize++ ] = element;

        modCount++;

        addSort(theItems);
        return true;
    }

    public boolean add(int index ,AnyType element )
    {
        if( theItems.length == size( ) )
        {
            AnyType [ ] old = theItems;
            theItems = (AnyType []) new Comparable[ theItems.length * 2 + 1 ];
            for( int i = 0; i < size( ); i++ )
                theItems[ i ] = old[ i ];
        }

        if(index>(theSize))
            throw new ArrayIndexOutOfBoundsException("Index: " + index + " Size: " + theSize);

        if(theItems[index]!= null)
        {
            for(int i = theSize-1; i>=index;i--)
            {
                theItems[i+1] = theItems[i];
            }
        }
        theItems[ index ] = element;
        addSort(theItems);

        theSize++;
        modCount++;

        return true;
    }

    private void addSort(AnyType [] arr)
    {
            int n = theSize;
            for (int j = 1; j < n; j++) {
                AnyType compare_value = arr[j];
                int i = j - 1;
                while ((i > -1) && (arr[i].compareTo(compare_value) > 0)) {
                    arr[i + 1] = arr[i];
                    i--;
                }
                arr[i + 1] = compare_value;
            }






    }

    public Result<AnyType> getMode()
    {
        AnyType mode = null;
        AnyType compared_instance = null;
        int mode_counter = 0;
        int compared_instance_counter = 1;

        for(int i = 0;i<theSize;i++)
        {
            compared_instance = theItems[i];
            if(i!=0 && compared_instance.equals(theItems[i-1]))
                compared_instance_counter++;
            else
                compared_instance_counter = 1;
            if(compared_instance_counter>mode_counter)
            {
                mode_counter = compared_instance_counter;
                mode = compared_instance;
            }
        }
        return new ForResult(mode,mode_counter);
    }

    public class ForResult implements Result<AnyType>
    {
        private AnyType mode;
        private int count;

        public ForResult(AnyType val, int i)
        {
            mode = val;
            count = i;
        }
        public AnyType mode()
        {
            return mode;

        }
        public int count()
        {
            return count;
        }

    }

    /**
     * Removes an item from this collection.
     * @param x any object.
     * @return true if this item was removed from the collection.
     */
    public boolean remove( Object x )
    {
        int pos = findPos( x );

        if( pos == NOT_FOUND )
            return false;
        else
        {
            remove( pos );
            return true;
        }
    }

    /**
     * Removes an item from this collection.
     * @param idx the index of the object.
     * @return the item was removed from the collection.
     */
    public AnyType remove( int idx )
    {
        AnyType removedItem = theItems[ idx ];

        for( int i = idx; i < size( ) - 1; i++ )
            theItems[ i ] = theItems[ i + 1 ];
        theSize--;

        modCount++;
        return removedItem;
    }

    /**
     * Change the size of this collection to zero.
     */
    public void clear( )
    {
        theSize = 0;
        theItems = (AnyType []) new Comparable[ DEFAULT_CAPACITY ];
        modCount++;
    }

    public static<AnyType extends Comparable<? super AnyType>> int binSearch(A2232CKen list, AnyType value)
    {
        int beginning = 0;
        int end = list.size()-1;

        while(beginning<=end)
        {
            int mid = (beginning+end)/2;

            if(list.get(mid).compareTo(value)==0)
            {
                return (mid);
            }

            else if(list.get(mid).compareTo(value)<0)
            {
                beginning = mid +1;
            }

            else
            {
                end = mid-1;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Obtains an Iterator object used to traverse the collection.
     * @return an iterator positioned prior to the first element.
     */
    public Iterator<AnyType> iterator( )
    {
        return new ArrayListIterator( 0 );
    }
    /**
     * Obtains a ListIterator object used to traverse the collection bidirectionally.
     * @return an iterator positioned prior to the requested element.
     * @param idx the index to start the iterator. Use size() to do complete
     * reverse traversal. Use 0 to do complete forward traversal.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
     */
    public ListIterator<AnyType> listIterator( int idx )
    {
        return new ArrayListIterator( idx );
    }

    /**
     * This is the implementation of the ArrayListIterator.
     * It maintains a notion of a current position and of
     * course the implicit reference to the ArrayList.
     */
    private class ArrayListIterator implements ListIterator<AnyType>
    {
        private int current;
        private int expectedModCount = modCount;
        private boolean nextCompleted = false;
        private boolean prevCompleted = false;

        ArrayListIterator( int pos )
        {
            if( pos < 0 || pos > size( ) )
                throw new IndexOutOfBoundsException( );
            current = pos;
        }

        public boolean hasNext( )
        {
            if( expectedModCount != modCount )
                throw new ConcurrentModificationException( );
            return current < size( );
        }

        public boolean hasPrevious( )
        {
            if( expectedModCount != modCount )
                throw new ConcurrentModificationException( );
            return current > 0;
        }

        public AnyType next( )
        {
            if( !hasNext( ) )
                throw new NoSuchElementException( );
            nextCompleted = true;
            prevCompleted = false;
            return theItems[ current++ ];
        }

        public AnyType previous( )
        {
            if( !hasPrevious( ) )
                throw new NoSuchElementException( );
            prevCompleted = true;
            nextCompleted = false;
            return theItems[ --current ];
        }

        public void remove( )
        {
            if( expectedModCount != modCount )
                throw new ConcurrentModificationException( );

            if( nextCompleted )
                A2232CKen.this.remove( --current );
            else if( prevCompleted )
                A2232CKen.this.remove( current );
            else
                throw new IllegalStateException( );

            prevCompleted = nextCompleted = false;
            expectedModCount++;
        }
    }
}


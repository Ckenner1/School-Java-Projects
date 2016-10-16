package cis233.a1;

public class A1233CKenLL<AnyType extends Comparable<? super AnyType>>
    {
        private A1233CKenListNode<AnyType> header;
        /**
         * Construct the list
         */
        public A1233CKenLL( )
        {
            header = new A1233CKenListNode<AnyType>( null );
        }

        /**
         * Test if the list is logically empty.
         * @return true if empty, false otherwise.
         */
        public boolean isEmpty( )
        {
            return header.next == null;
        }

        /**
         * Make the list logically empty.
         */
        public void makeEmpty( )
        {
            header.next = null;
        }

        /**
         * Return an iterator representing the header node.
         */
        public A1233CKenLLIterator<AnyType> zeroth( )
        {
            return new A1233CKenLLIterator<AnyType>( header );
        }

        /**
         * Return an iterator representing the first node in the list.
         * This operation is valid for empty lists.
         */
        public A1233CKenLLIterator<AnyType> first( )
        {
            return new A1233CKenLLIterator<>( header.next );
        }

        /**
         * Insert after p.
         * @param x the item to insert.
         * @param p the position prior to the newly inserted item.
         */
        private void insert( AnyType x, A1233CKenLLIterator<AnyType> p )
        {
            if( p != null && p.current != null )
                p.current.next = new A1233CKenListNode<AnyType>( x, p.current.next );
        }

        public void add(AnyType x)
        {
            if(header.next == null)
            {
                A1233CKenLLIterator point = new A1233CKenLLIterator(header);
                insert(x,point);
            }
            else
            {
                A1233CKenLLIterator itr = zeroth();

                while (itr.current.next!= null && itr.current.next.element.compareTo(x) < 0)
                {
                    itr.advance();
                }
                insert(x,itr);
            }
        }

        public Boolean replace(AnyType x,AnyType y)
        {
            add(y);

            A1233CKenLLIterator value = find(x);

            remove(x);

            return value.isValid();
        }

        public void showList()
        {
            int count=0;
            if(isEmpty())
            {
                System.out.println("Empty List!");
            }
            A1233CKenLLIterator itr = zeroth();

            System.out.println("\nList Begin:");
            while(itr.current.next != null)
            {
                itr.advance();
                System.out.println(itr.retrieve());
                count++;
            }
            System.out.println("List End.");
            System.out.println("Number of Values Currently in List: " + count + "\n");
        }

        public void showList(int x)
        {
            int count=0;
            if(isEmpty())
            {
                System.out.println("Empty List!");
            }
            A1233CKenLLIterator itr = zeroth();

            System.out.println("\nList Begin:");
            while(itr.current.next != null)
            {
                if(count!=0 &&count%x==0)
                {
                    System.out.println();
                }
                itr.advance();
                System.out.print(itr.retrieve()+ " ");
                count++;
            }
            System.out.println("\nList End.");
            System.out.println("Number of Values Currently in List: " + count + "\n");
        }


        /**
         * Return iterator corresponding to the first node containing an item.
         * @param x the item to search for.
         * @return an iterator; iterator is not valid if item is not found.
         */
        public A1233CKenLLIterator<AnyType> find(AnyType x )
        {
            A1233CKenListNode<AnyType> itr = header.next;

            while( itr != null && !itr.element.equals( x ) )
                itr = itr.next;

            return new A1233CKenLLIterator<AnyType>( itr );
        }

        /**
         * Return iterator prior to the first node containing an item.
         * @param x the item to search for.
         * @return appropriate iterator if the item is found. Otherwise, the
         * iterator corresponding to the last element in the list is returned.
         */
        public A1233CKenLLIterator<AnyType> findPrevious(AnyType x )
        {
            A1233CKenListNode<AnyType> itr = header;

            while( itr.next != null && !itr.next.element.equals( x ) )
                itr = itr.next;

            return new A1233CKenLLIterator<>( itr );
        }
        public Result<AnyType> getMode()
        {
            Comparable mode = null;
            Comparable compared_instance = header.next.element;
            int mode_counter = 0;
            int compared_instance_counter = 0;

            if(header.next==null)
            {
                throw(new NullPointerException("empty list"));
            }

            A1233CKenLLIterator itr = first();

            while(itr.current != null)
            {

             if(itr.retrieve().equals(compared_instance))
                {
                    compared_instance_counter++;
                }
             else
                {
                    if(compared_instance_counter>mode_counter)
                    {
                        mode=compared_instance;
                        mode_counter = compared_instance_counter;
                        compared_instance = itr.retrieve();
                        compared_instance_counter = 0;
                        compared_instance_counter++;
                    }
                    else
                    {
                        compared_instance = itr.retrieve();
                        compared_instance_counter = 0;
                        compared_instance_counter++;
                    }
                }
                if(compared_instance_counter>mode_counter)
                {
                    mode=compared_instance;
                    mode_counter=compared_instance_counter;
                }
                itr.advance();
            }
            return new ForResult(mode,mode_counter);
        }

        public class ForResult implements Result<AnyType>
        {
            private Comparable mode;
            private int count;

            public ForResult(Comparable val, int i)
            {
                mode = val;
                count = i;
            }
            public Comparable mode()
            {
                return mode;

            }
            public int count()
            {
                return count;
            }

        }

        /**
         * Remove the first occurrence of an item.
         * @param x the item to remove.
         */
        public void remove( AnyType x )
        {
            A1233CKenLLIterator<AnyType> p = findPrevious( x );

            if( p.current.next != null )
                p.current.next = p.current.next.next;  // Bypass deleted node
        }

        // Simple print method
        public static <AnyType extends Comparable<?super AnyType>> void printList( A1233CKenLL<AnyType> theList )
        {
            if( theList.isEmpty( ) )
                System.out.print( "Empty list" );
            else
            {
                A1233CKenLLIterator<AnyType> itr = theList.first( );
                for( ; itr.isValid( ); itr.advance( ) )
                    System.out.print( itr.retrieve( ) + " " );
            }

            System.out.println( );
        }

        // In this routine, LinkedList and LinkedListIterator are the
        // classes written in Section 17.2.
        public static <AnyType extends Comparable<? super AnyType>> int listSize( A1233CKenLL<AnyType> theList )
        {
            A1233CKenLLIterator<AnyType> itr;
            int size = 0;

            for( itr = theList.first(); itr.isValid(); itr.advance() )
                size++;

            return size;
        }

        /*public static void main( String [ ] args )
        {
            A1233CKenLL<Integer> theList = new A1233CKenLL<Integer>( );
            A1233CKenLLIterator<Integer> theItr;
            int i;
            theItr = theList.zeroth( );
            printList( theList );
            theList.add(15);
            theList.add(200);

            for( i = 0; i < 10; i++ )
            {
                theList.add(i);
                printList( theList );
                theItr.advance( );
            }
            theList.add(15);
            theList.add(17);
            theList.replace(15,200);
            theList.add(17);
            theList.add(200);
            theList.add(200);
            theList.showList(5);
            System.out.println( "Size was: " + listSize( theList ) );

            Result temp = theList.getMode();
            System.out.println("the Mode is: " + temp.mode() + " And the # of occurrences: " + temp.count());

            for( i = 0; i < 10; i += 2 )
                theList.remove( i );

            for( i = 0; i < 10; i++ )
                if( ( i % 2 == 0 ) == ( theList.find( i ).isValid( ) ) )
                    System.out.println( "Find fails!" );

            System.out.println( "Finished deletions" );
            printList( theList );
        }*/

    }

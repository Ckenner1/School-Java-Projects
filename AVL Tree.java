/**
 * Created by Collin Kenner on 4/13/2016.
 */
package cis233.a2;
import weiss.nonstandard.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;


public class A2233CKenAVL <AnyType extends Comparable<? super AnyType>>
{
    /** The tree root.
     *  number of occurrences of the mode.
     *  mode of the tree.
     *  */
    private A2233CKenAvlNode<AnyType> root;
    private int num_greatest_occurrence;
    private AnyType greatest_instance;
    private int tree_size;

    /**
     * Construct the tree.
     */
    public A2233CKenAVL( )
    {
        root = null;
        tree_size=0;
        num_greatest_occurrence=0;
        greatest_instance=null;
    }

    /**
     * prints name of the author.
     */
    public String author()
    {
        return("Collin James Kenner");
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
    **/
    public void insert( AnyType x )
    {
        root = insert( x, root );
        tree_size++;
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    public void remove( AnyType x )
    {
        root = remove( x, root );
    }


    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private A2233CKenAvlNode<AnyType> remove( AnyType x, A2233CKenAvlNode<AnyType> t )
    {
        if( t == null )
            return t;   // Item not found; do nothing

        int compareResult = x.compareTo( t.element.getFront() );

        if( compareResult < 0 )
            t.left = remove( x, t.left );
        else if( compareResult > 0 )
            t.right = remove( x, t.right );
        else
        {
            if(t.num_instances>1)
            {
                t.element.dequeue();
                t.num_instances--;
            }
            else
            {
                t.visibility = false;
                t.num_instances = 0;
            }

            tree_size--;
        }
        return balance( t );
    }

    /**
     * finds mode of the tree
     * @return new Result class
     */
    public Result findMode()
    {
        findMode(root);
        return (new A2233CKenForResult<AnyType>(greatest_instance, num_greatest_occurrence));
    }

    /**
     * internal method to find mode of the tree.
     * private instance variables: greatest_instance, and greatest_occurrence
     * are used to
     * @param t the current node.
     */
    private void findMode( A2233CKenAvlNode<AnyType> t)
    {

        if(t!=null)
        {
            findMode(t.left);
            if( t.num_instances> num_greatest_occurrence)
            {
                greatest_instance = t.element.getFront();
                num_greatest_occurrence = t.num_instances;
            }
            findMode(t.right);

        }
    }
    private static class A2233CKenForResult<AnyType extends Comparable<? super AnyType>>
            implements Result<AnyType>
    {
        AnyType mode = null;
        int mode_count = 0;

        public A2233CKenForResult(AnyType t,int count)
        {
            mode = t;
            mode_count = count;
        }
        public AnyType mode()
        {
            return mode;
        }

        public int count()
        {
            return mode_count;
        }
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public AnyType findMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException("Tree is currently empty." );
        return findMin( root ).element.getFront();
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public AnyType findMax( )
    {
        if( isEmpty( ) )
            throw new UnderflowException("Tree is currently empty.");
        return findMax( root ).element.getFront();
    }

    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return true if x is found.
     */
    public boolean contains( AnyType x )
    {
        return contains( x, root );
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( )
    {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree( )
    {
        if( isEmpty( )|| tree_size==0 )
            System.out.println( "Empty tree" );
        else
            printTree( root );
    }

    public void writeTree(boolean ascending)
    {

        if(isEmpty( ) )
            System.out.println("Tree is currently empty");
        else
        {
            PrintWriter fileout = null;
            try
            {
                fileout = new PrintWriter(new FileWriter("A2233CKenAVLout.txt"));
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            writeBalTree(root,fileout,ascending);
            fileout.close();
        }
    }

    public void printBalTree(boolean ascending)
    {
        if(isEmpty( ) )
            System.out.println("Tree is currently empty");
        else
            printBalTree(root,ascending);
    }

    private static final int ALLOWED_IMBALANCE = 1;

    // Assume t is either balanced or within one of being balanced
    private A2233CKenAvlNode<AnyType> balance( A2233CKenAvlNode<AnyType> t )
    {
        if( t == null )
            return t;

        if( height( t.left ) - height( t.right ) > ALLOWED_IMBALANCE )
            if( height( t.left.left ) >= height( t.left.right ) )
                t = rotateWithLeftChild( t );
            else
                t = doubleWithLeftChild( t );
        else
        if( height( t.right ) - height( t.left ) > ALLOWED_IMBALANCE )
            if( height( t.right.right ) >= height( t.right.left ) )
                t = rotateWithRightChild( t );
            else
                t = doubleWithRightChild( t );

        t.height = Math.max( height( t.left ), height( t.right ) ) + 1;
        return t;
    }

    public void checkBalance( )
    {
        checkBalance( root );
    }

    private int checkBalance( A2233CKenAvlNode<AnyType> t )
    {
        if( t == null )
            return -1;

        if( t != null )
        {
            int hl = checkBalance( t.left );
            int hr = checkBalance( t.right );
            if( Math.abs( height( t.left ) - height( t.right ) ) > 1 ||
                    height( t.left ) != hl || height( t.right ) != hr )
                System.out.println( "OOPS!!" );
        }

        return height( t );
    }


    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private A2233CKenAvlNode<AnyType> insert( AnyType x, A2233CKenAvlNode<AnyType> t )
    {
        if( t == null )
            return new A2233CKenAvlNode<>( x, null, null );

        int compareResult = x.compareTo( t.element.getFront() );

        if( compareResult < 0)
            t.left = insert( x, t.left );
        else if( compareResult > 0 )
            t.right = insert( x, t.right );
        else
        {
            if(t.visibility==false)
            {
                t.visibility = true;
            }
            t.element.enqueue(x);
            t.num_instances++;
        }
        return balance( t );
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the smallest item.
     */
    private A2233CKenAvlNode<AnyType> findMin( A2233CKenAvlNode<AnyType> t )
    {
        if( t == null )
            return t;

        while( t.left != null )
            t = t.left;
        return t;
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the largest item.
     */
    private A2233CKenAvlNode<AnyType> findMax( A2233CKenAvlNode<AnyType> t )
    {
        if( t == null )
            return t;

        while( t.right != null )
            t = t.right;
        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the tree.
     * @return true if x is found in subtree.
     */
    private boolean contains( AnyType x, A2233CKenAvlNode<AnyType> t )
    {
        while( t != null )
        {
            int compareResult = x.compareTo( t.element.getFront() );

            if( compareResult < 0 )
                t = t.left;
            else if( compareResult > 0 )
                t = t.right;
            else
                return true;    // Match
        }
        return false;   // No match
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param t the node that roots the tree.
     */
    private void printTree( A2233CKenAvlNode<AnyType> t )
    {
        if( t != null )
        {
            printTree( t.left );
            if(t.visibility!=false)
            {
                for(int i=0;i<t.num_instances;i++)
                {
                    AnyType toprint = t.element.dequeue();
                    System.out.println(toprint);
                    t.element.enqueue(toprint);
                }
            }
            printTree( t.right );
        }
    }

    /**
     * prints balanced tree in order along with heights, balance, and child nodes.
     * @param t current AVL node.
     * @param ascending boolean variable to determine print direction.
     */
    private void printBalTree( A2233CKenAvlNode<AnyType> t,boolean ascending)
    {
        AnyType data;
        int balance_t = 0;
            if (t != null)
            {
                if (ascending == true)
                {
                    printBalTree(t.left, ascending);

                    if ((height(t.left) > height(t.right)))
                        balance_t = -1;
                    else if ((height(t.left) < height(t.right)))
                        balance_t = 1;
                    else
                        balance_t = 0;
                    for (int i = 0; i < t.num_instances; i++)
                    {
                        data = t.element.dequeue();
                        System.out.print("Data: " + data);
                        System.out.print("     Height: " + t.height);
                        System.out.println("    Balance: " + balance_t);
                        if (t.left != null)
                            System.out.println("Left: " + t.left.element.getFront());
                        else
                            System.out.println("Left: " + "null");
                        if (t.right != null)
                            System.out.println("Right: " + t.right.element.getFront());
                        else
                            System.out.println("Right: " + "null");

                        System.out.println();
                        t.element.enqueue(data);
                    }
                    printBalTree(t.right, ascending);
                }
                else
                {
                    printBalTree( t.right,ascending );
                    {
                        if(( height( t.left ) > height( t.right ) ))
                            balance_t = -1;
                        else if(( height( t.left ) < height( t.right ) ))
                            balance_t = 1;
                        else
                            balance_t = 0;
                        for(int i =0;i< t.num_instances; i++)
                        {
                            data = t.element.dequeue();
                            System.out.print("Data: " + data);
                            System.out.print("     Height: " + t.height );
                            System.out.println("    Balance: " + balance_t);
                            if (t.left != null)
                                System.out.println("Left: " + t.left.element.getFront());
                            else
                                System.out.println("Left: " + "null");
                            if (t.right != null)
                                System.out.println("Right: " + t.right.element.getFront());
                            else
                                System.out.println("Right: " + "null");

                            System.out.println();
                            t.element.enqueue(data);
                        }
                    }
                    printBalTree( t.left,ascending );
                }
            }
    }

    /**
     *
     * @param t current AVL node.
     * @param file the file that is being printed too.
     * @param ascending boolean to determine whether the tree is in  order.
     */
    private void writeBalTree( A2233CKenAvlNode<AnyType> t,PrintWriter file,boolean ascending)
    {
        AnyType data;
        int balance_t = 0;

        if( t != null )
        {
            if(ascending == true)
            {
                writeBalTree(t.left, file, ascending);
                {
                    if ((height(t.left) > height(t.right)))
                        balance_t = -1;
                    else if ((height(t.left) < height(t.right)))
                        balance_t = 1;
                    else
                        balance_t = 0;
                    for (int i = 0; i < t.num_instances; i++)
                    {
                        data = t.element.dequeue();
                        file.print("Data: " + data);
                        file.print("     Height: " + t.height);
                        file.println("    Balance: " + balance_t);

                        if (t.left != null)
                            file.println("Left: " + t.left.element.getFront());
                        else
                            file.println("Left: " + "null");
                        if (t.right != null)
                            file.println("Right: " + t.right.element.getFront());
                        else
                            file.println("Right: " + "null");

                        file.println();
                        t.element.enqueue(data);
                    }
                }
                writeBalTree(t.right, file, ascending);
            }
            else
            {
                writeBalTree( t.right,file,ascending);
                {
                    if(( height( t.left ) > height( t.right ) ))
                        balance_t = -1;
                    else if(( height( t.left ) < height( t.right ) ))
                        balance_t = 1;
                    else
                        balance_t = 0;
                    for(int i =0;i< t.num_instances; i++)
                    {
                        data = t.element.dequeue();
                        file.print("Data: " + data);
                        file.print("     Height: " + t.height);
                        file.println("    Balance: " + balance_t);
                        if (t.left != null)
                            file.println("Left: " + t.left.element.getFront());
                        else
                            file.println("Left: " + "null");
                        if (t.right != null)
                            file.println("Right: " + t.right.element.getFront());
                        else
                            file.println("Right: " + "null");
                        file.println();
                        t.element.enqueue(data);
                    }
                }
                writeBalTree( t.left,file,ascending);
            }
        }
    }

    /**
     * Return the height of node t, or -1, if null.
     */
    private int height( A2233CKenAvlNode<AnyType> t )
    {
        return t == null ? -1 : t.height;
    }

    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     */
    private A2233CKenAvlNode<AnyType> rotateWithLeftChild( A2233CKenAvlNode<AnyType> k2 )
    {
        A2233CKenAvlNode<AnyType> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max( height( k2.left ), height( k2.right ) ) + 1;
        k1.height = Math.max( height( k1.left ), k2.height ) + 1;
        return k1;
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     */
    private A2233CKenAvlNode<AnyType> rotateWithRightChild( A2233CKenAvlNode<AnyType> k1 )
    {
        A2233CKenAvlNode<AnyType> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max( height( k1.left ), height( k1.right ) ) + 1;
        k2.height = Math.max( height( k2.right ), k1.height ) + 1;
        return k2;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     */
    private A2233CKenAvlNode<AnyType> doubleWithLeftChild( A2233CKenAvlNode<AnyType> k3 )
    {
        k3.left = rotateWithRightChild( k3.left );
        return rotateWithLeftChild( k3 );
    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     */
    private A2233CKenAvlNode<AnyType> doubleWithRightChild( A2233CKenAvlNode<AnyType> k1 )
    {
        k1.right = rotateWithLeftChild( k1.right );
        return rotateWithRightChild( k1 );
    }

    private static class A2233CKenAvlNode<AnyType extends Comparable<?super AnyType>>
    {
        // Constructors
        A2233CKenAvlNode( AnyType theElement )
        {
            this( theElement, null, null );
        }

        A2233CKenAvlNode( AnyType theElement, A2233CKenAvlNode<AnyType> lt, A2233CKenAvlNode<AnyType> rt )
        {
            left     = lt;
            right    = rt;
            height   = 0;
            visibility = true;
            element = new ListQueue<AnyType>();
            element.enqueue(theElement);
            num_instances=1;
        }


        A2233CKenAvlNode<AnyType>  left;         // Left child
        A2233CKenAvlNode<AnyType>  right;        // Right child
        int               height;       // Height
        boolean           visibility;//
        ListQueue<AnyType> element;
        int               num_instances;


    }
}

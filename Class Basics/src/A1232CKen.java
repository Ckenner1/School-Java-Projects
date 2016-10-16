//Collin Kenner
//CIS 232 Assignment 1
//Prof. Randy Scovil

import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;

public class A1232CKen
{
    public static void main(String [] args)
    {
        ArrayList<Shape> shapelist = new ArrayList<>();
        int i = 0;

        System.out.println("Creating and Comparing Shape Areas");

        while (i != 10)
        {
            displayMenu();

            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();

            //checks for user input, and runs menu choice based upon user user inputted choice.
            switch (choice)
            {
                case 1:
                {
                    System.out.print("\nEnter radius: ");
                    int radius=getInput();
                    if(radius!=0) {
                        shapelist.add(new Circle(radius));
                        break;
                    }
                    break;
                }

                case 2:
                {
                    System.out.print("\nEnter length: ");
                    int len = getInput();
                    System.out.print("\nEnter width: ");
                    int wid = getInput();
                    if(len!=0 && wid!=0) {
                        shapelist.add(new Rectangle(len, wid));
                        break;
                    }
                    break;
                }
                case 3:
                {
                    System.out.print("\nEnter side length: ");
                    int len=getInput();
                    if(len!=0) {
                        shapelist.add(new A1232CKenSquare(len));
                        break;
                    }
                    break;
                }
                case 4:
                {
                    System.out.print("\nEnter side length: ");
                    int len=getInput();
                    if(len!=0) {
                        shapelist.add(new A1232CKenRegularHexagon(len));
                        break;
                    }
                    break;
                }
                case 5:
                {
                    System.out.print("\nEnter side length: ");
                    int len = getInput();
                    if(len!=0) {
                        shapelist.add(new A1232CKenRegularOctagon(len));
                        break;
                    }
                    break;
                }
                case 6:
                {
                    sortAscending(shapelist);
                    displayCollection(shapelist);
                    break;
                }
                case 7:
                {
                    sortDescending(shapelist);
                    displayCollection(shapelist);
                    break;
                }
                case 8:
                {
                    sortAscending(shapelist);
                    sortByType(shapelist);
                    displayCollection(shapelist);
                    break;
                }
                case 9:
                {
                    sortDescending(shapelist);
                    sortByType(shapelist);
                    displayCollection(shapelist);
                    break;
                }
                case 10:
                {
                    i = 10;
                    System.out.println("\nQuitting the program....");
                    break;
                }
                default:
                {
                    System.out.println("\nInvalid input please select a number between 1 and 10:");
                    break;
                }

            }
        }
    }
    //takes in sorted array and organizes by classtype, arrays must be sorted first
    public static <AnyType> void sortByType(ArrayList<AnyType> sAL)
    {
        for (int i = 1; i < sAL.size(); i++)
        {
            AnyType temp = sAL.get(i);
            int j;
            for (j = i - 1; j >= 0 && !(temp.getClass()==(sAL.get(j).getClass())); j--)
            {
                sAL.set(j + 1, sAL.get(j));
            }
            sAL.set(j + 1, temp);
        }
    }
    //sorts <AnyType> ArrayList into ascending order based upon comparator of objects.
    public static <AnyType extends Comparable<? super AnyType>> void sortAscending(ArrayList<AnyType> sAL) {
        int index;
        for (int i = 0; i < sAL.size() - 1; i++)
        {
            index = i;
            for (int j = i + 1; j < sAL.size(); j++)
            {
                if (sAL.get(j).compareTo(sAL.get(index)) == -1)
                {
                    index = j;
                }
            }
            AnyType temp = sAL.get(index);
            sAL.set(index, sAL.get(i));
            sAL.set(i, temp);

        }
    }
    //sorts array in descending order based upon the comparator of the objects.
    public static <AnyType extends Comparable<? super AnyType>> void sortDescending(ArrayList<AnyType> sAL) {

        int index;

        for (int i = 0; i < sAL.size() - 1; i++)
        {
            index = i;
            for (int j = i + 1; j < sAL.size(); j++)
            {
                if (sAL.get(j).compareTo(sAL.get(index)) == 1)
                {
                    index = j;
                }
            }
            AnyType temp = sAL.get(index);
            sAL.set(index, sAL.get(i));
            sAL.set(i, temp);

        }
    }
    //gets shape parameters, and checks for negative values
    public static int getInput()
    {
        try {
            Scanner in = new Scanner(System.in);

            int shape_parameter = in.nextInt();

            if (shape_parameter < 0)
                 throw new NegativeInputException();

            return shape_parameter;

        }
        catch (InputMismatchException e)
        {
            System.out.println("\nInput must be a positive integer.");
            return 0;
        }
        catch (NegativeInputException e)
        {
            System.out.println("\nInput must be a positive integer.");
            return 0;
        }
    }

    //prints collection in a column
    public static <AnyType> void displayCollection(ArrayList<AnyType> sAL)
    {
        if (sAL.size()==0)
        {
            System.out.println("\nNo shapes have been created, please create a shape.");
        }
        else
        {
            System.out.println("\nShape(s) are as follows: ");

            Iterator itr = sAL.iterator();

            while (itr.hasNext())
            {
                Object print_item = itr.next();
                System.out.println(print_item);
            }

            System.out.println();
        }

    }

    public static void displayMenu()
    {
        System.out.println("\nMenu:");
        System.out.println("1: Create a Circle");
        System.out.println("2: Create a Rectangle");
        System.out.println("3: Create a Square");
        System.out.println("4: Create a Regular Hexagon");
        System.out.println("5: Create a Regular Octagon");
        System.out.println("6: Display Shapes in Ascending order by area");
        System.out.println("7: Display Shapes in Descending order by area");
        System.out.println("8: Display Shapes by group in Ascending order by area");
        System.out.println("9: Display Shapes by group in Descending order by area");
        System.out.println("10: Quit");
        System.out.print("\nPlease Select an option between 1-10: ");
    }
}

class NegativeInputException extends Exception
{
    public NegativeInputException()
    {

    }
}

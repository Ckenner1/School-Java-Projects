import java.lang.Math;

public class A1232CKenRegularHexagon extends Shape
{
    private double length;

    public A1232CKenRegularHexagon()
    {
        length = 0.0;
    }

    public A1232CKenRegularHexagon(int len)
    {
        length = len;
    }

    public double area( )
    {
        return 3*Math.sqrt(3)*Math.pow(length,2)/2;
    }

    public double perimeter( )
    {
        return 6*length;
    }

    public String toString()
    {
        return "Hexagon of Side Length: " + length + " and Area: "+ area();
    }

    public boolean equals(Object shape)
    {
        if(!(shape instanceof A1232CKenRegularHexagon))
        {
            return false;
        }

        else if(this == shape)
        {
            return true;
        }
        else
            return getClass() == shape.getClass();
    }
}
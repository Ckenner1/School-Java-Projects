import java.lang.Math;
public class A1232CKenRegularOctagon extends Shape
{
    private double length;

    public A1232CKenRegularOctagon()
    {
        length = 0.0;
    }

    public A1232CKenRegularOctagon(int len)
    {
        length = len;
    }

    public double area( )
    {
        return 2*(1+Math.sqrt(2))*Math.pow(length,2);
    }

    public double perimeter( )
    {
        return 8*length;
    }

    public String toString()
    {
        return "Octagon of Side Length: " + length + " and Area: " +area();
    }

    public boolean equals(Object shape)
    {
        if(!(shape instanceof A1232CKenRegularOctagon))
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
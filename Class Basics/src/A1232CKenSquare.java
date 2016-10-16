public class A1232CKenSquare extends Shape
{
    private double length;

    public A1232CKenSquare(double len)
    {
        length = len;
    }

    public A1232CKenSquare()
    {
        length = 0.0;
    }

    public double area( )
    {
        return length * length;
    }

    public double perimeter( )
    {
        return 2 * ( length + length );
    }

    public String toString( )
    {
        return "Square side length: " + length + " and Area: " + area();
    }

    public boolean equals(Object shape)
    {
        if(!(shape instanceof A1232CKenSquare))
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

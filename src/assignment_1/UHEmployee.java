package assignment_1;

public class UHEmployee implements Employee, Human
{
    public String name;
    public int id;
    private boolean isActive;

    @Override
    public void hire()
    {
        isActive = true;
    }

    @Override
    public void fire()
    {
        isActive = false;
    }

    @Override
    public void die() {isActive = false;

    }

    @Override
    public String toString()
    {
        return name;
    }


}

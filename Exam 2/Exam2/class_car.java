package Exam2;

public class class_car implements iVehicle {

    public String name;

    @Override
    public String drive(int speed) {
        String label = "The car '" + name + "' is driving at speed: " + speed;
        return label;
    }

    @Override
    public String toString()
    {
        return name;
    }
}

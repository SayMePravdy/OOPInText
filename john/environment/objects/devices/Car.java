package john.environment.objects.devices;

import john.environment.Environment;
import john.environment.objects.EnvironmentObject;
import john.environment.objects.Storage;
import john.exceptions.InvalidAction;
import john.exceptions.InvalidArgument;
import john.exceptions.WrongEnvironmentException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Car extends EnvironmentObject implements Storage<EnvironmentObject> {

    private List<EnvironmentObject> objects = new ArrayList<>();
    private final String name;
    private String color;

    public static class Builder{
        private Environment environment;
        private String name;

        private String color = "default color";
        private List<EnvironmentObject> objects = new ArrayList<>();

        public Builder(Environment environment, String name){
            if (name == null) {
                throw new InvalidArgument("Error trying to add null name to object");
            }
            this.environment = environment;
            this.name = name;
        }

        public Builder color(String color) {
            this.color = color;
            return this;
        }

        public Builder object(EnvironmentObject obj) {
            objects.add(obj);
            return this;
        }

        public Builder objects(List<EnvironmentObject> objects) {
            this.objects = objects;
            return this;
        }

        public Car build() {
            return new Car(this);
        }


    }

    private Car(Builder builder){
        super(builder.environment);
        name = builder.name;
        color = builder.color;
        objects = builder.objects;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<EnvironmentObject> getObjects() {
        return objects;
    }

    public String getName() {
        return name;
    }

    @Override
    public void addObject(EnvironmentObject obj) throws WrongEnvironmentException {
        if (environment != obj.getEnvironment()) {
            throw new WrongEnvironmentException(String.format("Error while adding object to %s." +
                    "Trying to add object from different environment", name));
        }
        if (obj.getCar() != null) {
            obj.getCar().removeObject(obj);
        }
        obj.setCar(this);
        objects.add(obj);
    }

    @Override
    public void removeObject(EnvironmentObject obj) {
        objects.remove(obj);
    }

    @Override
    public int getDefenseLevel() {
        return 2;
    }

    @Override
    public void setStrength(double strength) {
        super.setStrength(strength);
        if (strength == 0){
            for (EnvironmentObject obj: objects){
                obj.setStrength(strength);
            }
        }

    }

    @Override
    public void moveTo(Environment environment) throws InvalidAction {
        if (environment == null) {
            throw new InvalidArgument("Error trying move People to null environment");
        }
        if (flying){
            throw new InvalidAction("Object is flying");
        }

        for (EnvironmentObject obj : objects){
            obj.moveTo(environment);
        }

        super.moveTo(environment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(objects, car.objects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objects);
    }

    @Override
    public String toString() {
        return "Car{" +
                "objects=" + objects +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}

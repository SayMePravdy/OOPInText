package john.environment.objects.people;

import john.environment.Environment;
import john.environment.objects.*;
import john.environment.objects.devices.Car;
import john.environment.objects.gun.Gun;
import john.exceptions.InvalidAction;
import john.exceptions.InvalidArgument;
import john.exceptions.WrongEnvironmentException;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class People extends EnvironmentObject implements Storage<Storable> {
    final protected String name;
    protected List<Storable> objects = new ArrayList<>();
    protected double leftArm;
    protected double rightArm;
    protected double leftLeg;
    protected double rightLeg;
    protected double body;
    protected double head;

    public People(String name, Environment environment, boolean antilunitePresent) {
        super(environment);
        this.name = name;
        this.antilunitePresent = antilunitePresent;
        ObjType = EnvironmentObjectType.HUMAN;
        leftArm = 0.25d;
        rightArm = leftArm;
        leftLeg = 0.35d;
        rightLeg = leftLeg;
        body = 0.75d;
        head = 1.0d;
    }

    public People(String name, Environment environment, double strength, boolean antilunitePresent) {
        super(environment, strength);
        if (name == null) {
            throw new InvalidArgument("Error trying to add null name to object");
        }
        this.name = name;
        this.antilunitePresent = antilunitePresent;
        leftArm = strength / 4;
        rightArm = leftArm;
        leftLeg = strength / 3;
        rightLeg = leftLeg;
        body = 3 * strength / 4;
        head = strength;
    }

    public void setAntilunitePresent(boolean antilunitePresent) {
        this.antilunitePresent = antilunitePresent;
    }

    public void heal(People p, int health) throws InvalidAction {
        if (flying){
            throw new InvalidAction("Object is flying");
        }
        p.setStrength(p.getStrength() + health);
    }

    @Override
    public void setStrength(double strength) {
        this.strength = strength;
        if (strength <= 0) {
            this.environment.removeObject(this);
            for (Storable obj : objects) {
                obj.setOwner(null);
            }
            if (car != null) {
                this.car.removeObject(this);
            }
        }
    }

    public double getStrength() {
        return strength;
    }

    public void destroy(Destroyable d) throws InvalidAction {
        if (flying){
            throw new InvalidAction("Object is flying");
        }
        for (Storable obj : objects) {
            if (obj.getObjType() == EnvironmentObjectType.GUN) {
                d.setStrength(((Gun) obj).shoot(d));
                if (d.getStrength() == 0) {
                    break;
                }
            }
        }

    }

    public double getLeftArm() {
        return leftArm;
    }

    public double getRightArm() {
        return rightArm;
    }

    public double getLeftLeg() {
        return leftLeg;
    }

    public double getRightLeg() {
        return rightLeg;
    }

    public double getBody() {
        return body;
    }

    public double getHead() {
        return head;
    }

    public void setObjects(List<Storable> objects) {
        this.objects = objects;
    }

    public void setLeftArm(double leftArm) {
        this.leftArm = leftArm;
    }

    public void setRightArm(double rightArm) {
        this.rightArm = rightArm;
    }

    public void setLeftLeg(double leftLeg) {
        this.leftLeg = leftLeg;
    }

    public void setRightLeg(double rightLeg) {
        this.rightLeg = rightLeg;
    }

    public void setBody(double body) {
        this.body = body;
    }

    public void setHead(double head) {
        this.head = head;
    }

    public List<Storable> getObjects() {
        return objects;
    }

    @Override
    public void setCar(Car car) {
        for (Storable obj : objects) {
            obj.setCar(car);
        }
        super.setCar(car);
    }


    @Override
    public void moveTo(Environment environment) throws InvalidAction {
        if (environment == null) {
            throw new InvalidArgument("Error trying move People to null environment");
        }
        if (flying){
            throw new InvalidAction("Object is flying");
        }
        for (Storable obj : objects) {
            obj.moveTo(environment);
        }
        super.moveTo(environment);
    }

    @Override
    public void addObject(Storable obj) throws WrongEnvironmentException {
        if (environment != obj.getEnvironment()) {
            throw new WrongEnvironmentException(String.format("Error while adding object to %s." +
                    "Trying to add object from different environment", name));
        }
        if (obj.getOwner() != null) {
            obj.getOwner().removeObject(obj);
        }
        obj.setOwner(this);
        obj.setCar(car);
        objects.add(obj);

    }

    @Override
    public void removeObject(Storable obj) {
        objects.remove(obj);
    }


    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        People people = (People) o;
        return name.equals(people.name) &&
                objects.equals(people.objects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, objects);
    }

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", objects=" + objects +
                ", health=" + strength +
                '}';
    }
}

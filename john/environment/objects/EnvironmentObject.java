package john.environment.objects;

import john.environment.Environment;
import john.environment.objects.devices.Car;
import john.exceptions.InvalidAction;
import john.exceptions.InvalidArgument;

public abstract class EnvironmentObject implements Destroyable{
    protected Environment environment;
    protected boolean flying = false;
    protected double strength = 1;
    protected EnvironmentObjectType ObjType;
    protected boolean antilunitePresent = false;
    protected Car car;

    public EnvironmentObject(Environment environment) {
        if (environment == null)
            throw new InvalidArgument("Error trying to add object to null environment");
        this.environment = environment;
//        environment.addObject(this);
    }

    public EnvironmentObject(Environment environment, double strength){
        this(environment);
        this.strength = strength;
    }

    @Override
    public int getDefenseLevel() {
        return 1;
    }

    @Override
    public double getStrength() {
        return strength;
    }

    @Override
    public void setStrength(double strength) {
        this.strength = strength;
        if (strength == 0){
            if (environment != null){
                environment.removeObject(this);
            }
            if (car != null) {
                car.removeObject(this);
            }
        }
    }

    public Environment getEnvironment() {
        return environment;
    }

    public EnvironmentObjectType getObjType() {
        return ObjType;
    }

    public void moveTo(Environment environment) throws InvalidAction {
        if (environment == null) {
            throw new InvalidArgument("Error trying to move object to null environment");
        }
        if (!flying) {
            this.environment.removeObject(this);
            this.environment = environment;
            environment.addObject(this);
        }
    }


    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    public boolean isFlying(){
        return flying;
    }

    public boolean isAntilunitePresent() {
        return antilunitePresent;
    }

}


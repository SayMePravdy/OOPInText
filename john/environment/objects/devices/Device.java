package john.environment.objects.devices;

import john.environment.Environment;
import john.environment.objects.EnvironmentObjectType;
import john.environment.objects.Storable;
import john.environment.objects.people.People;

import java.util.Objects;

public class Device extends Storable {
    protected final DeviceType type;

    public Device(DeviceType type, Environment environment) {
        super(environment);
        this.type = type;
        ObjType = EnvironmentObjectType.DEVICE;
    }

    public Device(DeviceType type, int strength, Environment environment) {
        super(environment, strength);
        this.type = type;
        ObjType = EnvironmentObjectType.DEVICE;
    }

    public void setStrength(double strength) {
        this.strength = strength;
        if (strength == 0){
            super.setStrength(strength);
            if (owner != null)
                owner.removeObject(this);
        }
    }

    public void setOwner(People owner) {
        this.owner = owner;
    }

    public DeviceType getType() {
        return type;
    }

    public double getStrength() {
        return strength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return strength == device.strength &&
                type == device.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, strength);
    }

    @Override
    public String toString() {
        return "Device{" +
                "type=" + type +
                ", strength=" + strength +
                '}';


    }

}

package john.environment;

import john.environment.objects.Destroyable;
import john.environment.objects.EnvironmentObject;
import john.environment.objects.Storage;
import john.environment.objects.devices.WeightlessnessDevice;
import john.exceptions.InvalidArgument;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Environment implements Storage<EnvironmentObject>, Destroyable {
    private final String name;
    private final int defenseLevel;
    private double strength = 1;
    private List<EnvironmentObject> objects = new ArrayList<>();
    private List<WeightlessnessDevice> listeners = new ArrayList<>();
    private boolean weightlessness = false;

    public Environment(String name, int defenseLevel) {
        if (name == null) {
            throw new InvalidArgument("Error trying to add null name to object");
        }
        if (defenseLevel < 1) {
            throw new InvalidArgument("Invalid defense");
        }
        this.name = name;
        this.defenseLevel = defenseLevel;
    }

    public Environment(String name, int defenseLevel, int strength) {
        this(name, defenseLevel);
        if (strength < 1) {
            throw new InvalidArgument("Invalid strength");
        }
        this.strength = strength;
    }

    @Override
    public int getDefenseLevel() {
        return defenseLevel;
    }

    @Override
    public void setStrength(double strength){
        this.strength = strength;
        if (strength == 0){
            for (EnvironmentObject obj : objects){
                obj.setStrength(strength);
            }
        }

    }

    @Override
    public double getStrength() {
        return strength;
    }

    public List<EnvironmentObject> getObjects() {
        return objects;
    }

    public List<WeightlessnessDevice> getListeners() {
        return listeners;
    }

    @Override
    public void addObject(EnvironmentObject obj) {
        objects.add(obj);
        if (obj instanceof WeightlessnessDevice) {
            listeners.add((WeightlessnessDevice) obj);
            if (!weightlessness && ((WeightlessnessDevice)obj).getDeviceState()) {
                setWeightlessness(true);
            }
        }
        for (WeightlessnessDevice w : listeners) {
            w.onEnter(obj);
        }
    }

    @Override
    public void removeObject(EnvironmentObject obj) {
        objects.remove(obj);

        for (WeightlessnessDevice w : listeners){
            w.onLeave(obj);
        }

        if (obj instanceof WeightlessnessDevice){
            listeners.remove(obj);
            if (((WeightlessnessDevice)obj).getDeviceState()) {
                boolean check = false;
                for (WeightlessnessDevice w : listeners){
                    if (w.getDeviceState()){
                        check = true;
                        break;
                    }
                }
                if (!check){
                    setWeightlessness(true);
                }
            }
        }
    }

    public void setWeightlessness(boolean weightlessness) {
        this.weightlessness = weightlessness;
        for (EnvironmentObject obj : objects) {
            if (!obj.isAntilunitePresent())
                obj.setFlying(weightlessness);
        }
    }

    public boolean isWeightlessness() {
        return weightlessness;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Environment that = (Environment) o;
        return weightlessness == that.weightlessness &&
                name.equals(that.name) &&
                objects.equals(that.objects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, objects, weightlessness);
    }

    @Override
    public String toString() {
        return "Environment{" +
                "name='" + name + '\'' +
                ", weightlessness=" + weightlessness +
                '}';
    }
}

package john.environment.objects.people;

import john.environment.Environment;
import john.environment.objects.Storable;
import john.environment.objects.devices.WeightlessnessDevice;

import java.util.Objects;

public class Stranger extends People {
    public Stranger(String name, Environment environment, int health) {
        super(name, environment, health,true);
    }

    public Stranger(String name, Environment environment) {
        super(name, environment,true);
    }

    public void turnOnWeightlessness(WeightlessnessDevice d){
        d.setWeightlessness(true);
    }

    public void turnOnWeightlessness(){
        for (Storable obj : objects){
            if (obj instanceof WeightlessnessDevice && !((WeightlessnessDevice) obj).getDeviceState()){
                ((WeightlessnessDevice) obj).setWeightlessness(true);
                break;
            }
        }
    }

    public void turnOffWeightlessness(WeightlessnessDevice d){
        d.setWeightlessness(false);
    }

    public void turnOffWeightlessness(){
        for (Storable obj : objects){
            if (obj instanceof WeightlessnessDevice && !((WeightlessnessDevice) obj).getDeviceState()){
                ((WeightlessnessDevice) obj).setWeightlessness(false);
            }
        }
    }


    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stranger stranger = (Stranger) o;
        return name.equals(stranger.name) &&
                objects.equals(stranger.objects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, objects);
    }

    @Override
    public String toString() {
        return "Stranger{" +
                "name='" + name + '\'' +
                ", objects=" + objects +
                ", health=" + strength +
                '}';
    }
}

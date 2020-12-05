package john.environment.objects.devices;

import john.environment.Environment;
import john.environment.objects.EnvironmentActionListener;
import john.environment.objects.EnvironmentObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WeightlessnessDevice extends Device implements EnvironmentActionListener {
    private List<EnvironmentObject> objects = new ArrayList<>();
    private boolean deviceState = false;

    public WeightlessnessDevice(int strength, Environment environment) {
        super(DeviceType.WEIGHTLESSNESS, strength, environment);
    }

    public WeightlessnessDevice(Environment environment) {
        super(DeviceType.WEIGHTLESSNESS, environment);
    }

    public void setWeightlessness(boolean weightlessness) {
        deviceState = weightlessness;
        this.environment.setWeightlessness(weightlessness);
    }

    public boolean getDeviceState() {
        return deviceState;
    }

    public void onLeave(EnvironmentObject obj) {
        objects.remove(obj);
    }

    public void onEnter(EnvironmentObject obj) {
        objects.add(obj);
        if (this.deviceState && !obj.isAntilunitePresent()) {
            obj.setFlying(true);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WeightlessnessDevice that = (WeightlessnessDevice) o;
        return deviceState == that.deviceState &&
                objects.equals(that.objects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), deviceState);
    }
}

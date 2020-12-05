package john.environment.objects;

import john.environment.Environment;
import john.environment.objects.people.People;

public abstract class Storable extends EnvironmentObject {
    protected People owner;

    public Storable(Environment environment){
        super(environment);
    }

    public Storable(Environment environment, int strength){
        super(environment, strength);

    }

    public People getOwner() {
        return owner;
    }

    public void setOwner(People owner) {
        this.owner = owner;
    }
}

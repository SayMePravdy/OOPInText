package john.environment.objects.people;

import john.environment.Environment;
import java.util.Objects;

public class Police extends People {
    public Police(String name, Environment environment) {
        super(name, environment, false);
    }

    public Police(String name, Environment environment, int health) {
        super(name, environment, health,false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Police police = (Police) o;
        return name.equals(police.name) &&
                objects.equals(police.objects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, objects);
    }

    @Override
    public String toString() {
        return "Police{" +
                "name='" + name + '\'' +
                ", environment=" + environment +
                ", flying=" + flying +
                ", ObjType=" + ObjType +
                '}';
    }
}

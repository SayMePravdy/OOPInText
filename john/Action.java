package john;

import john.environment.Environment;
import john.environment.objects.*;
import john.environment.objects.devices.Car;
import john.environment.objects.devices.Device;
import john.environment.objects.devices.DeviceType;
import john.environment.objects.devices.WeightlessnessDevice;
import john.environment.objects.gun.Gun;
import john.environment.objects.people.People;
import john.environment.objects.people.Police;
import john.environment.objects.people.Shorty;
import john.environment.objects.people.Stranger;
import john.exceptions.InvalidAction;
import john.exceptions.InvalidArgument;
import john.exceptions.WrongEnvironmentException;

public class Action {
    public static void main(String[] args) {
        Environment rocket = new Environment("Rocket", 2);
        Environment wood = new Environment("Wood", 2);
        Environment field = new Environment("Field", 2);
        Police p1 = new Police("John", wood);
        Police p2 = new Police("Bob", wood);
        Police p3 = new Police("Ivan", field);
        Stranger stranger = new Stranger("Rigl", rocket);

        rocket.addObject(stranger);
        wood.addObject(p1);
        wood.addObject(p2);
        field.addObject(p3);

        People znayka = new People("Znayka", rocket, true);
        People doctor = new People("Pilulkin", rocket, true);
        Shorty s1 = new Shorty("Fuksia", rocket);
        Shorty s2 = new Shorty("Seledochka", rocket);
        Shorty s3 = new Shorty("Klepka", rocket);

        rocket.addObject(znayka);
        rocket.addObject(doctor);
        rocket.addObject(s1);
        rocket.addObject(s2);
        rocket.addObject(s3);

        Gun g1 = new Gun(1, wood);
        Gun g2 = new Gun(6, wood);
        Gun g3 = new Gun(6, field);

        try {
            p1.addObject(g1);
            p2.addObject(g2);
            p3.addObject(g3);
        } catch (WrongEnvironmentException e) {
            System.out.println(e.getMessage());
        }

        try{
           p1.destroy(rocket);
        } catch (InvalidAction e){
            System.out.println(e.getMessage());
        }

        try {
            p1.destroy(s3);
        } catch (InvalidAction e) {
            System.out.println(e.getMessage());
        }


        try {
            doctor.heal(s3, 5);
        } catch (InvalidAction e) {
            System.out.println(e.getMessage());
        }


        Device anemometer = new Device(DeviceType.ANEMOMETER, rocket);
        Device seismograph = new Device(DeviceType.SEISMOGRAPH, rocket);
        WeightlessnessDevice weightlessness = new WeightlessnessDevice(rocket);
        Device raingauge = new Device(DeviceType.RAINGAUGE, rocket);
        Device barometer = new Device(DeviceType.BAROMETER, rocket);
        Device camera = new Device(DeviceType.TELECAMERA, field);

        rocket.addObject(anemometer);
        rocket.addObject(seismograph);
        rocket.addObject(weightlessness);
        rocket.addObject(barometer);
        rocket.addObject(raingauge);

        try {
            p1.moveTo(rocket);
            p2.moveTo(rocket);
            p3.moveTo(rocket);
        } catch (InvalidArgument | InvalidAction e) {
            System.out.println(e.getMessage());
        }

        try {
            stranger.addObject(weightlessness);
        } catch (WrongEnvironmentException e) {
            System.out.println(e.getMessage());
        }

        try {
            p1.destroy(barometer);
            p2.destroy(seismograph);
            p3.destroy(raingauge);
            stranger.turnOnWeightlessness(weightlessness);
            p1.destroy(anemometer);
        } catch (InvalidAction e) {
            System.out.println(e.getMessage());
        }

        People teleoperetor = new People("Richard", field, false);
        Car telCar = new Car.Builder(field, "Ford")
                .color("blue")
                .object(teleoperetor)
                .build();

        teleoperetor.setCar(telCar);
        try {
            teleoperetor.addObject(camera);
        } catch (WrongEnvironmentException e) {
            System.out.println(e.getMessage());
        }

        try {
            telCar.moveTo(rocket);
        } catch (InvalidArgument | InvalidAction e) {
            System.out.println(e.getMessage());
        }


        Car car1 = new Car.Builder(field, "BMW")
                .color("black")
                .build();

        for (int i = 0; i < 25; i++) {
            try {
                car1.addObject(new EnvironmentObject(field) {
                    private Gun gun = new Gun(2, field);

                    @Override
                    public double getStrength() {
                        return strength;
                    }

                    public void kill(People people) {
                        double health = people.getStrength();
                        for (Gun.Bullet bullet : gun.getBullets()) {
                            health -= bullet.getDamage();
                            gun.getBullets().remove(bullet);
                            if (health <= 0) {
                                people.setStrength(0);
                                break;
                            }
                        }
                    }

                    public Gun getGun() {
                        return gun;
                    }

                    public void destroyDevice(Device device) {
                        double health = device.getStrength();
                        for (Gun.Bullet bullet : gun.getBullets()) {
                            health -= bullet.getDamage();
                            gun.getBullets().remove(bullet);
                            if (health <= 0) {
                                device.setStrength(0);
                                break;
                            }
                        }
                    }

                });
            } catch (WrongEnvironmentException e) {
                System.out.println(e.getMessage());
            }

        }

        try {
            car1.moveTo(rocket);
        } catch (InvalidArgument | InvalidAction e) {
            System.out.println(e.getMessage());
        }

    }

}

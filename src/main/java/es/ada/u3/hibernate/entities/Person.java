package es.ada.u3.hibernate.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "person_ja24")
public class Person {
    @Id
    @Column(length = 50,nullable = false)
    private String driver_id;
    @Column(length = 50)
    private String address;
    @Column(length = 50)
    private String name;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private Set<Car> cars = new HashSet<>();

    public Person() {
    }

    public Person(String driver_id, String address, String name) {
        this.driver_id = driver_id;
        this.address = address;
        this.name = name;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    @Override
    public String toString() {
        StringBuilder a = new StringBuilder("Person{" +
                "driver_id='" + driver_id + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                "}, cars=:\n");
        for (Car car : cars) {
            a.append("Car Licence =" + car.getLicense_id()).append("\n");
        }
        return a.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return Objects.equals(driver_id, person.driver_id);
    }

    @Override
    public int hashCode() {
        return driver_id != null ? driver_id.hashCode() : 0;
    }
}

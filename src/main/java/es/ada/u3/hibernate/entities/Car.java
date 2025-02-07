package es.ada.u3.hibernate.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "car_ja24")
public class Car {
    @Id
    @Column(length = 50,nullable = false)
    private String license_id;
    @Column(length = 50)
    private String model;
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Person owner;

    @OneToOne(mappedBy = "car", cascade = CascadeType.REMOVE)
    private Policy policy;

   @ManyToMany
    @JoinTable(
        name = "participated_ja24", // Nombre de la tabla intermedia
        joinColumns = @JoinColumn(name = "license_id"),
        inverseJoinColumns = @JoinColumn(name = "report_number")
    )

    private Set<Accident> accidents = new HashSet<>();
    public Car() {
    }

    public Car(String license_id, String model, Integer year, Person owner) {
        this.license_id = license_id;
        this.model = model;
        this.year = year;
        this.owner = owner;
    }

    public Set<Accident> getAccidents() {
        return accidents;
    }

    public void setAccidents(Set<Accident> accidents) {
        this.accidents = accidents;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public String getLicense_id() {
        return license_id;
    }

    public void setLicense_id(String license_id) {
        this.license_id = license_id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        if(policy == null){
             return "Car{" +
                "license_id='" + license_id + '\'' +
                ", model='" + model + '\'' +
                ", year='" + year + '\'' +
                ", police id='no policy'" + '\'' +
                ", owner id='"+ owner.getDriver_id() + "'}";
        }
        return "Car{" +
                "license_id='" + license_id + '\'' +
                ", model='" + model + '\'' +
                ", year='" + year + '\'' +
                ", police id='" + policy.getPolicy_id() + '\'' +
                ", owner id='"+ owner.getDriver_id() + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        return Objects.equals(license_id, car.license_id);
    }

    @Override
    public int hashCode() {
        return license_id != null ? license_id.hashCode() : 0;
    }
}

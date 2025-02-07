package es.ada.u3.hibernate.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "accident_ja24")
public class Accident {
    @Id
    @Column(nullable = false,length = 50)
    private String report_number;

    @Column(length = 50)
    private String location;

    @ManyToMany
    @JoinTable(
        name = "participated_ja24",
        joinColumns = @JoinColumn(name = "report_number"),
        inverseJoinColumns = @JoinColumn(name = "license_id")
    )
    private Set<Car> carsInvolved = new HashSet<>();

    public Accident(String report_number, String location) {
        this.report_number = report_number;
        this.location = location;
    }

    public String getReport_number() {
        return report_number;
    }

    public void setReport_number(String report_number) {
        this.report_number = report_number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<Car> getCarsInvolved() {
        return carsInvolved;
    }

    public void setCarsInvolved(Set<Car> carsInvolved) {
        this.carsInvolved = carsInvolved;
    }

    @Override
    public String toString() {
        StringBuilder a = new StringBuilder("Accident{" +
                "report_number='" + report_number + '\'' +
                ", location='" + location + '\'' +
                ", carsInvolved=\n");
        for (Car car : carsInvolved) {
            a.append(car.getLicense_id()).append("\n");
        }
        a.append('}');
        return a.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Accident accident = (Accident) o;

        return Objects.equals(report_number, accident.report_number);
    }

    @Override
    public int hashCode() {
        return report_number != null ? report_number.hashCode() : 0;
    }
}

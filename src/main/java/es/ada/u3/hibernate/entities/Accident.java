package es.ada.u3.hibernate.entities;

import jakarta.persistence.*;

import java.util.HashSet;
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

}

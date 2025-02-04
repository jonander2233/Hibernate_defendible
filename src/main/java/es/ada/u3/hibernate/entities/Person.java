package es.ada.u3.hibernate.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(length = 50,nullable = false)
    private String driver_id;
    @Column(length = 50)
    private String address;
    @Column(length = 50)
    private String name;

    @OneToMany(mappedBy = "owner")
    private Set<Car> cars = new HashSet<>();
}

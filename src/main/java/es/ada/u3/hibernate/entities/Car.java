package es.ada.u3.hibernate.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "car_ja24")
class Car {
    @Id
    @Column(length = 50,nullable = false)
    private String license_id;
    @Column(length = 50)
    private String model;
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Person owner;

}

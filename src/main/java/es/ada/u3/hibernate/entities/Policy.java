package es.ada.u3.hibernate.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "policy_ja24")
public class Policy {
    @Id
    @Column(length = 50,nullable = false)
    private String policy_id;

    @OneToOne
    @JoinColumn(name = "license_id")
    private Car car;

}

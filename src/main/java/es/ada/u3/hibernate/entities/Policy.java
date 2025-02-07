package es.ada.u3.hibernate.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "policy_ja24")
public class Policy {
    @Id
    @Column(length = 50,nullable = false)
    private String policy_id;

    @OneToOne
    @JoinColumn(name = "license_id")
    private Car car;

    public Policy() {
    }

    public Policy(String policy_id, Car car) {
        this.policy_id = policy_id;
        this.car = car;
    }

    public String getPolicy_id() {
        return policy_id;
    }

    public void setPolicy_id(String policy_id) {
        this.policy_id = policy_id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Policy{" +
                "policy_id='" + policy_id + '\'' +
                ", car=" + car.getLicense_id() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Policy policy = (Policy) o;

        return Objects.equals(policy_id, policy.policy_id);
    }

    @Override
    public int hashCode() {
        return policy_id != null ? policy_id.hashCode() : 0;
    }
}

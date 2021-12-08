package org.ultims.playleagues.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

@Table(name = "leagues")
@Entity
public class League {

    @Id
    @Size(max = 36, min = 36, message = "League ID must be 36 characters long")
    @NotEmpty(message = "League ID cannot be null or empty")
    private String id;

    @Size(min = 3, max = 32, message = "League Name must be between 3 and 32 characters")
    @NotEmpty(message = "League Name cannot be null or empty")
    private String name;

    public League() {
    }

    public League(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof League league)) return false;
        return Objects.equals(getId(), league.getId()) && Objects.equals(getName(), league.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return "League{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

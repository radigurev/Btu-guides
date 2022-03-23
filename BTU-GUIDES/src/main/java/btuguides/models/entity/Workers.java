package btuguides.models.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "workers")
public class Workers extends BaseEntity{
    private String firstName;
    private String lastName;
    private String url;
    private Set<Locations> locations;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    @OneToMany
    public Set<Locations> getLocations() {
        return locations;
    }

    public void setLocations(Set<Locations> locations) {
        this.locations = locations;
    }
}

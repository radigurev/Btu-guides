package btuguides.models.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "locations")
public class Locations extends BaseEntity {
    private String location;
}

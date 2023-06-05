package pet.park.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Amenity {
<<<<<<< HEAD

=======
>>>>>>> 6a73c0167bb50553acd6e1086190e677709127ba
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long amenityId;

 private String amenity;

 @EqualsAndHashCode.Exclude
 @ToString.Exclude
 @ManyToMany(mappedBy = "amenities")
 private Set<PetPark> petParks = new HashSet<>();

}

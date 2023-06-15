package dog.rescue.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Breed {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long breedId;

 private String name;

 @EqualsAndHashCode.Exclude
 @ToString.Exclude
 @ManyToMany(mappedBy = "breeds")
 private Set<Dog> dogs = new HashSet<>();
}

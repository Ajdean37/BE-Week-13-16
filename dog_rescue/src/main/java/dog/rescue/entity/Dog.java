package dog.rescue.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Dog {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long dogId;

 @EqualsAndHashCode.Exclude
 private String name;

 @EqualsAndHashCode.Exclude
 private int age;

 @EqualsAndHashCode.Exclude
 private String color;

 @EqualsAndHashCode.Exclude
 @ToString.Exclude
 @ManyToOne
 @JoinColumn(name = "location_id", nullable = false)
 private Location location;

 @EqualsAndHashCode.Exclude
 @ToString.Exclude
 @ManyToMany(cascade = CascadeType.PERSIST)
 @JoinTable(
   name = "dog_breed",
   joinColumns = @JoinColumn(name = "dog_id"),
   inverseJoinColumns = @JoinColumn(name = "breed_id")
 )
 private Set<Breed> breeds = new HashSet<>();
}

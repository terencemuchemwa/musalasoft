/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musalasoft.drones.model;

/**
 *
 * @author user
 */
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author user
 */
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "drones")
//@JsonIgnoreProperties(value= {"hibernateLazyInitializer", "handler","medications"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Drone implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column //(length = 100)
    @Size(max = 100)
    private String serialnumber;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private Model model;

    @Max(500)
    @Column
    private double weightlimit;

    @Column
    private float batterycapacity;

//    
    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "drone", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Medication> medications = new ArrayList<>();
}

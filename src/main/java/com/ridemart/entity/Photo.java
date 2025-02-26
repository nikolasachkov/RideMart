package com.ridemart.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "advertisement_id", nullable = false, unique = true)
    private Advertisement advertisement;

    @Column(nullable = false)
    private String photoUrl;
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musalasoft.drones.repository;

import com.musalasoft.drones.model.Drone;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author user
 */
@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {
//    @PersistenceContext
//  private EntityManager entityManager;
}

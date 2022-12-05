/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musalasoft.drones.repository;

import com.musalasoft.drones.model.Medication;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author user
 */
@Repository
public interface MedicationRepository  extends JpaRepository<Medication,Long>{
//    List<Medication> findByfkdrone(Long fkdrone);
}

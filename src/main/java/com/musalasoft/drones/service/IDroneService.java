/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.musalasoft.drones.service;

import com.musalasoft.drones.model.Drone;
import com.musalasoft.drones.model.Medication;
import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author user
 */
public interface IDroneService {
    /**
     *
     * @param d
     * @return
     */
    ResponseEntity<Drone> registerDrone(Drone d);

    /**
     *
     * @param d
     * @param medication
     * @return
     */
    ResponseEntity<?> loadDrone(Long d, List<Medication> medication);

    /**
     *
     * @param d
     * @return
     */
    ResponseEntity<List<Medication>> listMedicationByDrone(Long d);

    /**
     *
     * @return
     */
    ResponseEntity<List<Drone>> available();

    /**
     *
     * @return
     * @message This method is for initializing drones
     */
    ResponseEntity<List<Drone>> initialize();

    /**
     *
     * @param id
     * @return
     */
    ResponseEntity<String> getBatteryLevelByDrone(Long id);

    public List<Drone> listAll();
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musalasoft.drones.rest;

import com.musalasoft.drones.model.Drone;
import com.musalasoft.drones.model.Medication;
import com.musalasoft.drones.repository.DroneRepository;
import com.musalasoft.drones.service.IDroneService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author user
 */
@RestController
@RequestMapping(value = "/drones", produces = "application/json")
public class RestDrone {

    @Autowired
    private IDroneService servicedrone;

    @Autowired
    private DroneRepository dronerepo;

    @GetMapping
    public String index() {
        return "testing";
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<Drone> Register(@RequestBody Drone d) {
        return servicedrone.registerDrone(d);
    }

    @PostMapping(value = "/load/{id}", consumes = "application/json")
    public ResponseEntity<?> Register(@PathVariable("id") Long id, @RequestBody List<Medication> ld) {
        System.err.println(ld);
        return servicedrone.loadDrone(id, ld);
    }

    @GetMapping("/{id}/batterylevel")
    public ResponseEntity<String> getLevel(@PathVariable("id") Long id) {
        return servicedrone.getBatteryLevelByDrone(id);
    }

    @GetMapping("/{id}/medications")
    public ResponseEntity<?> getMedications(@PathVariable("id") Long id) {
        return servicedrone.listMedicationByDrone(id);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Drone>> listAvailable() {
        return servicedrone.available();
    }

    @GetMapping("/initialize")
    public ResponseEntity<List<Drone>> listInitialize() {
        return servicedrone.initialize();

    }

}

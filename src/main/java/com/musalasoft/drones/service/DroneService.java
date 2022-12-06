/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musalasoft.drones.service;

import com.musalasoft.config.AppConstants;
import static com.musalasoft.config.AppConstants.DRONE_OVERLOAD;
import com.musalasoft.config.CustomMessage;
import com.musalasoft.drones.model.Drone;
import com.musalasoft.drones.model.Medication;
import com.musalasoft.drones.repository.DroneRepository;
import com.musalasoft.drones.repository.MedicationRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service("ServiceDrone")
public class DroneService implements IDroneService {

    @Autowired
    private DroneRepository dronerepo;
    @Autowired
    private MedicationRepository medsrepo;

    @Override
    public ResponseEntity<Drone> registerDrone(Drone d) {
        boolean exists = dronerepo.exists(d.getId());
        if (exists) {
            return ResponseEntity.badRequest().build();
        } else {
            Drone ld = dronerepo.save(d);
            return new ResponseEntity<>(ld, HttpStatus.CREATED);
        }
    }

    @Override
    public ResponseEntity<?> loadDrone(Long d, List<Medication> medication) {
        boolean exists = dronerepo.exists(d);
        if (!exists) {
//            return ResponseEntity.notFound().build();
            CustomMessage errorResponse = new CustomMessage();
            errorResponse.setMessage(AppConstants.DRONE_NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.OK);
        } else {
            Drone dron = dronerepo.findOne(d);
            double medweight = medication.stream().collect(Collectors.summingDouble(Medication::getWeight));
            double curdroneweight = 0;
            if (dron.getMedications().size() > 0) {
                curdroneweight = dron.getMedications().stream().collect(Collectors.summingDouble(Medication::getWeight));
            }
            if ((dron.getWeightlimit() - curdroneweight) < medweight) {
                CustomMessage errorResponse = new CustomMessage();
                errorResponse.setMessage(DRONE_OVERLOAD);
                return new ResponseEntity<>(errorResponse, HttpStatus.OK);
            } else {
                medication.forEach(r -> {
                    r.setDrone(dron);
                });
                medsrepo.save(medication);
                return new ResponseEntity<Drone>(dronerepo.findOne(dron.getId()), HttpStatus.OK);
            }
        }
    }

    @Override
    public ResponseEntity<List<Medication>> listMedicationByDrone(Long d) {
        boolean exists = dronerepo.exists(d);
        if (!exists) {
            return ResponseEntity.notFound().build();
        } else {
            Drone dron = dronerepo.findOne(d);
            System.err.println(dron.getMedications());
            return new ResponseEntity<>(dron.getMedications(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<List<Drone>> available() {
        List<Drone> listAvailable = new ArrayList<>();
        List<Drone> listdrones = dronerepo.findAll();
        listdrones.forEach(d -> {
//            if (null != d.getMedications()) {
            if (d.getMedications().size() > 0) {
                List<Medication> lm = d.getMedications();
                double mm = 0;
                if (lm.size() > 0) {
                    mm = lm.stream().collect(Collectors.summingDouble(Medication::getWeight));
                }
                if (d.getWeightlimit() > mm) {
                    listAvailable.add(d);
                }
            } else {
                listAvailable.add(d);
            }
//            } else {
//                listAvailable.add(d);
//            }
        });
        return new ResponseEntity<>(listAvailable, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getBatteryLevelByDrone(Long id) {
        boolean exists = dronerepo.exists(id);
        if (!exists) {
            return ResponseEntity.notFound().build();
        } else {
            Drone d = dronerepo.findOne(id);
            return new ResponseEntity<>("" + d.getBatterycapacity(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<List<Drone>> initialize() {
        List<Drone> d = new ArrayList<Drone>() {
            {
                add(Drone.builder().batterycapacity(10).weightlimit(5).serialnumber("123").build());
                add(Drone.builder().batterycapacity(20).weightlimit(6).serialnumber("124").build());
                add(Drone.builder().batterycapacity(30).weightlimit(5).serialnumber("125").build());
                add(Drone.builder().batterycapacity(10).weightlimit(6).serialnumber("126").build());
                add(Drone.builder().batterycapacity(17).weightlimit(5).serialnumber("127").build());
                add(Drone.builder().batterycapacity(23).weightlimit(6).serialnumber("128").build());
                add(Drone.builder().batterycapacity(35).weightlimit(5).serialnumber("129").build());
                add(Drone.builder().batterycapacity(19).weightlimit(6).serialnumber("130").build());
                add(Drone.builder().batterycapacity(24).weightlimit(5).serialnumber("131").build());
                add(Drone.builder().batterycapacity(17).weightlimit(6).serialnumber("132").build());
            }
        };
        dronerepo.save(d);
        return new ResponseEntity<>(dronerepo.findAll(), HttpStatus.OK);
    }

    @Override
    public List<Drone> listAll() {
        return dronerepo.findAll();
    }
}

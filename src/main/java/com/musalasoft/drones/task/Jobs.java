/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musalasoft.drones.task;

import com.musalasoft.drones.model.Drone;
import com.musalasoft.drones.service.IDroneService;
import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author user
 */
@EnableAsync
@PropertySource("classpath:timer.properties")
@Component
public class Jobs {

    @Autowired
    private IDroneService servicedrones;

    @Async
    @Scheduled(fixedRateString = "${battery_level}")
    public void checkbateryLevel() throws InterruptedException, IOException {
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.INFO);//Loget Info, Warning dhe Severe do ruhen
        FileHandler fileTxt = new FileHandler("c:/Logs/DroneLog.txt");
        SimpleFormatter formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);
        List<Drone> ld = servicedrones.listAll();
        ld.forEach(drone -> {
            String log = "Drone " + drone.getSerialnumber() + " : battery level " + drone.getBatterycapacity();
            logger.info(log);
        });
    }
}

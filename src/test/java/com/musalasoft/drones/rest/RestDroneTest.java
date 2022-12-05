/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musalasoft.drones.rest;

import java.io.IOException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;

//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlConfig.TransactionMode;
/*import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlConfig.TransactionMode;*/

/**
 *
 * @author user
 */
//@SpringBootTest
@Sql(scripts ={"/data-h2.sql"}, 
  config = @SqlConfig(encoding = "utf-8", transactionMode = TransactionMode.ISOLATED))
@ContextConfiguration(locations = {"classpath:spring-web-servlet.xml", "classpath:applicationContext.xml"})
public class RestDroneTest {

    private final String PROJECT_URL = "http://localhost:10080/Drones";

    @Before
    public void setup() {
    }

    @Test
    public void registerDrone() throws ClientProtocolException, IOException {
        String JSON_STRING = "{\"id\":11,\"serialnumber\":\"123444\",\"model\":null,\"weightlimit\":5.0,\"batterycapacity\":10.0,\"medications\":[]}" ;
        StringEntity entity = new StringEntity(
                JSON_STRING,
                ContentType.APPLICATION_JSON);
        System.err.println(JSON_STRING);
        HttpPost pp = new HttpPost(PROJECT_URL + "/drones/register");
        pp.setEntity(entity);
    pp.setHeader("Accept", "application/json");
    pp.setHeader("Content-type", "application/json");
//        HttpUriRequest request = pp;
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(pp);
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_CREATED));
    }

    @Test
    public void loadDrone() throws ClientProtocolException, IOException {
        String JSON_STRING = "[{\"id\":1,\"code\":\"1\",\"name\":\"med\",\"weight\":1,\"image\":null},\n"
                + "{\"id\":2,\"code\":\"12\",\"name\":\"med1\",\"weight\":1,\"image\":null},\n"
                + "{\"id\":3,\"code\":\"13\",\"name\":\"med2\",\"weight\":1,\"image\":null}\n"
                + "]";
        StringEntity entity = new StringEntity(
                JSON_STRING,
                ContentType.APPLICATION_JSON);
        HttpPost pp = new HttpPost(PROJECT_URL + "/drones/load/1");
        pp.setEntity(entity);
//        HttpUriRequest request = pp;
        
    pp.setHeader("Accept", "application/json");
    pp.setHeader("Content-type", "application/json");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(pp);
        assertThat(httpResponse.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void checkBatteryLevel()
            throws ClientProtocolException, IOException {
        HttpUriRequest request = new HttpGet(PROJECT_URL + "/drones/1/batterylevel"); 
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request); 
        assertThat(                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK)); 
    }

    @Test
    public void listMedicationByDrone()
            throws ClientProtocolException, IOException {
        long droneId = 1;
        HttpUriRequest request = new HttpGet(PROJECT_URL + "/drones+" + droneId + "/medications");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertThat(httpResponse.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void listavailable()
            throws ClientProtocolException, IOException {
        String name = RandomStringUtils.randomAlphabetic(8);
        HttpUriRequest request = new HttpGet(PROJECT_URL + "/drones/available");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));
    }
}

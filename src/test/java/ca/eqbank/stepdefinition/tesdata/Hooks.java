package ca.eqbank.stepdefinition.tesdata;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.example.GlobalVariables;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Hooks {

    @Before
    public void beforeScenario(Scenario scenario) throws IOException ,FileNotFoundException{
        System.out.println("Started scenario: "+scenario.getName());
        Properties prop = new Properties();
        prop.load(new FileInputStream(new File("config.properties")));
        String fetchEnv = prop.getProperty("env");
        System.out.println("Environment selected is: " + fetchEnv);
        }

    @After
    public void afterScenario(Scenario scenario){
        System.out.println("Completed scenario: "+scenario.getName());
    }
}

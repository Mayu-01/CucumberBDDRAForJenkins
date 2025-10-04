package ca.eqbank.stepdefinition.tesdata;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources/features",
        glue={"ca.eqbank.stepdefinition"},
tags="@prodtest")
public class TestRunner {

}

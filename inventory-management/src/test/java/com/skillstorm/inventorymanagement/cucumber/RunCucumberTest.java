package com.skillstorm.inventorymanagement.cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;



@CucumberOptions(
    features = "classpath:com/skillstorm/features/",
    glue = "com.skillstorm.inventorymanagement.cucumber",
    plugin = {"pretty", "html:target/cucumber-reports.html"}
    )
public class RunCucumberTest extends AbstractTestNGCucumberTests{
}



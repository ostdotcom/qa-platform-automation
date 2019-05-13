package com.platform.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.generators.OverviewReport;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import java.io.File;


//@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "json:target/cucumber/*.json","html:target/cucumber/cucumber-pretty"},
        glue = "com.platform.steps/",
        tags = "@token",
        features = "src/test/java/com/platform/features/"
        //monochrome = true
)

@RunWith(ExtendedCucumberRunner.class)
public class RunnerClass {


    @BeforeSuite
    public static void setUp() {
        System.out.println("In before Suite");
    }
    @AfterSuite
    public static void tearDown() {
        System.out.println("In After Suite");
    }
}

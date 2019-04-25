package com.platform.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.generators.OverviewReport;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import java.io.File;


@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "json:target/cucumber/","html:target/cucumber/cucumber-pretty"},
        glue = "com.platform.steps/",
        //tags = "@users",
        features = "src/test/java/com/platform/features/",
        monochrome = true
)

public class RunnerClass {
        @AfterClass
        public static void writeExtentReport() {
          //  Configuration configuration = new Configuration();
    }
}


package com.platform.runners;

import com.platform.base.Base_API;
import com.platform.constants.Constant;
import com.platform.managers.TestDataManager;
import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;



//@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "json:target/cucumber/*.json","html:target/cucumber/cucumber-pretty"},
        glue = "com.platform.steps/",
        //tags = "@token",
        features = "src/test/java/com/platform/features/",
        monochrome = true
)

@RunWith(ExtendedCucumberRunner.class)
public class RunnerClass {

    private Base_API base;

    public RunnerClass(Base_API base) {
        this.base = base;
    }

    // To-Do make it work with mvn command. Running great only if Runner.class called from IDE
    @BeforeSuite
    public static void setUp() {
        System.out.println("In before Suite");
        System.out.println("This Suite is running on '" + Constant.ENVIRONMENT + "' Environment and '" + TestDataManager.economy1.symbol + "' economy");
    }

    @AfterSuite
    public static void tearDown() {
        System.out.println("In After Suite");
    }
}

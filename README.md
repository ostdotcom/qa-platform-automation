# Test Automation Suite for OST PLATFORM
For QA automation projects




## Requirements

In order to utilise this project you need to have the following installed locally:

* Maven 3
* Java 1.8


## How To Run

To test all the features written in this project, run the **RunnerClass.java** This class will include all the features and tags.

````cmd
mvn test -Denv=st_s4_testnet -Dtoken=BEST -Dtest=RunnerClass
````

To run particular suite or group of test use **tags** to execute. Like **@sanity** or **@regression**

````cmd
mvn verify -Denv=st_s4_testnet -Dtoken=BEST -DCucumber.options="--tags @sanity"
````

Here user need to provide environment and token to be tested.


## Reporting

1. For reporting user need to look into consol, which will give you detailed output for each and every steps or API
2. For scenario reports, please navigate to 'target/cucumber/cucumber-reports/cucumber-html-reports/overview-features.html'

Example output
![Maven Cucumber report](https://cdn-images-1.medium.com/max/1600/1*59cbbRsnv5sajlD07BEV8g.png)



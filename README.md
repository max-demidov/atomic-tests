# Atomic 🦊 Tests

Base Java test framework for fast, scalable and atomic tests

## Technologies
* [Maven](https://maven.apache.org/) - Dependency management and build automation tool
* [TestNG](https://testng.org/) - Testing framework
* [Hamcrest](http://hamcrest.org/) - Framework for writing matcher objects
* [Selenium](https://www.seleniumhq.org/) - Web Browser Automation
* [Project Lombok](https://projectlombok.org/) - Library that spicing up Java syntax
* [ReportPortal](http://reportportal.io/) - Test automation analytics platform and real-time reporting
* [Sauce Labs](https://saucelabs.com/) - Cross Browser Testing cloud

## Installing

```
git clone https://github.com/max-demidov/atomic-tests.git
cd atomic-tests/
```

## Running

```
mvn test
```

### Example of running demo test in your local

* Install [Google Chrome](https://www.google.com/chrome/).
* Download and unpack [ChromeDriver](http://chromedriver.chromium.org/) to C:\webdriver\chromedriver.exe.
* Execute:

```
mvn test -Dwebdriver.chrome.driver=C:\webdriver\chromedriver.exe -Dsurefire.suiteXmlFiles=src/test/resources/suite/demo.xml
```

### Arguments

**Note.** As usual for JVM options each parameter must follow ` -D`.

| Parameter | Default | Description |
|:---|---:|:---|
|`surefire.suiteXmlFiles`   |           |Path for TestNG test suite XML file to execute.|
|`webdriver.chrome.driver`  |           |Path for your local chromedriver.exe. Required to run tests in your local Chrome.|
|`webdriver.gecko.driver`   |           |Path for your local geckodriver.exe. Required to run tests in your local Firefox.|
|`webdriver.edge.driver`    |           |Path for your local MicrosoftWebDriver.exe. Required to run tests in your local Edge.|
|`webdriver.ie.driver`      |           |Path for your local IEDriverServer.exe. Required to run tests in your local IE.|
|`browser.name`             |`CHROME`   |Browser to run tests against. Supported ones: CHROME, FIREFOX, EDGE, IE, SAFARI.|
|`browser.size`             |`1920x1080`|Size of browser window.|
|`pageLoadTimeout`          |`60`       |Seconds to wait until any page to be loaded. Exceeding throws TimeoutException.|
|`env`                      |`GOOGLE`   |Test environment to run tests in. There might be for example DEV, STAGE, PROD, etc environments in your project. For now there is only GOOGLE one for demo.|
|`sauceCreds`               |           |To run tests in Saucelabs you will need to specify `<username>:<access_key>`.|
|`sauceTunnel`              |           |To grant Saucelabs access to web resources in VPN you will need to specify `<tunnel_id>`.|
|`rp.endpoint`              |           |To use ReportPortal you need to specify URL of ReportPortal web service, where requests should be send to.|
|`rp.uuid`                  |           |Your ReportPortal UUID|
|`rp.enable`                |           |Boolean. Enable/Disable logging to Report Portal.|
|`rp.project`               |           |Project name in ReportPortal to identify scope.|
|`rp.keystore.resource`     |           |Path for your `reportportal-client-v2.jks`.|
|`rp.keystore.password`     |           |Access password for JKS (certificate storage) package, mentioned above.|
|`rp.launch`                |           |Name of test run in ReportPortal. Example with Jenkins variables: `$JOB_NAME in $env against ${browser.name}`.|
|`rp.description`           |           |Description of test run in ReportPortal. Example with Jenkins variables: `Jenkins $JOB_NAME #$BUILD_NUMBER in $env against ${browser.name} ($branch)`.|
|`rp.tags`                  |           |Set of tags separated by `;` with any additional meta data for current test run in ReportPortal. Example with Jenkins variables: `Jenkins; Saucelabs; #$BUILD_NUMBER; $env; $branch; ${surefire.suiteXmlFiles}; ${browser.name}`.|
|`rp.convertimage`          |`false`    |Boolean. Set `true` to convert colored log images to grayscale for reducing image size.|
|`rp.mode`                  |`DEFAULT`  |Supported values: `DEFAULT`, `DEBUG`. With `DEBUG` a run will not be available in ReportPortal for users with **Customer** role.|
|`rp.skipped.issue`         |`false`    |Boolean. With `true` skipped tests are considered as issues and marked as **To investigate**.|
|`rp.batch.size.logs`       |`10`       |In order to rise up performance and reduce number of requests to server.|
|`rp.group_by_folder`       |`false`    |Set `true` to represent folders with features as nested suites in ReportPortal.|

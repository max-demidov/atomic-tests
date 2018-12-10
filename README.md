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

1. Install [Google Chrome](https://www.google.com/chrome/).
2. Download and unpack [ChromeDriver](http://chromedriver.chromium.org/) to C:\webdriver\chromedriver.exe.
3. Execute:

```
mvn test -Dwebdriver.chrome.driver=C:\webdriver\chromedriver.exe -Dsurefire.suiteXmlFiles=src/test/resources/demo.xml
```

### Arguments

| Parameter | Default | Description |
|:---|---:|:---|
|`-Dsurefire.suiteXmlFiles` |`src/test/resources/demo.xml`  |Path for TestNG test suite XML file to execute.|
|`-Dwebdriver.chrome.driver`|   |Path for your local chromedriver.exe. Required to run tests in your local Chrome.|
|`-Dwebdriver.gecko.driver` |   |Path for your local geckodriver.exe. Required to run tests in your local Firefox.|
|`-Dwebdriver.edge.driver`  |   |Path for your local MicrosoftWebDriver.exe. Required to run tests in your local Edge.|
|`-Dwebdriver.ie.driver`    |   |Path for your local IEDriverServer.exe. Required to run tests in your local IE.|
|`-Dbrowser.name`   |`CHROME`   |Browser to run tests against. Supported ones: CHROME, FIREFOX, EDGE, IE, SAFARI.|
|`-Dbrowser.size`   |`1920x1080`|Size of browser window.|
|`-DpageLoadTimeout`|`60`   |Seconds to wait until any page to be loaded. Exceeding throws TimeoutException.|
|`-Denv`|`GOOGLE`   |Test environment to run tests in. There might be for example DEV, STAGE, PROD, etc environments in your project. For now there is only GOOGLE one for demo.|
|`-DsauceCreds` |   |To run tests in Saucelabs you will need to specify `<username>:<access_key>`.|
|`-DsauceTunnel`|   |To grant Saucelabs access to web resources in VPN you will need to specify `<tunnel_id>`.|
|`-Drp.endpoint`|   |To use ReportPortal you need to specify URL of ReportPortal web service, where requests should be send to.|
|`-Drp.uuid`    |   |Your ReportPortal UUID|
|`-Drp.enable`  |   |Boolean. Enable/Disable logging to Report Portal.|
|`-Drp.project` |   |Project name in ReportPortal to identify scope.|
|`-Drp.keystore.resource`   |   |Path for your `reportportal-client-v2.jks`.|
|`-Drp.keystore.password`   |   |Access password for JKS (certificate storage) package, mentioned above.|
|`-Drp.launch`  |   |Name of test run in ReportPortal. Example with Jenkins variables: `$JOB_NAME in $env against ${browser.name}`.|
|`-Drp.description` |   |Description of test run in ReportPortal. Example with Jenkins variables: `Jenkins $JOB_NAME #$BUILD_NUMBER in $env against ${browser.name} ($branch)`.|
|`-Drp.tags`|   |Set of tags separated by `;` with any additional meta data for current test run in ReportPortal. Example with Jenkins variables: `Jenkins; Saucelabs; #$BUILD_NUMBER; $env; $branch; ${surefire.suiteXmlFiles}; ${browser.name}`.|
|`-Drp.convertimage`|`false`|Boolean. Set `true` to convert colored log images to grayscale for reducing image size.|
|`-Drp.mode`|`DEFAULT`  |Supported values: `DEFAULT`, `DEBUG`. With `DEBUG` a run will not be available in ReportPortal for users with *Customer* role.|
|`-Drp.skipped.issue`   |`false`|Boolean. With `true` skipped tests are considered as issues and marked as *To investigate*.|
|`-Drp.batch.size.logs` |10 |In order to rise up performance and reduce number of requests to server.|
|`-Drp.group_by_folder` |`false`|Set `true` to represent folders with features as nested suites in ReportPortal.|

# Shrink Your Link
## About

The service "Shrink your Link" was created as part of a student project for "Web Programming" class (WWI2018EI) at the Cooperative State University Baden-Wuerttemberg (DHBW) in Stuttgart.

The project objective was to create a web service which shortens a URL provided by the user. Additionally, the service offers a list of previously shortened URLs, the corresponding original URLs as well as options to copy or remove the short URLs.

In accordance with the simplicity of this service, the user interface has also been kept in a simple yet modern and sleek design using plain HTML+CSS.

## Prerequisites

Please make sure you've installed the following prerequisites to build and run the project locally:

* **Apache Maven - Build Automation and Dependency Manager (see https://maven.apache.org)**
* **Java Development Kit (recommended: JDK 14) (see https://www.oracle.com/java/technologies/javase-downloads.html)**

## Run the service

We've provided an automated script that builds the project, runs the server and opens your default browser on localhost on the respective port.

Please run the script "run_webprog.sh" located in the webprog directory.

```
sh PATH_TO_DIRECTORY/webprog/run_webprog.sh 
```

## Compatibility
The script was tested on Windows and MacOS.

The service should with any major webbrowser. 

Please note, that due to some browser specifics the styling might vary slightly 
(e.g. smooth scroll on Mozilla Firefox - jump scroll on Apple Safari).


## Testing

The service has been tested for HTML/JS injections. 

It is not possible to inject HTML tags into the shortening form.


Various "types" of URLs have been tested.

If the URL is submitted without protocol specification, HTTP is added automatically.

If the URL starts or ends with whitespace, it is automaticcaly removed. 

If the URL contains blanks or no URL is specified at all, the URL is not submitted to the shortening service.

URLs with a length of over 2000 characters have been tested successfully.

## Troubleshooting

* If the service doesn't respond after the browser is opened by the script, the startup took longer than expected. Please refresh the browser.

* If the webbrowser doesn't open automatically, make sure your OS is supported by the script or open the browser manually on localhost:8080.

* If the page's style is not loaded properly, please clear your browser's cache & cookies or try using an alternate browser.

* If any other bugs or issues occur, please reach out to the authors via the contact provided below.


## Authors

* **Klaus Baumecker**
* **Philipp Bohlen** - wi18129@lehre.dhbw-stuttgart.de -
* **David Drexlin** - wi18047@lehre.dhbw-stuttgart.de -
* **Mika Heiringhoff** - wi18228@lehre.dhbw-stuttgart.de -
* **Natalie Vretinaris** - wi18128@lehre.dhbw-stuttgart.de -


## Acknowledgments

Thank you to Mr. Baumecker for laying the groundwork of this project 
and providing the necessary input during the lectures on Webprogramming.


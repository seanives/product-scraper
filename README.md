# product-scraper

> A sample java console application.

This project is a sample Java console application which scrapes a portion of the Sainsbury’s Groceries website.

The rationale behind the structure of this project is to make components reusable and testable. To achieve this I implemented a presenter/coordinator pattern that outlines all the possible error and success responses from each processing component (parsing, data aggregation, and reporting).  Each component is completely separated from each of the others, with the presenter forming a contract between components with different responsibilities.

The source of the implemented presenter can be found [here](https://github.com/seanives/product-scraper/blob/master/src/main/java/com/seanives/productscraper/presenter/ProductPresenter.java).

## Prerequisites

Before installing this application you will need the following:

1. Java 1.8 must be installed.  To find out if it is already installed, type the following at a command prompt:
```bash
java -version 
```
If not then you can install it via the following [link](https://java.com/en/download/help/download_options.xml).

2. This project is built using [Apache Maven](https://maven.apache.org/). To find out if it is already installed, type the following at a command prompt:
```bash
mvn -v
```
If not then you will need to [download](https://maven.apache.org/download.cgi) and [install](https://maven.apache.org/install.html) it.

## Installation

Checkout this repo and install the dependencies via Maven:

``` bash
mvn clean install 
```

This includes compiling the sources, executing the tests and packaging the compiled files into a JAR file.

## Running
To run the application from the command line:

```bash
java -jar target/ProductScraper-1.0-SNAPSHOT.jar
```

The results will be output to the console.

## Built with

* [JSoup](https://jsoup.org/) - A Java HTML parser library
* [org.json](https://github.com/stleary/JSON-java) - An implementation of JSON in Java
* [Google guice](https://github.com/google/guice) - A lightweight dependency injection framework

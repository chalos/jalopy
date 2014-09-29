# Jalopy [![License](http://img.shields.io/badge/license-MIT-lightgrey.svg?style=flat)](https://tldrlegal.com/l/bsd3) [![Build Status](http://img.shields.io/travis/lukespragg/jalopy/master.svg?style=flat)](https://travis-ci.org/lukespragg/jalopy) [![Coverage Status](http://img.shields.io/coveralls/lukespragg/jalopy/master.svg?style=flat)](https://coveralls.io/r/lukespragg/jalopy)

A source code formatter/beautifier/pretty printer for the Java programming language. It is aimed to provide a full-featured, yet free alternative to the well-known Jindent. Plug-ins for Ant, Eclipse, IDEA, JBuilder, JDeveloper, jEdit, NetBeans.

## Source Code
The latest and greatest source can be found here on [GitHub](https://github.com/lukespragg/jalopy). If you are using Git, use this command to clone the project:

    git clone git://github.com/lukespragg/jalopy.git

Or download the [latest zip archive](https://github.com/lukespragg/jalopy/archive/master.zip).

## Building from Source
This project can be built with the _latest_ [Java Development Kit](http://oracle.com/technetwork/java/javase/downloads) and [Maven](http://maven.apache.org/) or [Gradle](http://www.gradle.org/). Maven and Gradle are used to simplify dependency management, but using either of them is optional.

For Maven, the command `mvn clean package` will build the project and will put the compiled JAR in `target`, and `mvn clean install` will copy it to your local Maven repository.

For Gradle, the command `gradlew` will build the project and will put the compiled JAR in `~/build/distributions`, and `gradlew install` will copy it to your local Maven repository.

## Contributing
Are you a talented programmer looking to contribute some code? We'd love the help!

* Open a pull request with your changes, following the [guidelines and coding standards](CONTRIBUTING.md).
* Please follow the above guidelines for your pull request(s) accepted.
* For help setting up the project, keep reading!

## Usage
If you're using [Maven](http://maven.apache.org/download.html) to manage project dependencies, simply include the following in your `pom.xml` file:

    <dependency>
        <groupId>com.lukespragg</groupId>
        <artifactId>jalopy</artifactId>
        <version>1.1.0-SNAPSHOT</version>
    </dependency>

If you do not already have the required repo in your repository list, you will need to add this as well:

    <repository>
        <id>sonatype-nexus</id>
        <url>https://oss.sonatype.org/content/groups/public</url>
    </repository>

If you're using [Gradle](http://www.gradle.org/) to manage project dependencies, simply include the following in your `build.gradle` file:

    repositories {
        mavenLocal()
        mavenCentral()
        maven {
            name = 'sonatype-nexus'
            url = 'https://oss.sonatype.org/content/groups/public/'
        }
    }
    dependencies {
        compile 'com.lukespragg:jalopy:1.0.0-SNAPSHOT'
    }

## Documentation
To generate the the Javadocs with Maven, use the `mvn javadoc:javadoc` command. To view the javadocs simply go to `target/site/apidocs/` and open `index.html` in a web browser.

To generate Javadocs with Gradle, use the `gradlew javadoc` command. To view the javadocs simply go to `build/docs/javadoc/` and open `index.html` in a web browser.

## Legal Stuff
Jalopy is licensed under the [BSD 3-Clause License](https://tldrlegal.com/l/bsd3). Basically, you can do whatever you want as long as you include the original copyright. Please see the `LICENSE.txt` file for details.

## Credits
* The original developers of the Jalopy project and jalopy-maven-plugin.
* All the people behind [Java](http://www.oracle.com/technetwork/java/index.html), [Maven](http://maven.apache.org/), and [Gradle](http://www.gradle.org/).

Requires a custom built Jar file java_server.jar which is inside the lib directory in this repo

to run tests:

Add jar file

mvn install:install-file -Dfile=/your/path/to/lib/java_server.jar -DgroupId=TakaServer -DartifactId=TakaServerJar -Dversion=1.0.0 -Dpackaging=jar

mvn test

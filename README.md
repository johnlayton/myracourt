# Bunnings Catalogue Merge  

### Prerequisites

1. Java Version 11;
   ```shell
   $> brew install asdf && \
      asdf plugin add java && \
      asdf install java graalvm-21.1.0+java11 && \
      asdf local java graalvm-21.1.0+java11 && \
      asdf current
   ...
   java graalvm-21.1.0+java11 /<pwd>/.tool-versions
   ...
   ```

### Build and execute

   ```shell
   $> ./mvnw clean install
   ```

   ```shell
   $> ./mvnw clean install exec:java -Dexec.args="output.csv" && cat output.csv
   ```

   ```shell
   $> ./mvnw clean install exec:java -Dexec.args="output.csv src/main/resources/input A B" && cat output.csv         
   ```

   ```shell
   $> ./mvnw clean install exec:java -DforceStdout -q | column -t -s,      
   ```

   ```shell
   $> ./mvnw clean install exec:java -Dexec.args="output.csv src/main/resources/input A B" && cat output.csv | column -t -s,        
   ```

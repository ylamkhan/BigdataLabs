# BigdataLabs

A collection of Big Data labs and examples using Apache Hadoop MapReduce framework.

## Overview

This repository contains Hadoop MapReduce examples for learning and experimenting with Big Data processing. The project is built with Maven and includes a classic WordCount example to get started.

## Prerequisites

- Java JDK 8 or higher
- Apache Maven 3.6+
- Apache Hadoop 3.x (for running on a cluster)

## Project Structure

```
BigdataLabs/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/bigdata/wordcount/
│   │   │       └── WordCount.java
│   │   └── resources/
│   └── test/
│       ├── java/
│       └── resources/
├── input/
│   └── sample.txt
├── output/
├── pom.xml
└── README.md
```

## Building the Project

To compile the project and create a JAR file:

```bash
mvn clean package
```

This will generate a JAR file in the `target/` directory.

## Running WordCount Example

### Local Mode (Standalone)

Run the WordCount example on local input:

```bash
# Using Maven
mvn exec:java -Dexec.mainClass="com.bigdata.wordcount.WordCount" -Dexec.args="input output"

# Or using the compiled JAR
hadoop jar target/hadoop-labs-1.0-SNAPSHOT.jar com.bigdata.wordcount.WordCount input output
```

### HDFS Mode

If you have a Hadoop cluster running:

```bash
# Upload input to HDFS
hdfs dfs -mkdir -p /user/$(whoami)/wordcount/input
hdfs dfs -put input/sample.txt /user/$(whoami)/wordcount/input/

# Run the job
hadoop jar target/hadoop-labs-1.0-SNAPSHOT.jar com.bigdata.wordcount.WordCount \
    /user/$(whoami)/wordcount/input \
    /user/$(whoami)/wordcount/output

# View the results
hdfs dfs -cat /user/$(whoami)/wordcount/output/part-r-00000
```

## Examples Included

1. **WordCount**: Classic MapReduce example that counts word occurrences in text files
   - Location: `src/main/java/com/bigdata/wordcount/WordCount.java`
   - Input: Text files
   - Output: Word and count pairs

## Adding Your Own Examples

To add new MapReduce examples:

1. Create a new package under `src/main/java/com/bigdata/`
2. Implement your Mapper and Reducer classes
3. Add a main method to configure and run the job
4. Update the README with usage instructions

## License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.
# jdbc-pipe

Quick JDBC queries respecting UNIX conventions (environmental variables, STDIN, STDOUT etc)

## Installation

 1. Install the [Java SDK](https://openjdk.java.net/), preferably avoiding Oracle.
 2. Install [Leiningen](https://leiningen.org/).
 3. Check this project out and cd into it (`git clone git@github.com:forbesmyester/jdbc-pipe.git && cd jdbc-pipe`)
 4. Run `lein uberjar`.
 5. Create the destination directories in to ~/.local/bin `mkdir -p ~/.local/bin/jdbc-pipe-jars`
 6. Copy the JAR's and the bash script `cp target/uberjar/jdbc-pipe-*-standalone.jar ~/.local/bin/jdbc-pipe-jars/ && cp ./bin/jdbc-pipe ~/.local/bin`
 7. SLF4J which is in libraries this code uses is slightly noisy, might want to dig out slf4j-nop-?.?.??.jar from <https://www.slf4j.org/download.html> and drop it `~/.local/bin/jdbc-pipe-jars/` too.
 8. You may also want to put your chosen JDBC drivers in the `~/.local/bin/jdbc-pipe-jars/`.

## Usage

echo 'SELECT * FROM "@user".table_name limit 50' | java -cp "$PWD/target/uberjar/*" jdbc_pipe.core  csv -u username -p password -c "jdbc:url" -d "com.data-vendor.Driver" -s "sub-protocol"

## Options

    NAME:
     jdbc-pipe - Quick JDBC queries respecting UNIX conventions (environmental variables, STDIN, ST
    DOUT etc)
    
    USAGE:
     jdbc-pipe [global-options] command [command options] [arguments...]
    
    VERSION:
     0.0.1
    
    COMMANDS:
       ndjson               Runs a query outputting JSON
       edn                  Runs a query outputting EDN
       csv                  Runs a query outputting csv
       tsv                  Runs a query outputting tsv
    
    GLOBAL OPTIONS:
           --classname S    JDBC Driver Class [$JDBC_PIPE_CLASSNAME]
           --subprotocol S  JDBC Sub Protocol / Vendor [$JDBC_PIPE_SUBPROTOCOL]
           --subname S      JDBC Sub Name / Database Name [$JDBC_PIPE_SUBNAME]
       -u, --user S         JDBC User [$JDBC_PIPE_USER]
       -p, --password S     JDBC Password [$JDBC_PIPE_PASSWORD]
           --name S         JDBC Name [$JDBC_PIPE_NAME]
           --host S         JDBC Host [$JDBC_PIPE_HOST]
           --port S         JDBC Port [$JDBC_PIPE_PORT]
           --vendor S       JDBC Vendor [$JDBC_PIPE_VENDOR]
           --schema S       JDBC Schema [$JDBC_PIPE_SCHEMA]
           --read-only S    JDBC Read Only [$JDBC_PIPE_READ_ONLY]
       -r, --uri S          JDBC URI or Connection String [$JDBC_PIPE_URI]
       -?, --help

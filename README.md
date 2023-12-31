**Extreamly sample Kafka real time job:** <br />
##
In this example, we using docker and single node for all config. <br />
##
_Function_:<br />
##

   We have data in oracle, when we add new row, new line is append to file real time, I just implement very sample code for add data, this data will be append to file. <br />
##
_Preparing_:<br />
##
+ Dowload and install docker:<br />
+ Install JVM 8.<br />
+ Clone project.<br />
+ Dowload ojdbc11.jar (Oracle 21): https://www.oracle.com/database/technologies/appdev/jdbc-downloads.html ( note: we need to dowload exactly driver for oracle version)<br />
##
_Procedure_:<br />
##
+ Run cmd : docker compose up<br />
dockerfile\kafkaclusterandsampledata\docker-compose.yml<br />
+ Run cmd step by step:<br />
 dockerfile\kafkaclusterandsampledata\configdb.txt<br />
+  Run cmd : docker compose up<br />
dockerfile\flink\docker-compose.yml<br />
+ Call api to config debezium: <br />
     curl --location 'localhost:8083/connectors' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data '{
    "name": "customers-connector",
    "config": {
        "connector.class": "io.debezium.connector.oracle.OracleConnector",
        "tasks.max": "1",
        "database.hostname": "dbz_oracle21",
        "database.port": "1521",
        "topic.prefix": "fix",
        "database.user": "c##dbzuser",
        "database.password": "dbz",
        "database.dbname": "ORCLCDB",
        "database.pdb.name": "ORCLPDB1",
        "database.server.name": "server1",
        "schema.history.internal.kafka.bootstrap.servers": "kafka:9092",
        "schema.history.internal.kafka.topic": "schema-changes"
    }
}' <br />
(note: you can only create use with format "c##...".)<br />
+ Check config at: localhost:8088.<br />
+ Run project: <br />
You can run it as a standalone or submit job to flink cluster. If you subit to flink cluster, you need to build by maven shape "mvn shade:shade", access and submit add : localhost:8081<br />
+ Add new row to data base.<br />
+ check data on kafka.<br />
+ Check data in file.<br />

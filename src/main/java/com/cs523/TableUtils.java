package com.cs523;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;

public class TableUtils {
    public static TableName TABLE = TableName.valueOf("electronic_store");
    public static String CF_EVENTS = "events";

    public Configuration getConfig() {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "zookeeper");
        config.set("hbase.zookeeper.property.clientPort", "2181");
        return config;
    }

    public Connection newConnection() throws IOException {
        return ConnectionFactory.createConnection(getConfig());
    }

    public void createTable() throws IOException {
        // Create a connection to the HBase server
        Connection connection = newConnection();
        // Create an admin object to perform table administration operations
        Admin admin = connection.getAdmin();

        // Create a table descriptor
        TableDescriptor tableDesc = TableDescriptorBuilder.newBuilder(TABLE)
                .setColumnFamily(ColumnFamilyDescriptorBuilder.of(CF_EVENTS))
                .build();

        // Create the table
        if (admin.tableExists(TABLE)) {
            admin.disableTable(TABLE);
            admin.deleteTable(TABLE);
        }
        admin.createTable(tableDesc);
        System.out.println("==== Table created!!! ====");
    }
}

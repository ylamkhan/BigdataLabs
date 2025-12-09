package edu.supmti.hadoop.hdfslab;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

public class WriteHDFS {

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.out.println("Usage : <chemin_complet> <message>");
            System.exit(1);
        }

        Path path = new Path(args[0]);
        String message = args[1];

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);

        if (!fs.exists(path)) {
            FSDataOutputStream out = fs.create(path);
            out.writeUTF("Bonjour tout le monde !");
            out.writeUTF(message);
            out.close();
            System.out.println("Fichier créé sur HDFS.");
        } else {
            System.out.println("Le fichier existe déjà.");
        }

        fs.close();
    }
}

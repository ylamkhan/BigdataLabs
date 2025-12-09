package edu.supmti.hadoop.hdfslab;

import java.io.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

public class ReadHDFS {

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.out.println("Usage : <chemin_fichier>");
            System.exit(1);
        }

        String fichier = args[0];

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);

        Path path = new Path(fichier);

        if (!fs.exists(path)) {
            System.out.println("Fichier introuvable !");
            System.exit(1);
        }

        FSDataInputStream input = fs.open(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(input));

        String ligne;
        while ((ligne = br.readLine()) != null) {
            System.out.println(ligne);
        }

        br.close();
        fs.close();
    }
}

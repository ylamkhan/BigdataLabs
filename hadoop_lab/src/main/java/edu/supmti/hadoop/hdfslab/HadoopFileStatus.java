package edu.supmti.hadoop.hdfslab;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

public class HadoopFileStatus {

    public static void main(String[] args) throws IOException {

        if (args.length != 3) {
            System.out.println("Usage : <chemin> <nom_fichier> <nouveau_nom>");
            System.exit(1);
        }

        String chemin = args[0];
        String fichier = args[1];
        String nouveau = args[2];

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);

        Path filepath = new Path(chemin, fichier);

        if (!fs.exists(filepath)) {
            System.out.println("Le fichier n'existe pas !");
            System.exit(1);
        }

        FileStatus status = fs.getFileStatus(filepath);

        System.out.println("---- Informations du fichier ----");
        System.out.println("Nom : " + filepath.getName());
        System.out.println("Taille : " + status.getLen() + " bytes");
        System.out.println("Propriétaire : " + status.getOwner());
        System.out.println("Permissions : " + status.getPermission());
        System.out.println("Facteur de réplication : " + status.getReplication());
        System.out.println("Taille de bloc : " + status.getBlockSize());

        // Affichage localisation des blocs
        BlockLocation[] blocs = fs.getFileBlockLocations(status, 0, status.getLen());
        for (BlockLocation b : blocs) {
            System.out.println("\nBloc offset : " + b.getOffset());
            System.out.println("Bloc taille : " + b.getLength());
            System.out.print("Hôtes : ");
            for (String host : b.getHosts()) {
                System.out.print(host + " ");
            }
            System.out.println();
        }

        // Renommage du fichier
        fs.rename(filepath, new Path(chemin, nouveau));
        System.out.println("\nFichier renommé en : " + nouveau);

        fs.close();
    }
}

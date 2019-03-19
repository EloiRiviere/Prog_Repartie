package tp1;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
/**
 *
 * @author elriviere2
 * @author sapereira3
 */

 /*
  * Commandes:
  * cd Prog_Repartie/1_Comptage_de_schur_sans_parallelisme/tp1/build/classes
  * javac ../../src/tp1/MainTp1.java; mv ../../src/tp1/MainTp1.class tp1/;
  * -> time java tp1.MainTp1 10000.schur
  * -> time java tp1.MainTp1 100000.schur
  * -> time java tp1.MainTp1 300000.schur
  * -> time java tp1.MainTp1 1000000.schur
  */

 /*
  * Valeurs à trouver:
  *   10000.schur -> Valeur:    249 332 | Temps:   0.2s
  *  100000.schur -> Valeur:  6 251 210 | Temps:   2.6s
  *  300000.schur -> Valeur: 14 066 974 | Temps:  16.0s
  * 1000000.schur -> Valeur: 39 072 907 | Temps: 127.0s
 */


 /*
  * Résultats trouvés:
  *   10000.schur -> Valeur:    249 332 | Temps:     0.117s
  *  100000.schur -> Valeur:  6 251 210 | Temps:     3.471s
  *  300000.schur -> Valeur: 14 066 974 | Temps:    27.261s
  * 1000000.schur -> Valeur: 39 072 907 | Temps: 4m 27,618s
  */

  /*
  * Idée pour auglenter rapidité:
  * -> Utilisation de threads
  *    -> calcul en parallèle, 1 thread par valeur de i (for ligne 114)
  */

public class MainTp1
{
    static Integer nbBytes;

    /**
     * Méthode Main
     * @param args, tableau de caractères contenant les arguments
     *     (si respect des règles d'utilisaation, nom du fichier .schur)
     */
    public static void main(String[] args)
    {
        try
        {
          System.out.println(conflits(chargement(args[0])));
        }
        catch(IOException e)
        {
          System.out.println("Erreur, fichier passé en argument introuvable.");
        }
    }

    /**
     * Méthode chargement
     * @param location, chaîne de caractères contenant le nom d'un fichier de lecture
     * @return Retourne un tableau de Bytes contenant les valeurs lues dans le fichier
     *     ou null si le fichier de lecture n'a pas pu s'ouvrir
     */
    public static Byte[] chargement(String location) throws IOException
    {
        File file = new File("ressources/" + location);

        try
        {
            FileReader reader = new FileReader(file);
            BufferedReader buffer = new BufferedReader(reader);

            MainTp1.nbBytes = new Integer(buffer.readLine()).intValue();

            Byte[] data = new Byte[MainTp1.nbBytes+1];
            int i;

            for(i=1;i<=MainTp1.nbBytes;i++)
            {
                    data[i] = new Byte(buffer.readLine()).byteValue();
            }

            return data;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            MainTp1.nbBytes = 0;
            return null;
        }
    }

    /**
     * Méthode conflits
     * @param data, Tableau de Bytes
     * @return int, retourne le nombre de conflits
     */
    public static int conflits(Byte[] data)
    {
        int nbConflits = 0;
        int i, j=0;

        for(i=1;i<=MainTp1.nbBytes/2;i++)
        {
            for(j=i;i+j<=MainTp1.nbBytes;j++)
            {
                if(data[i] == data[j] && data[j] == data[i+j])
                {
                    nbConflits ++;
                }
            }
        }
        return nbConflits;
    }
}

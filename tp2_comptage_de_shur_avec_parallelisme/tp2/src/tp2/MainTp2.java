package tp2;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.InterruptedException;
/**
 *
 * @author elriviere2
 * @author sapereira3
 */

 /*
  * Commandes:
  * cd Prog_Repartie/1_Comptage_de_schur_sans_parallelisme/tp2/build/classes
  * javac ../../src/tp2/MainTp2.java; mv ../../src/tp2/MainTp2.class tp2/;
  * // Pour [X ∈ {1,2,4,8,16}] où X est le nombre de threads utilisés
  * -> time java tp2.MainTp2 10000.schur X
  * -> time java tp2.MainTp2 100000.schur X
  * -> time java tp2.MainTp2 300000.schur X
  * -> time java tp2.MainTp2 1000000.schur X
  */

 /*
  * Valeurs à trouver:
  *   10000.schur -> Valeur:    249 332 | Temps:   0.2s
  *  100000.schur -> Valeur:  6 251 210 | Temps:   2.6s
  *  300000.schur -> Valeur: 14 066 974 | Temps:  16.0s
  * 1000000.schur -> Valeur: 39 072 907 | Temps: 127.0s
 */


 /*
  * Résultats trouvés: [Machine: ASUS ROG GL-502-VT + PNY SSD 1To]
  *
  * Pour  10000.schur:
  * time java tp2.MainTp2   10000.schur  1 ->   249332 -> real: 0m  0,119s -> user: 0m  0,152s
  * time java tp2.MainTp2   10000.schur  2 ->   249332 -> real: 0m  0,100s -> user: 0m  0,160s
  * time java tp2.MainTp2   10000.schur  4 ->   249332 -> real: 0m  0,085s -> user: 0m  0,144s
  * time java tp2.MainTp2   10000.schur  8 ->   249332 -> real: 0m  0,092s -> user: 0m  0,160s
  * time java tp2.MainTp2   10000.schur 16 ->   249332 -> real: 0m  0,089s -> user: 0m  0,156s
  *
  * Pour 100000.schur
  * time java tp2.MainTp2  100000.schur  1 ->  6251210 -> real: 0m  3,474s -> user: 0m  3,532s
  * time java tp2.MainTp2  100000.schur  2 ->  6251210 -> real: 0m  1,900s -> user: 0m  3,788s
  * time java tp2.MainTp2  100000.schur  4 ->  6251210 -> real: 0m  1,057s -> user: 0m  3,996s
  * time java tp2.MainTp2  100000.schur  8 ->  6251210 -> real: 0m  0,938s -> user: 0m  3,344s
  * time java tp2.MainTp2  100000.schur 16 ->  6251210 -> real: 0m  0,841s -> user: 0m  3,120s
  *
  * Pour 300000.schur
  * time java tp2.MainTp2  300000.schur  1 -> 14066974 -> real: 0m 26,308s -> user: 0m 26,376s
  * time java tp2.MainTp2  300000.schur  2 -> 14066974 -> real: 0m 13,925s -> user: 0m 27,812s
  * time java tp2.MainTp2  300000.schur  4 -> 14066974 -> real: 0m  7,809s -> user: 0m 29,420s
  * time java tp2.MainTp2  300000.schur  8 -> 14066974 -> real: 0m  6,974s -> user: 0m 26,724s
  * time java tp2.MainTp2  300000.schur 16 -> 14066974 -> real: 0m  5,976s -> user: 0m 23,376s
  *
  * Pour 1000000.schur
  * time java tp2.MainTp2 1000000.schur  1 -> 39072907 -> real: 4m 56,021s -> user: 4m 56,156s
  * time java tp2.MainTp2 1000000.schur  2 -> 39072907 -> real: 2m 32,815s -> user: 5m  5,532s
  * time java tp2.MainTp2 1000000.schur  4 -> 39072907 -> real: 1m 19,738s -> user: 5m  4,708s
  * time java tp2.MainTp2 1000000.schur  8 -> 39072907 -> real: 1m 19,533s -> user: 5m  7,500s
  * time java tp2.MainTp2 1000000.schur 16 -> 39072907 -> real: 1m  8,142s -> user: 4m 27,596s
  */

/**
 *  Classe MainTp2
 */
public class MainTp2 implements Runnable
{
    static int nbBytes, nbConflits = 0, nbThreads;
    int nbConflitsT = 0;
    int pos;
    static Byte[] data;

    /**
     * Méthode Main
     * @param args, tableau de caractères contenant les arguments
     *     (si respect des règles d'utilisation:
     *        args[0]: nom du fichier .schur
     *        args[1]: nombre de threads
     *     )
     */
    public static void main(String[] args)
    {
      int i;

        try
        {
          MainTp2.data = chargement(args[0]);
          MainTp2.nbThreads = Integer.parseInt(args[1]);
        }
        catch(IOException e)
        {
          System.out.println("Erreur, fichier passé en argument introuvable.");
        }

        MainTp2 classes[] = new MainTp2[MainTp2.nbThreads];
        Thread threads[] = new Thread[MainTp2.nbThreads];
        for(i=0;i<MainTp2.nbThreads;i++)
        {
          classes[i] = new MainTp2(i,MainTp2.nbBytes);
          threads[i] = new Thread(classes[i]);
        }

        for(i=0;i<MainTp2.nbThreads;i++)
        {
          threads[i].start();
        }

        try
        {
          for(i=0;i<MainTp2.nbThreads;i++)
          {
            threads[i].join();
          }
        }
        catch(InterruptedException e)
        {
          return;
        }

        for(i = 0;i<MainTp2.nbThreads;i++)
        {
          MainTp2.nbConflits += classes[i].nbConflitsT;
        }

        System.out.println(MainTp2.nbConflits);
    }

    /**
     * Constructeur MainTp2
     * @param pos, int identifiant le numéro du thread
     * @param nbBytes, int contenant le nombre de bytes dans le tableau de bytes "data"
     */
    public MainTp2(int pos, int nbBytes)
    {
      this.pos = pos+1;
      this.nbBytes = nbBytes;
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

            MainTp2.nbBytes = new Integer(buffer.readLine()).intValue();

            Byte[] data = new Byte[MainTp2.nbBytes+1];
            int i;

            for(i=1;i<=MainTp2.nbBytes;i++)
            {
              data[i] = new Byte(buffer.readLine()).byteValue();
            }

            return data;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            MainTp2.nbBytes = 0;
            return null;
        }
    }

    /**
     * Méthode run
     * Définition de la méthode run du thread
     */
    public void run()
    {
      this.nbConflitsT = traitementThread();
    }

    /**
     * Méthode traitementThread
     * Définit le traitement que le thread effectuera, méthode appelée dans la méthode run()
     * @return int, retourne le nombre de conflits
     */
    public int traitementThread()
    {
      int i, j;
      for(i = this.pos;i<MainTp2.nbBytes/2;i += MainTp2.nbThreads)
      {
        for(j=i;i+j<=MainTp2.nbBytes;j++)
        {
            if(MainTp2.data[i] == MainTp2.data[j] && MainTp2.data[j] == MainTp2.data[i+j])
            {
                this.nbConflitsT ++;
            }
        }
      }
      return this.nbConflitsT;
    }

}

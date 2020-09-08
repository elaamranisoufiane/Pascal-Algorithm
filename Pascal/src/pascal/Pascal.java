package Pascal;
/*
####################################################################################
#                        *** Algorithme PASCAL ***                                #
#               -------------------------------------------------                  #
#  Réaliser par : EL Aamrani SouFiane                                              #
#  Objet : ALgorithm Pascal avec langage Java                                   #        
####################################################################################
*/

 
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Pascal {

    public static void main(String[] args) {
        AprioriCalculation ap = new AprioriCalculation();
        ap.aprioriProcess();
    }
}

class AprioriCalculation
{
    // Les Motifs Condidats 
    Vector<String> candidates=new Vector<String>();
     Vector<String> FalseCandidates = new Vector<String>();
    /* fichier de configuration qui contient 
    1/ nombre des colonnes 
    1/ nombre des lignes 
    1/  le support minimal (par pourcentage)
     */
    String configFile="src\\config.txt";
    /*
    le fichier des transitions qui contient la presentaion booleanne de la base de données   
    */
    String transaFile="src\\transa.txt";
    /*
    le fichier de sortie outputfile  permet de conserver les resultats de l'extraction 
    */
    String outputFile="src\\apriori-output.txt";
    String KeysFile= "src\\Keys.txt";
    //le nombre des items 
    int numItems;
    // le nombre des transitions 
    int numTransactions;
    // le support minimal 
    double minSup;
    
    String oneVal[];
    //le chaine de charactere qui contient les supports les  motifs condidats 
    String itemSep = " ";

 public void aprioriProcess()
    {
        Date d;
        //le temps de debut et de fin de l'operation 
        long start, end;
        // le numero d'itemset extrairer !!
        int itemsetNumber=0;
        //get config
       getConfig();
        System.out.println("L'algorithme Pascal a commencé !! .\n");
        //le temps de debut
        d = new Date();
        start = d.getTime();
        //si l'operation nest pas complete !!!!!!!!
        do
        {
            //accrementer le nombre des itemset traité
            itemsetNumber++;
            //appel de la methode qui génere les items condidats
            generateCandidates(itemsetNumber);
            //appel de la methode qui permet de calculer les Fréquences des élements  condidats
          //  calculateFrequentItemsets(itemsetNumber);
            // si il exist des condidats qui sont fréquent faire le suit : 
            //####################
           IfExistInKey();
            System.out.println(FalseCandidates);
           // System.out.println(Exist("4"));
                       Exist("4");
           System.out.println(candidates);
            //####################
            if(candidates.size()!=0)
            {
                //le nomnbre d'itemset courante
                System.out.println("Frequent " + itemsetNumber + "-itemsets");
                // l'ensemble des condidats fréquents 
                System.out.println(candidates);
            }
            }while(candidates.size()>1);
        //Temps final
        d = new Date();
        end = d.getTime();
        System.out.println("Temps d'execution est : "+((double)(end-start)/1000) + " s.");
    }
 //!!!!!!!!!!!!!!!!!!!!!!!
public static String getInput()
    {
        String input="";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try
        {
            input = reader.readLine();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return input;
    }
//Methode : extraire les données qui ont dans les fichiers config.txt et transa.txt 
  private void getConfig()
    {
        FileWriter fw;
        BufferedWriter file_out;
        String input="";
      /*  System.out.println("Default Configuration: ");
        System.out.println("\tRegular transaction file with '" + itemSep + "' item separator.");
        System.out.println("\tConfig File: " + configFile);
        System.out.println("\tTransa File: " + transaFile);
        System.out.println("\tOutput File: " + outputFile);
        System.out.println("\nPress 'C' to change the item separator, configuration file and transaction files");
        System.out.print("or any other key to continue.  ");
*/
       // input=getInput();
 
        /*
        if(input.compareToIgnoreCase("c")==0)
        {
            System.out.print("Enter new transaction filename (return for '"+transaFile+"'): ");
            input=getInput();
            if(input.compareToIgnoreCase("")!=0)
                transaFile=input;

            System.out.print("Enter new configuration filename (return for '"+configFile+"'): ");
            input=getInput();
            if(input.compareToIgnoreCase("")!=0)
                configFile=input;

            System.out.print("Enter new output filename (return for '"+outputFile+"'): ");
            input=getInput();
            if(input.compareToIgnoreCase("")!=0)
                outputFile=input;

            System.out.println("Filenames changed");

            System.out.print("Enter the separating character(s) for items (return for '"+itemSep+"'): ");
            input=getInput();
            if(input.compareToIgnoreCase("")!=0)
                itemSep=input;
        }
*/
        try
        {
             FileInputStream file_in = new FileInputStream(configFile);
             BufferedReader data_in = new BufferedReader(new InputStreamReader(file_in));
             //number of items
             numItems=Integer.valueOf(data_in.readLine()).intValue();

             //number of transactions
             numTransactions=Integer.valueOf(data_in.readLine()).intValue();

             //minsup
             minSup=(Double.valueOf(data_in.readLine()).doubleValue());

             //output config info to the user
          /*
             System.out.print("\nInput configuration: "+numItems+" items, "+numTransactions+" transactions, ");
             System.out.println("minsup = "+minSup+"%");
             System.out.println();
*/
             minSup/=100.0;

            oneVal = new String[numItems];
            //System.out.print("Enter 'y' to change the value each row recognizes as a '1':");
         /*   if(getInput().compareToIgnoreCase("y")==0)
            {
                for(int i=0; i<oneVal.length; i++)
                {
                    System.out.print("Enter value for column #" + (i+1) + ": ");
                  //  oneVal[i] = getInput();
                }
            }
            else*/
                for(int i=0; i<oneVal.length; i++)
                    oneVal[i]="1";
            fw= new FileWriter(outputFile);
            file_out = new BufferedWriter(fw);
            file_out.write(numTransactions + "\n");
            file_out.write(numItems + "\n******\n");
            file_out.close();
        }
                catch(IOException e)
        {
            System.out.println(e);
        }

    }

  //Methode de généralisation des items condidats 
 private void generateCandidates(int n)
    {
        Vector<String> tempCandidates = new Vector<String>();
        String str1, str2;
        StringTokenizer st1, st2;
        if(n==1)
        {
            for(int i=1; i<=numItems; i++)
            {
                tempCandidates.add(Integer.toString(i));
            }
        }
        else if(n==2)         {
             for(int i=0; i<candidates.size(); i++)
            {
                st1 = new StringTokenizer(candidates.get(i));
                str1 = st1.nextToken();
                for(int j=i+1; j<candidates.size(); j++)
                {
                    st2 = new StringTokenizer(candidates.elementAt(j));
                    str2 = st2.nextToken();
                    tempCandidates.add(str1 + " " + str2);
                }
            }
        }
        else
        {
            for(int i=0; i<candidates.size(); i++)
            {
                for(int j=i+1; j<candidates.size(); j++)
                {
                    str1 = new String();
                    str2 = new String();
                    st1 = new StringTokenizer(candidates.get(i));
                    st2 = new StringTokenizer(candidates.get(j));

                    for(int s=0; s<n-2; s++)
                    {
                        str1 = str1 + " " + st1.nextToken();
                        str2 = str2 + " " + st2.nextToken();
                    }

                        if(str2.compareToIgnoreCase(str1)==0)
                        tempCandidates.add((str1 + " " + st1.nextToken() + " " + st2.nextToken()).trim());
                        
                }
            }
        }
        candidates.clear();
        candidates = new Vector<String>(tempCandidates);
        tempCandidates.clear();
    }
 //calcule des items fréquents 
 private void calculateFrequentItemsets(int n)
    {
        Vector<String> frequentCandidates = new Vector<String>();
        FileInputStream file_in;
        FileInputStream file2_in;
        BufferedReader data_in;
      //  BufferedReader data2_in;
        FileWriter fw;
        BufferedWriter file_out;

        StringTokenizer st, stFile;
        boolean match;
        boolean trans[] = new boolean[numItems];
        int count[] = new int[candidates.size()];
        
                try
        {
                //output file
                fw= new FileWriter(outputFile, true);
                file_out = new BufferedWriter(fw);
               //load the transaction file
                file_in = new FileInputStream(transaFile);
                //file2_in = new FileInputStream("src\\Keys.txt");
                data_in = new BufferedReader(new InputStreamReader(file_in));
               // data2_in = new BufferedReader(new InputStreamReader(file2_in));
               for(int i=0; i<numTransactions; i++)
                {
                    stFile = new StringTokenizer(data_in.readLine(), itemSep); //read a line from the file to the tokenizer
                    for(int j=0; j<numItems; j++)
                    {
                       trans[j]=(stFile.nextToken().compareToIgnoreCase(oneVal[j])==0); //if it is not a 0, assign the value to true
                    }
                    for(int c=0; c<candidates.size(); c++)
                    {
                        match = false;                        
                        st = new StringTokenizer(candidates.get(c));
                        while(st.hasMoreTokens())
                        {
                            match = (trans[Integer.valueOf(st.nextToken())-1]);
                           
                            if(!match)
                                
                                break;
                        }
                        if(match)
                        count[c]++;
                     
                    }
                 
                }   
               System.out.println("");        
            for(int i=0; i<candidates.size(); i++)
                {
                    
                    System.out.print((count[i]/(double)numTransactions)+"\t");
                   
                  if((count[i]/(double)numTransactions)>=minSup)
                    {
                        frequentCandidates.add(candidates.get(i));
                        file_out.write(candidates.get(i) + "," + count[i]/(double)numTransactions + "\n");
                    }
                }
             System.out.println("");
                file_out.write("-\n");
                file_out.close();
        }
      catch(IOException e)
        {
            System.out.println(e);
        }
        candidates.clear();
        candidates = new Vector<String>(frequentCandidates);
        frequentCandidates.clear();
    }

 //////////////////////
  
 private void IfExistInKey()
 {
   
      
        FileInputStream file_in_keys;
        BufferedReader data_in_keys;
         FileWriter fw_keys;
        BufferedWriter file_keys;

        StringTokenizer st_keys, stFile_keys;
        boolean match;
        boolean trans[] = new boolean[numItems];
        int TempKeys[] = new int[candidates.size()];
        
                try
        {
               
                fw_keys= new FileWriter(KeysFile, true);
                file_keys = new BufferedWriter(fw_keys);
                file_in_keys = new FileInputStream(KeysFile);
                data_in_keys = new BufferedReader(new InputStreamReader(file_in_keys));
               // data2_in = new BufferedReader(new InputStreamReader(file2_in));
                       int count =0,CountFalseMotifs = 0;
                      match = false;                        
                        stFile_keys = new StringTokenizer(data_in_keys.readLine()); 
                        while(stFile_keys.hasMoreTokens())
                        { 
                         String courrentkey=stFile_keys.nextToken();
                       
                 // test= (Integer.valueOf(stFile_keys.nextToken())-1);
                
                  if(courrentkey.compareToIgnoreCase("0") ==0 )
                  {
               //Integer.valueOf(courrentkey)
                      CountFalseMotifs++;
                  //System.out.println(count);
                  FalseCandidates.add(String.valueOf(count));
                  count++;
                  }
                  else{ count++; }
                  
                        }
                                  
                        
               
               
               /*
                  
               for(int i=0; i<numTransactions; i++)
                {
                    stFile = new StringTokenizer(data_in.readLine(), itemSep); //read a line from the file to the tokenizer
                    for(int j=0; j<numItems; j++)
                    {
                       trans[j]=(stFile.nextToken().compareToIgnoreCase(oneVal[j])==0); //if it is not a 0, assign the value to true
                    }
                    for(int c=0; c<candidates.size(); c++)
                    {
                        match = false;                        
                        st = new StringTokenizer(candidates.get(c));
                        while(st.hasMoreTokens())
                        {
                            match = (trans[Integer.valueOf(st.nextToken())-1]);
                           
                            if(!match)
                                
                                break;
                        }
                        if(match)
                        count[c]++;
                     
                    }
                 
                }   
               System.out.println("");        
            for(int i=0; i<candidates.size(); i++)
                {
                    
                    System.out.print((count[i]/(double)numTransactions)+"\t");
                   
                  if((count[i]/(double)numTransactions)>=minSup)
                    {
                        frequentCandidates.add(candidates.get(i));
                        file_out.write(candidates.get(i) + "," + count[i]/(double)numTransactions + "\n");
                    }
                }
            
            */
               
                
                        
                        
               
               
               
               
             System.out.println("");
                file_keys.write("-\n");
                file_keys.close();
        }
      catch(IOException e)
        {
            System.out.println(e);
        }
        candidates.clear();
     //   candidates = new Vector<String>(FalseCandidates);
       // frequentCandidates.clear();
      
 }
 
 public void Exist(String motif){
 boolean test=false;
     for(int i=0;i<candidates.size();i++)
     {
    //  if(motif.compareToIgnoreCase(candidates.elementAt(i)) ==0)
    //  {
          System.out.println("test"+candidates.elementAt(i));
      test=true;
     // }
          
 }
    // return test;
 }
 
}

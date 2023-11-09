import java.util.*;
import java.io.*;




public class randomwordgen {

    static String[] words = new String[100000]; 
    static int oldCount = Initialize();  

    public static void main(String[] args){
        int choice = 0;
        String wordChoice;
        String wordChoice2;
        Scanner s = new Scanner(System.in);
        while (choice != -1){
            System.out.println("Enter your choice :");
            System.out.println("1  - Add Words");
            System.out.println("2  - Delete Word");
            System.out.println("3  - Search Word");
            System.out.println("4  - Replace Word");
            System.out.println("5  - Get Rid of Duplicates");
            System.out.println("-1 - End Program");
            choice = s.nextInt();
            s.nextLine();
            switch(choice){
                case 1 : 
                    AddWords(s);
                    break;
                case 2:
                    System.out.println("Enter word to be deleted");
                    wordChoice = s.nextLine();
                    System.out.println(DeleteWord(wordChoice));
                    break;
                case 3:
                    System.out.println("Enter word to be searched");
                    wordChoice = s.nextLine();
                    System.out.println(SearchWord(wordChoice));
                    break;
                case 4:
                    System.out.println("Enter word to be replaced");
                    wordChoice = s.nextLine();
                    System.out.println("Enter new word");
                    wordChoice2 = s.nextLine();
                    System.out.println(ReplaceWord(wordChoice, wordChoice2));
                    break;
                case 5:
                    DuplicateRemover();
                    System.out.println("Duplicates Removed :)");
                case -1:
                    System.out.println("Ending Program");
                    break;
                default:
                    break;
            }
        }
        s.close();


    }

    public static int Initialize(){
        try{
            FileReader fr = new FileReader("words.txt");
            BufferedReader br = new BufferedReader(fr);
            int oldCount =0;
            String textline = br.readLine();
            while(textline != null){
                words[oldCount] = textline;
                oldCount++;
                textline = br.readLine();
            }
            br.close();
            return oldCount;
            


        }
        catch(IOException ex){
            System.out.println("error");
            return -1;
        }
    }

    public static String SearchWord(String searchWord){
        for(int i =0; i<oldCount; i++){
            if((searchWord.toLowerCase()).equals(words[i].toLowerCase())){
                return "The word is present in the database \n" ;
            }
        }
        return "The word is not present in the database \n";
    }

    public static String DeleteWord(String deleteWord){

        if(SearchWord(deleteWord).equals("The word is present in the database \n")){
            try{
            int deletecount =0;
            for(int i =0; i<oldCount; i++){
                if((words[i].toLowerCase()).equals(deleteWord.toLowerCase())){
                    for(int j=i; j<oldCount-1; j++){
                        words[j] = words[j+1];
                    }
                    deletecount++;
                    oldCount--;
                    i--;
                }
            }
            FileWriter fw = new FileWriter("words.txt");
            for (int i =0;i<oldCount;i++){
                fw.write(words[i] + "\n");
            }
            fw.close();
            return "Successfully deleted the word " + deletecount + " times!";

            }
            catch(IOException ex){
            return "error";
            }
        }
        else {
            return "Word not found in database";
        }
    }
    
    public static void AddWords(Scanner s){
        try{
            String line;
            System.out.println("Input the list of words");
            line = s.nextLine();
            int count = 0;
            while (!line.equals("")){
               
                words[count + oldCount] = line;
                count++;
                line = s.nextLine();
                
            }
            words[count+oldCount] = null; 
            System.out.println("\n\n"+"Successfully added " + (count) + " words to the list");
            FileWriter fw = new FileWriter("words.txt");
            for (int i =0;i<(oldCount+count);i++){
                fw.write(words[i] + "\n");
            }
            fw.close();
            oldCount += count;
            }


        catch(IOException ex){
            System.out.println("error");
        }

    }

    public static String ReplaceWord(String oldWord, String newWord){
        if (SearchWord(oldWord).equals("The word is present in the database \n")) {
            for (int i =0; i<oldCount;i++){
                if ((words[i].toLowerCase()).equals(oldWord.toLowerCase())){
                    words[i] = newWord;
                }
            }
            try{
                FileWriter fw = new FileWriter("words.txt");
                for (int i =0;i<oldCount;i++){
                    fw.write(words[i] + "\n");
                }
                fw.close();
                return "Successfully changed the word";
            }
            catch(IOException ex){
                return "Error";
            }
        }
        else {
            return "Word not found in database";
        }
    }

    public static void DuplicateRemover() {
        HashSet<String> set = new HashSet<>();
        for (String word : words) {
            if (word != null) {
                set.add(word.toLowerCase());
            }
        }
        words = set.toArray(new String[0]);
        oldCount = words.length;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("words.txt"));
            for (String word : words) {
                if (word != null) {
                    writer.write(word);
                    writer.newLine();
                }
            }
            writer.close();
        } catch(IOException ex) {
            System.out.println("Error");
        }
    }
    
    
    
}

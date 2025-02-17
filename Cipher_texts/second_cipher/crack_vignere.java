import java.io.*;
import java.util.*;
import java.util.Map.Entry;
public class crack_vignere {
    
    public static final String Alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_#";
    public static final int key_length = 13;
    public static void main(String[] args){
        char[][]table = generate_letter_table(new File("cipher2.txt"));
        Map<Integer, String> column_max_letter_freq = new HashMap<Integer, String>();
        for(int i = 0; i < 13; i++){
            Map<Character, Integer> freq = count_letter_freq_in_column(table, i);        
            Entry<Character, Integer> maxEntry = Collections.max(freq.entrySet(), Comparator.comparingInt(Entry::getValue));
            String item = maxEntry.getKey().toString();
            column_max_letter_freq.put(i, item);
        }
       String[][] vignere_table = generate_vignere_table();
       print_2d_array(vignere_table);
       String[] keys = get_probable_keys(column_max_letter_freq, vignere_table);
       print_key_cipher_correlation(keys, column_max_letter_freq.values().toArray(new String[0]));
    }


    public static String[] get_probable_keys(Map<Integer, String> column_max_letter_freq, String[][]vignere_table){
        String[] probable_keys = new String[key_length];
        int k = 0;
        String[] array = column_max_letter_freq.values().toArray(new String[0]);
    for(int j = 0; j < key_length; j++){
        for(int i = 0; i < Alphabet.length(); i++){
            if(k == key_length){
                break;
            }
            if(vignere_table[i][14].equals(array[j])){
                probable_keys[j] = vignere_table[0][i];
            }
        }
    }
        return probable_keys;
    }

    public static void print_key_cipher_correlation(String[] array, String[] array2){
        System.out.println();
        int i = 0;
        System.out.println("Probable Key -> Cipher");
        for(; i < key_length; i++){
            System.out.println("\t " + array[i] + " -----> " + array2[i]);
        }
    }

    public static Map<Character, Integer> count_letter_freq_in_column(char[][] table, int column){
        Map<Character, Integer> freq = new HashMap<>();
        int i = 0;
        for (; i < table.length; i++) {
            char character = table[i][column];
            if(freq.containsKey(character)){
                freq.put(character, freq.get(character)+1);
            }
            else{
                freq.put(character, 1);
            }
        }
        //System.out.println("column " + column + " ");
        return freq;
    }

    public static char[][] generate_letter_table(File cipher){
        char[][] table = new char[808][13];
        try {
            BufferedReader br = new BufferedReader(new FileReader(cipher));
            int i = 0;
            int j = 0;
            int c;
            while ((c = br.read()) != -1) {
                char character = (char) c;
                if(i < 808 && j < 13){
                    table[i][j] = character;
                     j++;
                }
                else if(j == 13){
                    i++;
                    j = 0;
                }
               
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return table;
    }
    public static String[][] generate_vignere_table(){
        String[][] table = new String[Alphabet.length()][Alphabet.length()];
        for (int i = 0; i < Alphabet.length(); i++) {
            for (int j = 0; j < Alphabet.length(); j++) {
                table[i][j] = Alphabet.charAt((i+j)%Alphabet.length())+"";
            }
        }
        return table;
    }
    public static void print_2d_array(char[][] table){
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                System.out.print(table[i][j]+" ");
            }
            System.out.println();
        }
    }
    public static void print_2d_array(String[][] table){
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                System.out.print(table[i][j]+" ");
            }
            System.out.println();
        }
    }


}


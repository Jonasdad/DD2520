import java.io.*;
import java.util.*;
import java.util.Map.Entry;
public class crack_vignere {
    
    public static final String Alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_#";
    public static final int key_length = 13;
    public static int[] frequencies = new int[38];
    public static void main(String[] args){
        char[][]table = generate_letter_table(new File("cipher2.txt"));
        char[] column_max_letter_freq = new char[13];
        for(int i = 0; i < 13; i++){
            char most_common_in_column = count_letter_freq_in_column(table, i);        
            column_max_letter_freq[i] = most_common_in_column;
            System.out.println(most_common_in_column);
        }
       
       String[][] vignere_table = generate_vignere_table();
       print_2d_array(vignere_table);
       //String[] keys = get_probable_keys(column_max_letter_freq, vignere_table);
       //print_key_cipher_correlation(keys, column_max_letter_freq.values().toArray(new String[0]));
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

    public static char count_letter_freq_in_column(char[][] table, int column){
       Map<Character, Integer> freq = new HashMap<Character, Integer>();
       for(int i = 0; i < table.length; i++){
            char character = table[i][column];
            int index = Alphabet.indexOf(character);
            frequencies[index]++;
       }
       int max = Arrays.stream(frequencies).max().getAsInt();
       return Alphabet.charAt(max);
    }

    public static char[][] generate_letter_table(File cipher){
        char[][] table = new char[808][13];
        int count = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(cipher));
            int c;
            int row = 0;
            int col = 0;
            while ((c = br.read()) != -1) {
                char character = (char) c;
                if (Alphabet.indexOf(character) == -1) {
                    continue; // Skip characters not in the allowed set
                }
                count++;
                table[row][col] = character;
                col++;
                if (col == 13) {
                    col = 0;
                    row++;
                    if (row == 808) {
                        break; // Stop if the table is full
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
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


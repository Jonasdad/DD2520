import java.io.*;
import java.util.*;
import java.util.Map.Entry;
public class crack_vignere {
    
    public static final String Alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_#";
    public static final int key_length = 12;
    public static int[] frequencies = new int[38];
    public static void main(String[] args){
        char[][]table = generate_letter_table(new File("cipher2.txt"));
        char[] column_max_letter_freq = new char[key_length];
        for(int i = 0; i < key_length; i++){
            char most_common_in_column = count_letter_freq_in_column(table, i);        
            column_max_letter_freq[i] = most_common_in_column;
            System.out.println("Most Common Letter in Column: "+ i + " is " + most_common_in_column);
        }
       
       String[][] vignere_table = generate_vignere_table();
       print_2d_array(vignere_table);
       String[] keys = get_probable_keys(column_max_letter_freq, vignere_table);
       char[][] cipher_as_char_array = read_cipher_as_columns("chiper_as_columns.txt", 806, 12);
       shift_and_write_to_file(cipher_as_char_array, keys, "deciphered.txt");
      
    }    
    
    public static char[][] read_cipher_as_columns(String filePath, int numRows, int numCols) {
        char[][] table = new char[numRows][numCols];
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            int row = 0;
            while ((line = br.readLine()) != null && row < numRows) {
                for (int col = 0; col < line.length() && col < numCols; col++) {
                    table[row][col] = line.charAt(col);
                }
                row++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return table;
    }
    public static void shift_and_write_to_file(char[][] cipher_as_columns, String[] probable_keys, String outputFilePath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
            for (int i = 0; i < cipher_as_columns.length; i++) {
                for (int j = 0; j < cipher_as_columns[i].length; j++) {
                    char original_char = cipher_as_columns[i][j];
                    int original_index = Alphabet.indexOf(original_char);
                    int key_index = Alphabet.indexOf(probable_keys[j % probable_keys.length].charAt(0));
                    int shifted_index = (original_index - key_index + Alphabet.length()) % Alphabet.length();
                    char shifted_char = Alphabet.charAt(shifted_index);
                    writer.write(shifted_char);
                }
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static String[] get_probable_keys(char[] column_max_letter_freq, String[][] vignere_table) {
        String[] probable_keys = new String[key_length];
        int index = 0;
        for (int i = 0; i < key_length; i++) {
            for (int j = 0; j < Alphabet.length(); j++) {
                if (vignere_table[j][14].equals(column_max_letter_freq[i] + "")) {
                    System.out.println(column_max_letter_freq[i] + " --> " + vignere_table[j][0]);
                    probable_keys[index] = vignere_table[j][0];
                    index++;
                    break; // Move to the next character in column_max_letter_freq
                }
            }
        }
        return probable_keys;
    }

    public static char count_letter_freq_in_column(char[][] table, int column){
       for(int i = 0; i < table.length; i++){
            char character = table[i][column];
            int index = Alphabet.indexOf(character);
            frequencies[index]++;
           // System.out.println("Column: " + column +" Character: " + character + " Index: " + index + " Frequency: " + frequencies[index]);
       }
         int max = get_largest_int_in_array(frequencies);
         //System.out.println("Max: " + max);
         return Alphabet.charAt(get_index_of_value(frequencies, max));
    }

    public static int get_index_of_value(int[] array, int value){
        for(int i = 0; i < array.length; i++){
            if(array[i] == value){
                return i;
            }
        }
        return -1;
    }

    public static int get_largest_int_in_array(int[] array){
        int max = Arrays.stream(array).max().getAsInt();
        return max;
    }

    public static char[][] generate_letter_table(File cipher){
        char[][] table = new char[808][key_length];
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
                if (col == key_length) {
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


import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class vignere{

    static String Alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_#";
    public static void main(String[] args) {
        driver();
    }


    public static void driver(){
        File cipher = new File("cipher2.txt");
        Map<Integer, Integer> primeCount_triplets = new HashMap<>();
        Map<Integer, Integer> primeCount_quadruples = new HashMap<>();
        try {
            remove_newline_underscore_and_hash(cipher);
            List<SequenceWithIndex> triplets = count_n_letter_sequence(new File("realcipher.txt"), 3);
            List<SequenceWithIndex> quadruples = count_n_letter_sequence(new File("realcipher.txt"), 4);
            remove_occurances_under_n(triplets, 15);
            remove_occurances_under_n(quadruples, 5);
            distance(triplets);
            distance(quadruples);
            print_triplets(triplets);
            pretty_print(triplets, primeCount_triplets);
            pretty_print(quadruples, primeCount_quadruples);
            //System.out.println("Prime Count for Triplets: ");
            //printSortedPrimeCounts(primeCount_triplets);
            //System.out.println("Prime Count for Quadruples: ");
            //printSortedPrimeCounts(primeCount_quadruples);
            //common_primes(primeCount_triplets, primeCount_quadruples);
            for(int i = 0; i < triplets.size(); i++){
                System.out.println(gcd(triplets.get(i).distance_to_next.get(0), triplets.get(i).distance_to_next.get(1)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void common_primes(Map<Integer, Integer> primeCount_triplets, Map<Integer, Integer> primeCount_quadruples) {
        for (Entry<Integer, Integer> entry : primeCount_triplets.entrySet()) {
            if (primeCount_quadruples.containsKey(entry.getKey())) {
                System.out.println("Common Prime: " + entry.getKey() + " (Triplets: " + entry.getValue() + ", Quadruples: " + primeCount_quadruples.get(entry.getKey()) + ")");
            }
        }
    }

    static int gcd(int a, int b)
    {
        // stores minimum(a, b)
        int i;
        if (a < b)
            i = a;
        else
            i = b;
 
        // take a loop iterating through smaller number to 1
        for (i = i; i > 1; i--) {
 
            // check if the current value of i divides both
            // numbers with remainder 0 if yes, then i is
            // the GCD of a and b
            if (a % i == 0 && b % i == 0)
                return i;
        }
 
        // if there are no common factors for a and b other
        // than 1, then GCD of a and b is 1
        return 1;
    }
    
    public static void printSortedPrimeCounts(Map<Integer, Integer> primeCount) {
        List<Entry<Integer, Integer>> list = new ArrayList<>(primeCount.entrySet());
        list.sort(Entry.comparingByValue(Comparator.reverseOrder()));

        System.out.println("Sorted prime factor counts:");
        System.out.print("\n[");
        for (Entry<Integer, Integer> entry : list) {
            System.out.print(" " + entry.getKey() + ": " + entry.getValue()+", ");
        }
        System.out.println("]");
    }

    public static void pretty_print(List<SequenceWithIndex> sequences, Map<Integer, Integer> primeCount) {
        for (SequenceWithIndex sequence : sequences) {
            System.out.println("Sequence: " + sequence.Sequence);
            System.out.println("Index: " + sequence.index);
            System.out.println("Occurance: " + sequence.index.size());
            System.out.println("Distance to next: " + sequence.distance_to_next);
            System.out.println("Prime factors of distance to next: ");
            for (int distance : sequence.distance_to_next) {
                primeFactors(distance, primeCount);
                System.out.println();
            }
        }
    }
 
    //GEEKS FOR GEEKS CODE: https://www.geeksforgeeks.org/java-program-for-efficiently-print-all-prime-factors-of-a-given-number/
    public static void primeFactors(int n, Map<Integer, Integer> primeCount) {
        // Print the number of 2s that divide n
        while (n % 2 == 0) {
            System.out.print(2 + " ");
            primeCount.put(2, primeCount.getOrDefault(2, 0) + 1);
            n /= 2;
        }

        // n must be odd at this point. So we can skip one element (Note i = i +2)
        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            // While i divides n, print i and divide n
            while (n % i == 0) {
                System.out.print(i + " ");
                primeCount.put(i, primeCount.getOrDefault(i, 0) + 1);
                n /= i;
            }
        }

        // This condition is to handle the case when n is a prime number greater than 2
        if (n > 2) {
            System.out.print(n);
            primeCount.put(n, primeCount.getOrDefault(n, 0) + 1);
        }
    }
    public static void distance(List<SequenceWithIndex> triplets){
        int distance = 0;
        for (int i = 0; i < triplets.size(); i++) {
            for (int j = 0; j < triplets.get(i).index.size() - 1; j++) {
                distance = triplets.get(i).index.get(j+1) - triplets.get(i).index.get(j);
                triplets.get(i).distance_to_next.add(distance);
            }
        }
    }

    public static void print_triplets(List<SequenceWithIndex> triplets){
        System.out.println("Sequence Occurance DistanceToNext");
        for (SequenceWithIndex SequenceWithIndex : triplets) {
            System.out.println(SequenceWithIndex.Sequence +" " + SequenceWithIndex.occurance+ " " + SequenceWithIndex.distance_to_next);
        }
    }
    public static void remove_occurances_under_n(List<SequenceWithIndex> triplets, int n){
        for (int i = 0; i < triplets.size(); i++) {
            if(triplets.get(i).occurance < n){
                triplets.remove(i);
                i--;
            }
        }
    }
    public static void remove_newline_underscore_and_hash(File file) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(file));
        FileWriter fw = new FileWriter("realcipher.txt");
        String line;
        while ((line = br.readLine()) != null) {
            line = line.replace("\n", "");
            fw.write(line);
        }
        fw.close();        
        br.close();
    }


    public static List<SequenceWithIndex> count_n_letter_sequence(File file, int n) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        List<SequenceWithIndex> triplets = new ArrayList<>();
        int currentIndex = 1;
        while ((line = br.readLine()) != null) {
            line = line.replace("\n", "").replace("_", " ");
            for (int i = 0; i <= line.length() - n; i++) {
                String Sequence = line.substring(i, i + n);
                if(!exists_in_list(triplets, Sequence)){
                    List<Integer> indices = new ArrayList<>();
                    List<Integer> distance_to_next = new ArrayList<>();
                    indices.add(currentIndex + i);
                    triplets.add(new SequenceWithIndex(Sequence, indices, distance_to_next));
                }
                else{
                    for (SequenceWithIndex SequenceWithIndex : triplets) {
                        if(SequenceWithIndex.Sequence.equals(Sequence)){
                            SequenceWithIndex.occurance++;
                            SequenceWithIndex.index.add(currentIndex + i);
                        }
                    }
                }
            }
            currentIndex += line.length();
        }
        br.close();
        return triplets;
    }


    public static boolean exists_in_list(List<SequenceWithIndex> triplets, String Sequence){
        for (SequenceWithIndex SequenceWithIndex : triplets) {
            if(SequenceWithIndex.Sequence.equals(Sequence)){
                return true;
            }
        }
        return false;
    }


}
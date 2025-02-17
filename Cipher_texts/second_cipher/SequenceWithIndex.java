import java.util.*;
public class SequenceWithIndex{

    String Sequence;
    List<Integer> index;
    int occurance;
    // E.g. Index 0 means the first occurance of this word to the next identical word
    List<Integer> distance_to_next;
    HashMap<Integer, Integer> primes;

    public SequenceWithIndex(String Sequence, List<Integer> index, List<Integer> distance_to_next){
        this.Sequence = Sequence;
        this.occurance = 1;
        this.index = index;
        this.distance_to_next = distance_to_next;
    }
}
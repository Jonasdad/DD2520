# English word frequency
#english_word_frequency = ["e", "t", "a", "o", "i", "n", "s",
#                          "h", "r", "d", "l", "u", "c", "w", "m",
#                          "f", "y", "g", "p", "b", "v", "k", "j",
#                          "x", "q", "z"]


english_word_frequency = ["a", "o", "i", "n", "s",
                          "r", "d", "l", "u", "c", "w", "m",
                          "f", "y", "g", "p", "b", "v", "k", "j",
                          "x", "q", "z"]

def clean_up_cipher(input_filename, output_filename):
    with open(input_filename, 'r') as file:
        data = file.read()
        data = data.replace("#", "\n")
        data = data.replace("_", " ")
    with open(output_filename, 'w') as file:
        file.write(data)

def count_frequencies(filename, n):
    # Initialize a dictionary to store the frequencies
    frequencies = {}
    
    # Read the file and clean up the text
    with open(filename, 'r') as file:
        content = file.read().replace(" ", "").replace("\n", "")
    
    # Iterate through the content to count n-grams
    for i in range(len(content) - n + 1):
        ngram = content[i:i+n]
        if ngram in frequencies:
            frequencies[ngram] += 1
        else:
            frequencies[ngram] = 1
    
    return frequencies


def count_word_frequencies(filename, lengths):
    # Initialize a dictionary to store the frequencies
    frequencies = {}
    
    # Read the file and clean up the text
    with open(filename, 'r') as file:
        content = file.read()
    
    # Split the content into words
    words = content.split()
    
    # Iterate through the words to count words of specified lengths
    for word in words:
        if len(word) in lengths:
            if word in frequencies:
                frequencies[word] += 1
            else:
                frequencies[word] = 1
    
    return frequencies

def write_frequencies_to_file(frequencies, output_filename):
    with open(output_filename, 'w') as file:
        for ngram, count in sorted(frequencies.items(), key=lambda item: item[1], reverse=True):
            file.write(f"{ngram}: {count}\n")


mapping_table = {
    "2" : "e",
    "1" : "t",
    "H" : "h",
    "E" : "o",
    "K" : "a",
    "9" : "n",
    "7" : "f",
    "Z" : "d",
    "M" : "i",
    "U" : "c",
    "T" : "r",
    "Y" : "s",
    "_" : " ",
    "#" : "l",
    "B" : "v",
    "J" : "p",
    "4" : "m",
    "6" : "u",
    "C" : "g",
    "G" : "w",
    "W" : "k",
    "Q" : "y",
    "P" : "b",
    "L" : "x",
    "O" : "q",
    "3" : " ",
    "\n" : "",
}
def transform_cipher(input_filename, output_filename, mapping_table):
    with open(input_filename, 'r') as file:
        content = file.read()
    
    # Perform the transformation using the decode table
    transformed_content = ''.join(mapping_table.get(char, char) for char in content)
    
    with open(output_filename, 'w') as file:
        file.write(transformed_content)




if __name__ == "__main__":
    input_filename = "cipher1.txt"  # Replace with your input file path
    clean_cipher = "clean_cipher.txt"
    output_filename = "transformed_cipher.txt"  # Output file path
    
    clean_up_cipher(input_filename, clean_cipher)
    one_letter_word_frequencies = count_word_frequencies(clean_cipher, [1])
    write_frequencies_to_file(one_letter_word_frequencies, "one_letter_word_frequencies.txt")
    # Compute and write 2-letter word frequencies
    two_letter_word_frequencies = count_word_frequencies(clean_cipher, [2])
    write_frequencies_to_file(two_letter_word_frequencies, "two_letter_word_frequencies.txt")
    
    # Compute and write 3-letter word frequencies
    three_letter_word_frequencies = count_word_frequencies(clean_cipher, [3])
    write_frequencies_to_file(three_letter_word_frequencies, "three_letter_word_frequencies.txt")
    transform_cipher(input_filename, "semi_transformed.txt", mapping_table)
    print("Finished computing word frequencies! See 'two_letter_word_frequencies.txt' and 'three_letter_word_frequencies.txt'")
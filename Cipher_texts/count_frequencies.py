def count_letter_frequencies(filename):
    # Initialize a dictionary to store the frequencies
    frequencies = {}
    
    # Open the file and read its contents
    with open(filename, 'r') as file:
        for line in file:
            for char in line:
                # Convert to lowercase to count letters case-insensitively
                char = char.lower()
                if char.isalpha():  # Check if the character is a letter
                    if char in frequencies:
                        frequencies[char] += 1
                    else:
                        frequencies[char] = 1
    
    return frequencies

# Example usage
if __name__ == "__main__":
    input_filename = "cipher1.txt"  # Replace with your input file path
    output_filename = "letter_frequencies.txt"  # Output file path

    frequencies = count_letter_frequencies(input_filename)
    frequencies2 = count_letter_frequencies("cipher2.txt")
    # Print frequencies to the console

    # Write frequencies to the output file
    with open(output_filename, 'w') as output_file:
        output_file.write(f"Cipher 1:\n")
        for letter, count in sorted(frequencies.items()):
            output_file.write(f"\t{letter}: {count}\n")
        output_file.write(f"\nCipher 2:\n")
        for letter, count in sorted(frequencies2.items()):
            output_file.write(f"\t{letter}: {count}\n")
    print("Finished counting letters! See 'letter_frequencies.txt'")
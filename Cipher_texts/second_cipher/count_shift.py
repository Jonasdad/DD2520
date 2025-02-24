ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_#"
SHIFT_TO_E = "26AEIMQUY0OM"

#KEY: 
# 4
#Shifts
#"{'2': 12, '6': 8, 'A': 4, 'E': 0, 'I': 34, 'M': 30, 'Q': 26, 'U': 22, 'Y': 18, '0': 14, 'O': 28} "
#{'2': 34, '6': 30, 'A': 26, 'E': 22, 'I': 18, 'M': 14, 'Q': 10, 'U': 6, 'Y': 2, '0': 36, 'O': 12}
shifts = [34, 30, 26, 22, 18, 14, 10, 6, 2 ,36, 12, 14]
def calculate_shift_to_e(alphabet, shift_to_e):
    shifts = {}
    target = '_'
    target_index = alphabet.index(target)
    
    for char in shift_to_e:
        char_index = alphabet.index(char)
        shift = (target_index - char_index) % len(alphabet)
        shifts[char] = shift
    
    return shifts

# ...existing code...

def read_file(file_path):
    with open(file_path, 'r') as file:
        return file.read()

def shift_text(text, alphabet, shifts):
    shifted_text = []
    alphabet_length = len(alphabet)
    
    for i, char in enumerate(text):
        if char in alphabet:
            char_index = alphabet.index(char)
            shift = shifts[i % len(shifts)]
            new_index = (char_index + shift) % alphabet_length
            shifted_text.append(alphabet[new_index])
        else:
            shifted_text.append(char)  # Keep the character as is if it's not in the alphabet
    
    return ''.join(shifted_text)

# Example usage
file_path = 'realcipher.txt'
text = read_file(file_path)
shifted_text = shift_text(text, ALPHABET, shifts)
print(shifted_text)


# Example usage
shifts = calculate_shift_to_e(ALPHABET, SHIFT_TO_E)
print(shifts)
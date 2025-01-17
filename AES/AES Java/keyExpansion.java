public class keyExpansion {

    private byte[][] words;
    private byte[] originalKey;
    public byte[][] roundKeys;

    public keyExpansion(byte[] originalKey){
        this.originalKey = originalKey;
        this.words = new byte[44][4];
        this.roundKeys = new byte[11][16];
    }

    public void generate_keys(){
        generate_words();
        roundKeys[0] = originalKey;
        for(int i = 1; i < 11; i++){
            for(int j = 0; j < 4; j++){
                for(int k = 0; k < 4; k++){
                    roundKeys[i][4*j + k] = words[4*i + j][k];
                }
            }
        }
    }

    public void generate_words(){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                words[i][j] = originalKey[4*i + j];
            }
        }
        for(int i = 4; i < 44; i++){
            byte[] temp = new byte[4];
            for(int j = 0; j < 4; j++){
                temp[j] = words[i-1][j];
            }
            if(i % 4 == 0){
                temp = Subword(Rotword(temp));
                temp[0] ^= AES.rcon[(i / 4) - 1];  // Ensure correct Rcon index
            }
            for(int j = 0; j < 4; j++){
                words[i][j] = (byte) (words[i-4][j] ^ temp[j]);
            }
        }
    }



    public byte[] Rotword(byte[] word){
        return new byte[]{word[1], word[2], word[3], word[0]};
    }

    public byte[] Subword(byte[] word){
        byte[] result = new byte[4];
        for(int i = 0; i < 4; i++){
            int value = word[i] & 0xFF;  // Convert to unsigned
            result[i] = (byte) AES.sbox[(value >> 4) & 0x0F][value & 0x0F];
        }
        return result;
    }

    public void print_words() {
        for (int i = 0; i < 44; i++) {
            System.out.print("Word " + i + ": ");
            for (int j = 0; j < 4; j++) {
                System.out.printf("0x%02X ", words[i][j]);
            }
            System.out.println();
        }
    }

    public void print_keys(){
        for(int i = 0; i < 11; i++){
            System.out.print("Round key " + i + ": ");
            for(int j = 0; j < 16; j++){
                System.out.printf("%02X", roundKeys[i][j]);
            }
            System.out.println();
        }
    }
}

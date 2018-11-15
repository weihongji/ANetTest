package Tools;


//******************************************************************
//
//  Encryption stuff
//
//******************************************************************
public class Encryption {

    private byte[] key = null;

    ////////////////////////////////////////////////////////////////////
    //
    //  Constructor
    //
    ////////////////////////////////////////////////////////////////////
    public Encryption() {
        this(null);
    }

    public Encryption(String init_value) {
        if (init_value == null || init_value.length() == 0) {
            init_value = "FooBarCity";
        }
        setKey(init_value);
    }
    
    private void setKey(String seed) {
        
        StringBuilder hk = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            switch (i) {
                case 0:
                    hk.append("H");
                    break;
                case 1:
                    hk.append("i");
                    break;
                case 2:
                    hk.append("d");
                    break;
                case 3:
                    hk.append("d");
                    break;
                case 4:
                    hk.append("e");
                    break;
                case 5:
                    hk.append("n");
                    break;
                case 6:
                    hk.append(" ");
                    break;
                case 7:
                    hk.append("K");
                    break;
                case 8:
                    hk.append("e");
                    break;
                case 9:
                    hk.append("y");
                    break;
            }
        }

        if (seed == null || seed.length() == 0) {
            key = hk.toString().getBytes();
        } else {
            key = RC4(seed.getBytes(), hk.toString().getBytes());
        }
    }

    ////////////////////////////////////////////////////////////////////
    //
    //  Tack a checksum onto the front of the given byte array
    //
    ////////////////////////////////////////////////////////////////////
    private byte[] checkSum(byte[] b) {
        byte[] c = new byte[b.length + 2];
        c[0] = c[1] = 0;

        for (int i = 0; i < b.length; i++) {
            c[i + 2] = b[i];
            c[i & 1] ^= b[i];
        }

        return c;
    }

    ////////////////////////////////////////////////////////////////////
    //
    //  Encrypt the given password using RC4 encryption and a private
    //  key.
    //
    // 	NOTE: This method is the same as encryptString except that 
    //  encryptString pads bytes < 16 with "0". Values encrypted with 
    //  encryptPassword can't be decrypted with decryptString.
    ////////////////////////////////////////////////////////////////////
    public String encryptPassword(String password) {
        if (password == null || password.length() == 0 || key.length == 0) {
            return "";
        }
        return encryptPassword(checkSum(password.getBytes()), key);
    }
 
    private String encryptPassword(byte[] passwordWithChecksum, byte[] key) {
        byte[] b = RC4(passwordWithChecksum, key);
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            s.append(Integer.toString(b[i] & 0xFF, 16));
        }
    
        return s.toString().toUpperCase();
    }    

    ////////////////////////////////////////////////////////////////////
    //
    //  Convert a password to equivalent 'touchtone' value
    //
    ////////////////////////////////////////////////////////////////////
    public String encryptTouchTonePassword(String s) {
        StringBuilder sb = new StringBuilder();
        s = s.toLowerCase();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == 'a' || c == 'b' || c == 'c') {
                sb.append('2');
            } else if (c == 'd' || c == 'e' || c == 'f') {
                sb.append('3');
            } else if (c == 'g' || c == 'h' || c == 'i') {
                sb.append('4');
            } else if (c == 'j' || c == 'k' || c == 'l') {
                sb.append('5');
            } else if (c == 'm' || c == 'n' || c == 'o') {
                sb.append('6');
            } else if (c == 'p' || c == 'q' || c == 'r' || c == 's') {
                sb.append('7');
            } else if (c == 't' || c == 'u' || c == 'v') {
                sb.append('8');
            } else if (c == 'w' || c == 'x' || c == 'y' || c == 'z') {
                sb.append('9');
            } else if (Character.isDigit(c)) {
                sb.append(c);
            }
        }

        return encryptPassword(sb.toString());        
    }

    ////////////////////////////////////////////////////////////////////
    //
    //  Encrypt/decrypt the given string using RC4 encryption and a
    //  private key.
    //
    ////////////////////////////////////////////////////////////////////
    public String encryptString(String value) {
        if (key.length == 0 || value == null || value.length() == 0) {
            return "";
        }
        
        return encryptString(checkSum(value.getBytes()), key);
    }

    private String encryptString(byte[] passwordWithChecksum, byte[] key) {
        byte[] b = RC4(passwordWithChecksum, key);
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            int j = b[i] & 0xFF;
            if (j < 16) {
                s.append("0");
            }
            s.append(Integer.toString(j, 16));
        }
    
        return s.toString().toUpperCase();
    }    
    
    public String decryptString(String value) {
        if (value == null || value.length() == 0 || value.length() % 2 != 0 || key.length == 0) {
            return "";
        }

        try {
            byte[] b = new byte[value.length() / 2];
            for (int i = 0; i < b.length; i++) {
                char[] next = new char[2];
                next[0] = value.charAt(i * 2);
                next[1] = value.charAt(i * 2 + 1);

                b[i] = (byte) Integer.parseInt(new String(next), 16);
            }

            byte[] decrypt = RC4(b, key);
            byte[] original_bytes = new byte[decrypt.length - 2];
            for (int i = 0; i < original_bytes.length; i++) {
                original_bytes[i] = decrypt[i + 2];
            }

            byte[] checksum = checkSum(original_bytes);
            if (checksum[0] != decrypt[0] || checksum[1] != decrypt[1]) {
                return "";
            }

            return new String(original_bytes);
        } catch (Exception e) {
            return "";
        }
    }

    ////////////////////////////////////////////////////////////////////
    //
    //  RC4 encryption algorithm
    //
    ////////////////////////////////////////////////////////////////////
    private byte[] RC4(byte[] in, byte[] user_key) {

        //  Use the key to setup the state array

        int[] sBox = new int[256];
        for (int i = 0; i < sBox.length; i++) {
            sBox[i] = i;
        }

        int i1 = 0, i2 = 0;
        for (int i = 0; i < sBox.length; i++) {
            i2 = ((user_key[i1] & 0xFF) + sBox[i] + i2) & 0xFF;

            int t = sBox[i];
            sBox[i] = sBox[i2];
            sBox[i2] = t;

            i1 = (i1 + 1) % user_key.length;
        }

        //  Use the state array to encrypt each byte of the caller's data

        int x = 0, y = 0;
        byte[] out = new byte[in.length];
        for (int i = 0; i < out.length; i++) {
            x = (x + 1) & 0xFF;
            y = (sBox[x] + y) & 0xFF;

            int t = sBox[x];
            sBox[x] = sBox[y];
            sBox[y] = t;

            out[i] = (byte) (in[i] ^ sBox[(sBox[x] + sBox[y]) & 0xFF]);
        }

        return out;
    }
    
    /*
     * Is the passed string an encrypted value
     */
    public boolean isEncrypted(String value) {
        if (value == null || value.length() == 0) {
            return true;
        }
        // Decrypt the passed string and then re-encrypt.
        // If result matches passed string then it's already encrypted
    	return encryptString(decryptString(value)).equals(value);
    }
    
    /*
     * Encrypt portal passsword. Adapted from encryption logic in portal (ActiveNetWeb).
     */
    public String encryptPortalPassword(String inputPassword) {
        
        String encryptedPassword = "";
        if (inputPassword == null || inputPassword.length() == 0) {
            return encryptedPassword;
        }
        
        // Reset encryption key
        setKey("");

        // Compute the 16bit checksum for the input password
        // Compute the high order byte
        byte[] checksum = new byte[2];
        byte[] password = inputPassword.getBytes();
        for (int i = 0; i < password.length; i=i+2) {
            checksum[0] ^= password[i];
        }
        // Compute the high order byte
        for (int i = 1; i < password.length; i=i+2) {
            checksum[1] ^= password[i];
        }

        // Concatenate the checksum and the password
        byte[] passwordWithChecksum = addAll(checksum, password);
        
        // Encrypt the value (use encryptString instead of encryptPassword for compatability with portal logic).
        encryptedPassword = encryptString(passwordWithChecksum, key);

        // Return encrypted password
        return encryptedPassword;
        
    }
    
    public static byte[] addAll(byte[] array1, byte[] array2) {
      if (array1 == null)
        return clone(array2);
      if (array2 == null) {
        return clone(array1);
      }
      byte[] joinedArray = new byte[array1.length + array2.length];
      System.arraycopy(array1, 0, joinedArray, 0, array1.length);
      System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
      return joinedArray;
    }
    
    public static byte[] clone(byte[] array) {
      if (array == null) {
        return null;
      }
      return (byte[])array.clone();
    }
}

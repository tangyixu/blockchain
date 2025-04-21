package edu.grinnell.csc207.blockchain;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A wrapper class over a hash value (a byte array).
 */
public class Hash {

    private byte[] dataVal;

    /**
     * Calculate the hash.
     *
     * @param numB
     * @param amountTrans
     * @param prevHash
     * @param nonce
     * @return a bye[] as the hash.
     * @throws NoSuchAlgorithmException
     */
    public static byte[] calculateHash(int numB, int amountTrans,
            Hash prevHash, long nonce) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");
        md.update(ByteBuffer.allocate(4).putInt(numB).array());
        md.update(ByteBuffer.allocate(4).putInt(amountTrans).array());
        if (prevHash != null) {
            md.update(prevHash.getData());
        }
        md.update(ByteBuffer.allocate(8).putLong(nonce).array());
        byte[] hash = md.digest();
        return hash;
    }

    /**
     * Constructs a new Hash object that contains the given hash (as an array of
     * bytes).
     *
     * @param data
     */
    public Hash(byte[] data) {
        this.dataVal = data;
    }

    /**
     * Returns the hash contained in this object.
     *
     * @return a byte array as the hash.
     */
    public byte[] getData() {
        return this.dataVal;
    }

    /**
     * Returns true if this hash meets the criteria for validity.
     *
     * @return a boolean, true if its first three indices contain zeroes,
     * otherwise false.
     */
    public boolean isValid() {
        for (int n = 0; n < 3; n++) {
            if (Byte.toUnsignedInt(this.dataVal[n]) != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the string representation of the hash. as a string of hexadecimal
     * digits, 2 digits per byte.
     *
     * @return a string form of the hash.
     */
    @Override
    public String toString() {
        String output = "";
        for (int n = 0; n < this.dataVal.length; n++) {
            output += String.format("%02X", Byte.toUnsignedInt(this.dataVal[n]));
        }
        return output;
    }

    /**
     * Returns true if this hash is structurally equal to the argument.
     *
     * @param other
     * @return a boolean
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Hash o) {
            return Arrays.equals(o.getData(), this.dataVal);
        }
        return false;
    }
}

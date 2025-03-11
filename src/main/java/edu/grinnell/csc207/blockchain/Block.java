package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;

/**
 * A single block of a blockchain.
 */
public class Block {

    private int numBlock;
    private int amountTransfer;
    private Hash prevHash;
    private long nonce;
    private Hash hash;

    /**
     * Do mining to find a valid nonce for the block.
     *
     * @param prevHash
     * @return a valid nonce.
     * @throws java.security.NoSuchAlgorithmException
     */
    private long mining(Hash prevHash) throws NoSuchAlgorithmException {
        long finalNonce = 0;
        Hash test = new Hash(Hash.calculateHash(prevHash.toString() + finalNonce));
        while (true) {
            if (test.isValid()) {
                return finalNonce;
            }
            nonce++;
        }
    }

    /**
     * Creates a new block from the specified parameters. Performing the mining
     * operation to discover the nonce and hash for this block given these
     * parameters.
     *
     * @param num
     * @param amount
     * @param prevHash
     * @throws java.security.NoSuchAlgorithmException
     */
    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        this.numBlock = num;
        this.amountTransfer = amount;
        this.prevHash = prevHash;
        this.nonce = mining(prevHash);
        this.hash = new Hash(Hash.calculateHash(this.prevHash.toString() + nonce));
    }

    /**
     * Creates a new block from the specified parameters without mining.
     *
     * @param num
     * @param amount
     * @param prevHash
     * @param nonce
     */
    public Block(int num, int amount, Hash prevHash, long nonce) {
        this.numBlock = num;
        this.amountTransfer = amount;
        this.prevHash = prevHash;
        this.nonce = nonce;
    }

    /**
     * Returns the number of this block.
     *
     * @return the number of blocks in the chain
     */
    public int getNum() {
        return this.numBlock;
    }

    /**
     * Returns the amount transferred that is recorded in this block.
     *
     * @return the dataTransfer.
     */
    public int getAmount() {
        return this.amountTransfer;
    }

    /**
     * Returns the nonce of this block.
     *
     * @return the nonce.
     */
    public long getNonce() {
        return this.nonce;
    }

    /**
     * Returns the hash of the previous block in the blockchain.
     *
     * @return prevHash.
     */
    public Hash getPrevHash() {
        return this.prevHash;
    }

    /**
     * Returns the hash of this block.
     *
     * @return hash.
     */
    public Hash getHash() {
        return this.hash;
    }

    /**
     * Returns a string representation of the block.
     *
     * @return a string as the representation of the block.
     */
    @Override
    public String toString() {
        return "Block " + this.numBlock + " (Amount: " + this.amountTransfer
                + ", Nonce: " + this.nonce + ", prevHash: "
                + this.prevHash + ", hash: " + this.hash + ")";
    }
}

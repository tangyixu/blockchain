package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;

/**
 * A linked list of hash-consistent blocks representing a ledger of monetary
 * transactions.
 */
public class BlockChain {

    private static class Node<T> {

        public T value;
        public Node<T> next;

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node<Block> first;
    private Node<Block> last;
    private int initialBalance;
    private int currentBalance;

    /**
     * Creates a BlockChain that possess a single block the starts with the
     * given initial amount.
     *
     * @param initial block.
     * @throws java.security.NoSuchAlgorithmException
     */
    public BlockChain(int initial) throws NoSuchAlgorithmException {
        this.first = new Node(new Block(0, initial, null), last);
        this.last = first;
        this.currentBalance += initial;
        this.initialBalance = initial;
    }

    /**
     * Mines a new candidate block to be added to the list.
     *
     * @param amount
     * @return the candidate block.
     * @throws java.security.NoSuchAlgorithmException
     */
    public Block mine(int amount) throws NoSuchAlgorithmException {
        Block candidate = new Block(this.getSize(), amount, this.last.value.getHash());
        return candidate;
    }

    /**
     * Returns the size of the blockchain.
     *
     * @return the size of the blockchain.
     */
    public int getSize() {
        return this.last.value.getNum() + 1;
    }

    /**
     * Adds this block to the list.
     *
     * @param blk
     */
    public void append(Block blk) {
        if (this.currentBalance + blk.getAmount() > 0) {
            this.currentBalance += blk.getAmount();
            if (this.getSize() == 1) {
                this.last = new Node(blk, null);
            } else {
                this.last.next = new Node(blk, null);
                this.last = this.last.next;
            }
        } else {
            throw new IllegalArgumentException("invalid block");
        }
    }

    /**
     * Removes the last block from the chain, returning true.
     *
     * @return true. If the chain only contains a single block, then removeLast
     * does nothing and returns false.
     */
    public boolean removeLast() {
        if (this.getSize() == 1) {
            return false;
        } else {
            this.currentBalance -= this.last.value.getAmount();
            Node<Block> walk = this.first;
            int count = this.getSize() - 2;
            for (int n = 0; n < count; n++) {
                walk = this.first.next;
            }
            this.last = walk;
            this.last.next = null;
        }
        return true;
    }

    /**
     * Returns the hash of the last block in the chain.
     *
     * @return a hash.
     */
    public Hash getHash() {
        return this.last.value.getHash();
    }

    /**
     * Walks the blockchain and ensures that its blocks are consistent and
     * valid.
     *
     * @return true if it is valid, false otherwise.
     */
    public boolean isValidBlockChain() {
        return this.currentBalance > 0;
    }

    /**
     * Prints Alice's and Bob's respective balances in the form Alice: amount,
     * Bob: amount on a single line. e.g., Alice: 300, Bob: 0.
     */
    public void printBalances() {
        int bobBalance = this.currentBalance - this.initialBalance;
        System.out.println("Alice: " + this.currentBalance + ", Bob: " + bobBalance + ".");
    }

    /**
     * Returns a string representation of the BlockChain. which is simply the
     * string representation of each of its blocks, earliest to latest, one per
     * line.
     *
     * @return the blockchain as a string.
     */
    @Override
    public String toString() {
        String result = "";
        Node<Block> pointer = this.first;
        for (int n = 0; n < this.getSize(); n++) {
            result += pointer.toString();
            result += "\n";
            pointer = pointer.next;
        }
        return result;
    }
}

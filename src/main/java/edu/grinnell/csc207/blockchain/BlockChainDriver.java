package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * The main driver for the block chain program.
 */
public class BlockChainDriver {

    /**
     * The main entry point for the program.
     *
     * @param args the command-line arguments
     * @throws java.security.NoSuchAlgorithmException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        BlockChain chain = new BlockChain(Integer.parseInt(args[0]));
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(chain.toString());
            System.out.println("Command?");
            String command = scanner.nextLine();
            switch (command) {
                case "mine":
                    System.out.println("Amount transferred?");
                    int amount = Integer.parseInt(scanner.nextLine());
                    System.out.println("amount = " + amount + ", nonce = "
                            + chain.mine(amount).getNonce());
                    break;
                case "append":
                    System.out.println("Amount transferred?");
                    amount = Integer.parseInt(scanner.nextLine());
                    System.out.println("Nonce?");
                    long nonce = Long.parseLong(scanner.nextLine());
                    Block newB = new Block(chain.getSize(), amount, chain.getHash(), nonce);
                    chain.append(newB);
                    break;
                case "remove":
                    chain.removeLast();
                    break;
                case "check":
                    if (chain.isValidBlockChain()) {
                        System.out.println("Chain is valid");
                    } else {
                        System.out.println("Chain is invalid");
                    }
                    break;
                case "report":
                    chain.printBalances();
                    break;
                case "help":
                    System.out.println("Valid commands:");
                    System.out.println("""
    mine:    discovers the nonce for a given transaction
    append:  appends a new block onto the end of the chain
    remove:  removes the last block from the end of the chain
    check:   checks that the block chain is valid
    report:  reports the balances of Alice and Bob
    help:    prints this list of commands
    quit:    quits the program
                                        """);
                    break;
                case "quit":
                    System.exit(0);
                default:
                    System.out.println("Invalid commands. Try again");
            }
        }

    }
}

package com.danmueller;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayingWithPrimes {
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
//        System.out.println("Hello, World!");
//        System.out.println(args);
//        for (String a : args) {
//            System.out.println(a);
//            checkPrime(Integer.parseInt(a));
//        }
        List<String> menuOptions = new ArrayList<>();
        menuOptions.add("Check if a number is prime.");
        menuOptions.add("Find prime numbers between x and y.");
        menuOptions.add("Check if a number is semiprime.");
        menuOptions.add("Find semiprime numbers between x and y.");

        if (args.length == 0) {
            while (true) {
                for (int i = 0; i < menuOptions.size(); i++) {
                    System.out.println((i + 1) + ". " + menuOptions.get(i));
                }
                System.out.println((menuOptions.size() + 1) + ". Exit.");
                int choice = getPositiveInt("\nWhaddyawant? ", "  Enter a number between 1 and " + (menuOptions.size() + 1));

                switch (choice) {
                    case 1:
                        doCheckPrime();
                        break;
                    case 2:
                        doBuildPrimeList();
                        break;
                    case 3:
                        doCheckSemiPrime();
                        break;
                    case 4:
                        doBuildSemiPrimeList();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("  It's a number between 1 and " + (menuOptions.size() + 1) + "\n");
                }
            }
        } else {
            int min = 1;
            int max = Integer.parseInt(args[0]);
            if (args.length > 1) {
                min = Integer.parseInt(args[0]);
                max = Integer.parseInt(args[1]);
            }
            System.out.println("Checking for primes from " + min + " to " + max);

            List<Integer> primes = buildPrimeList(min, max);
            displayPrimeStats(max, primes);
        }
    //        printList(primes);
    }

    private static int getPositiveInt(String message, String badInputMessage) {
        int returnVal = -1;

        while (true) {
            System.out.print(message);

            try {
                returnVal = Integer.parseInt(scan.nextLine());
                if (returnVal < 1) {
                    System.out.println(badInputMessage);
                } else {
                    return returnVal;
                }
            } catch (NumberFormatException nfe) {
                System.out.println(badInputMessage);
            }
        }
    }



    private static void doCheckPrime() {
        int num = getPositiveInt("Enter a number to see if it's prime: ", "  Please enter a positive integer.");
        checkPrime(num);
    }

    private static void doBuildPrimeList() {
    }

    private static void doCheckSemiPrime() {
    }

    private static void doBuildSemiPrimeList() {
    }

    private static boolean checkPrime(int theNum) {
        if (theNum == 1) {
            System.out.println("1 is not prime because it only has one factor (1).\n");
            return false;
        }
        boolean returnVal = true;
        int divisible = 2;
        if (theNum % 2 != 0) {
            for (int i = 3; i <= Math.sqrt(theNum); i+=2) {
                if (theNum % i == 0) {
                    divisible = i;
                    returnVal = false;
                    break;
                }
            }
        } else {
            returnVal = false;
        }
        if (!returnVal) {
            System.out.println(theNum + " is not prime. (" + divisible + ")\n");
        } else {
            System.out.println("" + theNum + " is prime.\n");
        }

        return returnVal;
    }

    private static List<Integer> buildPrimeListSlow(int min, int max) {
        List<Integer> returnVal = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            if (checkPrime(i)) {
                returnVal.add(i);
            }
        }
        return returnVal;
    }

    private static List<Integer> buildPrimeList(int min, int max) {
        if (min > 2) {
            return buildPrimeListSlow(min, max);
        }
        List<Integer> returnVal = new ArrayList<>();
        returnVal.add(2);
        int largestGap = 0;
        int largestGapIndex = 0;
        for (int i = 3; i <= max; i+=2) {
            boolean prime = false;
            double sqrt = Math.sqrt(i);
            for (int p : returnVal) {
                if (p > sqrt) {
                    prime = true;
                } else if (i % p  == 0) {
                    break;
                }
            }
            if (prime) {
                returnVal.add(i);
                int gap = returnVal.get(returnVal.size() - 1) - returnVal.get(returnVal.size() - 2);
//                if (gap > 50) {
//                    System.out.print(i + " is the " + returnVal.size() + "th prime. It is " + gap + " larger than the previous.");
//                }
                if (gap >= largestGap || gap >= 100) {
                    System.out.print(i + " is the " + returnVal.size() + "th prime. It is " + gap + " larger than the previous.");
                    if (gap > largestGap) {
                        largestGap = gap;
                        largestGapIndex = returnVal.size() - 1;
                        System.out.print(" (LARGEST GAP YET)");
                    }
                    System.out.println();
                }
//                if (gap > 50)
//                    System.out.println();
            }
        }
        System.out.println("\nThe first instance of the largest gap ("
                + largestGap +
                ") was between "
                + returnVal.get(largestGapIndex - 1) +
                " and " + returnVal.get(largestGapIndex));
        return returnVal;
    }

    private static void printList(List list) {
        System.out.println("Printing the primes");
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + "  ");
            if ((i+1) % 10 == 0) System.out.println();
        }
    }

    public static void displayPrimeStats(int maxChecked, List<Integer> primes) {
        int nextPowerOfTen = 10;
        for (int i = 0; i < primes.size(); i++) {
            if (primes.get(i) > nextPowerOfTen) {
                System.out.println("There are " + i + " primes less than " + nextPowerOfTen + " (" + (double)i*100/nextPowerOfTen + "%)");
                nextPowerOfTen *= 10;
            }
        }
        System.out.println("\nThere are " + primes.size() + " primes less than " + maxChecked + " (" + (double)primes.size()*100/maxChecked + "%)");
    }
}
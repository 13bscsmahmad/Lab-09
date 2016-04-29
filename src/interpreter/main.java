package interpreter;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.apache.commons.lang.math.NumberUtils.INTEGER_MINUS_ONE;
import static org.apache.commons.lang.math.NumberUtils.isNumber;

/**
 * Created by MMA on 4/29/2016.
 */
public class main {

    public static HashMap<String, String> variableMap = new HashMap<String, String>();

    public static enum commandType {ASSIGNMENT, EVALUATION, PRINTING}

    ;

    public static void main(String[] args) {

        /*
        *
        * TODO:
        *
        * 1) READ FILE. Take file name as either args or ask...
        * 2) TOKENIZE FILE
        * 3) BASED ON NUMBER OF TOKENS, INTERPRET AND HARDCODE ACTIONS (USING SWITCH STMTS)
        *
        * */

        // read and get file

        ArrayList<ArrayList<String>> file = new ArrayList<ArrayList<String>>();
        try {
            file = readFile(args);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        interpret(file);

    }

    public static ArrayList<ArrayList<String>> readFile(String[] args) throws FileNotFoundException {

        String fileName = "";

        try {
            fileName = args[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            Scanner reader = new Scanner(System.in);  // Reading from System.in
            System.out.println("File name (case sensitive, with ext.): ");
            fileName = reader.nextLine(); // Scans the next token of the input as an int.
        }


        ArrayList<ArrayList<String>> file = new ArrayList<ArrayList<String>>();

        // read file into file variable

        File readFile = new File(fileName);
        Scanner sc = new Scanner(readFile);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();

            // for each line, tokenize items on white spaces, and store them in list.

            ArrayList<String> tokens = new ArrayList<String>();

            String[] tokenArray = line.split(" ");
            for (String token : tokenArray) {
                tokens.add(token);
            }

            file.add(tokens);

            System.out.println(line);
        }
        sc.close();

        return file;
    }

    public static void interpret(ArrayList<ArrayList<String>> file) {

        // For each line, find type of statement, based on number of tokens in a line

        System.out.println("Interpreter supports arithmetic operations for signed integers, and line numbers start from 0.");
        System.out.println();

        for (ArrayList<String> line : file) {

            // check number of tokens

            switch (line.size()) {


                case 2:
                    //

                {
                    // must contain print stmt

                    if (line.get(0).equalsIgnoreCase("print")) {

                        // check if variable is present in map

                        if (variableMap.get((String) line.get(1)) != null) {
                            // present in map


                        } else {

                            // not present in map, so error

                            System.out.println("Variable not defined. Error at line: " + file.indexOf((ArrayList<String>) line));
                            return;


                        }
                    }
                }

                break;

                case 4:

                    /*
                    *  let x = 0 <-- type 1 stmt
                    *
                    *
                    * */

                {
                    if (line.get(0).equalsIgnoreCase("let")) { // type 1 stmt


                        if (isNumber(line.get(1))) {
                            System.out.println("Var can't be a number. Syntax error at line: " + file.indexOf((ArrayList<String>) line));
                            return;
                        }

                        // check if LHS is numeric or an existing variable...

                        if (isNumber(line.get(3))) {
                            // is a number, so store in Hashmap

                            variableMap.put(line.get(1), line.get(3));

                        } else if (variableMap.get(line.get(3)) != null) { // key exists in hashmap

                            String value = variableMap.get((String) line.get(3));
                            variableMap.put(line.get(1), value);

                        } else if (variableMap.get(line.get(3)) == null) {
                            System.out.println("UNINITIALIZED ERROR at line # " + file.indexOf((ArrayList<String>) line) + ": " + line.get(3) + " has not been initialized before!");
                            return;
                        }


                    } else {


                    }
                }

                break;


                case 5:


                    /*
                    *
                    * z = x + y
                    *
                    * */


                    if (variableMap.get(line.get(0)) != null) { // var exists in map

                        // check if left and right operands exists in map from before

                        int leftOperand = Integer.MIN_VALUE;
                        int rightOperand = Integer.MIN_VALUE;

                        if (isNumber(line.get(2))) {
                            leftOperand = Integer.parseInt(line.get(2));
                        } else if (variableMap.get(line.get(2)) != null) {
                            leftOperand = Integer.parseInt(variableMap.get((String) line.get(2)));
                        } else {
                            System.out.println("Variable undefined at line: " + file.indexOf((ArrayList<String>) line));
                        }

                        if (isNumber(line.get(4))) {
                            rightOperand = Integer.parseInt(line.get(4));
                        } else if (variableMap.get(line.get(4)) != null) {
                            rightOperand = Integer.parseInt(variableMap.get((String) line.get(4)));
                        } else {
                            System.out.println("Variable undefined at line: " + file.indexOf((ArrayList<String>) line));
                        }


                        switch (line.get(3)) {

                            case "+":
                                //

                                int sum = leftOperand + rightOperand;
                                // update map

                                variableMap.put(line.get(0), String.valueOf(sum));
                                break;

                            case "-":
                                //

                                int difference = leftOperand - rightOperand;
                                // update map

                                variableMap.put(line.get(0), String.valueOf(difference));
                                break;

                            case "*":
                                //

                                int product = leftOperand * rightOperand;
                                // update map

                                variableMap.put(line.get(0), String.valueOf(product));
                                break;

                            case "/":
                                //

                                int quotient = leftOperand / rightOperand;
                                // update map

                                variableMap.put(line.get(0), String.valueOf(quotient));
                                break;

                        }


                    } else {

                        System.out.println("UNINITIALIZED ERROR at line # " + file.indexOf((ArrayList<String>) line) + ": " + line.get(0) + " has not been initialized before!");

                    }






                    break;

                case 6:

                    // Let z = x + y

                {
                    // check if starts with let

                    if (line.get(0).equalsIgnoreCase("let")) {

                        //line.get(1) can be defined or undefined in map. Doesn't matter.
                        int leftOperand = Integer.MIN_VALUE;
                        int rightOperand = Integer.MIN_VALUE;

                        if (isNumber(line.get(3))) {
                            leftOperand = Integer.parseInt(line.get(3));
                        } else if (variableMap.get(line.get(3)) != null) {
                            leftOperand = Integer.parseInt(variableMap.get((String) line.get(3)));
                        } else {
                            System.out.println("Variable undefined at line: " + file.indexOf((ArrayList<String>) line));
                        }


                        if (isNumber(line.get(5))) {
                            rightOperand = Integer.parseInt(line.get(5));
                        } else if (variableMap.get(line.get(5)) != null) {
                            rightOperand = Integer.parseInt(variableMap.get((String) line.get(5)));
                        } else {
                            System.out.println("Variable undefined at line: " + file.indexOf((ArrayList<String>) line));
                        }

                        switch (line.get(4)) {

                            case "+":
                                //
                                int sum = leftOperand + rightOperand;

                                // update map

                                variableMap.put(line.get(1), String.valueOf(sum));
                                break;

                            case "-":
                                //

                                int difference = leftOperand - rightOperand;
                                variableMap.put(line.get(1), String.valueOf(difference));

                                break;

                            case "*":
                                //

                                int product = leftOperand * rightOperand;

                                variableMap.put(line.get(1), String.valueOf(product));
                                break;

                            case "/":
                                //

                                int quotient = leftOperand / rightOperand;

                                variableMap.put(line.get(1), String.valueOf(quotient));

                                break;

                        }

                    }

                }
                break;


            }

        }


    }

}

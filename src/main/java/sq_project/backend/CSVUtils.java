package sq_project.backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Code to read CSV-Data taken in large parts from https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
 */
public class CSVUtils {

    private static final char DEFAULT_SEPARATOR = ';';
    private static final char DEFAULT_QUOTE = '"';

    /**
     * Parse File with params String path, char separators, char quote and boolean hasHeaders. Return the retVal
     * @param path as String path
     * @param separator as char separator
     * @param quote as char quote
     * @param hasHeader as boolean if it has header
     * @return retVal
     * @throws FileNotFoundException
     */
    public static CSVPackage parseFile(String path, char separator, char quote, boolean hasHeader)
            throws FileNotFoundException {

        CSVPackage retVal = new CSVPackage(hasHeader);
        Scanner scanner = new Scanner(new File(path));
        while (scanner.hasNext()) {
            List<String> line = parseLine(scanner.nextLine());
           retVal.add(line);
        }
        scanner.close();
        return retVal;
    }


    /**
     * Return parseFile
     * @param path as String path
     * @return parseFile
     * @throws FileNotFoundException
     */
    public static CSVPackage parseFile(String path) throws FileNotFoundException {
        return parseFile(path, DEFAULT_SEPARATOR, DEFAULT_QUOTE, true);
    }

    /**
     * Read the firstLines as String path and return a retVal
     * @param path as String path
     * @return retVal
     * @throws FileNotFoundException
     */
    public static List<String> getFirstLines(String path) throws FileNotFoundException{
        Scanner scanner = new Scanner(new File(path));
        List<String> retVal = new ArrayList<>();
        while (scanner.hasNext() && retVal.size() < 2) {
            retVal.add(scanner.nextLine());
        }
        return retVal;
    }

    /**
     * Read String csvLine and return parseLine
     * @param csvLine csvLine
     * @return parseLine
     */
    public static List<String> parseLine(String csvLine) {
        return parseLine(csvLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    /**
     * Read String csvLine and char separators and return parseLine
     * @param csvLine csvLine
     * @param separators seperators
     * @return parseLine
     */
    public static List<String> parseLine(String csvLine, char separators) {
        return parseLine(csvLine, separators, DEFAULT_QUOTE);
    }

    /**
     * Read String csvLine, char separators and char customQuote and return the result
     * @param csvLine csvLine
     * @param separators seperators
     * @param customQuote customQuote
     * @return result
     */
    public static List<String> parseLine(String csvLine, char separators, char customQuote) {

        List<String> result = new ArrayList<>();

        //if empty, return!
        if (csvLine == null && csvLine.isEmpty()) {
            return result;
        }

        if (customQuote == ' ') {
            customQuote = DEFAULT_QUOTE;
        }

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] chars = csvLine.toCharArray();

        for (char ch : chars) {

            if (inQuotes) {
                startCollectChar = true;
                if (ch == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {

                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        curVal.append(ch);
                    }

                }
            } else {
                if (ch == customQuote) {

                    inQuotes = true;

                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && customQuote == '\"') {
                        curVal.append('"');
                    }

                    //double quotes in column will hit this!
                    if (startCollectChar) {
                        curVal.append('"');
                    }

                } else if (ch == separators) {

                    result.add(curVal.toString());

                    curVal = new StringBuffer();
                    startCollectChar = false;

                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }

        }

        result.add(curVal.toString());

        return result;
    }

}
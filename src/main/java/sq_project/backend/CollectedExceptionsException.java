package sq_project.backend;

import java.util.*;

/**
 * An Exception containing a collection of other exceptions occuring during the parsing of a csv-file
 */
public class CollectedExceptionsException extends Exception {
    private Map<Integer, Exception> innerExceptions = new HashMap<Integer, Exception>();

    /**
     * Add new exception to the internal collection
     * @param e new exception
     * @param lineNumber line in which this exception occured
     */
    public void add(Exception e, int lineNumber){
        innerExceptions.put(lineNumber, e);
    }

    /**
     * The Messages of the internal exceptions
     * @return a string containing the messages of the internal exceptions
     */
    @Override
    public String getMessage() {
        String retVal = "Collected Line Errors: ";
        for(Integer i : innerExceptions.keySet()){
            retVal += "\r\nline " + i + ": "+ innerExceptions.get(i).getMessage();
        }
        return retVal;
    }

    /**
     * Are there any internal Exceptions?
     * @return true if there are any internal exceptions
     */
    public boolean isEmpty(){
        return innerExceptions.isEmpty();
    }
}

package sq_project.backend;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.util.*;

public class CollectedExceptionsException extends Exception {
    private Map<Integer, Exception> innerExceptions = new HashMap<Integer, Exception>();

    public void add(Exception e, int lineNumber){
        innerExceptions.put(lineNumber, e);
    }

    @Override
    public String getMessage() {
        String retVal = "Collected Line Errors: ";
        for(Integer i : innerExceptions.keySet()){
            retVal += "\r\nline " + i + ": "+ innerExceptions.get(i).getMessage();
        }
        return retVal;
    }

    public boolean isEmpty(){
        return innerExceptions.isEmpty();
    }
}

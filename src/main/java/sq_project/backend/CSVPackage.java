package sq_project.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVPackage extends ArrayList<List<String>> {
    private static final List<String> DEFAULT_HEADERS = Arrays.asList(
            "Datum der Sprechstunde",
            "Medikamentname",
            "Anzahl der Verschreibungen",
            "Kosten pro Verschreibung");

    private boolean hasHeader;

    /**
     *
     * CTOR
     * @param hasHeader Does the csv have an extra line for headers? if not, DEFAULT_HEADERS will be used
     */
    public CSVPackage(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    /**
     * Returns the headers of this csv, or the default headers, if none are available
     * Can throw a nullpointerexception if empty
     * @return a List of Strings containing the headers
     */
    public List<String> getHeaders() {
        if(this.hasHeader){
            return this.get(0);
        }
        return DEFAULT_HEADERS;
    }

    /**
     * Returns the values at a certain index. Ignores the headerline if present.
     * @param i Index of the values to get
     * @return a List of Strings containing the values at the given index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    @Override
    public List<String> get(int i){
        if(this.isEmpty()) return null;

        // if a header line exists, index is actually index+1 (concerning the wrapped values)
        if(hasHeader){
            i++;
        }
        if(this.size() < i) {
            throw new IndexOutOfBoundsException();
        }
        return this.get(i);
    }

    @Override
    public int size(){
        if(hasHeader) {
            return super.size() - 1;
        }
        return super.size();
    }
}

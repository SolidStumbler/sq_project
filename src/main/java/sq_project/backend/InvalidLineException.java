package sq_project.backend;

import java.util.List;

public class InvalidLineException extends Exception{
    private Exception innerException;
    private String line;

    public InvalidLineException(String line) {
        this.line = line;
    }

    public InvalidLineException(List<String> line) {
        String concat = "";
        for(String l : line){
            concat = concat + l;
        }
        this.line = concat;
    }

    public InvalidLineException(String line, Exception e) {
        this.line = line;
        this.innerException = e;
    }

    public InvalidLineException(List<String> line, Exception e) {
        String concat = "";
        for(String l : line){
            concat = concat + l;
        }
        this.line = concat;
        this.innerException = e;
    }

    public String getLine() {
        return line;
    }

    @Override
    public String getMessage() {
        return "Unable to parse: \"" + getLine()+ "\"";
    }

    public void setLine(String line) {
        this.line = line;
    }
}

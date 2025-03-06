import java.util.List;
public class Parser {
    private List<String> tokens;
    private int index;
    public Parser(List<String> tokens) {
        this.tokens = tokens;
        this.index = 0;
    }
    public String next() {
        return tokens.get(index++);
    }
    public String peek() {
        return tokens.get(index);
    }
    public boolean hasNext() {
        return index < tokens.size();
    }
    public void reset() {
        index = 0;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public List<String> getTokens() {
        return tokens;
    }
    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }
    public void addToken(String token) {
        tokens.add(token);
    }
}

package hello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Joke {

    private int id;
    private String joke;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getJoke() {
        return joke;
    }
    public void setJoke(String joke) {
        this.joke = joke;
    }
    
}

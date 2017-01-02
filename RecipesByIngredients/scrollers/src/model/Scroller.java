package model;

/**
 * Class that represents the scrollers.
 */
public class Scroller  {

    private String name;
    private String developerKey;

    public Scroller() {

    }

    public Scroller(String name, String developerKey) {
        this.name = name;
        this.developerKey = developerKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeveloperKey() {
        return developerKey;
    }

    public void setDeveloperKey(String developerKey) {
        this.developerKey = developerKey;
    }
}

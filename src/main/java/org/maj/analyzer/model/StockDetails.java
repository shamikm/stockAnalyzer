package org.maj.analyzer.model;

/**
 * Created by shamikm78 on 9/16/16.
 */
public class StockDetails {
    private String source;
    private String user;
    private String message;
    private Seniment seniment;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Seniment getSeniment() {
        return seniment;
    }

    public void setSeniment(Seniment seniment) {
        this.seniment = seniment;
    }
}

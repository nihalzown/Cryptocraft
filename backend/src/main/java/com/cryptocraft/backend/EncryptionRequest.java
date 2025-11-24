package com.cryptocraft.backend;

public class EncryptionRequest {
    private String algorithm;
    private String plaintext;
    private String key;

    // Getters and setters
    public String getAlgorithm() {
        return algorithm;
    }
    public void setAlgorithm(String algorithm) {
        this.algorithm=algorithm;
    }

    public String getPlaintext() {
        return plaintext;
    }
    public void setPlaintext(String plaintext) {
        this.plaintext=plaintext;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key=key;
    }
}
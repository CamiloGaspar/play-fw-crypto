package model;

public class CryptographyForm {

    private String text;
    private byte[] publicKey;

    public CryptographyForm(String text, byte[] publicKey) {
        this.text = text;
        this.publicKey = publicKey;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }
}

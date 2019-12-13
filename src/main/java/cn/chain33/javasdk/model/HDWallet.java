package cn.chain33.javasdk.model;

import java.io.Serializable;

/**
 * @author hbj
 * @date 2019/12/13 14:06
 */
public class HDWallet implements Serializable {

    private static final long serialVersionUID = -6431503430451248196L;
    /**
     * 助记词
     */
    private String word;
    /**
     * 路径-标志位
     */
    private String path;
    /**
     * 密码
     */
    private String passphrase;
    /**
     * 私钥
     */
    private String priKey;
    /**
     * 公钥
     */
    private String pubKey;
    /**
     * 地址
     */
    private String address;

    public HDWallet() {
    }

    public HDWallet(String path, String priKey, String pubKey, String address) {
        super();
        this.path = path;
        this.priKey = priKey;
        this.pubKey = pubKey;
        this.address = address;
    }

    public HDWallet(String priKey, String pubKey, String address) {
        super();
        this.priKey = priKey;
        this.pubKey = pubKey;
        this.address = address;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    public String getPriKey() {
        return priKey;
    }

    public void setPriKey(String priKey) {
        this.priKey = priKey;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "HDWallet{" +
                "word='" + word + '\'' +
                ", path='" + path + '\'' +
                ", passphrase='" + passphrase + '\'' +
                ", priKey='" + priKey + '\'' +
                ", pubKey='" + pubKey + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

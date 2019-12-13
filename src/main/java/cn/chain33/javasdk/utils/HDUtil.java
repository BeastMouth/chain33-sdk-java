package cn.chain33.javasdk.utils;

import cn.chain33.javasdk.model.HDWallet;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.*;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hbj
 * @date 2019/12/13 12:05
 */
public class HDUtil {
    private static Logger log = LoggerFactory.getLogger(HDUtil.class);
    static NetworkParameters PARAMS = new MainNetParams();

    public static String generateMnemonic(String passphrase) {
        StringBuilder words = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        long creationTimeSeconds = System.currentTimeMillis() / 1000;
        DeterministicSeed seed = new DeterministicSeed(secureRandom, 128, passphrase, creationTimeSeconds);
        for (String str : seed.getMnemonicCode()) {
            words.append(str).append(" ");
        }
        return words.toString().trim();
    }

    public static List<HDWallet> createHDWalletByPath(String word, String passphrase, int[] childNum) {
        List<HDWallet> wallet = new ArrayList<>();
        try {
            DeterministicSeed deterministicSeed = new DeterministicSeed(word, null, passphrase, 0L);
            DeterministicKeyChain deterministicKeyChain = DeterministicKeyChain.builder().seed(deterministicSeed).build();
            DeterministicKey main = deterministicKeyChain.getKeyByPath(HDUtils.parsePath("44H/0H"), true);
            DeterministicHierarchy tree = new DeterministicHierarchy(main);
            DeterministicKey rootKey = tree.getRootKey();
            log.info("BTY childs privateKey,public,address start");
            for (int i = childNum[0], len = childNum[1]; i < len; i++) {
                DeterministicKey deriveChildKey = HDKeyDerivation.deriveChildKey(rootKey, new ChildNumber(i));
                String path = deriveChildKey.getPathAsString();
                String priKey = deriveChildKey.getPrivateKeyAsHex();
                String pubKey = deriveChildKey.getPublicKeyAsHex();
                String address = ECKey.fromPrivate(deriveChildKey.getPrivKey()).toAddress(PARAMS).toBase58();
                HDWallet hdWallet = new HDWallet(path, priKey, pubKey, address);
                wallet.add(hdWallet);
                log.info("child HDWallet info : path [{}]\nprivateKey [{}]\npublicKey [{}]\naddress [{}]", path, priKey, pubKey, address);
            }
            log.info("BTY childs privateKey,public,address end");
        } catch (UnreadableWalletException e) {
            e.printStackTrace();
        }
        return wallet;
    }

    public static String generateAddress(String publicKey) {
        byte[] publicKeyByte = HexUtil.fromHexString(publicKey);
        return TransactionUtil.genAddress(publicKeyByte);
    }

    public static void main(String[] args) {
//        String mnemonic = generateMnemonic("hbj123456");
        String mnemonic = "weekend message replace tornado jump mail volume apology wheel peasant enemy dog";
        System.out.println("mnemonic is : [" + mnemonic + "]");
        int[] a = {1, 10};
        List<HDWallet> hdWallets = createHDWalletByPath(mnemonic, "hbj123456", a);
        for (HDWallet hdWallet : hdWallets) {
            System.out.println("=========================");
            System.out.println("address : " + hdWallet.getAddress());
            System.out.println("pubKey  : " + hdWallet.getPubKey());
            System.out.println("priKey  : " + hdWallet.getPriKey());
            System.out.println("=========================");
        }
        String hexPublicKey = "0294a118763da4fb7157dad1309732c93bb125a5ad8da00e841abf14cfbffd837d";
        System.out.println("公钥[" + hexPublicKey + "]生成的地址为[" + generateAddress(hexPublicKey) + "]");
    }
}

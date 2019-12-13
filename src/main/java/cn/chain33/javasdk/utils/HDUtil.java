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

    /**
     * 生成助记词
     *
     * @param passphrase 密码
     * @return 生成的助记词
     */
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

    /**
     * 生成分层钱包
     *
     * @param word       助记词
     * @param passphrase 密码
     * @param childNum   生成的分层钱包个数
     * @return 生成分层钱包的结果
     */
    public static List<HDWallet> createHDWalletByPath(String walletPath, String word, String passphrase, int[] childNum) {
        List<HDWallet> wallet = new ArrayList<>();
        try {
            // 保存种子节点到确定性钱包中
            // seed：通过助记词和密码生成
            DeterministicSeed deterministicSeed = new DeterministicSeed(word, null, passphrase, 0L);
            // 通过种子信息生成确定性钥匙链
            DeterministicKeyChain deterministicKeyChain = DeterministicKeyChain.builder().seed(deterministicSeed).build();
            // 通过路径获得相对应的钥匙
            DeterministicKey main = deterministicKeyChain.getKeyByPath(HDUtils.parsePath(walletPath), true);
            DeterministicHierarchy tree = new DeterministicHierarchy(main);
            DeterministicKey rootKey = tree.getRootKey();
            System.out.println("root key : " + rootKey.toString());
            log.info("BTY childs privateKey,public,address start");
            for (int i = childNum[0], len = childNum[1]; i < len; i++) {
                DeterministicKey deriveChildKey = HDKeyDerivation.deriveChildKey(rootKey, new ChildNumber(i));
                System.out.println("path : " + deriveChildKey.getPath());
                String path = deriveChildKey.getPathAsString();
                System.out.println("path as string : " + deriveChildKey.getPathAsString());
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

    /**
     * 通过公钥获取钱包地址
     *
     * @param publicKey 公钥
     * @return 钱包地址
     */
    public static String generateAddress(String publicKey) {
        byte[] publicKeyByte = HexUtil.fromHexString(publicKey);
        return TransactionUtil.genAddress(publicKeyByte);
    }
}

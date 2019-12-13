package cn.chain33.javasdk.client;

import cn.chain33.javasdk.model.HDWallet;
import cn.chain33.javasdk.utils.HDUtil;
import org.junit.Test;

import java.util.List;

/**
 * @author hbj
 * @date 2019/12/13 18:24
 */
public class HDWalletTest {
    @Test
    public void testHDWalletGenerate() {
        String mnemonic = HDUtil.generateMnemonic("hbj123456");
        System.out.println("mnemonic is : [" + mnemonic + "]");
        int[] a = {1, 10};
        List<HDWallet> hdWallets = HDUtil.createHDWalletByPath(mnemonic, "hbj123456", a);
        for (HDWallet hdWallet : hdWallets) {
            System.out.println("=========================");
            System.out.println("address : " + hdWallet.getAddress());
            System.out.println("pubKey  : " + hdWallet.getPubKey());
            System.out.println("priKey  : " + hdWallet.getPriKey());
            System.out.println("=========================");
        }
        String hexPublicKey = "0294a118763da4fb7157dad1309732c93bb125a5ad8da00e841abf14cfbffd837d";
        System.out.println("公钥[" + hexPublicKey + "]生成的地址为[" + HDUtil.generateAddress(hexPublicKey) + "]");
    }
}

package cn.chain33.javasdk.utils;

import com.google.protobuf.ByteString;

import cn.chain33.javasdk.model.enums.StorageEnum;
import cn.chain33.javasdk.model.protobuf.StorageProtobuf;
import cn.chain33.javasdk.model.protobuf.StorageProtobuf.ContentOnlyNotaryStorage.Builder;
import cn.chain33.javasdk.model.protobuf.StorageProtobuf.EncryptNotaryStorage;
import cn.chain33.javasdk.model.protobuf.StorageProtobuf.EncryptShareNotaryStorage;
import cn.chain33.javasdk.model.protobuf.StorageProtobuf.HashOnlyNotaryStorage;
import cn.chain33.javasdk.model.protobuf.StorageProtobuf.LinkNotaryStorage;
import cn.chain33.javasdk.model.protobuf.StorageProtobuf.StorageAction;

public class StorageUtil {
    
    /**
     * 
     * @description 内容存证模型
     * @param content 内容
     * @return  payload
     *
     */
    public static byte[] createOnlyNotaryStorage(byte[] content) {
        Builder contentOnlyNotaryStorageBuilder = StorageProtobuf.ContentOnlyNotaryStorage.newBuilder(); 
        contentOnlyNotaryStorageBuilder.setContent(ByteString.copyFrom(content));//内容小于512k;
        cn.chain33.javasdk.model.protobuf.StorageProtobuf.StorageAction.Builder storageActionBuilder = StorageProtobuf.StorageAction.newBuilder();
        storageActionBuilder.setContentStorage(contentOnlyNotaryStorageBuilder.build());
        storageActionBuilder.setTy(StorageEnum.ContentOnlyNotaryStorage.getTy());
        StorageAction storageAction = storageActionBuilder.build();
        return storageAction.toByteArray();
    }
    
    /**
     * 
     * @description 哈希存证模型，推荐使用sha256哈希，限制256位得摘要值
     * @param hash  长度固定为32字节
     * @return payload
     * 
     */
    public byte[] createHashStorage(byte[] hash) {
        cn.chain33.javasdk.model.protobuf.StorageProtobuf.HashOnlyNotaryStorage.Builder hashStorageBuilder = StorageProtobuf.HashOnlyNotaryStorage.newBuilder();
        hashStorageBuilder.setHash(ByteString.copyFrom(hash));
        HashOnlyNotaryStorage hashOnlyNotaryStorage = hashStorageBuilder.build();
        cn.chain33.javasdk.model.protobuf.StorageProtobuf.StorageAction.Builder storageActionBuilder = StorageProtobuf.StorageAction.newBuilder();
        storageActionBuilder.setHashStorage(hashOnlyNotaryStorage);
        storageActionBuilder.setTy(StorageEnum.HashOnlyNotaryStorage.getTy());
        StorageAction storageAction = storageActionBuilder.build();
        return storageAction.toByteArray();
    }
    
    /**
     * 
     * @description 链接存证模型
     * @param link  存证内容的链接，可以写入URL,或者其他可用于定位源文件得线索.
     * @param hash  源文件得hash值，推荐使用sha256哈希，限制256位得摘要值
     * @return
     *
     */
    public byte[] createLinkNotaryStorage(byte[] link,byte[] hash) {
        cn.chain33.javasdk.model.protobuf.StorageProtobuf.LinkNotaryStorage.Builder storageBuilder = StorageProtobuf.LinkNotaryStorage.newBuilder();
        storageBuilder.setLink(ByteString.copyFrom(link));
        storageBuilder.setHash(ByteString.copyFrom(hash));
        LinkNotaryStorage linkNotaryStorage = storageBuilder.build();
        cn.chain33.javasdk.model.protobuf.StorageProtobuf.StorageAction.Builder storageActionBuilder = StorageProtobuf.StorageAction.newBuilder();
        storageActionBuilder.setLinkStorage(linkNotaryStorage);
        storageActionBuilder.setTy(StorageEnum.LinkNotaryStorage.getTy());
        StorageAction storageAction = storageActionBuilder.build();
        return storageAction.toByteArray();
    }
    
    /**
     * 
     * @description 隐私存证模型，如果一个文件需要存证，且不公开内容，可以选择将源文件通过对称加密算法加密后上链
     * @param encryptContent   存证明文内容的hash值，推荐使用sha256哈希，限制256位得摘要值
     * @param contentHash      源文件的密文，由加密key及nonce对明文加密得到该值。
     * @param nonce            加密iv，通过AES进行加密时制定随机生成的iv,解密时需要使用该值
     * @return payload
     *
     */
    public byte[] createEncryptNotaryStorage(byte[] encryptContent,byte[] contentHash,byte[] nonce) {
        cn.chain33.javasdk.model.protobuf.StorageProtobuf.EncryptNotaryStorage.Builder storageBuilder = StorageProtobuf.EncryptNotaryStorage.newBuilder();
        storageBuilder.setEncryptContent(ByteString.copyFrom(encryptContent));
        storageBuilder.setContentHash(ByteString.copyFrom(contentHash));
        storageBuilder.setNonce(ByteString.copyFrom(nonce));
        EncryptNotaryStorage encryptNotaryStorage = storageBuilder.build();
        cn.chain33.javasdk.model.protobuf.StorageProtobuf.StorageAction.Builder storageActionBuilder = StorageProtobuf.StorageAction.newBuilder();
        storageActionBuilder.setEncryptStorage(encryptNotaryStorage);
        storageActionBuilder.setTy(StorageEnum.EncryptNotaryStorage.getTy());
        StorageAction storageAction = storageActionBuilder.build();
        return storageAction.toByteArray();
    }
    
    /**
     * 
     * @description 分享隐私存证模型
     * @param contentHash   存证明文内容的hash值，推荐使用sha256哈希，限制256位的摘要值
     * @param content       源文件的密文。
     * @param nonce         加密iv，通过AES进行加密时制定随机生成的iv,解密时需要使用该值
     * @param keyName       密钥的kdf推导路径。密钥tree父节点根据该路径可以推导出私钥key
     * @param keyWrap       加密key的wrap key。加密key随机生成，对明文进行加密，该key有私密key进行key wrap后公开。使用时，通过私密key对wrap key解密得到加密key对密文进行解密。
     * @return payload
     *
     */
    public byte[] createEncryptShareNotaryStorage(byte[] contentHash,byte[] content,byte[] nonce,byte[] keyName,byte[] keyWrap) {
        cn.chain33.javasdk.model.protobuf.StorageProtobuf.EncryptShareNotaryStorage.Builder storageBuilder = StorageProtobuf.EncryptShareNotaryStorage.newBuilder();
        storageBuilder.setContentHash(ByteString.copyFrom(contentHash));
        storageBuilder.setEncryptContent(ByteString.copyFrom(content));
        storageBuilder.setKeyName(ByteString.copyFrom(keyName));
        storageBuilder.setNonce(ByteString.copyFrom(nonce));
        storageBuilder.setKeyWrap(ByteString.copyFrom(keyWrap));
        
        EncryptShareNotaryStorage encryptShareNotaryStorage = storageBuilder.build();
        cn.chain33.javasdk.model.protobuf.StorageProtobuf.StorageAction.Builder storageActionBuilder = StorageProtobuf.StorageAction.newBuilder();
        storageActionBuilder.setEncryptShareStorage(encryptShareNotaryStorage);
        storageActionBuilder.setTy(StorageEnum.EncryptShareNotaryStorage.getTy());
        StorageAction storageAction = storageActionBuilder.build();
        return storageAction.toByteArray();
    }
}


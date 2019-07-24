package pro.meisen.boot.ext.shiro.encrypt;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author meisen
 * 2019-07-22
 */
public class MD5HashEncryptor extends SimpleHash {

    private static final String algorithmName = "MD5";

//    public MD5HashEncryptor() {
//        super(algorithmName);
//    }

    private MD5HashEncryptor(Builder builder) {
        super(algorithmName, builder.source, builder.salt, builder.hashIterations);
    }

    public static class Builder {
        // 加密源
        private Object source;
        // 盐
        private Object salt = "";
        // hash次数 默认两次
        private int hashIterations = 2;


        public Builder(Object source) {
            this.source = source;
        }

        public Builder salt(Object salt) {
            this.salt = salt;
            return this;
        }

        public Builder hashIterations(int hashIterations) {
            this.hashIterations = hashIterations;
            return this;
        }

        public MD5HashEncryptor build() {
            return new MD5HashEncryptor(this);
        }


    }

}

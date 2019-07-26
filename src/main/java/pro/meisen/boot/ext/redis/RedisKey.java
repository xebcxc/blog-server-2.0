package pro.meisen.boot.ext.redis;

/**
 * @author meisen
 * 2019-05-23
 */
public enum RedisKey {
   BLOG("blog"),
   BLOG_PAGE("page_blog_");

    private String key;

    RedisKey(String key) {
        this.key = key;
    }

    private ThreadLocal<String> afterKey = new ThreadLocal<>();

    public RedisKey afterKey(String after) {
        afterKey.set(after);
        return this;
    }


    public String getAfterKey() {
        return afterKey.get() == null ? "" : afterKey.get();
    }


    public String getKey() {
        return key + getAfterKey();
    }

}

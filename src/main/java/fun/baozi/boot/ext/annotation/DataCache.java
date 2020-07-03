package fun.baozi.boot.ext.annotation;

import fun.baozi.boot.core.constants.DataCacheType;

import java.lang.annotation.*;

/**
 * @author baozi
 * 2019-07-24
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataCache {

    String key();

    String type() default DataCacheType.SELECT;


}

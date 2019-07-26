package pro.meisen.boot.ext.annotation;

import pro.meisen.boot.core.constants.DataCacheType;

import java.lang.annotation.*;

/**
 * @author meisen
 * 2019-07-24
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataCache {

    String key();

    String type() default DataCacheType.SELECT;


}

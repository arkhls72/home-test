
package com.home.digital.server.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 *Indicates that an annotated class is a "ServiceConfig". Such classes are considered
 * as candidates for auto-detection when using annotation-based configuration and classpath scanning. 
 * It is "an operation offered as an interface that stands alone in the model, with no encapsulated state.
 * This annotation used for startup listener configuration
 *    
 * @author ralu 
 * 
 */
public @interface ServiceConfig {
    public String[] value() default {};
}

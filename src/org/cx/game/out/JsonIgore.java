package org.cx.game.out;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 必须声明在jvm中保留（RetentionPolicy.RUNTIME）
 * @author admin
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonIgore {

}

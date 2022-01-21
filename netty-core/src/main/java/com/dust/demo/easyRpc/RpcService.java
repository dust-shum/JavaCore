package com.dust.demo.easyRpc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author DUST
 * @description 对外暴露服务接口
 * @date 2022/1/21
*/
@Target(ElementType.TYPE) ///接口、类、枚举
@Retention(RetentionPolicy.RUNTIME) //在运行时候可以获取到
public @interface RpcService {
}

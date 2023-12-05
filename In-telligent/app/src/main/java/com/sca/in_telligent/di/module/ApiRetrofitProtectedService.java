package com.sca.in_telligent.di.module;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Marcos Ambrosi on 1/16/19.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiRetrofitProtectedService {
}

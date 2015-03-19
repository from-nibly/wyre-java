package com.fromnibly.util;

import org.jdeferred.Deferred;
import org.jdeferred.impl.DeferredObject;

/**
 * Created by thato_000 on 3/12/2015.
 */
public class PromiseFactory {

    public static <T1, T2, T3> Deferred<T1, T2, T3> create(Deferred<T1, T2, T3> deferred) {
        if (deferred == null) {
            deferred = new DeferredObject<T1, T2, T3>();
        }
        return deferred;
    }
}

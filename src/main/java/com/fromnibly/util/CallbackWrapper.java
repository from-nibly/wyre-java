package com.fromnibly.util;

import com.fromnibly.api.WyreCallback;
import org.jdeferred.Deferred;
import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.ProgressCallback;

/**
 * Created by thato_000 on 3/12/2015.
 */
public class CallbackWrapper {

    public static <T> Deferred<T, Throwable, T> wrap(final WyreCallback<T> callback) {
        Deferred<T, Throwable, T> deferred = PromiseFactory.create(null);

        deferred.done(new DoneCallback<T>() {
            @Override
            public void onDone(T t) {
                callback.callback(t, null, null);
            }
        });

        deferred.fail(new FailCallback<Throwable>() {
            @Override
            public void onFail(Throwable throwable) {
                callback.callback(null, throwable, null);
            }
        });


        deferred.progress(new ProgressCallback<T>() {
            @Override
            public void onProgress(T t) {
                callback.callback(null, null, t);
            }
        });

        return deferred;
    }

}

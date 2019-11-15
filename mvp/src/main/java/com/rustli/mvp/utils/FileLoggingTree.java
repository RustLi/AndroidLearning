package com.rustli.mvp.utils;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import timber.log.Timber;

public class FileLoggingTree extends Timber.Tree{

    private static Logger logger = LoggerFactory.getLogger("logback");
    @Override
    protected void log(int priority, @Nullable String tag, @NotNull String message, @Nullable Throwable t) {
        if (priority == Log.VERBOSE) {
            return;
        }
        if (logger != null){
            String logMessage = tag + ": " + message;
            switch (priority) {
                case Log.DEBUG:
                    logger.debug(logMessage);
                    break;
                case Log.INFO:
                    logger.info(logMessage);
                    break;
                case Log.WARN:
                    logger.warn(logMessage);
                    break;
                case Log.ERROR:
                    logger.error(logMessage);
                    break;
            }
        }
    }
}

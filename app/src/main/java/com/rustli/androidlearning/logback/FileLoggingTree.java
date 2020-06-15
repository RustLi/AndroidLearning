package com.rustli.androidlearning.logback;

import android.util.Log;
import androidx.annotation.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import timber.log.Timber;

public class FileLoggingTree extends Timber.DebugTree {
    private static Logger mLogger = LoggerFactory.getLogger("logback");

//    public static void setLogFileName(String filename){
//        FileAppender appender= (FileAppender) Logger.getRootLogger().getName();//获取FileAppender对象
//        appender.setFile("C:NewData\\src\\main\\日志文件\\"+filename+".txt");//重新设置输出的日志的路径和文件名,动态地修改这个文件
//    }
//
//    public static void confifure() {
//        final String LOG_DIR = Environment.getExternalStorageDirectory() + File.separator + "logback";
//        final String PREFIX = "log";
//        configureLogbackDirectly(LOG_DIR, PREFIX);
//    }
//
//    private static void configureLogbackDirectly(String log_dir, String filePrefix) {
//        // reset the default context (which may already have been initialized)
//        // since we want to reconfigure it
//        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
//        context.reset();
//
//
//        RollingFileAppender<ILoggingEvent> rollingFileAppender = new RollingFileAppender<ILoggingEvent>();
//        rollingFileAppender.setAppend(true);
//        rollingFileAppender.setContext(context);
//
//        // OPTIONAL: Set an active log file (separate from the rollover files).
//        // If rollingPolicy.fileNamePattern already set, you don't need this.
//        //rollingFileAppender.setFile(LOG_DIR + "/log.txt");
//
//        TimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = new TimeBasedRollingPolicy<ILoggingEvent>();
//        rollingPolicy.setFileNamePattern(log_dir + "/" + filePrefix + "_%d{yyyyMMdd}.txt");
//        rollingPolicy.setMaxHistory(7);
//        rollingPolicy.setParent(rollingFileAppender);  // parent and context required!
//        rollingPolicy.setContext(context);
//        rollingPolicy.start();
//
//        rollingFileAppender.setRollingPolicy(rollingPolicy);
//
//        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
//        encoder.setPattern("%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
//        encoder.setContext(context);
//        encoder.start();
//
//        rollingFileAppender.setEncoder(encoder);
//        rollingFileAppender.start();
//
//        LogcatAppender logcatAppender = new LogcatAppender();
//        logcatAppender.setContext(context);
//        logcatAppender.setEncoder(encoder);
//        logcatAppender.setName("logcat1");
//        logcatAppender.start();
//
//        // add the newly created appenders to the root logger;
//        // qualify Logger to disambiguate from org.slf4j.Logger
//        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
//        root.setLevel(Level.TRACE);
//        root.addAppender(rollingFileAppender);
//        root.addAppender(logcatAppender);
//
//        // print any status messages (warnings, etc) encountered in logback config
//        //StatusPrinter.print(context);
//    }

    @Override
    protected void log(int priority, String tag, @NonNull String message, Throwable t) {
        if (priority == Log.VERBOSE) {
            return;
        }
        if (mLogger != null) {
            String logMessage = tag + ": " + message;
            switch (priority) {
                case Log.DEBUG:
                    mLogger.debug(logMessage);
                    break;
                case Log.INFO:
                    mLogger.info(logMessage);
                    break;
                case Log.WARN:
                    mLogger.warn(logMessage);
                    break;
                case Log.ERROR:
                    mLogger.error(logMessage);
                    break;
            }
        }
    }
}

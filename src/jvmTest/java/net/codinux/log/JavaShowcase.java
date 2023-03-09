package net.codinux.log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavaShowcase {

    public void showUsageInJava() {
        LoggerFactory.setLoggerFactory(new ILoggerFactory() {
            @NotNull
            @Override
            public Logger getLogger(@NotNull String name) {
                return new MyFancyLogger(name);
            }
        });
    }

    public class MyFancyLogger extends LoggerBase implements Logger {

        public MyFancyLogger(String loggerName) {
            super(loggerName);
        }

        @Override
        public void log(@NotNull LogLevel level, @NotNull String message, @Nullable Throwable exception) {
            // do your fancy logging here
        }

    }
}

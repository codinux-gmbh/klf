package net.codinux.log;

import net.codinux.log.appender.Appender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;

public class JavaShowcase {

    public void showUsageInJava() {
        LoggerFactory.setLoggerFactory(new ILoggerFactory() {

            @NotNull
            @Override
            public Logger getLogger(@NotNull String name) {
                return new MyFancyLogger(name);
            }

            @NotNull
            @Override
            public Collection<Appender> getAppenders() {
                return Collections.emptyList();
            }

            @Override
            public void addAppender(@NotNull Appender appender) {
                // no-op
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

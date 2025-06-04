package net.codinux.log;

import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;

@TargetClass(value = LoggerFactory.class)
final class Target_net_codinux_log_LoggerFactory {

    // Required as otherwise for all classes for which getLogger(KClass) gets called @RegisterForReflexion would have to be configured.
    // This is the same as Quarkus does for org.slf4j.LoggerFactory.getLogger(Class<?>).
    @Substitute
    public static Logger getLogger(kotlin.reflect.KClass<?> forClass) {
        // calling LoggerFactory.getLogger(forClass) during native image build works as there all reflection information is available
        String loggerName = LoggerNameService.Companion.getDefault().getLoggerName(forClass);

        return LoggerFactory.getLogger(loggerName);
    }

}
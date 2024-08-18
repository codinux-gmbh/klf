package net.codinux.log;

import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;

@TargetClass(value = LoggerFactory.class)
final class Target_net_codinux_log_LoggerFactory {

    // Required as otherwise for all classes for which getLogger(KClass) gets called @RegisterForReflexion would have to be configured.
    // This is the same as Quarkus does for org.slf4j.LoggerFactory.getLogger(Class<?>).
    @Substitute
    public static Logger getLogger(kotlin.reflect.KClass<?> forClass) {
        // unwrapping Companion objects does not work reliably yet, but at least does a primitive approach by removing '.Companion' from end of class name
        String loggerName = JvmDefaults.INSTANCE.getClassNameWithUnwrappingCompanion(forClass);

        return LoggerFactory.getLogger(loggerName);
    }

}
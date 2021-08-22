package com.readonlydev.api.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Getter;
import net.minecraftforge.fml.common.Mod;

public class Log {
    private static final Map<String, Log> LOGGER_BY_MODNAME = new HashMap<>();

    @Getter
    private final Logger logger;
    @Getter
    private final int buildNumber;
    private String lastDebugOutput = "";
    
    public Log(Object modClass, int buildBumber) {
    	this.buildNumber = buildBumber;
    	String name = modClass.getClass().getAnnotation(Mod.class).name();
    	this.logger = LogManager.getLogger(name);
    	LOGGER_BY_MODNAME.put(name, this);
    }
    
    /**
     * Gets the LogHelper for the mod name (<em>not mod ID</em>), if it exists. Holding a reference
     * to the object should be preferred; this method should not be used in most cases.
     *
     * @param modName The mod name (not ID)
     * @return Optional of LogHelper if one has been registered, empty Optional otherwise
     */
    public static Optional<Log> getRegisteredLogger(String modName) {
        if (!LOGGER_BY_MODNAME.containsKey(modName)) return Optional.empty();
        return Optional.of(LOGGER_BY_MODNAME.get(modName));
    }

    public void catching(Throwable t) {
        this.logger.catching(t);
    }

    public void debug(String msg, Object... params) {
        this.logger.debug(msg, params);

        if (this.buildNumber == 0) {
            String newOutput = this.logger.getMessageFactory().newMessage(msg, params).getFormattedMessage();
            if (!newOutput.equals(lastDebugOutput)) {
                info("[DEBUG] " + newOutput);
                this.lastDebugOutput = newOutput;
            }
        }
    }

    public void error(String msg, Object... params) {
        this.logger.error(msg, params);
    }

    public void fatal(String msg, Object... params) {
        this.logger.fatal(msg, params);
    }

    public void info(String msg, Object... params) {
        this.logger.info(msg, params);
    }

    public void log(Level level, String msg, Object... params) {
        this.logger.log(level, msg, params);
    }

    public void trace(String msg, Object... params) {
        this.logger.trace(msg, params);
    }

    public void warn(String msg, Object... params) {
        this.logger.warn(msg, params);
    }

    public void warn(Throwable t, String msg, Object... params) {
        this.logger.warn(msg, params);
        this.logger.catching(t);
    }

    public void noticableWarning(boolean trace, List<String> lines) {
        this.error("********************************************************************************");

        for (final String line : lines) {
            for (final String subline : wrapString(line, 78, false, new ArrayList<>())) {
                this.error("* " + subline);
            }
        }

        if (trace) {
            final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            for (int i = 2; i < 8 && i < stackTrace.length; i++) {
                this.warn("*  at {}{}", stackTrace[i].toString(), i == 7 ? "..." : "");
            }
        }

        this.error("********************************************************************************");
    }

    private static List<String> wrapString(String string, int lnLength, boolean wrapLongWords, List<String> list) {
        final String lines[] = WordUtils.wrap(string, lnLength, null, wrapLongWords).split(SystemUtils.LINE_SEPARATOR);
        Collections.addAll(list, lines);
        return list;
    }
}

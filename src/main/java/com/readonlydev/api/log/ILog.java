package com.readonlydev.api.log;

import java.util.List;

import org.apache.logging.log4j.Level;

public interface ILog {

	public void catching(Throwable t);

	public void debug(String msg, Object... params);

	public void error(String msg, Object... params);

	public void fatal(String msg, Object... params);

	public void info(String msg, Object... params);

	public void log(Level level, String msg, Object... params);

	public void trace(String msg, Object... params);

	public void warn(String msg, Object... params);

	public void warn(Throwable t, String msg, Object... params);

	public void noticableWarning(boolean trace, List<String> lines);

}

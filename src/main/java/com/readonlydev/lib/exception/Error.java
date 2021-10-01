package com.readonlydev.lib.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.text.WordUtils;

import com.readonlydev.lib.Interstellar;
import com.readonlydev.lib.system.Log;

public abstract class Error {

	private final Log logger = Interstellar.log;
	private String TOP = "========================================= ERROR =========================================";
	private String END = "=========================================================================================";

	private String errorName;
	private String[] lines;

	Error(String name, String... lines) {
		this.errorName = name;
		this.lines = lines;
	}

	public void log() {
		this.logger.error(TOP);
		this.logger.error("# [{}]", errorName);
		for (final String line : lines) {
			for (final String subline : wrapString(line, 78, false, new ArrayList<>())) {
				this.logger.error("# " + subline);
			}
		}
		this.logger.error("#");
		this.logger.error("# [StackTrace]");
		final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		for (int i = 2; i < 8 && i < stackTrace.length; i++) {
			this.logger.warn("*  at {}{}", stackTrace[i].toString(), i == 7 ? "..." : "");
		}
		this.logger.error(END);
	}

	private List<String> wrapString(String string, int lnLength, boolean wrapLongWords, List<String> list) {
		final String lines[] = WordUtils.wrap(string, lnLength, null, wrapLongWords).split(SystemUtils.LINE_SEPARATOR);
		Collections.addAll(list, lines);
		return list;
	}

}

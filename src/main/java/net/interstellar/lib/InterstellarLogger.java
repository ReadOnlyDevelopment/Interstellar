/*
 * Library License
 *
 * Copyright (c) 2021 ReadOnly Development
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.interstellar.lib;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class InterstellarLogger {

	private static final Logger LOGGER;

	static {
		LOGGER = LogManager.getLogger("Interstellar");
	}

	public static void trace(String format, Object... data) {

		LOGGER.log(Level.TRACE, format, data);
	}

	public static void info(String format, Object... data) {

		LOGGER.log(Level.INFO, format, data);
	}

	public static void warn(String format, Object... data) {

		LOGGER.log(Level.WARN, format, data);
	}

	public static void error(String format, Object... data) {

		LOGGER.log(Level.ERROR, format, data);
	}

	public static void fatal(String format, Object... data) {

		LOGGER.log(Level.FATAL, format, data);
	}

	public static void catching(Throwable throwable) {
		LOGGER.catching(Level.TRACE, throwable);
	}

	public static void log(Level level, String format, Object... data) {
		LOGGER.log(level, format, data);
	}
}

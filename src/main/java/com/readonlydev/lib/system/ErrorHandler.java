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

package com.readonlydev.lib.system;

import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import com.readonlydev.lib.Interstellar;

public class ErrorHandler implements SubscriberExceptionHandler {

	/** The Exception handler for all events. */
	public static final ErrorHandler instance = new ErrorHandler();

	@Override
	public void handleException(Throwable exception, SubscriberExceptionContext context) {
		Interstellar.log.error(
				"An error occured while processing event : " + context.getEvent().getClass().getSimpleName(),
				exception);
	}
}

package com.readonlydev.api.event;

import net.minecraftforge.fml.common.eventhandler.Event;

@FunctionalInterface
public interface CallEvent {

	boolean post(Event event);

}

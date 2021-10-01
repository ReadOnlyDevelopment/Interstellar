package com.readonlydev.lib.exception;

import com.readonlydev.lib.system.Log;

import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;

public class InvalidFingerprintError {
	private final static String header = "Invalid fingerprint detected!";
	private final static String yellAboutNoSupport = "This version will NOT be supported by the developer!";

	public InvalidFingerprintError(Log log, FMLFingerprintViolationEvent event) {
		log.error(header + event.getSource().getName() + " may have been tampered with. " + yellAboutNoSupport);
	}
}
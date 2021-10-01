package com.readonlydev.lib.base.phase;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;
import com.readonlydev.api.mod.phase.IInitPhase;
import com.readonlydev.api.mod.phase.IPostInitPhase;
import com.readonlydev.api.mod.phase.IPreInitPhase;
import com.readonlydev.api.mod.phase.Step;

public class PhaseSets {

	private final Set<IPreInitPhase> preInitSet = new HashSet<>();
	private final Set<IInitPhase> initSet = new HashSet<>();
	private final Set<IPostInitPhase> postInitSet = new HashSet<>();

	/**
	 * Register a new PreInit listener.
	 *
	 * @param preInitListener The PreInit listener.
	 */
	public void addPreInitListener(IPreInitPhase listner) {
		synchronized (preInitSet) {
			preInitSet.add(listner);
		}
	}

	/**
	 * Register a new PreInit listener.
	 *
	 * @param preInitListener The PreInit listener.
	 */
	public void addInitListener(IInitPhase listner) {
		synchronized (initSet) {
			initSet.add(listner);
		}
	}

	/**
	 * Register a new PreInit listener.
	 *
	 * @param preInitListener The PreInit listener.
	 */
	public void addPostInitListener(IPostInitPhase listner) {
		synchronized (postInitSet) {
			postInitSet.add(listner);
		}
	}

	/**
	 * Get the pre-listeners on a thread-safe way;
	 *
	 * @return A copy of the preinit listeners list.
	 */
	private Set<IPreInitPhase> getSafePreInitListeners() {
		Set<IPreInitPhase> clonedListeners;
		synchronized (preInitSet) {
			clonedListeners = Sets.newHashSet(preInitSet);
		}
		return clonedListeners;
	}

	/**
	 * Get the init-listeners on a thread-safe way;
	 *
	 * @return A copy of the init listeners list.
	 */
	private Set<IInitPhase> getSafeInitListeners() {
		Set<IInitPhase> clonedListeners;
		synchronized (initSet) {
			clonedListeners = Sets.newHashSet(initSet);
		}
		return clonedListeners;
	}

	/**
	 * Get the post-listeners on a thread-safe way;
	 *
	 * @return A copy of the postinit listeners list.
	 */
	private Set<IPostInitPhase> getSafePostInitListeners() {
		Set<IPostInitPhase> clonedListeners;
		synchronized (postInitSet) {
			clonedListeners = Sets.newHashSet(postInitSet);
		}
		return clonedListeners;
	}

	/**
	 * Call the prephease-listeners for the given step.
	 *
	 * @param step The step of initialization.
	 */
	public void callPreInitPhaseListeners(Step step) {
		for (IPreInitPhase listener : getSafePreInitListeners()) {
			listener.call(step);
		}
	}

	/**
	 * Call the initphase-listeners for the given step.
	 *
	 * @param step The step of initialization.
	 */
	public void callInitPhaseListeners(Step step) {
		for (IInitPhase listener : getSafeInitListeners()) {
			listener.call(step);
		}
	}

	/**
	 * Call the postphase-listeners for the given step.
	 *
	 * @param step The step of initialization.
	 */
	public void callPostInitPhaseListeners(Step step) {
		for (IPostInitPhase listener : getSafePostInitListeners()) {
			listener.call(step);
		}
	}
}

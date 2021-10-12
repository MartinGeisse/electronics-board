/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.electronics_board.backend.application.logic.simulator.model.items;

import name.martingeisse.electronics_board.backend.application.logic.simulator.model.Item;
import name.martingeisse.electronics_board.backend.application.logic.simulator.model.ItemBasedSimulationModel;

/**
 *
 */
public final class IntervalItem extends Item {

	private final long initialDelay;
	private final long period;
	private final Runnable callback;

	public IntervalItem(ItemBasedSimulationModel simulationModel, long initialDelay, long period, Runnable callback) {
		super(simulationModel);
		this.initialDelay = initialDelay;
		this.period = period;
		this.callback = callback;
	}

	@Override
	protected void initializeSimulation() {
		fire(this::handle, initialDelay);
	}

	private void handle() {
		callback.run();
		fire(this::handle, period);
	}

}

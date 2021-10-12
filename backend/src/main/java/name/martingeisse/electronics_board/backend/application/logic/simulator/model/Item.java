/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.electronics_board.backend.application.logic.simulator.model;

import name.martingeisse.electronics_board.backend.application.logic.simulator.output.SimulatorOutputEvent;

/**
 *
 */
public abstract class Item {

	private final ItemBasedSimulationModel simulationModel;

	public Item(ItemBasedSimulationModel simulationModel) {
		this.simulationModel = simulationModel;
		simulationModel.register(this);
	}

	public ItemBasedSimulationModel getSimulationModel() {
		return simulationModel;
	}

	protected void initializeSimulation() {
	}

	protected final void fire(Runnable callback, long ticks) {
		simulationModel.fire(callback, ticks);
	}

	protected final void output(SimulatorOutputEvent event) {
		simulationModel.output(event);
	}

}

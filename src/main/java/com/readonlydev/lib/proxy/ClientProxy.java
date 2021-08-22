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

package com.readonlydev.lib.proxy;

import com.readonlydev.lib.guide.command.CommandReloadGuide;
import com.readonlydev.lib.registry.InterstellarRegistry;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends ServerProxy {

	@Override
	public void preInit(InterstellarRegistry registry, FMLPreInitializationEvent event) {
		super.preInit(registry, event);

		registry.clientPreInit(event);
	}

	@Override
	public void init(InterstellarRegistry registry, FMLInitializationEvent event) {
		super.init(registry, event);

		ClientCommandHandler.instance.registerCommand(new CommandReloadGuide());

		registry.clientInit(event);
	}

	@Override
	public void postInit(InterstellarRegistry registry, FMLPostInitializationEvent event) {
		super.postInit(registry, event);

		registry.clientPostInit(event);
	}

}

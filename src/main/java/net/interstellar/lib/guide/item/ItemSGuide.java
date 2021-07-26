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

package net.interstellar.lib.guide.item;

import net.interstellar.api.client.IGuiObjectHandler;
import net.interstellar.api.client.IGuiObjectReference;
import net.interstellar.lib.Interstellar;
import net.interstellar.lib.client.InterstellarGuiHandler;
import net.interstellar.lib.guide.client.GuiGuide;
import net.interstellar.lib.item.ItemInterstellar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemSGuide extends ItemInterstellar {

	private static GuiRegistryID GUIDE_ID;

	public static IGuiObjectReference getGuiObjectReference() {
		if (GUIDE_ID == null) {
			GUIDE_ID = new GuiRegistryID();
		}
		return GUIDE_ID;
	}

	public static class GuiRegistryID implements IGuiObjectReference, IGuiObjectHandler {

		@Override
		public Object getServerElement(EntityPlayer player) {
			return null;
		}

		@Override
		public Object getClientElement(EntityPlayer player) {
			return new GuiGuide(player);
		}
	}

	public ItemSGuide() {
		setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		playerIn.openGui(Interstellar._instance, InterstellarGuiHandler.instance().getGuiID(getGuiObjectReference()), worldIn, playerIn.getPosition().getX(), playerIn.getPosition().getY(), playerIn.getPosition().getZ());
		return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}
}

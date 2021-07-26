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

package net.interstellar.lib.client.gui;

import static net.interstellar.lib.utils.ColorUtil.Color.BLUE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.interstellar.api.client.IGuiData;
import net.interstellar.api.client.IGuiDraw;
import net.interstellar.api.client.IGuiDrawTooltip;
import net.interstellar.api.client.IGuiInput;
import net.interstellar.api.client.IGuiInputEvent;
import net.interstellar.api.client.IGuiUpdate;
import net.interstellar.lib.client.gui.element.SElement;
import net.interstellar.lib.client.gui.element.button.SButtonText;
import net.interstellar.lib.utils.LangUtil;
import net.interstellar.lib.utils.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;

public class InterstellarGuiScreen extends GuiScreen implements IGuiInput, IGuiInputEvent, IGuiDraw, IGuiUpdate, IGuiData, InterstellarGui {

	public int					guiLeft		= 0;
	public int					guiTop		= 0;
	public int					xSize		= 256;
	public int					ySize		= 128;
	protected List<SElement>	elements	= new ArrayList<>();
	private SButtonText			info;
	boolean						init;
	int							mx;
	int							my;
	boolean						leftDown;

	public void addElement(SElement element) {
		element.setGui(this);
		this.elements.add(element);
	}

	public boolean allowResize() {
		return false;
	}

	protected boolean enableInfo() {
		return false;
	}

	protected void addInfo(List<String> toolTips) {
	}

	public InterstellarGuiScreen() {
		this.init = false;
		this.leftDown = false;
		this.mc = Minecraft.getMinecraft();
		this.fontRenderer = this.mc.fontRenderer;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		update();
	}

	@Override
	public void setGuiSize(int w, int h) {
		this.xSize = w;
		this.ySize = h;
	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.guiLeft = (int) Math.floor(this.width / 2.0D) - this.xSize / 2;
		this.guiTop = (int) Math.floor(this.height / 2.0D) - this.ySize / 2;
		if (enableInfo()) {
			this.info = new SButtonText("info", -14, 0, 12, 12, LangUtil.toLoc("gui.interstellar.infosymbol")) {
				@Override
				public boolean isEnabledOutsideOfGui() {
					return true;
				}
			};
			this.info.setBg(SGuiResources.GUI_STYLE_6);
			this.info.setBgDisabled(SGuiResources.GUI_STYLE_6);
			this.info.setBgMo(SGuiResources.GUI_STYLE_6);
			List<String> tt = new ArrayList<>();
			tt.add(TextUtil.colorTextTranslate(BLUE, "gui.interstellar.info"));
			addInfo(tt);
			this.info.set(tt);
			addElement(this.info);
		}
	}

	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		int scrollAmnt = (int) -Math.signum(Mouse.getEventDWheel());
		onScroll(this.mx, this.my, scrollAmnt);
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(this.mx, this.my, mouseButton);
		onClick(this.mx, this.my, mouseButton);
	}

	@Override
	protected void keyTyped(char character, int keyCode) throws IOException {
		super.keyTyped(character, keyCode);
		onKey(character, keyCode);
		if (keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode)) {
			onCloseExecution();
		}
	}

	public void onCloseExecution() {
		this.mc.player.closeScreen();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		this.mx = mouseX;
		this.my = mouseY;
		super.drawScreen(mouseX, mouseY, partialTicks);
		GlStateManager.pushMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawBackground(mouseX, mouseY, partialTicks);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		super.drawScreen(mouseX, mouseY, partialTicks);
		GlStateManager.popMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawForeground(mouseX, mouseY, partialTicks);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void drawBackground(int mx, int my, float partialTicks) {
		for (SElement ele : this.elements) {
			if (ele instanceof IGuiDraw && ((IGuiDraw) ele).isBackgroundVisible()) {
				((IGuiDraw) ele).drawBackground(mx, my, partialTicks);
			}
		}
	}

	@Override
	public void drawForeground(int mx, int my, float partialTicks) {
		for (SElement ele : this.elements) {
			if (ele instanceof IGuiDraw && ((IGuiDraw) ele).isForegroundVisible()) {
				((IGuiDraw) ele).drawForeground(mx, my, partialTicks);
			}
		}
		for (SElement ele : this.elements) {
			if (ele instanceof IGuiDrawTooltip) {
				((IGuiDrawTooltip) ele).draw(mx, my);
			}
		}
	}

	@Override
	public boolean onClick(int mx, int my, int mouseButton) {
		boolean any = false;
		for (SElement ele : this.elements) {
			if (ele instanceof IGuiInput && ((IGuiInput) ele).onClick(mx, my, mouseButton)) {
				clickEvent(ele, mx, my, mouseButton);
				any = true;
			}
		}
		return any;
	}

	@Override
	public boolean onScroll(int mx, int my, int scroll) {
		boolean any = false;
		for (SElement ele : this.elements) {
			if (ele instanceof IGuiInput && ((IGuiInput) ele).onScroll(mx, my, scroll)) {
				scrollEvent(ele, mx, my, scroll);
				any = true;
			}
		}
		return any;
	}

	@Override
	public boolean onKey(char character, int keyCode) {
		boolean any = false;
		for (SElement ele : this.elements) {
			if (ele instanceof IGuiInput && ((IGuiInput) ele).onKey(character, keyCode)) {
				keyEvent(ele, character, keyCode);
				any = true;
			}
		}
		return any;
	}

	@Override
	public boolean isInGUI(int x, int y) {
		return isInBox(x, y, this.guiLeft, this.guiTop, this.width, this.height);
	}

	@Override
	public Gui getGui() {
		return this;
	}

	@Override
	public int getGuiLeft() {
		return this.guiLeft;
	}

	@Override
	public int getGuiTop() {
		return this.guiTop;
	}

	@Override
	public int getGuiSizeX() {
		return this.xSize;
	}

	@Override
	public int getGuiSizeY() {
		return this.ySize;
	}

	@Override
	public FontRenderer getFontRenderer() {
		return this.fontRenderer;
	}

	@Override
	public void onElementResize(SElement ele) {
	}

	@Override
	public void update() {
		for (SElement ele : this.elements) {
			if (ele instanceof IGuiUpdate) {
				((IGuiUpdate) ele).update();
			}
		}
	}

	@Override
	public void clickEvent(SElement ele, int mx, int my, int mouseButton) {
	}

	@Override
	public void scrollEvent(SElement ele, int mx, int my, int scroll) {
	}

	@Override
	public void keyEvent(SElement ele, char character, int keyCode) {
	}

	@Override
	public void drawHoverText(List<String> list, int x, int y) {
		drawHoveringText(list, x, y);
	}

	@Override
	public void read(NBTTagCompound tag) {
		for (SElement ele : this.elements) {
			if (ele instanceof IGuiData) {
				ele.read(tag);
			}
		}
	}

	@Override
	public NBTTagCompound write(NBTTagCompound tag) {
		for (SElement ele : this.elements) {
			if (ele instanceof IGuiData) {
				ele.write(tag);
			}
		}
		return tag;
	}
}

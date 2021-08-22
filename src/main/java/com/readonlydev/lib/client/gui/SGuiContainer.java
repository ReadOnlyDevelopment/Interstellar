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

package com.readonlydev.lib.client.gui;

import static com.readonlydev.lib.utils.ColorUtil.Color.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.readonlydev.api.client.IGuiDraw;
import com.readonlydev.api.client.IGuiDrawTooltip;
import com.readonlydev.api.client.IGuiInput;
import com.readonlydev.api.client.IGuiInputEvent;
import com.readonlydev.api.client.IGuiUpdate;
import com.readonlydev.api.mod.IIDProvider;
import com.readonlydev.lib.client.InterstellarGuiHandler;
import com.readonlydev.lib.client.gui.element.SElement;
import com.readonlydev.lib.client.gui.element.button.SButtonText;
import com.readonlydev.lib.guide.client.GuiGuide;
import com.readonlydev.lib.inventory.InterstellarContainer;
import com.readonlydev.lib.utils.ColorUtil;
import com.readonlydev.lib.utils.LangUtil;
import com.readonlydev.lib.utils.TextUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StringUtils;

public abstract class SGuiContainer extends GuiContainer implements IGuiInput, IGuiInputEvent, IGuiDraw, IGuiUpdate, InterstellarGui, IIDProvider {

	public int					guiLeftT	= 0;
	public int					guiTopT		= 0;
	public int					xSizeT		= 256;
	public int					ySizeT		= 128;
	protected List<SElement>	elements	= new ArrayList<>();
	private SButtonText			info;
	private SButtonText			jei;
	protected EntityPlayer		player;
	boolean						init;
	private int					blmx;
	private int					blmy;

	public SGuiContainer(EntityPlayer player, InterstellarContainer inventorySlotsIn) {
		super(inventorySlotsIn);
		this.init = false;
		this.blmx = 0;
		this.blmy = 0;
		this.player = player;
	}

	protected boolean enableInfo() {
		return true;
	}

	protected boolean enableJEIButton() {
		return false;
	}

	protected abstract void addInfo(List<String> paramList);

	public void addElement(SElement ele) {
		ele.setGui(this);
		this.elements.add(ele);
	}

	@Override
	public void setGuiSize(int w, int h) {
		this.xSizeT = w;
		this.ySizeT = h;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		update();
	}

	@Override
	public void initGui() {
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		this.guiLeftT = (int) Math.floor(this.width / 2.0D) - this.xSizeT / 2;
		this.guiTopT = (int) Math.floor(this.height / 2.0D) - this.ySizeT / 2;
		this.guiLeft = (int) Math.floor(this.width / 2.0D) - this.xSizeT / 2;
		this.guiTop = (int) Math.floor(this.height / 2.0D) - this.ySizeT / 2;
		if (enableInfo()) {
			this.info = new SButtonText("info", -14, 0, 12, 12, LangUtil.toLoc("gui.infosymbol")) {
				@Override
				public boolean isEnabledOutsideOfGui() {
					return true;
				}
			};
			this.info.setBg(SGuiResources.GUI_STYLE_6);
			this.info.setBgDisabled(SGuiResources.GUI_STYLE_6);
			this.info.setBgMo(SGuiResources.GUI_STYLE_6);
			List<String> tt = new ArrayList<>();
			tt.add(TextUtil.colorTextTranslate(BLUE, "gui.info"));
			tt.add(TextUtil.colorTextTranslate(AQUA, "gui.clickhere"));
			addInfo(tt);
			this.info.set(tt);
			addElement(this.info);
		}
		if (enableJEIButton()) {
			this.jei = new SButtonText("jei", -14, 14, 12, 12, LangUtil.toLoc("gui.jeisymbol")) {
				@Override
				public boolean isEnabledOutsideOfGui() {
					return true;
				}
			};
			this.jei.setBg(SGuiResources.GUI_STYLE_6);
			this.jei.setBgDisabled(SGuiResources.GUI_STYLE_6);
			this.jei.setBgMo(SGuiResources.GUI_STYLE_6);
			this.jei.getText().setColor(ColorUtil.calcMCColor(0, 128, 1));
			this.jei.getTextMo().setColor(ColorUtil.calcMCColor(0, 158, 1));
			addElement(this.jei);
		}
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		onClick(mouseX, mouseY, mouseButton);
		int scrollAmnt = (int) -Math.signum(Mouse.getEventDWheel());
		onScroll(mouseX, mouseY, scrollAmnt);
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
	public Gui getGui() {
		return this;
	}

	@Override
	public int getGuiLeft() {
		return this.guiLeftT;
	}

	@Override
	public int getGuiTop() {
		return this.guiTopT;
	}

	@Override
	public int getGuiSizeX() {
		return this.xSizeT;
	}

	@Override
	public int getGuiSizeY() {
		return this.ySizeT;
	}

	@Override
	public FontRenderer getFontRenderer() {
		return this.fontRenderer;
	}

	@Override
	public boolean isInGUI(int x, int y) {
		return isInBox(x, y, this.guiLeft, this.guiTop, this.xSize, this.ySize);
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
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawBackground(mouseX, mouseY, partialTicks);
		this.blmx = mouseX;
		this.blmy = mouseY;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		drawForeground(this.blmx, this.blmy, 0.0F);
		super.drawGuiContainerForegroundLayer(this.blmx, this.blmy);
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
		if (this.info.onClick(mx, my, mouseButton)) {
			InterstellarGuiHandler.instance().setLast(this);
			GuiGuide gui = new GuiGuide();
			String guiPage = getGuiPage();
			if (StringUtils.isNullOrEmpty(guiPage)) {
				guiPage = "mainmenu";
			}
			gui.loadFromString(getModId(), guiPage, getYScroll());
			Minecraft.getMinecraft().displayGuiScreen(gui);
		}
		boolean any = false;
		for (SElement ele : this.elements) {
			if (ele instanceof IGuiInput && ((IGuiInput) ele).onClick(mx, my, mouseButton)) {
				clickEvent(ele, mx, my, mouseButton);
			}
		}
		return any;
	}

	public String getGuiPage() {
		return "";
	}

	public int getYScroll() {
		return 0;
	}

	@Override
	public boolean onScroll(int mx, int my, int scroll) {
		boolean any = false;
		for (SElement ele : this.elements) {
			if (ele instanceof IGuiInput) {
				((IGuiInput) ele).onScroll(mx, my, scroll);
				scrollEvent(ele, mx, my, scroll);
			}
		}
		return any;
	}

	@Override
	public boolean onKey(char character, int keyCode) {
		boolean any = false;
		for (SElement ele : this.elements) {
			if (ele instanceof IGuiInput) {
				((IGuiInput) ele).onKey(character, keyCode);
				keyEvent(ele, character, keyCode);
			}
		}
		return any;
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
		int mxn = x - getGuiLeft();
		int myn = y - getGuiTop();
		drawHoveringText(list, mxn, myn);
	}
}

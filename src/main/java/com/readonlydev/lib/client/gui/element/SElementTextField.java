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

package com.readonlydev.lib.client.gui.element;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.readonlydev.api.client.IGuiDraw;
import com.readonlydev.api.client.IGuiInput;
import com.readonlydev.api.client.IGuiUpdate;
import com.readonlydev.lib.client.gui.SGuiResources;
import com.readonlydev.lib.client.lib.SizableBox;
import com.readonlydev.lib.client.lib.TexUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.math.MathHelper;

public class SElementTextField extends SElement implements IGuiUpdate, IGuiDraw, IGuiInput {

	private String text = "";

	private int maxStringLength = 32;

	private int cursorCounter;

	private boolean enableBackgroundDrawing = true;

	private boolean canLoseFocus = true;

	private boolean isFocused;

	private boolean isEnabled = true;

	private int lineScrollOffset;

	private int cursorPosition;

	private int selectionEnd;

	private int enabledColor = 14737632;

	private int disabledColor = 7368816;

	private boolean visible = true;

	private Predicate<String> validator = Predicates.alwaysTrue();

	private SizableBox bg = SGuiResources.S_CC_WHITE;

	private float r = 0.0F, g = 0.0F, b = 0.0F;

	public SElementTextField(String elementID, int x, int y, int par5Width, int par6Height) {
		super(elementID);
		this.xPosOffset = x;
		this.yPosOffset = y;
		this.xSize = par5Width;
		this.ySize = par6Height;
	}

	@Override
	public void read(NBTTagCompound nbt) {
	}

	@Override
	public NBTTagCompound write(NBTTagCompound nbt) {
		return null;
	}

	@Override
	public void init() {
	}

	public void setBg(SizableBox bg) {
		this.bg = bg;
	}

	public void setBgColor(int r, int g, int b) {
		this.r = r / 255.0F;
		this.g = g / 255.0F;
		this.b = b / 255.0F;
	}

	@Override
	public void update() {
		this.cursorCounter++;
	}

	@Override
	public void drawBackground(int mx, int my, float partialTicks) {
		GlStateManager.color(this.r, this.g, this.b, 1.0F);
		TexUtils.renderSizableBox(this.bg, this.gui, getXPosActual(), getYPosActual(), getXSize(), getYSize());
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawTextBox();
	}

	@Override
	public void drawForeground(int mx, int my, float partialTicks) {
	}

	@Override
	public boolean onClick(int mx, int my, int mouseButton) {
		clickEvent(mx, my, mouseButton);
		return false;
	}

	@Override
	public boolean onScroll(int mx, int my, int scroll) {
		return false;
	}

	@Override
	public boolean onKey(char c, int keyCode) {
		textboxKeyTyped(c, keyCode);
		return false;
	}

	public void setText(String textIn) {
		if (this.validator.apply(textIn)) {
			if (textIn.length() > this.maxStringLength) {
				this.text = textIn.substring(0, this.maxStringLength);
			} else {
				this.text = textIn;
			}
			setCursorPositionEnd();
		}
	}

	public String getText() {
		return this.text;
	}

	public String getSelectedText() {
		int i = (this.cursorPosition < this.selectionEnd) ? this.cursorPosition : this.selectionEnd;
		int j = (this.cursorPosition < this.selectionEnd) ? this.selectionEnd : this.cursorPosition;
		return this.text.substring(i, j);
	}

	public void setValidator(Predicate<String> theValidator) {
		this.validator = theValidator;
	}

	public void writeText(String textToWrite) {
		int l;
		String s = "";
		String s1 = ChatAllowedCharacters.filterAllowedCharacters(textToWrite);
		int i = (this.cursorPosition < this.selectionEnd) ? this.cursorPosition : this.selectionEnd;
		int j = (this.cursorPosition < this.selectionEnd) ? this.selectionEnd : this.cursorPosition;
		int k = this.maxStringLength - this.text.length() - i - j;
		if (!this.text.isEmpty()) {
			s = s + this.text.substring(0, i);
		}
		if (k < s1.length()) {
			s = s + s1.substring(0, k);
			l = k;
		} else {
			s = s + s1;
			l = s1.length();
		}
		if (!this.text.isEmpty() && j < this.text.length()) {
			s = s + this.text.substring(j);
		}
		if (this.validator.apply(s)) {
			this.text = s;
			moveCursorBy(i - this.selectionEnd + l);
		}
	}

	public void deleteWords(int num) {
		if (!this.text.isEmpty()) {
			if (this.selectionEnd != this.cursorPosition) {
				writeText("");
			} else {
				deleteFromCursor(getNthWordFromCursor(num) - this.cursorPosition);
			}
		}
	}

	public void deleteFromCursor(int num) {
		if (!this.text.isEmpty()) {
			if (this.selectionEnd != this.cursorPosition) {
				writeText("");
			} else {
				boolean flag = (num < 0);
				int i = flag ? (this.cursorPosition + num) : this.cursorPosition;
				int j = flag ? this.cursorPosition : (this.cursorPosition + num);
				String s = "";
				if (i >= 0) {
					s = this.text.substring(0, i);
				}
				if (j < this.text.length()) {
					s = s + this.text.substring(j);
				}
				if (this.validator.apply(s)) {
					this.text = s;
					if (flag) {
						moveCursorBy(num);
					}
				}
			}
		}
	}

	public int getNthWordFromCursor(int numWords) {
		return getNthWordFromPos(numWords, getCursorPosition());
	}

	public int getNthWordFromPos(int n, int pos) {
		return getNthWordFromPosWS(n, pos, true);
	}

	public int getNthWordFromPosWS(int n, int pos, boolean skipWs) {
		int i = pos;
		boolean flag = (n < 0);
		int j = Math.abs(n);
		for (int k = 0; k < j; k++) {
			if (!flag) {
				int l = this.text.length();
				i = this.text.indexOf(' ', i);
				if (i == -1) {
					i = l;
				} else {
					while (skipWs && i < l && this.text.charAt(i) == ' ') {
						i++;
					}
				}
			} else {
				while (skipWs && i > 0 && this.text.charAt(i - 1) == ' ') {
					i--;
				}
				while (i > 0 && this.text.charAt(i - 1) != ' ') {
					i--;
				}
			}
		}
		return i;
	}

	public void moveCursorBy(int num) {
		setCursorPosition(this.selectionEnd + num);
	}

	public void setCursorPosition(int pos) {
		this.cursorPosition = pos;
		int i = this.text.length();
		this.cursorPosition = MathHelper.clamp(this.cursorPosition, 0, i);
		setSelectionPos(this.cursorPosition);
	}

	public void setCursorPositionZero() {
		setCursorPosition(0);
	}

	public void setCursorPositionEnd() {
		setCursorPosition(this.text.length());
	}

	public boolean textboxKeyTyped(char typedChar, int keyCode) {
		if (!this.isFocused) {
			return false;
		}
		if (GuiScreen.isKeyComboCtrlA(keyCode)) {
			setCursorPositionEnd();
			setSelectionPos(0);
			return true;
		}
		if (GuiScreen.isKeyComboCtrlC(keyCode)) {
			GuiScreen.setClipboardString(getSelectedText());
			return true;
		}
		if (GuiScreen.isKeyComboCtrlV(keyCode)) {
			if (this.isEnabled) {
				writeText(GuiScreen.getClipboardString());
			}
			return true;
		}
		if (GuiScreen.isKeyComboCtrlX(keyCode)) {
			GuiScreen.setClipboardString(getSelectedText());
			if (this.isEnabled) {
				writeText("");
			}
			return true;
		}
		switch (keyCode) {
			case 14:
				if (GuiScreen.isCtrlKeyDown()) {
					if (this.isEnabled) {
						deleteWords(-1);
					}
				} else if (this.isEnabled) {
					deleteFromCursor(-1);
				}
				return true;
			case 199:
				if (GuiScreen.isShiftKeyDown()) {
					setSelectionPos(0);
				} else {
					setCursorPositionZero();
				}
				return true;
			case 203:
				if (GuiScreen.isShiftKeyDown()) {
					if (GuiScreen.isCtrlKeyDown()) {
						setSelectionPos(getNthWordFromPos(-1, getSelectionEnd()));
					} else {
						setSelectionPos(getSelectionEnd() - 1);
					}
				} else if (GuiScreen.isCtrlKeyDown()) {
					setCursorPosition(getNthWordFromCursor(-1));
				} else {
					moveCursorBy(-1);
				}
				return true;
			case 205:
				if (GuiScreen.isShiftKeyDown()) {
					if (GuiScreen.isCtrlKeyDown()) {
						setSelectionPos(getNthWordFromPos(1, getSelectionEnd()));
					} else {
						setSelectionPos(getSelectionEnd() + 1);
					}
				} else if (GuiScreen.isCtrlKeyDown()) {
					setCursorPosition(getNthWordFromCursor(1));
				} else {
					moveCursorBy(1);
				}
				return true;
			case 207:
				if (GuiScreen.isShiftKeyDown()) {
					setSelectionPos(this.text.length());
				} else {
					setCursorPositionEnd();
				}
				return true;
			case 211:
				if (GuiScreen.isCtrlKeyDown()) {
					if (this.isEnabled) {
						deleteWords(1);
					}
				} else if (this.isEnabled) {
					deleteFromCursor(1);
				}
				return true;
		}
		if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
			if (this.isEnabled) {
				writeText(Character.toString(typedChar));
			}
			return true;
		}
		return false;
	}

	public boolean clickEvent(int mouseX, int mouseY, int mouseButton) {
		boolean flag = (mouseX >= getXPosActual() && mouseX < getXPosActualLargest() && mouseY >= getYPosActual() && mouseY < getYPosActualLargest());
		if (this.canLoseFocus) {
			setFocused(flag);
		}
		if (this.isFocused && flag && mouseButton == 0) {
			int i = mouseX - getXPosActual();
			String s = this.gui.getFontRenderer().trimStringToWidth(this.text.substring(this.lineScrollOffset), getXSize());
			setCursorPosition(this.gui.getFontRenderer().trimStringToWidth(s, i).length() + this.lineScrollOffset);
			return true;
		}
		return false;
	}

	public void drawTextBox() {
		if (getVisible()) {
			int i = this.isEnabled ? this.enabledColor : this.disabledColor;
			int j = this.cursorPosition - this.lineScrollOffset;
			int k = this.selectionEnd - this.lineScrollOffset;
			String s = this.gui.getFontRenderer().trimStringToWidth(this.text.substring(this.lineScrollOffset), getXSize());
			boolean flag = (j >= 0 && j <= s.length());
			boolean flag1 = (this.isFocused && this.cursorCounter / 6 % 2 == 0 && flag);
			int l = this.enableBackgroundDrawing ? (getXPosActual() + 2) : getXPosActual();
			int i1 = this.enableBackgroundDrawing ? (getYPosActual() + (this.ySize - 8) / 2) : getYPosActual();
			int j1 = l;
			if (k > s.length()) {
				k = s.length();
			}
			if (!s.isEmpty()) {
				String s1 = flag ? s.substring(0, j) : s;
				j1 = this.gui.getFontRenderer().drawStringWithShadow(s1, l, i1, i);
			}
			boolean flag2 = (this.cursorPosition < this.text.length() || this.text.length() >= getMaxStringLength());
			int k1 = j1;
			if (!flag) {
				k1 = (j > 0) ? (l + this.xSize) : l;
			} else if (flag2) {
				k1 = j1 - 1;
				j1--;
			}
			if (!s.isEmpty() && flag && j < s.length()) {
				j1 = this.gui.getFontRenderer().drawStringWithShadow(s.substring(j), j1, i1, i);
			}
			if (flag1) {
				if (flag2) {
					Gui.drawRect(k1, i1 - 1, k1 + 1, i1 + 1 + (this.gui.getFontRenderer()).FONT_HEIGHT, -3092272);
				} else {
					this.gui.getFontRenderer().drawStringWithShadow("_", k1, i1, i);
				}
			}
			if (k != j) {
				int l1 = l + this.gui.getFontRenderer().getStringWidth(s.substring(0, k));
				drawSelectionBox(k1, i1 - 1, l1 - 1, i1 + 1 + (this.gui.getFontRenderer()).FONT_HEIGHT);
			}
		}
	}

	private void drawSelectionBox(int startX, int startY, int endX, int endY) {
		if (startX < endX) {
			int i = startX;
			startX = endX;
			endX = i;
		}
		if (startY < endY) {
			int j = startY;
			startY = endY;
			endY = j;
		}
		if (endX > getXPosActualLargest()) {
			endX = getXPosActualLargest();
		}
		if (startX > getXPosActualLargest()) {
			startX = getXPosActualLargest();
		}
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		GlStateManager.color(0.0F, 0.0F, 255.0F, 255.0F);
		GlStateManager.disableTexture2D();
		GlStateManager.enableColorLogic();
		GlStateManager.colorLogicOp(GlStateManager.LogicOp.OR_REVERSE);
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
		bufferbuilder.pos(startX, endY, 0.0D).endVertex();
		bufferbuilder.pos(endX, endY, 0.0D).endVertex();
		bufferbuilder.pos(endX, startY, 0.0D).endVertex();
		bufferbuilder.pos(startX, startY, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.disableColorLogic();
		GlStateManager.enableTexture2D();
	}

	public void setMaxStringLength(int length) {
		this.maxStringLength = length;
		if (this.text.length() > length) {
			this.text = this.text.substring(0, length);
		}
	}

	public int getMaxStringLength() {
		return this.maxStringLength;
	}

	public int getCursorPosition() {
		return this.cursorPosition;
	}

	public boolean getEnableBackgroundDrawing() {
		return this.enableBackgroundDrawing;
	}

	public void setEnableBackgroundDrawing(boolean enableBackgroundDrawingIn) {
		this.enableBackgroundDrawing = enableBackgroundDrawingIn;
	}

	public void setTextColor(int color) {
		this.enabledColor = color;
	}

	public void setDisabledTextColour(int color) {
		this.disabledColor = color;
	}

	public void setFocused(boolean isFocusedIn) {
		if (isFocusedIn && !this.isFocused) {
			this.cursorCounter = 0;
		}
		this.isFocused = isFocusedIn;
		if ((Minecraft.getMinecraft()).currentScreen != null) {
			(Minecraft.getMinecraft()).currentScreen.setFocused(isFocusedIn);
		}
	}

	public boolean isFocused() {
		return this.isFocused;
	}

	public void setEnabled(boolean enabled) {
		this.isEnabled = enabled;
	}

	public int getSelectionEnd() {
		return this.selectionEnd;
	}

	public void setSelectionPos(int position) {
		int i = this.text.length();
		if (position > i) {
			position = i;
		}
		if (position < 0) {
			position = 0;
		}
		this.selectionEnd = position;
		if (this.gui.getFontRenderer() != null) {
			if (this.lineScrollOffset > i) {
				this.lineScrollOffset = i;
			}
			int j = getXSize();
			String s = this.gui.getFontRenderer().trimStringToWidth(this.text.substring(this.lineScrollOffset), j);
			int k = s.length() + this.lineScrollOffset;
			if (position == this.lineScrollOffset) {
				this.lineScrollOffset -= this.gui.getFontRenderer().trimStringToWidth(this.text, j, true).length();
			}
			if (position > k) {
				this.lineScrollOffset += position - k;
			} else if (position <= this.lineScrollOffset) {
				this.lineScrollOffset -= this.lineScrollOffset - position;
			}
			this.lineScrollOffset = MathHelper.clamp(this.lineScrollOffset, 0, i);
		}
	}

	public void setCanLoseFocus(boolean canLoseFocusIn) {
		this.canLoseFocus = canLoseFocusIn;
	}

	public boolean getVisible() {
		return this.visible;
	}

	public void setVisible(boolean isVisible) {
		this.visible = isVisible;
	}
}

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

package net.interstellar.lib.guide.client.element;

import java.util.List;

import javax.annotation.Nonnull;

import net.interstellar.lib.client.gui.element.SElement;
import net.interstellar.lib.client.gui.element.SElementIndex;
import net.interstellar.lib.client.gui.element.container.SContainerScissorPane;
import net.interstellar.lib.client.gui.element.container.dynamic.SContFixedXDynY;
import net.interstellar.lib.client.gui.element.container.list.SContListVFixedX;
import net.interstellar.lib.client.gui.element.image.SElementImage;
import net.interstellar.lib.client.lib.GridAlignment;
import net.minecraft.item.ItemStack;

public abstract class Guide extends SContainerScissorPane {

	protected GuideIndex		index;
	int							test	= 0;
	public static final String	TOC		= ".page.toc";

	public Guide(String elementID) {
		super(elementID);
	}

	public void setGuidePageSize(int xSize, int ySize) {
		this.xSize = xSize;
		this.ySize = ySize;
	}

	@Override
	public void preInit() {
		addElement(this.index = new GuideIndex(this, "index"));
		this.index.setXSize(this.xSize);
	}

	public GuideTOCPage getMainMenu() {
		return this.index.pageTOC;
	}

	public boolean home() {
		resetScroll();
		return this.index.home();
	}

	public void next() {
		this.index.next();
		resetScroll();
	}

	public boolean hasNext() {
		return this.index.hasNextIndex();
	}

	public void prev() {
		this.index.prev();
		resetScroll();
	}

	public boolean hasPrev() {
		return this.index.hasPrevIndex();
	}

	public abstract SElementImage getMainMenuLogo();

	public abstract ButtonSElement getButton();

	private static class GuideIndex extends SElementIndex {

		protected Guide					guide;
		protected Guide.GuideTOCPage	pageTOC;
		protected int					pageXSize;

		public GuideIndex(Guide guide, String elementID) {
			super(elementID);
			this.guide = guide;
		}

		@Override
		public boolean setIndex(String indexID) {
			if (hasIndex(indexID)) {
				this.currentIndex = indexID;
				int sy = -getIndex(this.currentIndex).getYSize() + this.guide.getYSize() - 8;
				if (sy < 0) {
					this.guide.setScrollMin(0, sy);
				} else {
					this.guide.setScrollMin(0, 0);
				}
				return true;
			}
			return false;
		}

		@Override
		public void addIndex(SElement index) {
			super.addIndex(index);
		}

		@Override
		public void init() {
			this.pageTOC = new Guide.GuideTOCPage(this.elementID + ".page.toc", this, this.guide.getXSize());
			addIndex(this.pageTOC);
			this.pageTOC.setXSize(this.guide.getXSize());
		}

		@Override
		public void onElementResize(@Nonnull SElement ele) {
			if (ele.getYPosActualLargest() > getYPosActualLargest()) {
				this.ySize = ele.getXPosOffsetLargest();
			}
			this.gui.onElementResize(ele);
		}

		@Override
		public void clickEvent(SElement ele, int mx, int my, int mouseButton) {
		}

		public boolean home() {
			if (isCurrentIndex(this.pageTOC.getID())) {
				return true;
			}
			setIndex(this.pageTOC.getID());
			return false;
		}

		public void next() {
			if (hasNextIndex()) {
				setIndex(getNextIndex());
			}
		}

		public void prev() {
			if (hasPrevIndex()) {
				setIndex(getPrevIndex());
			}
		}
	}

	public static class GuideTOCPage extends SContListVFixedX {

		public Guide.GuideIndex		index;
		public Guide.GuideTOCMenu	menu;

		public GuideTOCPage(String eleID, Guide.GuideIndex index, int xSize) {
			super(eleID, xSize);
			this.index = index;
		}

		@Override
		protected void preInit() {
			setXPosOffset(4);
			setYPosOffset(4);
		}

		@Override
		public void addElements() {
			addElement(this.index.guide.getMainMenuLogo());
			addElement(this.menu = new Guide.GuideTOCMenu(this.elementID + ".menu", this.index, this));
			this.menu.setXSize(this.xSize - 8);
			this.menu.setSpacing(4, 4);
		}

		public void addMenuButton(String address, List<ItemStack> stacks, String loc) {
			this.menu.addMenuButton(address, stacks, loc);
		}

		public void addPage(GuiGuidePage page) {
			this.index.addIndex(page);
		}
	}

	public static class GuideTOCMenu extends SContFixedXDynY {

		public Guide.GuideIndex		index;
		public Guide.GuideTOCPage	menuPage;
		protected GridAlignment		menuGrid;
		protected int				buttonXSize		= 100;
		protected int				buttonYSize		= 20;
		protected int				buttonXSpacing	= 4;
		protected int				buttonYSpacing	= 4;
		private boolean				initMenuButtonSize;

		public GuideTOCMenu(String elementID, Guide.GuideIndex index, Guide.GuideTOCPage page) {
			super(elementID + ".page.toc", index.pageXSize);
			this.initMenuButtonSize = false;
			this.menuPage = page;
			this.index = index;
		}

		public void setSpacing(int xSpacing, int ySpacing) {
			this.buttonXSpacing = xSpacing;
			this.buttonYSpacing = ySpacing;
		}

		@Override
		public void addElements() {
		}

		public void addMenuButton(String address, List<ItemStack> stack, String text) {
			if (!this.initMenuButtonSize) {
				this.initMenuButtonSize = true;
				this.buttonXSize = (this.xSize - 4) / 2;
				this.menuGrid = new GridAlignment(this.buttonXSize, this.buttonYSize, this.buttonXSpacing, this.buttonYSpacing, 2, GridAlignment.Alignment.VERTICAL);
			}
			ButtonMain n = new ButtonMain(address, this.menuGrid.getCurrentXOffset(), this.menuGrid.getCurrentYOffset() + 4, this.buttonXSize, this.buttonYSize, stack, text);
			this.menuGrid.next();
			addElement(n);
			onElementResize(n);
		}

		@Override
		public void clickEvent(SElement ele, int mx, int my, int mouseButton) {
			if (mouseButton == 0) {
				this.index.setIndex(ele.getID());
			}
		}

		@Override
		public void scrollEvent(SElement ele, int mx, int my, int scroll) {
		}

		@Override
		public void keyEvent(SElement ele, char character, int keyCode) {
		}
	}
}

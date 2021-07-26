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

package net.interstellar.lib.guide.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.google.common.collect.Sets;

import net.interstellar.lib.Interstellar;
import net.interstellar.lib.InterstellarLogger;
import net.interstellar.lib.client.InterstellarGuiHandler;
import net.interstellar.lib.client.gui.InterstellarGuiScreen;
import net.interstellar.lib.client.gui.SGuiResources;
import net.interstellar.lib.client.gui.element.SElement;
import net.interstellar.lib.client.gui.element.SElementIndex;
import net.interstellar.lib.client.gui.element.button.SButtonIcon;
import net.interstellar.lib.client.gui.element.container.SContainerScissorPane;
import net.interstellar.lib.client.gui.element.sizablebox.SElementGuiSizableBox;
import net.interstellar.lib.client.gui.element.sizablebox.SElementSizablePanel;
import net.interstellar.lib.client.lib.GridAlignment;
import net.interstellar.lib.guide.client.element.ButtonSElement;
import net.interstellar.lib.guide.client.element.Guide;
import net.interstellar.lib.guide.item.ItemSGuide;
import net.interstellar.lib.network.packet.PacketSaveGuide;
import net.interstellar.lib.utils.LangUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;

public class GuiGuide extends InterstellarGuiScreen {

	protected static List<GuideEntry>	GUIDE_ENTRIES	= new ArrayList<>();
	SGuiResources						GR;
	private SButtonIcon					discord;
	private SButtonIcon					curseforge;
	private SButtonIcon					github;
	private SButtonIcon					home;
	private SButtonIcon					next;
	private SButtonIcon					prev;
	private SButtonIcon					up;
	private SButtonIcon					down;
	private SElementIndex				guideIndex;
	private MainMenu					mainMenu;
	private GridAlignment				grid;
	protected EntityPlayer				player;
	Guide								cmg;

	public static void addGuide(GuideEntry guideEntry) {
		if (guideEntry != null) {
			GUIDE_ENTRIES.add(guideEntry);
		}
	}

	public GuiGuide(EntityPlayer player) {
		this();
		this.player = player;
		loadFromHeld();
	}

	public GuiGuide() {
		this.grid = new GridAlignment(120, 56, 4, 4, 3, GridAlignment.Alignment.VERTICAL);
		int guiWidth = 384;
		int guiHeight = 212;
		setGuiSize(guiWidth, guiHeight);
		addElement(new SElementGuiSizableBox("bg", SGuiResources.GUI_STYLE_6));
		addElement(new SElementSizablePanel("screenbg", SGuiResources.S_CC_BLACK, 4, 4, 376, 184));
		int iy = guiHeight - 20;
		addElement(this.discord = new SButtonIcon("discord", 4, iy, 16, 16, SGuiResources.LOGO_DISCORD_WHITE));
		this.discord.setBg(SGuiResources.S_DISCORD_BURPLE);
		this.discord.setIconMo(SGuiResources.LOGO_DISCORD_BLUE);
		this.discord.setBgMo(SGuiResources.S_A_WHITE);
		this.discord.add(LangUtil.toLoc(".guide.button.discord"));
		addElement(this.curseforge = new SButtonIcon("curseforge", 24, iy, 16, 16, SGuiResources.LOGO_CURSEFORGE));
		this.curseforge.setBg(SGuiResources.S_CURSEFORGE_GREEN);
		this.curseforge.setBgMo(SGuiResources.S_CURSEFORGE_ORANGE);
		this.curseforge.setBgDisabled(SGuiResources.S_A_GRAY);
		this.curseforge.add(LangUtil.toLoc("guide.button.curseforge"));
		addElement(this.github = new SButtonIcon("github", 44, iy, 16, 16, SGuiResources.LOGO_GITHUB_WHITE));
		this.github.setBg(SGuiResources.S_A_BLACK);
		this.github.setBgMo(SGuiResources.S_A_WHITE);
		this.github.setIconMo(SGuiResources.LOGO_GITHUB_BLACK);
		this.github.add(LangUtil.toLoc("guide.button.github"));
		int hx = guiWidth / 2 - 8;
		addElement(this.home = new SButtonIcon("home", hx, iy, 16, 16, SGuiResources.S_HOME_WHITE));
		this.home.add(LangUtil.toLoc("gui.guide.home"));
		this.home.setBg(SGuiResources.S_DISCORD_GRAY);
		this.home.setBgMo(SGuiResources.S_A_LIGHT_BLUE);
		int px = hx - 20;
		addElement(this.prev = new SButtonIcon("prev", px, iy, 16, 16, SGuiResources.S_PREV_WHITE));
		this.prev.add(LangUtil.toLoc("gui.guide.prev"));
		this.prev.setBg(SGuiResources.S_DISCORD_GRAY);
		this.prev.setBgMo(SGuiResources.S_A_LIGHT_BLUE);
		int nx = hx + 20;
		addElement(this.next = new SButtonIcon("next", nx, iy, 16, 16, SGuiResources.S_NEXT_WHITE));
		this.next.add(LangUtil.toLoc("gui.guide.next"));
		this.next.setBg(SGuiResources.S_DISCORD_GRAY);
		this.next.setBgMo(SGuiResources.S_A_LIGHT_BLUE);
		addElement(this.guideIndex = new SElementIndex("mainindex") {
			@Override
			public void init() {
			}

			@Override
			public void onElementResize(SElement ele) {
			}
		});
		int xScroll = guiWidth - 4;
		addElement(this.up = new SButtonIcon("up", xScroll - 8, iy, 8, 8, SGuiResources.S_UP_WHITE));
		this.up.add(LangUtil.toLoc("gui.guide.scrollup"));
		this.up.setBg(SGuiResources.S_DISCORD_GRAY);
		this.up.setBgMo(SGuiResources.S_A_LIGHT_BLUE);
		addElement(this.down = new SButtonIcon("down", xScroll - 8, iy + 8, 8, 8, SGuiResources.S_DOWN_WHITE));
		this.down.add(LangUtil.toLoc("gui.guide.scrolldown"));
		this.down.setBg(SGuiResources.S_DISCORD_GRAY);
		this.down.setBgMo(SGuiResources.S_A_LIGHT_BLUE);
		this.guideIndex.addIndex(this.mainMenu = new MainMenu(this, "mainmenu", 376, 184));
		this.guideIndex.setIndex("mainmenu");
		this.mainMenu.setXPosOffset(4);
		this.mainMenu.setYPosOffset(4);
		Collections.sort(GUIDE_ENTRIES);
		for (GuideEntry cl : GUIDE_ENTRIES) {
			Guide mg = cl.getGuide();
			mg.setGuidePageSize(this.mainMenu.getXSize(), this.mainMenu.getYSize());
			addGuide(mg);
		}
	}

	@Override
	public void onCloseExecution() {
		InterstellarGuiHandler.instance().returnToLast();
	}

	public void loadFromHeld() {
		ItemStack held = this.player.getHeldItem(EnumHand.MAIN_HAND);
		if (!(held.getItem() instanceof ItemSGuide)) {
			held = this.player.getHeldItem(EnumHand.OFF_HAND);
		}
		if (!(held.getItem() instanceof ItemSGuide)) {
			return;
		}
		NBTTagCompound nbt = held.getTagCompound();
		if (nbt == null) {
			return;
		}
		NBTTagCompound data = nbt.getCompoundTag("guide_data");
		read(data);
	}

	public void loadFromString(String mod, String page, int yScroll) {
		String nbt = "{mainindex:{x_scroll:0,y_scroll:" + yScroll + ",index:" + mod + "," + mod + ":{index:{index:" + page + "}}}}";
		try {
			NBTTagCompound data = JsonToNBT.getTagFromJson(nbt);
			read(data);
		} catch (NBTException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		Interstellar.network.sendToServer(new PacketSaveGuide(this, this.player));
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		if (this.cmg == null) {
			SElement t = this.guideIndex.getCurrentIndex();
			if (t instanceof Guide) {
				this.cmg = (Guide) t;
			} else {
				this.cmg = null;
			}
		} else if (this.guideIndex.getCurrentIndex() == this.mainMenu) {
			this.cmg = null;
		}
		if (this.cmg != null) {
			this.next.setEnabled(this.cmg.hasNext());
			this.prev.setEnabled(this.cmg.hasPrev());
		} else if (this.guideIndex.getCurrentIndex() == this.mainMenu) {
			this.next.setEnabled(false);
			this.prev.setEnabled(false);
		}
	}

	public void mouseClicked(SElement element, int mx, int my, int mouseButton) {
		if (mouseButton != 0) {
			return;
		}
		if (this.discord == element) {
			openWebLink("https://discord.gg/5qzseB3");
		} else if (this.curseforge == element) {
			openWebLink("https://www.curseforge.com/members/ValkyrieofNight/projects");
		} else if (this.github == element) {
			openWebLink("https://github.com/ValkyrieofNight");
		}
		if (this.prev == element) {
			if (this.guideIndex.getCurrentIndex() != null && this.guideIndex.getCurrentIndex() instanceof Guide) {
				Guide mg = (Guide) this.guideIndex.getCurrentIndex();
				if (mg.hasPrev()) {
					mg.prev();
				}
			}
		} else if (this.home == element) {
			if (this.guideIndex.getCurrentIndex() != null && this.guideIndex.getCurrentIndex() instanceof Guide) {
				Guide mg = (Guide) this.guideIndex.getCurrentIndex();
				if (mg.home()) {
					this.guideIndex.setIndex(this.mainMenu.getID());
				}
			}
		} else if (this.next == element) {
			if (this.guideIndex.getCurrentIndex() != null && this.guideIndex.getCurrentIndex() instanceof Guide) {
				Guide mg = (Guide) this.guideIndex.getCurrentIndex();
				if (mg.hasNext()) {
					mg.next();
				}
			}
		} else if (this.up == element) {
			if (this.guideIndex.getCurrentIndex() != null && this.guideIndex.getCurrentIndex() instanceof Guide) {
				Guide mg = (Guide) this.guideIndex.getCurrentIndex();
				mg.addYScroll(this.height / 3);
			}
		} else if (this.down == element) {
			if (this.guideIndex.getCurrentIndex() != null && this.guideIndex.getCurrentIndex() instanceof Guide) {
				Guide mg = (Guide) this.guideIndex.getCurrentIndex();
				mg.addYScroll(-this.height / 3);
			}
		} else if (element instanceof ButtonSElement) {
			setGuide(element.getID());
		}
	}

	protected void setGuide(String guide) {
		SElement g = this.guideIndex.getIndex(guide);
		if (g instanceof Guide) {
			this.guideIndex.setIndex(guide);
		}
	}

	@Override
	public void scrollEvent(SElement ele, int mx, int my, int scroll) {
	}

	@Override
	public void keyEvent(SElement ele, char character, int keyCode) {
	}

	public void addMainMenuButton(ButtonSElement button) {
		button.setPos(4 + this.grid.getCurrentXOffset(), 4 + this.grid.getCurrentYOffset());
		this.grid.next();
		this.mainMenu.addElement(button);
		this.mainMenu.setScrollMin(0, 0);
		this.mainMenu.setScrollMax(0, 2000);
	}

	public void addSupportPage() {
	}

	public void addGuide(Guide modGuide) {
		if (modGuide == null) {
			return;
		}
		this.guideIndex.addIndex(modGuide);
		modGuide.setXPosOffset(4);
		modGuide.setYPosOffset(4);
		modGuide.setXSize(this.mainMenu.getXSize());
		modGuide.setYSize(this.mainMenu.getYSize());
		ButtonSElement but = modGuide.getButton();
		addMainMenuButton(but);
	}

	protected class MainMenu extends SContainerScissorPane {
		GuiGuide gui;

		public MainMenu(GuiGuide gui, String elementID, int xSize, int ySize) {
			super(elementID, xSize, ySize);
			this.gui = gui;
		}

		public void mouseClicked(SElement ele, int mx, int my, int mouseButton) {
			this.gui.mouseClicked(ele, mx, my, mouseButton);
		}

		@Override
		public void addElements() {
		}
	}

	private void openWebLink(String url) {
		final Set<String> PROTOCOLS = Sets.newHashSet("http", "https");
		try {
			URI uri = new URI(url);
			String s = uri.getScheme();
			if (s == null) {
				InterstellarLogger.error("{} is missing protocol", url);
				return;
			}
			if (!PROTOCOLS.contains(s.toLowerCase(Locale.ROOT))) {
				InterstellarLogger.error("Unsupported protocol: [{}] ({})", s.toLowerCase(Locale.ROOT), url);
				return;
			}
			try {
				Class<?> oclass = Class.forName("java.awt.Desktop");
				Object object = oclass.getMethod("getDesktop").invoke((Object) null);
				oclass.getMethod("browse", URI.class).invoke(object, url);
			} catch (Throwable throwable1) {
				Throwable throwable = throwable1.getCause();
				InterstellarLogger.error("Couldn't open link: {}", (Object) (throwable == null ? "<UNKNOWN>" : throwable.getMessage()));
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}

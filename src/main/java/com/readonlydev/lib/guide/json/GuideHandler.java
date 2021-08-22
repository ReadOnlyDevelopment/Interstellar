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

package com.readonlydev.lib.guide.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.readonlydev.api.mod.IIDProvider;
import com.readonlydev.lib.guide.client.element.GuiGuidePage;
import com.readonlydev.lib.guide.client.element.Guide;
import com.readonlydev.lib.guide.json.objects.GOContainerHorizontal;
import com.readonlydev.lib.guide.json.objects.GOContainerVertical;
import com.readonlydev.lib.guide.json.objects.GOImage;
import com.readonlydev.lib.guide.json.objects.GOItemStack;
import com.readonlydev.lib.guide.json.objects.GOParagraph;
import com.readonlydev.lib.guide.json.objects.GOPlainText;
import com.readonlydev.lib.guide.json.objects.GOSpacing;
import com.readonlydev.lib.guide.json.objects.GOText;
import com.readonlydev.lib.guide.json.objects.GOTileData;
import com.readonlydev.lib.guide.json.objects.GuideObject;

import io.netty.util.internal.StringUtil;

public abstract class GuideHandler implements IIDProvider {

	private static List<GuideHandler>	REGISTRY	= new ArrayList<>();
	private final Class<?>				MOD_CLASS;
	private static String				FS			= "/";
	public List<GuidePage>				guidePages;

	public static void reloadAllRegistries() {
		for (GuideHandler guideHandler : REGISTRY) {
			guideHandler.reload();
		}
	}

	public GuideHandler(Class<?> modClass) {
		this.MOD_CLASS = modClass;
		REGISTRY.add(this);
	}

	public void reload() {
		this.guidePages = new ArrayList<>();
		try {
			loadPages();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<InputStream> getAllStreamsFromFolder(String location) throws URISyntaxException, IOException {
		Path path;
		if (this.MOD_CLASS == null) {
			return null;
		}
		if (StringUtil.isNullOrEmpty(location)) {
			return null;
		}
		URI uri = this.MOD_CLASS.getResource(location).toURI();
		if (uri.getScheme().equals("jar")) {
			FileSystem fs = FileSystems.newFileSystem(uri, Collections.emptyMap());
			path = fs.getPath(location, new String[0]);
		} else {
			path = Paths.get(uri);
		}
		Stream<Path> walk = Files.walk(path, 1, new java.nio.file.FileVisitOption[0]);
		Files.list(path).collect(Collectors.toList());
		List<Path> ps = new ArrayList<>();
		walk.forEach(ps::add);
		List<InputStream> streams = new ArrayList<>();
		for (Path p : ps) {
			if (p.toString().endsWith(".json")) {
				InputStream is = Files.newInputStream(p, new java.nio.file.OpenOption[0]);
				streams.add(is);
			}
		}
		walk.close();
		return streams;
	}

	public void loadPages() throws Exception {
		List<InputStream> isl = getAllStreamsFromFolder(FS + "assets" + FS + getModId() + FS + "guide" + FS);
		for (InputStream is : isl) {
			BufferedReader jsReader = new BufferedReader(new InputStreamReader(is));
			if (jsReader.ready()) {
				GuidePage gp = getGson().fromJson(jsReader, GuidePage.class);
				if (gp != null) {
					this.guidePages.add(gp);
				}
			}
			jsReader.close();
		}
		Collections.sort(this.guidePages);
	}

	public List<GuiGuidePage> addGuiPages(Guide.GuideTOCPage mainMenu, int xSize) {
		List<GuiGuidePage> r = new ArrayList<>();
		for (GuidePage guidePage : this.guidePages) {
			GuiGuidePage gp = guidePage.addPage(mainMenu, mainMenu.getXSize());
			if (gp != null) {
				r.add(gp);
			}
		}
		return r;
	}

	private static Gson GSON = (new GsonBuilder()).registerTypeAdapter(GuidePage.class, new GuidePage.GuidePageSerializer()).registerTypeAdapter(GOContainerHorizontal.class, new GOContainerHorizontal.GOContainerHorizontalSerializer()).registerTypeAdapter(GOContainerVertical.class, new GOContainerVertical.GOContainerVerticalSerializer()).registerTypeAdapter(GOImage.class, new GOImage.GOImageSerializer()).registerTypeAdapter(GOItemStack.class, new GOItemStack.GOItemStackSerializer())
			.registerTypeAdapter(GOParagraph.class, new GOParagraph.GOParagraphSerializer()).registerTypeAdapter(GOSpacing.class, new GOSpacing.GOSpacingSerializer()).registerTypeAdapter(GOText.class, new GOText.GOTextSerializer()).registerTypeAdapter(GOTileData.class, new GOTileData.GOTileDataSerializer()).registerTypeAdapter(GOPlainText.class, new GOPlainText.GOPlainTextSerializer()).setPrettyPrinting().create();

	private static Map<String, Class<?>> map = new TreeMap<>();

	static {
		map.put(GuideObject.BLANK.getType(), GuideObject.class);
		map.put(GOContainerHorizontal.BLANK.getType(), GOContainerHorizontal.class);
		map.put(GOContainerVertical.BLANK.getType(), GOContainerVertical.class);
		map.put(GOImage.BLANK.getType(), GOImage.class);
		map.put(GOItemStack.BLANK.getType(), GOItemStack.class);
		map.put(GOParagraph.BLANK.getType(), GOParagraph.class);
		map.put(GOSpacing.BLANK.getType(), GOSpacing.class);
		map.put(GOText.BLANK.getType(), GOText.class);
		map.put(GOTileData.BLANK.getType(), GOTileData.class);
		map.put(GOPlainText.BLANK.getType(), GOPlainText.class);
	}

	public static Map<String, Class<?>> getTypes() {
		return map;
	}

	public static Gson getGson() {
		return GSON;
	}

	public static void registerType(String name, Class<?> clazz) {
		map.put(name, clazz);
	}
}

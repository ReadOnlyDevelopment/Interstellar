package com.readonlydev.lib.base.config.impl;

import com.google.common.base.Supplier;

import net.minecraftforge.common.config.Configuration;

public class Category implements Supplier<String> {

	public static final String SEP = Configuration.CATEGORY_SPLITTER;
	private String category;
	private String category_commit;
	public boolean requiresWorldRestart = false;
	public boolean requiresMCRestart = false;

	private Category(String category) {
		this.category = category;
	}

	public static Category of(String category) {
		return new Category(category);
	}

	public static Category of(Category parent, String category) {
		String combined = parent.get() + SEP + category;
		return new Category(combined);
	}

	public Category setRequiredRestarts(boolean requiresWorldRestart, boolean requiresMCRestart) {
		this.requiresWorldRestart = requiresWorldRestart;
		this.requiresMCRestart = requiresMCRestart;
		return this;
	}

	public void setCategoryComment(String comment) {
		this.category_commit = comment;
	}

	@Override
	public String get() {
		return this.category;
	}

	public String getCatgeoryComment() {
		return this.category_commit;
	}

	public String getLangKey() {
		return ".config.gui.cat." + this.category.toLowerCase();
	}
}

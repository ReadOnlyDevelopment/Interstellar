package com.readonlydev.lib.base.config.values;

import com.readonlydev.lib.base.config.impl.Category;
import com.readonlydev.lib.base.config.impl.Comment;
import com.readonlydev.lib.base.config.impl.Key;

import net.minecraftforge.common.config.Configuration;

public abstract class ConfigValue {

	private Type type;
	private Key key;
	private Category category;
	private Comment comment;
	private boolean requiresWorldRestart = false;
	private boolean requiresGameRestart = false;

	public ConfigValue(Type type, String key, Category category) {
		this(key, category, null);
		setType(type);
	}

	public ConfigValue(Type type, String key, Category category, String comment) {
		this(key, category, comment);
		setType(type);
	}

	public ConfigValue(String key, Category category) {
		this(key, category, null);
	}

	public ConfigValue(String key, Category category, String comment) {
		this.key = Key.of(key);
		this.category = category;
		this.comment = Comment.of(comment);
	}

	void formatDescription(String defualtVal) {
		this.comment.add(Configuration.NEW_LINE + defualtVal);
	}

	void setComment(Comment comment) {
		this.comment = comment;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Type getType() {
		return this.type;
	}

	public String key() {
		return this.key.get();
	}

	public String langKey() {
		return this.key.getLangKey();
	}

	public String category() {
		return this.category.get();
	}

	public Category getConfigCat() {
		return this.category;
	}

	public String comment() {
		return this.comment.get();
	}

	public ConfigValue requiresWorldRestart() {
		this.requiresWorldRestart = true;
		return this;
	}

	public ConfigValue requiresGameRestart() {
		this.requiresGameRestart = true;
		return this;
	}

	public boolean needsWorldRestart() {
		return this.requiresWorldRestart;
	}

	public boolean needsGameRestart() {
		return this.requiresGameRestart;
	}

	public enum Type {
		INTEGER, INTEGER_ARRAY, DOUBLE, DOUBLE_ARRAY, BOOLEAN, STRING, STRING_ARRAY
	}
}

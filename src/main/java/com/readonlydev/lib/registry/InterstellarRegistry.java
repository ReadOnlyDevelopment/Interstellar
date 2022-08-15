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

package com.readonlydev.lib.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.readonlydev.api.block.IBlockTileEntity;
import com.readonlydev.api.block.IModelProvider;
import com.readonlydev.api.item.IMeshProvider;
import com.readonlydev.api.registry.IInitProcessor;
import com.readonlydev.api.repcie.IRecipeProcessor;
import com.readonlydev.lib.block.BlockSubtype;
import com.readonlydev.lib.item.ItemBlockSubtype;
import com.readonlydev.lib.recipe.RecipeBuilder;
import com.readonlydev.lib.world.biome.ExoplanetBiome;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.command.ICommand;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraft.world.storage.loot.properties.EntityProperty;
import net.minecraft.world.storage.loot.properties.EntityPropertyManager;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class InterstellarRegistry {
	private static final Pattern PATTERN_REGISTRY_NAME = Pattern.compile("[^a-z0-9_]+");

	/**
     * Gets the blocks.
     *
     * @return the blocks
     */
	@Getter
	private final List<Block> BLOCKS = NonNullList.create();
	
	/**
     * Gets the items.
     *
     * @return the items
     */
	@Getter
	private final List<Item> ITEMS = NonNullList.create();
	
	/**
     * Gets the fluids.
     *
     * @return the fluids
     */
	@Getter
	private final List<Fluid> FLUIDS = NonNullList.create();
	
	/**
     * Gets the fluid blocks.
     *
     * @return the fluid blocks
     */
	@Getter
	private final List<IFluidBlock> FLUID_BLOCKS = NonNullList.create();
	
	/**
     * Gets the biomes.
     *
     * @return the biomes
     */
	@Getter
	private final List<Biome> BIOMES = NonNullList.create();

	private final List<IRecipeProcessor> recipeAdders = NonNullList.create();

	private final List<IInitProcessor> phasedInitializers = new ArrayList<>();
	private final Map<Class<? extends IForgeRegistryEntry<?>>, Consumer<InterstellarRegistry>> registrationHandlers = new HashMap<>();

	private Object mod;
	private final Logger logger;
	private final String modId;
	private final String resourcePrefix;

	@Nonnull
	private final RecipeBuilder recipes;

	/**
     * Gets the default creative tab.
     *
     * @return the default creative tab
     */
	@Getter
	
	/**
     * Sets the default creative tab.
     *
     * @param defaultCreativeTab the new default creative tab
     */
	@Setter
	@Nullable
	private CreativeTabs defaultCreativeTab = null;

	/**
     * Interstellar registry.
     */
	public InterstellarRegistry() {
		ModContainer mod = Objects.requireNonNull(Loader.instance().activeModContainer());
		this.modId = mod.getModId();
		this.resourcePrefix = this.modId + ":";
		this.logger = LogManager.getLogger(mod.getName() + "/Registry");
		this.recipes = new RecipeBuilder(modId);
		MinecraftForge.EVENT_BUS.register(new EventHandler(this));
	}

	/**
     * Gets the recipe builder.
     *
     * @return the recipe builder
     */
	public RecipeBuilder getRecipeBuilder() {
		return recipes;
	}

	/**
     * Sets the mod.
     *
     * @param object the new mod
     */
	public void setMod(Object object) {
		this.mod = object;
	}

	/**
	 * Add a phased initializer, which has preInit, init, and postInit methods which
	 * InterstellarRegistry will call automatically.
	 * <p>
	 * This method should be called during <em>pre-init</em> in the proper proxy,
	 * <em>before</em> calling the InterstellarRegistry's preInit method.
	 * </p>
	 *
	 * @param instance Your initializer (singleton design is recommended)
	 */
	public void addPhasedInitializer(IInitProcessor instance) {
		this.phasedInitializers.add(instance);
	}

	/**
	 * Adds a function that will be called when it is time to register objects for a
	 * certain class. For example, adding a handler for class {@link Item} will call
	 * the function during {@link RegistryEvent.Register} for type {@link Item}.
	 * <p>
	 * This method should be called during <em>pre-init</em> in the proper proxy.
	 * </p>
	 *
	 * @param registerFunction The function to call
	 * @param registryClass    The registry object class
	 * @throws RuntimeException if a handler for the class is already registered
	 */
	public void addRegistrationHandler(Consumer<InterstellarRegistry> registerFunction,
			Class<? extends IForgeRegistryEntry<?>> registryClass) throws RuntimeException {
		if (this.registrationHandlers.containsKey(registryClass)) {
			throw new RuntimeException("Registration handler for class " + registryClass + " already registered!");
		}
		this.registrationHandlers.put(registryClass, registerFunction);
	}

	/**
     * Make creative tab.
     *
     * @param  label label
     * @param  icon  icon
     * 
     * @return       creative tabs
     */
	public CreativeTabs makeCreativeTab(String label, Supplier<ItemStack> icon) {
		CreativeTabs tab = new CreativeTabs(label) {

			@Override
			public ItemStack createIcon() {
				return icon.get();
			}
		};

		if (defaultCreativeTab == null) {
			defaultCreativeTab = tab;
		}
		return tab;
	}

	/**
     * Register a Block. Its name (registry key/name) must be provided. Uses a new ItemBlockSL.
     *
     * @param  <T>   the generic type
     * @param  block block
     * @param  key   key
     * 
     * @return       t
     */
	public <T extends Block> T register(T block, String key) {
		return register(block, key, defaultItemBlock(block));
	}

	@Nonnull
	private <T extends Block> ItemBlock defaultItemBlock(T block) {
		if (block instanceof BlockSubtype) {
			return new ItemBlockSubtype((BlockSubtype) block);
		} else {
			return new ItemBlock(block);
		}
	}

	/**
     * Register a Block. Its name registry name and ItemBlock must be provided.
     *
     * @param  <T>       the generic type
     * @param  block     block
     * @param  key       key
     * @param  itemBlock item block
     * 
     * @return           t
     */
	public <T extends Block> T register(T block, String key, ItemBlock itemBlock) {
		BLOCKS.add(block);
		block.setTranslationKey(modId + "." + key);

		validateRegistryName(key);
		ResourceLocation name = new ResourceLocation(modId, key);
		safeSetRegistryName(block, name);
		ForgeRegistries.BLOCKS.register(block);

		safeSetRegistryName(itemBlock, name);
		ForgeRegistries.ITEMS.register(itemBlock);

		if (block instanceof IBlockTileEntity) {
			Class<? extends TileEntity> clazz = ((IBlockTileEntity) block).getTileEntityClass();
			registerTileEntity(clazz, key);
		}

		if (block instanceof IRecipeProcessor) {
			this.recipeAdders.add((IRecipeProcessor) block);
		}

		if (defaultCreativeTab != null) {
			block.setCreativeTab(defaultCreativeTab);
		}

		return block;
	}

	// Fluid

	/**
     * Create a {@link Fluid} and its {@link IFluidBlock}, or use the existing ones if a fluid has already been registered with the same name.
     *
     * @param  <T>          the generic type
     * @param  fluid        fluid
     * @param  blockFactory A function that creates the {@link IFluidBlock}
     * 
     * @return              The fluid and block
     */
	public <T extends Block & IFluidBlock> Fluid register(Fluid fluid, Function<Fluid, T> blockFactory) {
		final boolean useOwnFluid = FluidRegistry.registerFluid(fluid);
		if (useOwnFluid) {
			register(blockFactory.apply(fluid), "fluid_" + fluid.getName());
		} else {
			fluid = FluidRegistry.getFluid(fluid.getName());
		}
		FLUIDS.add(fluid);
		return fluid;
	}

	// Item

	/**
     * Register an Item. Its name (registry key/name) must be provided.
     *
     * @param  <T>  the generic type
     * @param  item item
     * @param  key  key
     * 
     * @return      t
     */
	public <T extends Item> T register(T item, String key) {
		ITEMS.add(item);
		item.setTranslationKey(modId + "." + key);

		validateRegistryName(key);
		ResourceLocation name = new ResourceLocation(modId, key);
		safeSetRegistryName(item, name);
		ForgeRegistries.ITEMS.register(item);

		if (item instanceof IRecipeProcessor) {
			this.recipeAdders.add((IRecipeProcessor) item);
		}

		if (defaultCreativeTab != null) {
			item.setCreativeTab(defaultCreativeTab);
		}

		return item;
	}

	/**
     * Register.
     *
     * @param  <T>   the generic type
     * @param  biome biome
     * 
     * @return       t
     */
	public <T extends ExoplanetBiome> T register(T biome) {
		BIOMES.add(biome);
		validateRegistryName(biome.getBiomeData().biomeName);
		ResourceLocation name = new ResourceLocation(modId, biome.getBiomeData().biomeName);
		safeSetRegistryName(biome, name);
		ForgeRegistries.BIOMES.register(biome);
		return biome;
	}

	// Enchantment

	/**
     * Register enchantment.
     *
     * @param enchantment enchantment
     * @param key         key
     */
	public void registerEnchantment(Enchantment enchantment, String key) {
		validateRegistryName(key);
		ResourceLocation name = new ResourceLocation(modId, key);
		safeSetRegistryName(enchantment, name);
		enchantment.setName(name.getNamespace() + "." + name.getPath());
		ForgeRegistries.ENCHANTMENTS.register(enchantment);
	}

	// Entity

	/**
	 * Automatically incrementing ID number for registering entities.
	 */
	private int lastEntityId = -1;

	/**
     * Register entity.
     *
     * @param entityClass entity class
     * @param key         key
     */
	public void registerEntity(Class<? extends Entity> entityClass, String key) {
		registerEntity(entityClass, key, ++lastEntityId, mod, 64, 20, true);
	}

	/**
     * Register entity.
     *
     * @param entityClass          entity class
     * @param key                  key
     * @param trackingRange        tracking range
     * @param updateFrequency      update frequency
     * @param sendsVelocityUpdates sends velocity updates
     */
	public void registerEntity(Class<? extends Entity> entityClass, String key, int trackingRange, int updateFrequency,
			boolean sendsVelocityUpdates) {
		registerEntity(entityClass, key, ++lastEntityId, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
	}

	/**
     * Register entity.
     *
     * @param entityClass          entity class
     * @param key                  key
     * @param id                   id
     * @param mod                  mod
     * @param trackingRange        tracking range
     * @param updateFrequency      update frequency
     * @param sendsVelocityUpdates sends velocity updates
     */
	public void registerEntity(Class<? extends Entity> entityClass, String key, int id, Object mod, int trackingRange,
			int updateFrequency, boolean sendsVelocityUpdates) {
		ResourceLocation resource = new ResourceLocation(modId, key);
		EntityRegistry.registerModEntity(resource, entityClass, key, id, mod, trackingRange, updateFrequency,
				sendsVelocityUpdates);
	}

	/**
     * Register entity.
     *
     * @param entityClass          entity class
     * @param key                  key
     * @param trackingRange        tracking range
     * @param updateFrequency      update frequency
     * @param sendsVelocityUpdates sends velocity updates
     * @param eggPrimary           egg primary
     * @param eggSecondary         egg secondary
     */
	public void registerEntity(Class<? extends Entity> entityClass, String key, int trackingRange, int updateFrequency,
			boolean sendsVelocityUpdates, int eggPrimary, int eggSecondary) {
		registerEntity(entityClass, key, ++lastEntityId, mod, trackingRange, updateFrequency, sendsVelocityUpdates,
				eggPrimary, eggSecondary);
	}

	/**
     * Register entity.
     *
     * @param entityClass          entity class
     * @param key                  key
     * @param id                   id
     * @param mod                  mod
     * @param trackingRange        tracking range
     * @param updateFrequency      update frequency
     * @param sendsVelocityUpdates sends velocity updates
     * @param eggPrimary           egg primary
     * @param eggSecondary         egg secondary
     */
	public void registerEntity(Class<? extends Entity> entityClass, String key, int id, Object mod, int trackingRange,
			int updateFrequency, boolean sendsVelocityUpdates, int eggPrimary, int eggSecondary) {
		ResourceLocation resource = new ResourceLocation(modId, key);
		EntityRegistry.registerModEntity(resource, entityClass, key, id, mod, trackingRange, updateFrequency,
				sendsVelocityUpdates, eggPrimary, eggSecondary);
	}

	/**
     * Register entity renderer.
     *
     * @param <T>           the generic type
     * @param entityClass   entity class
     * @param renderFactory render factory
     */
	@SideOnly(Side.CLIENT)
	public <T extends Entity> void registerEntityRenderer(Class<T> entityClass, IRenderFactory<T> renderFactory) {
		RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
	}

	// Potion

	/**
     * Register potion.
     *
     * @param potion potion
     * @param key    key
     */
	public void registerPotion(Potion potion, String key) {
		if (potion.getName().isEmpty()) {
			potion.setPotionName("effect." + modId + "." + key);
		}

		validateRegistryName(key);
		ResourceLocation name = new ResourceLocation(this.modId, key);
		safeSetRegistryName(potion, name);
		ForgeRegistries.POTIONS.register(potion);
	}

	// Sound Events

	/**
     * Register sound event.
     *
     * @param sound sound
     * @param key   key
     */
	public void registerSoundEvent(SoundEvent sound, String key) {
		validateRegistryName(key);
		ResourceLocation name = new ResourceLocation(modId, key);
		safeSetRegistryName(sound, name);
		ForgeRegistries.SOUND_EVENTS.register(sound);
	}

	// Loot

	/**
     * Register loot condition.
     *
     * @param serializer serializer
     */
	public void registerLootCondition(LootCondition.Serializer<? extends LootCondition> serializer) {
		LootConditionManager.registerCondition(serializer);
	}

	/**
     * Register loot entity property.
     *
     * @param serializer serializer
     */
	public void registerLootEntityProperty(EntityProperty.Serializer<? extends EntityProperty> serializer) {
		EntityPropertyManager.registerProperty(serializer);
	}

	/**
     * Register loot function.
     *
     * @param serializer serializer
     */
	public void registerLootFunction(LootFunction.Serializer<? extends LootFunction> serializer) {
		LootFunctionManager.registerFunction(serializer);
	}

	/**
     * Register loot table.
     *
     * @param name name
     */
	public void registerLootTable(String name) {
		LootTableList.register(new ResourceLocation(this.modId, name));
	}

	/**
	 * Set the object's registry name, if it has not already been set. Logs a
	 * warning if it has.
	 */
	private void safeSetRegistryName(IForgeRegistryEntry<?> entry, ResourceLocation name) {
		if (entry.getRegistryName() == null) {
			entry.setRegistryName(name);
		} else {
			logger.warn("Registry name for {} has already been set. Was trying to set it to {}.",
					entry.getRegistryName(), name);
		}
	}

	/**
	 * Ensure the given name does not contain upper case letters. If it does then
	 * toLowercase() is called
	 */
	private void validateRegistryName(String name) {
		if (PATTERN_REGISTRY_NAME.matcher(name).matches()) {
			logger.warn("Invalid name for object: {}", name);
		}
	}

	/**
     * Register advancement trigger.
     *
     * @param  <T>     the generic type
     * @param  trigger trigger
     * 
     * @return         i criterion trigger
     */
	// Advancements
	public <T extends ICriterionInstance> ICriterionTrigger<T> registerAdvancementTrigger(
			ICriterionTrigger<T> trigger) {
		CriteriaTriggers.register(trigger);
		return trigger;
	}

	/**
     * Register a TileEntity. "tile." + resourcePrefix is automatically prepended to the key.
     *
     * @param tileClass tile class
     * @param key       key
     */
	public void registerTileEntity(Class<? extends TileEntity> tileClass, String key) {
		GameRegistry.registerTileEntity(tileClass, new ResourceLocation(modId, key));
	}

	/**
     * Registers a renderer for a TileEntity.
     *
     * @param <T>       the generic type
     * @param tileClass tile class
     * @param renderer  renderer
     */
	@SideOnly(Side.CLIENT)
	public <T extends TileEntity> void registerTileEntitySpecialRenderer(Class<T> tileClass,
			TileEntitySpecialRenderer<T> renderer) {
		ClientRegistry.bindTileEntitySpecialRenderer(tileClass, renderer);
	}

	// Model registration wrappers

	/**
     * Set model.
     *
     * @param block     block
     * @param meta      meta
     * @param modelPath model path
     */
	@SideOnly(Side.CLIENT)
	public void setModel(Block block, int meta, String modelPath) {
		setModel(Item.getItemFromBlock(block), meta, modelPath, "inventory");
	}

	/**
     * Set model.
     *
     * @param block     block
     * @param meta      meta
     * @param modelPath model path
     * @param variant   variant
     */
	@SideOnly(Side.CLIENT)
	public void setModel(Block block, int meta, String modelPath, String variant) {
		setModel(Item.getItemFromBlock(block), meta, modelPath, variant);
	}

	/**
     * Set model.
     *
     * @param item      item
     * @param meta      meta
     * @param modelPath model path
     */
	@SideOnly(Side.CLIENT)
	public void setModel(Item item, int meta, String modelPath) {
		setModel(item, meta, modelPath, "inventory");
	}

	/**
     * Set model.
     *
     * @param item      item
     * @param meta      meta
     * @param modelPath model path
     * @param variant   variant
     */
	@SideOnly(Side.CLIENT)
	public void setModel(Item item, int meta, String modelPath, String variant) {
		ModelLoader.setCustomModelResourceLocation(item, meta,
				new ModelResourceLocation(this.resourcePrefix + modelPath, variant));
	}

	// endregion

	// region Initialization phases

	/*
	 * Initialization phases. Calling in either your common or client proxy is
	 * recommended. "client" methods in your client proxy, the rest in your common
	 * AND client proxy.
	 */

	private boolean preInitDone = false;
	private boolean initDone = false;
	private boolean postInitDone = false;

	/**
     * Call in the "preInit" phase in your common proxy.
     *
     * @param event event
     */
	public void preInit(FMLPreInitializationEvent event) {
		if (this.preInitDone) {
			logger.warn("preInit called more than once!");
			return;
		}

		verifyOrFindModObject();
		this.phasedInitializers.forEach(i -> i.preInit(this, event));
		this.preInitDone = true;
	}

	private void verifyOrFindModObject() {
		if (mod == null) {
			logger.warn("Mod {} did not manually set its mod object! This is bad and may cause crashes.", modId);
			ModContainer container = Loader.instance().getIndexedModList().get(modId);
			if (container != null) {
				this.mod = container.getMod();
				logger.warn("Automatically acquired mod object for {}", modId);
			} else {
				logger.warn("Could not find mod object. The mod ID is likely incorrect.");
			}
		}
	}

	/**
     * Call in the "init" phase in your common proxy.
     *
     * @param event event
     */
	public void init(FMLInitializationEvent event) {
		if (this.initDone) {
			logger.warn("init called more than once!");
			return;
		}
		this.phasedInitializers.forEach(i -> i.init(this, event));
		this.initDone = true;
	}

	/**
     * Call in the "postInit" phase in your common proxy.
     *
     * @param event event
     */
	public void postInit(FMLPostInitializationEvent event) {
		if (this.postInitDone) {
			logger.warn("postInit called more than once!");
			return;
		}

		int oldRecipeRegisterCount = recipes.getOldRecipeRegisterCount();
		if (oldRecipeRegisterCount > 0) {
			long totalRecipes = ForgeRegistries.RECIPES.getKeys().stream().map(ResourceLocation::getNamespace)
					.filter(s -> s.equals(modId)).count();
			logger.warn("Mod '{}' is still registering recipes with RecipeBuilder ({} recipes, out of {} total)", modId,
					oldRecipeRegisterCount, totalRecipes);
		}

		this.phasedInitializers.forEach(i -> i.postInit(this, event));
		this.postInitDone = true;
	}

	/**
     * Call in the "preInit" phase in your client proxy.
     *
     * @param event event
     */
	@SideOnly(Side.CLIENT)
	public void clientPreInit(FMLPreInitializationEvent event) {
	}

	/**
     * Call in the "init" phase in your client proxy.
     *
     * @param event event
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void clientInit(FMLInitializationEvent event) {
		for (Block block : this.BLOCKS) {
			if (block instanceof IBlockTileEntity) {
				IBlockTileEntity tileBlock = (IBlockTileEntity) block;
				final TileEntitySpecialRenderer tesr = tileBlock.getTileRenderer();
				if (tesr != null) {
					ClientRegistry.bindTileEntitySpecialRenderer(tileBlock.getTileEntityClass(), tesr);
				}
			}
		}
	}

	/**
     * Call in the "postInit" phase in your client proxy.
     *
     * @param event event
     */
	@SideOnly(Side.CLIENT)
	public void clientPostInit(FMLPostInitializationEvent event) {
	}

	/**
     * Register command.
     *
     * @param command command
     */
	public void registerCommand(ICommand command) {
		ClientCommandHandler.instance.registerCommand(command);
	}

	// endregion

	private void addRecipes() {
		this.recipeAdders.forEach(obj -> obj.addRecipes(this.recipes));
	}

	private void addOreDictEntries() {
		this.recipeAdders.forEach(IRecipeProcessor::addOreDict);
	}

	@SideOnly(Side.CLIENT)
	private void registerModels() {
		for (Block block : BLOCKS) {
			if (block instanceof IModelProvider) {
				((IModelProvider) block).registerModels();
			} else {
				ResourceLocation registryName = Objects.requireNonNull(block.getRegistryName());
				ModelResourceLocation model = new ModelResourceLocation(registryName, "inventory");
				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, model);
			}
		}
		for (Item item : ITEMS) {
			if (item instanceof IMeshProvider) {
				IMeshProvider customMesh = (IMeshProvider) item;
				ModelBakery.registerItemVariants(item, customMesh.getVariants());
				ModelLoader.setCustomMeshDefinition(item, customMesh.getCustomMesh());
			} else if (item instanceof IModelProvider) {
				((IModelProvider) item).registerModels();
			} else {
				ResourceLocation registryName = Objects.requireNonNull(item.getRegistryName());
				ModelResourceLocation model = new ModelResourceLocation(registryName, "inventory");
				ModelLoader.setCustomModelResourceLocation(item, 0, model);
			}
		}
	}

	public static class EventHandler {
		private final InterstellarRegistry registry;

		/**
         * Event handler.
         *
         * @param sregistry sregistry
         */
		public EventHandler(InterstellarRegistry sregistry) {
			this.registry = sregistry;
		}

		private void runRegistrationHandlerIfPresent(Class<? extends IForgeRegistryEntry<?>> registryClass) {
			if (registry.registrationHandlers.containsKey(registryClass)) {
				registry.registrationHandlers.get(registryClass).accept(registry);
			}
		}

		/**
         * Register blocks.
         *
         * @param event event
         */
		@SubscribeEvent
		public void registerBlocks(RegistryEvent.Register<Block> event) {
			runRegistrationHandlerIfPresent(Block.class);
		}

		/**
         * Register items.
         *
         * @param event event
         */
		@SubscribeEvent
		public void registerItems(RegistryEvent.Register<Item> event) {
			runRegistrationHandlerIfPresent(Item.class);
			registry.addOreDictEntries();
		}

		/**
         * Register potions.
         *
         * @param event event
         */
		@SubscribeEvent
		public void registerPotions(RegistryEvent.Register<Potion> event) {
			runRegistrationHandlerIfPresent(Potion.class);
		}

		/**
         * Register biomes.
         *
         * @param event event
         */
		@SubscribeEvent
		public void registerBiomes(RegistryEvent.Register<Biome> event) {
			runRegistrationHandlerIfPresent(Biome.class);
		}

		/**
         * Register sound events.
         *
         * @param event event
         */
		@SubscribeEvent
		public void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
			runRegistrationHandlerIfPresent(SoundEvent.class);
		}

		/**
         * Register potion types.
         *
         * @param event event
         */
		@SubscribeEvent
		public void registerPotionTypes(RegistryEvent.Register<PotionType> event) {
			runRegistrationHandlerIfPresent(PotionType.class);
		}

		/**
         * Register enchantments.
         *
         * @param event event
         */
		@SubscribeEvent
		public void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
			runRegistrationHandlerIfPresent(Enchantment.class);
		}

		/**
         * Register villager professions.
         *
         * @param event event
         */
		@SubscribeEvent
		public void registerVillagerProfessions(RegistryEvent.Register<VillagerProfession> event) {
			runRegistrationHandlerIfPresent(VillagerProfession.class);
		}

		/**
         * Register entities.
         *
         * @param event event
         */
		@SubscribeEvent
		public void registerEntities(RegistryEvent.Register<EntityEntry> event) {
			runRegistrationHandlerIfPresent(EntityEntry.class);
		}

		/**
         * Register recipes.
         *
         * @param event event
         */
		@SubscribeEvent
		public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
			runRegistrationHandlerIfPresent(IRecipe.class);
			registry.addRecipes();
		}

		/**
         * Register models.
         *
         * @param event event
         */
		@SubscribeEvent
		public void registerModels(ModelRegistryEvent event) {
			registry.registerModels();
		}
	}
}

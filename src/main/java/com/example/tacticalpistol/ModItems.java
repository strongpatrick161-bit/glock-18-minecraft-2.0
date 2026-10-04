package com.example.tacticalpistol;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, TacticalPistol.MODID);
    public static final RegistryObject<Item> PISTOL =
        ITEMS.register("tactical_pistol", () -> new TacticalPistolItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> AMMO =
        ITEMS.register("ammo_9x19", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RED_DOT =
        ITEMS.register("red_dot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SUPPRESSOR =
        ITEMS.register("suppressor", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EXTENDED_MAG =
        ITEMS.register("extended_mag", () -> new Item(new Item.Properties()));

    public static final DeferredRegister<CreativeModeTab> TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TacticalPistol.MODID);
    public static final RegistryObject<CreativeModeTab> TAB = TABS.register("gear",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tacticalpistol"))
            .icon(() -> new ItemStack(PISTOL.get()))
            .displayItems((params, output) -> {
                output.accept(PISTOL.get());
                output.accept(AMMO.get());
                output.accept(RED_DOT.get());
                output.accept(SUPPRESSOR.get());
                output.accept(EXTENDED_MAG.get());
            }).build());

    private ModItems() {}
}

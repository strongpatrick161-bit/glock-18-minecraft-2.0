package com.example.tacticalpistol;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TacticalPistol.MODID)
public class TacticalPistol {
    public static final String MODID = "tacticalpistol";
    public TacticalPistol() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(bus);
        ModItems.TABS.register(bus);
    }
}

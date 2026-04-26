package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.modules.ModuleCategory;
import net.minecraft.client.Minecraft;
package client.modules;

public class Fullbright extends Module {

    public Fullbright() {
        super("Fullbright", Category.RENDER);
    }

    @Override
    public void onEnable() {
        mc.gameSettings.gammaSetting = 1000f;
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = 1f;
    }

    @Override
    public void onUpdate() {
        mc.gameSettings.gammaSetting = 1000f;
    }
}

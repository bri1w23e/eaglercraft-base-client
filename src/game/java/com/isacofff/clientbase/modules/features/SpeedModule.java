package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.modules.ModuleCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;

public class SpeedModule extends Module {

    private static final String MODULE_NAME = "Speed";
    private static final String MODULE_DESCRIPTION = "Movement speed enhancement for Meteor Client";
    
    public enum SpeedMode {
        GROUND("Ground"),
        AIR("Air"),
        STRAFE("Strafe");
        
        private final String displayName;
        
        SpeedMode(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return this.displayName;
        }
    }
    
    private SpeedMode mode = SpeedMode.GROUND;
    private float speedMultiplier = 1.5f;

    public SpeedModule() {
        super(MODULE_NAME, MODULE_DESCRIPTION, ModuleCategory.MOVEMENT, false);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onUpdate() {
        if (!this.enabled) return;
        
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null) return;
        
        switch (this.mode) {
            case GROUND:
                applyGroundSpeed(mc);
                break;
            case AIR:
                applyAirSpeed(mc);
                break;
            case STRAFE:
                applyStrafeSpeed(mc);
                break;
        }
    }

    @Override
    public void onRender(float partialTicks) {
        if (!this.enabled) return;
        // Render speed module visuals here
    }

    private void applyGroundSpeed(Minecraft mc) {
        if (!mc.thePlayer.onGround) return;
        
        double motionX = mc.thePlayer.motionX;
        double motionZ = mc.thePlayer.motionZ;
        double motion = Math.sqrt(motionX * motionX + motionZ * motionZ);
        
        if (motion > 0) {
            mc.thePlayer.motionX = (motionX / motion) * this.speedMultiplier;
            mc.thePlayer.motionZ = (motionZ / motion) * this.speedMultiplier;
        }
    }

    private void applyAirSpeed(Minecraft mc) {
        if (mc.thePlayer.onGround) return;
        
        double motionX = mc.thePlayer.motionX;
        double motionZ = mc.thePlayer.motionZ;
        double motion = Math.sqrt(motionX * motionX + motionZ * motionZ);
        
        if (motion > 0) {
            mc.thePlayer.motionX = (motionX / motion) * (this.speedMultiplier * 0.8f);
            mc.thePlayer.motionZ = (motionZ / motion) * (this.speedMultiplier * 0.8f);
        }
    }

    private void applyStrafeSpeed(Minecraft mc) {
        double motionX = mc.thePlayer.motionX;
        double motionZ = mc.thePlayer.motionZ;
        double motion = Math.sqrt(motionX * motionX + motionZ * motionZ);
        
        if (motion > 0) {
            mc.thePlayer.motionX = (motionX / motion) * this.speedMultiplier;
            mc.thePlayer.motionZ = (motionZ / motion) * this.speedMultiplier;
        }
    }

    public void setMode(SpeedMode mode) {
        this.mode = mode;
    }

    public void setSpeedMultiplier(float multiplier) {
        this.speedMultiplier = multiplier;
    }

    public SpeedMode getMode() {
        return this.mode;
    }

    public float getSpeedMultiplier() {
        return this.speedMultiplier;
    }
}

package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.modules.ModuleCategory;
import net.minecraft.client.Minecraft;

public class FlightModule extends Module {

    private static final String MODULE_NAME = "Flight";
    private static final String MODULE_DESCRIPTION = "Flight module for Meteor Client";
    
    public enum FlightMode {
        VANILLA("Vanilla"),
        CREATIVE("Creative");
        
        private final String displayName;
        
        FlightMode(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return this.displayName;
        }
    }
    
    private FlightMode mode = FlightMode.VANILLA;
    private float flightSpeed = 0.5f;
    private boolean wasFlying = false;

    public FlightModule() {
        super(MODULE_NAME, MODULE_DESCRIPTION, ModuleCategory.MOVEMENT, false);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer != null) {
            this.wasFlying = mc.thePlayer.capabilities.isFlying;
            mc.thePlayer.capabilities.isFlying = true;
            mc.thePlayer.capabilities.allowFlying = true;
            mc.thePlayer.sendHotbarSnapshot();
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer != null) {
            mc.thePlayer.capabilities.isFlying = this.wasFlying;
            if (!this.wasFlying) {
                mc.thePlayer.capabilities.allowFlying = false;
            }
            mc.thePlayer.sendHotbarSnapshot();
        }
    }

    @Override
    public void onUpdate() {
        if (!this.enabled) return;
        
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null) return;
        
        mc.thePlayer.capabilities.allowFlying = true;
        mc.thePlayer.capabilities.isFlying = true;
        mc.thePlayer.capabilities.flySpeed = this.flightSpeed;
        
        switch (this.mode) {
            case VANILLA:
                handleVanillaFlight(mc);
                break;
            case CREATIVE:
                handleCreativeFlight(mc);
                break;
        }
    }

    @Override
    public void onRender(float partialTicks) {
        if (!this.enabled) return;
        // Render flight module visuals here
    }

    private void handleVanillaFlight(Minecraft mc) {
        // Standard creative-like flight
        mc.thePlayer.onGround = false;
    }

    private void handleCreativeFlight(Minecraft mc) {
        // Advanced directional flight with keyboard input
        float moveForward = 0;
        float strafe = 0;
        
        if (mc.gameSettings.keyBindForward.isKeyDown()) {
            moveForward += 1.0f;
        }
        if (mc.gameSettings.keyBindBack.isKeyDown()) {
            moveForward -= 1.0f;
        }
        if (mc.gameSettings.keyBindLeft.isKeyDown()) {
            strafe += 1.0f;
        }
        if (mc.gameSettings.keyBindRight.isKeyDown()) {
            strafe -= 1.0f;
        }
        
        // Calculate movement vector
        float yaw = mc.thePlayer.rotationYaw;
        double motionX = -Math.sin(Math.toRadians(yaw)) * moveForward * this.flightSpeed + -Math.sin(Math.toRadians(yaw - 90)) * strafe * this.flightSpeed;
        double motionZ = Math.cos(Math.toRadians(yaw)) * moveForward * this.flightSpeed + Math.cos(Math.toRadians(yaw - 90)) * strafe * this.flightSpeed;
        
        mc.thePlayer.motionX = motionX;
        mc.thePlayer.motionZ = motionZ;
        
        // Handle up/down
        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.thePlayer.motionY = this.flightSpeed;
        } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.thePlayer.motionY = -this.flightSpeed;
        } else {
            mc.thePlayer.motionY = 0;
        }
        
        mc.thePlayer.onGround = false;
    }

    public void setMode(FlightMode mode) {
        this.mode = mode;
    }

    public void setFlightSpeed(float speed) {
        this.flightSpeed = speed;
    }

    public FlightMode getMode() {
        return this.mode;
    }

    public float getFlightSpeed() {
        return this.flightSpeed;
    }
}

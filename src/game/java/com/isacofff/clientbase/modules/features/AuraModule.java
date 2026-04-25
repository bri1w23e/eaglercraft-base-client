package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.modules.Module;
import com.isacofff.clientbase.modules.ModuleCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

public class AuraModule extends Module {

    private static final String MODULE_NAME = "Aura";
    private static final String MODULE_DESCRIPTION = "Combat aura module for Meteor Client";
    
    private float range = 4.5f;
    private EntityLivingBase target;

    public AuraModule() {
        super(MODULE_NAME, MODULE_DESCRIPTION, ModuleCategory.COMBAT, false);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.target = null;
    }

    @Override
    public void onUpdate() {
        if (!this.enabled) return;
        
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null || mc.theWorld == null) return;
        
        // Find closest entity
        this.target = findNearestEntity(mc);
        
        if (this.target != null && this.target.isEntityAlive()) {
            // Attack target
            mc.thePlayer.attackTargetEntityWithCurrentItem(this.target);
        }
    }

    @Override
    public void onRender(float partialTicks) {
        if (!this.enabled) return;
        // Render aura visuals here
    }

    private EntityLivingBase findNearestEntity(Minecraft mc) {
        EntityLivingBase nearest = null;
        double distance = this.range;

        for (Object entity : mc.theWorld.loadedEntityList) {
            if (!(entity instanceof EntityLivingBase)) continue;
            
            EntityLivingBase livingEntity = (EntityLivingBase) entity;
            
            if (livingEntity == mc.thePlayer || !livingEntity.isEntityAlive()) continue;
            if (livingEntity instanceof net.minecraft.entity.player.EntityPlayer) {
                double dist = mc.thePlayer.getDistanceToEntity(livingEntity);
                if (dist < distance) {
                    distance = dist;
                    nearest = livingEntity;
                }
            }
        }

        return nearest;
    }

    public void setRange(float range) {
        this.range = range;
    }
}

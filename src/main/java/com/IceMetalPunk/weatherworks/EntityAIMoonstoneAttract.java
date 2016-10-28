package com.IceMetalPunk.weatherworks;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIMoonstoneAttract extends EntityAIBase {
	private EntityLiving entity=null; 
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private double speed;
    private static final String __OBFID = "CL_00001608";

    public EntityAIMoonstoneAttract(EntityLiving p_i1648_1_, double p_i1648_2_)
    {
        this.entity = p_i1648_1_;
        this.speed = p_i1648_2_;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
    	int bX=(int)Math.floor(this.entity.posX);
    	int bY=(int)Math.floor(this.entity.posY);
    	int bZ=(int)Math.floor(this.entity.posZ);
    	boolean foundMoonstone=false;
    	int xx=0, yy=0, zz=0;
    	
    	outerLoop:
    	for (xx=-10; xx<=10; ++xx) {
    		for (yy=-10; yy<=10; ++yy) {
    			for (zz=-10; zz<=10; ++zz) {
    				Block block=this.entity.worldObj.getBlock(bX+xx, bY+yy, bZ+zz);
    				if (block instanceof BlockMoonstone) {
    					foundMoonstone=true;
    					break outerLoop;
    				}
    			}
    		}
    	}

    	if (!foundMoonstone) {
    		return false;
    	}
        else
        {
        	this.xPosition = bX+xx;
            this.yPosition = bY+yy;
            this.zPosition = bZ+zz;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !this.entity.getNavigator().noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
    }
}

package com.IceMetalPunk.weatherworks;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class WeatherManipulatorRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation field_147523_b = new ResourceLocation("textures/entity/beacon_beam.png");
    public void renderTileEntityAt(TileEntityWeatherManipulator tileEnt, double rX, double rY, double rZ, float timeParam)
    {
        byte f1 = tileEnt.getActiveLevel();
        byte maxLevel= tileEnt.beamTime;
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
    	
        if (f1 > 0)
        {
            Tessellator tessellator = Tessellator.instance;
            this.bindTexture(field_147523_b);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDepthMask(true);
            OpenGlHelper.glBlendFunc(770, 1, 1, 0);
            float f2 = (float)tileEnt.getWorldObj().getTotalWorldTime() + timeParam;
            float f3 = -f2 * 0.2F - (float)MathHelper.floor_float(-f2 * 0.1F);
            byte b0 = 1;
            double d3 = (double)f2 * 0.025D * (1.0D - (double)(b0 & 1) * 2.5D);
            tessellator.startDrawingQuads();
            tessellator.setBrightness(255);
            
            /* Should set the blending color to white, right? But it's black. */
            tessellator.setColorRGBA(255, 255, 255, 32);
            
            double d5 = (double)b0 * 0.2D;
            double d7 = 0.5D + Math.cos(d3 + 2.356194490192345D) * d5;
            double d9 = 0.5D + Math.sin(d3 + 2.356194490192345D) * d5;
            double d11 = 0.5D + Math.cos(d3 + (Math.PI / 4D)) * d5;
            double d13 = 0.5D + Math.sin(d3 + (Math.PI / 4D)) * d5;
            double d15 = 0.5D + Math.cos(d3 + 3.9269908169872414D) * d5;
            double d17 = 0.5D + Math.sin(d3 + 3.9269908169872414D) * d5;
            double d19 = 0.5D + Math.cos(d3 + 5.497787143782138D) * d5;
            double d21 = 0.5D + Math.sin(d3 + 5.497787143782138D) * d5;
            double b=1020-4*rY;
    		double x=(double)f1/(double)maxLevel;
    		double d23=b*x-b*x*x+rY;
            double d25 = 0.0D;
            double d27 = 1.0D;
            double d28 = (double)(-1.0F + f3);
            double d29=1;
            
            tessellator.addVertexWithUV(rX + d7, d23, rZ + d9, d27, d29);
            tessellator.addVertexWithUV(rX + d7, rY, rZ + d9, d27, d28);
            tessellator.addVertexWithUV(rX + d11, rY, rZ + d13, d25, d28);
            tessellator.addVertexWithUV(rX + d11, d23, rZ + d13, d25, d29);
            tessellator.addVertexWithUV(rX + d19, d23, rZ + d21, d27, d29);
            tessellator.addVertexWithUV(rX + d19, rY, rZ + d21, d27, d28);
            tessellator.addVertexWithUV(rX + d15, rY, rZ + d17, d25, d28);
            tessellator.addVertexWithUV(rX + d15, d23, rZ + d17, d25, d29);
            tessellator.addVertexWithUV(rX + d11, d23, rZ + d13, d27, d29);
            tessellator.addVertexWithUV(rX + d11, rY, rZ + d13, d27, d28);
            tessellator.addVertexWithUV(rX + d19, rY, rZ + d21, d25, d28);
            tessellator.addVertexWithUV(rX + d19, d23, rZ + d21, d25, d29);
            tessellator.addVertexWithUV(rX + d15, d23, rZ + d17, d27, d29);
            tessellator.addVertexWithUV(rX + d15, rY, rZ + d17, d27, d28);
            tessellator.addVertexWithUV(rX + d7, rY, rZ + d9, d25, d28);
            tessellator.addVertexWithUV(rX + d7, d23, rZ + d9, d25, d29);
            tessellator.draw();
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glDepthMask(false);
            tessellator.startDrawingQuads();
            tessellator.setBrightness(255);
            tessellator.setColorRGBA(255, 255, 255, 32);
            
            double d30 = 0.2D;
            double d4 = 0.2D;
            double d6 = 0.8D;
            double d8 = 0.2D;
            double d10 = 0.2D;
            double d12 = 0.8D;
            double d14 = 0.8D;
            double d16 = 0.8D;
            double d18 = d23;
            double d20 = 0.0D;
            double d22 = 1.0D;
            double d24 = (double)(-1.0F + f3);
            double d26 = 1;
            tessellator.addVertexWithUV(rX + d30, d18, rZ + d4, d22, d26);
            tessellator.addVertexWithUV(rX + d30, rY, rZ + d4, d22, d24);
            tessellator.addVertexWithUV(rX + d6, rY, rZ + d8, d20, d24);
            tessellator.addVertexWithUV(rX + d6, d18, rZ + d8, d20, d26);
            tessellator.addVertexWithUV(rX + d14, d18, rZ + d16, d22, d26);
            tessellator.addVertexWithUV(rX + d14, rY, rZ + d16, d22, d24);
            tessellator.addVertexWithUV(rX + d10, rY, rZ + d12, d20, d24);
            tessellator.addVertexWithUV(rX + d10, d18, rZ + d12, d20, d26);
            tessellator.addVertexWithUV(rX + d6, d18, rZ + d8, d22, d26);
            tessellator.addVertexWithUV(rX + d6, rY, rZ + d8, d22, d24);
            tessellator.addVertexWithUV(rX + d14, rY, rZ + d16, d20, d24);
            tessellator.addVertexWithUV(rX + d14, d18, rZ + d16, d20, d26);
            tessellator.addVertexWithUV(rX + d10, d18, rZ + d12, d22, d26);
            tessellator.addVertexWithUV(rX + d10, rY, rZ + d12, d22, d24);
            tessellator.addVertexWithUV(rX + d30, rY, rZ + d4, d20, d24);
            tessellator.addVertexWithUV(rX + d30, d18, rZ + d4, d20, d26);
            tessellator.draw();
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDepthMask(true);
        }
    }

    public void renderTileEntityAt(TileEntity tileEnt, double rX, double rY, double rZ, float timeParam)
    {
        this.renderTileEntityAt((TileEntityWeatherManipulator)tileEnt, rX, rY, rZ, timeParam);
    }
}
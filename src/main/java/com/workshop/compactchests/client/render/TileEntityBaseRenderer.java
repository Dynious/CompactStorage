package com.workshop.compactchests.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

/**
 * Author Professor Mobius
 */
public abstract class TileEntityBaseRenderer extends TileEntitySpecialRenderer
{
    protected float scale = 1f/256f;
    protected RenderBlocks renderBlocks = new RenderBlocks();
    protected RenderItem renderItem   = new RenderItem();

    protected Minecraft mc = Minecraft.getMinecraft();
    protected TextureManager texManager = mc.renderEngine;
    protected FontRenderer renderFont = mc.fontRenderer;

    protected static byte ALIGNLEFT = 0x00;
    protected static byte ALIGNCENTER = 0x01;
    protected static byte ALIGNRIGHT = 0x02;

    public void renderTextOnBlock(String renderString, ForgeDirection side, ForgeDirection orientation, Vector3f barrelPos, float size, double posx, double posy, int red, int green, int blue, int alpha, byte align)
    {
        int color = (alpha << 24) | (red << 16) | (blue << 8) | green;
        this.renderTextOnBlock(renderString, side, orientation, barrelPos, size, posx, posy, color, align);
    }

    protected void renderTextOnBlock(String renderString, ForgeDirection side, ForgeDirection orientation, Vector3f barrelPos, float size, double posx, double posy, int color, byte align)
    {
        this.renderTextOnBlock(renderString, side, orientation, barrelPos, size, posx, posy, 0F, color, align);
    }

    protected void renderTextOnBlock(String renderString, ForgeDirection side, ForgeDirection orientation, Vector3f barrelPos, float size, double posx, double posy, float angle, int color, byte align)
    {
        if (renderString == null || renderString.equals("")){return;}

        int stringWidth = this.func_147498_b().getStringWidth(renderString);

        GL11.glPushMatrix();

        this.alignRendering(side, orientation, barrelPos);
        this.moveRendering(size, posx, posy, -0.01);

        GL11.glRotatef(angle, 0.0f, 0.0f, 1.0f);

        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_LIGHTING);

        switch (align)
        {
            case 0:
                this.func_147498_b().drawString(renderString, 0, 0, color);
                break;
            case 1:
                this.func_147498_b().drawString(renderString, -stringWidth / 2, 0, color);
                break;
            case 2:
                this.func_147498_b().drawString(renderString, -stringWidth, 0, color);
                break;
        }

        GL11.glDepthMask(true);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    protected void renderStackOnBlock(ItemStack stack, ForgeDirection side, ForgeDirection orientation, Vector3f barrelPos, float size, double posx, double posy)
    {
        if (stack == null){return;}

        GL11.glPushMatrix();

        this.alignRendering(side, orientation, barrelPos);
        this.moveRendering(size, posx, posy, -0.001);

        if (!ForgeHooksClient.renderInventoryItem(this.renderBlocks, this.texManager, stack, true, 0.0F, 0.0F, 0.0F))
        {
            this.renderItem.renderWithColor = true;

            this.renderItem.renderItemIntoGUI(this.renderFont, this.texManager, stack, 0, 0);
        }

        GL11.glPopMatrix();
    }

    protected void alignRendering(ForgeDirection side, ForgeDirection orientation, Vector3f position)
    {
        GL11.glTranslated(position.x + 0.5F, position.y + 0.5F, position.z + 0.5F);     // We align the rendering on the center of the block
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(this.getRotationYForSide(side, orientation), 0.0F, 1.0F, 0.0F); // We rotate it so it face the right face
        GL11.glRotatef(this.getRotationXForSide(side), 1.0F, 0.0F, 0.0F);
        GL11.glTranslated(-0.5F, -0.5F, -0.5f);
    }

    protected void moveRendering(float size, double posX, double posY, double posz)
    {
        GL11.glTranslated(0, 0, posz);
        GL11.glScalef(scale, scale, -0.0001f);			  // We flatten the rendering and scale it to the right size
        GL11.glTranslated(posX, posY, 0);		  // Finally, we translate the icon itself to the correct position
        GL11.glScalef(size, size, 1.0f);
    }

    protected float getRotationYForSide(ForgeDirection side, ForgeDirection orientation)
    {
        int orientRotation[] = {0,0,0,2,3,1,0};
        int sideRotation[]  = {orientRotation[orientation.ordinal()],orientRotation[orientation.ordinal()],0,2,3,1};
        return sideRotation[side.ordinal()] * 90F;
    }

    protected float getRotationXForSide(ForgeDirection side)
    {
        int sideRotation[]  = {1,3,0,0,0,0};
        return sideRotation[side.ordinal()] * 90F;
    }
}

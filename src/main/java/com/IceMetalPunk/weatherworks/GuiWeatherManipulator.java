package com.IceMetalPunk.weatherworks;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiWeatherManipulator extends GuiScreen {
	private TileEntityWeatherManipulator tileEntity;
	private GuiButton weatherTypeBtn;
	private GuiButton doneBtn;
	private byte typeSet=0;
	private static final ResourceLocation bgTex=new ResourceLocation("weatherworks:textures/gui/manipulatorGui.png");
	private int xSize=176;
	private int ySize=166;
	
	public GuiWeatherManipulator(TileEntity ent) {
		super();
		this.tileEntity=(TileEntityWeatherManipulator) ent;
		//this.initGui();
	}
	
	@Override
	public void initGui() {
		this.buttonList.clear();
		this.weatherTypeBtn=new GuiButton(0, this.width/2-75, this.height/2-22, 150, 20, "Clear");
		this.doneBtn=new GuiButton(1, this.width/2-75, this.height/2+2, 150, 20, I18n.format("gui.done", new Object[]{}));
		this.buttonList.add(this.weatherTypeBtn);
		this.buttonList.add(this.doneBtn);
		this.setButton();
	}
	
	
	@Override
	public void actionPerformed(GuiButton but) {
		if (but.id==0) {
			this.typeSet=(byte) ((this.typeSet+1)%3);
			if (this.typeSet==0) { this.weatherTypeBtn.displayString="Clear"; }
			else if (this.typeSet==1) { this.weatherTypeBtn.displayString="Rain"; }
			else if (this.typeSet==2) { this.weatherTypeBtn.displayString="Thunder"; }
			this.tileEntity.updateState(this.typeSet);
			WeatherworksPacketHandler.INSTANCE.sendToServer(new WeatherManipMessage((byte)0, this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord, this.typeSet));
		}
		else if (but.id==1) {
			this.mc.displayGuiScreen(null);
		}
	}
	
	private void setButton() {
		this.typeSet=this.tileEntity.getState();
		if (this.typeSet==0) { this.weatherTypeBtn.displayString="Clear"; }
		else if (this.typeSet==1) { this.weatherTypeBtn.displayString="Rain"; }
		else if (this.typeSet==2) { this.weatherTypeBtn.displayString="Thunder"; }
	}
	
	@Override
	public void drawScreen(int x, int y, float floatParam) {
		this.drawGuiContainerBackgroundLayer(floatParam, x, y);
		this.drawGuiContainerForegroundLayer(x, y);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.drawCenteredString(this.fontRendererObj, "Weather type: ", this.width/2, this.height/2-42, 16777215);
		for (int p=0; p<this.buttonList.size(); ++p) {
			((GuiButton)this.buttonList.get(p)).drawButton(this.mc, mouseX, mouseY);
		}
	}
	
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(bgTex);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2 + 20;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}
}

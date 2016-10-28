package com.IceMetalPunk.weatherworks;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class WeatherManipMessage implements IMessage {

	public byte val=0;
	public double x, y, z;
	public byte messageType=0;
	
	public WeatherManipMessage() {};
	
	public WeatherManipMessage(byte type, double x, double y, double z, byte state) {
		this.val=state;
		this.x=x;
		this.y=y;
		this.z=z;
		this.messageType=type;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.messageType=buf.readByte();
		this.x=buf.readDouble();
		this.y=buf.readDouble();
		this.z=buf.readDouble();
		this.val=buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(this.messageType);
		buf.writeDouble(this.x);
		buf.writeDouble(this.y);
		buf.writeDouble(this.z);
		buf.writeByte(this.val);
	}
	
	public static class WeatherWorksMessageHandler implements IMessageHandler<WeatherManipMessage, IMessage> {

		@Override
		public IMessage onMessage(WeatherManipMessage message, MessageContext ctx) {
			if (message.messageType==0) {
				EntityPlayer player=ctx.getServerHandler().playerEntity;
				World world=player.worldObj;
				TileEntityWeatherManipulator tileEntity=(TileEntityWeatherManipulator)world.getTileEntity((int)message.x, (int)message.y, (int)message.z);
				tileEntity.updateState(message.val);
			}
			return null;
		}

	}
	
	public static class WeatherWorksManipLevelHandler implements IMessageHandler<WeatherManipMessage, IMessage> {

		@Override
		public IMessage onMessage(WeatherManipMessage message, MessageContext ctx) {
			if (message.messageType==1) {
				EntityPlayer player=Minecraft.getMinecraft().thePlayer;
				World world=player.worldObj;
				TileEntityWeatherManipulator tileEntity=(TileEntityWeatherManipulator)world.getTileEntity((int)message.x, (int)message.y, (int)message.z);
				if (tileEntity!=null) {
					tileEntity.setActiveLevel(message.val);
				}
			}
			return null;
		}

	}
	
}

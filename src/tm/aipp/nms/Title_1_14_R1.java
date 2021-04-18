package tm.aipp.nms;

import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_14_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_14_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_14_R1.PacketPlayOutTitle.EnumTitleAction;

public class Title_1_14_R1 implements Title{

	public void sendTitle(Player p, String titlem, String subtitlem) {
		PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\":\"" + titlem +"\"}"),5,60,20);
		PacketPlayOutTitle subtitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a("{\"text\":\"" + subtitlem +"\"}"),5,60,20);
		
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(title);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(subtitle);
		
	}
}

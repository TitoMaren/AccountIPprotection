package tm.aipp.nms;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Title_1_8_R3 implements Title{

	public void sendTitle(Player p, String titlem, String subtitlem) {
		
		PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\":\"" + titlem +"\"}"),5,60,20);
		PacketPlayOutTitle subtitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a("{\"text\":\"" + subtitlem +"\"}"),5,60,20);
		
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(title);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(subtitle);
	}
}

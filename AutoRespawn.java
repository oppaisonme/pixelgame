import java.lang.reflect.Field;
import net.minecraft.server.v1_7_R1.EnumClientCommand;
import net.minecraft.server.v1_7_R1.PacketPlayInClientCommand;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import javax.swing.JComponent;
public class AutoRespawn extends JComponent implements Listener{   
    @Override
    public void onEnable(){
        this.getServer().getPluginManager().registerEvents(this, this);
    }
    @EventHandler
    public void onRespawn(PlayerDeathEvent ev){
        final Player plr = ev.getEntity();
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                PacketPlayInClientCommand packet = new PacketPlayInClientCommand();
                try {
                    Field a = PacketPlayInClientCommand.class.getDeclaredField("a");
                    a.setAccessible(true);
                    a.set(packet, EnumClientCommand.PERFORM_RESPAWN);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {   
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                ((CraftPlayer) plr).getHandle().playerConnection.a(packet);
            }
        }, 2L);
    }
}
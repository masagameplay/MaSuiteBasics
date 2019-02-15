package fi.matiaspaavilainen.masuitebasics;

import fi.matiaspaavilainen.masuitebasics.commands.FlyCommand;
import fi.matiaspaavilainen.masuitebasics.commands.GameModeCommand;
import fi.matiaspaavilainen.masuitecore.core.configuration.BukkitConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Aljosha on 13.02.2019
 */
public class MaSuiteBasics extends JavaPlugin {

    private final BukkitConfiguration config = new BukkitConfiguration();

    @Override
    public void onEnable() {
        config.create(this, "basics", "config.yml");
        config.create(this, "basics", "messages.yml");
        config.create(this, "basics", "syntax.yml");

        getCommand("gamemode").setExecutor(new GameModeCommand());
        getCommand("fly").setExecutor(new FlyCommand());
    }
}

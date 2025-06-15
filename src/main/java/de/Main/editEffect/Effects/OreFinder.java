package de.Main.editEffect.Effects;

import de.Main.editEffect.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class OreFinder implements Listener {

    private final int RADIUS = 20;
    private final int POINTS = 20;
    private final int PARTICLES_PER_POINT = 1;
    private final int MAX_ORES = 10;
    private final long DURATION_TICKS = 5 * 20L; // 5 Sekunden

    @EventHandler(ignoreCancelled = true)
    public void onShiftRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (!player.isSneaking()) {
            return;
        }

        if (event.getItem() == null || !event.getItem().getType().toString().contains("DIAMOND")) {
            return;
        }

        Location playerLoc = player.getLocation();
        Vector playerDir = player.getEyeLocation().getDirection().normalize();

        List<Location> oreLocations = new ArrayList<>();

        outerloop:
        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int y = -RADIUS; y <= RADIUS; y++) {
                for (int z = -RADIUS; z <= RADIUS; z++) {
                    Location checkLoc = playerLoc.clone().add(x, y, z);
                    Material mat = checkLoc.getBlock().getType();

                    if (isOre(mat)) {
                        Vector toBlock = checkLoc.toVector().subtract(playerLoc.toVector()).normalize();
                        double dot = playerDir.dot(toBlock);

                        if (dot > 0.707) { // max 45° Winkel
                            oreLocations.add(checkLoc);
                            if (oreLocations.size() >= MAX_ORES) {
                                break outerloop;
                            }
                        }
                    }
                }
            }
        }


        spawnMovingParticleLines(player, oreLocations, DURATION_TICKS);
    }

    private boolean isOre(Material mat) {
        switch (mat) {
            case DIAMOND_ORE:
            case GOLD_ORE:
            case IRON_ORE:
            case COAL_ORE:
            case EMERALD_ORE:
            case LAPIS_ORE:
            case REDSTONE_ORE:
            case NETHER_GOLD_ORE:
            case NETHER_QUARTZ_ORE:
            case ANCIENT_DEBRIS:
                return true;
            default:
                return false;
        }
    }

    private Particle getOreParticle(Material mat) {
        switch (mat) {
            case DIAMOND_ORE:
                return Particle.DRAGON_BREATH;
            case GOLD_ORE:
            case NETHER_GOLD_ORE:
                return Particle.FLAME;
            case IRON_ORE:
                return Particle.CRIT;
            case COAL_ORE:
                return Particle.LARGE_SMOKE;
            case EMERALD_ORE:
                return Particle.HAPPY_VILLAGER;
            case LAPIS_ORE:
                return Particle.RAIN;
            case REDSTONE_ORE:
                return Particle.COMPOSTER;
            case ANCIENT_DEBRIS:
                return Particle.ANGRY_VILLAGER;
            default:
                return Particle.FLAME;
        }
    }

    private void spawnParticleLine(Player player, Location start, Location end, Particle particle) {
        double dx = (end.getX() - start.getX()) / POINTS;
        double dy = (end.getY() - start.getY()) / POINTS;
        double dz = (end.getZ() - start.getZ()) / POINTS;

        for (int i = 0; i <= POINTS; i++) {
            Location point = start.clone().add(dx * i, dy * i + 0.5, dz * i);
            player.spawnParticle(particle, point, PARTICLES_PER_POINT, 0, 0, 0, 0);
        }
    }

    private void spawnMovingParticleLines(Player player, List<Location> oreLocations, long durationTicks) {
        new BukkitRunnable() {
            long ticksPassed = 0;

            @Override
            public void run() {
                if (ticksPassed >= durationTicks) {
                    this.cancel();
                    return;
                }

                Location playerLoc = player.getLocation().add(0, 0.2, 0);

                for (Location oreLoc : oreLocations) {
                    Material oreMaterial = oreLoc.getBlock().getType();
                    Particle particle = getOreParticle(oreMaterial);
                    spawnParticleLine(player, playerLoc, oreLoc, particle);
                }

                ticksPassed++;
            }
        }.runTaskTimer(Main.getInstance(), 0L, 1L);
    }
}

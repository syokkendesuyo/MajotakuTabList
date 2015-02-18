package net.jp.minecraft.plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

/**
 * 魔女宅用TabListプラグイン
 * MajotakuTabList
 * @author syokkendesuyo
 */

public class MajotakuTabList extends JavaPlugin implements Listener {

	/**
     * プラグインが有効になったときに呼び出されるメソッド
     * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
     */

	private static final String TEAM_OP_NAME = "OP";
	private static final String TEAM_ADMIN_NAME = "Admin";
	private static final String TEAM_BUILDER_NAME = "Builder";
	private static final String TEAM_TEXTURE_NAME = "Texture";
	private static final String TEAM_PLUGIN_NAME = "Plugin";
	private Team op;
	private Team Admin;
	private Team Builder;
	private Team Texture;
	private Team Plugin;


    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);


		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();

		op = board.getTeam(TEAM_OP_NAME);
		if(op == null){
			op = board.registerNewTeam(TEAM_OP_NAME);
			op.setPrefix(ChatColor.GOLD.toString() + "[OP] ");
			op.setDisplayName(TEAM_OP_NAME);
		}

		Admin = board.getTeam(TEAM_ADMIN_NAME);
		if(Admin == null){
			Admin = board.registerNewTeam(TEAM_ADMIN_NAME);
			Admin.setPrefix(ChatColor.RED.toString() + "[Admin] ");
			Admin.setDisplayName(TEAM_ADMIN_NAME);
		}

		Builder = board.getTeam(TEAM_BUILDER_NAME);
		if(Builder == null){
			Builder = board.registerNewTeam(TEAM_BUILDER_NAME);
			Builder.setPrefix(ChatColor.AQUA.toString() + "[Builder] ");
			Builder.setDisplayName(TEAM_BUILDER_NAME);
		}
		Texture = board.getTeam(TEAM_TEXTURE_NAME);
		if(Texture == null){
			Texture = board.registerNewTeam(TEAM_TEXTURE_NAME);
			Texture.setPrefix(ChatColor.GREEN.toString() + "[Texture] ");
			Texture.setDisplayName(TEAM_TEXTURE_NAME);
		}
		Plugin = board.getTeam(TEAM_PLUGIN_NAME);
		if(Plugin == null){
			Plugin = board.registerNewTeam(TEAM_PLUGIN_NAME);
			Plugin.setPrefix(ChatColor.LIGHT_PURPLE.toString() + "[Plugin] ");
			Plugin.setDisplayName(TEAM_PLUGIN_NAME);
		}


    }

	//ログイン時のイベント
	//チャットを行った際、割り込み操作で名前左側に[ADMIN]などを挿入しています。
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {

		Player player = event.getPlayer();

		//初期化
		//プレイヤーのscoreboardデータを初期化し再度チームへ送り込む
		op.removePlayer(player);
		Admin.removePlayer(player);
		Builder.removePlayer(player);
		Texture.removePlayer(player);
		Plugin.removePlayer(player);


			String name = event.getPlayer().getName();

			if(player.hasPermission("majotaku.mark")){
				player.sendMessage("マーク権限あり！マーク更新！");
				player.setDisplayName( name +" * ");
			}


			if(player.isOp()){
				op.addPlayer(player);
				player.sendMessage( ChatColor.AQUA + "[MajotakuTabList]オペレータ権限を保有しています。");
				player.setDisplayName( ChatColor.GOLD  + "[OP] " + ChatColor.WHITE + name );

			}
			else{
				if(player.hasPermission("majotaku.*")){
					Admin.addPlayer(player);
					//player.sendMessage( ChatColor.AQUA + "[MajotakuTabList]ADMIN権限を保有しています。");
					player.setDisplayName( ChatColor.RED +  "[Admin] " + ChatColor.WHITE + name );
				}
				else if(player.hasPermission("majotaku.admin")){
					Admin.addPlayer(player);
					//player.sendMessage( ChatColor.AQUA + "[MajotakuTabList]ADMIN権限を保有しています。");
					player.setDisplayName( ChatColor.RED +  "[Admin] " + ChatColor.WHITE + name );
				}
				else if(player.hasPermission("majotaku.builder")){
					Builder.addPlayer(player);
					//player.sendMessage( ChatColor.AQUA + "[MajotakuTabList]Builder権限を保有しています。");
					player.setDisplayName( ChatColor.AQUA +  "[Builder] " + ChatColor.WHITE + name );
				}
				else if(player.hasPermission("majotaku.texture")){
					Texture.addPlayer(player);
					//player.sendMessage( ChatColor.AQUA + "[MajotakuTabList]Texture権限を保有しています。");
					player.setDisplayName( ChatColor.GREEN +  "[Texture] " + ChatColor.WHITE + name );
				}
				else if(player.hasPermission("majotaku.plugin")){
					Plugin.addPlayer(player);
					//player.sendMessage( ChatColor.AQUA + "[MajotakuTabList]Plugin権限を保有しています。");
					player.setDisplayName( ChatColor.LIGHT_PURPLE +  "[Plugin] " + ChatColor.WHITE + name );
				}
				else if(player.hasPermission("majotaku.visitor")){
					//player.sendMessage( ChatColor.AQUA + "[MajotakuTabList]Visitor権限を保有しています。");
				}
			}
	}
}
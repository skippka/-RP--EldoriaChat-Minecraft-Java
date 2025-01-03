/* Decompiler 106ms, total 302ms, lines 309 */
package me.eldoriaChat.util;

import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import me.eldoriaChat.Main;

public class Values {
   public static String GENDER_INVALID_MESSAGE;
   private static FileConfiguration config;
   public static boolean REGISTRATION_ENABLE;
   public static long REGISTRATION_TITLE_UPDATE;
   public static String REGISTRATION_ENTER_NAME_TITLE;
   public static String REGISTRATION_ENTER_NAME_SUBTITLE;
   public static String REGISTRATION_ENTER_AGE_TITLE;
   public static String REGISTRATION_ENTER_AGE_SUBTITLE;
   public static String REGISTRATION_ENTER_SEX_TITLE;
   public static String REGISTRATION_ENTER_SEX_SUBTITLE;
   public static String REGISTRATION_INCORRECT_FORMAT;
   public static String REGISTRATION_END;
   public static String REGISTRATION_CONFIRMATION_HEADER;
   public static String REGISTRATION_CONFIRMATION_ACCEPT;
   public static String REGISTRATION_CONFIRMATION_RESET;
   public static List<String> REGISTRATION_COMMANDS_WHITELIST;
   public static boolean FRIENDS_ENABLE;
   public static String FRIENDS_REQUEST_SEND;
   public static String FRIENDS_REQUEST_ACCEPT_ACCEPTER;
   public static String FRIENDS_REQUEST_ACCEPT_TARGET;
   public static String FRIENDS_REQUEST_DECLINE_DECLINER;
   public static String FRIENDS_REQUEST_DECLINE_TARGET;
   public static String FRIENDS_REMOVE_REMOVER;
   public static String FRIENDS_REMOVE_TARGET;
   public static String FRIENDS_COOLDOWN_MESSAGE;
   public static String FRIENDS_STRANGER_NAME;
   public static String FRIENDS_GUI_REQUEST_TITLE;
   public static String FRIENDS_GUI_REQUEST_ACCEPT;
   public static String FRIENDS_GUI_REQUEST_DECLINE;
   public static String FRIENDS_GUI_FRIEND_TITLE;
   public static String FRIENDS_GUI_FRIEND_REMOVE;
   public static long FRIENDS_COOLDOWN;
   public static String PRISON_HELP;
   public static String PRISON_ALREADY_CREATED;
   public static String PRISON_CREATE;
   public static String PRISON_DELETE;
   public static String PRISON_LIST;
   public static Material[] CRAFT = new Material[9];
   public static ItemStack KEY;
   public static boolean DOOR_INTEGRATION;
   public static String BIRTHDAY_PLACEHOLDER;
   public static String BREAKING_START;
   public static String BREAKING_DISTANCE;
   public static String BREAKING_SUCCESS;
   public static String BREAKING_FAIL;
   public static boolean CHANGENAME_REQUEST_ENABLE;
   public static String CHANGENAME_REQUEST_NO_STAFF;
   public static String CHANGENAME_REQUEST_MESSAGE;
   public static String CHANGENAME_REQUEST_FOR_STAFF;
   public static String CHANGENAME_REQUEST_ACCEPT_FORMAT;
   public static String CHANGENAME_REQUEST_DECLINE_FORMAT;
   public static String CHANGENAME_ACCEPT_TARGET;
   public static String CHANGENAME_ACCEPT_USE;
   public static String CHANGENAME_ACCEPT_NO_REQUEST;
   public static String CHANGENAME_DECLINE_TARGET;
   public static String CHANGENAME_DECLINE_USE;
   public static String CHANGENAME_DECLINE_NO_REQUEST;
   public static String GME_FORMAT;
   public static String GDO_FORMAT;
   public static String GTRY_FORMAT;
   public static String CUBE_FORMAT;
   public static String SH_FORMAT;
   public static String WH_FORMAT;
   public static String COIN_FORMAT;
   public static String TODO_FORMAT;
   public static String NRP_FORMAT;
   public static String ME_FORMAT;
   public static String DO_FORMAT;
   public static String TRY_FORMAT;
   public static String ROLL_FORMAT;
   public static String INFO_GME;
   public static String INFO_GTRY;
   public static String INFO_GDO;
   public static String INFO_NRP;
   public static String INFO_ME;
   public static String INFO_DO;
   public static String INFO_TRY;
   public static String INFO_TODO;
   public static String INFO_WH;
   public static String INFO_SH;
   public static String INFO_RPAGE;
   public static String RPAGE_SUCCESS;
   public static String RPAGE_INCORRECT;
   public static String RPAGE_ALREADY;
   public static String BIRTHDAY_USE;
   public static String BIRTHDAY_NO_DATE;
   public static String BIRTHDAY_ANNOUNCE;
   public static String BIRTHDAY_TIME_ERROR;
   public static int BIRTHDAY_YEAR_MAX;
   public static int BIRTHDAY_YEAR_MIN;
   public static int BIRTHDAY_YEAR_CURRENT;
   public static int RADIUS_CUBE;
   public static int RADIUS_SH;
   public static int RADIUS_WH;
   public static int RADIUS_COIN;
   public static int RADIUS_NRP;
   public static int RADIUS_ME;
   public static int RADIUS_DO;
   public static int RADIUS_TRY;
   public static int RADIUS_TODO;
   public static int RADIUS_ROLL;
   public static String NAMERESET_NO_CHANGE;
   public static String NAMERESET_USE;
   public static String PERMISSION;
   public static String OFFLINE;
   public static String NUMBER_EXCEPTION;
   public static String NO_JAIL;
   public static String INFO_CHANGENAME;
   public static String CHANGENAME;
   public static int CHANGENAME_SIZE_MAX;
   public static int CHANGENAME_SIZE_MIN;
   public static String CHANGENAME_SIZE_MESSAGE;
   public static String NAME_CONTAINS_FORBIDDEN_WORD;
   public static String CHANGENAME_COOLDOWN_MESSSAGE;
   public static List<String> FORBIDDEN_WORDS;
   public static int CHANGENAME_COOLDOWN;
   public static String INFO_ARREST;
   public static String ALREADY_ARREST;
   public static String HIGH_DISTANCE;
   public static String SEND_ARREST_POLICEMAN;
   public static String SEND_ARREST_CRIMINAL;
   public static String INFO_LETOUT;
   public static String NOT_IN_JAIL;
   public static String SEND_LETOUT_POLICEMAN;
   public static String SEND_LETOUT_CRIMINAL;
   public static String LETOUT_TIME;
   public static String INFO_ADDTIME;
   public static String SEND_ADDTIME_POLICEMAN;
   public static String SEND_ADDTIME_CRIMINAL;
   public static String LOCATIONS_HELP;
   public static String SCPRISON;
   public static String SLPRISON;
   public static List<String> BLOCKED_COMMANDS;
   public static String COMMAND_IS_BLOCK;

   public static void load(FileConfiguration file) {
      config = file;
      DOOR_INTEGRATION = config.getBoolean("settings.door-integration", false);
      Material type = Material.matchMaterial(config.getString("settings.key.type", "barrier"));
      KEY = new ItemStack(type == null ? Material.BARRIER : type);
      ItemMeta meta = KEY.getItemMeta();
      meta.setDisplayName(getMessage("settings.key.name"));
      meta.setLore((List)config.getStringList("settings.key.lore").stream().map((t) -> {
         return t.replace("&", "§");
      }).collect(Collectors.toList()));
      KEY.setItemMeta(meta);
      List<String> craftTypes = config.getStringList("settings.key.craft");

      for(int i = 0; i < craftTypes.size(); ++i) {
         String materialFormat = (String)craftTypes.get(i);
         Material material = Material.matchMaterial(materialFormat);
         if (material != null) {
            CRAFT[i] = material;
         } else {
            Main.getInstance().getLogger().warning("Unknown type of key: " + materialFormat);
         }
      }

      CHANGENAME_REQUEST_ENABLE = config.getBoolean("settings.changename.request.enable");
      CHANGENAME_REQUEST_NO_STAFF = getMessage("settings.changename.request.no-staff");
      CHANGENAME_REQUEST_FOR_STAFF = getMessage("settings.changename.request.for-staff");
      CHANGENAME_REQUEST_MESSAGE = getMessage("settings.changename.request.message");
      CHANGENAME_REQUEST_ACCEPT_FORMAT = getMessage("settings.changename.request.accept");
      CHANGENAME_REQUEST_DECLINE_FORMAT = getMessage("settings.changename.request.decline");
      CHANGENAME_ACCEPT_USE = getMessage("settings.changename.accept.use");
      CHANGENAME_ACCEPT_NO_REQUEST = getMessage("settings.changename.accept.no-request");
      CHANGENAME_ACCEPT_TARGET = getMessage("settings.changename.accept.target");
      CHANGENAME_DECLINE_USE = getMessage("settings.changename.decline.use");
      CHANGENAME_DECLINE_NO_REQUEST = getMessage("settings.changename.decline.no-request");
      CHANGENAME_DECLINE_TARGET = getMessage("settings.changename.decline.target");
      BREAKING_START = getMessage("settings.breaking-start");
      BREAKING_DISTANCE = getMessage("settings.breaking-distance");
      BREAKING_SUCCESS = getMessage("settings.breaking-success");
      BREAKING_FAIL = getMessage("settings.breaking-fail");
      GME_FORMAT = getMessage("settings.formats.gme");
      GDO_FORMAT = getMessage("settings.formats.gdo");
      GTRY_FORMAT = getMessage("settings.formats.gtry");
      INFO_GME = getMessage("settings.gme.info");
      INFO_GTRY = getMessage("settings.gtry.info");
      INFO_GDO = getMessage("settings.gdo.info");
      INFO_SH = getMessage("settings.sh.info");
      INFO_WH = getMessage("settings.wh.info");
      INFO_RPAGE = getMessage("settings.rpage.info");
      INFO_TODO = getMessage("settings.todo.info");
      WH_FORMAT = getMessage("settings.formats.wh");
      RADIUS_WH = config.getInt("settings.radiuses.wh");
      SH_FORMAT = getMessage("settings.formats.sh");
      RADIUS_SH = config.getInt("settings.radiuses.sh");
      CUBE_FORMAT = getMessage("settings.formats.cube");
      RADIUS_CUBE = config.getInt("settings.radiuses.cube");
      TODO_FORMAT = getMessage("settings.formats.todo");
      RADIUS_TODO = config.getInt("settings.radiuses.todo");
      COIN_FORMAT = getMessage("settings.formats.coin");
      RADIUS_COIN = config.getInt("settings.radiuses.coin");
      NRP_FORMAT = getMessage("settings.formats.nrp");
      ME_FORMAT = getMessage("settings.formats.me");
      DO_FORMAT = getMessage("settings.formats.do");
      TRY_FORMAT = getMessage("settings.formats.try");
      ROLL_FORMAT = getMessage("settings.formats.roll");
      PERMISSION = getMessage("settings.errors.permission");
      RADIUS_NRP = config.getInt("settings.radiuses.nrp");
      RADIUS_ME = config.getInt("settings.radiuses.me");
      RADIUS_DO = config.getInt("settings.radiuses.do");
      RADIUS_TRY = config.getInt("settings.radiuses.try");
      RADIUS_ROLL = config.getInt("settings.radiuses.roll");
      OFFLINE = getMessage("settings.errors.offline");
      NO_JAIL = getMessage("settings.errors.no-jail");
      NUMBER_EXCEPTION = getMessage("settings.errors.number-exception");
      INFO_CHANGENAME = getMessage("settings.changename.info");
      CHANGENAME = getMessage("settings.changename.use");
      NAMERESET_NO_CHANGE = getMessage("settings.namereset.no-change");
      NAMERESET_USE = getMessage("settings.namereset.use");
      NAME_CONTAINS_FORBIDDEN_WORD = getMessage("settings.changename.name-contains-forbidden-word");
      CHANGENAME_SIZE_MAX = config.getInt("settings.changename-size.max");
      CHANGENAME_SIZE_MIN = config.getInt("settings.changename-size.min");
      CHANGENAME_SIZE_MESSAGE = getMessage("settings.changename.size");
      CHANGENAME_COOLDOWN_MESSSAGE = getMessage("settings.changename.cooldown");
      FORBIDDEN_WORDS = config.getStringList("settings.forbidden-words");
      CHANGENAME_COOLDOWN = config.getInt("settings.cooldown");
      INFO_ARREST = getMessage("settings.arrest.info");
      ALREADY_ARREST = getMessage("settings.arrest.already");
      HIGH_DISTANCE = getMessage("settings.arrest.high-distance");
      SEND_ARREST_POLICEMAN = getMessage("settings.arrest.send-arrest-policeman");
      SEND_ARREST_CRIMINAL = getMessage("settings.arrest.send-arrest-criminal");
      INFO_LETOUT = getMessage("settings.letout.info");
      NOT_IN_JAIL = getMessage("settings.letout.not-in-jail");
      LETOUT_TIME = getMessage("settings.letout.letout-time");
      SEND_LETOUT_POLICEMAN = getMessage("settings.letout.send-letout-policeman");
      SEND_LETOUT_CRIMINAL = getMessage("settings.letout.send-letout-criminal");
      INFO_ADDTIME = getMessage("settings.addtime.info");
      SEND_ADDTIME_POLICEMAN = getMessage("settings.addtime.send-addtime-policeman");
      SEND_ADDTIME_CRIMINAL = getMessage("settings.addtime.send-addtime-criminal");
      SCPRISON = getMessage("settings.locations.scprison");
      SLPRISON = getMessage("settings.locations.slprison");
      INFO_NRP = getMessage("settings.nrp.info");
      INFO_ME = getMessage("settings.me.info");
      INFO_DO = getMessage("settings.do.info");
      INFO_TRY = getMessage("settings.try.info");
      BLOCKED_COMMANDS = config.getStringList("settings.blocked-commands");
      COMMAND_IS_BLOCK = getMessage("settings.errors.command-is-block");
      RPAGE_SUCCESS = getMessage("settings.rpage.success");
      RPAGE_INCORRECT = getMessage("settings.rpage.incorrect");
      RPAGE_ALREADY = getMessage("settings.rpage.already");
      LOCATIONS_HELP = getMessage("settings.locations.help");
      BIRTHDAY_PLACEHOLDER = getMessage("settings.birthday.placeholder");
      BIRTHDAY_USE = getMessage("settings.birthday.use");
      BIRTHDAY_NO_DATE = getMessage("settings.birthday.no-date");
      BIRTHDAY_ANNOUNCE = getMessage("settings.birthday.announce");
      BIRTHDAY_TIME_ERROR = getMessage("settings.birthday.time-error");
      BIRTHDAY_YEAR_MAX = config.getInt("settings.birthday.max");
      BIRTHDAY_YEAR_MIN = config.getInt("settings.birthday.min");
      BIRTHDAY_YEAR_CURRENT = config.getInt("settings.birthday.current");
      PRISON_HELP = getMessage("settings.prison.help");
      PRISON_ALREADY_CREATED = getMessage("settings.prison.already-created");
      PRISON_CREATE = getMessage("settings.prison.create");
      PRISON_DELETE = getMessage("settings.prison.delete");
      PRISON_LIST = getMessage("settings.prison.list");
      FRIENDS_ENABLE = config.getBoolean("settings.friends.enable");
      FRIENDS_REQUEST_SEND = getMessage("settings.friends.request.send");
      FRIENDS_REQUEST_ACCEPT_ACCEPTER = getMessage("settings.friends.request.accept.accepter");
      FRIENDS_REQUEST_ACCEPT_TARGET = getMessage("settings.friends.request.accept.target");
      FRIENDS_REQUEST_DECLINE_DECLINER = getMessage("settings.friends.request.decline.decliner");
      FRIENDS_REQUEST_DECLINE_TARGET = getMessage("settings.friends.request.decline.target");
      FRIENDS_COOLDOWN_MESSAGE = getMessage("settings.friends.cooldown-message");
      FRIENDS_STRANGER_NAME = getMessage("settings.friends.stranger-name");
      FRIENDS_GUI_REQUEST_TITLE = getMessage("settings.friends.gui.request.title");
      FRIENDS_GUI_REQUEST_ACCEPT = getMessage("settings.friends.gui.request.accept");
      FRIENDS_GUI_REQUEST_DECLINE = getMessage("settings.friends.gui.request.decline");
      FRIENDS_GUI_FRIEND_TITLE = getMessage("settings.friends.gui.friend.title");
      FRIENDS_GUI_FRIEND_REMOVE = getMessage("settings.friends.gui.friend.remove");
      FRIENDS_REMOVE_REMOVER = getMessage("settings.friends.remove.remover");
      FRIENDS_REMOVE_TARGET = getMessage("settings.friends.remove.target");
      FRIENDS_COOLDOWN = config.getLong("settings.friends.cooldown") * 1000L;
      REGISTRATION_ENABLE = config.getBoolean("settings.first-join");
      REGISTRATION_TITLE_UPDATE = config.getLong("settings.registration.title-update");
      REGISTRATION_ENTER_AGE_TITLE = getMessage("settings.registration.enter-age.title");
      REGISTRATION_ENTER_AGE_SUBTITLE = getMessage("settings.registration.enter-age.subtitle");
      REGISTRATION_ENTER_SEX_TITLE = getMessage("settings.registration.enter-sex.title");
      REGISTRATION_ENTER_SEX_SUBTITLE = getMessage("settings.registration.enter-sex.subtitle");
      REGISTRATION_ENTER_NAME_TITLE = getMessage("settings.registration.enter-name.title");
      REGISTRATION_ENTER_NAME_SUBTITLE = getMessage("settings.registration.enter-name.subtitle");
      REGISTRATION_INCORRECT_FORMAT = getMessage("settings.registration.incorrect-format");
      REGISTRATION_END = getMessage("settings.registration.end");
      REGISTRATION_CONFIRMATION_HEADER = getMessage("settings.registration.confirmation.header");
      REGISTRATION_CONFIRMATION_ACCEPT = getMessage("settings.registration.confirmation.accept");
      REGISTRATION_CONFIRMATION_RESET = getMessage("settings.registration.confirmation.reset");
      REGISTRATION_COMMANDS_WHITELIST = config.getStringList("settings.registration.commands-whitelist");
   }

   private static String getMessage(String path) {
      return BukkitColors.format(config.getString(path, "no string: " + path));
   }

   private static Object get(String way) {
      Object obj = config.get(way);
      if (obj instanceof String) {
         obj = ((String)obj).replace("&", "§");
      }

      return obj;
   }
}

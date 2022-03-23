package srpattif.encantamentos.Utils;

import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

/**
 * Represents some special mob heads, also support creating player skulls and
 * custom skulls.
 *
 * @author xigsag, SBPrime
 */
public enum Skull {

	ARROW_LEFT("MHF_ArrowLeft"), ARROW_RIGHT("MHF_ArrowRight"), ARROW_UP("MHF_ArrowUp"), ARROW_DOWN("MHF_ArrowDown"), QUESTION("MHF_Question"), EXCLAMATION("MHF_Exclamation"), CAMERA("FHG_Cam"),

	ZOMBIE_PIGMAN("MHF_PigZombie"), PIG("MHF_Pig"), SHEEP("MHF_Sheep"), BLAZE("MHF_Blaze"), CHICKEN("MHF_Chicken"), COW("MHF_Cow"), SLIME("MHF_Slime"), SPIDER("MHF_Spider"), SQUID("MHF_Squid"), VILLAGER("MHF_Villager"), OCELOT("MHF_Ocelot"), HEROBRINE("MHF_Herobrine"), LAVA_SLIME("MHF_LavaSlime"), MOOSHROOM("MHF_MushroomCow"), GOLEM("MHF_Golem"), GHAST("MHF_Ghast"), ENDERMAN("MHF_Enderman"), CAVE_SPIDER("MHF_CaveSpider"),

	CACTUS("MHF_Cactus"), CAKE("MHF_Cake"), CHEST("MHF_Chest"), MELON("MHF_Melon"), LOG("MHF_OakLog"), PUMPKIN("MHF_Pumpkin"), TNT("MHF_TNT"), DYNAMITE("MHF_TNT2");

	private static final Base64 base64 = new Base64();
	private String id;

	private static HashMap<String, UUID> ids = new HashMap<>();
	
	private Skull(String id) {
		this.id = id;
	}

	/**
	 * Return a skull that has a custom texture specified by url.
	 *
	 * @param url
	 *            skin url
	 * @return itemstack
	 */
	public static ItemStack getCustomSkull(String url) {
		UUID uuid = UUID.randomUUID();
		
		if(ids.containsKey(url)) {
			uuid = ids.get(url);
			
		} else {
			ids.put(url, uuid);
		}
		
		GameProfile profile = new GameProfile(uuid, null);
		
		PropertyMap propertyMap = profile.getProperties();
		if (propertyMap == null) {
			throw new IllegalStateException("Profile doesn't contain a property map");
		}
		byte[] encodedData = base64.encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
		propertyMap.put("textures", new Property("textures", new String(encodedData)));
		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		ItemMeta headMeta = head.getItemMeta();
		Class<?> headMetaClass = headMeta.getClass();
		Reflections.getField(headMetaClass, "profile", GameProfile.class).set(headMeta, profile);
		head.setItemMeta(headMeta);
		return head;
	}

	/**
	 * Return a skull of a player.
	 *
	 * @param name
	 *            player's name
	 * @return itemstack
	 */
	public static ItemStack getPlayerSkull(String name) {
		ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
		meta.setOwner(name);
		itemStack.setItemMeta(meta);
		return itemStack;
	}

	/**
	 * Return the skull's id.
	 *
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Return the skull of the enum.
	 *
	 * @return itemstack
	 */
	public ItemStack getSkull() {
		ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
		meta.setOwner(id);
		itemStack.setItemMeta(meta);
		return itemStack;
	}
	
	public static void addUrlToHashMap(String url, UUID uuid) {
		ids.put(url, uuid);
	}
	
	  public static String getSkullFromFirstLetter(String text) {
		    String url = "";
		    String first = text.substring(0, 1).toLowerCase();
		    
		    if (first.equalsIgnoreCase("a")) {
		      url = "http://textures.minecraft.net/texture/a67d813ae7ffe5be951a4f41f2aa619a5e3894e85ea5d4986f84949c63d7672e";
		    }
		    else if (first.equalsIgnoreCase("b")) {
		      url = "http://textures.minecraft.net/texture/50c1b584f13987b466139285b2f3f28df6787123d0b32283d8794e3374e23";
		    }
		    else if (first.equalsIgnoreCase("c")) {
		      url = "http://textures.minecraft.net/texture/abe983ec478024ec6fd046fcdfa4842676939551b47350447c77c13af18e6f";
		    }
		    else if (first.equalsIgnoreCase("d")) {
		      url = "http://textures.minecraft.net/texture/3193dc0d4c5e80ff9a8a05d2fcfe269539cb3927190bac19da2fce61d71";
		    }
		    else if (first.equalsIgnoreCase("e")) {
		      url = "http://textures.minecraft.net/texture/dbb2737ecbf910efe3b267db7d4b327f360abc732c77bd0e4eff1d510cdef";
		    }
		    else if (first.equalsIgnoreCase("f")) {
		      url = "http://textures.minecraft.net/texture/b183bab50a3224024886f25251d24b6db93d73c2432559ff49e459b4cd6a";
		    }
		    else if (first.equalsIgnoreCase("g")) {
		      url = "http://textures.minecraft.net/texture/1ca3f324beeefb6a0e2c5b3c46abc91ca91c14eba419fa4768ac3023dbb4b2";
		    }
		    else if (first.equalsIgnoreCase("h")) {
		      url = "http://textures.minecraft.net/texture/31f3462a473549f1469f897f84a8d4119bc71d4a5d852e85c26b588a5c0c72f";
		    }
		    else if (first.equalsIgnoreCase("i")) {
		      url = "http://textures.minecraft.net/texture/46178ad51fd52b19d0a3888710bd92068e933252aac6b13c76e7e6ea5d3226";
		    }
		    else if (first.equalsIgnoreCase("j")) {
		      url = "http://textures.minecraft.net/texture/3a79db9923867e69c1dbf17151e6f4ad92ce681bcedd3977eebbc44c206f49";
		    }
		    else if (first.equalsIgnoreCase("k")) {
		      url = "http://textures.minecraft.net/texture/9461b38c8e45782ada59d16132a4222c193778e7d70c4542c9536376f37be42";
		    }
		    else if (first.equalsIgnoreCase("l")) {
		      url = "http://textures.minecraft.net/texture/319f50b432d868ae358e16f62ec26f35437aeb9492bce1356c9aa6bb19a386";
		    }
		    else if (first.equalsIgnoreCase("m")) {
		      url = "http://textures.minecraft.net/texture/49c45a24aaabf49e217c15483204848a73582aba7fae10ee2c57bdb76482f";
		    }
		    else if (first.equalsIgnoreCase("n")) {
		      url = "http://textures.minecraft.net/texture/35b8b3d8c77dfb8fbd2495c842eac94fffa6f593bf15a2574d854dff3928";
		    }
		    else if (first.equalsIgnoreCase("o")) {
		      url = "http://textures.minecraft.net/texture/d11de1cadb2ade61149e5ded1bd885edf0df6259255b33b587a96f983b2a1";
		    }
		    else if (first.equalsIgnoreCase("p")) {
		      url = "http://textures.minecraft.net/texture/a0a7989b5d6e621a121eedae6f476d35193c97c1a7cb8ecd43622a485dc2e912";
		    }
		    else if (first.equalsIgnoreCase("q")) {
		      url = "http://textures.minecraft.net/texture/43609f1faf81ed49c5894ac14c94ba52989fda4e1d2a52fd945a55ed719ed4";
		    }
		    else if (first.equalsIgnoreCase("r")) {
		      url = "http://textures.minecraft.net/texture/a5ced9931ace23afc351371379bf05c635ad186943bc136474e4e5156c4c37";
		    }
		    else if (first.equalsIgnoreCase("s")) {
		      url = "http://textures.minecraft.net/texture/3e41c60572c533e93ca421228929e54d6c856529459249c25c32ba33a1b1517";
		    }
		    else if (first.equalsIgnoreCase("t")) {
		      url = "http://textures.minecraft.net/texture/1562e8c1d66b21e459be9a24e5c027a34d269bdce4fbee2f7678d2d3ee4718";
		    }
		    else if (first.equalsIgnoreCase("u")) {
		      url = "http://textures.minecraft.net/texture/607fbc339ff241ac3d6619bcb68253dfc3c98782baf3f1f4efdb954f9c26";
		    }
		    else if (first.equalsIgnoreCase("v")) {
		      url = "http://textures.minecraft.net/texture/cc9a138638fedb534d79928876baba261c7a64ba79c424dcbafcc9bac7010b8";
		    }
		    else if (first.equalsIgnoreCase("w")) {
		      url = "http://textures.minecraft.net/texture/269ad1a88ed2b074e1303a129f94e4b710cf3e5b4d995163567f68719c3d9792";
		    }
		    else if (first.equalsIgnoreCase("x")) {
		      url = "http://textures.minecraft.net/texture/5a6787ba32564e7c2f3a0ce64498ecbb23b89845e5a66b5cec7736f729ed37";
		    }
		    else if (first.equalsIgnoreCase("y")) {
		      url = "http://textures.minecraft.net/texture/c52fb388e33212a2478b5e15a96f27aca6c62ac719e1e5f87a1cf0de7b15e918";
		    }
		    else if (first.equalsIgnoreCase("z")) {
		      url = "http://textures.minecraft.net/texture/90582b9b5d97974b11461d63eced85f438a3eef5dc3279f9c47e1e38ea54ae8d";
		    }
		    url = "http://textures.minecraft.net/texture/0ebe7e5215169a699acc6cefa7b73fdb108db87bb6dae2849fbe24714b27";
		    
		    return url;
		  }

}
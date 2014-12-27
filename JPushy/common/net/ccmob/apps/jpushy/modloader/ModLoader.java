package net.ccmob.apps.jpushy.modloader;

import java.util.ArrayList;

public class ModLoader {

	public final static String modsFolder = "mods/";
	private static ArrayList<Mod> mods = new ArrayList<Mod>();
	private static ArrayList<ModEventHandler> modEventHandler = new ArrayList<ModEventHandler>();
  
  public static void registerMod(Mod m){
  	if(!mods.contains(m)){
  		for(Mod m_ : mods){
  			if(m_.getModID() == m.getModID()){
  				return;
  			}
  		}
  		mods.add(m);
  	}else{
  		return;
  	}
  }
  
  public static void queueEvent(ModEventType type, ModEventArg arg){
  	for(ModEventHandler handler: modEventHandler){
  			handler.handleEvent(type, arg);
  	}
  }
  
  public static void loadMods(){
  	ModResourceLoader.loadPlugins(modsFolder);
  }
  
  public static void onPreInit(){
  	for(Mod m : mods)
  		m.onPreInit();
  }
  
  public static void onInit(){
  	for(Mod m : mods)
  		m.onInit();
  }
  
  public static void onPostInit(){
  	for(Mod m : mods)
  		m.onPostInit();
  }
	
  public static void registerEventHandler(ModEventHandler eventHandler){
  	System.out.println("Registrating event handler");
  	modEventHandler.add(eventHandler);
  }
  
}

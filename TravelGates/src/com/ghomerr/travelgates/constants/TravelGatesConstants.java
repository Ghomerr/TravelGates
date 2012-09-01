package com.ghomerr.travelgates.constants;

public interface TravelGatesConstants
{	
	public static final String MINECRAFT = "Minecraft";
	
	public static final String PLUGIN_TAG = "[TRAVELGATES]";
	
	public static final String DEBUG_TAG = "[DEBUG_TG]";
	
	public static final String DEST_FILE_NAME = "destinations.dat";
	
	public static final String RESTRICTIONS_FILE_NAME = "restrictions.dat";
	
	public static final String CONFIG_FILE_NAME = "travelgates.conf";
	
	public static final String LANGUAGE_TAG = "{{LANG}}";
	
	public static final String LANGUAGE_FILE_NAME = "messages_" + LANGUAGE_TAG + ".lang";
	
	public static final String WILDCARD = "*";
	
	public static final String PLUGINS_DIRECTORY = "plugins";
	
	public static final String PLUGIN_ROOT_PATH = PLUGINS_DIRECTORY + "/TravelGates";
	
	public static final String PLUGIN_FILE_PATH = PLUGIN_ROOT_PATH + "/" + DEST_FILE_NAME;
	
	public static final String PLUGIN_RESTRICTIONS_FILE_PATH = PLUGIN_ROOT_PATH + "/" + RESTRICTIONS_FILE_NAME;
	
	public static final String PLUGIN_CONFIG_PATH = PLUGIN_ROOT_PATH + "/" + CONFIG_FILE_NAME;
	
	public static final String PLUGIN_MESSAGES_PATH = PLUGIN_ROOT_PATH + "/" + LANGUAGE_FILE_NAME;
	
	public static final String PLUGIN_MESSAGES_RESOURCES = "messages/messages_default.lang";

	public static final String DESTINATION_NAME_PATTERN = "^[a-zA-Z0-9_-]{1,20}$";
	
	public static final String UPDATE_RESTRICTIONS_PATTERN = "^([+-][a-zA-Z0-9_-]{1,20},?)+$";
	
	public static final String ADD_RESTRICTION_PATTERN = "^[+][a-zA-Z0-9_-]{1,20}$";
	
	public static final String REMOVE_RESTTRICTION_PATTERN = "^[-][a-zA-Z0-9_-]{1,20}$";
	
	public static final String BLANK_STRING_PATTERN = "^[ ]+$";
	
	public static final String DELIMITER = ",";
	
	public static final String OPTION_PREFIX = "-";
	
	public static final String VAR_MESSAGE_TAG = "\\{[a-zA-Z0-9_-]+\\}";
	
	public static final String DEFAULT_LANGUAGE = "default";
	
	public static final String PLUGIN_PERMISSONS = "Permissions";
	
	public static final String PLUGIN_PERMISSIONS_BUKKIT = "PermissionsBukkit";
	
	public static final String PLUGIN_PERMISSIONS_EX = "PermissionsEx";
	
	public static final int REGULAR_SIGN = -1;
	
	public static final int NO_VALID_TRAVELGATE_SIGN = -2;
	
	public static final int NO_DESTINATION_ON_SIGN = -3;
	
	public static final int NO_AVAILABLE_LINE_ON_SIGN = -4;
	
	public static final String PERMISSIONS_ROOT = "travelgates.";
	
	public static final String START_POSITION = "(";
	
	public static final String END_POSITION = ")";
	
	public static final String START_RESTRICTIONS = "{";
	
	public static final String END_RESTRICTIONS = "}";
	
	public static final int MAX_COORDINATE = 30000000;
	
	public static final int MIN_COORDINATE = -30000000;
	
	public static final String UNKNOWN_HEIGHT = "?";
	
	public static final int POSITION_WITHOUT_WORLD = 0;
	
	public static final int POSITION_WITH_WORLD = 1;
	
	public static final int MAX_TARGET_RANGE = 5;
}

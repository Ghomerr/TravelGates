################################################
######## TRAVELGATES PLUGIN BY GHOMERR #########
################################################

This plugin is multilangual and uses a configuration file. 
This plugin must be used with Craftbukkit version 1.1-Rx. Previous versions won't work. (New events system compatibility)


################################################
############## SETUP FROM SCRATCH ##############
################################################

Unzip the archive TravelGates-x.x.x.zip
Move the TravelGates-x.x.x.jar in your "plugins" directory (in the server directory)
Unzip the archive resources-x.x.x.zip
Create a directory named "TravelGates" in the previous "plugins" directory
Copy inside this directory the files : travelgates.conf file and the message_xx.lang of your choice (or make your own, see the section "LANGUAGE FILE" below)
Configure the configuration file as you want (see the next section below for more information)
Start your server and enjoy !

Data files will be created with they don't exist.

If you have already installed this plugin:
    Keep your TravelGates directory to save your configuration and the saved destinations and other data.
    Remove the old TravelGates-x.x.x.jar and replace it by the new one.
    Replace the language file if messages have been updated.
    Restart the server (or reload plugins)
    

################################################
############## CONFIGURATION FILE ##############
################################################

The configuration file is "travelgates.conf" and has to be placed in the "TravelGates" directory in your plugins directory.
"TravelGates" directory is created automatically if it does not exist.
If this directory is missing, you'd have to create it yourself because this plugin will be disabled if data can't be stored.
Each missing parameter will be ignored and the default value will be used. (See the example configuration file)

Configuration parameters:
    language                : set the languages of the players's messages. Has to be the same value as the message file name. (Example : language=en -> messages_en.lang)
    usepermissions          : (true/false) use PermissionsBukkit or PermissionsEx or Permissions 2x/3x or Native Bukkit Permissions (in that order, depending on what plugin you have)
    teleportwithsign        : (true/false) enable the sign to teleport by clicking on it
    teleportwithportal      : (true/false) enable Nether Portals substitution 
    debug                   : (true/false) used to enable the debug mode when the plugin is loading.
    clearallinventory       : (true/false) used to clear the armor content when the "i" option is set to a destination
    protectadmininventory   : (true/false) used to protect admin inventory when the clear inventory option is enabled
    tpblock                 : (type[,data]/disabled) used to select the teleport block:
                                - type (case insensitive) = BEDROCK,BOOKSHELF,BRICK,CLAY,COAL_ORE,COBBLESTONE,DIAMOND_BLOCK,DIAMOND_ORE,DIRT,ENDER_STONE,FURNACE,GLASS,GLOWING_REDSTONE_ORE,GLOWSTONE,GOLD_BLOCK,GOLD_ORE,GRASS,GRAVEL,ICE,IRON_BLOCK,IRON_ORE,JACK_O_LANTERN,JUKEBOX,LAPIS_BLOCK,LAPIS_ORE,LOG,MELON_BLOCK,MOSSY_COBBLESTONE,MYCEL,NETHER_BRICK,NETHERRACK,NOTE_BLOCK,OBSIDIAN,PUMPKIN,REDSTONE,SAND,SANDSTONE,SMOOTH_BRICK,SNOW_BLOCK,SOUL_SAND,SPONGE,STONE,TNT,WOOD,WOOL,WORKBENCH
                                - data (case insensitive) = all DyeColor for WOOL or TreeSpecies for LOG
    worlds                  : (world_name1,world_name2,...) used to load additional worlds which would not be loaded automatically. Other will be ignored (already loaded or not worlds)


################################################
################ LANGUAGE FILES ################
################################################

You have to use a messages_xx.lang file corresponding to your language defined in the configuration file. 
The "xx" has to match with the value in the configuration file.
The messages file has to be placed in the TravelGates directory.
If the language file is missing, the default language file will be used, stored directly inside the plugin.


################################################
########### PERMISSIONS CONFIGURATION ##########
################################################

Permissions Compatibility : supports PermissionsBukkit, PermissionsEx and Permissions 2x/3x plugins (they will be searched in this order)
Permissions nodes are the followings:
# For admins:
travelgates.add         # Permission to add a Destination
travelgates.del         # Permission to remove a Destination
travelgates.admintp     # Permission to travel to an Admin's Destination
travelgates.save        # Permission to request an instant save of destinations and restrictions
travelgates.update      # Permission to update destination's data
travelgates.debug       # Permission to toggle the Debug Mode
travelgates.worlds      # Permission to display available loaded worlds
travelgates.config      # Permission to display the current configuration
travelgates.perms       # Permission to toogle permissions 
travelgates.signtp      # Permission to toggle the usage of sign teleportation
travelgates.portaltp    # Permission to toggle the usage of nether portal
travelgates.clearallinv # Permission to toggle the state of the option clear all inventory
travelgates.protectadmininv # Permission to toggle the state of the configuration protect admin inventory and to be eligible to this protection
travelgates.autosave    # Permission to toggle the auto save
travelgates.tpblock     # Perrmissions to modify the tp block
travelgates.forcetp     # Permissions to teleport from everywhere using /tg <dest>

# For users:
travelgates.teleportcmd     # Allow a player to use the command /tg <dest>
travelgates.teleportsign    # Allow a player to use a TravelGates sign to teleport by clicking on it
travelgates.teleportportal  # Allow a player to use a TravelGates Nether Portal 
travelgates.list            # Allow a player to view the list of Destinations
travelgates.details         # Allow a player to view the detailed list of Destinations
travelgates.loc             # Allow a player to view the location of the Destination
travelgates.name            # Allow a player to view the name of the Destination where player is standing
travelgates.lever           # Allow a player to update the Portal Sign using a lever
travelgates.restrict        # Allow a player to display list of restricted destinations
travelgates.version         # Allow a player to display the current version of the plugin
travelgates.help            # Allow a player to view the Help


################################################
############ HOW TO USE THIS PLUGIN ############
################################################ 
    
Plugin in-game usage:
    Main command is "/travelgates" but you can use "/tg".
    All commands and destinations are case insensitive. (you can enter destinations without regarding if your destinations is lower case, UPPER CASE or bOtH !)
    Each command has several aliases, listed below.
    
    Admin commands:
    /tg add <destination> [-options] : add a destination. By default, the destination is the current players's Location AND direction (North/South/East/West) ! (Options are described below)
    /tg del <destination> [-s] : delete a destination
    /tg save : Force saving all data (configuration, destinations, restrictions)
    /tg debug : Toggle the debug mode
    /tg update <destination> [-options] : update all desintation's options
    /tg worlds (<world>|*) [-lu] [world_type]: display available loaded worlds with no options. World name use the same pattern as destinations. Wildcard * can be used to display all worlds in the server directory. Use options [-lu] to load or unload a world. Without options, it will display the state of the world (loaded or unloaded). With the l option is used, you can add the world type : (normal|nether|end), default is normal. You can't unload worlds which have still some players.
    /tg config : display current configuration
    /tg perms : toggle the permissions activation (reload permissions configuration if you enable them)
    /tg signtp : toggle the usage of sign to teleport 
    /tg portaltp : toggle the usage of the nether portal to teleport 
    /tg clearallinv : toggle the state of the clear all inventory option
    /tg protectadmininv : toggle the state of the protect admin inventory option
    /tg autosave : toggle the auto save
    /tg tpblock [new_configuration] : modify or toggle the tp block
    /tg <destination> [-f] : teleport player to the destination if he is standing on an existing location. If an admin uses the -f option, he can travel from everywhere.
   
   Users commands:
    /tg list : display all destinations available to the player.
    /tg details <destination> : display all destinations, their locations and options to the player. If a destination is entered, it would display its details.
    /tg loc <destination> : display the location of the destination.
    /tg name : display the name of the location where the player is standing.
    /tg help : display short help of the plugin.
    /tg restrict <destination> : display all restricted destinations for the selected destination
    /tg <destination> : teleport player to the destination if he is standing on an existing location.

    Other teleportation means:
    Right click on a "TravelGates'" sign containing on a line [TG] or [TRAVELGATES] (case insensitive) and on another line or on the same line (order doesn't matter) the name of an existing destination : do the same as /tg <destination>.
    Use Nether Portals : Place a "TravelGates'" sign (see above) on a Nether Portal with the "-ON-" state (defined in language file), updated with a lever or directly by editing the sign, and use the Portal as usual. The sign has to be placed on an obsidian portal bloc facing the head of the player. You can place the destination on the Obsidian blocks or on blocks beside of the portal.
    Using a teleport block: You can be teleported if you use a teleportation block, for instance lime wool. You have to cover a rectangle surface containing the destination with this kind of block to be teleported. Max range around the destination is 5 block (6 with the destination block). All these block have to be at the same height. Just under the player location when the destination has been added.
    
Available options (order of options doesn't matter):
    a = admin teleport only (only OP or Allowed players can travel to this destination)
    i = clear inventory while travelling to a destination (if the configuration "clearallinventory" is set to true, whole armor and the item wielded will be cleared too)
    s = force the save of new data (destinations, restrictions)
    p = set the position of your destination as following : -p(world,x,y,z) or -p(x,z,y). Conditions: -30M < x,z < +30M and 0 < y < MaxHeight and y = ? -> highest block
        (These commands with the -p option do not check the 'y' coordinate if you enter it. Use a "good" 'y' value. Use "?" if you don't know the max height.)
        (When you type the height with this option, it will be decreased by one, because the displayed "y" value is higher than the value you can be teleported)
        -p() or -p can be used to update a destination with the current player location
    r = set restricted destinations where players are allowed to go as following : -r{dest1,dest2,dest3} : (all updated) ; -r{+dest1,-dest2} : (add and remove) ; -r{} -r : (clear and disable restrictions)
    f = force the travel to teleport from everywhere (cannot be used as a destination option yet)
    l = use to load a world with the command /tg worlds <world>
    u = use to unload a world with the command /tg worlds <world>
    
Commands aliases:
    ADD: "a", "add" 
    DEL: "dl", "rm", "del", "rem", "delete", "remove"
    DETAILS: "dt", "det", "detail", "details"
    HELP: "h", "help"
    LIST: "ls", "list", "liste"
    LOC: "lc", "loc", "location"
    NAME: "n", "name" 
    TRAVELGATES: "tg", "travelgates" 
    SAVE: "s", "sv", "save"
    UPDATE: "u", "o", "up", "opt", "update", "option", "options" 
    DEBUG: "db","debug"
    WORLDS: "w","world","worlds"
    RESTRICT: "r","rst","restrict","restricts","restriction","restrictions"
    CONFIG: "c","conf","config","configuration"
    PERMS: "p","perm", "perms", "useperm", "useperms", "permission", "permissions", "usepermissions"
    SIGNTP: "stp", "signtp", "signteleport", "teleportwithsign"
    PORTALTP: "ptp", "portaltp", "portalteleport", "teleportwithportal"
    CLEARALLINV: "cai", "clr", "clear", "clearall", "clearinv", "clearallinv", "clearallinventory"
    PROTECTADMININV: "pai", "pro", "protectadm", "protectadminv", "protectadmininv", "protectadmininventory", "prtadminv"
    AUTOSAVE: "as", "autosave"
    TPBLOCK: "tpb", "tpblk", "tpblc", "tpblock", "tpbloc", "teleportblock"
    VERSION: "v", "ver", "version"

Examples:
    /tg add New-York -ias : add the destination New-York. Travelling to it will clear Player's inventory and only admins can travel to it. This new destinations will be saved immediately.
    /tg add New-York -p(world,20,?,20) : add the destination New-York. The location will be in "world" and at the location x=20, z=20 and at the heighest available block to stand on.
    /tg add New-York -ar{Paris,London} : add the destination New-York. This is an admin destination. You can only travel to Paris or London from New-York.
    /tg add New-York -ap(20,100,50)ir{London,Paris}s : add the destination New-York. This is an admin destination. The location is in your current world at the location 20,100,50, inventories will be cleared by travelling from this destination, only London and Paris can be reached and save immediately.
    
    /tg del New-York -s : delete the destination New-York and save immediately.
    
    /tg update New-York -ias : toggle the options "i", "a" of New-York and save immediately. The location is still the same.
    /tg update New-York -sp() : update the location of New-York with your current location and save immediately.
    /tg update New-York -sp : update the location of New-York with your current location and save immediately.
    /tg update New-York -p(world_nether,10,?,100) : update the location of New-York in the world "world_nether", at the location x=10, y=highest available block, z=100.
    /tg update New-York -r{London,Paris}s : update the restrictions list of New-York with London and Paris destinations. Save immediately.
    /tg update New-York -r{+London} : add the restricted destination London to the existing list of restrictions of New-York.
    /tg update New-York -r{-London} : remove the restricted destination London to the existing list of restrictions of New-York.
    /tg update New-York -r{-London,+Paris,Chicago} : remove London, add Paris and ignore Chicago.
    /tg update New-York -r{} : remove all restricted destinations
    /tg update New-York -r : remove all restricted destinations
   
    /tg tpblock : toggle the state of the configuration. If it was enabled, it will be disabled. If it was disabled, it will restore the last configuration.
    /tg tpblock wool : set the teleport block to a white (default) wool.
    /tg tpblock wool,0 : set the teleport block to a white (data value) wool.
    /tg tpblock wool,lime : set the teleport block to a lime wool.
    /tg tpblock cobblestone : set the teleport block to a cobblestone.
    /tg tpblock disabled : disable the teleport block.
    /tg tpblock anythingelse : disable the teleport block.
    
    /tg worlds : display available loaded worlds
    /tg worlds * : display worlds in the server directory, and show the state of each one (green=loaded, red=unloaded)
    /tg worlds <world_name> -l : load a new "normal" world
    /tg worlds <world_name> -l nether : load a new "nether" world  
    /tg worlds <world_name> -l end : load a new "end" world 
    /tg worlds <world_name> -u : unload a world if there are no players on it
    /tg worlds <world_name> : display the state of the world.
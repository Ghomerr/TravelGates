package com.ghomerr.travelgates.messages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Logger;

import org.bukkit.ChatColor;

import com.ghomerr.travelgates.TravelGates;
import com.ghomerr.travelgates.constants.TravelGatesConstants;

public class TravelGatesMessagesManager
{
	private static final Logger _logger = Logger.getLogger(TravelGatesConstants.MINECRAFT);

	private HashMap<TravelGatesMessages, String> _messages = null;
	private TravelGates _plugin = null;

	public TravelGatesMessagesManager(final TravelGates plugin, final String language)
	{
		_plugin = plugin;

		if (_plugin.isDebugEnabled())
		{
			_logger.info(TravelGatesConstants.DEBUG_TAG + " Creating TravelGatesMessagesManager(language=" + language
					+ ")");
		}

		FileInputStream in = null;

		if (language.equalsIgnoreCase(TravelGatesConstants.DEFAULT_LANGUAGE))
		{
			try
			{
				in = (FileInputStream) _plugin.getClass().getResourceAsStream(
						TravelGatesConstants.PLUGIN_MESSAGES_RESOURCES);
			}
			catch (final Throwable th)
			{
				_logger.severe(TravelGatesConstants.PLUGIN_TAG + " Messages resource file not found.");
				th.printStackTrace();
			}
		}
		else
		{
			final File languageFile = new File(TravelGatesConstants.PLUGIN_MESSAGES_PATH.replace(
					TravelGatesConstants.LANGUAGE_TAG, language.toLowerCase()));

			try
			{
				in = new FileInputStream(languageFile);
			}
			catch (final FileNotFoundException ex)
			{
				_logger.warning(TravelGatesConstants.PLUGIN_TAG + " Configuraton file not found.");
			}
		}

		if (in != null)
		{
			// LOAD MESSAGES
			final Properties pluginMessages = new Properties();
			try
			{
				pluginMessages.load(in);
				in.close();
				_messages = new HashMap<TravelGatesMessages, String>();
			}
			catch (final IOException ex)
			{
				_logger.severe(TravelGatesConstants.PLUGIN_TAG + " Error while loading the Messages file.");
				ex.printStackTrace();
			}

			if (_messages != null)
			{
				for (final Object key : pluginMessages.keySet())
				{
					try
					{
						final String strKey = String.valueOf(key).toUpperCase();
						final TravelGatesMessages message = TravelGatesMessages.valueOf(strKey);
						_messages.put(message, pluginMessages.getProperty(strKey));
					}
					catch (final Throwable th)
					{
						_logger.severe(TravelGatesConstants.PLUGIN_TAG + " Error while reading the Messages file.");
						th.printStackTrace();
						_messages = null;
					}
				}
			}
		}

		if (_plugin.isDebugEnabled())
		{
			_logger.info(TravelGatesConstants.DEBUG_TAG + " TravelGatesMessagesManager created");
		}
	}

	public String get(final TravelGatesMessages message, final String... vars)
	{
		if (_plugin.isDebugEnabled())
		{
			_logger.info(TravelGatesConstants.DEBUG_TAG + " Start get(message=" + message + ", vars=" + vars + ")");
		}

		String completeMessage = _messages.get(message);

		if (completeMessage != null)
		{
			for (final String var : vars)
			{
				try
				{
					completeMessage = completeMessage.replaceFirst(TravelGatesConstants.VAR_MESSAGE_TAG, var);
				}
				catch (final Throwable th)
				{
					System.err.println(TravelGatesConstants.PLUGIN_TAG + " Error while parsing the message  : "
							+ completeMessage);
					th.printStackTrace();
				}
			}
		}
		else
		{
			completeMessage = ChatColor.RED + "<" + message.name() + ">";
		}

		if (_plugin.isDebugEnabled())
		{
			_logger.info(TravelGatesConstants.DEBUG_TAG + " End get : " + completeMessage);
		}

		return completeMessage;
	}
}

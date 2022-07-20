package com.chylex.intellij.keeppopupswhiledebugging

import com.intellij.ide.plugins.CannotUnloadPluginException
import com.intellij.ide.plugins.DynamicPluginListener
import com.intellij.ide.plugins.IdeaPluginDescriptor

class PluginLoadListener : DynamicPluginListener {
	private companion object {
		private const val PLUGIN_ID = "com.chylex.intellij.keeppopupswhiledebugging"
	}
	
	override fun pluginLoaded(pluginDescriptor: IdeaPluginDescriptor) {
		if (pluginDescriptor.pluginId.idString == PLUGIN_ID) {
			PreventHidingPopups.installListener()
			PreventHidingPopups.enablePopupsInAllProjects()
		}
	}
	
	override fun beforePluginUnload(pluginDescriptor: IdeaPluginDescriptor, isUpdate: Boolean) {
		if (pluginDescriptor.pluginId.idString == PLUGIN_ID && !PreventHidingPopups.tryUninstallListener()) {
			throw CannotUnloadPluginException("A restart is required to unload Keep Editor Tooltips While Debugging plugin.")
		}
	}
}

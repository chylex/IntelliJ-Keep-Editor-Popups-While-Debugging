package com.chylex.intellij.keeppopupswhiledebugging

import com.intellij.openapi.editor.EditorMouseHoverPopupManager
import com.intellij.openapi.editor.impl.EditorMouseHoverPopupControl
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.startup.StartupActivity
import com.intellij.openapi.util.Key

class PreventHidingPopups : StartupActivity {
	override fun runActivity(project: Project) {
		installListener()
	}
	
	companion object {
		fun installListener() {
			EditorMouseHoverPopupManager.getInstance() // Installs the default listener.
			EditorMouseHoverPopupControl.getInstance()?.addListener(MouseTrackingDisabledListener)
		}
		
		fun tryUninstallListener(): Boolean {
			val instance = EditorMouseHoverPopupControl.getInstance() ?: return true
			
			return try {
				val listenersField = instance.javaClass.getDeclaredField("listeners").also { it.isAccessible = true }
				val listeners = listenersField.get(instance) as MutableCollection<*>
				listeners.remove(MouseTrackingDisabledListener)
				true
			} catch (e: Exception) {
				false
			}
		}
		
		fun enablePopupsInAllProjects() {
			val projectManager = ProjectManager.getInstanceIfCreated() ?: return
			
			for (project in projectManager.openProjects) {
				repeat(MouseTrackingDisabledKey.getDisabledCount(project)) {
					EditorMouseHoverPopupControl.enablePopups(project)
				}
			}
		}
	}
	
	private object MouseTrackingDisabledListener : Runnable {
		private var isReenablingPopups = false
		
		override fun run() {
			if (!isReenablingPopups) {
				isReenablingPopups = true
				enablePopupsInAllProjects()
				isReenablingPopups = false
			}
		}
	}
	
	private object MouseTrackingDisabledKey {
		private val KEY: Key<*>?
		
		init {
			EditorMouseHoverPopupControl.getInstance() // Creates the key.
			KEY = Key.findKeyByName("MOUSE_TRACKING_DISABLED_COUNT")
		}
		
		@Suppress("ConvertLambdaToReference")
		fun getDisabledCount(project: Project): Int {
			return KEY?.let { project.getUserData(it) } as? Int ?: 0
		}
	}
}

package com.workasintended.chromaggus.desktop

import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}
import com.workasintended.chromaggus.ChromaggusGame

object DesktopLauncher {
	def main (args: Array[String]): Unit = {
		val config = new LwjglApplicationConfiguration
		config.width = 800
		config.height = 600

		val chromaggusGame = new ChromaggusGame
		new LwjglApplication(chromaggusGame, config)
	}
}

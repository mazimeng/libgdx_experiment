package com.workasintended.chromaggus

import com.badlogic.gdx.Game

class ChromaggusGame extends Game {
  private lazy val uiScreen = new UiScreen()

  override def create(): Unit = {
    this.setScreen(uiScreen)
  }

  override def render() = {
    super.render()
  }
}

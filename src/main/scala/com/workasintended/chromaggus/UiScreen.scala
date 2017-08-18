package com.workasintended.chromaggus

import com.badlogic.gdx.ai.GdxAI
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.scenes.scene2d.{Actor, Stage}
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent
import com.badlogic.gdx.utils.viewport.{FillViewport, Viewport}
import com.badlogic.gdx.{Gdx, ScreenAdapter}

/**
  * Created by mazimeng on 7/16/17.
  */
class UiScreen extends ScreenAdapter {
  private lazy val ui = new Stage()
  initGui()

  override def render(delta: Float): Unit = {
    Gdx.gl.glClearColor(0.0f, 0.0f, 0f, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    GdxAI.getTimepiece.update(delta)
    ui.act(Gdx.graphics.getDeltaTime)

    ui.draw()
  }

  override def resize(width: Int, height: Int): Unit = {
    ui.getViewport().update(width, height)
  }

  def makeCamera(): OrthographicCamera = {
    val w = Gdx.graphics.getWidth
    val h = Gdx.graphics.getHeight
    val cam = new OrthographicCamera(w, h)
    cam.update()
    cam
  }

  def makeViewport(): Viewport = {
    val cam: OrthographicCamera = makeCamera
    val (w, h) = (Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    val viewport = new FillViewport(w, h)
    viewport.setCamera(cam)

    return viewport
  }

  def initGui(): Unit = {
    Gdx.input.setInputProcessor(ui)

    val assetManager = new AssetManager()
    assetManager.load("uiskin.json", classOf[Skin])
    assetManager.finishLoading()

    val w = Gdx.graphics.getWidth
    val h = Gdx.graphics.getHeight
    val viewport = makeViewport()
    val skin: Skin = assetManager.get("uiskin.json")
    //    ui.setViewport(viewport)
    val table = new Table(skin)
    table.setDebug(true)
    table.setFillParent(true)
    table.right().top()


    val list = new com.badlogic.gdx.scenes.scene2d.ui.List[TextButton](skin)

    list.addListener(new ChangeListener() {
      def changed(event: ChangeEvent, actor: Actor): Unit = {
        val theList = actor.asInstanceOf[com.badlogic.gdx.scenes.scene2d.ui.List[String]]
        println(s"list changed: ${theList.getSelected}")
      }
    })

    val button = new TextButton("Click me!", skin)

    // Add a listener to the button. ChangeListener is fired when the button's checked state changes, eg when clicked,
    // Button#setChecked() is called, via a key press, etc. If the event.cancel() is called, the checked state will be reverted.
    // ClickListener could have been used, but would only fire when clicked. Also, canceling a ClickListener event won't
    // revert the checked state.
    button.addListener(new ChangeListener() {
      def changed(event: ChangeEvent, actor: Actor): Unit = {
        System.out.println("Clicked! Is checked: " + button.isChecked)
        button.setText("Good job!")
      }
    })

    table.add(button)

    val container = new Container[TextButton]()
    container.setFillParent(true)
    container.setDebug(true, true)
    //    container.top().right()
    //    ui.addActor(container)

    container.setActor(button)

    val wp: Window = weapons(skin)
    top(skin, wp)
  }

  def top(skin: Skin, weapons: Window): Unit = {
    val weaponsButton = new TextButton("weapons", skin)

    weaponsButton.addListener(new ChangeListener() {
      def changed(event: ChangeEvent, actor: Actor): Unit = {
        weapons.setVisible(!weapons.isVisible)
      }
    })

    val table = new Table(skin)
    table.top().left()
    table.add(weaponsButton)
    table.setDebug(true, true)
    table.setFillParent(true)

    ui.addActor(table)
  }

  def weapons(skin: Skin): Window = {

    val window = new Window("this is window", skin)
    window.setDebug(true, true)
    window.setResizable(true)
    window.setSize(320f, 200f)
    window.top().left()

    ui.addActor(window)

    val table = new Table(skin)
//    table.setDebug(true)
//    table.setFillParent(true)
    window.add(new Label("name", skin)).expandX()
    window.add(new Label("damage", skin)).expandX()
    window.row()
    window.add(new Label("ashkandi", skin))
    window.add(new Label("10-15", skin))
    window.row()
    window.add(new Label("one-hand sword", skin))
    window.add(new Label("2-5", skin))

    window.add(table)
    window
  }
}

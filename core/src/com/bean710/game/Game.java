package com.bean710.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.UBJsonReader;

public class Game extends ApplicationAdapter implements InputProcessor {
	private PerspectiveCamera camera;
	private ModelBatch modelBatch;
	private Model model;
	private ModelInstance modelInstance;
	private Environment environment;
	private AnimationController controller;
	private ArrayList<ModelInstance> instances;

	@Override
	public void create() {
		// Set up camera
		camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(0f, 100f, 150f);
		camera.lookAt(0f, 100f, 0f);
		camera.near = 0.1f;
		camera.far = 3000f;

		modelBatch = new ModelBatch(); // Create batch for multiple models
		
		UBJsonReader jsonReader = new UBJsonReader();
		
		G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);
		
		model = modelLoader.loadModel(Gdx.files.getFileHandle("model.g3db", Files.FileType.Internal)); // Create 3 dimensional zombie
		modelInstance = new ModelInstance(model, 0, 0, 0); // Create instance of 'model'
		
		environment = new Environment(); // Make the game's environment
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1f));
		
		controller = new AnimationController(modelInstance);
		controller.setAnimation("mixamo.com", -1);
		
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render() {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0, 0, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		camera.update();
		controller.update(Gdx.graphics.getDeltaTime());
		
		modelInstance.transform.translate(0, 0, 30*Gdx.graphics.getDeltaTime());
		
		modelBatch.begin(camera);
		modelBatch.render(modelInstance);
		modelBatch.end();
		
	}

	@Override
	public void dispose() {
		model.dispose();
		modelBatch.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		//TODO MAKE THESE TEMPORARY VARIABLES
		if (keycode == Input.Keys.LEFT) {
			camera.rotateAround(new Vector3(0f, 0f, 0f), new Vector3(0f, 1f, 0f), 10f);
		}
		if (keycode == Keys.RIGHT) {
			camera.rotateAround(new Vector3(0f, 0f, 0f), new Vector3(0f, 1f, 0f), -10f);
		}
		
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}

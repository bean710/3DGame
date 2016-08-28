package com.bean710.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.UBJsonReader;

public class Game extends ApplicationAdapter implements InputProcessor {
	private PerspectiveCamera camera;
	private ModelBatch modelBatch;
	private ModelBuilder modelBuilder;
	private Model model;
	private Model box;
	private ModelInstance boxI;
	private Environment environment;
	private ArrayList<ModelInstance> instances;
	private ArrayList<AnimationController> controllers;
	
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	private int mouseY;
	private int mouseX;
	private float rotSpeed;
	private Vector3 tmp = new Vector3();
	
	@Override
	public void create() {
		instances = new ArrayList<ModelInstance>();
		controllers = new ArrayList<AnimationController>();
		
		// Set up camera
		camera = new PerspectiveCamera(60, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(100f, 100f, 300f);
		camera.lookAt(0f, 100f, 0f);
		camera.near = 0.1f;
		camera.far = 3000f;
		
		mouseY = 0;
		mouseX = 0;
		rotSpeed = 0.2f;

		modelBatch = new ModelBatch(); // Create batch for multiple models
		modelBuilder = new ModelBuilder();
		
		UBJsonReader jsonReader = new UBJsonReader();
		
		G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);
		
		box = modelBuilder.createBox(10000f, 1f, 10000f, new Material(ColorAttribute.createDiffuse(Color.RED)),
 				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
		boxI = new ModelInstance(box, 0, -1, 0);
		
		model = modelLoader.loadModel(Gdx.files.getFileHandle("model.g3db", Files.FileType.Internal)); // Create 3 dimensional zombie
		instances.add(new ModelInstance(model, 0, 0, 0)); // Create instance of 'model'
		/*instances.add(new ModelInstance(model, 100, 0, 0)); // Create instance of 'model'
		instances.add(new ModelInstance(model, -100, 0, 0)); // Create instance of 'model'
		instances.add(new ModelInstance(model, 200, 0, 0)); // Create instance of 'model'
		instances.add(new ModelInstance(model, -200, 0, 0)); // Create instance of 'model'
		instances.add(new ModelInstance(model, 300, 0, 0)); // Create instance of 'model'
		instances.add(new ModelInstance(model, -300, 0, 0)); // Create instance of 'model'
		instances.add(new ModelInstance(model, 400, 0, 0)); // Create instance of 'model'
		instances.add(new ModelInstance(model, -400, 0, 0)); // Create instance of 'model'
		instances.add(new ModelInstance(model, 500, 0, 0)); // Create instance of 'model'
		instances.add(new ModelInstance(model, -500, 0, 0)); // Create instance of 'model'
		instances.add(new ModelInstance(model, 600, 0, 0)); // Create instance of 'model'
*/		
		
		environment = new Environment(); // Make the game's environment
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1f));
		
		for (ModelInstance modelInstance : instances) {
			AnimationController temp = new AnimationController(modelInstance);
			temp.setAnimation("mixamo.com", -1);
			controllers.add(temp);
		}
		
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCursorCatched(true);
	}

	@Override
	public void render() {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0, 0, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		camera.update();
		for (AnimationController controller : controllers) {
			controller.update(Gdx.graphics.getDeltaTime());
		}
		
		for (ModelInstance modelInstance : instances) {
			modelInstance.transform.translate(0, 0, 30*Gdx.graphics.getDeltaTime());
		}
		
		modelBatch.begin(camera);
		modelBatch.render(boxI);
		for (ModelInstance modelInstance : instances)
			modelBatch.render(modelInstance);
		modelBatch.end();

		//TODO MAKE THESE TEMPORARY VARIABLES
		if (right) {
			camera.position.set(camera.position.x-camera.direction.z*3, camera.position.y, camera.position.z+camera.direction.x*3);
		}
		if (left) {
			camera.position.set(camera.position.x+camera.direction.z*3, camera.position.y, camera.position.z-camera.direction.x*3);		
		}
		if (up) {
			camera.position.set(camera.position.x+camera.direction.x*3, camera.position.y, camera.position.z + camera.direction.z*5);
		}
		if (down) {
			camera.position.set(camera.position.x-camera.direction.x*3, camera.position.y, camera.position.z - camera.direction.z*5);
		}
		
	}

	@Override
	public void dispose() {
		model.dispose();
		modelBatch.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.A) {
			left = true;
		}
		if (keycode == Input.Keys.D) {
			right = true;
		}
		if (keycode == Input.Keys.W) {
			up = true;
		}
		if (keycode == Input.Keys.S) {
			down = true;
		}
		
		if (keycode == Input.Keys.ESCAPE) {
			Gdx.input.setCursorCatched(false);
		}
		
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.A) {
			left = false;
		}
		if (keycode == Keys.D) {
			right = false;
		}
		if (keycode == Keys.W) {
			up = false;
		}
		if (keycode == Keys.S) {
			down = false;
		}
		return true;
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
		float deltaX = -Gdx.input.getDeltaX() * 0.5f;
		float deltaY = -Gdx.input.getDeltaY() * 0.5f;
		camera.direction.rotate(camera.up, deltaX);
		tmp.set(camera.direction).crs(camera.up).nor();
		camera.direction.rotate(tmp, deltaY);
// camera.up.rotate(tmp, deltaY);
		
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}

package com.bean710.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class Game extends ApplicationAdapter {
	private PerspectiveCamera camera;
	private ModelBatch modelBatch;
	private ModelBuilder modelBuilder;
	private Model box;
	private ModelInstance modelInstance;
	private Environment environment;

	@Override
	public void create() {
		// Set up camera
		camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(0f, 0f, 3f);
		camera.lookAt(0f, 0f, 0f);
		camera.near = 0.1f;
		camera.far = 100f;

		modelBatch = new ModelBatch(); // Create batch for multiple models
		modelBuilder = new ModelBuilder(); // Tool for building models
		
		box = modelBuilder.createBox(2f, 2f, 2f, new Material(ColorAttribute.createDiffuse(Color.RED)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal); // Create 3 dimensional box
		modelInstance = new ModelInstance(box, 0, 0, 0); // Create instance of 'box'
		
		environment = new Environment(); // Make the game's environment
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, ));
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
	}

	@Override
	public void dispose() {
	}
}

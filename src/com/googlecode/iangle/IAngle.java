package com.googlecode.iangle;


/*
 * @author Rohit Kalhans <rohit.kalhans@gmail.com> 
 */


/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details,
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class IAngle extends Activity implements OrientationListener {

	private GLSurfaceView glSurface;
	private  static Context CONTEXT;
	private float lastpitch=0;
	private float lastroll=0;
	private float lastazimuth=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CONTEXT  = IAngle.this;
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
			        WindowManager.LayoutParams.FLAG_FULLSCREEN);
			        requestWindowFeature(Window.FEATURE_NO_TITLE);
		glSurface = new GLSurfaceView(this);
		glSurface.setRenderer(new CubeRenderer(this));
		setContentView(glSurface);
	}

	@Override
	protected void onResume() {
		super.onResume();
		glSurface.onResume();
		if (OrientationManager.isSupported()) {
            OrientationManager.startListening(this);
        }
	}

	@Override
	protected void onDestroy()
	{
		if (OrientationManager.isListening()) {
            OrientationManager.stopListening();
        }
	}
	@Override
	protected void onPause() {
		super.onPause();
		glSurface.onPause();
	}

	@Override
	public void onOrientationChanged(float azimuth, float pitch, float roll) {
		//CubeRenderer.zrot=azimuth;
				
		//TODO: prevent virations due to shaky hands 

		CubeRenderer.xrot=360-pitch;
		CubeRenderer.yrot=360-roll;
		
		lastroll = roll;
		lastpitch = pitch; 
		
	}

	public static Context getContext()
	{
		return CONTEXT ;
	}
	
}
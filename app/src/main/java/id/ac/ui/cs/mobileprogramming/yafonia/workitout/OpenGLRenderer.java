package id.ac.ui.cs.mobileprogramming.yafonia.workitout;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLRenderer implements GLSurfaceView.Renderer {

    private double redValue =0.5f;
    private static final double FLASH_DURATION = 10000;
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig config) {
        GLES20.glClearColor(0.5f, 0, 0, 1f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClearColor((float) redValue, 0, 0, 1f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        redValue = ((Math.sin(System.currentTimeMillis() * 5 * Math.PI / FLASH_DURATION) * 0.5) + 0.5);

    }
}

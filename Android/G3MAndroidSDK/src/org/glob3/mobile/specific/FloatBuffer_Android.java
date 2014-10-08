

package org.glob3.mobile.specific;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.glob3.mobile.generated.IFloatBuffer;

import android.opengl.GLES20;


public final class FloatBuffer_Android
extends
IFloatBuffer {

   private final FloatBuffer _buffer;
   private int               _timestamp;

   //   private boolean           _disposed              = false;
   private int               _vertexBuffer          = -1;
   private int               _vertexBufferTimeStamp = -1;

   //ID
   private static long       _nextID                = 0;
   private final long        _id                    = _nextID++;
   
   NativeGL2_Android _nativeGL = null;


   FloatBuffer_Android(final int size) {
      _buffer = ByteBuffer.allocateDirect(size * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
   }


   FloatBuffer_Android(final float f0,
            final float f1,
            final float f2,
            final float f3,
            final float f4,
            final float f5,
            final float f6,
            final float f7,
            final float f8,
            final float f9,
            final float f10,
            final float f11,
            final float f12,
            final float f13,
            final float f14,
            final float f15) {
      _buffer = ByteBuffer.allocateDirect(16 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
      _buffer.put(0, f0);
      _buffer.put(1, f1);
      _buffer.put(2, f2);
      _buffer.put(3, f3);
      _buffer.put(4, f4);
      _buffer.put(5, f5);
      _buffer.put(6, f6);
      _buffer.put(7, f7);
      _buffer.put(8, f8);
      _buffer.put(9, f9);
      _buffer.put(10, f10);
      _buffer.put(11, f11);
      _buffer.put(12, f12);
      _buffer.put(13, f13);
      _buffer.put(14, f14);
      _buffer.put(15, f15);
   }


   //     FloatBuffer_Android(final float[] array) {
      //      _buffer = ByteBuffer.allocateDirect(array.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
   //      _buffer.put(array);
   //      _buffer.rewind();
   //   }


   FloatBuffer_Android(final float[] array,
            final int length) {
      _buffer = ByteBuffer.allocateDirect(length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
      _buffer.put(array, 0, length);
      _buffer.rewind();
   }


   @Override
   public int size() {
      return _buffer.capacity();
   }


   @Override
   public int timestamp() {
      return _timestamp;
   }


   @Override
   public float get(final int i) {
      return _buffer.get(i);
   }


   @Override
   public void put(final int i,
                   final float value) {
      if (_buffer.get(i) != value) {
         _buffer.put(i, value);
         _timestamp++;
      }
   }


   @Override
   public void rawPut(final int i,
                      final float value) {
      _buffer.put(i, value);
   }


   @Override
   public void rawAdd(final int i,
                      final float value) {
      _buffer.put(i, _buffer.get(i) + value);
   }


   public FloatBuffer getBuffer() {
      return _buffer;
   }


   @Override
   public String description() {
      return "FloatBuffer_Android(timestamp=" + _timestamp + ", buffer=" + _buffer + ")";
   }


   public int bindAsVBOToGPU(NativeGL2_Android gl) {
	  _nativeGL = gl;
      if (_vertexBuffer < 0) {
         _vertexBuffer = _nativeGL.genBuffer();
      }
  
      _nativeGL.bindVBO(_vertexBuffer);

      if (_vertexBufferTimeStamp != _timestamp) {
         _vertexBufferTimeStamp = _timestamp;

         final FloatBuffer buffer = getBuffer();
         final int vboSize = 4 * size();
         GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vboSize, buffer, GLES20.GL_STATIC_DRAW);
      }

      //	    if (GL_NO_ERROR != glGetError()){
      //	      ILogger::instance()->logError("Problem using VBO");
      //	    }
      
      return _vertexBuffer;
   }


   @Override
   public void dispose() {
      super.dispose();
      if (_vertexBuffer > -1 && _nativeGL != null) {
    	  _nativeGL.deleteVBO(_vertexBuffer);
    	  _vertexBuffer = -1;
      }
   }


   @Override
   public long getID() {
      return _id;
   }


   @Override
   public void rawPut(final int i,
                      final IFloatBuffer srcBuffer,
                      final int srcFromIndex,
                      final int count) {
      if ((i < 0) || ((i + count) > size())) {
         throw new RuntimeException("buffer put error");
      }

      final FloatBuffer_Android androidSrcBuffer = (FloatBuffer_Android) srcBuffer;
      for (int j = 0; j < count; j++) {
         _buffer.put(i + j, androidSrcBuffer._buffer.get(srcFromIndex + j));
      }
   }


   //   @Override
   //   protected void finalize() throws Throwable {
   //      if (!_disposed) {
   //         ILogger.instance().logError(this + " not disposed (_vertexBufferCreated=" + _vertexBufferCreated + ")");
   //      }
   //
   //      super.finalize();
   //   }

   //   public int getGLBuffer() {
   //      if (!_hasGLBuffer) {
   //         final int[] buffers = new int[1];
   //         GLES20.glGenBuffers(1, buffers, 0);
   //         _glBuffer = buffers[0];
   //         _hasGLBuffer = true;
   //      }
   //
   //      return _glBuffer;
   //   }

}

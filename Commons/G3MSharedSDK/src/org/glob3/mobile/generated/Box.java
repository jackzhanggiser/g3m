package org.glob3.mobile.generated; 
//
//  Box.cpp
//  G3MiOSSDK
//
//  Created by Agustín Trujillo Pino on 17/07/12.
//  Copyright (c) 2012 Universidad de Las Palmas. All rights reserved.
//

//
//  Box.h
//  G3MiOSSDK
//
//  Created by Agustín Trujillo Pino on 16/07/12.
//  Copyright (c) 2012 Universidad de Las Palmas. All rights reserved.
//



//class Vector2D;
//class Mesh;
//class Color;

public class Box extends Extent
{
  private final Vector3D _lower ;
  private final Vector3D _upper ;

  private java.util.ArrayList<Vector3D> _cornersD = null; // cache for getCorners() method
  private java.util.ArrayList<Vector3F> _cornersF = null; // cache for getCornersF() method

  private Mesh _mesh;
  private void createMesh(Color color)
  {
  
    float[] v = { (float) _lower._x, (float) _lower._y, (float) _lower._z, (float) _lower._x, (float) _upper._y, (float) _lower._z, (float) _lower._x, (float) _upper._y, (float) _upper._z, (float) _lower._x, (float) _lower._y, (float) _upper._z, (float) _upper._x, (float) _lower._y, (float) _lower._z, (float) _upper._x, (float) _upper._y, (float) _lower._z, (float) _upper._x, (float) _upper._y, (float) _upper._z, (float) _upper._x, (float) _lower._y, (float) _upper._z };
  
    short[] i = { 0, 1, 1, 2, 2, 3, 3, 0, 1, 5, 5, 6, 6, 2, 2, 1, 5, 4, 4, 7, 7, 6, 6, 5, 4, 0, 0, 3, 3, 7, 7, 4, 3, 2, 2, 6, 6, 7, 7, 3, 0, 1, 1, 5, 5, 4, 4, 0 };
  
    FloatBufferBuilderFromCartesian3D vertices = new FloatBufferBuilderFromCartesian3D(CenterStrategy.firstVertex(), Vector3D.zero());
    ShortBufferBuilder indices = new ShortBufferBuilder();
  
    final int numVertices = 8;
    for (int n = 0; n<numVertices; n++)
    {
      vertices.add(v[n *3], v[n *3+1], v[n *3+2]);
    }
  
    final int numIndices = 48;
    for (int n = 0; n<numIndices; n++)
    {
      indices.add(i[n]);
    }
  
    _mesh = new IndexedMesh(GLPrimitive.lines(), true, vertices.getCenter(), vertices.create(), indices.create(), 1, 1, color);
  }

  public Box(Vector3D lower, Vector3D upper)
  {
     _lower = new Vector3D(lower);
     _upper = new Vector3D(upper);
     _mesh = null;
  }

  public void dispose()
  {
    if (_mesh != null)
       _mesh.dispose();
  }

  public final boolean touches(Frustum frustum)
  {
    return frustum.touchesWithBox(this);
  }

  public final Vector3D getLower()
  {
     return _lower;
  }
  public final Vector3D getUpper()
  {
     return _upper;
  }

  public final java.util.ArrayList<Vector3D> getCorners()
  {
    if (_cornersD == null) {
      _cornersD = new java.util.ArrayList<Vector3D>(8);
  
      _cornersD.add(_lower);
      _cornersD.add(new Vector3D(_lower._x, _lower._y, _upper._z));
      _cornersD.add(new Vector3D(_lower._x, _upper._y, _lower._z));
      _cornersD.add(new Vector3D(_lower._x, _upper._y, _upper._z));
      _cornersD.add(new Vector3D(_upper._x, _lower._y, _lower._z));
      _cornersD.add(new Vector3D(_upper._x, _lower._y, _upper._z));
      _cornersD.add(new Vector3D(_upper._x, _upper._y, _lower._z));
      _cornersD.add(_upper);
    }
    return _cornersD;
  }
  public final java.util.ArrayList<Vector3F> getCornersF()
  {
    if (_cornersF == null) {
      _cornersF = new java.util.ArrayList<Vector3F>(8);
  
      _cornersF.add(new Vector3F((float) _lower._x, (float) _lower._y, (float) _lower._z));
      _cornersF.add(new Vector3F((float) _lower._x, (float) _lower._y, (float) _upper._z));
      _cornersF.add(new Vector3F((float) _lower._x, (float) _upper._y, (float) _lower._z));
      _cornersF.add(new Vector3F((float) _lower._x, (float) _upper._y, (float) _upper._z));
      _cornersF.add(new Vector3F((float) _upper._x, (float) _lower._y, (float) _lower._z));
      _cornersF.add(new Vector3F((float) _upper._x, (float) _lower._y, (float) _upper._z));
      _cornersF.add(new Vector3F((float) _upper._x, (float) _upper._y, (float) _lower._z));
      _cornersF.add(new Vector3F((float) _upper._x, (float) _upper._y, (float) _upper._z));
    }
    return _cornersF;
  }

  public final double squaredProjectedArea(G3MRenderContext rc)
  {
    final Vector2I extent = projectedExtent(rc);
    return extent._x * extent._y;
  }
  public final Vector2I projectedExtent(G3MRenderContext rc)
  {
    final java.util.ArrayList<Vector3F> corners = getCornersF();
  
    final Camera currentCamera = rc.getCurrentCamera();
  
    final Vector2I pixel0 = currentCamera.point2Pixel(corners.get(0));
  
    int lowerX = pixel0._x;
    int upperX = pixel0._x;
    int lowerY = pixel0._y;
    int upperY = pixel0._y;
  
    final int cornersSize = corners.size();
    for (int i = 1; i < cornersSize; i++)
    {
      final Vector2I pixel = currentCamera.point2Pixel(corners.get(i));
  
      final int x = pixel._x;
      final int y = pixel._y;
  
      if (x < lowerX)
      {
         lowerX = x;
      }
      if (y < lowerY)
      {
         lowerY = y;
      }
  
      if (x > upperX)
      {
         upperX = x;
      }
      if (y > upperY)
      {
         upperY = y;
      }
    }
  
    final int width = upperX - lowerX;
    final int height = upperY - lowerY;
  
    return new Vector2I(width, height);
  }

  public final boolean contains(Vector3D p)
  {
    final double margin = 1e-3;
    if (p._x < _lower._x - margin)
       return false;
    if (p._x > _upper._x + margin)
       return false;
  
    if (p._y < _lower._y - margin)
       return false;
    if (p._y > _upper._y + margin)
       return false;
  
    if (p._z < _lower._z - margin)
       return false;
    if (p._z > _upper._z + margin)
       return false;
  
    return true;
  }

  public final Vector3D intersectionWithRay(Vector3D origin, Vector3D direction)
  {
    //MIN X
    {
      Plane p = new Plane(new Vector3D(1.0, 0.0, 0.0), _lower._x);
      Vector3D inter = p.intersectionWithRay(origin, direction);
      if (!inter.isNan() && contains(inter))
         return inter;
    }
  
    //MAX X
    {
      Plane p = new Plane(new Vector3D(1.0, 0.0, 0.0), _upper._x);
      Vector3D inter = p.intersectionWithRay(origin, direction);
      if (!inter.isNan() && contains(inter))
         return inter;
    }
  
    //MIN Y
    {
      Plane p = new Plane(new Vector3D(0.0, 1.0, 0.0), _lower._y);
      Vector3D inter = p.intersectionWithRay(origin, direction);
      if (!inter.isNan() && contains(inter))
         return inter;
    }
  
    //MAX Y
    {
      Plane p = new Plane(new Vector3D(0.0, 1.0, 0.0), _upper._y);
      Vector3D inter = p.intersectionWithRay(origin, direction);
      if (!inter.isNan() && contains(inter))
         return inter;
    }
  
    //MIN Z
    {
      Plane p = new Plane(new Vector3D(0.0, 0.0, 1.0), _lower._z);
      Vector3D inter = p.intersectionWithRay(origin, direction);
      if (!inter.isNan() && contains(inter))
         return inter;
    }
  
    //MAX Z
    {
      Plane p = new Plane(new Vector3D(0.0, 0.0, 1.0), _upper._z);
      Vector3D inter = p.intersectionWithRay(origin, direction);
      if (!inter.isNan() && contains(inter))
         return inter;
    }
  
    return Vector3D.nan();
  }

  public final void render(G3MRenderContext rc, GLState parentState, GPUProgramState parentProgramState)
  {
    if (_mesh == null)
    {
      createMesh(Color.newFromRGBA(1.0f, 0.0f, 1.0f, 1.0f));
    }
    _mesh.render(rc, parentState, parentProgramState);
  }

  public final boolean touchesBox(Box box)
  {
    if (_lower._x > box._upper._x)
    {
       return false;
    }
    if (_upper._x < box._lower._x)
    {
       return false;
    }
    if (_lower._y > box._upper._y)
    {
       return false;
    }
    if (_upper._y < box._lower._y)
    {
       return false;
    }
    if (_lower._z > box._upper._z)
    {
       return false;
    }
    if (_upper._z < box._lower._z)
    {
       return false;
    }
    return true;
  }

  public final Extent mergedWith(Extent that)
  {
    if (that == null)
    {
      return null;
    }
    return that.mergedWithBox(this);
  }

  public final Extent mergedWithBox(Box that)
  {
    final IMathUtils mu = IMathUtils.instance();
  
    final double lowerX = mu.min(_lower._x, that._lower._x);
    final double lowerY = mu.min(_lower._y, that._lower._y);
    final double lowerZ = mu.min(_lower._z, that._lower._z);
  
    final double upperX = mu.max(_upper._x, that._upper._x);
    final double upperY = mu.max(_upper._y, that._upper._y);
    final double upperZ = mu.max(_upper._z, that._upper._z);
  
    return new Box(new Vector3D(lowerX, lowerY, lowerZ), new Vector3D(upperX, upperY, upperZ));
  }

  public final boolean fullContains(Extent that)
  {
    return that.fullContainedInBox(this);
  }

  public final boolean fullContainedInBox(Box box)
  {
  //  return contains(box->_upper) && contains(box->_lower);
    return box.contains(_upper) && box.contains(_lower);
  }


}
package org.glob3.mobile.generated; 
//
//  TileRasterizer.cpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 7/8/13.
//
//

//
//  TileRasterizer.hpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 7/8/13.
//
//



//class IImage;
//class Tile;
//class IImageListener;
//class ChangedListener;
//class G3MContext;


public class TileRasterizerContext
{
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//  TileRasterizerContext(TileRasterizerContext that);

  public final Tile    _tile;
  public final boolean _mercator;

  public TileRasterizerContext(Tile tile, boolean mercator)
  {
     _tile = tile;
     _mercator = mercator;
  }

  public void dispose()
  {
  }
}
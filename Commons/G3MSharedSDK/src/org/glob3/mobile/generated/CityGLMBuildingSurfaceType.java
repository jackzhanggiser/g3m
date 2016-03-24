package org.glob3.mobile.generated; 
//
//  CityGMLBuildingSurface.cpp
//  G3MiOSSDK
//
//  Created by Jose Miguel SN on 24/3/16.
//
//

//
//  CityGMLBuildingSurface.hpp
//  G3MiOSSDK
//
//  Created by Jose Miguel SN on 24/3/16.
//
//



public enum CityGLMBuildingSurfaceType
{
  WALL,
  ROOF,
  GROUND;

   public int getValue()
   {
      return this.ordinal();
   }

   public static CityGLMBuildingSurfaceType forValue(int value)
   {
      return values()[value];
   }
}
package org.glob3.mobile.generated; 
//
//  IRenderer.h
//  G3MiOSSDK
//
//  Created by José Miguel S N on 31/05/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//




public abstract class Renderer
{
  public abstract void initialize(InitializationContext ic);

  public abstract int render(RenderContext rc);

  public abstract boolean onTouchEvent(TouchEvent touchEvent);

  public void dispose()
  {
  }
}
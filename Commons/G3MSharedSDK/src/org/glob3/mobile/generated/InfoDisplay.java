package org.glob3.mobile.generated; 
//
//  InfoDisplay.cpp
//  G3MiOSSDK
//
//  Created by Vidal Toboso on 21/04/14.
//
//

//
//  InfoDisplay.hpp
//  G3MiOSSDK
//
//  Created by Vidal Toboso on 21/04/14.
//
//



public abstract class InfoDisplay implements ChangedRendererInfoListener
{


  void dispose();

  public abstract void showDisplay();

  public abstract void hideDisplay();

  public abstract boolean isShowing();
}
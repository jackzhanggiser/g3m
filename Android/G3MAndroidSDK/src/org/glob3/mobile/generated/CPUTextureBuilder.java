package org.glob3.mobile.generated; 
//
//  CPUTextureBuilder.cpp
//  G3MiOSSDK
//
//  Created by José Miguel S N on 23/07/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

//
//  CPUTextureBuilder.hpp
//  G3MiOSSDK
//
//  Created by José Miguel S N on 23/07/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//



public class CPUTextureBuilder extends TextureBuilder
{
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: const GLTextureID createTextureFromImages(GL * gl, const java.util.ArrayList<const IImage*> images, int width, int height) const
  public final GLTextureID createTextureFromImages(GL gl, java.util.ArrayList<IImage> images, int width, int height)
  {
	final int imagesSize = images.size();
  
	if (imagesSize == 0)
	{
	  return GLTextureID.invalid();
	}
  
	if (imagesSize == 1)
	{
	  return gl.uploadTexture(images.get(0), width, height);
	}
  
	IImage im = images.get(0);
	IImage im2 = null;
	for (int i = 1; i < imagesSize; i++)
	{
	  IImage imTrans = images.get(i);
	  im2 = im.combineWith(imTrans, width, height);
	  if (i > 1)
	  {
		if (im != null)
			im.dispose();
	  }
	  im = im2;
	}
  
	final GLTextureID texID = gl.uploadTexture(im, width, height);
  
	if (imagesSize > 1)
	{
	  if (im != null)
		  im.dispose();
	}
  
	return texID;
  }

//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: const GLTextureID createTextureFromImages(GL * gl, const IFactory* factory, const java.util.ArrayList<const IImage*> images, const java.util.ArrayList<const Rectangle*> rectangles, int width, int height) const
  public final GLTextureID createTextureFromImages(GL gl, IFactory factory, java.util.ArrayList<IImage> images, java.util.ArrayList<Rectangle> rectangles, int width, int height)
  {
	final int imagesSize = images.size();
  
	if (imagesSize == 0)
	{
	  return GLTextureID.invalid();
	}
  
  //  const Rectangle baseRec(0,0, width, height);
  //
  //  if ((imagesSize == 1) && (rectangles[0]->equalTo(baseRec))) {
  //    return gl->uploadTexture(images[0], width, height);
  //  }
  //
  //
  //
  //  const IImage* image = factory->createImageFromSize(width, height);
  //  for (int i = 0; i < imagesSize; i++) {
  //    IImage* nextImage = image->combineWith(*images[i], *rectangles[i], width, height);
  //    delete image;
  //    image = nextImage;
  //  }
  //
  //  const GLTextureID texID = gl->uploadTexture(image, width, height);
  //
  //  delete image;
  //
  //  return texID;
  
  
	IImage base;
	int i = 0; //First image to merge
	Rectangle baseRec = new Rectangle(0,0, width, height);
	if (rectangles.size() > 0 && rectangles.get(0).equalTo(baseRec))
	{
	  base = images.get(0);
	  i = 1;
	}
	else
	{
	  base = factory.createImageFromSize(width, height);
  
  //    printf("IMAGE BASE %d, %d\n", base->getWidth(), base->getHeight());
	}
  
	for (; i < images.size(); i++)
	{
	  IImage im2 = base.combineWith(*images.get(i), *rectangles.get(i), width, height);
	  if (base != images.get(0))
	  {
		if (base != null)
			base.dispose();
	  }
	  base = im2;
	}
  
	final GLTextureID texID = gl.uploadTexture(base, width, height);
  
	if (rectangles.size() > 0 && base != images.get(0))
	{
	  if (base != null)
		  base.dispose();
	}
  
	return texID;
  }

}
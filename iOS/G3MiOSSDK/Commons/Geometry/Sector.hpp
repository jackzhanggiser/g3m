//
//  Sector.h
//  G3MiOSSDK
//
//  Created by Agustín Trujillo Pino on 12/06/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#ifndef G3MiOSSDK_Sector_h
#define G3MiOSSDK_Sector_h

#include <vector>

#include "Geodetic2D.hpp"
#include "Context.hpp"

class Sector {
  
private:
  const Geodetic2D _lower;
  const Geodetic2D _upper;
  
  const Angle _deltaLatitude;
  const Angle _deltaLongitude;

  
public:
  
  Sector(const Geodetic2D& lower,
         const Geodetic2D& upper) :
  _lower(lower),
  _upper(upper),
  _deltaLatitude(upper.latitude().sub(lower.latitude())),
  _deltaLongitude(upper.longitude().sub(lower.longitude()))
  {
  }
  
  Sector(const Sector& s) :
  _lower(s._lower),
  _upper(s._upper),
  _deltaLatitude(s._deltaLatitude),
  _deltaLongitude(s._deltaLongitude)
  {
  }
  
  Vector2D getScaleFactor(const Sector& s)
  {
    double u = _deltaLatitude.div(s._deltaLatitude);
    double v = _deltaLongitude.div(s._deltaLongitude);
    Vector2D scale(u,v);
    return scale;
  }
  
  Vector2D getTranslationFactor(const Sector& s)
  {
    double diff = _deltaLongitude.div(s._deltaLongitude);
    Vector2D uv = s.getUVCoordinates(_lower);
    
    Vector2D trans(uv.x(), uv.y()- diff);
    return trans;
  }
  
  bool fullContains(const Sector& s) const;
  
  static Sector fullSphere() {
    return Sector(Geodetic2D(Angle::fromDegrees(-90), Angle::fromDegrees(-180)),
                  Geodetic2D(Angle::fromDegrees(90), Angle::fromDegrees(180)));
  }
  
  const Geodetic2D lower() const {
    return _lower;
  }
  
  const Geodetic2D upper() const {
    return _upper;
  }
  
  bool contains(const Geodetic2D& position) const;
  
  bool touchesWith(const Sector& that) const;
  
  Angle getDeltaLatitude() const {
    return _deltaLatitude;
  }
  
  Angle getDeltaLongitude() const {
    return _deltaLongitude;
  }
  
  Geodetic2D getSW() const {
    return _lower;
  }
  
  Geodetic2D getNE() const {
    return _upper;
  }
  
  Geodetic2D getNW() const {
    return Geodetic2D(_upper.latitude(), _lower.longitude());
  }
  
  Geodetic2D getSE() const {
    return Geodetic2D(_lower.latitude(), _upper.longitude());
  }

  Geodetic2D getCenter() const {
    return Geodetic2D(Angle::midAngle(_lower.latitude(), _upper.latitude()),
                      Angle::midAngle(_lower.longitude(), _upper.longitude()));
  }
  
//  Angle getWidth() const { return _upper.longitude().sub(_lower.longitude()); }
//  Angle getHeight() const { return _upper.latitude().sub(_lower.latitude()); }
  
  Geodetic2D getInnerPoint(double u, double v) const;
  // (u,v) are similar to texture coordinates inside the Sector
  // (u,v)=(0,0) in NW point, and (1,1) in SE point
  
  Vector2D getUVCoordinates(const Geodetic2D& point) const;
  
  bool isBackOriented(const RenderContext *rc) const;
  
};


#endif

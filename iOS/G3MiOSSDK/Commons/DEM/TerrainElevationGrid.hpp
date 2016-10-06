//
//  DEM.hpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 10/5/16.
//
//

#ifndef DEM_hpp
#define DEM_hpp

#include "RCObject.hpp"
#include "Sector.hpp"
#include "Geodetic2D.hpp"
#include "Vector2I.hpp"

class Mesh;
class Geodetic3D;


class TerrainElevationGrid : public RCObject {
protected:
  const Sector     _sector;
#ifdef C_CODE
  const Vector2I   _extent;
#endif
#ifdef JAVA_CODE
  protected final Vector2I _extent;
#endif
  const Geodetic2D _resolution;

  TerrainElevationGrid(const Sector&   sector,
                       const Vector2I& extent);

  virtual ~TerrainElevationGrid();

public:

  const Vector2I getExtent() const;

  const Geodetic2D getResolution() const;

  virtual double getElevationAt(int x,
                                int y) const = 0;

  virtual Vector3D getMinMaxAverageElevations() const = 0;

  Mesh* createMesh(const Planet* planet,
                   float verticalExaggeration,
                   const Geodetic3D& positionOffset,
                   float pointSize) const;
  
};

#endif
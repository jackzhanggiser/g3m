//
//  BuildingDataParser.hpp
//  G3MiOSSDK
//
//  Created by Jose Miguel SN on 11/4/16.
//
//

#ifndef BuildingDataParser_hpp
#define BuildingDataParser_hpp

#include "CityGMLBuilding.hpp"
#include <vector>
#include <string>

class BuildingDataParser{
  

public:
  
  static void includeDataInBuildingSet(const std::string& data,
                                       const std::vector<CityGMLBuilding*>& buildings);
  
  static Mesh* createPointCloudMesh(const std::string& data, const Planet* planet, const ElevationData* elevationData);
  
  static Mesh* createSolarRadiationMesh(const std::string& data, const Planet* planet, const ElevationData* elevationData);
  
};

#endif /* BuildingDataParser_hpp */
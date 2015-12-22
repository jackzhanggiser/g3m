//
//  TimedLODTester.hpp
//  G3MiOSSDK
//
//  Created by Jose Miguel SN on 22/12/15.
//
//

#ifndef TimedLODTester_hpp
#define TimedLODTester_hpp

#include "TileLODTester.hpp"
#include "Tile.hpp"

class TimedTileLODTester: public TileLODTester{
private:
  TileLODTester* _nextTester;
  long long _time;
  
  class TimedTileLODTesterData: public TileLODTesterData{
  public:
    bool _lastMeetsRenderCriteriaResult;
    long long _lastMeetsRenderCriteriaTimeInMS;
    
    TimedTileLODTesterData(long long now){
      _lastMeetsRenderCriteriaTimeInMS = now;
      _lastMeetsRenderCriteriaResult = false;
    }
  };
  
public:
  
  TimedTileLODTester(long long time,
                     TileLODTester* nextTester):
  _time(time),
  _nextTester(nextTester)
  {}
  
  virtual ~TimedTileLODTester(){
    delete _nextTester;
  }
  
  virtual bool meetsRenderCriteria(int testerLevel,
                                   Tile* tile, const G3MRenderContext& rc) const{
    
    long long now = rc.getFrameStartTimer()->nowInMilliseconds();
    
    TimedTileLODTesterData* data = (TimedTileLODTesterData*) tile->getDataForLoDTester(testerLevel);
    if (data == NULL){
      data = new TimedTileLODTesterData(now);
      tile->setDataForLoDTester(testerLevel, data);
      data->_lastMeetsRenderCriteriaResult = _nextTester->meetsRenderCriteria(testerLevel+1, tile, rc);
    }
    
    if ((now - data->_lastMeetsRenderCriteriaTimeInMS) > _time){
      data->_lastMeetsRenderCriteriaResult = _nextTester->meetsRenderCriteria(testerLevel+1, tile, rc);
    }
    
    return data->_lastMeetsRenderCriteriaResult;
  }
  
  virtual bool isVisible(int testerLevel, Tile* tile, const G3MRenderContext& rc) const{
    return _nextTester->isVisible(testerLevel+1, tile, rc);
  }
  
  virtual void onTileHasChangedMesh(int testerLevel, Tile* tile) const{
    _nextTester->onTileHasChangedMesh(testerLevel+1, tile);
  }
  
};

#endif /* TimedLODTester_hpp */

//
//  G3MMarkersDemoScene.cpp
//  G3MApp
//
//  Created by Diego Gomez Deck on 11/18/13.
//  Copyright (c) 2013 Igo Software SL. All rights reserved.
//

#include "G3MMarkersDemoScene.hpp"

#include <G3MiOSSDK/G3MWidget.hpp>
#include <G3MiOSSDK/LayerSet.hpp>
#include <G3MiOSSDK/MapBoxLayer.hpp>
#include <G3MiOSSDK/IDownloader.hpp>
#include <G3MiOSSDK/DownloadPriority.hpp>
#include <G3MiOSSDK/IBufferDownloadListener.hpp>
#include <G3MiOSSDK/IJSONParser.hpp>
#include <G3MiOSSDK/JSONObject.hpp>
#include <G3MiOSSDK/JSONArray.hpp>
#include <G3MiOSSDK/JSONNumber.hpp>
#include <G3MiOSSDK/Mark.hpp>
#include <G3MiOSSDK/Geodetic3D.hpp>
#include <G3MiOSSDK/IStringUtils.hpp>
#include <G3MiOSSDK/MarksRenderer.hpp>

#include "G3MDemoModel.hpp"


class G3MMarkersDemoScene_BufferDownloadListener : public IBufferDownloadListener {
private:
  G3MMarkersDemoScene* _scene;
public:
  G3MMarkersDemoScene_BufferDownloadListener(G3MMarkersDemoScene* scene) :
  _scene(scene)
  {
  }

  void onDownload(const URL& url,
                  IByteBuffer* buffer,
                  bool expired) {

    const JSONBaseObject* jsonBaseObject = IJSONParser::instance()->parse(buffer);
    if (jsonBaseObject == NULL) {
      ILogger::instance()->logError("Can't parse (1) \"%s\"", url.getPath().c_str());
    }
    else {
      const JSONObject* jsonObject = jsonBaseObject->asObject();
      if (jsonObject == NULL) {
        ILogger::instance()->logError("Can't parse (2) \"%s\"", url.getPath().c_str());
      }
      else {
        const IStringUtils* su = IStringUtils::instance();

        const JSONArray* list = jsonObject->getAsArray("list");
        for (int i = 0; i < list->size(); i++) {

          const JSONObject* city = list->getAsObject(i);
          const JSONObject* coords = city->getAsObject("coord");
          const Geodetic3D position = Geodetic3D::fromDegrees(coords->getAsNumber("lat")->value(),
                                                              coords->getAsNumber("lon")->value(),
                                                              0);
          const JSONArray* weather = city->getAsArray("weather");
          const JSONObject* weatherObject = weather->getAsObject(0);

          std::string icon;
          if (weatherObject->getAsString("icon", "DOUBLE") == "DOUBLE") {
            icon = su->toString( (int) weatherObject->getAsNumber("icon")->value() ) + "d.png";
            if (icon.length() < 7) {
              icon = "0" + icon;
            }
          }
          else {
            icon = weatherObject->getAsString("icon", "DOUBLE") + ".png";
          }

          Mark* mark = new Mark(city->getAsString("name", ""),
                                URL("http://openweathermap.org/img/w/" + icon),
                                position,
                                RELATIVE_TO_GROUND,
                                0,
                                true,
                                14);

          _scene->addMark(mark);

        }
      }

      delete jsonBaseObject;
    }

    delete buffer;
  }

  void onError(const URL& url) {
    ILogger::instance()->logError("Error downloading \"%s\"", url.getPath().c_str());
  }

  void onCancel(const URL& url) {
    // do nothing
  }

  void onCanceledDownload(const URL& url,
                          IByteBuffer* buffer,
                          bool expired) {
    // do nothing
  }

};

void G3MMarkersDemoScene::addMark(Mark* mark) {
  getModel()->getMarksRenderer()->addMark(mark);
}

void G3MMarkersDemoScene::rawActivate(const G3MContext* context) {
  G3MDemoModel* model     = getModel();
  G3MWidget*    g3mWidget = model->getG3MWidget();

  g3mWidget->setBackgroundColor(Color::fromRGBA(0.9f, 0.21f, 0.21f, 1.0f));

  MapBoxLayer* layer = new MapBoxLayer("examples.map-m0t0lrpu",
                                       TimeInterval::fromDays(30),
                                       true,
                                       2);
  model->getLayerSet()->addLayer(layer);

  IDownloader* downloader = context->getDownloader();

  _requestId = downloader->requestBuffer(URL("http://openweathermap.org/data/2.1/find/city?bbox=-80,-180,80,180,4&cluster=yes"),
                                         DownloadPriority::HIGHEST,
                                         TimeInterval::fromHours(1),
                                         false,
                                         new G3MMarkersDemoScene_BufferDownloadListener(this),
                                         true);
}

void G3MMarkersDemoScene::deactivate(const G3MContext* context) {
  context->getDownloader()->cancelRequest(_requestId);

  G3MDemoScene::deactivate(context);
}

void G3MMarkersDemoScene::rawSelectOption(const std::string& option,
                                          int optionIndex) {
  
}
